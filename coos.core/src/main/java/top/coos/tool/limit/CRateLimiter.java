package top.coos.tool.limit;

import java.util.concurrent.TimeUnit;

public class CRateLimiter {
    private long timestamp = 0L;

    private double tokenStableInterval;

    private double maxTokens;

    private double nowTokens;

    /**
     * @param tokenPerSecond
     *            令牌每秒生成速率
     */
    public CRateLimiter(double tokenPerSecond) {
        init(tokenPerSecond);
    }

    /**
     * 初始化
     * 
     * @param tokenPerSecond
     */
    private void init(double tokenPerSecond) {
        // 计算每个令牌生成需要的时间
        tokenStableInterval = TimeUnit.SECONDS.toNanos(1L) / tokenPerSecond;
        // 设置最大令牌数量
        maxTokens = 1.0D * tokenPerSecond;
        // 设置当前令牌数量
        nowTokens = 0.0D;
    }

    /**
     * 默认取1.0个令牌 始终等待不会超时
     * 
     * @return
     */
    public boolean acquire() {
        return acquire(1.0);
    }

    /**
     * 取出N个令牌 始终等待不会超时
     * 
     * @param token
     *            令牌
     * @return
     */
    public boolean acquire(double token) {
        return acquire(token, Long.MAX_VALUE);
    }

    /**
     * 
     * 取出N个令牌 计算超过设定的时间 将会直接返回false 不会再等待 超时默认以秒为单位
     * 
     * @param token
     * @param timeout
     * @return
     */
    public boolean acquire(double token, long timeout) {
        return acquire(token, timeout, TimeUnit.SECONDS);
    }

    /**
     * 取出N个令牌 计算超过设定的时间 将会直接返回false
     * 
     * @param token
     * @param timeout
     * @param unit
     * @return
     */
    public boolean acquire(double token, long timeout, TimeUnit unit) {
        // 刷新令牌数量
        resetTokens();
        // 转换超时时间
        long timeoutNanos = unit.toNanos(timeout);
        // 获取令牌 得到等待时间 如果小于0则获取不到令牌无需等待直接返回
        long waitTime = waitToken(token, timeoutNanos);
        if (waitTime < 0L) {
            return false;
        } else if (waitTime > 0L) {
            sleep(waitTime);
            resetTokens();
        }
        return true;
    }

    /**
     * 获取令牌 得到等待时间 如果小于0则获取不到令牌无需等待
     * 
     * @param token
     * @param timeout
     * @return
     */
    synchronized private long waitToken(double token, long timeout) {
        long waitTime = 0L;
        // 比对当前令牌
        if (token > this.nowTokens) {
            double diffToken = token - this.nowTokens;
            // 计算需要等待毫秒数
            waitTime = (long) (tokenStableInterval * diffToken);
            // 等待时间大于 预设的超时时间 则无需等待 直接返回获取不到令牌结果
            if (waitTime > timeout) {
                return -1;
            }
        }
        // 直接移除相应的令牌数量
        calculateTokens(-token);
        return waitTime;
    }

    /**
     * 刷新令牌 根据时间偏移 计算添加的令牌
     */
    synchronized private void resetTokens() {
        calculateTokens((diffTime() / tokenStableInterval));
    }

    /**
     * 计算令牌
     * 
     * @param token
     *            令牌数量
     */
    synchronized private void calculateTokens(double token) {
        nowTokens += token;
        this.nowTokens = Math.min(this.maxTokens, this.nowTokens);
    }

    synchronized private long diffTime() {
        if (timestamp == 0L) {
            timestamp = systemNanoTime();
        }
        long now = systemNanoTime();
        long diffTime = now - timestamp;
        timestamp = now;
        return diffTime;
    }

    private void sleep(long time) {
        sleep(time, TimeUnit.NANOSECONDS);
    }

    private void sleep(long time, TimeUnit unit) {
        try {
            unit.sleep(time);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public static long systemNanoTime() {
        return System.nanoTime();
    }
}
