package util;

import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.io.IOException;

import doa.engine.graphics.DoaFonts;
import doa.engine.graphics.DoaSprites;

public final class Assets {

	public static void initializeAssets() {
		try {
			DoaSprites.createSpriteFromSpriteSheet("PlayerSprite", "/KenneyAssets/Spritesheet/spritesheet_characters.png", new Rectangle(2, 132, 51, 43));
			DoaSprites.createSpriteFromSpriteSheet("PlayerSprite2", "/KenneyAssets/Spritesheet/spritesheet_characters.png", new Rectangle(313, 132, 39, 43));
			DoaSprites.createSpriteFromSpriteSheet("EnemySprite", "/KenneyAssets/Spritesheet/spritesheet_characters.png", new Rectangle(426, 0, 33, 43));
			DoaSprites.createSprite("map", "/level-test3-bg.png");
			DoaSprites.createSprite("mapData", "/level-test3.png");
			DoaSprites.createSprite("UnknownItem", "/SimgeAssets/unknown.png");

			int k = 0;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					DoaSprites.createSpriteFromSpriteSheet("light" + k, "/lights.png", new Rectangle(i * 128, j * 128, 128, 128));
					k++;
				}
			}

			DoaFonts.createFont("Soup", "/sojaAssets/soupofjustice.ttf");
		} catch (FontFormatException | IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	private Assets() {}
}
