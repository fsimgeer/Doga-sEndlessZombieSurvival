package event;

import components.PlayerData;

public class PlayerDataChanged implements IEvent {

	private PlayerData data;

	public PlayerDataChanged(PlayerData data) {
		this.data = data;
	}

	@Override
	public Object getEventData() { return data; }
}
