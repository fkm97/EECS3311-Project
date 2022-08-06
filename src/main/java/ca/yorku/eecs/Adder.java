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
					if (m1.length < 4) {
						sendString(request, badResponse, 400);
					}
					String nameMov = m1[1].substring(13);		
					int i = nameMov.indexOf("\"");
					String movieNameFinal = nameMov.substring(0, i);
					
					System.out.println(movieNameFinal);
					if (m1[2].length() <= 16) {
						sendString(request, badResponse, 400);
					}
					else {
						String movieID = m1[2].substring(16);
						System.out.println("movieID" + movieID);
//						if (movieID == null) {
//							sendString(request, badResponse, 400);
//						}
						
						int jj = movieID.indexOf("\"");
						String movieIDFinal = movieID.substring(0, jj);
						System.out.println(movieIDFinal);
						
						neo4j.addMovie(movieNameFinal, movieIDFinal);
						
						sendString(request, response, 200);
					}
				case "/api/v1/addRelationship":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
					String z = Utils.getBody(request);
					System.out.println(z);
					String[] r1 = z.split("\n");
					String firstID = r1[1].substring(16);		
					int iii = firstID.indexOf("\"");
					String firstIDFinal = firstID.substring(0, iii);
					
										
					System.out.println(firstIDFinal);
					
					String secondID = r1[2].substring(16);
					int jjj = secondID.indexOf("\"");
					String secondIDFinal = secondID.substring(0, jjj);
					System.out.println(secondIDFinal);
					
					neo4j.addRelationship(firstIDFinal, secondIDFinal);
					
					sendString(request, response, 200);
					
					
				}
			} 
			
			
			if (request.getRequestMethod().equals("GET")) {
				switch(request.getRequestURI().getPath()) {
				
				case "/api/v1/getActor":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
					String a = Utils.getBody(request);
					System.out.println(a);
					String[] g1 = a.split("\n");
					String ID = g1[1].substring(16);
					int i3 = ID.indexOf("\"");
					String IDFinal = ID.substring(0, i3);
					
					System.out.println(IDFinal);
					
					String n = neo4j.getActor(IDFinal);
					System.out.println("kadkakwn" + n);
				
				}
				
			}
			
			else {
				
			}
		} 
		catch (Exception e) {
			
		}
		
	}
	
}
