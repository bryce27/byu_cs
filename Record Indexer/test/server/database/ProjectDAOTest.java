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

import server.database.ProjectDAO;
import shared.model.Project;

public class ProjectDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Load database driver	
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	private Database db;
	private ProjectDAO ProjectTester;
	
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
		ProjectTester = db.getProjectDAO();	
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		ProjectTester = null;
	}

	@Test
	public void testGetProject() {
		Project test = ProjectTester.getProject(1);
		assertNull(test);
		
	}

	@Test
	public void testGetAll() {
		ArrayList<Project> allProjects = ProjectTester.getAllProjects();
		assertEquals(0, allProjects.size());
	}

	@Test
	public void testAdd() {
		Project p1 = new Project("Project1!", 10, 100, 60, 1);
		Project p2 = new Project("Project 2", 5, 101, 62, 2);
		
		ProjectTester.add(p1);
		ProjectTester.add(p2);
		
		ArrayList<Project> allProjects = ProjectTester.getAllProjects();
		assertEquals(2, allProjects.size());
		
		Project p1result = allProjects.get(0);
		Project p2result = allProjects.get(1);
		
		assertTrue(p1.equals(p1result) && p2.equals(p2result));
	}

	@Test
	public void testUpdate() {
		Project project = new Project("project3", 11, 12, 13, -1);
		
		ProjectTester.add(project);
		project.setTitle("whee update");
		project.setRecordheight(100);
		project.setFirstycoord(200);
		
		ProjectTester.update(project);
		
		Project updated = ProjectTester.getProject(1);
		
		assertTrue(project.equals(updated));
	}
	
	

}