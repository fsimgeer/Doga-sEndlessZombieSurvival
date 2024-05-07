package main;

import java.awt.Color;

import doa.engine.core.DoaEngineSettings;
import doa.engine.core.DoaGame;
import doa.engine.core.DoaWindowMode;
import doa.engine.core.DoaWindowSettings;
import doa.engine.graphics.DoaLights;
import doa.engine.graphics.DoaSprites;
import doa.engine.log.DoaLogger;
import doa.engine.log.LogLevel;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaSceneHandler;
import event.EventDispatcher;
import util.Assets;
import util.LevelLoader;

public class Main extends DoaGame {

	private EventDispatcher dispatcher = new EventDispatcher();
	private LevelLoader loader = new LevelLoader(dispatcher);

	public static void main(final String[] args) { launch(); }

	@Override
	public void initialize(DoaEngineSettings eSettings, DoaWindowSettings wSettings, String... args) {
		DoaLogger.getInstance().setLevel(LogLevel.FINEST);
		DoaLights.ambientLight(new Color(255, 255, 255));
		Assets.initializeAssets();
		DoaSceneHandler.loadScene(DoaSceneHandler.createScene("gameScene"));

		loader.loadLevel(DoaSprites.ORIGINAL_SPRITES.get("mapData"), DoaSprites.getSprite("map"));

		wSettings.TITLE = "DOA's Endless Zombie Survival!";
		wSettings.RESOLUTION_OD = new DoaVector(1920, 1080);
		wSettings.WM = DoaWindowMode.WINDOWED;
	}
}
