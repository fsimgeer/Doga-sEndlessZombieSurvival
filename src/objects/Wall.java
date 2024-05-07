package objects;

import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.physics.DoaBodyType;
import doa.engine.scene.elements.physics.DoaBoxCollider;
import doa.engine.scene.elements.physics.DoaRigidBody;
import globals.Layers;

public class Wall extends DoaObject {

	private static final long serialVersionUID = -1823772727435109215L;

	public Wall(final float x, final float y, final float width, final float height) {
		super("Wall");
		transform.position.x = x;
		transform.position.y = y;
		setzOrder(10);

		DoaRigidBody b = new DoaRigidBody();
		b.type = DoaBodyType.STATIC;
		b.colliders.add(new DoaBoxCollider(new DoaVector(width, height), new DoaVector(width / 2, height / 2)));
		b.mass = 100;
		b.debugRender = true;
		addComponent(b);

		setzOrder(Layers.WALL);
	}

}
