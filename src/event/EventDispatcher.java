package event;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcher {

	private List<IEventListener> listeners = new ArrayList<>();

	public void RegisterListener(final IEventListener listener) {
		listeners.add(listener);
	}

	public void DispatchEvent(final IEvent event) {
		for (IEventListener listener : listeners) {
			listener.onEventReceived(event);
		}
	}
}
