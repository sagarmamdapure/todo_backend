package com.example.todospringboot.config;

import com.example.todospringboot.utils.AwsRdsCredentialUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
public class PersistenceConfig {
  private final Environment env;

  private Logger logger = Logger.getLogger(getClass().getName());

  public PersistenceConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public DataSource myDataSource() {

    // create connection pool
    ComboPooledDataSource myDataSource = new ComboPooledDataSource();
    HashMap<String, String> awsSecret =
            AwsRdsCredentialUtil.getSecret(
                    env.getProperty("aws.secret_name"), env.getProperty("aws.region"));

    logger.info(
            String.format(
                    "aws.secret_name : %s, aws.region: %s",
                    env.getProperty("aws.secret_name"), env.getProperty("aws.region")));
    // set the jdbc driver
    try {
      myDataSource.setDriverClass(env.getProperty("jdbc.driver"));
    } catch (PropertyVetoException exc) {
      throw new RuntimeException(exc);
    }

    // set database connection props
    String jdbcUrl =
            String.format(
                    "jdbc:%s://%s:%s/%s",
                    awsSecret.get("engine"),
                    awsSecret.get("host"),
                    awsSecret.get("port"),
                    awsSecret.get("dbInstanceIdentifier"));
    myDataSource.setJdbcUrl(jdbcUrl);
    myDataSource.setUser(awsSecret.get("username"));
    myDataSource.setPassword(awsSecret.get("password"));

    // set connection pool props
    myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
    myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
    myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
    myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

    return myDataSource;
  }

  private Properties getHibernateProperties() {

    // set hibernate properties
    Properties props = new Properties();

    props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
    props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    props.setProperty("hibernate.jdbc.time_zone", env.getProperty("hibernate.jdbc.time_zone"));
    return props;
  }

  // need a helper method
  // read environment property and convert to int

  private int getIntProperty(String propName) {

    String propVal = env.getProperty(propName);

    // now convert to int
    assert propVal != null;

    return Integer.parseInt(propVal);
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {

    // create session factorys
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

    // set the properties
    sessionFactory.setDataSource(myDataSource());
    sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
    sessionFactory.setHibernateProperties(getHibernateProperties());

    return sessionFactory;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

    // setup transaction manager based on session factory
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory);

    return transactionManager;
  }
}
