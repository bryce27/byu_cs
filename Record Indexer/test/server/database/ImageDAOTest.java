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

import shared.model.Image;

public class ImageDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	private static Database db;
	private ImageDAO imageTester;

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
		imageTester = db.getImageDAO();
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		imageTester = null;
	}


	public void testGetImagesbyProjectID(){
		Image image1 = new Image("url", true, 2, 1);
		Image image2 = new Image("url2", true, 3, 2);
		Image image3 = new Image("url3", false, 2, 3);
		Image image4 = new Image("url4", false, 2, 4);
		imageTester.add(image1);
		imageTester.add(image2);
		imageTester.add(image3);
		imageTester.add(image4);
		
		ArrayList<Image> images = imageTester.getImagesbyProjectID(2);
		
		assertEquals(3, images.size());
	}
	

	public void testGetImage(){
		Image test = imageTester.getImage(1);
		assertNull(test);
	}


	public void testGetAll(){
		ArrayList<Image> images = imageTester.getAllImages();
		assertEquals(images.size(), 0);
	}


	public void testAdd(){
		Image test = new Image("imageURL", true, 2, 1);
		imageTester.add(test);
		Image result = imageTester.getImage(1);
		assertTrue(test.equals(result));
		
	}
	@Test
	public void testUpdate(){
		Image test = new Image("imageurllllll", false, 2, 1);
		imageTester.add(test);
		
		test.setAvailable(true);
		imageTester.update(test);
		Image updated = imageTester.getImage(1);
		
		assertTrue(test.equals(updated));
		
	}

}