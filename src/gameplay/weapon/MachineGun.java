package gameplay.weapon;

public class MachineGun extends AWeapon {
	
	public MachineGun() {
		bs = new BulletSpecsBuilder()
				.setDimensions(5, 10)
				.setColor(0, 0, 0)
				.setBulletTravelSpeed(3f)
				.setAttackSpeed(14)
				.setBulletDamage(10)
				.setBulletSpread(20)
				.setBulletRange(300)
				.setPiercing(false)
				.setBouncing(false)
				.instantiate();
	}
}