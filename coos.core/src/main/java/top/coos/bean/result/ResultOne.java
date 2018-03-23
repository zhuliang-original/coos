package top.coos.bean.result;

public class ResultOne extends Result {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ResultOne() {
        super.setDatatype(DataType.ONE);
    }

}
