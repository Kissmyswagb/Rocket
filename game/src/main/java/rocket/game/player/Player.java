package rocket.game.player;

import rocket.game.Location;

public class Player {
	private String name;
	private int hp;
	private int prayerPoints;
	private int runEnergy;

	private Location location = Location.create(3232, 3232);
	
	private Player(String name) {
		this.name = name;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public static Player createPlayer(String name) {
		return new Player(name);
	}
}
