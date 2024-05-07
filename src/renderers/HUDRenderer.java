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
import event.IEvent;
import event.IEventListener;
import event.PlayerDataChanged;
import event.WaveStarted;
import objects.EnemySpawner;

public class HUDRenderer extends DoaRenderer implements IEventListener {

	private static final long serialVersionUID = -8237946402686130597L;

	private static final Color TRANSLUCENT_GRAY = new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 127);
	private static final Color TRANSLUCENT_GREEN = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), 127);
	private static final Color TRANSLUCENT_DARK_GREEN = new Color(Color.GREEN.darker().darker().darker().getRed(), Color.GREEN.darker().darker().darker().getGreen(), Color.GREEN.darker().darker().darker().getBlue(), 200);
	private static final Color GRAY = new Color(74, 74, 74);
	private static final Color ORANGE = new Color(232, 106, 23);

	private static final String COINS = "Coins: ";
	private static final String LEVEL = "Level: ";
	private static final String SCORE = "Score: ";

	private float playerHealth;
	private float playerHealthMax;
	private float playerCoins;
	private float playerScore;

	private int waveNumber;

	@Override
	public void render() {
		pushTransform();
		rotate((float) Math.PI, 1920f / 2, 1080f / 2);
		setColor(TRANSLUCENT_GRAY);
		fillRect(1920 - 60 - 3, 20 - 3, 50 + 6, playerHealthMax + 6);
		setColor(TRANSLUCENT_GREEN);
		fillRect(1920 - 60, 20, 50, playerHealth);
		pushStroke();
		setStroke(new BasicStroke(4));
		setColor(TRANSLUCENT_DARK_GREEN);
		drawRect(1920 - 60, 20, 50, playerHealth);
		popStroke();
		popTransform();

		setFont(DoaFonts.getFont("Soup").deriveFont(36f));

		setColor(GRAY);
		drawString(COINS + playerCoins, 69, 1080 - 81);
		drawString(COINS + playerCoins, 69, 1080 - 79);
		drawString(COINS + playerCoins, 71, 1080 - 81);
		drawString(COINS + playerCoins, 71, 1080 - 79);
		drawString(LEVEL + waveNumber, 69, 1080 - 51);
		drawString(LEVEL + waveNumber, 69, 1080 - 49);
		drawString(LEVEL + waveNumber, 71, 1080 - 51);
		drawString(LEVEL + waveNumber, 71, 1080 - 49);
		drawString(SCORE + playerScore, 69, 1080 - 21);
		drawString(SCORE + playerScore, 69, 1080 - 19);
		drawString(SCORE + playerScore, 71, 1080 - 21);
		drawString(SCORE + playerScore, 71, 1080 - 19);

		setColor(ORANGE);
		drawString(COINS + playerCoins, 70, 1080 - 80);
		drawString(LEVEL + waveNumber, 70, 1080 - 50);
		drawString(SCORE + playerScore, 70, 1080 - 20);
	}

	@Override
	public void onEventReceived(IEvent event) {
		if (event instanceof PlayerDataChanged) {
			PlayerData pd = (PlayerData) event.getEventData();
			playerHealth = pd.getHealth();
			playerHealthMax = pd.getHealthMAX();
			playerCoins = pd.getCoins();
			playerScore = pd.getScore();
		}
		if (event instanceof WaveStarted) {
			EnemySpawner es = (EnemySpawner) event.getEventData();
			waveNumber = es.Difficulty;
		}
	}
}
