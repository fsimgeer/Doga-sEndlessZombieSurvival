package scripts;

import static doa.engine.core.DoaGraphicsFunctions.drawImage;
import static doa.engine.core.DoaGraphicsFunctions.fillOval;
import static doa.engine.core.DoaGraphicsFunctions.popComposite;
import static doa.engine.core.DoaGraphicsFunctions.pushComposite;
import static doa.engine.core.DoaGraphicsFunctions.setComposite;
import static doa.engine.core.DoaGraphicsFunctions.setPaint;
import static doa.engine.core.DoaGraphicsFunctions.turnOffLightContribution;
import static doa.engine.core.DoaGraphicsFunctions.turnOnLightContribution;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import doa.engine.graphics.DoaSprites;
import doa.engine.maths.DoaMath;
import doa.engine.maths.DoaVector;
import doa.engine.scene.elements.scripts.DoaScript;
import doa.engine.task.DoaTaskGuard;
import doa.engine.task.DoaTasker;
import objects.Enemy;

public class EnemySpawnerScript extends DoaScript {

	private static final int MAX_ALLOWED_ENEMY_AT_ONCE = 50;
	private static final int BASE_COOLDOWN_TIME = 1000;
	private static final int COOLDOWN_BETWEEN_CONSECUTIVE_SPAWNS = 4000;

	public int EnemiesPresent = 0;
	public int EnemiesLeftToSpawn = (int) Math.ceil(Math.E);
	public int EnemiesInWave = EnemiesLeftToSpawn;
	public int Difficulty = 1;

	private List<DoaVector> positions = new ArrayList<>();
	private List<DoaTaskGuard> cooldowns = new ArrayList<>();

	public void newSpawnerAt(final float x, final float y) {
		positions.add(new DoaVector(x, y));

		DoaTaskGuard t = new DoaTaskGuard(false);
		cooldowns.add(t);
		DoaTasker.guard(t, BASE_COOLDOWN_TIME + DoaMath.randomIntBetween(0, 5000));
	}

	public void nextWave() {
		Difficulty++;
		EnemiesPresent = 0;
		EnemiesLeftToSpawn = (int) Math.ceil(Math.pow(Math.E, Difficulty));
		EnemiesInWave = EnemiesLeftToSpawn;
	}

	@Override
	public void tick() {
		if (EnemiesLeftToSpawn > 0) {
			for (int i = 0; i < cooldowns.size(); i++) {
				DoaTaskGuard t = cooldowns.get(i);
				if (t.get() && EnemiesLeftToSpawn > 0 && EnemiesPresent < MAX_ALLOWED_ENEMY_AT_ONCE) {
					beginCooldown(t);

					Enemy e = new Enemy(positions.get(i).x, positions.get(i).y);
					getOwner().getScene().add(e);
					EnemiesPresent++;
					EnemiesLeftToSpawn--;
				}
			}
		} else if (EnemiesLeftToSpawn == 0) {
			// show shop etc.
		}
	}

	@Override
	public void debugRender() {
		super.debugRender();
		turnOffLightContribution();
		for (int i = 0; i < positions.size(); i++) {
			DoaVector pos = positions.get(i);

			Point2D center = new Point2D.Float(pos.x, pos.y);
			float radius = 16;
			float[] dist = { 0.0f, 1.0f };
			Color[] colors = { new Color(255, 0, 0, 128), new Color(255, 0, 0, 0) };
			RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
			setPaint(p);

			fillOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
			pushComposite();
			setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
			drawImage(DoaSprites.getSprite("light0"), pos.x - radius * 2, pos.y - radius * 2, radius * 4, radius * 4);
			popComposite();

		}
		turnOnLightContribution();
	}

	private static void beginCooldown(DoaTaskGuard t) {
		DoaTasker.guard(t, BASE_COOLDOWN_TIME + DoaMath.randomIntBetween(0, COOLDOWN_BETWEEN_CONSECUTIVE_SPAWNS));
	}

}
