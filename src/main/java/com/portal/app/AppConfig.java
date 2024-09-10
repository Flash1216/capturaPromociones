package com.portal.app;

import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.portal.app.util.AppInfo;

@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
@EnableAutoConfiguration(exclude={	DataSourceAutoConfiguration.class,
									DataSourceTransactionManagerAutoConfiguration.class,
									HibernateJpaAutoConfiguration.class})
@PropertySource("classpath:/promociones/${spring.profiles.active}.properties")
public class AppConfig implements WebMvcConfigurer, SchedulingConfigurer
{
	private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
	
	@Value("${spring.profiles.active}")		private String profile;
	@Value("${logging.file}")				private String logfile;
	@Value("${spring.application.name}")	private String appId;
	
	@Value("${proxy.enabled}")				private boolean proxyEnabled;
	@Value("${proxy.server}")				private String proxyServer;
	@Value("${resources.url}")				private String resourcesUrl;
	
	@Value("${app.name}")					private String appName;
	@Value("${app.desc}")					private String appDesc;
	@Value("${app.version}")				private String appVersion;
	@Value("${app.release}")				private String appRelease;
	@Value("${app.developer}")				private String appDeveloper;
	
	@Value("${jdbc.packages}")				private String jdbcPackagesToScan;
	@Value("${jdbc.show.sql}")				private String jdbcShowSql;
	@Value("${jdbc.name}")					private String jdbcName;
	@Value("${jdbc.url}")					private String jdbcUrl;
	@Value("${jdbc.user}")					private String jdbcUser;
	@Value("${jdbc.password}")				private String jdbcPassword;
	
	/*******************************************************************************
	 * Configuración del log *
	 *******************************************************************************/
	@Bean(name = "appInfo")
	public int appInfo() 
	{
		log.info("*** Configuración ******");
		log.info("-- Id:                  " + appId);
		log.info("-- Aplicativo:          " + appName);
		log.info("-- Descripción:         " + appDesc);
		log.info("-- Versión:             " + appVersion);
		log.info("-- Desarrollador:       " + appDeveloper);
		log.info("-- Profile:             " + profile);
		log.info("-- Ruta de log:         " + logfile);
		log.info("-- Base de datos:       " + jdbcName);
		log.info("-- Url:                 " + jdbcUrl);
		log.info("-- Proxy:               " + proxyServer);
		System.out.println(" *** Configuración de log AppId:" + appId+" App name:"+appName + " : " + logfile);
		return 0;
	}
	
	/************************************************************************************
	 * CONFIGURACIÓN DE LA BASE DE DATOS *
	 ************************************************************************************/
	@Bean(name = "dataSource")
	public DataSource getDataSource() 
	{
		String password = new String(Base64.decodeBase64(Base64.decodeBase64(Base64.decodeBase64(jdbcPassword.getBytes()))));
	
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUser);
		dataSource.setPassword(password);

		dataSource.setMaxTotal(100);
		dataSource.setMaxWaitMillis(2000);
		dataSource.setValidationQuery("select 1 from dual");
		dataSource.setTimeBetweenEvictionRunsMillis(500);
		dataSource.setLogExpiredConnections(true);
		dataSource.setMaxOpenPreparedStatements(150);
		dataSource.setRemoveAbandonedTimeout(30);
		dataSource.setLogAbandoned(true);
		return dataSource;
	}
	
	@Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception 
	{
        Properties properties = new Properties();
        properties.put("hibernate.dialect", 			"org.hibernate.dialect.Oracle10gDialect");
        properties.put("hibernate.hbm2dll.auto",		"validate");
        properties.put("hibernate.show_sql", 			jdbcShowSql);
        properties.put("current_session_context_class",	"org.springframework.orm.hibernate4.SpringSessionContext");
        
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
 
        factoryBean.setPackagesToScan(new String[] { jdbcPackagesToScan});
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        SessionFactory sf = factoryBean.getObject();
        return sf;
    }
	
	@Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) 
	{
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }
	
	 @Override
	 public void addCorsMappings(CorsRegistry registry) 
	 {
		if(!proxyEnabled){ registry.addMapping("/**").allowedOrigins("*"); }	
	 }
	 
	@Bean
	public AppInfo appInfoBean()
	{
		AppInfo appInfo = new AppInfo();
		appInfo.setId(appId);
		appInfo.setName(appName);
		appInfo.setDesc(appDesc);
		appInfo.setVersion(appVersion);
		appInfo.setRelease(appRelease);
		appInfo.setProfile(profile);
		appInfo.setDeveloper(appDeveloper);
		appInfo.setResources(proxyEnabled?proxyServer:resourcesUrl);
		return appInfo;
	}
	
	
	@Bean 
	public TareasProgramadas tareasProgramadasBean()
	{
		return new TareasProgramadas();
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) 
	{
		taskRegistrar.setScheduler(poolScheduler());
	}
	@Bean(name="poolScheduler")
    public TaskScheduler poolScheduler() 
	{
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(15);
        scheduler.setWaitForTasksToCompleteOnShutdown(false);
        return scheduler;
    }
	
	public static void main(String[] args) 
	{
		SpringApplication.run(AppConfig.class, args);
	}
}
