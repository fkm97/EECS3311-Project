package ca.yorku.eecs;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class Adder {
	
	Neo4jHandler neo4j = new Neo4jHandler();
	String response = "For a successful add";
	String badResponse = "Improper Format";

	
	private void sendString(HttpExchange request, String data, int restCode) 
			throws IOException {
		request.sendResponseHeaders(restCode, data.length());
        OutputStream os = request.getResponseBody();
        os.write(data.getBytes());
        os.close();
	}


	public void handle(HttpExchange request) throws IOException {
		// TODO Auto-generated method stub
		
		try {
			if (request.getRequestMethod().equals("PUT")) {
				switch(request.getRequestURI().getPath()) {
				
				
				case "/api/v1/addActor":
//					request.getRequestBody().
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
					String x = Utils.getBody(request);
					System.out.println(x);
					String[] s1 = x.split("\n");
					String nameActor = s1[1].substring(13);		
					int ii = nameActor.indexOf("\"");
					String movieActorFinal = nameActor.substring(0, ii);
					
					
					sendString(request, response, 200);
					
					System.out.println(movieActorFinal);
					
					String nameID = s1[2].substring(16);
					int j = nameID.indexOf("\"");
					String nameIDFinal = nameID.substring(0, j);
					System.out.println(nameIDFinal);
					
					neo4j.addActor(movieActorFinal, nameIDFinal);
					
					sendString(request, response, 200);

					
						
					break;
				case "/api/v1/addMovie":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
					String y = Utils.getBody(request);
					String[] m1 = y.split("\n");
					String nameMov = m1[1].substring(13);		
					int i = nameMov.indexOf("\"");
					String movieNameFinal = nameMov.substring(0, i);
					
					System.out.println(movieNameFinal);
					
					String movieID = m1[2].substring(16);
					if (movieID == null) {
						sendString(request, badResponse, 400);
					}
					int jj = movieID.indexOf("\"");
					String movieIDFinal = movieID.substring(0, jj);
					System.out.println(movieIDFinal);
					
					neo4j.addMovie(movieNameFinal, movieIDFinal);
					
					sendString(request, response, 200);
					
				}
			} else {
				
			}
		} 
		catch (Exception e) {
			
		}
		
	}
	
}
