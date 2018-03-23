package top.coos.bean.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Result implements Serializable {

    public enum DataType {
        LIST, ONE
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Object value;

    private DataType datatype;

    private int datacount = 0;

    private int executecount = 0;

    private Map<String, Object> searchforsql = new HashMap<String, Object>();

    private Map<String, Object> searchforview = new HashMap<String, Object>();

    public Result addSearchForSql(String key, Object value) {
        if (searchforsql == null) {
            searchforsql = new HashMap<String, Object>();
        }
        searchforsql.put(key, value);
        return this;
    }

    public Result addSearchForView(String key, Object value) {
        if (searchforview == null) {
            searchforview = new HashMap<String, Object>();
        }
        searchforview.put(key, value);
        return this;
    }

    public Map<String, Object> getSearchforsql() {
        return searchforsql;
    }

    public void setSearchforsql(Map<String, Object> searchforsql) {
        this.searchforsql = searchforsql;
    }

    public Map<String, Object> getSearchforview() {
        return searchforview;
    }

    public void setSearchforview(Map<String, Object> searchforview) {
        this.searchforview = searchforview;
    }

    public Object getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public void setValue(Object value) {
        if (value != null) {
            if (datatype.equals(DataType.LIST)) {
                datacount = ((List<Map<String, Object>>) value).size();
            } else {
                datacount = 1;
            }
        } else {
            datacount = 0;
        }
        this.value = value;
    }

    public DataType getDatatype() {
        return datatype;
    }

    public void setDatatype(DataType datatype) {
        this.datatype = datatype;
    }

    public int getDatacount() {
        return datacount;
    }

    public void setDatacount(int datacount) {
        this.datacount = datacount;
    }

    public int getExecutecount() {
        return executecount;
    }

    public void setExecutecount(int executecount) {
        this.executecount = executecount;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
