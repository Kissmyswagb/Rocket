package rocket.game.dao;

import java.util.Iterator;

import rocket.game.player.PlayerProxy;
import rocket.game.world.World;
import rocket.net.Session;

public class WorldDao extends Dao<World> {
	private WorldDao(int size) {
		super(size);
	}
	
	public PlayerProxy locatePlayerBySession(Session session) {
		Iterator<World> it = iterator();
		PlayerProxy player = null;
		boolean found = false;
		while(it.hasNext() && !found) {
			World world = it.next();
			PlayerProxy p = world.getPlayerDao().getPlayerBySession(session);
			if (p != null) {
				player = p;
				found = true;
			}
		}
		return player;
	}
	
	public static WorldDao createUnbounded() {
		return new WorldDao(-1);
	}
}
