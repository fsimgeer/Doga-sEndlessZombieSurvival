package ui.shop;

import java.awt.Rectangle;

import components.ShopData;
import doa.engine.scene.DoaObject;
import renderers.ShopRenderer;

public class Shop extends DoaObject{
	private static final long serialVersionUID = -4966597756746116724L;

	public ShopData shopData;

	public static final int X = 100;
	public static final int Y = 100;
	public static final int SHOP_WIDTH = 1920 - 2 * X;
	public static final int SHOP_HEIGHT = 1080 - 2 * Y;

	public static final int DIST_BETWEEN_ITEMS = 30;
	public static final int COLUMN_ITEM_COUNT = 2;

	public final Rectangle closeButton = new Rectangle((int) X + SHOP_WIDTH - 120, (int) Y + SHOP_HEIGHT - 60, 100, 40);
	private ShopTab selectedTab;
	
	public Shop(ShopData shopData) {
		this.shopData = shopData;
		setzOrder(1000);
		addComponent(new ShopRenderer(this));
	}

	public void initializeTabs() {
		for (int i = 0; i < shopData.tabs.size(); i++) {
			ShopTab newTab = new ShopTab(this, i);
			newTab.setzOrder(getzOrder() + 1);
			if(i == 0) setSelectedTab(newTab);
			getScene().add(newTab);
		}
	}
	
	public void setSelectedTab(ShopTab tab) {
		selectedTab = tab;
	}

	public ShopTab getSelectedTab() {
		return selectedTab;
	}
}
