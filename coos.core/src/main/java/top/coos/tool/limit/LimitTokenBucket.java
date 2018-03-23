package top.coos.tool.limit;

/**
 * 令牌桶算法
 * 
 * @author ZhuLiang
 * 
 */
public class LimitTokenBucket extends Limit {

    public LimitTokenBucket(double limit) {
        super(limit);
        this.capacity = limit;
        this.rate = limit;
    }

    private double capacity; // 桶的容量

    protected double rate; // 速率

    private double tokens = 0; // 当前令牌数量

    private boolean fulled = false;

    synchronized public boolean validate() {
        ValidateResult result = new ValidateResult();
        boolean validate = false;

        if (isNextSecond() || !fulled) {
            tokens = tokens + rate;
            fulled = true;
        }
        tokens = Math.min(capacity, tokens);

        if (tokens < 1) {
            // 若不到1个令牌,则拒绝
            validate = false;
        } else {
            // 还有令牌，领取令牌
            tokens -= 1;
            validate = true;
        }

        // long now = getNowTime();
        // // 先添加令牌
        // tokens = Math.min(capacity, tokens + (now - timestamp) * rate);
        // timestamp = now;
        // if (tokens < 1) {
        // // 若不到1个令牌,则拒绝
        // validate = false;
        // } else {
        // // 还有令牌，领取令牌
        // tokens -= 1;
        // validate = true;
        // }
        result.setResult(validate);
        results.add(result);
        return result.isResult();
    }

    @Override
    public String getName() {
        return "令牌桶算法";
    }
}
