package top.coos.bean.database;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.enums.InputType;

public class Database extends Support {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Column(name = "catalog", comment = "目录")
	private String catalog;

	@Column(name = "driver", comment = "驱动")
	private String driver;

	@Column(name = "url", comment = "链接")
	private String url;

	@Column(name = "username", comment = "用户名")
	private String username;

	@Column(name = "password", comment = "密码")
	private String password;

	@Column(name = "initializeclass", comment = "初始化类")
	private String initializeclass;

	@Column(name = "validationquery", comment = "验证查询")
	private String validationquery;

	@Column(name = "initialsize", comment = "初始连接数", inputType = InputType.NUMBER)
	private Integer initialsize;

	@Column(name = "maxtotal", comment = "最大连接数", inputType = InputType.NUMBER)
	private Integer maxtotal;

	@Column(name = "minidle", comment = "最小空闲连接", inputType = InputType.NUMBER)
	private Integer minidle;

	@Column(name = "maxidle", comment = "最大空闲连接", inputType = InputType.NUMBER)
	private Integer maxidle;

	@Column(name = "maxwaitmillis", comment = "最大等待时间", inputType = InputType.NUMBER)
	private Integer maxwaitmillis;

	@Column(name = "showsql", comment = "显示SQL", inputType = InputType.SWITCH)
	private boolean showsql;

	@Column(name = "maxactive", comment = "最大连接数", inputType = InputType.NUMBER)
	private Integer maxactive;

	@Column(name = "maxwait", comment = "最大等待时间", inputType = InputType.NUMBER)
	private Integer maxwait;

	public String getInitializeclass() {

		return initializeclass;
	}

	public void setInitializeclass(String initializeclass) {

		this.initializeclass = initializeclass;
	}

	public String getCatalog() {

		return catalog;
	}

	public void setCatalog(String catalog) {

		this.catalog = catalog;
	}

	public String getDriver() {

		return driver;
	}

	public void setDriver(String driver) {

		this.driver = driver;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getValidationquery() {

		return validationquery;
	}

	public void setValidationquery(String validationquery) {

		this.validationquery = validationquery;
	}

	public Integer getInitialsize() {

		return initialsize;
	}

	public void setInitialsize(Integer initialsize) {

		this.initialsize = initialsize;
	}

	public Integer getMaxtotal() {

		return maxtotal;
	}

	public void setMaxtotal(Integer maxtotal) {

		this.maxtotal = maxtotal;
	}

	public Integer getMinidle() {

		return minidle;
	}

	public void setMinidle(Integer minidle) {

		this.minidle = minidle;
	}

	public Integer getMaxidle() {

		return maxidle;
	}

	public void setMaxidle(Integer maxidle) {

		this.maxidle = maxidle;
	}

	public Integer getMaxwaitmillis() {

		return maxwaitmillis;
	}

	public void setMaxwaitmillis(Integer maxwaitmillis) {

		this.maxwaitmillis = maxwaitmillis;
	}

	public boolean isShowsql() {

		return showsql;
	}

	public void setShowsql(boolean showsql) {

		this.showsql = showsql;
	}

	public Integer getMaxactive() {

		return maxactive;
	}

	public void setMaxactive(Integer maxactive) {

		this.maxactive = maxactive;
	}

	public Integer getMaxwait() {

		return maxwait;
	}

	public void setMaxwait(Integer maxwait) {

		this.maxwait = maxwait;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

}
