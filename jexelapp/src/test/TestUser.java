package test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.User;

public class TestUser {

	
	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence
				.createEntityManagerFactory("elink_JPA");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

		 User user = new User();
		 user.setAid(1);
		 user.setFname("Jack");
		 user.setLname("Morgan");
		 user.setAddress("123 pensilvania ave,NYC");
		 try{
		 user.setBirthDate(sdf.parse("1978-12-12"));
		 }
		 catch(ParseException ex)
		 {
		
		 System.out.println("can't parse date");
		 }
		
		 entitymanager.persist(user);
		 
		 
		 User user2 = new User();
		 user2.setAid(2);
		 user2.setFname("Jimmy");
		 user2.setLname("Morgan");
		 user2.setAddress("123 pensilvania ave,NYC");
		 try{
		 user2.setBirthDate(sdf.parse("1978-12-12"));
		 }
		 catch(ParseException ex)
		 {
		
		 System.out.println("can't parse date");
		 }
		
		 entitymanager.persist(user2);
		 
			Class ac = User.class;
			Annotation [] ans = ac.getDeclaredAnnotations();
			int a = ans.length;
			System.out.println("annotations count -> - "+a);
			Field[] fs = ac.getDeclaredFields();
			a = fs.length;
			System.out.println("field count -> - "+a);
			System.out.println("--------------Field Types--------------");
			for(int j=0;j<a;j++)
			{
			
				Object type = fs[j].getType();
				System.out.println(type.toString());
				
				String fieldName = fs[j].getName();
				System.out.println(fieldName);
				
			}
			User user3 = null;
			Constructor[] conssts = ac.getConstructors();
			Constructor constr1 = conssts[0];
			try {
			 user3 = (User) constr1.newInstance(Integer.valueOf(a),"Jack","Amsterdam","25 Green Light ave",sdf.parse("1978-12-12"));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 entitymanager.persist(user3);
		//find a user
//		User testUser = entitymanager.find(User.class, 1);
//		System.out.format("found user %s on following address %s -> ",
//				testUser.getFname() + " " + testUser.getLname() + " ",
//				testUser.getAddress());
//		// now update user
//		testUser.setAddress(" 234Macdonalds ave ,New Jersey");
//
//		// check if user updated
//		testUser = entitymanager.find(User.class, 1);
//		System.out.format("found user %s on following address %s -> ",
//				testUser.getFname() + " " + testUser.getLname() + " ",
//				testUser.getAddress());
//
//		// select all users
//		Query q = entitymanager.createQuery("select u from User u");
//		@SuppressWarnings("unchecked")
//		List<User> users = new ArrayList<User>(q.getResultList());
//		Iterator<User> it = users.iterator();
//		while (it.hasNext()) {
//
//			testUser = (User) it.next();
//			System.out.println(testUser.getFname() + " " + testUser.getLname()
//					+ " " + "born on " + testUser.getBirthDate()
//					+ "lives on following Address" + testUser.getAddress());
//
//		}
		// remove user
		// entitymanager.remove(testUser);
		 entitymanager.getTransaction().commit();
		
		entitymanager.close();
		emfactory.close();
	}

}
