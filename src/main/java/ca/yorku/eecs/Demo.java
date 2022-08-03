package ca.yorku.eecs;

public class Demo {
	public static void main(String[] args) {
		String name = "Daisy Ridley";
		String actorId = "nm1001213";
		App ap = new App();
		ap.addActor(name, actorId);
		ap.close();
	}
}
