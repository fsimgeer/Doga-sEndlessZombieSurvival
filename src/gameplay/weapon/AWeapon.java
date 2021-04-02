package gameplay.weapon;

import java.awt.Color;

import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaSceneHandler;
import doa.engine.task.DoaTaskGuard;
import doa.engine.task.DoaTasker;

public abstract class AWeapon implements IWeapon {

	protected BulletSpecs bs = new BulletSpecs();
	protected DoaTaskGuard fireRateGuard = new DoaTaskGuard();

	@Override
	public DoaVector getDimensions() { return new DoaVector(bs.width, bs.height); }

	@Override
	public void setDimensions(int w, int h) {
		bs.width = w;
		bs.height = h;
	}

	@Override
	public Color getBulletColor() { return bs.color; }

	@Override
	public void setBulletColor(int r, int g, int b) { bs.color = new Color(r, g, b); }

	@Override
	public float getBulletTravelSpeed() { return bs.velocity; }

	@Override
	public void setBulletTravelSpeed(float s) { bs.velocity = s; }

	@Override
	public float getAttackSpeed() { return bs.cooldown; }

	@Override
	public void setAttackSpeed(float s) { bs.cooldown = s; }

	@Override
	public float getBulletDamage() { return bs.damage; }

	@Override
	public void setBulletDamage(float d) { bs.damage = d; }

	@Override
	public float getBulletSpread() { return bs.spread; }

	@Override
	public void setBulletSpread(float s) { bs.spread = s; }

	@Override
	public int getBulletLife() { return bs.life; }

	@Override
	public void setBulletLife(int r) { bs.life = r; }

	@Override
	public boolean isUsingPiercingRounds() { return bs.piercing; }

	@Override
	public void usePiercingRounds() { bs.piercing = true; }

	@Override
	public void stopUsingPiercingRounds() { bs.piercing = false; }

	@Override
	public boolean isUsingBouncingRounds() { return bs.bouncing; }

	@Override
	public void useBouncingRounds() { bs.bouncing = true; }

	@Override
	public void stopUsingBouncingRounds() { bs.bouncing = false; }

	@Override
	public int getBounceChance() { return bs.bounceChance; }

	@Override
	public void fire(DoaVector position, DoaVector direction) {
		if (fireRateGuard.get()) {
			DoaTasker.guard(fireRateGuard, (long) getAttackSpeed());
			DoaSceneHandler.getLoadedScene().add(new Bullet(position, direction, this));
		}
	}
}
