package rocket.net.request;

import io.vertx.core.buffer.Buffer;

public interface Request {
	public Buffer handle(Buffer buffer);
}
