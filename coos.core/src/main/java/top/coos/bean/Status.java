package top.coos.bean;

import top.coos.Support;

public class Status extends Support {

    /** @Fields serialVersionUID: */

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Status() {

    }

    /**
     * 
     * @param code
     * @param message
     */
    public Status(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    /**
     * 操作成功 0
     */
    public static Status SUCCESS() {
        return new Status(0, "成功");
    }

    /**
     * 错误 -1
     */
    public static Status ERROR() {
        return new Status(-1, "系统错误");
    }

    private String errmsg;

    private int errcode;

    private Object result;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
