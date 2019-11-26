package ui.shop;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaMouse;
import com.doa.engine.task.DoaTaskGuard;
import com.doa.engine.task.DoaTasker;

import gameplay.BulletSpecs;
import gameplay.Player;

public class Gun extends ShopItem{
	
	private transient BulletSpecs bs = new BulletSpecs();
	private int[][] costs;
	private int alpha = 255;
	private DoaTaskGuard alphaTimeGuard = new DoaTaskGuard();
	
	private boolean alphaBool = false;
	
	private Rectangle equipButton = new Rectangle(100, 100, 100, 40);
	private boolean equipped = false;
	
	public Gun(String name, int numOfUpgrades, ShopItemAction a, BulletSpecs bs, boolean equipped) {
		super(name, numOfUpgrades, a);
		this.bs = bs;
		this.equipped = equipped;
		
		costs = new int[5][numOfUpgrades];
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < numOfUpgrades; j++) {
				costs[i][j] = (j + 1) + i;
			}
		}
	}

	public void tick() {
		final Point mousePoint = new Point((int) DoaMouse.X, (int) DoaMouse.Y);
		if(getBounds().contains(mousePoint)) {
			setMouseHovering(true);
		} else {
			setMouseHovering(false);
		}
		DoaTasker.guardExecution(() -> {
				if(!alphaBool) {
					alpha-=6;
				}else {
					alpha+=6;
				}
				if(alpha < 0) {
					alphaBool = true;
					alpha+=6;
				}
				else if(alpha > 255) {
					alphaBool = false;
					alpha-=6;
				}
			}, alphaTimeGuard, 10);
		
		if (DoaMouse.MB1 && this.getBounds().intersects(new Rectangle((int) DoaMouse.X, (int) DoaMouse.Y, 1, 1))) {
			if(equipButton.contains(mousePoint)) {
				for(int i = 0; i < Shop.getGunList().length; i++) {
					Shop.getGunList()[i].setEquipped(false);
				}
				equipped = true;
				Player.getInstance().setBulletSpecs(bs);
			} else if(getBounds().contains(mousePoint) && getItemUpgrade() < getNumOfUpgrades() && Player.getInstance().getCoins() >= getCosts()[getItemUpgrade()]) {
				getItemUpgradeBoxesFill().add(new Rectangle(getItemUpgradeBoxes()[getItemUpgrade()].x + 2,getItemUpgradeBoxes()[getItemUpgrade()].y + 2, getItemUpgradeBoxes()[getItemUpgrade()].width - 4, getItemUpgradeBoxes()[getItemUpgrade()].height - 4));
				Player.getInstance().setCoins(Player.getInstance().getCoins() - getCosts()[getItemUpgrade()]);
				setItemUpgrade(getItemUpgrade() + 1);
				getAction().execute();
				
				bs.setWidth(bs.getWidth() + costs[0][getItemUpgrade()]);
				bs.setHeight(bs.getHeight() + costs[1][getItemUpgrade()]);
				bs.setCooldown(bs.getCooldown() - costs[2][getItemUpgrade()]);
				bs.setVelocity(bs.getVelocity() + costs[3][getItemUpgrade()]);
				bs.setDamage(bs.getDamage() + costs[4][getItemUpgrade()]);
			}
		}
	}
	
	public void render(final DoaGraphicsContext g) {
		drawPicture(g);
		
		int a = (int) (getInnerBounds().x + 50 + (getInnerBounds().getMaxY() - getInnerBounds().getMinY()) * 3 / 4) + 20;
		int b = (int) (getInnerBounds().y + (getInnerBounds().getCenterY() - getInnerBounds().getMinY()) / 4) + 22;
		
		g.setColor(new Color(0, 0, 0, 100));
		g.drawString(getName().toUpperCase(), a, b);
		if(getItemUpgrade() < getNumOfUpgrades()) {
			g.drawString("Cost: " + getCosts()[getItemUpgrade()],  a, b + 50);
		}else {
			g.drawString("Cost: MAX",  a, b + 50);
		}

		g.setColor(new Color(0, 0, 0, 100));
		g.drawString("Velocity: " + bs.getVelocity(),  a, b + 100);
		g.setColor(new Color(0, 255, 0, alpha));
		g.drawString((getItemUpgrade() < getNumOfUpgrades()) ? "+" + costs[2][getItemUpgrade()] :
			"", a + ("Velocity: ".toUpperCase() + bs.getVelocity()).length() * 18, b + 100);

		g.setColor(new Color(0, 0, 0, 100));
		g.drawString("Cooldown: " + bs.getCooldown(),  a, b + 150);
		g.setColor(new Color(0, 255, 0, alpha));
		g.drawString((getItemUpgrade() < getNumOfUpgrades()) ? "-" + costs[3][getItemUpgrade()] :
			"", a + ("Cooldown: ".toUpperCase() + bs.getCooldown()).length() * 18, b + 150);

		g.setColor(new Color(0, 0, 0, 100));
		g.drawString("Damage: " + bs.getDamage(),  a, b + 200);
		g.setColor(new Color(0, 255, 0, alpha));
		g.drawString((getItemUpgrade() < getNumOfUpgrades()) ? "+" + costs[4][getItemUpgrade()] :
			"", a + ("Damage: ".toUpperCase() + bs.getDamage()).length() * 18, b + 200);
		
		g.setColor(new Color(0, 0, 0, 100));
		for(int i = 0; i < getNumOfUpgrades(); i++) {
			g.fill(getItemUpgradeBoxes()[i]);
		}
		
		g.setColor(new Color(255, 255, 255, 100));
		getItemUpgradeBoxesFill().stream().forEach(el -> g.fill(el));
		
		g.setColor(Color.YELLOW.darker());
		g.fill(equipButton);
		g.setColor(Color.BLACK);
		if(equipped) {
			g.setColor(Color.YELLOW.darker().darker());
			g.fill(equipButton);
			g.setColor(Color.BLACK);
			g.drawString("EQUIPPED", (int) getInnerBounds().getMaxX() - 165, (int) getInnerBounds().getMaxY() - 30);
		}else {
			g.setColor(Color.YELLOW.darker());
			g.fill(equipButton);
			g.setColor(Color.BLACK);
			g.drawString("EQUIP", (int) getInnerBounds().getMaxX() - 140, (int) getInnerBounds().getMaxY() - 30);
		}
	}
	
	public BulletSpecs getBS() {
		return bs;
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
