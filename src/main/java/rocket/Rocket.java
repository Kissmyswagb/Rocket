package rocket;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import io.vertx.core.Vertx;

import rocket.net.Server;

public class Rocket {
	private static final Logger logger = LoggerFactory.getLogger(Rocket.class);
	private static final int PORT = Integer.parseInt(System.getProperty("port", "43594"));

	public static void main(String[] args) {
		Stopwatch stopwatch = Stopwatch.createStarted();

		Vertx vertx = Vertx.vertx();
		Server server = new Server(PORT);

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
}
