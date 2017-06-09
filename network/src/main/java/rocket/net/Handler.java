package rocket.net;

import io.vertx.core.buffer.Buffer;
import rocket.game.dao.WorldDao;
import rocket.net.Session.State;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;
import rocket.net.request.game.GameRequest;
import rocket.net.request.handshake.HandshakeRequest;
import rocket.net.request.login.LoginRequest;

public class Handler implements io.vertx.core.Handler<Buffer> {
	private Session session;
	private WorldDao worldDao;
	
	public Handler(WorldDao worldDao, Session session) {
		this.session = session;
		this.worldDao = worldDao;
	}

	@Override
	public void handle(Buffer buffer) {
		Request request = null;
		
		switch (session.getState()) {
		
		case HANDSHAKE:
			request = new HandshakeRequest();
			session.switchState(State.LOGIN);
			break;
		case LOGIN:
			request = new LoginRequest(worldDao, session);
			session.switchState(State.GAME);
			break;
		case GAME:
			request = new GameRequest(worldDao, session);
			break;
		}
		
		if (request != null) {
			BufferWriter writer = request.handle(new BufferReader(buffer.getBytes()));
			session.write(writer);
		}
	}

}
