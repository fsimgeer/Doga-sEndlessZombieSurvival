package ui.elements;

import doa.engine.maths.DoaVector;

public interface IElement {

	boolean isVisible();
	void setVisible(boolean value);

	void setPosition(DoaVector position);
}
