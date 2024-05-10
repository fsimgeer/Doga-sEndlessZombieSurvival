package gameplay.weapon;

import doa.engine.maths.DoaMath;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.physics.DoaCircleCollider;
import doa.engine.scene.elements.physics.DoaCollider;
import doa.engine.scene.elements.physics.DoaEllipseCollider;
import doa.engine.scene.elements.physics.DoaRigidBody;
import objects.Enemy;
import objects.Wall;
import renderers.BulletRenderer;
import scripts.BulletLife;
import scripts.BulletRotationFix;

public class Bullet extends DoaObject {

	private static final long serialVersionUID = -4315362367824405514L;

	public Bullet(DoaVector position, DoaVector direction, IWeapon weapon) {
		super("Bullet");
		transform.position.x = position.x;
		transform.position.y = position.y;

		DoaVector velocity = new DoaVector(direction);
		transform.rotation = DoaMath.toDegress((float) Math.atan2(velocity.y, velocity.x)) + 90;
		DoaVector.mul(velocity, weapon.getBulletTravelSpeed(), velocity);

		DoaRigidBody b = new DoaRigidBody();
		DoaCollider triggerCollider;
		DoaCollider nonTriggerCollider;
		if (weapon.getDimensions().x == weapon.getDimensions().y) {
			triggerCollider = new DoaCircleCollider(weapon.getDimensions().x / 2) {

				@Override
				public void onTriggerEnter(DoaObject entered, DoaObject enterer) {
					if (enterer instanceof Enemy enemy) {
						enemy.TakeDamage(weapon.getBulletDamage());
						if (!weapon.isUsingPiercingRounds()) {
							delete(Bullet.this);
						}
					}
					if (enterer instanceof Wall wall) {
						if (!weapon.isUsingBouncingRounds()) {
							delete(Bullet.this);
						} else {
							if (DoaMath.randomIntBetween(0, 100) > weapon.getBounceChance()) {
								delete(Bullet.this);
							}
						}
					}
				}
			}.makeTrigger();
			nonTriggerCollider = new DoaCircleCollider(weapon.getDimensions().x / 2 - 1).group(-1);
		} else {
			triggerCollider = new DoaEllipseCollider(weapon.getDimensions().x / 2, weapon.getDimensions().y / 2) {
				@Override
				public void onTriggerEnter(DoaObject entered, DoaObject enterer) {
					if (enterer instanceof Enemy enemy) {
						enemy.TakeDamage(weapon.getBulletDamage());
						if (!weapon.isUsingPiercingRounds()) {
							delete(Bullet.this);
						}
					}
					if (enterer instanceof Wall wall) {
						if (!weapon.isUsingBouncingRounds()) {
							delete(Bullet.this);
						} else {
							if (DoaMath.randomIntBetween(0, 100) > weapon.getBounceChance()) {
								delete(Bullet.this);
							}
						}
					}
				}
			}.makeTrigger();
			nonTriggerCollider = new DoaEllipseCollider(weapon.getDimensions().x / 2 - 1, weapon.getDimensions().y / 2 - 2).group(-1);
		}
		b.colliders.add(triggerCollider);
		if (weapon.isUsingBouncingRounds()) {
			b.colliders.add(nonTriggerCollider);
			b.elasticity = .6f;
		}
		b.isBullet = true;
		b.mass = .3f;
		b.linearVelocity = velocity;
		b.fixedRotation = true;
		addComponent(b);

		addComponent(new BulletRenderer(weapon));
		addComponent(new BulletLife(weapon));
		addComponent(new BulletRotationFix());
	}

	private static void delete(Bullet bullet) {
		if (bullet.getScene() != null) {
			bullet.getScene().remove(bullet);
		}
	}
}
