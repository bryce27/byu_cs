package dataimporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import server.database.Database;
import shared.model.Image;
import shared.model.Project;
import shared.model.Cell;
import shared.model.User;
import shared.model.Field;
import shared.model.Record;

public class DOMBuilder {
	
	private static Database database = new Database();
	private static int projectID; 
	private static int imageID;
	private static ArrayList<Integer> fieldnumbers;
	private static String urlPrefix;

	public static void main(String [] args) throws Exception {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		File xmlInputFile = null;

		if (args != null && args.length > 0){
			xmlInputFile = new File(args[0]);
			urlPrefix = xmlInputFile.getParentFile().getPath() + "/";
		}
		
		else {
			xmlInputFile = new File("Records.xml");
			urlPrefix = "";
		}
		
		Document doc = builder.parse(xmlInputFile);
		projectID = 0;
		imageID = 0;
		fieldnumbers =  new ArrayList<Integer>();
		database.initialize();
		clearDatabase();
		database.startTransaction();

		NodeList usersList = doc.getElementsByTagName("user");
		parseUsers(usersList);
		NodeList projectsList = doc.getElementsByTagName("project");
		parseProjects(projectsList);
		database.endTransaction(true);

	}

	public static void clearDatabase(){
		
		File clearFile = new File("ClearTable.txt");
		
		try {
			Scanner scanClear = new Scanner(clearFile);
			String sql = "";
			while(scanClear.hasNextLine()){
				sql += scanClear.nextLine() + "\n";
			}
			database.startTransaction();
			database.getConnection().createStatement().executeUpdate(sql);
			database.endTransaction(true);
			
		} catch (FileNotFoundException| SQLException e) {
			e.printStackTrace();
			System.out.println("no such file to clear database/sql exception");
		}
		
		
	}

	public static void parseUsers(NodeList usersList){
		
		for (int i = 0; i < usersList.getLength(); i++){

			Element userElem = (Element) usersList.item(i);

			Element usernameElem = (Element) userElem.getElementsByTagName("username").item(0);
			Element passwordElem = (Element) userElem.getElementsByTagName("password").item(0);
			Element firstnameElem = (Element) userElem.getElementsByTagName("firstname").item(0);
			Element lastnameElem = (Element) userElem.getElementsByTagName("lastname").item(0);
			Element emailElem = (Element) userElem.getElementsByTagName("email").item(0);
			Element indexedRecordsElem = (Element) userElem.getElementsByTagName("indexedrecords").item(0);

			String username = usernameElem.getTextContent();
			String password = passwordElem.getTextContent();
			String firstname = firstnameElem.getTextContent();
			String lastname = lastnameElem.getTextContent();
			String email = emailElem.getTextContent();
			int indexedRecords = Integer.parseInt(indexedRecordsElem.getTextContent());
			
			User user = new User(username, password, firstname, lastname, email, -1, indexedRecords, -1);
			database.getUserDAO().add(user);
		}
	}

	public static void parseProjects(NodeList projectsList){
		
		for (int i = 0; i < projectsList.getLength(); i++){
			Element projectElem = (Element) projectsList.item(i);

			Element titleElem = (Element) projectElem.getElementsByTagName("title").item(0);
			Element recordsperimageElem = (Element) projectElem.getElementsByTagName("recordsperimage").item(0);
			Element firstycoordElem = (Element) projectElem.getElementsByTagName("firstycoord").item(0);
			Element recordheightElem =  (Element) projectElem.getElementsByTagName("recordheight").item(0);

			String title = titleElem.getTextContent();
			int recordsperimage = Integer.parseInt(recordsperimageElem.getTextContent());
			int firstycoord = Integer.parseInt(firstycoordElem.getTextContent());
			int recordheight = Integer.parseInt(recordheightElem.getTextContent());

			Project project = new Project(title, recordsperimage, firstycoord, recordheight, -1);
			database.getProjectDAO().add(project);
			projectID++;
			
			Element fieldElem = (Element) projectElem.getElementsByTagName("field").item(0);
			NodeList fieldsList = null;
			
			int rows = project.getRecordsperimage();
			
			if (fieldElem != null){
				fieldsList = projectElem.getElementsByTagName("field");
				parseFields(fieldsList);
			}
			
			Element imageElem = (Element) projectElem.getElementsByTagName("image").item(0);
			if (imageElem != null){
				NodeList imagesList = projectElem.getElementsByTagName("image");
				parseImages(imagesList, rows, fieldsList);
			}
			
			fieldnumbers.clear();
		}
	}

	
	public static void parseFields(NodeList fieldsList){
		
		for(int i = 0; i < fieldsList.getLength(); i++){
			
			Element fieldElem = (Element) fieldsList.item(i);
			
			Element titleElem = (Element) fieldElem.getElementsByTagName("title").item(0);
			Element xcoordElem = (Element) fieldElem.getElementsByTagName("xcoord").item(0);
			Element widthElem = (Element) fieldElem.getElementsByTagName("width").item(0);
			Element helphtmlElem = (Element) fieldElem.getElementsByTagName("helphtml").item(0);
			Element knowndataElem = (Element) fieldElem.getElementsByTagName("knowndata").item(0);
			
			String title = titleElem.getTextContent();
			int xcoord = Integer.parseInt(xcoordElem.getTextContent());
			int width = Integer.parseInt(widthElem.getTextContent());
			String helphtml = helphtmlElem.getTextContent();
			int columnnumber = i + 1;
			String knowndata = "";
			
			if(knowndataElem == null){
				knowndata = "";
			}
			
			else{
				knowndata = urlPrefix + knowndataElem.getTextContent();
			}
			
			
			Field field = new Field(title, columnnumber, xcoord, width, urlPrefix+helphtml, knowndata, projectID, -1);
			database.getFieldDAO().add(field);
		}
		
		ArrayList<Field> currFields = database.getFieldDAO().getFieldsByProjectID(projectID);
		
		for(Field f : currFields){
			fieldnumbers.add(f.getId());
		}
	}
	
	public static void parseImages(NodeList imagesList, int rows, NodeList fieldsList){
		
		for (int i = 0; i < imagesList.getLength(); i++){
			
			Element imageElem = (Element) imagesList.item(i);
			
			Element fileElem = (Element) imageElem.getElementsByTagName("file").item(0);
			
			String file = fileElem.getTextContent();
			
			Image image = new Image(urlPrefix+file, true, projectID, -1);
			database.getImageDAO().add(image);
			imageID++;
			
			Element recordElem = (Element) imageElem.getElementsByTagName("record").item(0);
			NodeList recordsList = null;
			
			// possibly breaks around here
			if (recordElem != null){
				recordsList = imageElem.getElementsByTagName("record");
				parseRecords(recordsList, rows, fieldsList.getLength(), imageID);
			}
			
//			if(recordElem != null){
//				NodeList valuesList = imageElem.getElementsByTagName("value");
//				parseValues(valuesList, rows, fieldsList.getLength());
//			}
			
		}
	}
	
	public static void parseRecords(NodeList recordsList, int rows, int fields, int currentImageID){
		
		for (int i = 0; i < recordsList.getLength(); i++){
			
			Element recordElem = (Element) recordsList.item(i);
			
			Element cellElem = (Element) recordElem.getElementsByTagName("value").item(0);
			NodeList cellsList = null;
			Record record = new Record(i+1, currentImageID, -1);
			int currentRecordID = database.getRecordDAO().add(record);
			
			if (cellElem != null){
				cellsList = recordElem.getElementsByTagName("value");
				parseValues(cellsList, fields, currentRecordID);
			}
		}
	}
	
	public static void parseValues(NodeList valuesList, int fields, int currentRecordID){
		
		int currField = 0;
		for(int i = 0; i < valuesList.getLength(); i++){
			
			Element valueElem = (Element) valuesList.item(i);
			String value = valueElem.getTextContent().toLowerCase();
			
			Cell recordValue = new Cell(value, currentRecordID, fieldnumbers.get(currField));
			
			// if these id's don't work then make them more specific
			database.getCellDAO().add(recordValue);
			currField++;
			
			if(currField == fieldnumbers.size()){
				currField = 0;
			}
			

		}
	}
}
