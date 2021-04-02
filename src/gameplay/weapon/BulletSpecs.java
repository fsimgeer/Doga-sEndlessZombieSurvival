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
	int life = 0;

	boolean piercing = false;
	boolean bouncing = false;

	int bounceChance = 0;

	public BulletSpecs() {}

	public BulletSpecs(int width, int height, Color color, float velocity, float cooldown, float damage, float spread, int life, boolean piercing, boolean bouncing, int bounceChance) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.velocity = velocity;
		this.cooldown = cooldown;
		this.damage = damage;
		this.spread = spread;
		this.life = life;
		this.piercing = piercing;
		this.bouncing = bouncing;
		this.bounceChance = bounceChance;
	}
}
