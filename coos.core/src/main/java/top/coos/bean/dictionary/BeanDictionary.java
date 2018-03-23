package top.coos.bean.dictionary;

import java.util.ArrayList;
import java.util.List;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

/**
 * 系统数据库字典类目
 */
public class BeanDictionary extends Support {

    @Override
    public BeanDictionary clone() {
        BeanDictionary object = (BeanDictionary) super.clone();
        if (datas != null) {
            List<BeanDictionaryData> os = new ArrayList<BeanDictionaryData>();
            for (BeanDictionaryData one : datas) {
                os.add(one.clone());
            }
            object.datas = os;
        }
        return object;
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Column(name = "dictionaryid", comment = "编号")
    private String dictionaryid;

    @Column(name = "name", comment = "名称")
    private String name;

    @Column(name = "chinesename", comment = "中文名称")
    private String chinesename;

    @Column(name = "remark", comment = "备注", inputType = InputType.TEXTAREA)
    private String remark;

    @Column(name = "sequence", comment = "顺序号", inputType = InputType.NUMBER)
    private Integer sequence;

    public List<BeanDictionaryData> datas;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<BeanDictionaryData> getDatas() {
        return datas;
    }

    public void setDatas(List<BeanDictionaryData> datas) {
        this.datas = datas;
    }

    public String getDictionaryid() {
        return dictionaryid;
    }

    public void setDictionaryid(String dictionaryid) {
        this.dictionaryid = dictionaryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
