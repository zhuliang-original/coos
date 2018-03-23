package top.coos.tool.limit;

import java.util.ArrayList;
import java.util.List;

public abstract class Limit {

    public abstract String getName();

    protected long timestamp = 0L;

    protected List<ValidateResult> results = new ArrayList<ValidateResult>();

    protected long getNowTime() {
        // System.out.println(System.nanoTime());
        // System.out.println(System.currentTimeMillis());
        return System.nanoTime();
        // return System.currentTimeMillis();
    }

    protected double limit;

    public boolean isNextSecond() {
        long now = getNowTime();
        long difference = now - timestamp;
        if (difference >= 1000000000.00) {
            timestamp = now;
            return true;
        }
        return false;
    }

    public Limit(double limit) {
        this.limit = limit;
    }

    public double getLimit() {
        return limit;
    }

    long start = System.nanoTime();

    public void arrange() {
        int success = 0;
        int error = 0;
        int index = 0;
        long expendTime = 0;
        for (int i = 0; i < results.size(); i++) {
            ValidateResult result = results.get(i);
            long x = result.getStart() - start;
            expendTime = x;
            if (x < 1000000000) {
                if (result.isResult()) {
                    success++;
                } else {
                    error++;
                }
            } else {
                index++;
                if (x == 1000000000) {
                    if (result.isResult()) {
                        success++;
                    } else {
                        error++;
                    }
                    System.out.println(this.getName() + " 第" + index + "区间,共处理:" + (success + error) + ",耗时:" + (expendTime / 1000000.00) + ",通过:" + success + ",不通过:" + error + ",处理频次:" + getLimit() + "/s");
                    error = 0;
                    success = 0;

                } else {
                    expendTime = results.get(i - 1).getStart() - start;
                    System.out.println(this.getName() + " 第" + index + "区间,共处理:" + (success + error) + ",耗时:" + (expendTime / 1000000.00) + ",通过:" + success + ",不通过:" + error + ",处理频次:" + getLimit() + "/s");
                    error = 0;
                    success = 0;
                    if (result.isResult()) {
                        success++;
                    } else {
                        error++;
                    }
                }
                start = result.getStart();
            }
        }
        index++;
        System.out.println(this.getName() + " 第" + index + "区间,共处理:" + (success + error) + ",耗时:" + (expendTime / 1000000.00) + ",通过:" + success + ",不通过:" + error + ",处理频次:" + getLimit() + "/s");

    }

    public abstract boolean validate();

    public static void main(String[] args) throws Exception {
        final Limit limit1 = new LimitCounter(100.0);
        test(limit1);
        System.out.println("---------------------------------");
        final Limit limit2 = new LimitLeakyBucket(100.0);
        test(limit2);
        System.out.println("---------------------------------");
        final Limit limit3 = new LimitTokenBucket(100.0);
        test(limit3);
        System.out.println("---------------------------------");
    }

    static int error = 0;

    static int success = 0;

    public static void test(final Limit limit) throws Exception {
        new Thread() {

            @Override
            public void run() {
                try {
                    int count = 2001;
                    long start = System.currentTimeMillis();
                    for (int i = 1; i <= count; i++) {
                        final int index = i;
                        // if (i % 101 == 0) {
                        // }
                        new Thread() {

                            @Override
                            public void run() {

                                if (index % 101 == 0) {
                                    try {

                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                boolean validate = limit.validate();
                                if (!validate) {
                                    error++;
                                } else {
                                    success++;
                                }
                            }

                        }.start();
                    }
                    long end = System.currentTimeMillis();
                    limit.arrange();
                    System.out.println(limit.getName() + " 调用:" + count + "次,耗时:" + (end - start) + ",通过:" + success + ",不通过:" + error + ",处理频次:" + limit.getLimit() + "/s");

                } catch (Exception e) {
                }
            }

        }.run();
    }
}
