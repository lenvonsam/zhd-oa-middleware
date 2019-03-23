package zhd.oa.middleware.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhd.oa.middleware.enums.DefaultProps;
import zhd.oa.middleware.mapper.ErpTransRecordMapper;
import zhd.oa.middleware.model.ErpTransRecord;
import zhd.oa.middleware.utils.PropertyUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class DatabaseConfig {
	private Properties dataProps = null;
	private static DatabaseConfig instance = null;
	public SqlSessionFactory sessionFactory = null;
	private Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
	private DatabaseConfig() {
	}

	public static DatabaseConfig shareInstance() {
		if (instance == null)
			instance = new DatabaseConfig();
		return instance;
	}

	public void configData() throws Exception {
		DataSource dataSource = getLocalDataSource();
		TransactionFactory trxFactory = new JdbcTransactionFactory();
		Environment env = new Environment(GlobalVariable.CURRENTPROFILE, trxFactory, dataSource);
		Configuration config = new Configuration(env);
		TypeAliasRegistry aliases = config.getTypeAliasRegistry();
		// 遍历返回值类型
		aliases.registerAliases(dataProps.getProperty(DefaultProps.MODEL.getName()));
//		System.out.println("model path:>>." + dataProps.getProperty(DefaultProps.MODEL.getName()));
		// 遍历mapper类型
		config.addMappers(dataProps.getProperty(DefaultProps.MAPPER.getName()));
//		System.out.println("mapper path:>>" + dataProps.getProperty(DefaultProps.MAPPER.getName()));
		if (this.sessionFactory == null)
			sessionFactory = new SqlSessionFactoryBuilder().build(config);
		log.info("database init config finish");
	}

	private DataSource getLocalDataSource() throws Exception {
		BasicDataSource dataSource = new BasicDataSource();
		dataProps = PropertyUtil.shareInstance().initProperties(DefaultProps.BOOTFILE.getName());
		dataSource.setDriverClassName(dataProps.getProperty(DefaultProps.DBDRIVER.getName()));
		dataSource.setUrl(dataProps.getProperty(DefaultProps.DBURL.getName()));
		dataSource.setUsername(dataProps.getProperty(DefaultProps.DBUSER.getName()));
		dataSource.setPassword(dataProps.getProperty(DefaultProps.DBPWD.getName()));
		return dataSource;
	}
	
	public void mybatisTest() {
		try {
			configData();
			SqlSession  s = this.sessionFactory.openSession();
			ErpTransRecordMapper mapper = s.getMapper(ErpTransRecordMapper.class);
			ErpTransRecord r = mapper.findOne(25);
            log.info("r.name:>>" + r.getFlow_name());
			s.commit();
			s.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadTest() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.80.180:1521:ecology", "ecology",
					"ecology");
            log.info("连接成功");
			java.sql.PreparedStatement psts = con.prepareStatement(
					"select id, flow_name, insert_date, trans_content from uf_erp_sale_record where id = ?");
			psts.setInt(1, 25);
			ResultSet rs = psts.executeQuery();
			while (rs.next()) {
				log.info("sys name:>>" + rs.getString("flow_name"));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
