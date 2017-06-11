package rocket.net.request.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rocket.game.dao.WorldDao;
import rocket.game.player.PlayerProxy;
import rocket.net.SessionProxy;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;
import rocket.net.request.message.Message;
import rocket.net.request.message.MessageDecoder;
import rocket.net.request.message.MessageEncoder;
import rocket.net.request.message.MessageRepositoryFactory;
import rocket.net.request.message.MessageHandler;

public class GameRequest implements Request {
	private static final Logger logger = LoggerFactory.getLogger(GameRequest.class);
	private SessionProxy session;
	private WorldDao worlds;
	
	public GameRequest(WorldDao worlds, SessionProxy session) {
		this.session = session;
		this.worlds = worlds;
	}

	@Override
	public BufferWriter handle(BufferReader reader) {
		BufferWriter writer = new BufferWriter();
		
		int opcode = getOpcode(reader);
		int size = getSize(reader);
		
		MessageDecoder decoder = getMessageDecoder(opcode);
		
		if (decoder != null) {
			
			Message message = getDecodedMessage(decoder, reader);
			MessageHandler handler = getMessageHandler(message);
			PlayerProxy player = worlds.locatePlayerBySession(session);
			
			if (handler != null) {
				MessageEncoder encoder = getMessageEncoder(message);
				handler.handle(player, message);
				
				if (encoder != null) {
					writer.clone(encoder.encode(message));
				}
				
			} else {
				logger.warn("No handler set for {}", message.getClass());
			}

		} else {
			logger.warn("Unhandled opcode: {}", opcode);
		}
		
		return writer;
	}

	private Message getDecodedMessage(MessageDecoder<?> decoder, BufferReader reader) {
		return decoder.decode(reader);
	}
	
	private MessageDecoder<?> getMessageDecoder(int opcode) {
		return MessageRepositoryFactory.getMessageRepo(session.getVersion()).getDecoder(opcode);
	}
	
	private MessageEncoder<?> getMessageEncoder(Message message) {
		return MessageRepositoryFactory.getMessageRepo(session.getVersion()).getEncoder(message);
	}

	private MessageHandler<?> getMessageHandler(Message message) {
		return MessageRepositoryFactory.getMessageRepo(session.getVersion()).getHandler(message);
	}

	private int getOpcode(BufferReader reader) {
		int opcode = -1;
		if (reader.getLength() >= 1) {
			opcode = reader.getUnsignedByte() & 0xFF;
			opcode = (opcode - session.getInCipher().getNextValue()) & 0xFF;
		}
		return opcode;
	}

	private int getSize(BufferReader reader) {
		int size = -1;
		if (reader.getLength() >= 1) {
			size = reader.getUnsignedByte() & 0xFF;
		}
		return size;
	}
}
