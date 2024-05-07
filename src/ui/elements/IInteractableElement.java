package ui.elements;

public interface IInteractableElement extends IElement{

	void setAction(IAction action);
	boolean isEnabled();
	void setEnabled(boolean value);
}
