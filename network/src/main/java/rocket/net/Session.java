package rocket.net;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import rocket.net.io.BufferWriter;

public class Session {
	private NetSocket socket;
	
	public Session(NetSocket socket) {
		this.socket = socket;
	}
	
	public void write(BufferWriter writer) {
		write(writer.getBuffer());
	}

	private void write(Buffer buffer) {
		socket.write(buffer);
	}
	
	public void close() {
		socket.close();
	}
}
