package top.coos.bean.level;

import top.coos.Support;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.enums.InputType;

public class BeanLevel extends Support {

	private static final long serialVersionUID = 1L;

	@Override
	public BeanLevel clone() {

		BeanLevel object = (BeanLevel) super.clone();
		return object;
	}

	@PrimaryKey
	@Column(name = "levelid", comment = "编号")
	private String levelid;

	@Column(name = "name", comment = "名称")
	private String name;

	@Column(name = "remark", comment = "备注")
	private String remark;

	@Column(name = "programcapacity", comment = "项目集数量", inputType = InputType.NUMBER)
	private int programcapacity;

	@Column(name = "projectcapacity", comment = "项目数量", inputType = InputType.NUMBER)
	private int projectcapacity;

	@Column(name = "databasecapacity", comment = "库数量", inputType = InputType.NUMBER)
	private int databasecapacity;

	@Column(name = "tablecapacity", comment = "表数量", inputType = InputType.NUMBER)
	private int tablecapacity;

	@Column(name = "dictionarycapacity", comment = "字典数量", inputType = InputType.NUMBER)
	private int dictionarycapacity;

	@Column(name = "selectcapacity", comment = "下拉数量", inputType = InputType.NUMBER)
	private int selectcapacity;

	@Column(name = "servicecapacity", comment = "服务数量", inputType = InputType.NUMBER)
	private int servicecapacity;

	@Column(name = "pagecapacity", comment = "页面数量", inputType = InputType.NUMBER)
	private int pagecapacity;

	@Column(name = "backupcapacity", comment = "备份数量", inputType = InputType.NUMBER)
	private int backupcapacity;

	@Column(name = "isdefault", comment = "是默认")
	private boolean isdefault;

	@Column(name = "sequence", comment = "顺序号", inputType = InputType.NUMBER)
	private Integer sequence;

	public boolean isIsdefault() {

		return isdefault;
	}

	public void setIsdefault(boolean isdefault) {

		this.isdefault = isdefault;
	}

	public String getLevelid() {

		return levelid;
	}

	public void setLevelid(String levelid) {

		this.levelid = levelid;
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

	public int getProgramcapacity() {

		return programcapacity;
	}

	public void setProgramcapacity(int programcapacity) {

		this.programcapacity = programcapacity;
	}

	public int getProjectcapacity() {

		return projectcapacity;
	}

	public void setProjectcapacity(int projectcapacity) {

		this.projectcapacity = projectcapacity;
	}

	public int getDatabasecapacity() {

		return databasecapacity;
	}

	public void setDatabasecapacity(int databasecapacity) {

		this.databasecapacity = databasecapacity;
	}

	public int getTablecapacity() {

		return tablecapacity;
	}

	public void setTablecapacity(int tablecapacity) {

		this.tablecapacity = tablecapacity;
	}

	public int getDictionarycapacity() {

		return dictionarycapacity;
	}

	public void setDictionarycapacity(int dictionarycapacity) {

		this.dictionarycapacity = dictionarycapacity;
	}

	public int getSelectcapacity() {

		return selectcapacity;
	}

	public void setSelectcapacity(int selectcapacity) {

		this.selectcapacity = selectcapacity;
	}

	public int getServicecapacity() {

		return servicecapacity;
	}

	public void setServicecapacity(int servicecapacity) {

		this.servicecapacity = servicecapacity;
	}

	public int getPagecapacity() {

		return pagecapacity;
	}

	public void setPagecapacity(int pagecapacity) {

		this.pagecapacity = pagecapacity;
	}

	public int getBackupcapacity() {

		return backupcapacity;
	}

	public void setBackupcapacity(int backupcapacity) {

		this.backupcapacity = backupcapacity;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public Integer getSequence() {

		return sequence;
	}

	public void setSequence(Integer sequence) {

		this.sequence = sequence;
	}

}
