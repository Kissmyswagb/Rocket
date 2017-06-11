package rocket.net.request.message.repository;

import java.util.HashMap;
import java.util.Map;

import rocket.net.request.message.Message;
import rocket.net.request.message.MessageDecoder;
import rocket.net.request.message.MessageEncoder;
import rocket.net.request.message.MessageHandler;

public abstract class MessageRepository {
	private Map<Class<?>, MessageEncoder<?>> encoders = new HashMap<>();
	private Map<Integer, MessageDecoder<?>> decoders = new HashMap<>();
	private Map<Class<?>, MessageHandler<?>> handlers = new HashMap<>();

	public abstract void bindEncoders();
	public abstract void bindDecoders();
	public abstract void bindHandlers();
	
	public MessageRepository() {
		bindEncoders();
		bindDecoders();
		bindHandlers();
	}
	
	public void addMessageEncoder(Class<?> message, MessageEncoder<?> encoder) {
		encoders.put(message, encoder);
	}
	
	public void addMessagDecoder(int opcode, MessageDecoder<?> decoder) {
		decoders.put(opcode, decoder);
	}
	
	public void addMessageHandler(Class<?> message, MessageHandler<?> handler) {
		handlers.put(message, handler);
	}
	
	public MessageEncoder<?> getEncoder(Message message) {
		return encoders.get(message.getClass());
	}

	public MessageDecoder<?> getDecoder(int opcode) {
		return decoders.get(opcode);
	}

	public MessageHandler<?> getHandler(Message message) {
		return handlers.get(message.getClass());
	}
}
