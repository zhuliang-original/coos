package top.coos.bean.model;

public class BeanPanelModel extends BeanModel {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public BeanPanelModel clone() {

		BeanPanelModel object = (BeanPanelModel) super.clone();
		return object;
	}
}
