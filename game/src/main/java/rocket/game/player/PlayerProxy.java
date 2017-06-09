package rocket.game.player;

import rocket.Rocket;
import rocket.game.dao.WorldDao;
import rocket.game.world.World;
import rocket.net.Session;

public class PlayerProxy {
	private Player player;
	private Session session;
	
	public PlayerProxy(Player player, Session session) {
		this.player = player;
		this.session = session;
	}
	
	public void register() {
		WorldDao worlds = Rocket.INSTANCE.getWorldDao();
		World world = worlds.get(session.getWorld() - 1);
		world.registerPlayer(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Session getSession() {
		return session;
	}
	
	public static PlayerProxy createPlayerAndProxy(String name, Session session) {
		final Player player = Player.createPlayer(name);
		return new PlayerProxy(player, session);
	}
	
	public static PlayerProxy createProxy(Player player, Session session) {
		return new PlayerProxy(player, session);
	}
}
