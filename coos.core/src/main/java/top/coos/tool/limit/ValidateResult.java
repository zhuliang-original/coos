package top.coos.tool.limit;

import top.coos.tool.datetime.DatetimeTool;

public class ValidateResult {

    private long start;

    private long end;

    private boolean result;

    public ValidateResult() {
        start = System.nanoTime();
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        end = DatetimeTool.currentTimeMillis();
        this.result = result;
    }

}
