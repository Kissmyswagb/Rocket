package rocket.net.request.endec_317.handlers;

import rocket.game.player.PlayerProxy;
import rocket.net.request.endec_317.ChatMessage;
import rocket.net.request.message.MessageHandler;

public class ChatMessageHandler extends MessageHandler<ChatMessage> {
	/* Colours */
	private static final int YELLOW = 0;
	private static final int RED = 1;
	private static final int GREEN = 2;
	private static final int CYAN = 3;
	private static final int PURPLE = 4;
	private static final int WHITE = 5;
	private static final int FLASH_1 = 6;
	private static final int FLASH_2 = 7;
	private static final int FLASH_3 = 8;
	private static final int GLOW_1 = 9;
	private static final int GLOW_2 = 10;
	private static final int GLOW_3 = 11;

	/* Effects */
	private static final int WAVE = 1;
	private static final int WAVE_2 = 2;
	private static final int SHAKE = 3;
	private static final int SCROLL = 4;
	private static final int SLIDE = 5;
	
	@Override
	public void handle(PlayerProxy playerProxy, ChatMessage message) {
		System.out.println("Chat : " + message.getColor() + ", " + message.getEffects() + ", " + message.getText());
	}

}
