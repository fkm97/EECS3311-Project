package ca.yorku.eecs;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class Connect {

	public static String uriDb = "bolt://localhost:7687"; // may need to change if you used a different port for your DBMS
    public static String uriUser ="http://localhost:8080";
    public static Config config = Config.builder().withoutEncryption().build();
    public static Driver driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","123456"), config);
	
}
