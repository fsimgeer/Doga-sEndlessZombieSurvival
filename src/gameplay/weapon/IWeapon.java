package gameplay.weapon;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.doa.maths.DoaVectorF;

public interface IWeapon {

	Rectangle2D.Float getDimensions();
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

	float getBulletRange();
	void setBulletRange(float r);
	
	boolean isUsingPiercingRounds();
	void usePiercingRounds();
	void stopUsingPiercingRounds();
	
	boolean isUsingBouncingRounds();
	void useBouncingRounds();
	void stopUsingBouncingRounds();
	
	void fire(float sx, float sy, DoaVectorF direction);
	void fire(double sx, double sy, DoaVectorF direction);
}