package rocket.net.request.message;

import java.util.HashMap;
import java.util.Map;

import rocket.net.request.message.endec.ChatMessage;
import rocket.net.request.message.endec.ChatMessageDecoder;
import rocket.net.request.message.endec.ChatMessageHandler;
import rocket.net.request.message.endec.CommandMessage;
import rocket.net.request.message.endec.CommandMessageDecoder;
import rocket.net.request.message.endec.CommandMessageEncoder;
import rocket.net.request.message.endec.CommandMessageHandler;

public class MessageRepo {
	class Opcode {
		private static final int CHAT = 4;
		private static final int COMMAND = 103;
	}

	private static Map<Class<?>, MessageEncoder<?>> encoders = new HashMap<>();
	private static Map<Integer, MessageDecoder<?>> decoders = new HashMap<>();
	private static Map<Class<?>, MessageHandler<?>> handlers = new HashMap<>();
	static {
		/* Encoders */
		encoders.put(CommandMessage.class, new CommandMessageEncoder());

		/* Decoders */
		decoders.put(Opcode.CHAT, new ChatMessageDecoder());
		decoders.put(Opcode.COMMAND, new CommandMessageDecoder());

		/* Handlers */
		handlers.put(ChatMessage.class, new ChatMessageHandler());
		handlers.put(CommandMessage.class, new CommandMessageHandler());
	}

	public static MessageEncoder<?> encode(Message message) {
		return encoders.get(message.getClass());
	}

	public static MessageDecoder<?> getDecoder(int opcode) {
		return decoders.get(opcode);
	}

	public static MessageHandler<?> getHandler(Message message) {
		return handlers.get(message.getClass());
	}
}
