package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.SearchParams;
import shared.communication.SearchResult;
import shared.model.Field;
import shared.model.Image;
import shared.model.Record;
import shared.model.Cell;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SearchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		SearchParams input = (SearchParams) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		//ArrayList<Record> records = db.getRecordDAO().getAllRecords();
		ArrayList<Integer> fieldIDs = input.getFieldIDs();
		ArrayList<String> inputValues = input.getSearchValues();
		
		db.startTransaction();
		User user = db.getUserDAO().getUser(username, password);
		SearchResult output = null;
		if(user != null && user.getPassword().equals(password)){
			//check if field ids are ok
			boolean goodFields = true;
			for(int id : fieldIDs){
				Field posField = db.getFieldDAO().getField(id);
				if(posField == null){
					goodFields = false;
					break;
				}
			}
			if(goodFields){
				ArrayList<Cell> results = new ArrayList<Cell>();
				for(int id : fieldIDs){
					for(String inputval : inputValues){
						ArrayList<Cell> value = db.getCellDAO().getCellsByFieldID(id, inputval);
						if(value.size() != 0){
							results.addAll(value);
						}
					}
				}
				if(results.size() != 0){
					ArrayList<Image> resultImages = new ArrayList<Image>();
					ArrayList<Record> resultRecords = new ArrayList<Record>();
					for(Cell resultval : results){
						int recordID = resultval.getRecord_id();
						Record record = db.getRecordDAO().getRecord(recordID);
						int imageID = record.getImage_id();
						Image image = db.getImageDAO().getImage(imageID);
						resultImages.add(image);
						resultRecords.add(record);
					}
					exchange.sendResponseHeaders(200, 0);
					output = new SearchResult(resultImages, results, resultRecords);
					OutputStream out = exchange.getResponseBody();
					xstream.toXML(output, out);
					out.close();
					
				}
				else{
					exchange.sendResponseHeaders(400, 0);
					System.out.println("no values were found");
				}
			}
			else{
				exchange.sendResponseHeaders(400, 0);
				System.out.println("some field was wrong");
			}
			
		}
		else{
			exchange.sendResponseHeaders(400, 0);
			System.out.println("wrong username or password");
		}
		db.endTransaction(false);
	}

}