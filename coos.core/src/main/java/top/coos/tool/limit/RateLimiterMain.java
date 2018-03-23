package top.coos.tool.limit;


public class RateLimiterMain {
    private static int sc = 0;

    static final CRateLimiter limiter = new CRateLimiter(10.0);

    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        final int count = 20;
        final long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            final int index = i;
            new Thread() {
                @Override
                public void run() {
                    boolean acquire = limiter.acquire(1.0, 1);
                    sc++;
                    System.out.println("执行业务代码:" + index + ",acquire:" + acquire);

                    if (sc >= count) {
                        long end = System.currentTimeMillis();
                        System.out.println("耗时：" + (end - start));
                    }
                }

            }.start();
            try {
                // Thread.sleep(400);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
