package ui.elements;

import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.setColor;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import doa.engine.input.DoaMouse;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.renderers.DoaRenderer;
import doa.engine.scene.elements.scripts.DoaScript;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public final class Button extends DoaObject implements IInteractableElement{

	private static final long serialVersionUID = -4350117124454796912L;

	@Getter
	@Setter
	private boolean isVisible;

	@Getter
	@Setter
	private boolean isEnabled = true;

	@Getter
	@Setter
	private String textKey;
	
	@Getter
	@Setter
	private Rectangle contentArea;

	@Setter
	private transient IAction action = null;

	@Builder
	Button(@NonNull String textKey, @NonNull Rectangle contentArea, @NonNull IAction action) {
		this.textKey = textKey;
		this.contentArea = contentArea;
		this.action = action;

		addComponent(new Script());
		addComponent(new Renderer());
		
		makeStatic();
	}

	@Override
	public void setPosition(DoaVector position) {
		transform.position.x = position.x;
		transform.position.y = position.y;
	}

	private final class Script extends DoaScript {

		private static final long serialVersionUID = -3597391748518840696L;

		@Override
		public void tick() {
			if (!isVisible() || !isEnabled) { return; }

			int mouseX = (int) DoaMouse.X;
			int mouseY = (int) DoaMouse.Y;
			if (contentArea.contains(new Point(mouseX, mouseY))) {
				if(DoaMouse.MB1_RELEASE) {
					action.execute(Button.this);
				}
			}
		}
	}

	private final class Renderer extends DoaRenderer {

		private static final long serialVersionUID = -8911543710080168324L;

		@Override
		public void render() {
			if(!isVisible()) { return; }

			setColor(Color.YELLOW.darker());
			fill(contentArea);
			setColor(Color.BLACK);
			drawString("CLOSE", contentArea.x, contentArea.y + contentArea.height * 3f / 4f);
		}
	}

}
