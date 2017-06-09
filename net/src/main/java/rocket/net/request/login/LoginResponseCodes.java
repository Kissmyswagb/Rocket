package rocket.net.request.login;

public class LoginResponseCodes {
	public static final int SUCCESS = 2;
	public static final int INVALID_USERNAME_PASSWORD = 3;
	public static final int ACCOUNT_DISABLED = 4;
	public static final int ACCOUNT_LOGGED_IN = 5;
	public static final int GAME_UPDATED = 6;
	public static final int WORLD_FULL = 7;
	public static final int LOGIN_SERVER_OFFLINE = 8;
	public static final int LOGIN_LIMIT_EXCEEDED = 9;
	public static final int BAD_SESSION_ID = 10;
	public static final int SESSION_REJECTED = 11;
	public static final int REQUIRE_MEMBERSHIP = 12;
	public static final int SERVER_BEING_UPDATED = 14;
	public static final int PROFILE_TRANSFER = 21;
}
