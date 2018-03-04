package calculator;

class OnlineStatusFake implements OnlineStatus {
	private boolean online = true;

	public void offline() {
		powerOff();
	}

	public void online() {
		online = true;
	}

	@Override
	public boolean isOnline() {
		return online;
	}

	@Override
	public void powerOff() {
		online = false;
	}
}
