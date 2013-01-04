package com.fhs.mensa.datastorage;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.fhs.mensa.datastorage.dataclasses.FavoriteCount;
import com.fhs.mensa.datastorage.dataclasses.Foot;
import com.fhs.mensa.datastorage.dataclasses.Tag;
import com.fhs.mensa.datastorage.dataclasses.User;
import com.fhs.mensa.datastorage.dataclasses.UserChoise;

/** produces sessions */
public final class HibernateUtil {
	/** the session factory */
	private static final SessionFactory	      SESSION_FACTORY	= buildSessionFactory();
	/** threadlocal storage */
	private static final ThreadLocal<Session>	THREAD_LOCAL	  = new ThreadLocal<>();
	
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration config = new Configuration().configure(new File("hibernate.cfg.xml")).addAnnotatedClass(User.class).addAnnotatedClass(Foot.class)
			        .addAnnotatedClass(UserChoise.class).addAnnotatedClass(Tag.class).addAnnotatedClass(FavoriteCount.class);
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
			return config.buildSessionFactory(serviceRegistry);
			
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	/*
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
	*/
	
	public static Session getSession() {
		Session session = (Session) THREAD_LOCAL.get();
		if (session == null || !session.isConnected()) {
			session = SESSION_FACTORY.openSession();
			THREAD_LOCAL.set(session);
		}
		return session;
	}
	
	public static void shutDown() {
				
		SESSION_FACTORY.close();
	}
	
	private HibernateUtil() {
		
	}
	
}
