package gameplay;

import java.awt.Color;
import java.awt.Polygon;

import com.doa.engine.DoaCamera;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaLights;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;
import com.doa.engine.input.DoaMouse;
import com.doa.engine.task.DoaTaskGuard;
import com.doa.engine.task.DoaTasker;
import com.doa.maths.DoaVectorF;

import util.Builders;

public class Player extends TypedGameObject {

	private static final long serialVersionUID = -5588139160019846890L;

	private static final double SIZE_FACTOR = 0.9;

	private static Player INSTANCE = null;

	private transient BulletSpecs bs = new BulletSpecs();
	private DoaTaskGuard cooldown = new DoaTaskGuard(true);
	private double health = 100;
	private double healthMAX = 100;
	private int coins = 100;
	private int score = 0;
	private double angleRad = 0;

	public Player(final float x, final float y) {
		super(x, y);
		super.type = ObjectType.PLAYER;
		INSTANCE = this;
		width = (int) (DoaSprites.get("PlayerSprite").getWidth() * SIZE_FACTOR);
		height = (int) (DoaSprites.get("PlayerSprite").getHeight() * SIZE_FACTOR);
		bs.setWidth(10);
		bs.setHeight(20);
		bs.setColor(Color.RED);
		bs.setCooldown(1000);
		bs.setVelocity(2f);
		bs.setDamage(200);
		bs.setSpread(0);
		bs.setRange(10000000);
		// bs.setPiercing(true);
	}

	@Override
	public void tick() {
		final float oldX = position.x;
		final float oldY = position.y;
		if (DoaKeyboard.A) {
			velocity.x = -1;
		} else if (DoaKeyboard.D) {
			velocity.x = 1;
		} else {
			velocity.x = 0;
		}
		position.x += velocity.x;
		if (Collision.checkCollision(this, ObjectType.OBSTACLE)) {
			position.x = oldX;
		}
		if (DoaKeyboard.W) {
			velocity.y = -1;
		} else if (DoaKeyboard.S) {
			velocity.y = 1;
		} else {
			velocity.y = 0;
		}
		position.y += velocity.y;
		if (Collision.checkCollision(this, ObjectType.OBSTACLE)) {
			position.y = oldY;
		}

		DoaVectorF intVec = new DoaVectorF(DoaCamera.getX() + (float) DoaMouse.X, DoaCamera.getY() + (float) DoaMouse.Y);
		intVec = position.clone().add(new DoaVectorF(width / 2f, height / 2f)).sub(intVec).normalise();
		intVec.x *= -1;

		angleRad = Math.atan2(-intVec.y, intVec.x);

		if (DoaMouse.MB1_HOLD && cooldown.get()) {
			DoaTasker.guard(cooldown, bs.getCooldown());

			DoaVectorF center = new DoaVectorF();
			Polygon bounds = getBounds();
			for (int i = 0; i < bounds.npoints; ++i) {
				center.x += bounds.xpoints[i];
				center.y += bounds.ypoints[i];
			}
			center.mul(1f / bounds.npoints);

			DoaVectorF offset = new DoaVectorF(20, 8);
			DoaVectorF rotated = offset.rotateRadians(angleRad);
			DoaVectorF rotatedTranslated = rotated.translate(center.x, center.y);

			final float mx = (float) (DoaMouse.X + DoaCamera.getX());
			final float my = (float) (DoaMouse.Y + DoaCamera.getY());
			Builders.BB.args(rotatedTranslated.x - bs.getWidth() * .5f, rotatedTranslated.y - bs.getHeight() * .5f, mx, my, bs).instantiate();
		}

		final TypedGameObject[] touchingEnemies = Collision.getCollidingObjects(this, ObjectType.ENEMY);
		health -= touchingEnemies.length * 0.2;

		angleRad = Math.atan2(DoaMouse.Y + DoaCamera.getY() - position.y, DoaMouse.X + DoaCamera.getX() - position.x);

		if (DoaKeyboard.O) {
			DoaLights.ambientLight(Color.WHITE);
		} else if (DoaKeyboard.P) {
			DoaLights.ambientLight(new Color(100, 100, 200));
		}
	}

	@Override
	public void render(final DoaGraphicsContext g) {
		g.pushTransform();
		g.rotate(angleRad, position.x + width * .5f, position.y + height * .5f);
		g.drawImage(DoaSprites.get("PlayerSprite"), position.x, position.y, width, height, null);
		g.popTransform();
		g.draw(getBounds());
	}

	@Override
	public Polygon getBounds() {
		int[] xp = new int[] { (int) position.x, (int) position.x, (int) position.x + width, (int) position.x + width };
		int[] yp = new int[] { (int) position.y, (int) position.y + height, (int) position.y + height, (int) position.y };
		for (int i = 0; i < 0; ++i) {
			// TODO find a way to accurately represent the bounding box! if i rotate,
			// collision detection fucks up, if i don't the model and the bounding box doesn't match!
			DoaVectorF f = new DoaVectorF(xp[i], yp[i]);
			f.translate(-position.x - width * .5f, -position.y - height * .5f);
			f.rotateRadians(angleRad);
			f.translate(position.x + width * .5f, position.y + height * .5f);
			xp[i] = (int) f.x;
			yp[i] = (int) f.y;
		}
		return new Polygon(xp, yp, 4);
	}

	public static Player getInstance() {
		return INSTANCE;
	}

	public BulletSpecs getBulletSpecs() {
		return bs;
	}

	public void setBulletSpecs(BulletSpecs bs) {
		this.bs = bs;
	}

	public int getHealth() {
		return (int) health;
	}

	public void setHealth(int newHealth) {
		health = newHealth;
	}

	public int getHealthMAX() {
		return (int) healthMAX;
	}

	public void setHealthMAX(int newMax) {
		double percentage = health / healthMAX;
		healthMAX = newMax;
		health = healthMAX * percentage;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(final int coins) {
		this.coins = coins;
	}

	public int getScore() {
		return score;
	}

	public void setScore(final int score) {
		this.score = score;
	}
}
