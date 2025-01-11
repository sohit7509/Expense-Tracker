package com.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.entity.User;

public class UserDao {
	private SessionFactory Factory = null;
	private Session session = null;
	private Transaction tx = null;

	public UserDao(SessionFactory factory) {
		super();
		Factory = factory;
	}

	public boolean saveUser(User user) {

		boolean f = false;

		try {
			session = Factory.openSession();
			tx = session.beginTransaction();

			session.save(user);
			tx.commit();
			f = true;

		} catch (Exception e) {
			if (tx != null) {
				f = false;
				e.printStackTrace();
			}
		}
		return f;
	}

	public User login(String email, String password) {
		User u = null;

		session = Factory.openSession();

		Query q = session.createQuery("from User where email=:em and password=:ps");
		q.setParameter("em", email);
		q.setParameter("ps", password);

		u = (User) q.uniqueResult();

		return u;
	}

}
