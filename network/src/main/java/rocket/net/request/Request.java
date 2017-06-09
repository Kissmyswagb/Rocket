package rocket.net.request;

import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;

public interface Request {
	public BufferWriter handle(BufferReader reader);
}
