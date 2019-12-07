package gameplay.weapon;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.doa.maths.DoaVectorF;

import util.Builders;

public abstract class AWeapon implements IWeapon {

	protected BulletSpecs bs;

	@Override
	public Rectangle2D.Float getDimensions() {
		return new Rectangle2D.Float(0, 0, bs.width, bs.height);
	}

	@Override
	public void setDimensions(int w, int h) {
		bs.width = w;
		bs.height = h;
	}

	@Override
	public Color getBulletColor() {
		return bs.color;
	}

	@Override
	public void setBulletColor(int r, int g, int b) {
		bs.color = new Color(r, g, b);
	}

	@Override
	public float getBulletTravelSpeed() {
		return bs.velocity;
	}

	@Override
	public void setBulletTravelSpeed(float s) {
		bs.velocity = s;
	}

	@Override
	public float getAttackSpeed() {
		return bs.cooldown;
	}

	@Override
	public void setAttackSpeed(float s) {
		bs.cooldown = s;
	}

	@Override
	public float getBulletDamage() {
		return bs.damage;
	}

	@Override
	public void setBulletDamage(float d) {
		bs.damage = d;
	}

	@Override
	public float getBulletSpread() {
		return bs.spread;
	}

	@Override
	public void setBulletSpread(float s) {
		bs.spread = s;
	}

	@Override
	public float getBulletRange() {
		return bs.range;
	}

	@Override
	public void setBulletRange(float r) {
		bs.range = r;
	}

	@Override
	public boolean isUsingPiercingRounds() {
		return bs.piercing;
	}

	@Override
	public void usePiercingRounds() {
		bs.piercing = true;
	}

	@Override
	public void stopUsingPiercingRounds() {
		bs.piercing = false;
	}

	@Override
	public boolean isUsingBouncingRounds() {
		return bs.bouncing;
	}

	@Override
	public void useBouncingRounds() {
		bs.bouncing = true;
	}

	@Override
	public void stopUsingBouncingRounds() {
		bs.bouncing = false;
	}

	@Override
	public void fire(float sx, float sy, DoaVectorF direction) {
		Builders.BB.args(sx, sy, direction.x, direction.y, this).instantiate();
	}

	@Override
	public void fire(double sx, double sy, DoaVectorF direction) {
		fire((float) sx, (float) sy, direction);
	}
}
