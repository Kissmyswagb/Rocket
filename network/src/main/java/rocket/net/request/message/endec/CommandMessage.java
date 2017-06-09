package rocket.net.request.message.endec;

import rocket.net.request.message.Message;

public class CommandMessage extends Message {
	private final String command;
	
	public CommandMessage(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
}
