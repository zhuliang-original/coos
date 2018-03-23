package top.coos.bean.template;

import top.coos.Support;


/**
 * 
 * @Description: TODO(操作模板)
 * @author 朱亮
 * @date 2016-3-17 下午3:17:52
 * 
 */
public class Template extends Support {
    @Override
    public Template clone() {
        Template object = (Template) super.clone();
        return object;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String templateid;

    private String title;

    private String version;

    private String templatekey;

    private String author;

    private String remark;

    private String programid;

    private String projectid;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplatekey() {
        return templatekey;
    }

    public void setTemplatekey(String templatekey) {
        this.templatekey = templatekey;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
