package rocket.net.request.endec_317.handlers;

import rocket.game.player.PlayerProxy;
import rocket.net.io.BufferWriter;
import rocket.net.io.Conversion;
import rocket.net.io.Endian;
import rocket.net.request.endec_317.CommandMessage;
import rocket.net.request.message.MessageHandler;

public class CommandMessageHandler extends MessageHandler<CommandMessage> {
	static boolean sent = false;
	@Override
	public void handle(PlayerProxy proxy, CommandMessage message) {
		System.out.println("Received command: " + message.getCommand());
		
		BufferWriter writer = new BufferWriter();
		
		if (!sent) {
			writer.put(249); //opcode
			writer.put(1, Conversion.ADD);
			writer.putShort(0, Conversion.ADD, Endian.BIG);
			sent = true;
		} else {
			writer.put(70); //opcode
			writer.putShort(147, Conversion.NONE, Endian.BIG);
			writer.put(12, Conversion.ADD);
		}
		
		proxy.getSession().write(writer);
	}
	
}
