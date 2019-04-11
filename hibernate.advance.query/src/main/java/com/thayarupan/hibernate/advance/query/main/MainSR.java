package com.thayarupan.hibernate.advance.query.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.thayarupan.hibernate.advance.query.entity.Category;
import com.thayarupan.hibernate.advance.query.jdbc.QueryItemWork;

public class MainSR {

	private static final EntityManagerFactory emFactoryObj;
	private static final String PERSISTENCE_UNIT_NAME = "MySQLJPA";

	static {
		emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static EntityManager getEntityManager() {
		return emFactoryObj.createEntityManager();
	}

	public static void main(String[] args) {
		selfRefQuery();
	}

	public static void selfRefQuery() {
		Session session = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = getEntityManager();
			tx = em.getTransaction();
			tx.begin();
			session = em.unwrap(Session.class);
/*
			Category catFruit = new Category();
			catFruit.setName("Veg");
			//em.persist(catFruit);

			Category catApple = new Category();
			catApple.setName("Carrot");
			catApple.setParent(catFruit);
			em.persist(catApple);

			Category catOrange = new Category();
			catOrange.setName("Greens");
			catOrange.setParent(catFruit);
			em.persist(catOrange);
*/
			//Getting root parents
			TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c where c.parent IS NULL", Category.class);
			
			//Getting direct chirldren of root
			TypedQuery<Category> query2 = em.createQuery("SELECT c FROM Category c, Category r WHERE r.parent IS NULL AND c.parent=r", Category.class);
			
			List<Category> list = query.getResultList();
		
			System.out.println(list);
			
			session.close();
			tx.commit();
			em.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// nothing at the moment
		}
	}

}
