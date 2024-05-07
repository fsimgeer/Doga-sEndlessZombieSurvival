package scripts;

import static doa.engine.core.DoaGraphicsFunctions.drawLine;
import static doa.engine.core.DoaGraphicsFunctions.setColor;

import java.awt.Color;

import doa.engine.maths.DoaMath;
import doa.engine.maths.DoaVector;
import doa.engine.scene.elements.DoaTransform;
import doa.engine.scene.elements.scripts.DoaScript;
import objects.Player;

public class EnemyChasePlayer extends DoaScript {

	private static final long serialVersionUID = 3324554374332147376L;

	public float speed = 3;
	public float dt = 0.01f;
	public float randomRotationMax = 30;
	public DoaVector forward = new DoaVector(1, 0); // sprite is facing right

	public float size;

	@Override
	public void tick() {
		float deltaRotation = calculateDeltaRotation();
		// make sure rotation is between -180 and 180. without this zombie will flip
		// around.
		while (deltaRotation <= -180) {
			deltaRotation += 360;
		}
		while (deltaRotation >= 180) {
			deltaRotation -= 360;
		}
		deltaRotation += DoaMath.randomBetween(-randomRotationMax, randomRotationMax);

		float drdt = deltaRotation * dt;

		forward = DoaVector.rotateAroundOrigin(forward, drdt);

		DoaTransform current = getOwner().rigidBody.getTransform();
		current.rotation = DoaMath.toDegress((float) Math.atan2(forward.y, forward.x));
		getOwner().rigidBody.setTransform(current);
		getOwner().rigidBody.setLinearVelocity(DoaVector.mul(forward, speed));
	}

	@Override
	public void debugRender() {
		DoaVector f = new DoaVector(100, 0);
		setColor(Color.RED);
		drawLine(0, 0, f.x, f.y);
	}

	private float calculateDeltaRotation() {
		DoaVector playerCenter = Player.getInstance().transform.position;
		DoaVector center = getOwner().transform.position;

		DoaVector toPlayer = DoaVector.sub(playerCenter, center);
		toPlayer = DoaVector.normalise(toPlayer);

		float currentRotation = getOwner().transform.rotation;
		float targetRotation = DoaMath.toDegress((float) Math.atan2(toPlayer.y, toPlayer.x));

		return targetRotation - currentRotation;
	}
}
