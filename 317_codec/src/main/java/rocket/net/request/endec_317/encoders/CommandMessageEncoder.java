package rocket.net.request.endec_317.encoders;

import rocket.net.io.BufferWriter;
import rocket.net.request.endec_317.CommandMessage;
import rocket.net.request.message.MessageEncoder;

public class CommandMessageEncoder extends MessageEncoder<CommandMessage> {

	@Override
	public BufferWriter encode(CommandMessage message) {
		BufferWriter writer = new BufferWriter();
		return writer;
	}

}
