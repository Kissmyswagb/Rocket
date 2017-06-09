package rocket.game.dao;

import java.util.Iterator;

import rocket.game.player.PlayerProxy;
import rocket.game.world.World;
import rocket.net.Session;

public class WorldDao extends Dao<World> {
	private WorldDao(int size) {
		super(size);
	}
	
	public PlayerProxy locatePlayer(String name) {
		return locate(name);
	}
	
	public PlayerProxy locatePlayer(Session session) {
		return locate(session);
	}
	
	private PlayerProxy locate(Object obj) {
		Iterator<World> it = iterator();
		PlayerProxy player = null;
		boolean found = false;
		while(it.hasNext() && !found) {
			World world = it.next();
			if (obj instanceof String) {
				PlayerProxy p = world.getPlayerDao().getPlayerByName((String) obj);
				if (p != null) {
					player = p;
					found = true;
				}
			} else {
				PlayerProxy p = world.getPlayerDao().getPlayerBySession((Session) obj);
				if (p != null) {
					player = p;
					found = true;
				}
			}
		}
		return player;
	}

	public static WorldDao createUnbounded() {
		return new WorldDao(-1);
	}
}
