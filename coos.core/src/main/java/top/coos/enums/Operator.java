package top.coos.enums;

public enum Operator {
    EQ("=", "等于"),

    NEQ("<>", "不等于"),

    GT(">", "大于"),

    LT("<", "小于"),

    GTE(">=", "大于等于"),

    LTE("<=", "小于等于"),

    LIKE("LIKE", "前后模糊匹配"),

    LIKE_AFTER("LIKE%", "后模糊匹配"),

    LIKE_BEFORE("%LIKE", "前模糊匹配"),

    IN("IN", "包含"),

    NOT_IN("NOT IN", "不包含");

    private Operator(String value, String text) {
        this.value = value;
        this.text = text;
    }

    private String value;

    private String text;

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
