package components;

import doa.engine.core.DoaGame;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.scripts.DoaScript;
import doa.engine.scene.elements.scripts.DoaWASDControl;
import gameplay.weapon.IWeapon;
import gameplay.weapon.Weapons;

public class PlayerData extends DoaScript {

	private static final long serialVersionUID = 9053448414879851253L;

	private IWeapon weapon = Weapons.MachineGun;
	private float health = 100;
	private float healthMAX = 100;
	private float speed = 2.0f;
	private int coins = 100;
	private int score = 0;
	private float healthDecay = 0; // used for enemy touch, dont show in shop

	private DoaWASDControl control;

	public IWeapon getWeapon() { return weapon;	}
	public void setWeapon(IWeapon newWeapon) { weapon = newWeapon; }

	public int getHealth() { return (int)health; }
	public void setHealth(float newHealth) {
		if(newHealth < 0) return;
		if(newHealth >= healthMAX)
			health = healthMAX;
		health = newHealth;
	}
	
	public int getHealthMAX() { return (int) healthMAX; }
	public void setHealthMAX(int newMax) {
		float percentage = health / healthMAX;
		healthMAX = newMax;
		health = healthMAX * percentage;
	}

	public float getSpeed() { return speed; }
	public void setSpeed(float newSpeed) { speed = newSpeed; }
	
	public int getCoins() { return coins; }
	public void setCoins(int newCoins) {
		if(newCoins < 0) return;
		coins = newCoins;
	}

	public int getScore() { return score; }
	public void setScore(int newScore) { score = newScore; }
	
	public float getHealthDecay() { return healthDecay; }
	public void setHealthDecay(float newHealthDecay) { healthDecay = newHealthDecay; }

	@Override
	protected void onAdd(DoaObject o) {
		super.onAdd(o);
		control = new DoaWASDControl();
		o.addComponent(control);
	}

	@Override
	public void tick() {
		health -= healthDecay / DoaGame.getInstance().getTargetTPS();
		control.speed = speed;
	}
}
