package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetFieldsParams;
import shared.communication.GetFieldsResult;
import shared.model.Field;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetFieldsHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		GetFieldsParams input = (GetFieldsParams) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int projectID = input.getProjectID();
		
		db.startTransaction();
		User user = db.getUserDAO().getUser(username, password);
		GetFieldsResult output = null;
		if(user != null && user.getPassword().equals(password)){
			ArrayList<Field> fields = null;
			if(projectID == -1){
				fields = db.getFieldDAO().getAllFields();
			}
			else{
				fields = db.getFieldDAO().getFieldsByProjectID(projectID);
			}
			if(fields.size() != 0){
				output = new GetFieldsResult(projectID, fields);
				exchange.sendResponseHeaders(200, 0);
			}
			else{
				exchange.sendResponseHeaders(400, 0);
				System.out.println("no such project");
			}
		}
		else{
			exchange.sendResponseHeaders(400, 0);
			System.out.println("incorrect password");
		}
		OutputStream out = exchange.getResponseBody();
		xstream.toXML(output, out);
		out.close();
		db.endTransaction(false);
	}

}
