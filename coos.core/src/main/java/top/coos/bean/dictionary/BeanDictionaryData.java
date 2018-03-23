package top.coos.bean.dictionary;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

/**
 * 系统数据库字典值
 */
public class BeanDictionaryData extends Support {

    private static final long serialVersionUID = 1L;

    @Override
    public BeanDictionaryData clone() {
        return (BeanDictionaryData) super.clone();
    }

    @PrimaryKey
    @Column(name = "dictionarydataid", comment = "编号")
    private String dictionarydataid;

    @Column(name = "dictionaryid", comment = "字典类目")
    private String dictionaryid;

    @Column(name = "text", comment = "选项文案")
    private String text;

    @Column(name = "value", comment = "选项值")
    private String value;

    @Column(name = "fonticon", comment = "字体图标", inputType = InputType.IMAGE)
    private String fonticon;

    @Column(name = "image", comment = "图片", inputType = InputType.IMAGE)
    private String image;

    @Column(name = "sequence", comment = "顺序号", inputType = InputType.NUMBER)
    private Integer sequence;

    @Column(name = "remark", comment = "备注描述")
    private String remark;

    public String getFonticon() {
        return fonticon;
    }

    public void setFonticon(String fonticon) {
        this.fonticon = fonticon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDictionarydataid() {
        return dictionarydataid;
    }

    public void setDictionarydataid(String dictionarydataid) {
        this.dictionarydataid = dictionarydataid;
    }

    public String getDictionaryid() {
        return dictionaryid;
    }

    public void setDictionaryid(String dictionaryid) {
        this.dictionaryid = dictionaryid;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
