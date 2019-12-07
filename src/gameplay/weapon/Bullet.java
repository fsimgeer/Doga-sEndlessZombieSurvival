package gameplay.weapon;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.scene.DoaSceneHandler;
import com.doa.maths.DoaVectorF;

import gameplay.Collision;
import gameplay.Enemy;
import gameplay.ObjectType;
import gameplay.TypedGameObject;
import util.Builders;

public class Bullet extends TypedGameObject {

	private static final long serialVersionUID = -4315362367824405514L;

	private transient AWeapon w;
	DoaVectorF v;

	public Bullet(final Float x, final Float y, final Float mx, final Float my, final AWeapon shooter) {
		super(x, y);
		super.type = ObjectType.PROJECTILE;
		w = shooter;
		velocity.x = mx - x;
		velocity.y = my - y;
		velocity.rotateAngle((Math.random() - .5f) * shooter.getBulletSpread());
		v = position.clone();
		velocity = velocity.normalise().mul(w.getBulletTravelSpeed());
	}

	@Override
	public synchronized void tick() {
		position.add(velocity);
		double distanceSquare = Math.pow(position.x - v.x, 2) + Math.pow(position.y - v.y, 2);
		if (distanceSquare > Math.pow(w.getBulletRange(), 2)) {
			deleteBullet();
		}
		if (!w.isUsingBouncingRounds() && Collision.checkCollision(this, ObjectType.OBSTACLE)) {
			deleteBullet();
		}
		final TypedGameObject[] possibleHitEnemies = Collision.getCollidingObjects(this, ObjectType.ENEMY);
		if (!w.isUsingPiercingRounds()) {
			if (possibleHitEnemies.length > 0) {
				((Enemy) possibleHitEnemies[0]).getHit(this);
				deleteBullet();
			}
		} else {
			Arrays.stream(possibleHitEnemies).forEach(enemy -> ((Enemy) enemy).getHit(this));
		}
	}

	@Override
	public synchronized void render(final DoaGraphicsContext g) {
		Rectangle2D.Float dimensions = w.getDimensions();

		g.setColor(w.getBulletColor());
		g.rotate(Math.atan2(velocity.y, velocity.x) + Math.PI / 2, position.x + dimensions.getCenterX(), position.y + dimensions.getCenterY());
		g.fillOval(position.x, position.y, dimensions.getWidth(), dimensions.getHeight());
	}

	@Override
	public Rectangle getBounds() {
		Rectangle2D.Float dimensions = w.getDimensions();
		return new Rectangle((int) position.x, (int) position.y, (int) dimensions.getWidth(), (int) dimensions.getHeight());
	}

	private void deleteBullet() {
		DoaSceneHandler.getLoadedScene().remove(this);
		Collision.remove(this);
	}

	public static void createBullet(BulletSpecs bs, final float x, final float y, final float mx, final float my) {
		Collision.add(Builders.BB.args(x, y, mx, my, bs).instantiate());
	}
}
