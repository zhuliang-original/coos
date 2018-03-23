package top.coos.bean.model;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

public class BeanModel extends Support {

	@Override
	public BeanModel clone() {

		BeanModel object = (BeanModel) super.clone();
		return object;
	}

	/**
     * 
     */
	private static final long serialVersionUID = 7038978348899755221L;

	@PrimaryKey
	@Column(name = "modelid", comment = "编号")
	private String modelid;

	@Column(name = "name", comment = "名称")
	private String name;

	@Column(name = "staticvalue", comment = "静态值")
	private String staticvalue;

	@Column(name = "icon", comment = "图标")
	private String icon;

	@Column(name = "image", comment = "图片")
	private String image;

	@Column(name = "images", comment = "图片列表")
	private String images;

	@Column(name = "files", comment = "附件列表")
	private String files;

	@Column(name = "accountid", comment = "账号")
	private String accountid;

	@Column(name = "csscontent", comment = "CSS", inputType = InputType.TEXTAREA)
	private String csscontent;

	@Column(name = "htmlcontent", comment = "HTML", inputType = InputType.TEXTAREA)
	private String htmlcontent;

	@Column(name = "jscontent", comment = "JS", inputType = InputType.TEXTAREA)
	private String jscontent;

	@Column(name = "config", comment = "配置", inputType = InputType.TEXTAREA)
	private String config;

	@Column(name = "remark", comment = "备注")
	private String remark;

	@Column(name = "status", comment = "状态", inputType = InputType.NUMBER)
	private Integer status;

	@Column(name = "auditstatus", comment = "审核状态", inputType = InputType.NUMBER)
	private Integer auditstatus;

	@Column(name = "openstatus", comment = "开放状态", inputType = InputType.NUMBER)
	private Integer openstatus;

	@Column(name = "sharestatus", comment = "分享状态", inputType = InputType.NUMBER)
	private Integer sharestatus;

	@Column(name = "sequence", comment = "顺序号", inputType = InputType.NUMBER)
	private Integer sequence;

	@Column(name = "type", comment = "类型")
	private String type;

	@Column(name = "modelplace", comment = "位置")
	private String modelplace;

	@Column(name = "createtime", comment = "创建时间", inputType = InputType.DATETIME)
	private String createtime;

	private boolean collect;

	public boolean isCollect() {

		return collect;
	}

	public void setCollect(boolean collect) {

		this.collect = collect;
	}

	public String getModelplace() {

		return modelplace;
	}

	public void setModelplace(String modelplace) {

		this.modelplace = modelplace;
	}

	public Integer getStatus() {

		return status;
	}

	public void setStatus(Integer status) {

		this.status = status;
	}

	public String getHtmlcontent() {

		return htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent) {

		this.htmlcontent = htmlcontent;
	}

	public String getConfig() {

		return config;
	}

	public void setConfig(String config) {

		this.config = config;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getIcon() {

		return icon;
	}

	public void setIcon(String icon) {

		this.icon = icon;
	}

	public String getImage() {

		return image;
	}

	public void setImage(String image) {

		this.image = image;
	}

	public String getImages() {

		return images;
	}

	public void setImages(String images) {

		this.images = images;
	}

	public String getFiles() {

		return files;
	}

	public void setFiles(String files) {

		this.files = files;
	}

	public String getAccountid() {

		return accountid;
	}

	public void setAccountid(String accountid) {

		this.accountid = accountid;
	}

	public String getModelid() {

		return modelid;
	}

	public void setModelid(String modelid) {

		this.modelid = modelid;
	}

	public String getCsscontent() {

		return csscontent;
	}

	public void setCsscontent(String csscontent) {

		this.csscontent = csscontent;
	}

	public String getJscontent() {

		return jscontent;
	}

	public void setJscontent(String jscontent) {

		this.jscontent = jscontent;
	}

	public Integer getAuditstatus() {

		return auditstatus;
	}

	public void setAuditstatus(Integer auditstatus) {

		this.auditstatus = auditstatus;
	}

	public Integer getOpenstatus() {

		return openstatus;
	}

	public void setOpenstatus(Integer openstatus) {

		this.openstatus = openstatus;
	}

	public Integer getSharestatus() {

		return sharestatus;
	}

	public void setSharestatus(Integer sharestatus) {

		this.sharestatus = sharestatus;
	}

	public String getStaticvalue() {

		return staticvalue;
	}

	public void setStaticvalue(String staticvalue) {

		this.staticvalue = staticvalue;
	}

	public Integer getSequence() {

		return sequence;
	}

	public void setSequence(Integer sequence) {

		this.sequence = sequence;
	}

	public String getRemark() {

		return remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
	}

	public String getCreatetime() {

		return createtime;
	}

	public void setCreatetime(String createtime) {

		this.createtime = createtime;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}
}
