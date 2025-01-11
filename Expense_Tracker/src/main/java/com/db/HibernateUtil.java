package com.db;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.entity.Expense;
import com.entity.User;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				Properties properties = new Properties();

				properties.put(Environment.DRIVER, "org.postgresql.Driver");
				properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/expenseTracker");
				properties.put(Environment.USER, "postgres");
				properties.put(Environment.PASS, "123");
				properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
				properties.put(Environment.SHOW_SQL, "true");
				properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				properties.put(Environment.HBM2DDL_AUTO, "update");

				configuration.setProperties(properties);
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(Expense.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			} catch (Exception e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError("Failed to create SessionFactory Object");
			}
		}
		return sessionFactory;

	}
}
