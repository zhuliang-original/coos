package top.coos.bean.audit;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

public class BeanAuditLog extends Support {

	@Override
	public BeanAuditLog clone() {

		BeanAuditLog object = (BeanAuditLog) super.clone();
		return object;
	}

	/**
     * 
     */
	private static final long serialVersionUID = 7038978348899755221L;

	@PrimaryKey
	@Column(name = "auditlogid", comment = "编号")
	private String auditlogid;

	@Column(name = "name", comment = "名称")
	private String name;

	@Column(name = "type", comment = "类型")
	private String type;

	@Column(name = "servletpath", comment = "请求路径")
	private String servletpath;

	@Column(name = "serviceid", comment = "服务")
	private String serviceid;

	@Column(name = "operateid", comment = "操作")
	private String operateid;

	@Column(name = "userid", comment = "用户")
	private String userid;

	@Column(name = "username", comment = "用户名称")
	private String username;

	@Column(name = "createtime", comment = "创建时间", inputType = InputType.DATETIME)
	private String createtime;

	@Column(name = "errcode", comment = "错误码")
	private String errcode;

	@Column(name = "errmsg", comment = "错误信息")
	private String errmsg;

	@Column(name = "remark", comment = "备注")
	private String remark;

	@Column(name = "parameter", comment = "参数", inputType = InputType.TEXTAREA)
	private String parameter;

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getErrcode() {

		return errcode;
	}

	public void setErrcode(String errcode) {

		this.errcode = errcode;
	}

	public String getErrmsg() {

		return errmsg;
	}

	public void setErrmsg(String errmsg) {

		this.errmsg = errmsg;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getAuditlogid() {

		return auditlogid;
	}

	public void setAuditlogid(String auditlogid) {

		this.auditlogid = auditlogid;
	}

	public String getServletpath() {

		return servletpath;
	}

	public void setServletpath(String servletpath) {

		this.servletpath = servletpath;
	}

	public String getServiceid() {

		return serviceid;
	}

	public void setServiceid(String serviceid) {

		this.serviceid = serviceid;
	}

	public String getOperateid() {

		return operateid;
	}

	public void setOperateid(String operateid) {

		this.operateid = operateid;
	}

	public String getUserid() {

		return userid;
	}

	public void setUserid(String userid) {

		this.userid = userid;
	}

	public String getParameter() {

		return parameter;
	}

	public void setParameter(String parameter) {

		this.parameter = parameter;
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
