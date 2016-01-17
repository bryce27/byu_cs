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

import shared.model.Cell;

public class CellDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	private static Database db;
	private CellDAO CellTester;

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
		CellTester = db.getCellDAO();
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		CellTester = null;
	}
	
	@Test
	public void testGetAll(){
		ArrayList<Cell> allCells = CellTester.getAllCells();
		assertEquals(0, allCells.size());
	}
	
	@Test
	public void testGetCellsByFieldID() {
		Cell v1 = new Cell("Cell1", 1, 2);
		Cell v2 = new Cell("Cell2!", 3, 2);
		
		CellTester.add(v1);
		CellTester.add(v2);
		
		ArrayList<Cell> test = CellTester.getCellsByFieldID(2, "Cell1");
		//ArrayList<Rvalue> test = valueTester.getValue(2, "input!?");
		assertEquals(1, test.size());
	}
	
	@Test
	public void testAdd(){
		Cell v1 = new Cell("Cell1", 1, 2);
		Cell v2 = new Cell("Cell2!", 3, 4);
		
		CellTester.add(v1);
		CellTester.add(v2);
		
		ArrayList<Cell> allCells = CellTester.getAllCells();
		assertEquals(2, allCells.size());
		
		Cell result1 = allCells.get(0);
		Cell result2 = allCells.get(1);
		
		assertTrue(v1.equals(result1) && v2.equals(result2));
	}
	
	@Test
	public void testUpdate(){
		Cell Cell = new Cell("Cellasdadsag", 1, 2);
		
		CellTester.add(Cell);
		Cell.setContent("meow");
		
		CellTester.update(Cell);
		
		ArrayList<Cell> updated = CellTester.getCellsByFieldID(2, "meow");
		
		assertTrue(Cell.equals(updated.get(0)));
		
	}

}