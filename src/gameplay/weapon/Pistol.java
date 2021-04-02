package gameplay.weapon;

import java.awt.Color;

public class Pistol extends AWeapon {

	public Pistol() {
		bs.width = 6;
		bs.height = 6;
		// bs.color = new Color(200, 200, 200);
		bs.color = new Color(0, 0, 0);
		bs.velocity = 800;
		bs.cooldown = 800;
		bs.damage = 500;
		bs.spread = 0;
		bs.life = 3000;
		bs.piercing = false;
		bs.bouncing = true;
		bs.bounceChance = 0;
	}
}
