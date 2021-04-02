package gameplay.weapon;

import java.awt.Color;

import doa.engine.maths.DoaVector;

public interface IWeapon {

	DoaVector getDimensions();

	void setDimensions(int w, int h);

	Color getBulletColor();

	void setBulletColor(int r, int g, int b);

	float getBulletTravelSpeed();

	void setBulletTravelSpeed(float s);

	float getAttackSpeed();

	void setAttackSpeed(float s);

	float getBulletDamage();

	void setBulletDamage(float d);

	float getBulletSpread();

	void setBulletSpread(float s);

	int getBulletLife();

	void setBulletLife(int r);

	boolean isUsingPiercingRounds();

	void usePiercingRounds();

	void stopUsingPiercingRounds();

	boolean isUsingBouncingRounds();

	void useBouncingRounds();

	void stopUsingBouncingRounds();

	int getBounceChance();

	void fire(DoaVector position, DoaVector direction);
}
