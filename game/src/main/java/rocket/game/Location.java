package rocket.game;

public class Location {
	private int x;
	private int y;
	private int z;

	private Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Location() {}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public static Location create(int x, int y) {
		return create(x, y, 0);
	}
	
	public static Location create(int x, int y, int z) {
		return new Location(x, y, z);
	}
}
