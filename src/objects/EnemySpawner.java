package objects;

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
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.DoaTransform;
import doa.engine.scene.elements.scripts.DoaScript;
import doa.engine.task.DoaTaskGuard;
import doa.engine.task.DoaTasker;
import event.EnemyDied;
import event.EnemySpawned;
import event.EnemyTargetOverridden;
import event.EventDispatcher;
import event.IEvent;
import event.IEventListener;
import event.ShopClosed;
import event.WaveEnded;

public class EnemySpawner extends DoaObject implements IEventListener {

	private static final long serialVersionUID = -7354384445030945196L;

	private static final int MAX_ALLOWED_ENEMY_AT_ONCE = 50;
	private static final int BASE_COOLDOWN_TIME = 1000;
	private static final int COOLDOWN_BETWEEN_CONSECUTIVE_SPAWNS = 4000;

	public int EnemiesPresent = 0;
	public int EnemiesLeftToSpawn = (int) Math.ceil(Math.E);
	public int EnemiesInWave = EnemiesLeftToSpawn;
	public int Difficulty = 1;

	private EnemySpawnerScheduler scheduler = null;

	private EventDispatcher dispatcher;
	private DoaTransform enemyTargetTransform;

	public EnemySpawner(EventDispatcher dispatcher, DoaTransform enemyTargetTransform) {
		scheduler = new EnemySpawnerScheduler(this);
		scheduler.enableDebugRender = true;
		addComponent(scheduler);

		setzOrder(100);

		this.dispatcher = dispatcher;
		this.enemyTargetTransform = enemyTargetTransform;
	}

	public void createNewSpawnerAt(final float x, final float y) {
		scheduler.createNewSpawnerAt(x, y);
	}

	private void spawnEnemyAt(final float x, final float y) {
		Enemy e = new Enemy(dispatcher, enemyTargetTransform, x, y);
		getScene().add(e);
		EnemiesPresent++;
		EnemiesLeftToSpawn--;
		dispatcher.DispatchEvent(new EnemySpawned());
	}

	@Override
	public void onEventReceived(IEvent event) {
		if (event instanceof EnemyDied) {
			EnemiesPresent--;
			if (EnemiesPresent == 0 && EnemiesLeftToSpawn == 0) {
				dispatcher.DispatchEvent(new WaveEnded());
			}
		} else if (event instanceof ShopClosed) {
			Difficulty++;
			EnemiesPresent = 0;
			EnemiesLeftToSpawn = (int) Math.ceil(Math.pow(Math.E, Difficulty));
			EnemiesInWave = EnemiesLeftToSpawn;
		} else if (event instanceof EnemyTargetOverridden) {
			enemyTargetTransform = (DoaTransform) event.getEventData();
		}
	}

	public class EnemySpawnerScheduler extends DoaScript {

		private static final long serialVersionUID = 4165889347822243658L;

		private List<DoaVector> positions = new ArrayList<>();
		private List<DoaTaskGuard> cooldowns = new ArrayList<>();

		private EnemySpawner owner;

		public EnemySpawnerScheduler(EnemySpawner owner) {
			this.owner = owner;
		}

		public void createNewSpawnerAt(final float x, final float y) {
			positions.add(new DoaVector(x, y));

			DoaTaskGuard t = new DoaTaskGuard(false);
			cooldowns.add(t);
			DoaTasker.guard(t, EnemySpawner.BASE_COOLDOWN_TIME + DoaMath.randomIntBetween(0, 5000));
		}

		@Override
		public void tick() {
			if (owner.EnemiesLeftToSpawn > 0) {
				for (int i = 0; i < cooldowns.size(); i++) {
					DoaTaskGuard t = cooldowns.get(i);
					if (t.get() && owner.EnemiesLeftToSpawn > 0 && owner.EnemiesPresent < EnemySpawner.MAX_ALLOWED_ENEMY_AT_ONCE) {
						DoaTasker.guard(t, EnemySpawner.BASE_COOLDOWN_TIME + DoaMath.randomIntBetween(0, EnemySpawner.COOLDOWN_BETWEEN_CONSECUTIVE_SPAWNS));
						owner.spawnEnemyAt(positions.get(i).x, positions.get(i).y);
					}
				}
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
	}
}
