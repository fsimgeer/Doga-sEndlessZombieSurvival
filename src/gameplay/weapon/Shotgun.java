package gameplay.weapon;

import com.doa.maths.DoaVectorF;

import util.Builders;

public class Shotgun extends AWeapon {
	
	public Shotgun() {
		bs = new BulletSpecsBuilder()
				.setDimensions(8, 8)
				.setColor(255, 0, 0)
				.setBulletTravelSpeed(1.5f)
				.setAttackSpeed(1f)
				.setBulletDamage(500)
				.setBulletSpread(40)
				.setBulletRange(200)
				.setPiercing(true)
				.setBouncing(false)
				.instantiate();
	}
	
	@Override
	public void fire(float sx, float sy, DoaVectorF direction) {
		for(int i = 0; i < 8; ++i) {
			Builders.BB.args(sx, sy, direction.x, direction.y, this).instantiate();
		}
	}

	@Override
	public void fire(double sx, double sy, DoaVectorF direction) {
		fire((float) sx, (float) sy, direction);
	}
}
