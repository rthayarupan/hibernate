package com.thayarupan.hibernate.advance.query.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;
 
import com.thayarupan.hibernate.advance.query.entity.Item;
import com.thayarupan.hibernate.advance.query.jdbc.QueryItemWork;
import com.thayarupan.hibernate.advance.query.jdbc.QueryReturnWork;

/**
 * In Hibernate 5.2 query.setResultTransformer is deprecated.
 * session.createCriteria --> (since 5.2) for Session, use the JPA Criteria
 * createFilter --> @deprecated (since 5.3) with no real replacement
 * 
 */
public class App {
	private static final EntityManagerFactory emFactoryObj;
	private static final String PERSISTENCE_UNIT_NAME = "MySQLJPA";

	static {
		emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static EntityManager getEntityManager() {
		return emFactoryObj.createEntityManager();
	}

	public static void main(String[] args) {
		runNativeSQL();
	}
	
	
	
	
	
	public static void fallBackSQL() {
		Session session = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {			
			em = getEntityManager();
			tx = em.getTransaction();
			tx.begin();
			session = em.unwrap(Session.class);
			session.doWork(new QueryItemWork(new Long(1)));
			session.close();
			tx.commit();
			em.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 //nothing at the moment
		}
	}
	
	public static void fallBackSQLItem() {
		Session session = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {			
			em = getEntityManager();
			tx = em.getTransaction();
			tx.begin();
			session = em.unwrap(Session.class);
			Item i = session.doReturningWork(new QueryReturnWork(new Long(1)));
			System.out.println(i);
			session.close();
			tx.commit();
			em.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 //nothing at the moment
		}
	}
	
	public static void getObjectArray() {
		Session session = null;
		try {
			/*
			 * Return an object of the specified type to allow access to the provider-specific API
			 */
			session = emFactoryObj.createEntityManager().unwrap(Session.class);
			Query<Object[]> query = session.createQuery("SELECT i.id, i.name FROM Item i", Object[].class);
			List<Object[]> result = query.list();
			
			for (Object[] object : result) {
				Long id = (Long) object[0];
				String name = (String) object[1];
				System.out.println("Id" + id + ", Name" + name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	public static void runNativeSQL() {
		Session session = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {			
			em = getEntityManager();
			tx = em.getTransaction();
			session = em.unwrap(Session.class);
			tx.begin();
			 
			//JPA Query
			javax.persistence.Query  jpaQuery  = em.createNativeQuery("SELECT * FROM Item");
			List<Object[]> list = jpaQuery.getResultList();
			for (Object[] obj : list) {
				String name =(String) obj[1];
				System.out.println("Name J: "+name);
			}			
			
			jpaQuery  = em.createNativeQuery("SELECT * FROM Item", Item.class);
			List<Item> items = jpaQuery.getResultList();
			System.out.println(items);
			
			jpaQuery  = em.createNativeQuery("SELECT * FROM Item", "ItemResult"); //SQL Result Mappings
			items= jpaQuery.getResultList();
			System.out.println(items);
			
			
			
			//Hibernate Queries
			Query hQuery = session.createNativeQuery("SELECT * FROM Item");
			list = hQuery.getResultList();
			
			for (Object[] obj : list) {
				String name =(String) obj[1];
				System.out.println("Name H: "+name);
			}
			
			tx.commit();
			session.close();
			em.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 //nothing at the moment
		}
	}
	
}
