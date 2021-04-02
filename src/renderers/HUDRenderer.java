package renderers;

import static doa.engine.core.DoaGraphicsFunctions.drawRect;
import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fillRect;
import static doa.engine.core.DoaGraphicsFunctions.popStroke;
import static doa.engine.core.DoaGraphicsFunctions.popTransform;
import static doa.engine.core.DoaGraphicsFunctions.pushStroke;
import static doa.engine.core.DoaGraphicsFunctions.pushTransform;
import static doa.engine.core.DoaGraphicsFunctions.rotate;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;
import static doa.engine.core.DoaGraphicsFunctions.setStroke;

import java.awt.BasicStroke;
import java.awt.Color;

import components.PlayerData;
import doa.engine.graphics.DoaFonts;
import doa.engine.scene.elements.renderers.DoaRenderer;
import objects.EnemySpawner;
import objects.Player;

public class HUDRenderer extends DoaRenderer {

	private static final long serialVersionUID = -8237946402686130597L;

	private static final Color TRANSLUCENT_GRAY = new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 127);
	private static final Color TRANSLUCENT_GREEN = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), 127);
	private static final Color TRANSLUCENT_DARK_GREEN = new Color(Color.GREEN.darker().darker().darker().getRed(), Color.GREEN.darker().darker().darker().getGreen(), Color.GREEN.darker().darker().darker().getBlue(), 200);
	private static final Color GRAY = new Color(74, 74, 74);
	private static final Color ORANGE = new Color(232, 106, 23);

	private static final String COINS = "Coins: ";
	private static final String LEVEL = "Level: ";
	private static final String SCORE = "Score: ";

	public HUDRenderer() {}

	@Override
	public void render() {
		PlayerData pd = (PlayerData) Player.getInstance().getComponentByType(PlayerData.class).get();
		pushTransform();
		rotate((float) Math.PI, 1920f / 2, 1080f / 2);
		setColor(TRANSLUCENT_GRAY);
		fillRect(1920 - 60 - 3, 20 - 3, 50 + 6, pd.getHealthMAX() + 6);
		setColor(TRANSLUCENT_GREEN);
		fillRect(1920 - 60, 20, 50, pd.health);
		pushStroke();
		setStroke(new BasicStroke(4));
		setColor(TRANSLUCENT_DARK_GREEN);
		drawRect(1920 - 60, 20, 50, pd.health);
		popStroke();
		popTransform();

		setFont(DoaFonts.getFont("Soup").deriveFont(36f));

		setColor(GRAY);
		drawString(COINS + pd.coins, 69, 1080 - 81);
		drawString(COINS + pd.coins, 69, 1080 - 79);
		drawString(COINS + pd.coins, 71, 1080 - 81);
		drawString(COINS + pd.coins, 71, 1080 - 79);
		drawString(LEVEL + EnemySpawner.getDifficulty(), 69, 1080 - 51);
		drawString(LEVEL + EnemySpawner.getDifficulty(), 69, 1080 - 49);
		drawString(LEVEL + EnemySpawner.getDifficulty(), 71, 1080 - 51);
		drawString(LEVEL + EnemySpawner.getDifficulty(), 71, 1080 - 49);
		drawString(SCORE + pd.score, 69, 1080 - 21);
		drawString(SCORE + pd.score, 69, 1080 - 19);
		drawString(SCORE + pd.score, 71, 1080 - 21);
		drawString(SCORE + pd.score, 71, 1080 - 19);

		setColor(ORANGE);
		drawString(COINS + pd.coins, 70, 1080 - 80);
		drawString(LEVEL + EnemySpawner.getDifficulty(), 70, 1080 - 50);
		drawString(SCORE + pd.score, 70, 1080 - 20);
	}

}
