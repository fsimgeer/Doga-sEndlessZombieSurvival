package gameplay.weapon;

import java.awt.Color;

public class BulletSpecs {

	int width = 0;
	int height = 0;
	Color color = null;
	float velocity = 0;
	float cooldown = 0;
	float damage = 0;
	float spread = 0;
	float range = 0;

	boolean piercing = false;
	boolean bouncing = false;

	public BulletSpecs() {}

	public BulletSpecs(int width, int height, Color color, float velocity, float cooldown, float damage, float spread, float range, boolean piercing, boolean bouncing) {
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
}