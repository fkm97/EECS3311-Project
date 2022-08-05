package ca.yorku.eecs;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.IOException;
import java.io.OutputStream;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import com.sun.net.httpserver.HttpExchange;

public class Neo4jHandler {
	
	private Driver driver;
	private String uriDb;
	
	public Neo4jHandler() {
    	uriDb = "bolt://localhost:7687";
    	Config config = Config.builder().withoutEncryption().build();
    	driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j", "123456"), config);
	}
	



    public void addActor(String name, String actorId) {
    	try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (a:Actor {name: $x, actorId: $y})", 
					parameters("x", name, "y", actorId)));
    	}
    }
    
    public void addMovie(String name, String movieId) {
    	try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (m:Movie {name: $x, movieId: $y})", 
					parameters("x", name, "y", movieId)));
    	}
    }
	
}
