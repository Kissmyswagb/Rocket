package rocket.net.request.message;

import rocket.net.io.BufferWriter;

public abstract class MessageEncoder<T extends Message> {
	public abstract BufferWriter encode(T message);
}
