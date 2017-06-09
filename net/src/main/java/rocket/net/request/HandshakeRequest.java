package rocket.net.request;

import java.security.SecureRandom;

import io.vertx.core.buffer.Buffer;

public class HandshakeRequest implements Request {

	@Override
	public Buffer handle(Buffer buffer) {
		Buffer out = Buffer.buffer();
		out.appendLong(0);
		out.appendByte((byte) 0);
		out.appendLong(new SecureRandom().nextLong());
		return out;
	}

}
