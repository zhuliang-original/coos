package top.coos.exception;

public enum Error {
    BASE(10000, "系统错误"),

    VALIDATE_ONLY(10001, "验证唯一错误"),

    VALIDATE_UNION_ONLY(10002, "验证组合唯一错误"),

    DELETE_DATA_ASSOCIATED_DATA(20001, "删除数据在其它表有关联数据");

    private int errcode;

    private String errmsg;

    Error(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
