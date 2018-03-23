package top.coos.servlet.model;

public class ModelAndView {
    public enum Type {
        JSON, PAGE, FORWARD, REDIRECT, CONTENT
    }

    private Type type = Type.JSON;

    private Object value;

    public ModelAndView(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
