package scripts;

import doa.engine.maths.DoaMath;
import doa.engine.maths.DoaVector;
import doa.engine.scene.elements.DoaTransform;
import doa.engine.scene.elements.physics.DoaRigidBody;
import doa.engine.scene.elements.scripts.DoaScript;

public class BulletRotationFix extends DoaScript {

	private static final long serialVersionUID = 5534354332898727679L;

	@Override
	public void tick() {
		DoaRigidBody rb = getOwner().rigidBody;
		DoaTransform t = rb.getTransform();
		DoaVector velocity = rb.getLinearVelocity();
		t.rotation = DoaMath.toDegress((float) Math.atan2(velocity.y, velocity.x)) + 90;
		rb.setTransform(t);
	}

}
