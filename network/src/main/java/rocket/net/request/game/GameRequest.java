package rocket.net.request.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rocket.game.dao.WorldDao;
import rocket.game.player.PlayerProxy;
import rocket.net.Session;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;
import rocket.net.request.message.Message;
import rocket.net.request.message.MessageDecoder;
import rocket.net.request.message.MessageHandler;
import rocket.net.request.message.MessageRepo;

public class GameRequest implements Request {
	private static final Logger logger = LoggerFactory.getLogger(GameRequest.class);
	private Session session;
	private WorldDao worlds;

	public GameRequest(WorldDao worlds, Session session) {
		this.session = session;
		this.worlds = worlds;
	}

	@Override
	public BufferWriter handle(BufferReader reader) {
		int opcode = getOpcode(reader);
		int size = getSize(reader);

		MessageDecoder<?> decoder = getMessageDecoder(opcode);

		if (decoder != null) {

			Message message = getDecodedMessage(decoder, reader);
			MessageHandler handler = getMessageHandler(message);

			if (handler != null) {

				PlayerProxy player = worlds.locatePlayerBySession(session);
				handler.handle(player, message);

			} else {
				logger.warn("No handler set for {}", message.getClass());
			}

		} else {
			logger.warn("Unhandled opcode: {}", opcode);
		}

		return null;
	}

	private Message getDecodedMessage(MessageDecoder<?> decoder, BufferReader reader) {
		return decoder.decode(reader);
	}

	private MessageDecoder<?> getMessageDecoder(int opcode) {
		return MessageRepo.getDecoder(opcode);
	}

	private MessageHandler<?> getMessageHandler(Message message) {
		return MessageRepo.getHandler(message);
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
