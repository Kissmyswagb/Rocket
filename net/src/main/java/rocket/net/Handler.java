package rocket.net;

import io.vertx.core.buffer.Buffer;
import rocket.net.Session.State;
import rocket.net.request.GameRequest;
import rocket.net.request.HandshakeRequest;
import rocket.net.request.Request;
import rocket.net.request.login.LoginRequest;

public class Handler implements io.vertx.core.Handler<Buffer> {
	private Session session;
	
	public Handler(Session session) {
		this.session = session;
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
			request = new LoginRequest(session);
			session.switchState(State.GAME);
			break;
		case GAME:
			request = new GameRequest(session);
			break;
		}
		
		if (request != null) {
			session.write(request.handle(buffer));
		}
	}

}
