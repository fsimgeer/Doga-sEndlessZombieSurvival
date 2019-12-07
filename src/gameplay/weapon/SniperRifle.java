package gameplay.weapon;

public class SniperRifle extends AWeapon {

	public SniperRifle() {
		bs = new BulletSpecsBuilder()
				.setDimensions(14, 40)
				.setColor(40, 40, 40)
				.setBulletTravelSpeed(5f)
				.setAttackSpeed(.9f)
				.setBulletDamage(500)
				.setBulletSpread(0)
				.setBulletRange(5000)
				.setPiercing(true)
				.setBouncing(false)
				.instantiate();
	}
}