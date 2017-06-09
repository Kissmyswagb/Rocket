package rocket.game.message;

import rocket.game.player.PlayerProxy;
import rocket.net.request.message.MessageHandler;
import rocket.net.request.message.endec.CommandMessage;

public class CommandMessageHandler extends MessageHandler<CommandMessage> {

	@Override
	public void handle(PlayerProxy playerProxy, CommandMessage message) {
		System.out.println("Received command: " + message.getCommand());
		
		
	}
	
}
