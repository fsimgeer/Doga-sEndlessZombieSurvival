package scripts;

import doa.engine.scene.elements.scripts.DoaScript;

public class EnemyLife extends DoaScript {

	public int life;

	public EnemyLife(int life) { this.life = life; }

	@Override
	public void tick() {
		if (life <= 0) {
			getOwner().getScene().remove(getOwner());
		}
	}

}
