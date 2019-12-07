package gameplay.weapon;

public class Pistol extends AWeapon {

	public Pistol() {
		bs = new BulletSpecsBuilder()
				.setDimensions(6, 6)
				.setColor(200, 200, 200)
				.setBulletTravelSpeed(2f)
				.setAttackSpeed(1.3f)
				.setBulletDamage(500)
				.setBulletSpread(0)
				.setBulletRange(100)
				.setPiercing(false)
				.setBouncing(false)
				.instantiate();
	}
}