package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetProjectsParams;
import shared.communication.GetProjectsResult;
import shared.model.Project;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetProjectsHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		GetProjectsParams input = (GetProjectsParams) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		db.startTransaction();
		User inDB = db.getUserDAO().getUser(username, password);
		ArrayList<Project> allProjects = null;
		GetProjectsResult output = null;
		if(inDB != null && inDB.getPassword().equals(password)){
			allProjects = db.getProjectDAO().getAllProjects();
			output = new GetProjectsResult(true, allProjects);
			exchange.sendResponseHeaders(200, 0);
		}
		else{
			//failed
			exchange.sendResponseHeaders(400, 0);
			System.out.println("Wrong password");
		}
		OutputStream out = exchange.getResponseBody();
		xstream.toXML(output, out);
		out.close();
		db.endTransaction(false);
		
	}

}