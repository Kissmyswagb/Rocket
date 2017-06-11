package rocket.game.player;

import rocket.game.world.World;
import rocket.net.SessionProxy;

public class PlayerProxy {
	private Player player;
	private SessionProxy session;
	
	private PlayerProxy(Player player, SessionProxy session) {
		this.player = player;
		this.session = session;
	}
	
	public void registerToWorld(World world) {
		world.registerPlayer(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public SessionProxy getSession() {
		return session;
	}
	
	public static PlayerProxy create(String name, SessionProxy session2) {
		return new PlayerProxy(new Player(name), session2);
	}
}
