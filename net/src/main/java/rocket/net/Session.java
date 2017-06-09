package rocket.net;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import rocket.crypto.ISAACCipher;
import rocket.net.io.BufferWriter;
import rocket.net.request.message.Message;
import rocket.net.request.message.MessageEncoder;
import rocket.net.request.message.MessageRepo;

public class Session {
	public enum State {
		HANDSHAKE, LOGIN, GAME;
	}
	private State state = State.HANDSHAKE;
	private NetSocket socket;
	
	private int world;
	public void setWorld(int world) {
		this.world = world;
	}
	public int getWorld() {
		return world;
	}
	
	public Session(NetSocket socket) {
		this.socket = socket;
	}
	
	private ISAACCipher inCipher;
	private ISAACCipher outCipher;
	
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

	public ISAACCipher getInCipher() {
		return inCipher;
	}
	
	public ISAACCipher getOutCipher() {
		return outCipher;
	}
	
	public <T extends Message> void write(T message) {
		MessageEncoder encoded = MessageRepo.encode(message);
		BufferWriter writer = encoded.encode(message);
		write(writer.getBuffer());
	}

	public void write(Buffer buffer) {
		socket.write(buffer);
	}
	
	public void close() {
		socket.close();
	}
	
	public void switchState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
}
