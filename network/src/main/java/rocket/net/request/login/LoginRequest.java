package rocket.net.request.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rocket.game.dao.WorldDao;
import rocket.net.SessionProxy;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;
import rocket.net.request.login_317.LoginHandler317;
import rocket.net.request.login_550.LoginHandler550;

public class LoginRequest implements Request {
	private static final Logger logger = LoggerFactory.getLogger(LoginRequest.class);
	private SessionProxy session;
	private WorldDao worlds;

	public LoginRequest(WorldDao worlds, SessionProxy session) {
		this.session = session;
		this.worlds = worlds;
	}

	@Override
	public BufferWriter handle(BufferReader reader) {
		BufferWriter writer = new BufferWriter();
		
		int version = session.getVersion();
		if (version == 0) version = 317;
		
		switch (version) {
		case 317:
			writer.clone(new LoginHandler317(worlds, session).handle(reader));
		case 550:
			writer.clone(new LoginHandler550(worlds, session).handle(reader));
			break;
		default:
			logger.warn("Unhandled version {}", version);
			break;
		}
		
		return writer;
		
	}
}