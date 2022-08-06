package ca.yorku.eecs;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.util.Pair;

import com.sun.net.httpserver.HttpExchange;

public class Neo4jHandler {
	
	private Driver driver;
	private String uriDb;
	
	public Neo4jHandler() {
    	uriDb = "bolt://localhost:7687";
    	Config config = Config.builder().withoutEncryption().build();
    	driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j", "123456"), config);
	}
	



    public int addActor(String name, String actorId) {
    	try (Session session = driver.session()) {
    		
    		if (actorInData(actorId)) {
    			return 400;
    		}
    		
    		else {
    		
			session.writeTransaction(tx -> tx.run("MERGE (a:Actor {name: $x, actorId: $y})", 
					parameters("x", name, "y", actorId)));
			return 200;
    		}
    	}
    	
    	catch (Exception e) {
    		e.printStackTrace();
    		return 500;
    	}
    }
    
    public boolean actorInData(String id) {
        try(Session session = driver.session()){
            Transaction tx = session.beginTransaction();
            String query = "MATCH (a: Actor) WHERE a.actorId = '%s' RETURN a".formatted(id);
            StatementResult result = tx.run(query);
            Boolean isInData = result.hasNext();
            return isInData;
        }
    }
    
    public int addMovie(String name, String movieId) {
    	    	
    	try (Session session = driver.session()) {
    		
    		if (movieInData(movieId)) {
    			return 400;
    		}
    		
    		else {
    			
			session.writeTransaction(tx -> tx.run("MERGE (m:Movie {name: $x, movieId: $y})", 
					parameters("x", name, "y", movieId)));
			return 200;
    	}
		}
    	
    	catch (Exception e) {
    		e.printStackTrace();
    		return 500;
    	}
		
		
    }
    
    public boolean movieInData(String id) {
        try(Session session = driver.session()){
            Transaction tx = session.beginTransaction();
            String query = "MATCH (m: Movie) WHERE m.movieId = '%s' RETURN m".formatted(id);
            StatementResult result = tx.run(query);
            Boolean isInData = result.hasNext();
            return isInData;
        }
    }
    
    public int addRelationship(String actorId, String movieId) {
    	try (Session session = driver.session()) {
    		
    		if (!actorInData(actorId) || !movieInData(movieId)) {
    			return 404;
    		}
    		
    		else {
    			if (hasRelationship(movieId, actorId)) {
    				return 400;
    			}
    		
    			else {
    			
			session.writeTransaction(tx -> tx.run("MATCH (a:Actor), (m:Movie)  WHERE a.actorId = $x AND m.movieId = $y  CREATE (a)-[r:ACTED_IN]->(m)  RETURN type(r)", parameters("x", actorId, "y", movieId)));
    		
//			session.writeTransaction(tx -> tx.run("MATCH (a:Actor),(m:Movie)\n" + 
//					"WHERE a.actorID = $x AND m.movieID = $y" +
//					 "CREATE (a)-[r:ACTED_IN]->(m)\n" + 
//					 "RETURN type(r)", parameters("x", actorId, "y", movieId)));
			session.close();   
			return 200;
    			}
    		}
			}
    	catch (Exception e) {
    		e.printStackTrace();
    		return 500;
    	}
    }
    
    public boolean hasRelationship(String movieId, String actorId) {
        try(Session session = driver.session()){
            Transaction tx = session.beginTransaction();
            String query = "RETURN EXISTS((:Actor{actorId:'%s'})-[:ACTED_IN]-(:Movie{movieId:'%s'}))".formatted(actorId, movieId);
            StatementResult result = tx.run(query);
            Boolean relationship = result.next().values().get(0).asBoolean();
            return relationship;
        }
    }
    
//    public void getActor(String Id) {
//    	try (Session session = driver.session()) {
////    		session.writeTransaction(tx -> tx.run("MATCH (a:Actor) WHERE a.actorId = $x RETURN a", parameters("x", Id)));
//         	try (Transaction tx = session.beginTransaction()) {
//        		StatementResult result = tx.run("MATCH (a:Actor) WHERE a.actorId = $x RETURN a", parameters("x", Id));
//        		System.out.println(result.summary());
////        		if (result.hasNext()) {
////        			System.out.println(" ID is " + Id);
////        			System.out.println(result.toString());
////        		}
//        		
//        		while ( result.hasNext() )
//        		{
//        		    org.neo4j.driver.v1.Record res = result.next();
//
//        		    System.err.println(" --> "+res.get(0) ); 
//        		    
//        		    List<Pair<String,Value>> values = res.fields();
//            		for (Pair<String,Value> nameValue: values) {
//            			Value value = nameValue.value();
//        		        
//        		        String ai = value.get("actorId").asString();
//        		        String n = value.get("name").asString();
//        		        
//        		        System.out.println(ai + ", " + n);
//            		}
//        		}  
//    		session.close();
//         	}
//    	}
//	
//    }
    
    public String getActor(String actorId) {
        // if an actor with the given actorId DNE, return an empty string
//        if(!actorPresent(actorId)) {
//            return "";
//        }
//        else {
//            try(Session session = driver.session()){
//                Transaction tx = session.beginTransaction();
//                String query = "MATCH (a: Actor) WHERE a.actorId = '%s' RETURN a.name".formatted(actorId);
//                StatementResult result = tx.run(query);
//                String name = result.next().values().get(0).asString();
//                
//                return name;
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//                return "error";
//            }
        //}
    	
    	if (!actorInData(actorId)) {
    		return "";
    	}
    	else {
    	
        try(Session session = driver.session()){
            Transaction tx = session.beginTransaction();
            String query = "MATCH (a: Actor) WHERE a.actorId = '%s' RETURN a.name".formatted(actorId);
            StatementResult result = tx.run(query);
            String name = result.next().values().get(0).asString();
            return name;
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return "error";
    	}
    	}
    }
    
    public List<String> getMoviesActedIn(String actorId) {
        try(Session session = driver.session()){
            Transaction tx = session.beginTransaction();
            String query = "MATCH (a {actorId: '%s'})-[:ACTED_IN]->(b) RETURN b".formatted(actorId);
            StatementResult result = tx.run(query);
            List<String> movies = new ArrayList<>();
            while(result.hasNext()) {
                Record row = result.next();
                String movie = row.values().get(0).get("movieId").toString();
                movies.add(movie);
            }
            return movies;
        }
    }
    
}
