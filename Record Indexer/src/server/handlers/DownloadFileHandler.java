package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.*;

public class DownloadFileHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Path path = Paths.get(exchange.getRequestURI().getPath());
		exchange.sendResponseHeaders(200, 0);
		OutputStream out = exchange.getResponseBody();
		out.write(Files.readAllBytes(path));
		out.close();
	}
	
}