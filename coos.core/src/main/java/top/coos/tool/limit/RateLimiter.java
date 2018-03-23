package top.coos.tool.limit;

import java.util.concurrent.TimeUnit;

public class RateLimiter {

    /**
     * 以1秒为计量单位
     */
    final double maxBurstSeconds = 1.0D;

    /**
     * 创建RateLimiter时的时间戳; 用于避免可能的溢出/时间包装错误。
     */
    private final long offsetNanos = systemNanoTime();

    /**
     * 当前存储的许可证
     */
    double storedPermits;

    /**
     * 存储许可证的最大数量
     */
    double maxPermits;

    /**
     * 两个单位请求之间的间隔，以我们稳定的速度。 例如，每秒5次允许的不稳定速率具有200ms的稳定间隔。
     */
    volatile double stableIntervalMicros;

    /**
     * 下一个请求（不管其大小）的时间将被授予。 在提出请求后，今后将进一步推动。 大的请求推动这个进一步比小的请求。
     */
    private long nextFreeTicketMicros = 0L;

    private final Object mutex = new Object();

    public RateLimiter(double permitsPerSecond) {
        setRate(permitsPerSecond);
    }

    public void acquire() {
        acquire(1);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
        long timeoutMicros = unit.toMicros(timeout);
        long microsToWait;
        synchronized (mutex) {
            long nowMicros = readSafeMicros();
            if (nextFreeTicketMicros > nowMicros + timeoutMicros) {
                return false;
            } else {
                microsToWait = reserveNextTicket(permits, nowMicros);
            }
        }
        sleepMicrosUninterruptibly(microsToWait);
        return true;
    }

    /**
     * Acquires the given number of permits from this {@code RateLimiter},
     * blocking until the request be granted.
     * 
     * @param permits
     *            the number of permits to acquire
     */
    public void acquire(int permits) {
        long microsToWait;
        synchronized (mutex) {
            microsToWait = reserveNextTicket(permits, readSafeMicros());
        }
        sleepMicrosUninterruptibly(microsToWait);
    }

    public void sleepMicrosUninterruptibly(long micros) {
        if (micros > 0) {
            sleepUninterruptibly(micros, TimeUnit.MICROSECONDS);
        }
    }

    public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
        boolean interrupted = false;
        try {
            long remainingNanos = unit.toNanos(sleepFor);
            long end = System.nanoTime() + remainingNanos;
            while (true) {
                try {
                    TimeUnit.NANOSECONDS.sleep(remainingNanos);
                    return;
                } catch (InterruptedException e) {
                    interrupted = true;
                    remainingNanos = end - System.nanoTime();
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private long reserveNextTicket(double requiredPermits, long nowMicros) {
        resync(nowMicros);
        long microsToNextFreeTicket = nextFreeTicketMicros - nowMicros;
        double storedPermitsToSpend = Math.min(requiredPermits, this.storedPermits);
        double freshPermits = requiredPermits - storedPermitsToSpend;

        long waitMicros = storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend) + (long) (freshPermits * stableIntervalMicros);

        this.nextFreeTicketMicros = nextFreeTicketMicros + waitMicros;
        this.storedPermits -= storedPermitsToSpend;
        return microsToNextFreeTicket;
    }

    /**
     * 设置速率
     * 
     * @param permitsPerSecond
     */
    public final void setRate(double permitsPerSecond) {
        synchronized (mutex) {
            resync(readSafeMicros());
            double stableIntervalMicros = TimeUnit.SECONDS.toMicros(1L) / permitsPerSecond;
            this.stableIntervalMicros = stableIntervalMicros;
            doSetRate(permitsPerSecond, stableIntervalMicros);
        }
    }

    /**
     * 重新同步
     * 
     * @param nowMicros
     */
    private void resync(long nowMicros) {
        // 如果nextFreeTicket在过去，请重新同步到现在
        if (nowMicros > nextFreeTicketMicros) {
            storedPermits = Math.min(maxPermits, storedPermits + (nowMicros - nextFreeTicketMicros) / stableIntervalMicros);
            nextFreeTicketMicros = nowMicros;
        }
    }

    public final double getRate() {
        return TimeUnit.SECONDS.toMicros(1L) / stableIntervalMicros;
    }

    void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
        double oldMaxPermits = this.maxPermits;
        maxPermits = maxBurstSeconds * permitsPerSecond;
        storedPermits = (oldMaxPermits == 0.0) ? 0.0 // initial state
                : storedPermits * maxPermits / oldMaxPermits;
    }

    private long readSafeMicros() {
        return TimeUnit.NANOSECONDS.toMicros(systemNanoTime() - offsetNanos);
    }

    long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
        return 0L;
    }

    public static long systemNanoTime() {
        return System.nanoTime();
    }
}
