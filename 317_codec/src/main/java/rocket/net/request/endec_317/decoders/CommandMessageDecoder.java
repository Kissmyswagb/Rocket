package rocket.net.request.endec_317.decoders;

import rocket.net.io.BufferReader;
import rocket.net.request.endec_317.CommandMessage;
import rocket.net.request.message.MessageDecoder;

public class CommandMessageDecoder extends MessageDecoder<CommandMessage> {

	@Override
	public CommandMessage decode(BufferReader reader) {
		String command = reader.getString();
		return new CommandMessage(command);
	}

}
