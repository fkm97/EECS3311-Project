package ca.yorku.eecs;

public class Demo {
	public static void main(String[] args) {
		String name = "Daisy Ridley";
		String actorId = "nm1001213";
		Neo4jHandler ap = new Neo4jHandler();
		ap.addActor(name, actorId);
		}
}
