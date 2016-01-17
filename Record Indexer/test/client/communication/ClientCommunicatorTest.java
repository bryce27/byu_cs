package client.communication;

import static org.junit.Assert.*;

import java.util.ArrayList;

import dataimporter.DOMBuilder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.communication.*;
import shared.model.*;

public class ClientCommunicatorTest {
	
	private static ClientCommunicator communicator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		communicator = new ClientCommunicator();
		communicator.getHostPort("localhost", "8080");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		DOMBuilder.main(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidateUser() {
		//test correct pw
		ValidateUserParams input = new ValidateUserParams("test1", "test1");
		ValidateUserResult output = communicator.validateUser(input);
		User user = new User("test1", "test1", "Test", "One", "test1@gmail.com", -1, 0, -1);
		ValidateUserResult result = new ValidateUserResult(true, user);
		assertTrue(output.isApproved());
		assertTrue(output.equals(result));
		//test incorrect pw 
		ValidateUserParams input2 = new ValidateUserParams("test1", "wrong");
		ValidateUserResult output2 = communicator.validateUser(input2);
		assertFalse(output2.isApproved());
	}
	
	@Test
	public void testGetProjects(){
		//test correct pw
		GetProjectsParams input = new GetProjectsParams("test1", "test1");
		GetProjectsResult output = communicator.getProject(input);
		Project p1 = new Project("1890 Census", 8, 199, 60, 1);
		Project p2 = new Project("1900 Census", 10, 204, 62, 2);
		Project p3 = new Project("Draft Records", 7, 195, 65, 3);
		ArrayList<Project> projects = new ArrayList<Project>();
		projects.add(p1);
		projects.add(p2);
		projects.add(p3);
		GetProjectsResult result = new GetProjectsResult(true, projects);
		assertTrue(output.equals(result));
		//test incorrect pw/fail
		GetProjectsParams input2 = new GetProjectsParams("bob123", "wrong");
		GetProjectsResult output2 = communicator.getProject(input2 );
		assertNull(output2);
	}
	
	@Test
	public void testGetSampleImage(){
		//test correct pw
		GetSampleImageParams input = new GetSampleImageParams("test1", "test1", 1);
		GetSampleImageResult output = communicator.getSampleImage(input);
		Image image = new Image("images/1890_image0.png", true, -1, 1);
		GetSampleImageResult result = new GetSampleImageResult(image);
		assertTrue(output.equals(result));
		//test incorrect pw/fail
		GetSampleImageParams input2 = new GetSampleImageParams("bob", "wrong", 2);
		GetSampleImageResult output2 = communicator.getSampleImage(input2);
		assertNull(output2);
		
	}
	
	@Test
	public void testDownloadBatch(){
		//test correct pw
		DownloadBatchParams input = new DownloadBatchParams("test1", "test1", 1);
		DownloadBatchResult output = communicator.downloadBatch(input); // says false available
		Project project = new Project("1890 Census", 8, 199, 60, 1);
		Image image = new Image("images/1890_image0.png", false, 1, 1);
		Field f1 = new Field("Last Name", 1, 60, 300, "fieldhelp/last_name.html", 
				"knowndata/1890_last_names.txt", 1, 1);
		Field f2 = new Field("First Name", 2, 360, 280, "fieldhelp/first_name.html", 
				"knowndata/1890_first_names.txt", 1, 2);
		Field f3 = new Field("Gender", 3, 640, 205, "fieldhelp/gender.html", 
				"knowndata/genders.txt" , 1, 3);
		Field f4 = new Field("Age", 4, 845, 120, "fieldhelp/age.html", "", 1, 4);
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(f1);
		fields.add(f2);
		fields.add(f3);
		fields.add(f4);
		DownloadBatchResult result = new DownloadBatchResult(project, image, fields); // says true available

		assertTrue(output.toString().compareTo(result.toString()) == 0);
		assertTrue(output.equals(result));
		
		//test incorrect pw/fail
		DownloadBatchParams input2 = new DownloadBatchParams("bob123", "wrong", 1);
		DownloadBatchResult output2 = communicator.downloadBatch(input2);
		assertNull(output2);
		
	}
	
	@Test
	public void testGetFields(){
		
		//test correct password w/projectID
		GetFieldsParams input = new GetFieldsParams("test1", "test1", 1);
		GetFieldsResult output = communicator.getFields(input);
		Field f1 = new Field("Last Name", 1, 60, 300, "fieldhelp/last_name.html", 
				"knowndata/1890_last_names.txt", 1, 1);
		Field f2 = new Field("First Name", 2, 360, 280, "fieldhelp/first_name.html", 
				"knowndata/1890_first_names.txt", 1, 2);
		Field f3 = new Field("Gender", 3, 640, 205, "fieldhelp/gender.html", 
				"knowndata/genders.txt" , 1, 3);
		Field f4 = new Field("Age", 4, 845, 120, "fieldhelp/age.html", "", 1, 4);
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(f1);
		fields.add(f2);
		fields.add(f3);
		fields.add(f4);
		GetFieldsResult result = new GetFieldsResult(input.getProjectID(), fields);
		assertTrue(output.toString().compareTo(result.toString()) == 0);
		
		//test correct password without projectID
		GetFieldsParams input2 = new GetFieldsParams("test1", "test1", -1);
		GetFieldsResult output2 = communicator.getFields(input2);
		Field f5 = new Field("Gender", 5, 45, 205, "fieldhelp/gender.html", 
				"knowndata/genders.txt" , 2, 5);
		Field f6 = new Field("Age", 6, 250, 120, "fieldhelp/age.html", "", 2, 6);
		Field f7 = new Field("Last Name", 7, 370, 325, "fieldhelp/last_name.html", 
				"knowndata/1900_last_names.txt", 2, 7);
		Field f8 = new Field("First Name", 8, 695, 325, "fieldhelp/first_name.html", 
				"knowndata/1900_first_names.txt", 2, 8);
		Field f9 = new Field("Ethnicity", 9, 1020, 450, "fieldhelp/ethnicity.html",
				"knowndata/ethnicities.txt", 2, 9);
		Field f10 = new Field("Last Name", 10, 75, 325, "fieldhelp/last_name.html", 
				"knowndata/draft_last_names.txt", 3, 10);
		Field f11 = new Field("First Name", 11, 400, 325, "fieldhelp/first_name.html", 
				"knowndata/draft_first_names.txt", 3, 11);
		Field f12 = new Field("Age", 12, 725, 120, "fieldhelp/age.html", "", 3, 12);
		Field f13 = new Field("Ethnicity", 13, 845, 450, "fieldhelp/ethnicity.html",
				"knowndata/ethnicities.txt", 3, 13);
		ArrayList<Field> allFields = new ArrayList<Field>();
		allFields.add(f1);
		allFields.add(f2);
		allFields.add(f3);
		allFields.add(f4);
		allFields.add(f5);
		allFields.add(f6);
		allFields.add(f7);
		allFields.add(f8);
		allFields.add(f9);
		allFields.add(f10);
		allFields.add(f11);
		allFields.add(f12);
		allFields.add(f13);
		GetFieldsResult result2 = new GetFieldsResult(-1, allFields);
		assertTrue(output2.toString().compareTo(result2.toString()) == 0);

		//test incorrect pw/fail
		GetFieldsParams input3 = new GetFieldsParams("test1", "wrong", 5);
		GetFieldsResult output3 = communicator.getFields(input3);
		assertNull(output3);
		
	}
	
	@Test
	public void testSubmitBatch(){
		//test correct
		DownloadBatchParams setup = new DownloadBatchParams("test1", "test1", 1);
		DownloadBatchResult setupOut = communicator.downloadBatch(setup);
		ArrayList<String> testValues = new ArrayList<String>();
		testValues.add("Mooney"); testValues.add("Dick"); testValues.add("M");
		testValues.add("3"); testValues.add("Carney"); testValues.add("Maxwell");
		testValues.add("M"); testValues.add("50"); testValues.add("Carney");
		testValues.add("Candi"); testValues.add("F"); testValues.add("80");
		testValues.add("Ritter"); testValues.add("Karon"); testValues.add("F");
		testValues.add("83"); 
		for(int i = 0; i < 16; i++){
			testValues.add("");
		}
		SubmitBatchParams input = new SubmitBatchParams("test1", "test1", 1, testValues);
		SubmitBatchResult output = communicator.submitBatch(input);
		SubmitBatchResult result = new SubmitBatchResult(true);
		assertTrue(output.equals(result));
		//test wrong values
		testValues.add("dog");
		SubmitBatchParams input2 = new SubmitBatchParams("test1", "test1", 1, testValues);
		SubmitBatchResult output2 = communicator.submitBatch(input2);
		assertNull(output2);
		
	}
	
	@Test
	public void testSearch(){
		//test correct
		ArrayList<Integer> fields = new ArrayList<Integer>();
		ArrayList<String> inputs = new ArrayList<String>();
		
		fields.add(10); fields.add(11); 
		inputs.add("keith"); 

		SearchParams input = new SearchParams("test1", "test1", fields, inputs);
		SearchResult output = communicator.search(input);
		
		Image image1 = new Image("images/draft_image2.png", true, 3, 43);
		Image image2 = new Image("images/draft_image3.png", true, 3, 44);
		Image image3 = new Image("images/draft_image9.png", true, 3, 50);

		Record record1 = new Record(5, 43, 19);
		Record record2 = new Record(2, 44, 23);
		Record record3 = new Record(1, 50, 64);
		
		ArrayList<Image> images = new ArrayList<Image>();
		ArrayList<Record> records = new ArrayList<Record>();
		
		records.add(record1); records.add(record2); records.add(record3);
		images.add(image1); images.add(image2); images.add(image3);
		
		Cell v1 = new Cell("keith", 19, 10);
		Cell v2 = new Cell("keith", 23, 11);
		Cell v3 = new Cell("keith", 64, 11);
		
		ArrayList<Cell> results = new ArrayList<Cell>();
		results.add(v1); results.add(v2); results.add(v3); //results.add(v4);
		SearchResult result = new SearchResult(images, results, records);

		assertTrue(output.toString().compareTo(result.toString()) == 0);
		assertTrue(output.equals(result));
		
		//test incorrect
		fields.add(50);
		SearchParams input2 = new SearchParams("test1", "test1", fields, inputs);
		SearchResult output2 = communicator.search(input2);
		assertNull(output2);
	}

}