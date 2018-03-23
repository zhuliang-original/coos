package top.coos.bean.plugin;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

public class BeanPlugin extends Support {

	private static final long serialVersionUID = 1L;

	@Override
	public BeanPlugin clone() {

		BeanPlugin object = (BeanPlugin) super.clone();
		if (jarcontent != null) {
			object.jarcontent = jarcontent.clone();
		}
		if (templatecontent != null) {
			object.templatecontent = templatecontent.clone();
		}
		return object;
	}

	@PrimaryKey
	@Column(name = "pluginid", comment = "编号")
	private String pluginid;

	@Column(name = "name", comment = "名称")
	private String name;

	@Column(name = "remark", comment = "备注")
	private String remark;

	@Column(name = "config", comment = "配置")
	private String config;

	@Column(name = "relyplugins", comment = "依赖")
	private String relyplugins;

	@Column(name = "createtime", comment = "创建时间")
	private String createtime;

	@Column(name = "version", comment = "版本")
	private String version;

	@Column(name = "templatepath", comment = "模板路径")
	private String templatepath;

	@Column(name = "jarpath", comment = "jar路径")
	private String jarpath;

	@Column(name = "sequence", comment = "顺序号", inputType = InputType.NUMBER)
	private Integer sequence;

	private byte[] jarcontent;

	private byte[] templatecontent;

	public String getRelyplugins() {

		return relyplugins;
	}

	public void setRelyplugins(String relyplugins) {

		this.relyplugins = relyplugins;
	}

	public String getTemplatepath() {

		return templatepath;
	}

	public void setTemplatepath(String templatepath) {

		this.templatepath = templatepath;
	}

	public String getJarpath() {

		return jarpath;
	}

	public void setJarpath(String jarpath) {

		this.jarpath = jarpath;
	}

	public byte[] getJarcontent() {

		return jarcontent;
	}

	public void setJarcontent(byte[] jarcontent) {

		this.jarcontent = jarcontent;
	}

	public byte[] getTemplatecontent() {

		return templatecontent;
	}

	public void setTemplatecontent(byte[] templatecontent) {

		this.templatecontent = templatecontent;
	}

	public String getConfig() {

		return config;
	}

	public void setConfig(String config) {

		this.config = config;
	}

	public String getPluginid() {

		return pluginid;
	}

	public void setPluginid(String pluginid) {

		this.pluginid = pluginid;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
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

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	public Integer getSequence() {

		return sequence;
	}

	public void setSequence(Integer sequence) {

		this.sequence = sequence;
	}

}
