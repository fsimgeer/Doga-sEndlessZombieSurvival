package gameplay.weapon;

import java.awt.Color;

public class SniperRifle extends AWeapon {

	public SniperRifle() {
		bs.width = 14;
		bs.height = 40;
		bs.color = new Color(40, 40, 40);
		bs.velocity = 500f;
		bs.cooldown = .9f;
		bs.damage = 500;
		bs.spread = 0;
		bs.life = 5000;
		bs.piercing = true;
		bs.bouncing = false;
		bs.bounceChance = 0;
	}
}
