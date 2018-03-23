package top.coos.tool.limit;

/**
 * 漏桶算法
 * 
 * @author ZhuLiang
 * 
 */
public class LimitLeakyBucket extends Limit {

    public LimitLeakyBucket(double limit) {
        super(limit);
        this.capacity = limit;
        this.rate = limit;
    }

    private double capacity; // 桶的容量

    protected double rate; // 泄漏率

    private double water = 0.0; // 当前水量(当前累积请求数)

    private boolean deleted = false;

    synchronized public boolean validate() {
        ValidateResult result = new ValidateResult();
        boolean validate = false;

        // 是否到下一秒
        if (isNextSecond() || !deleted) {
            water = water - rate;
            deleted = true;
        } else {
        }
        water = Math.max(0, water);
        if ((water + 1) <= capacity) {
            // 尝试加水,并且水还未满
            water += 1;
            validate = true;
        } else {
            // 水满，拒绝加水
            validate = false;
        }
        // long now = getNowTime();
        // // 先执行漏水，计算剩余水量
        // water = Math.max(0.0, water - ((now - timestamp) * rate /
        // 1000000000.00));
        // timestamp = now;
        // if (water <= capacity) {
        // // 尝试加水,并且水还未满
        // water += 1;
        // validate = true;
        // } else {
        // // 水满，拒绝加水
        // validate = false;
        // }
        result.setResult(validate);
        results.add(result);
        return result.isResult();
    }

    @Override
    public String getName() {
        return "漏桶算法";
    }
}
