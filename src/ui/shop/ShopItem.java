package ui.shop;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaMouse;

import gameplay.Player;

public class ShopItem {

	private String name;
	private Rectangle bounds;
	private Rectangle innerBounds;
	private int numOfUpgrades;
	private int itemUpgrade = 0;
	private Rectangle[] itemUpgradeBoxes;
	private List<Rectangle> itemUpgradeBoxesFill = new ArrayList<>();
	private ShopItemAction action;
	private int[] costs;
	private boolean isMouseHovering = false;
	private String picture = "UnknownItem";
	
	public ShopItem(String name, int numOfUpgrades, ShopItemAction a) {
		this.setName(name);
		this.setNumOfUpgrades(numOfUpgrades);
		this.setAction(a);
		setItemUpgradeBoxes(new Rectangle[numOfUpgrades]);
		setCosts(new int[numOfUpgrades]);
		
		/*for(int i = 0; i < numOfUpgrades; i++) {
			getCosts()[i] = i + 1;
		}*/
	}
	
	public ShopItem(String name, ShopItemAction a) {
		this.setName(name);
		this.setNumOfUpgrades(-1);
		this.setAction(a);
		//setCosts(new int[numOfUpgrades]);
		
		/*for(int i = 0; i < numOfUpgrades; i++) {
			getCosts()[i] = i + 1;
		}*/
	}
	
	public void tick() {
		if(getBounds().contains(DoaMouse.X, DoaMouse.Y)) {
			isMouseHovering = true;
		} else {
			isMouseHovering = false;
		}
		if (DoaMouse.MB1 && this.getBounds().intersects(new Rectangle((int) DoaMouse.X, (int) DoaMouse.Y, 1, 1))) {
			final Point mousePoint = new Point((int) DoaMouse.X, (int) DoaMouse.Y);
			if(getBounds().contains(mousePoint) && itemUpgrade < numOfUpgrades && Player.getInstance().getCoins() >= costs[itemUpgrade]) {
				getItemUpgradeBoxesFill().add(new Rectangle(itemUpgradeBoxes[itemUpgrade].x + 2, itemUpgradeBoxes[itemUpgrade].y + 2, getItemUpgradeBoxes()[getItemUpgrade()].width - 4, getItemUpgradeBoxes()[getItemUpgrade()].height - 4));
				Player.getInstance().setCoins(Player.getInstance().getCoins() - costs[itemUpgrade]);
				setItemUpgrade(getItemUpgrade() + 1);
				getAction().execute();
			}
		}
	}
	
	public void render(final DoaGraphicsContext g) {
		drawPicture(g);
		
		int a = (int) (innerBounds.x + 50 + (innerBounds.getMaxY() - innerBounds.getMinY()) * 3 / 4) + 20;
		int b = (int) (innerBounds.y + (innerBounds.getCenterY() - innerBounds.getMinY()) / 4) + 22;
		
		g.setColor(new Color(0, 0, 0, 100));
		g.drawString(name.toUpperCase(), a, b);
		if(itemUpgrade < numOfUpgrades) {
			g.drawString("Cost: " + costs[itemUpgrade], a, b + 50);
		}else {
			g.drawString("Cost: MAX", a, b + 50);
		}
		
		g.setColor(new Color(0, 0, 0, 100));
		for(int i = 0; i < getNumOfUpgrades(); i++) {
			g.fill(getItemUpgradeBoxes()[i]);
		}
		
		g.setColor(new Color(255, 255, 255, 100));
		getItemUpgradeBoxesFill().stream().forEach(el -> g.fill(el));
	}

	public void drawPicture(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get(picture), innerBounds.x + 50, innerBounds.y + (innerBounds.getCenterY() - innerBounds.getMinY()) / 4,
				(innerBounds.getMaxY() - innerBounds.getMinY()) * 3 / 4, (innerBounds.getMaxY() - innerBounds.getMinY()) * 3/ 4);
		if(getMouseHovering()) {
			g.setColor(new Color(200, 200, 100, 100));
		} else {
			g.setColor(new Color(100, 200, 200, 100));
		}
		g.fill(getBounds());
		
		if(getMouseHovering()) {
			g.setColor(new Color(160, 200, 239, 100));
		} else {
			g.setColor(new Color(200, 200, 200, 100));
		}
		g.fill(getInnerBounds());
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getItemUpgrade() {
		return itemUpgrade;
	}

	public void setItemUpgrade(int itemUpgrade) {
		this.itemUpgrade = itemUpgrade;
	}

	public int getNumOfUpgrades() {
		return numOfUpgrades;
	}

	public void setNumOfUpgrades(int numOfUpgrades) {
		this.numOfUpgrades = numOfUpgrades;
	}

	public int[] getCosts() {
		return costs;
	}

	public void setCosts(int[] costs) {
		this.costs = costs;
	}

	public Rectangle[] getItemUpgradeBoxes() {
		return itemUpgradeBoxes;
	}

	public void setItemUpgradeBoxes(Rectangle[] itemUpgradeBoxes) {
		this.itemUpgradeBoxes = itemUpgradeBoxes;
	}

	public List<Rectangle> getItemUpgradeBoxesFill() {
		return itemUpgradeBoxesFill;
	}

	public void setItemUpgradeBoxesFill(List<Rectangle> itemUpgradeBoxesFill) {
		this.itemUpgradeBoxesFill = itemUpgradeBoxesFill;
	}

	public ShopItemAction getAction() {
		return action;
	}

	public void setAction(ShopItemAction action) {
		this.action = action;
	}
	
	public Rectangle getInnerBounds() {
		return innerBounds;
	}
	
	public void setInnerBounds(Rectangle r) {
		this.innerBounds = r;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public boolean getMouseHovering() {
		return isMouseHovering;
	}
	
	public void setMouseHovering(boolean b) {
		isMouseHovering = b;
	}
	
}
