package calculator;

public interface OnlineStatus {
	boolean isOnline();

	default boolean isOffline() {
		return !isOnline();
	}

	void powerOff();
}
