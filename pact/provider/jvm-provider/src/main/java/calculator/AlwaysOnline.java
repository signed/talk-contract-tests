package calculator;

class AlwaysOnline implements OnlineStatus {
	@Override
	public boolean isOnline() {
		return true;
	}
}
