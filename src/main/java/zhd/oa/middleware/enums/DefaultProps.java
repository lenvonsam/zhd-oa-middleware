package zhd.oa.middleware.enums;

public enum DefaultProps {
	BOOTFILE("application.properties"), PROFILE("zhd.oa.profile"), PORT("zhd.oa.port"), CONTROLLER(
			"zhd.oa.controller.path"), MAPPER("zhd.oa.mapper.path"), MODEL("zhd.oa.model.path"), DBDRIVER(
					"zhd.db.driverName"), DBURL("zhd.db.url"), DBUSER("zhd.db.username"), DBPWD("zhd.db.password");
	private String name;

	private DefaultProps(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
