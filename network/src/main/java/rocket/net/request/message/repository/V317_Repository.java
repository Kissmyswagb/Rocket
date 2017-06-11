package rocket.net.request.message.repository;

import rocket.net.request.endec_317.ChatMessage;
import rocket.net.request.endec_317.CommandMessage;
import rocket.net.request.endec_317.decoders.ChatMessageDecoder;
import rocket.net.request.endec_317.decoders.CommandMessageDecoder;
import rocket.net.request.endec_317.encoders.CommandMessageEncoder;
import rocket.net.request.endec_317.handlers.ChatMessageHandler;
import rocket.net.request.endec_317.handlers.CommandMessageHandler;

public class V317_Repository extends MessageRepository {
	class Opcode {
		private static final int CHAT = 4;
		private static final int COMMAND = 103;
	}
	
	public V317_Repository() {
		super();
	}
	
	@Override
	public void bindEncoders() {
		addMessageEncoder(CommandMessage.class, new CommandMessageEncoder());
	}
	
	@Override
	public void bindDecoders() {
		addMessagDecoder(Opcode.CHAT, new ChatMessageDecoder());
		addMessagDecoder(Opcode.COMMAND, new CommandMessageDecoder());
	}
	
	@Override
	public void bindHandlers() {
		addMessageHandler(ChatMessage.class, new ChatMessageHandler());
		addMessageHandler(CommandMessage.class, new CommandMessageHandler());
	}
}
