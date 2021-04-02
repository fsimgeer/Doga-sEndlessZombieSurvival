package gameplay.weapon;

import java.awt.Color;

import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaSceneHandler;
import doa.engine.task.DoaTasker;

public class Shotgun extends AWeapon {

	public Shotgun() {
		bs.width = 8;
		bs.height = 8;
		bs.color = new Color(255, 0, 0);
		bs.velocity = 1.5f;
		bs.cooldown = 1f;
		bs.damage = 500;
		bs.spread = 40;
		bs.life = 200;
		bs.piercing = true;
		bs.bouncing = false;
		bs.bounceChance = 0;
	}

	@Override
	public void fire(DoaVector position, DoaVector direction) {
		if (fireRateGuard.get()) {
			DoaTasker.guard(fireRateGuard, (long) getAttackSpeed());
			for (int i = 0; i < 8; ++i) {
				DoaSceneHandler.getLoadedScene().add(new Bullet(position, direction, this));
			}
		}
	}
}
