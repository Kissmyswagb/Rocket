package rocket;


import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import io.vertx.core.Vertx;
import rocket.game.dao.WorldDao;
import rocket.game.world.World;
import rocket.net.Server;
import rocket.task.TaskHandler;

public enum Rocket {
	INSTANCE;
	
	private static final Logger logger = LoggerFactory.getLogger(Rocket.class);
	private static final int PORT = Integer.parseInt(System.getProperty("port", "43594"));
	private Rocket() {
		worldDao.add(new World());
	}
	
	public static void main(String[] args) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		final Vertx vertx = Vertx.vertx();
		final Server server = Server.createServer(PORT);

		vertx.deployVerticle(server, result -> {
			if (result.succeeded()) {
				logger.info("Succesfully deployed Rocket:" + result.result());
			} else {
				logger.error("Failed to deploy Rocket: " + result.cause().getMessage());
			}
		});

		long finish = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to deploy " + finish + "ms");
	}
	
	private final TaskHandler taskHandler = TaskHandler.createWithTime(600);
	private final WorldDao worldDao = WorldDao.createUnbounded();
	
	public TaskHandler getTaskHandler() {
		return taskHandler;
	}
	
	public WorldDao getWorldDao() {
		return worldDao;
	}
}
