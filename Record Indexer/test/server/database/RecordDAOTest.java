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

import shared.model.Record;

public class RecordDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;	
	}
	
	private static Database db;
	private RecordDAO RecordTester;

	@Before
	public void setUp() throws Exception {
		db = new Database();
		File dbsetupFile = new File("ClearTable.txt");
		try {
			Scanner scanner = new Scanner(dbsetupFile);
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

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		RecordTester = db.getRecordDAO();
	}

	@After
	public void tearDown() throws Exception {
		db.endTransaction(false);
		db = null;
		RecordTester = null;
	}
	
	//@Test
	public void testGetAll(){
		ArrayList<Record> allRecords = RecordTester.getAllRecords();
		assertEquals(0, allRecords.size());
	}
	
	//@Test
	public void testGetRecordsByImageID(){
		Record r1 = new Record(1, 2, 3);
		Record r2 = new Record(4, 5, 6);
		
		RecordTester.add(r1);
		RecordTester.add(r2);
		
		ArrayList<Record> test = RecordTester.getRecordsByImageID(2);
		assertEquals(1, test.size());
		assertTrue(r1.equals(test.get(0)));
	}
	
	//@Test
	public void testAdd(){
		Record r1 = new Record(1, 2, -1);
		Record r2 = new Record(4, 5, -1);
		
		RecordTester.add(r1);
		RecordTester.add(r2);
		
		ArrayList<Record> allRecords = RecordTester.getAllRecords();
		assertEquals(2, allRecords.size());

	}
	
	@Test
	public void testUpdate(){
		Record record = new Record(7, 8, 9);
		RecordTester.add(record);
		record.setRownumber(90);
		RecordTester.update(record);
		
		ArrayList<Record> test = RecordTester.getRecordsByImageID(8);
		System.out.println(test.size());
		assertTrue(record.equals(test.get(0)));
	}

}
