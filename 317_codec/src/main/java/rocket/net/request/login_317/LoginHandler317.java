package rocket.net.request.login_317;

import rocket.game.dao.WorldDao;
import rocket.game.player.PlayerProxy;
import rocket.net.SessionProxy;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.login.LoginHandler;
import rocket.net.request.login.LoginResponseCodes;

public class LoginHandler317 implements LoginHandler {
	private static final int LOGIN_REQUEST = 16;
	private static final int RECONNECTING = 18;
	private static final int MAGIC = 255;
	private static final int VERSION = 317;
	private static final int RSA = 10;
	private static final int DEFAULT_WORLD = 1;
	
	private SessionProxy session;
	private WorldDao worlds;
	
	public LoginHandler317(WorldDao worlds, SessionProxy session) {
		this.worlds = worlds;
		this.session = session;
	}
	
	@Override
	public BufferWriter handle(BufferReader reader) {
		BufferWriter writer = new BufferWriter();
		
		int connectionStatus = reader.getUnsignedByte();
		int size = reader.getSignedByte();
		int magic = reader.getUnsignedByte();
		int version = reader.getUnsignedShort();
		int client = reader.getUnsignedByte();

		for (int i = 0; i < 9; i++) {
			reader.getUnsignedInt();
		}

		int unknown = reader.getUnsignedByte();
		int rsa = reader.getUnsignedByte();

		if (!validRequest(connectionStatus)) {
			sendResponseCode(LoginResponseCodes.SESSION_REJECTED);
			throw new IllegalArgumentException(
					"Invalid request type: " + connectionStatus + ", excepted: " + LOGIN_REQUEST);
		}
		if (!validSize(reader.getLength(), size)) {
			sendResponseCode(LoginResponseCodes.SESSION_REJECTED);
			throw new IllegalArgumentException("Buffer size is " + reader.getLength() + " however expected: " + size);
		}
		if (!validMagic(magic)) {
			sendResponseCode(LoginResponseCodes.SESSION_REJECTED);
			throw new IllegalArgumentException(
					"Magic value is " + magic + " however expected: " + MAGIC);
		}
		if (!validVersion(version)) {
			sendResponseCode(LoginResponseCodes.GAME_UPDATED);
			throw new IllegalArgumentException(
					"Invalid client version: " + version + ", expected: " + VERSION);
		}
		if (!validRSA(rsa)) {
			throw new IllegalArgumentException("Invalid RSA opcode: " + rsa + ", expected: " + RSA);
		}

		long clientSessionKey = reader.getL();
		long serverSessionKey = reader.getL();
		int userId = reader.getUnsignedInt();
		int world = DEFAULT_WORLD;

		String username = reader.getString();
		char[] password = reader.getString().toCharArray();

		session.setVersion(version);
		session.setWorld(world);
		session.acceptKeys(serverSessionKey, clientSessionKey);

		PlayerProxy proxy = PlayerProxy.create(username, session);
		proxy.registerToWorld(worlds.get(world));

		sendResponseCode(LoginResponseCodes.SUCCESS);
		writer.put(2);
		writer.put(0);

		return writer;
	}
	
	private void sendResponseCode(int id) {
		session.write(new BufferWriter().put(id));
	}

	private boolean validRequest(int type) {
		return type == LOGIN_REQUEST || type == RECONNECTING;
	}

	private boolean validSize(int bufferSize, int size) {
		return bufferSize >= size;
	}

	private boolean validMagic(int magic) {
		return magic == MAGIC;
	}

	private boolean validVersion(int version) {
		return version == VERSION;
	}

	private boolean validRSA(int rsa) {
		return rsa == RSA;
	}
}
