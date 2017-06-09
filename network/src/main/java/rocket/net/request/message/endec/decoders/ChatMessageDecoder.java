package rocket.net.request.message.endec.decoders;

import rocket.net.io.BufferReader;
import rocket.net.io.Conversion;
import rocket.net.request.message.MessageDecoder;
import rocket.net.request.message.endec.ChatMessage;

public class ChatMessageDecoder extends MessageDecoder<ChatMessage> {

	@Override
	public ChatMessage decode(BufferReader reader) {
		int size = reader.getLength();
		int effect = reader.getSignedByteS();
		int colour = reader.getSignedByteS();
		
		byte[] chat = reader.getBytes(size, Conversion.NONE);
		String s = new String(chat);
		
		return new ChatMessage(colour, effect, s);
	}


}
