package rocket.net.request.handshake;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rocket.net.SessionProxy;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;
import rocket.net.request.message.MessageRepositoryFactory;

public class HandshakeRequest implements Request {
	private static final Logger logger = LoggerFactory.getLogger(HandshakeRequest.class);
	private static final int VERSION_OK = 0;
	private static final int VERSION_OUT_OF_DATE = 6;
	
	private SessionProxy session;
	
	public HandshakeRequest(SessionProxy session) {
		this.session = session;
	}
	
	@Override
	public BufferWriter handle(BufferReader reader) {
		BufferWriter writer = new BufferWriter();
		int request = reader.getUnsignedByte();

		switch (request) {
		case HandshakeConstants.GAME:
			writer.putLong(0);
			writer.put(0);
			writer.putLong(new SecureRandom().nextLong());
			break;
		case HandshakeConstants.UPDATE:
			int version = reader.getUnsignedInt();
			if (MessageRepositoryFactory.isSupportedVersion(version)) {
				session.setVersion(version);
				writer.put(VERSION_OK);
			} else {
				writer.put(VERSION_OUT_OF_DATE);
			}
			break;
		default:
			logger.warn("Unhandled handshake request {}", request);
			break;
		}

		return writer;
	}
}
