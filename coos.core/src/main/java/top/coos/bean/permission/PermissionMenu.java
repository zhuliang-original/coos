package top.coos.bean.permission;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

public class PermissionMenu extends Support {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public PermissionMenu clone() {

		return (PermissionMenu) super.clone();
	}

	@PrimaryKey
	@Column(name = "menuid", comment = "编号")
	private String menuid;

	@Column(name = "projectid", comment = "项目")
	private String projectid;

	@Column(name = "name", comment = "名称")
	private String name;

	@Column(name = "servletpath", comment = "请求路径")
	private String servletpath;

	@Column(name = "shouldlogin", comment = "需要登录", inputType = InputType.SWITCH)
	private boolean shouldlogin;

	@Column(name = "shouldauthorize", comment = "需要授权", inputType = InputType.SWITCH)
	private boolean shouldauthorize;

	@Column(name = "parentid", comment = "父菜单", forParentid = true)
	private String parentid;

	@Column(name = "fonticon", comment = "字体图标")
	private String fonticon;

	@Column(name = "sequence", comment = "顺序号", inputType = InputType.NUMBER)
	private Integer sequence;

	@Column(name = "remark", comment = "备注", maxlength = 500)
	private String remark;

	@Column(name = "notonline", comment = "未上线", inputType = InputType.SWITCH)
	private boolean notonline;

	@Column(name = "createtime", comment = "创建时间", inputType = InputType.DATETIME)
	private String createtime;

	public boolean isNotonline() {

		return notonline;
	}

	public void setNotonline(boolean notonline) {

		this.notonline = notonline;
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

	public String getCreatetime() {

		return createtime;
	}

	public void setCreatetime(String createtime) {

		this.createtime = createtime;
	}

	public boolean isShouldauthorize() {

		return shouldauthorize;
	}

	public void setShouldauthorize(boolean shouldauthorize) {

		this.shouldauthorize = shouldauthorize;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getMenuid() {

		return menuid;
	}

	public void setMenuid(String menuid) {

		this.menuid = menuid;
	}

	public String getServletpath() {

		return servletpath;
	}

	public void setServletpath(String servletpath) {

		this.servletpath = servletpath;
	}

	public boolean isShouldlogin() {

		return shouldlogin;
	}

	public void setShouldlogin(boolean shouldlogin) {

		this.shouldlogin = shouldlogin;
	}

	public String getParentid() {

		return parentid;
	}

	public void setParentid(String parentid) {

		this.parentid = parentid;
	}

	public String getFonticon() {

		return fonticon;
	}

	public void setFonticon(String fonticon) {

		this.fonticon = fonticon;
	}

	public Integer getSequence() {

		return sequence;
	}

	public void setSequence(Integer sequence) {

		this.sequence = sequence;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

}
