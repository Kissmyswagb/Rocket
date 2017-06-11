package rocket.game.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rocket.game.player.PlayerProxy;
import rocket.game.world.World;
import rocket.net.SessionProxy;

public class WorldDao implements Dao<World> {
	private List<World> worlds = new ArrayList<>();

	public PlayerProxy locatePlayerBySession(SessionProxy session) {
		Iterator<World> it = worlds.iterator();
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
		return worlds.get(id - 1);
	}
	
	@Override
	public void insert(World world) {
		worlds.add(world);
	}

	@Override
	public void remove(World world) {
		worlds.remove(world);
	}
	
	@Override
	public World[] getAll() {
		return (World[]) worlds.toArray();
	}

	@Override
	public int size() {
		return worlds.size();
	}
}
