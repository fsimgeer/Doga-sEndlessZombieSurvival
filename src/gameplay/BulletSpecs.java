package gameplay;

import java.awt.Color;

public class BulletSpecs {

	private int width = 0;
	private int height = 0;
	private Color color = null;
	private float velocity = 0;
	private int cooldown = 0;
	private float damage = 0;
	private float spread = 0;
	private float range = 0;

	private boolean piercing = false;
	private boolean bouncing = false;

	public BulletSpecs() {}

	public BulletSpecs(int width, int height, Color color, float velocity, int cooldown, float damage, float spread, float range, boolean piercing, boolean bouncing) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.velocity = velocity;
		this.cooldown = cooldown;
		this.damage = damage;
		this.spread = spread;
		this.range = range;
		this.piercing = piercing;
		this.bouncing = bouncing;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(final float velocity) {
		this.velocity = velocity;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(final int cooldown) {
		this.cooldown = cooldown;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(final float damage) {
		this.damage = damage;
	}
	
	public void setSpread(final float spread) {
		this.spread = spread;
	}
	
	public float getSpread() {
		return spread;
	}
	
	public void setRange(final float range) {
		this.range = range;
	}
	
	public float getRange() {
		return range;
	}

	public boolean isPiercing() {
		return piercing;
	}

	public void setPiercing(final boolean piercing) {
		this.piercing = piercing;
	}

	public boolean isBouncing() {
		return bouncing;
	}

	public void setBouncing(final boolean bouncing) {
		this.bouncing = bouncing;
	}
}
