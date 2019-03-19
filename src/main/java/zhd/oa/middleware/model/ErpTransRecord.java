package zhd.oa.middleware.model;

public class ErpTransRecord {
	private Integer id;
	private String flow_name;
	private String insert_date;
	private String trans_content;

	public String getTrans_content() {
		return trans_content;
	}

	public void setTrans_content(String trans_content) {
		this.trans_content = trans_content;
	}

	public long getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlow_name() {
		return flow_name;
	}

	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}

	public String getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
}
