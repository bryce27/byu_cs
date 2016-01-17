package server.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	@Before
	public void setUp() throws Exception {
		Database db = new Database();
	}

	@After
	public void tearDown() throws Exception {
		return;
	}

	@Test
	public void test() {
		Database db = new Database();
		db.initialize();
		db.startTransaction();
		db.endTransaction(false);
	}

}
