package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Field;

public class FieldDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	
	private static Database db;
	private FieldDAO fieldTester;
	
	@Before
	public void setUp() throws Exception {
		db = new Database();
		File dbsetupFile = new File("create_database.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(dbsetupFile);
			String sql = "";
			while(scanner.hasNextLine()){
				sql += scanner.nextLine() + "\n";
			}
			db.startTransaction();
			db.getConnection().createStatement().executeUpdate(sql);
			db.endTransaction(true);
		} catch (FileNotFoundException| SQLException e) {
			e.printStackTrace();
			System.out.println("no such file to set up test");
		}
		
		scanner.close();

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		fieldTester = db.getFieldDAO();
	}

	@After
	public void tearDown() throws Exception {
		db.endTransaction(false);
		db = null;
		fieldTester = null;
	}
	
	@Test
	public void getTestField(){
		Field test = fieldTester.getField(1);
		assertNull(test);
	}
	@Test
	public void testGetFieldsbyProjectID(){
		Field field1 = new Field("title", 1, 50, 100, "help!", "knowndata", 2, -1);
		Field field2 = new Field("titleeee", 2, 60, 120, "helpagain", "known", 2, -1);
		Field field3 = new Field("title!!!", 3, 10, 20, "html", "known!", 5, -1);
		Field field4 = new Field("title.", 4, 20, 40, "halp", "knoww", 3, -1);
		
		fieldTester.add(field1);
		fieldTester.add(field2);
		fieldTester.add(field3);
		fieldTester.add(field4);
		
		ArrayList<Field> fields = fieldTester.getFieldsByProjectID(2);
		
		assertEquals(2, fields.size());
	}
	@Test
	public void testGetAll(){
		ArrayList<Field> allFields = fieldTester.getAllFields();
		assertEquals(0, allFields.size());
	}
	@Test
	public void testAdd(){
		Field field1 = new Field("title", 1, 50, 100, "help!", "knowndata", 2, -1);
		Field field2 = new Field("titleeee", 2, 60, 120, "helpagain", "known", 2, -1);
		
		fieldTester.add(field1);
		fieldTester.add(field2);
		
		ArrayList<Field> allFields = fieldTester.getAllFields();
		assertEquals(2, allFields.size());
		
		Field result1 = allFields.get(0);
		Field result2 = allFields.get(1);
		
		assertTrue(field1.equals(result1) && field2.equals(result2));
	}
	
	@Test
	public void testUpdate(){
		Field field = new Field("title!!!", 1, 10, 20, "html", "known", 5, 1);
		
		fieldTester.add(field);
		field.setHelphtml("html2!");
		field.setKnowndata("different known");
		
		fieldTester.update(field);
		
		Field updated = fieldTester.getField(1);
		
		field.toString();
		
		updated.toString();
		
		assertTrue(field.equals(updated));
	}

}
