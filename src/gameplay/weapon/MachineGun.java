package gameplay.weapon;

import java.awt.Color;

public class MachineGun extends AWeapon {

	public MachineGun() {
		bs.width = 3;
		bs.height = 6;
		bs.color = new Color(0, 0, 0);
		bs.velocity = 1000f;
		bs.cooldown = 66.6666666667f;
		bs.damage = 10;
		bs.spread = 15;
		bs.life = 3000;
		bs.piercing = false;
		bs.bouncing = true;
		bs.bounceChance = 15;
	}
}
