package rocket.net.request.message;

import java.util.HashMap;
import java.util.Map;

import rocket.net.request.message.repository.MessageRepository;
import rocket.net.request.message.repository.V317_Repository;
import rocket.net.request.message.repository.V550_Repository;

public class MessageRepositoryFactory {
	private static Map<Integer, MessageRepository> repositories = new HashMap<>();
	static {
		repositories.put(317, new V317_Repository());
		repositories.put(550, new V550_Repository());
	}
	
	public static MessageRepository getMessageRepo(int version) {
		return repositories.get(version);
	}
	
	public static boolean isSupportedVersion(int version) {
		return getMessageRepo(version) != null;
	}
}
