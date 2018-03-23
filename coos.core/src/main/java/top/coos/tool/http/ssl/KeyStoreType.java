package top.coos.tool.http.ssl;

public enum KeyStoreType {
    PKCS12("PKCS12", "PKCS12类型", "PKCS12"), CA("CA", "CA类型", "JKS");

    private String value;

    private String text;

    private String type;

    private KeyStoreType(String value, String text, String type) {
        this.text = text;
        this.value = value;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
