package renderers;

import static doa.engine.core.DoaGraphicsFunctions.popTransform;
import static doa.engine.core.DoaGraphicsFunctions.pushTransform;
import static doa.engine.core.DoaGraphicsFunctions.translate;

import doa.engine.scene.elements.renderers.DoaSpriteRenderer;

public class EnemyRenderer extends DoaSpriteRenderer {

	private static final long serialVersionUID = 3589837904184564944L;

	@Override
	public void render() {
		pushTransform();
		translate(-getDimensions().x / 2, -getDimensions().y / 2);
		super.render();
		popTransform();
	}
}
