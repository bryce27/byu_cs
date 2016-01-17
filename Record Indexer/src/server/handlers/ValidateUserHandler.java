package server.handlers;

import java.io.IOException;
import java.io.OutputStream;

import server.database.Database;
import shared.communication.ValidateUserParams;
import shared.communication.ValidateUserResult;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ValidateUserHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver()); //Xstream possible parameter: new DomDriver()
		ValidateUserParams input = (ValidateUserParams) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		db.startTransaction();
		User inDB = db.getUserDAO().getUser(username, password);
		//validate username and password
		ValidateUserResult output = null;
		if(inDB != null && inDB.getPassword().equals(password)){
			output = new ValidateUserResult(true, inDB);
			exchange.sendResponseHeaders(200, 0);
		}
		else{
			output = new ValidateUserResult(false, null);
			exchange.sendResponseHeaders(401, 0);
			System.out.println("Wrong username or password");
		}
		
		OutputStream out = exchange.getResponseBody();
		xstream.toXML(output, out);
		out.close();
		db.endTransaction(false);
	}

}