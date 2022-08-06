package ca.yorku.eecs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

public class RESTHandler {
	
	Neo4jHandler neo4j = new Neo4jHandler();
//	String response = "For a successful add";
//	String badResponse = "Improper Format";

	
//	private void sendString(HttpExchange request, String data, int restCode) 
//			throws IOException {
//		request.sendResponseHeaders(restCode, data.length());
//        OutputStream os = request.getResponseBody();
//        os.write(data.getBytes());
//        os.close();
//	}


	public void handle(HttpExchange request) throws IOException {
		// TODO Auto-generated method stub
		
		try {
			if (request.getRequestMethod().equals("PUT")) {
				switch(request.getRequestURI().getPath()) {
				
				
				case "/api/v1/addActor":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
//					String x = Utils.getBody(request);
//					System.out.println(x);
//					String[] s1 = x.split("\n");
//					String nameActor = s1[1].substring(13);		
//					int ii = nameActor.indexOf("\"");
//					String movieActorFinal = nameActor.substring(0, ii);
//					
//										
//					System.out.println(movieActorFinal);
//					
//					String nameID = s1[2].substring(16);
//					int j = nameID.indexOf("\"");
//					String nameIDFinal = nameID.substring(0, j);
//					System.out.println(nameIDFinal);
//					
//					neo4j.addActor(movieActorFinal, nameIDFinal);
//					
//					sendString(request, response, 200);
					
					
					String addActorBody = Utils.getBody(request);
					JSONObject addActorJSON = new JSONObject(addActorBody);
					System.out.println(addActorJSON);
					
					
					String actorName, actorId;
					
					int addActorStatus = 400;
					
					if (addActorJSON.has("name") && addActorJSON.has("actorId")) {
					
					actorName = addActorJSON.getString("name");
					actorId = addActorJSON.getString("actorId");
					
					System.out.println(actorName);
					System.out.println(actorId);
					
					int addActorStatusCode = neo4j.addActor(actorName,actorId);
					request.sendResponseHeaders(addActorStatusCode, -1);
					}
					
					else {
						request.sendResponseHeaders(addActorStatus, -1);
					}
						
					break;
				case "/api/v1/addMovie":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
					
					
					String addMovieBody = Utils.getBody(request);
					JSONObject addMovieJSON = new JSONObject(addMovieBody);
					System.out.println(addMovieJSON);
					
					String movieName, movieId;
					
					int addMovieStatus = 400;
					
					if (addMovieJSON.has("name") && addMovieJSON.has("movieId")) {
					
					movieName = addMovieJSON.getString("name");
					movieId = addMovieJSON.getString("movieId");
					
					System.out.println(movieName);
					System.out.println(movieId);
					
					int addMovieStatusCode = neo4j.addMovie(movieName, movieId);
					request.sendResponseHeaders(addMovieStatusCode, -1);
					}
					
					else {
						request.sendResponseHeaders(addMovieStatus, -1);
					}
					break;
	
				case "/api/v1/addRelationship":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
					
					
					String addRelationshipBody = Utils.getBody(request);
					JSONObject addRelationshipJSON = new JSONObject(addRelationshipBody);
					System.out.println(addRelationshipJSON);
					
					String movieIdRelationship, actorIdRelationship;
					
					int addRelationshipStatus = 400;
					
					if (addRelationshipJSON.has("movieId") && addRelationshipJSON.has("actorId")) {
					
					movieIdRelationship = addRelationshipJSON.getString("movieId");
					actorIdRelationship = addRelationshipJSON.getString("actorId");
					
					System.out.println(movieIdRelationship);
					System.out.println(actorIdRelationship);
					
					int addRelationshipStatusCode = neo4j.addRelationship(actorIdRelationship, movieIdRelationship);
					request.sendResponseHeaders(addRelationshipStatusCode, -1);
					}
					else {
						request.sendResponseHeaders(addRelationshipStatus, -1);
					}
//					System.out.println(z);
//					String[] r1 = z.split("\n");
//					String firstID = r1[1].substring(16);		
//					int iii = firstID.indexOf("\"");
//					String firstIDFinal = firstID.substring(0, iii);
//					
//										
//					System.out.println(firstIDFinal);
//					
//					String secondID = r1[2].substring(16);
//					int jjj = secondID.indexOf("\"");
//					String secondIDFinal = secondID.substring(0, jjj);
//					System.out.println(secondIDFinal);
//					
//					neo4j.addRelationship(firstIDFinal, secondIDFinal);
//					
//					sendString(request, response, 200);
					
					break;
					
				}
			} 
			
			
			if (request.getRequestMethod().equals("GET")) {
				switch(request.getRequestURI().getPath()) {
				
				case "/api/v1/getActor":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());
					
					
					String getActorBody = Utils.getBody(request);
					JSONObject getActorJSON = new JSONObject(getActorBody);
					
					String getActorId, actorName;
					
					if (getActorJSON.has("actorId")) {
					
					getActorId = getActorJSON.getString("actorId");
					
					actorName = neo4j.getActor(getActorId);
					
					if (actorName.trim().equals("")) {
						request.sendResponseHeaders(404, -1);
						return;
					}
					
					List<String> moviesActedIn = neo4j.getMoviesActedIn(getActorId);
					System.out.println("Movies acted in " + moviesActedIn);
					
					JSONObject getActorResult = new JSONObject().put("actorId", getActorId).put("name", actorName).put("movies", moviesActedIn);
					
					request.sendResponseHeaders(200, getActorResult.toString().length());
					
					OutputStream os = request.getResponseBody();
					os.write(getActorResult.toString().getBytes());
					os.close();
				}
//					System.out.println(a);
//					String[] g1 = a.split("\n");
//					String ID = g1[1].substring(16);
//					int i3 = ID.indexOf("\"");
//					String IDFinal = ID.substring(0, i3);
//					
//					System.out.println(IDFinal);
//					
//					String n = neo4j.getActor(IDFinal);
//					System.out.println("kadkakwn" + n);
				
				}
				
			}
			
			else {
				request.sendResponseHeaders(400, -1);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
