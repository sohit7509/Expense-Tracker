package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.entity.Expense;
import com.entity.User;

public class ExpenseDao {
	private SessionFactory Factory = null;
	private Session session = null;
	private Transaction tx = null;

	public ExpenseDao(SessionFactory factory) {
		super();
		Factory = factory;
	}

	public boolean saveExpense(Expense ex) {
		boolean f = false;

		try {

			session = Factory.openSession();
			tx = session.beginTransaction();
			session.save(ex);

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

	public List<Expense> getAllExpenseByUser(User user) {
		List<Expense> list = new ArrayList<Expense>();

		try {
			session = Factory.openSession();
			Query q = session.createQuery("from Expense where user=:us");
			q.setParameter("us", user);
			list = q.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Expense getExpenseById(int id) {
		Expense ex = null;

		try {
			session = Factory.openSession();
			Query q = session.createQuery("from Expense where id=:id");

			q.setParameter("id", id);
			ex = (Expense) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ex;

	}

	public boolean updateExpense(Expense ex) {
		boolean f = false;

		try {

			session = Factory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(ex);

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

	public boolean deleteExpense(int id) {
		boolean f = false;

		try {
			session = Factory.openSession();
			tx = session.beginTransaction();

			Expense ex = session.get(Expense.class, id);

			session.delete(ex);
			tx.commit();
			f = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}

}
