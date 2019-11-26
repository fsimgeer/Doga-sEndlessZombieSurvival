package ui.shop;

import java.awt.Color;
import java.awt.Rectangle;

import com.doa.engine.graphics.DoaGraphicsContext;

public class ShopTab {

	private int columnItemCount;
	private String name;
	private Rectangle bounds;
	private boolean isActive = false;
	private ShopItem[] items;
	
	public ShopTab(String name, int c, Rectangle bounds, ShopItem[] items) {
		this.name = name;
		this.columnItemCount = c;
		this.bounds = bounds;
		this.items = items;
		
		int width = Shop.SHOP_WIDTH;
		int height = Shop.SHOP_HEIGHT - 110;
		int x = (int) Shop.X;
		int y = (int) Shop.Y + 50;
		int rowCount = (items.length % columnItemCount == 0) ? (items.length / columnItemCount) : (items.length / columnItemCount) + 1;
		int itemWidth = (width - (columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS) / columnItemCount;
		int itemHeight = (rowCount > 0) ? (height - (rowCount + 1) * Shop.DIST_BETWEEN_ITEMS) / rowCount : 1;
		for(int i = 0; i < this.items.length; i++) {
			this.items[i].setBounds(new Rectangle((int) x + (i % columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i % columnItemCount) * itemWidth, (int) y + (i / columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i / columnItemCount) * itemHeight, itemWidth, itemHeight));
		}
		
		int innerBounds = 10;
		for(int i = 0; i < this.items.length; i++) {
			items[i].setInnerBounds(new Rectangle((int) x + (i % columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i % columnItemCount) * itemWidth + innerBounds, (int) y + (i / columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i / columnItemCount) * itemHeight + innerBounds, itemWidth - 2 * innerBounds, itemHeight - 2 * innerBounds));
		}
		
		for(int i = 0; i < this.items.length; i++) {
			int a = (int) (items[i].getInnerBounds().x + 50 + (items[i].getInnerBounds().getMaxY() - items[i].getInnerBounds().getMinY()) * 3 / 4) + 20;
			for(int j = 0; j < items[i].getNumOfUpgrades(); j++) {
				items[i].getItemUpgradeBoxes()[j] = new Rectangle(a + 40 * j, (int) items[i].getInnerBounds().getMaxY() - 50, 20, 20);
			}
		}
	}
	
	public ShopTab(String name, int c, Rectangle bounds, Gun[] items) {
		this.name = name;
		this.columnItemCount = c;
		this.bounds = bounds;
		this.items = items;
		
		int width = Shop.SHOP_WIDTH;
		int height = Shop.SHOP_HEIGHT - 110;
		int x = (int) Shop.X;
		int y = (int) Shop.Y + 50;
		int rowCount = (items.length % columnItemCount == 0) ? (items.length / columnItemCount) : (items.length / columnItemCount) + 1;
		int itemWidth = (width - (columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS) / columnItemCount;
		int itemHeight = (rowCount > 0) ? (height - (rowCount + 1) * Shop.DIST_BETWEEN_ITEMS) / rowCount : 1;
		for(int i = 0; i < this.items.length; i++) {
			this.items[i].setBounds(new Rectangle((int) x + (i % columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i % columnItemCount) * itemWidth,
					(int) y + (i / columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i / columnItemCount) * itemHeight, itemWidth, itemHeight));
		}
		
		int innerBounds = 10;
		for(int i = 0; i < this.items.length; i++) {
			items[i].setInnerBounds(new Rectangle((int) x + (i % columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i % columnItemCount) * itemWidth + innerBounds,
					(int) y + (i / columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (i / columnItemCount) * itemHeight + innerBounds, itemWidth - 2 * innerBounds, itemHeight - 2 * innerBounds));
		}
		
		for(int i = 0; i < this.items.length; i++) {
			items[i].setEquipButton(new Rectangle((int) items[i].getInnerBounds().getMaxX() - 170, (int) items[i].getInnerBounds().getMaxY() - 60, 150, 40));
		}
		
		for(int i = 0; i < this.items.length; i++) {
			int a = (int) (items[i].getInnerBounds().x + 50 + (items[i].getInnerBounds().getMaxY() - items[i].getInnerBounds().getMinY()) * 3 / 4) + 20;
			for(int j = 0; j < items[i].getNumOfUpgrades(); j++) {
				items[i].getItemUpgradeBoxes()[j] = new Rectangle(a + 40 * j, (int) items[i].getInnerBounds().getMaxY() - 50, 20, 20);
			}
		}
	}
	
	public void tick() {
		if(isActive) {
			for(int i = 0; i < items.length; i++) {
				items[i].tick();
			}
		}
	}
	
	public void render(final DoaGraphicsContext g) {
		// render title
		// if active, render shop items
		g.setColor(Color.YELLOW.darker());
		g.fill(this.getBounds());
		if(isActive) {
			g.setColor(Color.YELLOW.darker().darker());
			g.fill(this.getBounds());
			for(int i = 0; i < items.length; i++) {
				items[i].render(g);
			}
		}
		g.setColor(Color.BLACK);
		g.drawString(this.name.toUpperCase(), this.bounds.getMinX() + 10, this.bounds.getMaxY() - 10);
		
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean b) {
		isActive = b;
	}
}
