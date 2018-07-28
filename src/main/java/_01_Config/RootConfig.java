package _01_Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "" })
@PropertySource(value = { "sqlite_connection.properties" }, encoding = "utf-8")
@EnableTransactionManagement
public class RootConfig {

	@Autowired
	private Environment env;
	
	@Bean
	@Profile(value = { "sqlite_env" })
	public DataSource dmDs() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlite:" + System.getProperty("user.dir") + "/" + "testDB.db");
		ds.setDriverClassName(env.getProperty("sqlite.driver.classname"));
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager txManager(DataSource ds) {
		return new DataSourceTransactionManager(ds);
	}
	
	@Bean
	public NamedParameterJdbcOperations namedParaJdbeTemplate(DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}
	
	
	public static void main(String[] args) {
		System.setProperty("spring.profiles.active" , "sqlite_env");
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		System.out.println("@@@@@@@@@@@@@@@@");
		context.close();
	}
}
















