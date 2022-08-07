package ca.yorku.eecs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

public class RESTHandler {

	Neo4jHandler neo4j = new Neo4jHandler();


	public void handle(HttpExchange request) throws IOException {

		try {
			if (request.getRequestMethod().equals("PUT")) {
				switch (request.getRequestURI().getPath()) {

				case "/api/v1/addActor":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());


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

						int addActorStatusCode = neo4j.addActor(actorName, actorId);
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
					} else {
						request.sendResponseHeaders(addRelationshipStatus, -1);
					}

					break;

				}
			}

			if (request.getRequestMethod().equals("GET")) {
				switch (request.getRequestURI().getPath()) {

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

						JSONObject getActorResult = new JSONObject().put("actorId", getActorId).put("name", actorName)
								.put("movies", moviesActedIn);

						request.sendResponseHeaders(200, getActorResult.toString().length());

						OutputStream os = request.getResponseBody();
						os.write(getActorResult.toString().getBytes());
						os.close();
					}

					break;
					
				case "/api/v1/getMovie":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());

					String getMovieBody = Utils.getBody(request);
					JSONObject getMovieJSON = new JSONObject(getMovieBody);

					String getMovieId, movieName;

					if (getMovieJSON.has("movieId")) {

						getMovieId = getMovieJSON.getString("movieId");

						movieName = neo4j.getActor(getMovieId);

						if (movieName.trim().equals("")) {
							request.sendResponseHeaders(404, -1);
							return;
						}

						List<String> moviesActedIn = neo4j.getActorsinMovie(getMovieId);
						System.out.println("Movies acted in " + moviesActedIn);

						JSONObject getActorResult = new JSONObject().put("movieId", getMovieId).put("name", movieName)
								.put("movies", moviesActedIn);

						request.sendResponseHeaders(200, getActorResult.toString().length());

						OutputStream os = request.getResponseBody();
						os.write(getActorResult.toString().getBytes());
						os.close();
					}

					break;
					
				case "/api/v1/hasRelationship":
					System.out.println(request.getRequestMethod());
					System.out.println(request.getRequestURI());

					String hasRelationshipBody = Utils.getBody(request);
					JSONObject hasRelationshipJSON = new JSONObject(hasRelationshipBody);

					String hasRelationshipActorId, hasRelationshipMovieId;
					
					int status = 400;

					if (hasRelationshipJSON.has("movieId") && hasRelationshipJSON.has("actorId")) {

						hasRelationshipMovieId = hasRelationshipJSON.getString("movieId");
						hasRelationshipActorId = hasRelationshipJSON.getString("actorId");
						int hasRelationshipStatusCode = neo4j.checkRelationship(hasRelationshipMovieId, hasRelationshipActorId);

						if (hasRelationshipStatusCode == 200) {
							  JSONObject hasRelationshipJSONBody = new JSONObject().put("actorId", hasRelationshipActorId).put("movieId", hasRelationshipMovieId).put("hasRelationship", true);
				                request.sendResponseHeaders(hasRelationshipStatusCode, hasRelationshipJSONBody.toString().length());
				                OutputStream os = request.getResponseBody();
				                os.write(hasRelationshipJSONBody.toString().getBytes());
				                os.close();
						} else if (hasRelationshipStatusCode == 201) {
							  JSONObject hasRelationshipJSONBody = new JSONObject().put("actorId", hasRelationshipActorId).put("movieId", hasRelationshipMovieId).put("hasRelationship", false);
				                request.sendResponseHeaders(hasRelationshipStatusCode, hasRelationshipJSONBody.toString().length());
				                OutputStream os = request.getResponseBody();
				                os.write(hasRelationshipJSONBody.toString().getBytes());
				                os.close();
						}
						else {
							request.sendResponseHeaders(hasRelationshipStatusCode, -1);
						}
					}
					
					else {
						request.sendResponseHeaders(status, -1);
					}

					break;
				}

			}

			else {
				request.sendResponseHeaders(400, -1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
