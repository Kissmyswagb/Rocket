import org.junit.Assert;
import org.junit.Test;

import io.vertx.core.Vertx;
import rocket.net.Server;

public class NetworkTest {

	@Test
	public void create() {
		Vertx vertx = Vertx.vertx();
		Server server = Server.createServer(43594);
		Assert.assertNotNull(vertx);
		Assert.assertNotNull(server);
	}
	
	@Test
	public void bind() {
		Vertx vertx = Vertx.vertx();
		Server server = Server.createServer(43594);
		vertx.deployVerticle(server, res -> {
			Assert.assertTrue(res.succeeded());
		});
	}
	
}
