package rocket.net.request.message;

import rocket.net.io.BufferReader;

public abstract class MessageDecoder <T extends Message> {
	public abstract T decode(BufferReader reader);
}
