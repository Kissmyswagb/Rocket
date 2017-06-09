package rocket.net.request.message.endec;

import rocket.net.io.BufferWriter;
import rocket.net.request.message.MessageEncoder;

public class CommandMessageEncoder extends MessageEncoder<CommandMessage> {

	@Override
	public BufferWriter encode(CommandMessage message) {
		BufferWriter writer = new BufferWriter(71);
		
		
		
		return writer;
	}

}
