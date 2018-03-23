package top.coos.servlet.enums;

public enum RequestMethod {
    GET("GET", "GET请求"),

    POST("POST", "POST请求"),

    HEAD("HEAD", "HEAD请求"),

    PUT("PUT", "PUT请求"),

    PATCH("PATCH", "PATCH请求"),

    DELETE("DELETE", "DELETE请求"),

    OPTIONS("OPTIONS", "OPTIONS请求"),

    TRACE("TRACE", "TRACE请求");

    private String value;

    private String text;

    RequestMethod(String value, String text) {
        this.text = text;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
