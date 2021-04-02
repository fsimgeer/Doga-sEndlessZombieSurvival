package main;

import java.awt.Color;

import doa.engine.core.DoaEngineSettings;
import doa.engine.core.DoaGame;
import doa.engine.core.DoaWindowSettings;
import doa.engine.graphics.DoaLights;
import doa.engine.graphics.DoaSprites;
import doa.engine.log.DoaLogger;
import doa.engine.log.LogLevel;
import doa.engine.scene.DoaSceneHandler;
import util.Assets;
import util.LevelLoader;

public class Main extends DoaGame {

	public static void main(final String[] args) { launch(); }

	@Override
	public void initialize(DoaEngineSettings eSettings, DoaWindowSettings wSettings, String... args) {
		DoaLogger.getInstance().setLevel(LogLevel.FINEST);
		DoaLights.ambientLight(new Color(255, 255, 255));
		Assets.initializeAssets();
		DoaSceneHandler.loadScene(DoaSceneHandler.createScene("gameScene"));
		LevelLoader.loadLevel(DoaSprites.ORIGINAL_SPRITES.get("mapData"), DoaSprites.getSprite("map"));
		wSettings.TITLE = "DOA's Endless Zombie Survival!";
		// wSettings.DM = wSettings.SCREEN.getDisplayModes()[40];
	}
}
