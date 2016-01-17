package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import shared.model.User;

public class UserDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	
	private static Database db;
	private UserDAO usertest;

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
		usertest = db.getUserDAO();
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		usertest = null;
	}


	@Test
	public void testGetUser() {
		User test = usertest.getUser("bob", "password");
		assertNull(test);
	}

	@Test
	public void testAdd() {
		User bob = new User("BobW", "password", "Bob", "White", 
				"bob@white.org", 0, 50, 1);
		User amy = new User("AmyB", "password2", "Amy", "Brown",
						"amy@brown.org", 2, 100, 2);
		
		usertest.add(bob);
		usertest.add(amy);
		
		User bobresult = usertest.getUser("BobW", "password");
		User amyresult = usertest.getUser("AmyB", "password");
		
		assertTrue(bob.equals(bobresult) && amy.equals(amyresult));
	}

	@Test
	public void testUpdate() {
		User test = new User("BobW", "password", "Bob", "White", 
				"bob@white.org", 1, 50, 2);
		
		usertest.add(test);
		test.setIndexedrecords(2);
		usertest.update(test);
		
		User updated = usertest.getUser("BobW", "password");
		
		assertTrue(test.equals(updated));
	}

}