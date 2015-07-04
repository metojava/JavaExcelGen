package excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import entities.User;

public class poia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Workbook wk = new HSSFWorkbook();
		Sheet sh = wk.createSheet("newsheet");

		CellStyle cs = wk.createCellStyle();
		cs.setFillForegroundColor(IndexedColors.AQUA.index);
		cs.setFillBackgroundColor(IndexedColors.CORAL.index);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		Calendar gc = GregorianCalendar.getInstance();
		int year = gc.YEAR;
		int month = gc.MONTH;
		int day = gc.DAY_OF_WEEK;

		String css = "" + year + "-" + month + "-" + day;

		// second way to build date
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		//
		// Row row = sh.createRow(0);
		// Cell cell = row.createCell(0);
		// cell.setCellStyle(cs);
		// cell.setCellValue("date -> "
		// + GregorianCalendar.getInstance().getTime().toString());
		//
		// row.createCell(1).setCellValue(1.2d);
		// row.createCell(2).setCellValue(css);
		// row.createCell(3).setCellValue(sdf.format(new Date()));
		//
		//
		// row = sh.createRow(2);
		// cell = row.createCell(0);
		// cell.setCellStyle(cs);
		// cell.setCellValue(new Date().toString());
		// row.createCell(1).setCellValue(5.2d);

		EntityManagerFactory emfactory = Persistence
				.createEntityManagerFactory("elink_JPA");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		createUsers(entitymanager);
		

		Query q = entitymanager.createQuery("select u from User u");

		User testUser = new User();
		@SuppressWarnings("unchecked")
		List<User> users = new ArrayList<User>(q.getResultList());

		Row row = sh.createRow(0);
		
		Class cls = User.class;
		Field[] fs = cls.getDeclaredFields();
		
		for (int i = 0; i < fs.length; i++) {
			String fieldName = fs[i].getName();
			row.createCell(i).setCellValue(fieldName);
		}
		
		for (int i = 0; i < users.size(); i++) {
			testUser = (User) users.get(i);
			 row = sh.createRow(i+1);

			row.createCell(0).setCellValue(testUser.getAid());
			row.createCell(1).setCellValue(testUser.getFname());
			row.createCell(2).setCellValue(testUser.getLname());
			row.createCell(3).setCellValue(testUser.getAddress());
			row.createCell(4).setCellValue(sdf.format(testUser.getBirthDate()));

		}
		{

			try {
				FileOutputStream fio = new FileOutputStream(new File(
						"C:\\Users\\User\\Desktop\\outputpoi.xls"));

				try {
					wk.write(fio);
					fio.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		entitymanager.getTransaction().commit();

		entitymanager.close();
		emfactory.close();

	}
	
	public static void createUsers(EntityManager entityManager)
	{
		
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
		
		 entityManager.persist(user);
		 
		 
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
		
		 entityManager.persist(user2);
		 
			Class ac = User.class;
			int a = 0;
//			Annotation [] ans = ac.getDeclaredAnnotations();
//			int a = ans.length;
//			System.out.println("annotations count -> - "+a);
			Field[] fs = ac.getDeclaredFields();
			a = fs.length;
			System.out.println("field count -> - "+a);
			System.out.println("--------------Field Types--------------");
			for(int j=0;j<a;j++)
			{
			
				Object type = fs[j].getType();
				System.out.println(type.toString());
				
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
			
			 entityManager.persist(user3);
		
	}
}
