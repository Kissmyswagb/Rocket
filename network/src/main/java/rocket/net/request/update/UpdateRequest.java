package rocket.net.request.update;

import java.io.IOException;
import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import net.scapeemulator.cache.Cache;
import net.scapeemulator.cache.Container;
import net.scapeemulator.cache.FileStore;
import rocket.net.SessionProxy;
import rocket.net.io.BufferReader;
import rocket.net.io.BufferWriter;
import rocket.net.request.Request;

public class UpdateRequest implements Request {
	private static final int NORMAL_FILE_REQUEST = 0;
	private static final int PRIORITY_FILE_REQUEST = 1;
	private static final int PLAYER_LOGGED_IN = 2;
	private static final int PLAYER_NOT_LOGGED_IN = 3;
	private static final int CONNECTION_INITILIZED = 6;
	
	private SessionProxy session;
	private Cache cache;
	
	public UpdateRequest(SessionProxy session) {
		this.session = session;
		try {
			cache = new Cache(FileStore.open("./data/cache/" + session.getVersion()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public BufferWriter handle(BufferReader reader) {
		BufferWriter writer = new BufferWriter();
		
		int opcode = reader.getUnsignedByte();
		switch(opcode) {
		case CONNECTION_INITILIZED:
		case PLAYER_LOGGED_IN:
		case PLAYER_NOT_LOGGED_IN:
			break;
			
		case NORMAL_FILE_REQUEST:
		case PRIORITY_FILE_REQUEST:
			int type = reader.getUnsignedByte();
			int file = reader.getUnsignedShort();
		}
		
		return writer;
	}

}
