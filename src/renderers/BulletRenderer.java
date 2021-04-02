package renderers;

import static doa.engine.core.DoaGraphicsFunctions.fillOval;
import static doa.engine.core.DoaGraphicsFunctions.popTransform;
import static doa.engine.core.DoaGraphicsFunctions.pushTransform;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.translate;

import java.awt.Color;

import doa.engine.scene.elements.renderers.DoaRenderer;
import gameplay.weapon.IWeapon;

public class BulletRenderer extends DoaRenderer {

	private static final long serialVersionUID = 8320990180378451467L;

	IWeapon weapon;

	public BulletRenderer(IWeapon weapon) { this.weapon = weapon; }

	@Override
	public void render() {
		pushTransform();
		translate(-weapon.getDimensions().x / 2, -weapon.getDimensions().y / 2);
		Color c = weapon.getBulletColor();
		for (int i = 5; i > 0; i--) {
			Color newC = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha() - (c.getAlpha() / 5 * i));
			setColor(newC);
			fillOval(0, i * 5, weapon.getDimensions().x, weapon.getDimensions().y);
		}
		popTransform();
	}

}
