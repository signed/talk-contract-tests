package calculator;

class DefaultOnlineStatus implements OnlineStatus {
	private boolean online = true;

	@Override
	public boolean isOnline() {
		return online;
	}

	@Override
	public void powerOff() {
		online = false;
	}
}
