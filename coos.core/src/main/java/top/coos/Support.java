package top.coos;

import java.io.Serializable;

public abstract class Support implements Serializable, Cloneable {

    private static final long serialVersionUID = -5427241567365766294L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public Support clone() {
        try {
            return (Support) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

}
