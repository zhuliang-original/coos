package top.coos.bean.permission;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

public class PermissionOperate extends Support {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public PermissionOperate clone() {

		return (PermissionOperate) super.clone();
	}

	@PrimaryKey
	@Column(name = "operateid", comment = "编号")
	private String operateid;

	@Column(name = "name", comment = "名称")
	private String name;

	@Column(name = "type", comment = "类型", inputType = InputType.SELECT)
	private String type;

	@Column(name = "parentid", comment = "父操作")
	private String parentid;

	@Column(name = "servletpath", comment = "请求路径")
	private String servletpath;

	@Column(name = "projectid", comment = "项目")
	private String projectid;

	@Column(name = "pageid", comment = "页面")
	private String pageid;

	@Column(name = "serviceid", comment = "服务")
	private String serviceid;

	@Column(name = "useconfigprocess", comment = "使用配置处理", inputType = InputType.SWITCH)
	private boolean useconfigprocess;

	@Column(name = "beforeexecuteclass", comment = "之前执行类名")
	private String beforeexecuteclass;

	@Column(name = "afterexecuteclass", comment = "之后执行类名")
	private String afterexecuteclass;

	@Column(name = "shouldlogin", comment = "需要登录", inputType = InputType.SWITCH)
	private boolean shouldlogin;

	@Column(name = "shouldauthorize", comment = "需要授权", inputType = InputType.SWITCH)
	private boolean shouldauthorize;

	@Column(name = "sequence", comment = "顺序号", inputType = InputType.NUMBER)
	private Integer sequence;

	@Column(name = "remark", comment = "备注", maxlength = 500)
	private String remark;

	@Column(name = "createtime", comment = "创建时间", inputType = InputType.DATETIME)
	private String createtime;

	public String getProjectid() {

		return projectid;
	}

	public void setProjectid(String projectid) {

		this.projectid = projectid;
	}

	public String getPageid() {

		return pageid;
	}

	public void setPageid(String pageid) {

		this.pageid = pageid;
	}

	public String getServiceid() {

		return serviceid;
	}

	public void setServiceid(String serviceid) {

		this.serviceid = serviceid;
	}

	public boolean isUseconfigprocess() {

		return useconfigprocess;
	}

	public void setUseconfigprocess(boolean useconfigprocess) {

		this.useconfigprocess = useconfigprocess;
	}

	public String getBeforeexecuteclass() {

		return beforeexecuteclass;
	}

	public void setBeforeexecuteclass(String beforeexecuteclass) {

		this.beforeexecuteclass = beforeexecuteclass;
	}

	public String getAfterexecuteclass() {

		return afterexecuteclass;
	}

	public void setAfterexecuteclass(String afterexecuteclass) {

		this.afterexecuteclass = afterexecuteclass;
	}

	public String getOperateid() {

		return operateid;
	}

	public void setOperateid(String operateid) {

		this.operateid = operateid;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getParentid() {

		return parentid;
	}

	public void setParentid(String parentid) {

		this.parentid = parentid;
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

	public boolean isShouldauthorize() {

		return shouldauthorize;
	}

	public void setShouldauthorize(boolean shouldauthorize) {

		this.shouldauthorize = shouldauthorize;
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

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

}
