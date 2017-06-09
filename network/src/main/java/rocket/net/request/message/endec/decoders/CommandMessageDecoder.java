package rocket.net.request.message.endec.decoders;

import rocket.net.io.BufferReader;
import rocket.net.request.message.MessageDecoder;
import rocket.net.request.message.endec.CommandMessage;

public class CommandMessageDecoder extends MessageDecoder<CommandMessage> {

	@Override
	public CommandMessage decode(BufferReader reader) {
		String command = reader.getString();
		return new CommandMessage(command);
	}

}
