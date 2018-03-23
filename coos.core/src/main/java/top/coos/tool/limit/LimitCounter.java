package top.coos.tool.limit;

/**
 * 计数器算法
 * 
 * @author ZhuLiang
 * 
 */
public class LimitCounter extends Limit {

    private int count = 0;

    // private final long interval = 1000000000; // 时间窗口s

    public LimitCounter(double limit) {
        super(limit);
    }

    synchronized public boolean validate() {
        ValidateResult result = new ValidateResult();
        
        boolean validate = false;
        if(isNextSecond()){
            count = 0;
        }
        count++;
        // 判断当前时间窗口内是否超过最大请求控制数
        validate = count <= limit;
        
        // long now = getNowTime();
        // if (now <= timestamp + interval) {
        // // 在时间窗口内
        // count++;
        // // 判断当前时间窗口内是否超过最大请求控制数
        // validate = count <= limit;
        // } else {
        // timestamp = now;
        // // 超时后重置
        // count = 1;
        // validate = true;
        // }
        result.setResult(validate);
        results.add(result);
        return result.isResult();
    }

    @Override
    public String getName() {
        return "计数器算法";
    }

}
