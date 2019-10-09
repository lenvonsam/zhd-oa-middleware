package zhd.oa.middleware.model;
/**
 * 固定资产
 * @author conqueror
 *2019-08-23
 */
public class PropertyBasic {
	/**
	 * 固资id
	 */
	private int property_id;
	/**
	 * 固资编号
	 */
	private String property_no;
	/**
	 * 固资名称
	 */
	private String goods_name;
	/**
	 * 固资型号规格
	 */
	private String goods_model;
	/**
	 * 固资计量单位
	 */
	private String goods_unit;
	/**
	 * 固资数量
	 */
	private String property_count;
	/**
	 * 固资存放地点
	 */
	private String store_place;
	/**
	 * 固资部门
	 */
	private String property_dept;
	
	
	public int getProperty_id() {
		return property_id;
	}
	public void setProperty_id(int property_id) {
		this.property_id = property_id;
	}
	public String getProperty_no() {
		return property_no;
	}
	public void setProperty_no(String property_no) {
		this.property_no = property_no;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_model() {
		return goods_model;
	}
	public void setGoods_model(String goods_model) {
		this.goods_model = goods_model;
	}
	public String getGoods_unit() {
		return goods_unit;
	}
	public void setGoods_unit(String goods_unit) {
		this.goods_unit = goods_unit;
	}
	public String getProperty_count() {
		return property_count;
	}
	public void setProperty_count(String property_count) {
		this.property_count = property_count;
	}
	public String getStore_place() {
		return store_place;
	}
	public void setStore_place(String store_place) {
		this.store_place = store_place;
	}
	public String getProperty_dept() {
		return property_dept;
	}
	public void setProperty_dept(String property_dept) {
		this.property_dept = property_dept;
	}
	@Override
	public String toString() {
		return "PropertyBasic [property_id=" + property_id + ", property_no=" + property_no + ", goods_name="
				+ goods_name + ", goods_model=" + goods_model + ", goods_unit=" + goods_unit + ", property_count="
				+ property_count + ", store_place=" + store_place + ", property_dept=" + property_dept + "]";
	}


}
