package rocket.net.request.login_550;

import rocket.game.dao.WorldDao;
import rocket.game.player.PlayerProxy;
import rocket.net.SessionProxy;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.login.LoginHandler;
import rocket.net.request.login.LoginResponseCodes;

public class LoginHandler550 implements LoginHandler {
	private static final int LOGIN_REQUEST = 16;
	private static final int RECONNECTING = 18;
	private static final int MAGIC = 255;
	private static final int VERSION = 550;
	private static final int RSA = 10;
	private static final int DEFAULT_WORLD = 2;
	
	private SessionProxy session;
	private WorldDao worlds;
	
	public LoginHandler550(WorldDao worlds, SessionProxy session) {
		this.worlds = worlds;
		this.session = session;
	}
	
	@Override
	public BufferWriter handle(BufferReader reader) {
		BufferWriter writer = new BufferWriter();
		
		
		return writer;
	}
	
	
}
