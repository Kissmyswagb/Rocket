package rocket.net.request.message;

import rocket.game.player.PlayerProxy;

public abstract class MessageHandler<T extends Message> {
	public abstract void handle(PlayerProxy playerProxy, T message);
}
