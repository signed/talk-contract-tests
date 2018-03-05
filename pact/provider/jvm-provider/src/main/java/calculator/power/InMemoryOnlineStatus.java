package calculator.power;

import calculator.OnlineStatus;

public class InMemoryOnlineStatus implements OnlineStatus {
	private boolean online = true;

	@Override
	public boolean isOnline() {
		return online;
	}

	@Override
	public void powerOff() {
		online = false;
	}

    @Override
    public void powerOn() {
        online = true;
    }
}
