package objects;

import doa.engine.graphics.DoaSprites;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.DoaScene;
import doa.engine.scene.elements.physics.DoaBodyType;
import doa.engine.scene.elements.physics.DoaCircleCollider;
import doa.engine.scene.elements.physics.DoaRigidBody;
import doa.engine.scene.elements.renderers.DoaSpriteRenderer;
import renderers.EnemyRenderer;
import scripts.EnemyChasePlayer;
import scripts.EnemyLife;

public class Enemy extends DoaObject {

	private static final long serialVersionUID = -3839835030750716763L;

	private static final float SIZE = 48;

	public EnemyLife lifeScript;

	public Enemy(final float x, final float y) {
		super("Zombie");

		transform.position.x = x;
		transform.position.y = y;

		EnemyChasePlayer cp = new EnemyChasePlayer();
		cp.debugRender = true;
		cp.size = SIZE;
		addComponent(cp);

		DoaSpriteRenderer r = new EnemyRenderer();
		r.setSprite(DoaSprites.getSprite("EnemySprite"));
		r.setDimensions(new DoaVector(SIZE, SIZE));
		addComponent(r);

		DoaRigidBody b = new DoaRigidBody();
		b.type = DoaBodyType.DYNAMIC;
		b.colliders.add(new DoaCircleCollider(SIZE / 2));
		b.fixedRotation = true;
		b.mass = 10;
		b.debugRender = true;
		addComponent(b);

		lifeScript = new EnemyLife(10);
		addComponent(lifeScript);
	}

	@Override
	public void onRemoveFromScene(DoaScene scene) {
		super.onRemoveFromScene(scene);
		EnemySpawner.enemyDied();

		Player.getInstance().data.coins += Math.max(1, Math.ceil(EnemySpawner.getDifficulty() / Math.PI));
		Player.getInstance().data.score += EnemySpawner.getDifficulty() * Math.PI;
	}

}
