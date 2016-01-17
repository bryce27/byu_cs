package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.database.Database;
import server.handlers.*;
import shared.model.*;

public class Server {
	
	private static int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	public Server(int portnum){
		SERVER_PORT_NUMBER = portnum;
	}
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("database"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	
	private HttpServer server;
	
	private Server() {
		return;
	}
	
	private void run() {
		
		logger.info("Initializing Database");
		
		Database.initialize();		
	
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER), MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use default executor
		
		server.createContext("/validateuser", validateUserHandler);
		server.createContext("/getprojects", getProjectsHandler);
		server.createContext("/getsampleimage", getSampleImageHandler);
		server.createContext("/downloadbatch", downloadBatchHandler);
		server.createContext("/submitbatch", submitBatchHandler);
		server.createContext("/getfields", getFieldsHandler);
		server.createContext("/search", searchHandler);
		server.createContext("/", downloadFileHandler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler validateUserHandler = (HttpHandler) new ValidateUserHandler();
	private HttpHandler getProjectsHandler = (HttpHandler) new GetProjectsHandler();
	private HttpHandler getSampleImageHandler = (HttpHandler) new SampleImageHandler();
	private HttpHandler downloadBatchHandler = (HttpHandler) new DownloadBatchHandler();
	private HttpHandler submitBatchHandler = (HttpHandler) new SubmitBatchHandler();
	private HttpHandler getFieldsHandler = (HttpHandler) new GetFieldsHandler();
	private HttpHandler searchHandler = (HttpHandler) new SearchHandler();
	private HttpHandler downloadFileHandler = (HttpHandler) new DownloadFileHandler();
	
	public static void main(String[] args) {

		if (args.length > 0){
			new Server(Integer.parseInt(args[0])).run();
		}
		else{
			new Server().run();
		}
	}
}
