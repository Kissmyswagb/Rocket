package rocket.net;

import rocket.crypto.ISAACCipher;
import rocket.net.io.BufferWriter;

public class SessionProxy {
	private State state = State.HANDSHAKE;
	private ISAACCipher inCipher, outCipher;
	private Session session;
	private int version;
	private int world;
	
	public SessionProxy(Session session) {
		this.session = session;
	}
	
	public void write(BufferWriter writer) {
		session.write(writer);
	}
	
	public void close() {
		session.close();
	}
	
	public void acceptKeys(long serverSessionKey, long clientSessionKey) {
		int[] seed = { 
			(int) (clientSessionKey >> 32),
			(int) clientSessionKey,
			(int) (serverSessionKey >> 32), 
			(int) serverSessionKey 
		};
		ISAACCipher inCipher = new ISAACCipher(seed);
		for (int i = 0; i < seed.length; i++) {
			seed[i] += 50;
		}
		ISAACCipher outCipher = new ISAACCipher(seed);
		this.inCipher = inCipher;
		this.outCipher = outCipher;
	}
	
	public void switchState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
	
	public ISAACCipher getInCipher() {
		return inCipher;
	}
	
	public ISAACCipher getOutCipher() {
		return outCipher;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public void setWorld(int world) {
		this.world = world;
	}
	public int getWorld() {
		return world;
	}
	
	public Session getSession() {
		return session;
	}
}
