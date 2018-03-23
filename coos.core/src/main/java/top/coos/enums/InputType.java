package top.coos.enums;

public enum InputType {
    TEXT("TEXT", "文本", 125),

    DATETIME("DATETIME", "日期时间", 50),

    DATE("DATE", "日期", 50),

    TIME("TIME", "时间", 50),

    NUMBER("NUMBER", "数字", 10),

    SLIDER("SLIDER", "进度条", 10),

    SWITCH("SWITCH", "开关", 1),

    SELECT("SELECT", "下拉框", 125),

    SELECTS("SELECTS", "多选下拉框", 125),

    RADIO("RADIO", "单选", 125),

    CHECKBOX("CHECKBOX", "多选", 125),

    TEXTAREA("TEXTAREA", "文本域", -1),

    FILE("FILE", "文件", 500),

    FILES("FILES", "多文件", 500),

    IMAGE("IMAGE", "图片", 500),

    IMAGES("IMAGES", "多图片", 500),

    EDITOR("EDITOR", "编辑器", -1),

    AUDIO("AUDIO", "音频", 500),

    VIDEO("VIDEO", "视频", 500),

    COLOR("COLOR", "颜色", 10);

    private InputType(String value, String text, int columnDefaultLength) {
        this.value = value;
        this.text = text;
        this.columnDefaultLength = columnDefaultLength;
    }

    private String value;

    private String text;

    private int columnDefaultLength;

    public String getValue() {
        return value;
    }

    public int getColumnDefaultLength() {
        return columnDefaultLength;
    }

    public String getText() {
        return text;
    }

}