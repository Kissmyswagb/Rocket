package rocket.game.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rocket.game.player.PlayerProxy;
import rocket.game.world.World;
import rocket.net.Session;

public class WorldDao implements Dao<World> {
	private List<World> elements = new ArrayList<>();

	public PlayerProxy locatePlayerBySession(Session session) {
		Iterator<World> it = elements.iterator();
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
	
	public World get(int id) {
		return elements.get(id - 1);
	}
	
	@Override
	public void insert(World world) {
		elements.add(world);
	}

	@Override
	public void remove(World world) {
		elements.remove(world);
	}

	@Override
	public int size() {
		return elements.size();
	}
}
