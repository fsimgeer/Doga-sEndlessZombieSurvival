package objects;

import doa.engine.scene.DoaObject;
import scripts.EnemySpawnerScript;

public class EnemySpawner extends DoaObject {

	private static final long serialVersionUID = -7354384445030945196L;

	private static EnemySpawner INSTANCE = null;
	private EnemySpawnerScript script = null;

	public EnemySpawner() {
		script = new EnemySpawnerScript();
		script.debugRender = true;
		addComponent(script);

		setzOrder(100);

		INSTANCE = this;
	}

	public static void newSpawnerAt(final float x, final float y) {
		return;
		// INSTANCE.script.newSpawnerAt(x, y);
	}

	public static void enemyDied() { INSTANCE.script.EnemiesPresent--; }

	public static int getDifficulty() { return INSTANCE.script.Difficulty; }

	public static void nextWave() { INSTANCE.script.nextWave(); }
}
