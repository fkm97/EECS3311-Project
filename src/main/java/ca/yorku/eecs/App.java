package ca.yorku.eecs;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

public class App 
{
	
	private Driver driver;
	private String uriDb;
	
    static int PORT = 8080;
    public static void main(String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
    }
    
    public App() {
    	uriDb = "bolt://localhost:7687";
    	Config config = Config.builder().withoutEncryption().build();
    	driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j", "123456"), config);
    }
    
	public void handle(HttpExchange request) throws IOException {
		// TODO Auto-generated method stub
		
		try {
			if (request.getRequestMethod().equals("GET") && request.getRequestURI().getPath() == "/api/v1/addActor") {
				
			} else {
				
			}
		} 
		catch (Exception e) {
			
		}
		
	}
    
    
    
    public void addActor(String name, String actorId) {
    	try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (a:Actor {name: $x, actorId: $y})", 
					parameters("x", name, "y", actorId)));
    	}
    }
    
    public void close() {
    	driver.close();
    }
    
}
