package rocket.net;

import io.vertx.core.buffer.Buffer;
import rocket.game.dao.WorldDao;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;
import rocket.net.request.game.GameRequest;
import rocket.net.request.handshake.HandshakeRequest;
import rocket.net.request.login.LoginRequest;
import rocket.net.request.update.UpdateRequest;

public class Handler implements io.vertx.core.Handler<Buffer> {
	private SessionProxy session;
	private WorldDao worldDao;
	
	public Handler(WorldDao worldDao, SessionProxy session) {
		this.session = session;
		this.worldDao = worldDao;
	}

	@Override
	public void handle(Buffer buffer) {
		Request request = null;
		
		switch (session.getState()) {
		
		case HANDSHAKE:
			request = new HandshakeRequest(session);
			session.switchState(State.UPDATE);
			break;
		case UPDATE:
			request = new UpdateRequest(session);
			break;
		case LOGIN:
			request = new LoginRequest(worldDao, session);
			session.switchState(State.GAME);
			break;
		case GAME:
			request = new GameRequest(worldDao, session);
			break;
		}
		
		BufferWriter writer = request.handle(new BufferReader(buffer.getBytes()));
		session.write(writer);
	}

}
