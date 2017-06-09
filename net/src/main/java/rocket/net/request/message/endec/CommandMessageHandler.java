package rocket.net.request.message.endec;

import rocket.game.player.PlayerProxy;
import rocket.net.request.message.MessageHandler;

public class CommandMessageHandler extends MessageHandler<CommandMessage> {

	@Override
	public void handle(PlayerProxy playerProxy, CommandMessage message) {
		System.out.println("Received command: " + message.getCommand());
		
		
	}
	
}
