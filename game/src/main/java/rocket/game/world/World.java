package rocket.game.world;

import rocket.game.dao.PlayerDao;
import rocket.game.player.PlayerProxy;

public class World {
	private PlayerDao playerDao = PlayerDao.createUnboundedDao();

	public World() {}
	
	public void registerPlayer(PlayerProxy player) {
		playerDao.add(player);
	}
	
	public void removePlayer(PlayerProxy player) {
		playerDao.remove(player);
	}
	
	public PlayerDao getPlayerDao() {
		return playerDao;
	}
}
