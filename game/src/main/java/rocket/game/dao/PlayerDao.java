package rocket.game.dao;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rocket.game.player.PlayerProxy;
import rocket.net.SessionProxy;

public class PlayerDao implements Dao<PlayerProxy> {
	private List<PlayerProxy> players = new ArrayList<>();
	private int capacity;
	
	private PlayerDao(int capacity) {
		this.capacity = capacity;
	}

	public PlayerProxy getPlayerBySession(SessionProxy session) {
		boolean found = false;
		PlayerProxy proxy = null;
		Iterator<PlayerProxy> it = players.iterator();
		while (it.hasNext() && !found) {
			PlayerProxy next = it.next();
			if (next.getSession().equals(session)) {
				proxy = next;
				found = true;
			}
		}
		return proxy;
	}
	
	public PlayerProxy getPlayerByName(String name) {
		boolean found = false;
		PlayerProxy proxy = null;
		Iterator<PlayerProxy> it = players.iterator();
		while (it.hasNext() && !found) {
			PlayerProxy next = it.next();
			if (next.getPlayer().getName().equalsIgnoreCase(name)) {
				proxy = next;
				found = true;
			}
		}
		return proxy;
	}
	
	public PlayerProxy get(int index) {
		return players.get(index);
	}

	@Override
	public void insert(PlayerProxy element) {
		if (players.size() > capacity && capacity != -1) {
			throw new BufferOverflowException();
		}
		players.add(element);
	}

	@Override
	public void remove(PlayerProxy element) {
		players.remove(element);
	}
	
	@Override
	public PlayerProxy[] getAll() {
		return (PlayerProxy[]) players.toArray();
	}

	@Override
	public int size() {
		return players.size();
	}
	
	public static PlayerDao createUnboundedDao() {
		return new PlayerDao(-1);
	}
}
