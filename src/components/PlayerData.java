package components;

import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.scripts.DoaScript;
import doa.engine.scene.elements.scripts.DoaWASDControl;
import gameplay.weapon.IWeapon;
import gameplay.weapon.Weapons;

public class PlayerData extends DoaScript {

	private static final long serialVersionUID = 9053448414879851253L;

	public IWeapon weapon = Weapons.Shotgun;
	public float health = 100;
	private float healthMAX = 100;
	public float speed = 0.25f;
	public int coins = 100;
	public int score = 0;
	public float healthDecay = 0; // used for enemy touch, dont show in shop

	private DoaWASDControl control;

	public int getHealthMAX() { return (int) healthMAX; }

	public void setHealthMAX(int newMax) {
		float percentage = health / healthMAX;
		healthMAX = newMax;
		health = healthMAX * percentage;
	}

	@Override
	protected void onAdd(DoaObject o) {
		super.onAdd(o);
		control = new DoaWASDControl();
		o.addComponent(control);
	}

	@Override
	public void tick() {
		health -= healthDecay / 240;
		control.speed = speed;
	}
}
