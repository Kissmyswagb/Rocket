package rocket.game.player;

import rocket.game.world.World;
import rocket.net.Session;

public class PlayerProxy {
	private Player player;
	private Session session;
	
	private PlayerProxy(Player player, Session session) {
		this.player = player;
		this.session = session;
	}
	
	public void registerToWorld(World world) {
		world.registerPlayer(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Session getSession() {
		return session;
	}
	
	public static PlayerProxy create(String name, Session session) {
		return new PlayerProxy(new Player(name), session);
	}
}
