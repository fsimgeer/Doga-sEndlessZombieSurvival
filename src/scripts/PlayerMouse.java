package scripts;

import doa.engine.core.DoaCamera;
import doa.engine.graphics.DoaSprites;
import doa.engine.input.DoaMouse;
import doa.engine.maths.DoaMath;
import doa.engine.maths.DoaVector;
import doa.engine.scene.elements.DoaTransform;
import doa.engine.scene.elements.renderers.DoaSpriteRenderer;
import doa.engine.scene.elements.scripts.DoaMouseAdapter;
import objects.Player;

public class PlayerMouse extends DoaMouseAdapter {

	private static final long serialVersionUID = 1732779016300506422L;

	private DoaVector playerPos;
	private DoaVector mousePos;

	private DoaVector toMouse;

	@Override
	public void tick() {
		super.tick();

		playerPos = getOwner().transform.position;
		mousePos = new DoaVector(DoaCamera.getX() + DoaMouse.X, DoaCamera.getY() + DoaMouse.Y);

		toMouse = DoaVector.sub(mousePos, playerPos);

		DoaTransform t = getOwner().rigidBody.getTransform();
		t.rotation = DoaMath.toDegress((float) Math.atan2(toMouse.y, toMouse.x));
		getOwner().rigidBody.setTransform(t);
	}

	@Override
	public void onMouse1Click() {
		DoaSpriteRenderer r = (DoaSpriteRenderer) getOwner().getComponentByType(DoaSpriteRenderer.class).get();
		r.setSprite(DoaSprites.getSprite("PlayerSprite"));
	}

	@Override
	public void onMouse1Hold() { ((Player) getOwner()).data.getWeapon().fire(playerPos, toMouse); }

	@Override
	public void onMouse1Release() {
		DoaSpriteRenderer r = (DoaSpriteRenderer) getOwner().getComponentByType(DoaSpriteRenderer.class).get();
		r.setSprite(DoaSprites.getSprite("PlayerSprite2"));
	}

}
