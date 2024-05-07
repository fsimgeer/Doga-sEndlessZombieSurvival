package objects;

import components.PlayerData;
import doa.engine.core.DoaCamera;
import doa.engine.graphics.DoaSprites;
import doa.engine.input.DoaMouse;
import doa.engine.maths.DoaMath;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.DoaTransform;
import doa.engine.scene.elements.physics.DoaBodyType;
import doa.engine.scene.elements.physics.DoaCircleCollider;
import doa.engine.scene.elements.physics.DoaRigidBody;
import doa.engine.scene.elements.renderers.DoaSpriteRenderer;
import doa.engine.scene.elements.scripts.DoaMouseAdapter;
import event.EnemyDied;
import event.IEvent;
import event.IEventListener;
import globals.Layers;
import renderers.PlayerRenderer;

public class Player extends DoaObject implements IEventListener {

	private static final long serialVersionUID = -5588139160019846890L;

	public static final float SIZE = 48;

	public PlayerData Data;

	public Player(final float x, final float y) {
		super("Player");
		transform.position.x = x;
		transform.position.y = y;

		DoaSpriteRenderer r = new PlayerRenderer();
		r.setSprite(DoaSprites.getSprite("PlayerSprite2"));
		addComponent(r);

		Data = new PlayerData();
		addComponent(Data);

		DoaRigidBody b = new DoaRigidBody();
		b.type = DoaBodyType.DYNAMIC;
		b.colliders.add(new DoaCircleCollider(SIZE / 2 - 1).group(-1));

		b.colliders.add(new DoaCircleCollider(SIZE / 2) {
			@Override
			public void onTriggerEnter(DoaObject entered, DoaObject enterer) {
				if (enterer instanceof Enemy enemy) {
					Data.setHealthDecay(Data.getHealthDecay() + 1);
				}
			}

			@Override
			public void onTriggerExit(DoaObject exited, DoaObject exiter) {
				if (exiter instanceof Enemy enemy) {
					Data.setHealthDecay(Data.getHealthDecay() - 1);
				}
			}
		}.makeTrigger());

		b.fixedRotation = true;
		b.debugRender = true;
		addComponent(b);

		addComponent(new PlayerMouse(this));

		setzOrder(Layers.PLAYER);
	}

	@Override
	public void onEventReceived(IEvent event) {
		if (event instanceof EnemyDied) {
			//EnemyDied ed = (EnemyDied) event.getEventData();

			//Data.setCoins((int)(Data.getCoins() + Math.max(1, Math.ceil(EnemySpawner.getDifficulty() / Math.PI))));
			//Data.setScore((int)(Data.getScore() + EnemySpawner.getDifficulty() * Math.PI));
		}
	}

	public class PlayerMouse extends DoaMouseAdapter {

		private static final long serialVersionUID = 1732779016300506422L;

		private DoaVector playerPos;
		private DoaVector mousePos = new DoaVector();

		private DoaVector toMouse;

		private Player owner;

		public PlayerMouse(Player owner) {
			this.owner = owner;
		}

		@Override
		public void tick() {
			super.tick();

			playerPos = owner.transform.position;
			mousePos.x = DoaCamera.getX() + DoaMouse.X;
			mousePos.y = DoaCamera.getY() + DoaMouse.Y;

			toMouse = DoaVector.sub(mousePos, playerPos);

			DoaTransform t = owner.rigidBody.getTransform();
			t.rotation = DoaMath.toDegress((float) Math.atan2(toMouse.y, toMouse.x));
			owner.rigidBody.setTransform(t);
		}

		@Override
		public void onMouse1Click() {
			DoaSpriteRenderer r = owner.getComponentByType(DoaSpriteRenderer.class).get();
			r.setSprite(DoaSprites.getSprite("PlayerSprite"));
		}

		@Override
		public void onMouse1Hold() { owner.Data.getWeapon().fire(playerPos, toMouse); }

		@Override
		public void onMouse1Release() {
			DoaSpriteRenderer r = owner.getComponentByType(DoaSpriteRenderer.class).get();
			r.setSprite(DoaSprites.getSprite("PlayerSprite2"));
		}
	}
}
