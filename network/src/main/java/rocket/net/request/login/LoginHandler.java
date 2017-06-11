package rocket.net.request.login;

import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;

public interface LoginHandler {
	public BufferWriter handle(BufferReader reader);
}
