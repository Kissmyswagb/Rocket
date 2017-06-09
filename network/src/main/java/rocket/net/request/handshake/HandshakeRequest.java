package rocket.net.request.handshake;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;

public class HandshakeRequest implements Request {
	private static final Logger logger = LoggerFactory.getLogger(HandshakeRequest.class);

	@Override
	public BufferWriter handle(BufferReader reader) {
		BufferWriter writer = BufferWriter.writer();
		int request = reader.getUnsignedByte();

		switch (request) {
		case HandshakeConstants.GAME:
			writer.putLong(0);
			writer.putByte(0);
			writer.putLong(new SecureRandom().nextLong());
			break;
		case HandshakeConstants.UPDATE:
			break;
		default:
			logger.warn("Unhandled handshake request: {}", request);
			break;
		}

		return writer;
	}
}
