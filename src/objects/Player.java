package objects;

import components.PlayerData;
import doa.engine.graphics.DoaSprites;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.physics.DoaBodyType;
import doa.engine.scene.elements.physics.DoaCircleCollider;
import doa.engine.scene.elements.physics.DoaRigidBody;
import doa.engine.scene.elements.renderers.DoaSpriteRenderer;
import renderers.PlayerRenderer;
import scripts.PlayerMouse;

public class Player extends DoaObject {

	private static final long serialVersionUID = -5588139160019846890L;

	public static final float SIZE = 48;

	private static Player INSTANCE = null;

	public PlayerData data;

	public Player(final float x, final float y) {
		super("Player");
		transform.position.x = x;
		transform.position.y = y;

		DoaSpriteRenderer r = new PlayerRenderer();
		r.setSprite(DoaSprites.getSprite("PlayerSprite2"));
		addComponent(r);

		data = new PlayerData();
		addComponent(data);

		DoaRigidBody b = new DoaRigidBody();
		b.type = DoaBodyType.DYNAMIC;
		b.colliders.add(new DoaCircleCollider(SIZE / 2 - 1).group(-1));

		b.colliders.add(new DoaCircleCollider(SIZE / 2) {
			@Override
			public void onTriggerEnter(DoaObject entered, DoaObject enterer) {
				if (enterer instanceof Enemy enemy) {
					((Player) entered).data.healthDecay += 1;
				}
			}

			@Override
			public void onTriggerExit(DoaObject exited, DoaObject exiter) {
				if (exiter instanceof Enemy enemy) {
					((Player) exited).data.healthDecay -= 1;
				}
			}
		}.makeTrigger());

		b.fixedRotation = true;
		b.debugRender = true;
		addComponent(b);

		addComponent(new PlayerMouse());

		INSTANCE = this;
	}

	public static Player getInstance() { return INSTANCE; }

}
