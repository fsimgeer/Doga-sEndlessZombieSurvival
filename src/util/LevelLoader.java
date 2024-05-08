package util;

import java.awt.Color;
import java.awt.image.BufferedImage;

import doa.engine.core.DoaCamera;
import doa.engine.scene.DoaObject;
import doa.engine.scene.DoaScene;
import doa.engine.scene.DoaSceneHandler;
import doa.engine.scene.elements.renderers.DoaSpriteRenderer;
import event.EventDispatcher;
import globals.Layers;
import objects.EnemySpawner;
import objects.Player;
import objects.Wall;
import renderers.HUDRenderer;
import ui.shop.ShopLoader;

public class LevelLoader {

	public static final int BLOCK_WIDTH = 32;
	public static final int BLOCK_HEIGHT = 32;

	private EventDispatcher dispatcher;

	public LevelLoader(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void loadLevel(final BufferedImage mapData, final BufferedImage map) {
		DoaScene loaded = DoaSceneHandler.getLoadedScene();
		loaded.clear();
		Player p = new Player(300, 300);
		loaded.add(p);
		dispatcher.RegisterListener(p);
		DoaCamera.adjustCamera(p, 0, 0, map.getWidth(), map.getHeight());
		DoaCamera.setTweenAmountX(0.01f);
		DoaCamera.setTweenAmountY(0.01f);

		EnemySpawner es = new EnemySpawner(dispatcher, p.transform);
		loaded.add(es);
		dispatcher.RegisterListener(es);
		final int w = mapData.getWidth();
		final int h = mapData.getHeight();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Color pixelColor = new Color(mapData.getRGB(x, y));
				final int red = pixelColor.getRed();
				final int green = pixelColor.getGreen();
				final int blue = pixelColor.getBlue();
				DoaObject o = null;
				if (pixelColor.equals(Color.WHITE)) {
					o = new Wall(x * BLOCK_WIDTH, y * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
				} else if (green == 255 && blue == 255) {
					// o = Builders.PB.args((float) x * BLOCK_WIDTH, (float) y *
					// BLOCK_HEIGHT).instantiate();
				} else if (red == 255 && blue == 255) {
					es.createNewSpawnerAt(x * BLOCK_WIDTH, y * BLOCK_HEIGHT);
				}
				if (o != null) {
					loaded.add(o);
				}
			}
		}
		DoaObject level = new DoaObject();
		level.setzOrder(Layers.BACKGROUND);

		DoaSpriteRenderer r = new DoaSpriteRenderer();
		r.setSprite(map);
		level.addComponent(r);

		loaded.add(level);

		DoaObject hud = new DoaObject().makeStatic();
		HUDRenderer hudr = new HUDRenderer(p.Data);
		hud.addComponent(hudr);
		loaded.add(hud);
		dispatcher.RegisterListener(hudr);

		ShopLoader.createShop(loaded);
	}
}
