package top.coos.constant;

public enum Color {
    WHITE("white", "白色"),

    GREEN("green", "绿色"),

    BLUE("blue", "蓝色"),

    YELLOW("yellow", "黄色"),

    RED("red", "红色"),

    GREY("grey", "灰色"),

    BLACK("black", "黑色");

    private Color(String value, String text) {
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
