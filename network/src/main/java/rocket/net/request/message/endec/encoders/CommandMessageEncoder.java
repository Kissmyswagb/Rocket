package rocket.net.request.message.endec.encoders;

import rocket.net.io.BufferWriter;
import rocket.net.request.message.MessageEncoder;
import rocket.net.request.message.endec.CommandMessage;

public class CommandMessageEncoder extends MessageEncoder<CommandMessage> {

	@Override
	public BufferWriter encode(CommandMessage message) {
		return BufferWriter.writer();
	}

}
