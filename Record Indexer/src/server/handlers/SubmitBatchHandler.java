package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.SubmitBatchParams;
import shared.communication.SubmitBatchResult;
import shared.model.Field;
import shared.model.Image;
import shared.model.Cell;
import shared.model.User;
import shared.model.Record;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SubmitBatchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		SubmitBatchParams input = (SubmitBatchParams) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int imageID = input.getImageID();
		ArrayList<String> values = input.getRecordValues();
		
		db.startTransaction();
		User user = db.getUserDAO().getUser(username, password);
		
		Image image = db.getImageDAO().getImage(user.getCurrentbatch_id());
		
		SubmitBatchResult output = null;
		
		if(user != null && user.getPassword().equals(password) && image != null && imageID == image.getId()){
			ArrayList<Field> fields = db.getFieldDAO().getFieldsByProjectID(image.getProject_id());
			double indexedRecords = values.size()/fields.size();
			if(indexedRecords > db.getProjectDAO().getProject(image.getProject_id()).getRecordsperimage()){
				exchange.sendResponseHeaders(400, 0);
				db.endTransaction(false);
				System.out.println("Too many values!");
			}
			else if(indexedRecords != (int) indexedRecords){
				exchange.sendResponseHeaders(400, 0);
				db.endTransaction(false);
				System.out.println("Wrong number of values!");
			}
			else{
				int fieldIDcount = 0;
				int rowsCount = 1;
				for(String rval: values){
					Record record = new Record(rowsCount, imageID, -1);
					int recordID = db.getRecordDAO().add(record);
					
					// this rowsCount thing might not work
					Cell cell = new Cell(rval, recordID, fields.get(fieldIDcount).getId());
					db.getCellDAO().add(cell);
					
					fieldIDcount++;
					if(fieldIDcount == fields.size()){
						fieldIDcount = 0;
						rowsCount++;
					}
					
				}
				System.out.println("submitted!!");
				output = new SubmitBatchResult(true);
				exchange.sendResponseHeaders(200, 0);
				OutputStream out = exchange.getResponseBody();
				xstream.toXML(output, out);
				out.close();
				user.setCurrentbatch_id(-1);
				image.setAvailable(false);
				user.incrementRecordsIndexed((int) indexedRecords);
				db.getImageDAO().update(image);
				db.getUserDAO().update(user);
				System.out.println("submitted: " + user);
				db.endTransaction(true);
			}
		}
		else{
			exchange.sendResponseHeaders(400, 0);
			db.endTransaction(false);
			System.out.println("Wrong password or trying to submit an image you do not own.");
		}
		
	}

}