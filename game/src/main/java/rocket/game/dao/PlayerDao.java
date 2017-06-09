package rocket.game.dao;

import java.util.Iterator;

import rocket.game.player.PlayerProxy;
import rocket.net.Session;

public class PlayerDao extends Dao<PlayerProxy> {

	private PlayerDao(int capacity) {
		super(capacity);
	}

	public PlayerProxy getPlayerBySession(Session session) {
		return search(session);
	}

	public PlayerProxy getPlayerByName(String name) {
		return search(name);
	}

	private PlayerProxy search(Object obj) {
		boolean found = false;
		PlayerProxy proxy = null;
		Iterator<PlayerProxy> it = iterator();
		while (it.hasNext() && !found) {
			PlayerProxy next = it.next();
			if (obj instanceof String) {
				if (next.getPlayer().getName().equalsIgnoreCase((String) obj)) {
					proxy = next;
					found = true;
				}
			} else if (next.getSession().equals(obj)) {
				proxy = next;
				found = true;
			}
		}
		return proxy;
	}

	public static PlayerDao createUnboundedDao() {
		return new PlayerDao(-1);
	}
}
