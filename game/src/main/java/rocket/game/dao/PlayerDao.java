package rocket.game.dao;

import java.util.Iterator;

import rocket.game.player.PlayerProxy;
import rocket.net.Session;

public class PlayerDao extends Dao<PlayerProxy> {

	private PlayerDao(int capacity) {
		super(capacity);
	}

	public PlayerProxy getPlayerBySession(Session session) {
		boolean found = false;
		PlayerProxy proxy = null;
		Iterator<PlayerProxy> it = iterator();
		while (it.hasNext() && !found) {
			PlayerProxy next = it.next();
			if (next.getSession().equals(session)) {
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
