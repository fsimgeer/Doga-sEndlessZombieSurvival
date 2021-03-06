package ui.shop;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.input.DoaMouse;
import com.doa.engine.task.DoaTaskGuard;
import com.doa.engine.task.DoaTasker;

import gameplay.Player;
import gameplay.weapon.IWeapon;

public class Gun extends ShopItem {

	private IWeapon w;
	private int[][] costs;
	private int alpha = 255;
	private DoaTaskGuard alphaTimeGuard = new DoaTaskGuard();

	private boolean alphaBool = false;

	private Rectangle equipButton = new Rectangle(100, 100, 100, 40);
	private boolean equipped = false;

	public Gun(String name, int numOfUpgrades, ShopItemAction a, IWeapon w, boolean equipped) {
		super(name, numOfUpgrades, a);
		this.w = w;
		this.equipped = equipped;

		costs = new int[5][numOfUpgrades];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < numOfUpgrades; j++) {
				costs[i][j] = (j + 1) + i;
			}
		}
	}

	@Override
	public void tick() {
		final Point mousePoint = new Point((int) DoaMouse.X, (int) DoaMouse.Y);
		if (getBounds().contains(mousePoint)) {
			setMouseHovering(true);
		} else {
			setMouseHovering(false);
		}
		DoaTasker.guardExecution(() -> {
			if (!alphaBool) {
				alpha -= 6;
			} else {
				alpha += 6;
			}
			if (alpha < 0) {
				alphaBool = true;
				alpha += 6;
			} else if (alpha > 255) {
				alphaBool = false;
				alpha -= 6;
			}
		}, alphaTimeGuard, 10);

		if (DoaMouse.MB1 && this.getBounds().intersects(new Rectangle((int) DoaMouse.X, (int) DoaMouse.Y, 1, 1))) {
			if (equipButton.contains(mousePoint)) {
				for (int i = 0; i < Shop.getGunList().length; i++) {
					Shop.getGunList()[i].setEquipped(false);
				}
				equipped = true;
				Player.getInstance().setWeapon(w);
			} else if (getBounds().contains(mousePoint) && getItemUpgrade() < getNumOfUpgrades() && Player.getInstance().getCoins() >= getCosts()[getItemUpgrade()]) {
				getItemUpgradeBoxesFill().add(new Rectangle(getItemUpgradeBoxes()[getItemUpgrade()].x + 2, getItemUpgradeBoxes()[getItemUpgrade()].y + 2,
				        getItemUpgradeBoxes()[getItemUpgrade()].width - 4, getItemUpgradeBoxes()[getItemUpgrade()].height - 4));
				Player.getInstance().setCoins(Player.getInstance().getCoins() - getCosts()[getItemUpgrade()]);
				setItemUpgrade(getItemUpgrade() + 1);
				getAction().execute();

				Rectangle2D.Float dimensions = w.getDimensions();
				w.setDimensions((int) dimensions.getWidth() + costs[0][getItemUpgrade()], (int) dimensions.getHeight() + costs[1][getItemUpgrade()]);
				w.setAttackSpeed(w.getAttackSpeed() + costs[2][getItemUpgrade()]);
				w.setBulletTravelSpeed(w.getBulletTravelSpeed() + costs[3][getItemUpgrade()]);
				w.setBulletDamage(w.getBulletDamage() + costs[4][getItemUpgrade()]);
			}
		}
	}

	@Override
	public void render(final DoaGraphicsContext g) {
		drawPicture(g);

		int a = (int) (getInnerBounds().x + 50 + (getInnerBounds().getMaxY() - getInnerBounds().getMinY()) * 3 / 4) + 20;
		int b = (int) (getInnerBounds().y + (getInnerBounds().getCenterY() - getInnerBounds().getMinY()) / 4) + 22;

		g.setColor(new Color(0, 0, 0, 100));
		g.drawString(getName().toUpperCase(), a, b);
		if (getItemUpgrade() < getNumOfUpgrades()) {
			g.drawString("Cost: " + getCosts()[getItemUpgrade()], a, b + 50);
		} else {
			g.drawString("Cost: MAX", a, b + 50);
		}

		g.setColor(new Color(0, 0, 0, 100));
		g.drawString("Velocity: " + w.getBulletTravelSpeed(), a, b + 100);
		g.setColor(new Color(0, 255, 0, alpha));
		g.drawString((getItemUpgrade() < getNumOfUpgrades()) ? "+" + costs[2][getItemUpgrade()] : "", a + ("Velocity: ".toUpperCase() + w.getBulletTravelSpeed()).length() * 18,
		        b + 100);

		g.setColor(new Color(0, 0, 0, 100));
		g.drawString("Attack Speed: " + w.getAttackSpeed(), a, b + 150);
		g.setColor(new Color(0, 255, 0, alpha));
		g.drawString((getItemUpgrade() < getNumOfUpgrades()) ? "-" + costs[3][getItemUpgrade()] : "", a + ("Cooldown: ".toUpperCase() + w.getAttackSpeed()).length() * 18, b + 150);

		g.setColor(new Color(0, 0, 0, 100));
		g.drawString("Damage: " + w.getBulletDamage(), a, b + 200);
		g.setColor(new Color(0, 255, 0, alpha));
		g.drawString((getItemUpgrade() < getNumOfUpgrades()) ? "+" + costs[4][getItemUpgrade()] : "", a + ("Damage: ".toUpperCase() + w.getBulletDamage()).length() * 18, b + 200);

		g.setColor(new Color(0, 0, 0, 100));
		for (int i = 0; i < getNumOfUpgrades(); i++) {
			g.fill(getItemUpgradeBoxes()[i]);
		}

		g.setColor(new Color(255, 255, 255, 100));
		getItemUpgradeBoxesFill().stream().forEach(el -> g.fill(el));

		g.setColor(Color.YELLOW.darker());
		g.fill(equipButton);
		g.setColor(Color.BLACK);
		if (equipped) {
			g.setColor(Color.YELLOW.darker().darker());
			g.fill(equipButton);
			g.setColor(Color.BLACK);
			g.drawString("EQUIPPED", (int) getInnerBounds().getMaxX() - 165, (int) getInnerBounds().getMaxY() - 30);
		} else {
			g.setColor(Color.YELLOW.darker());
			g.fill(equipButton);
			g.setColor(Color.BLACK);
			g.drawString("EQUIP", (int) getInnerBounds().getMaxX() - 140, (int) getInnerBounds().getMaxY() - 30);
		}
	}

	public void setEquipButton(Rectangle r) {
		equipButton = r;
	}

	public boolean getEquipped() {
		return equipped;
	}

	public void setEquipped(boolean e) {
		equipped = e;
	}
}