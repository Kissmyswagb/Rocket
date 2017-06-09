package rocket.net.request.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.buffer.Buffer;
import rocket.game.player.PlayerProxy;
import rocket.net.Session;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;

public class LoginRequest implements Request {
	private static final Logger logger = LoggerFactory.getLogger(LoginRequest.class);
	private static final int DEFAULT_WORLD = 1;
	
	private Session session;
	
	public LoginRequest(Session session) {
		this.session = session;
	}
	
	@Override
	public Buffer handle(Buffer buffer) {
		BufferReader reader = new BufferReader(buffer.getBytes());
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
			throw new IllegalArgumentException("Invalid request type: " + connectionStatus + ", excepted: " + LoginConstants.LOGIN_REQUEST);	
		}
		if (!validSize(buffer.length(), size)) {
			sendResponseCode(LoginResponseCodes.SESSION_REJECTED);
			throw new IllegalArgumentException("Buffer size is " + buffer.length()+ " however expected: " + size);
		}
		if (!validMagic(magic)) {
			sendResponseCode(LoginResponseCodes.SESSION_REJECTED);
			throw new IllegalArgumentException("Magic value is " + magic+ " however expected: " + LoginConstants.MAGIC);
		}
		if (!validVersion(version)) {
			sendResponseCode(LoginResponseCodes.GAME_UPDATED);
			throw new IllegalArgumentException("Invalid client version: " + version + ", expected: " + LoginConstants.VERSION);
		}
		if (!validRSA(rsa)) {
			throw new IllegalArgumentException("Invalid RSA opcode: " + rsa + ", expected: " + LoginConstants.RSA);
		}
		
		long clientSessionKey = reader.getL();
		long serverSessionKey = reader.getL();
		int userId = reader.getUnsignedInt();
		int world = DEFAULT_WORLD;

		String username = reader.getString();
		char[] password = reader.getString().toCharArray();
		
		session.setWorld(world);
		session.acceptKeys(serverSessionKey, clientSessionKey);
		
		PlayerProxy proxy = PlayerProxy.createPlayerAndProxy(username, session);
		proxy.register();
		
		sendResponseCode(LoginResponseCodes.SUCCESS);
		writer.putByte(2);
		writer.putByte(0);
		
		return writer.getBuffer();
	}
	
	private void sendResponseCode(int id) {
		session.write(Buffer.buffer().appendByte((byte) id));
	}
	
	private boolean validRequest(int type) {
		return type == LoginConstants.LOGIN_REQUEST || type == LoginConstants.RECONNECTING;
	}
	
	private boolean validSize(int bufferSize, int size) {
		return bufferSize >= size;
	}
	
	private boolean validMagic(int magic) {
		return magic == LoginConstants.MAGIC;
	}
	
	private boolean validVersion(int version) {
		return version == LoginConstants.VERSION;
	}
	
	private boolean validRSA(int rsa) {
		return rsa == LoginConstants.RSA;
	}
}