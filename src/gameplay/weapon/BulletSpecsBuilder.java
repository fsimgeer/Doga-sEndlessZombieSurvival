package gameplay.weapon;

import java.awt.Color;

public class BulletSpecsBuilder {

	private int width;
	private int height;
	private Color color;
	private float velocity;
	private float cooldown;
	private float damage;
	private float spread;
	private float range;

	private boolean piercing;
	private boolean bouncing;

	public BulletSpecsBuilder() {}

	public BulletSpecsBuilder setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public BulletSpecsBuilder setColor(int r, int g, int b) {
		this.color = new Color(r, g, b);
		return this;
	}

	public BulletSpecsBuilder setBulletTravelSpeed(float s) {
		this.velocity = s;
		return this;
	}

	public BulletSpecsBuilder setAttackSpeed(float s) {
		this.cooldown = s;
		return this;
	}

	public BulletSpecsBuilder setBulletDamage(float d) {
		this.damage = d;
		return this;
	}

	public BulletSpecsBuilder setBulletSpread(float s) {
		this.spread = s;
		return this;
	}

	public BulletSpecsBuilder setBulletRange(float r) {
		this.range = r;
		return this;
	}

	public BulletSpecsBuilder setPiercing(boolean p) {
		this.piercing = p;
		return this;
	}

	public BulletSpecsBuilder setBouncing(boolean b) {
		this.bouncing = b;
		return this;
	}

	public BulletSpecs instantiate() {
		return new BulletSpecs(width, height, color, velocity, cooldown, damage, spread, range, piercing, bouncing);
	}
}