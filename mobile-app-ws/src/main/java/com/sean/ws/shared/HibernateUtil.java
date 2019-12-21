package com.sean.ws.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sean.ws.io.entity.AddressEntity;
import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.entity.VehicleEntity;

@Component
public class HibernateUtil {
	
	private String jdbcUrl;
	
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	
	public HibernateUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public HibernateUtil(@Value("spring.datasource.url") String jdbcUrl) {
		super();
		this.jdbcUrl = jdbcUrl;
	}



	private static final String driver = "com.mysql.jdbc.Driver";
	
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		//String ss = Utils.fetchProperties("classpath:application.properties").getProperty("spring.datasource.url");
		//System.out.println(ss);
		
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, driver);
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/photo_app?serverTimezone=UTC");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "update");
				configuration.setProperties(settings);
				configuration
				.addAnnotatedClass(UserEntity.class)
				.addAnnotatedClass(AddressEntity.class)
				.addAnnotatedClass(VehicleEntity.class);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}

/*
 * public class HibernateUtil {
 * 
 * private static StandardServiceRegistry standardServiceRegistry; private
 * static SessionFactory sessionFactory;
 * 
 * static { if (sessionFactory == null) { // Creating
 * StandardServiceRegistryBuilder StandardServiceRegistryBuilder registryBuilder
 * = new StandardServiceRegistryBuilder();
 * 
 * // Hibernate settings which is equivalent to hibernate.cfg.xml's properties
 * Map<String, String> dbSettings = new HashMap<>();
 * dbSettings.put(Environment.URL,
 * "jdbc:mysql://localhost:3306/photo_app?serverTimezone=UTC");
 * dbSettings.put(Environment.USER, "root"); dbSettings.put(Environment.PASS,
 * ""); dbSettings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
 * dbSettings.put(Environment.DIALECT,
 * "org.hibernate.dialect.MySQL5InnoDBDialect");
 * 
 * // Apply database settings registryBuilder.applySettings(dbSettings); //
 * Creating registry standardServiceRegistry = registryBuilder.build(); //
 * Creating MetadataSources MetadataSources sources = new
 * MetadataSources(standardServiceRegistry); // Creating Metadata Metadata
 * metadata = sources.getMetadataBuilder().build(); // Creating SessionFactory
 * 
 * sessionFactory = metadata.getSessionFactoryBuilder().build(); } }
 * 
 * // Utility method to return SessionFactory public static SessionFactory
 * getSessionFactory() { return sessionFactory; }
 * 
 * }
 */
