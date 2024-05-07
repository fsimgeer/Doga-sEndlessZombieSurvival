package ui.elements;

public interface Observable {

	void registerObserver(Observer o);
	void notifyObservers();
}