package objects;

import static doa.engine.core.DoaGraphicsFunctions.drawLine;
import static doa.engine.core.DoaGraphicsFunctions.setColor;

import java.awt.Color;

import doa.engine.graphics.DoaSprites;
import doa.engine.maths.DoaMath;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.DoaScene;
import doa.engine.scene.elements.DoaTransform;
import doa.engine.scene.elements.physics.DoaBodyType;
import doa.engine.scene.elements.physics.DoaCircleCollider;
import doa.engine.scene.elements.physics.DoaRigidBody;
import doa.engine.scene.elements.renderers.DoaSpriteRenderer;
import doa.engine.scene.elements.scripts.DoaScript;
import event.EnemyDied;
import event.EnemyTargetOverridden;
import event.EventDispatcher;
import event.IEvent;
import event.IEventListener;
import globals.Layers;
import renderers.EnemyRenderer;

public class Enemy extends DoaObject {

	private static final long serialVersionUID = -3839835030750716763L;

	private static final float SIZE = 48;

	public Life Life;

	private EventDispatcher dispatcher;

	public Enemy(EventDispatcher dispatcher, DoaTransform chaseTarget, final float x, final float y) {
		super("Zombie");

		transform.position.x = x;
		transform.position.y = y;

		ChaseTarget cp = new ChaseTarget(chaseTarget);
		cp.enableDebugRender = true;
		cp.size = SIZE;
		addComponent(cp);
		dispatcher.RegisterListener(cp);

		DoaSpriteRenderer r = new EnemyRenderer();
		r.setSprite(DoaSprites.getSprite("EnemySprite"));
		r.setDimensions(new DoaVector(SIZE, SIZE));
		addComponent(r);

		DoaRigidBody b = new DoaRigidBody();
		b.type = DoaBodyType.DYNAMIC;
		b.colliders.add(new DoaCircleCollider(SIZE / 2));
		b.fixedRotation = true;
		b.mass = 10;
		b.enableDebugRender = true;
		addComponent(b);

		Life = new Life(this, 10);
		addComponent(Life);

		this.dispatcher = dispatcher;

		setzOrder(Layers.ENEMY);
	}

	public void TakeDamage(float bulletDamage) { Life.life -= bulletDamage; }

	@Override
	public void onRemoveFromScene(DoaScene scene) {
		super.onRemoveFromScene(scene);
		dispatcher.DispatchEvent(new EnemyDied());
	}

	public class Life extends DoaScript {

		private static final long serialVersionUID = 7559679489591744740L;

		private Enemy owner;
		private int life;

		public Life(Enemy owner, int life) {
			this.owner = owner;
			this.life = life;
		}

		@Override
		public void tick() {
			if (life <= 0) {
				owner.getScene().remove(owner);
			}
		}
	}

	public class ChaseTarget extends DoaScript implements IEventListener {

		private static final long serialVersionUID = 3324554374332147376L;

		private DoaTransform target;

		public float speed = 6;
		public float dt = 0.01f;
		public float randomRotationMax = 120;
		public DoaVector forward = new DoaVector(1, 0); // sprite is facing right

		public float size;

		public ChaseTarget(DoaTransform target) {
			setTarget(target);
		}

		void setTarget(DoaTransform target) {
			this.target = target;
		}

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
			DoaVector playerCenter = target.position;
			DoaVector center = getOwner().transform.position;

			DoaVector toPlayer = DoaVector.sub(playerCenter, center);
			toPlayer = DoaVector.normalise(toPlayer);

			float currentRotation = getOwner().transform.rotation;
			float targetRotation = DoaMath.toDegress((float) Math.atan2(toPlayer.y, toPlayer.x));

			return targetRotation - currentRotation;
		}

		@Override
		public void onEventReceived(IEvent event) {
			if (event instanceof EnemyTargetOverridden) {
				target = (DoaTransform) event.getEventData();
			}
		}
	}
}
