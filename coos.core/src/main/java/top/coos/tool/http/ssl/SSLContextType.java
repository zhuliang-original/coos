package top.coos.tool.http.ssl;

public enum SSLContextType {
    SSL("SSL", "SSL证书"), TCP("TCP", "TCP证书"), TLS("TLSv1.2", "TLS");

    private String value;

    private String text;

    private SSLContextType(String value, String text) {
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
