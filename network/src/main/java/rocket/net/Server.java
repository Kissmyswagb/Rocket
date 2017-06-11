package rocket.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import rocket.game.dao.WorldDao;
import rocket.game.world.World;

public class Server extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	private NetServer server;
	private int port;
	
	private WorldDao worlds = new WorldDao();
	
	public Server(int port) {
		this.port = port;
		setupWorlds();
	}
	
	private void setupWorlds() {
		World _317 = new World();
		World _550 = new World();
		worlds.insert(_317); // 1
		worlds.insert(_550);    // 2
	}
	
	@Override
	public void start() {
		
		NetServerOptions options = new NetServerOptions()
						.setPort(port)
						.setIdleTimeout(10);
		
		server = vertx.createNetServer(options);
		
		server.connectHandler(socket -> { 
			
			logger.info("Connection from: {}", socket.remoteAddress());
			
			SessionProxy session = new SessionProxy(new Session(socket));
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
