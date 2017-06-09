package rocket.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import rocket.game.dao.WorldDao;
import rocket.game.world.World;
import rocket.net.Session.State;

public class Server extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	private NetServer server;
	private int port;
	
	private WorldDao worlds = WorldDao.createUnbounded();
	
	public Server(int port) {
		this.port = port;

		World world = new World();
		worlds.add(world);
	}
	
	@Override
	public void start() {
		NetServerOptions options = new NetServerOptions()
				.setPort(port)
				.setIdleTimeout(10);
		server = vertx.createNetServer(options);
		server.connectHandler(socket -> { 
			logger.info("Connection from: {}", socket.remoteAddress());
			Session session = new Session(socket);
			socket.handler(new Handler(worlds, session));	
			socket.closeHandler(s -> {
				session.close();
				session.switchState(State.LOGIN);
				logger.info("{} disconnected", socket.remoteAddress());
			});
		});
		server.listen();
	}
}
