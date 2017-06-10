package rocket.game.dao;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rocket.game.player.PlayerProxy;
import rocket.net.Session;

public class PlayerDao implements Dao<PlayerProxy> {
	private List<PlayerProxy> elements = new ArrayList<>();
	private int capacity;
	
	private PlayerDao(int capacity) {
		this.capacity = capacity;
	}

	public PlayerProxy getPlayerBySession(Session session) {
		boolean found = false;
		PlayerProxy proxy = null;
		Iterator<PlayerProxy> it = elements.iterator();
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
		Iterator<PlayerProxy> it = elements.iterator();
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
		return elements.get(index);
	}

	@Override
	public void insert(PlayerProxy element) {
		if (elements.size() > capacity && capacity != -1) {
			throw new BufferOverflowException();
		}
		elements.add(element);
	}

	@Override
	public void remove(PlayerProxy element) {
		elements.remove(element);
	}

	@Override
	public int size() {
		return elements.size();
	}
	
	public static PlayerDao createUnboundedDao() {
		return new PlayerDao(-1);
	}
}
