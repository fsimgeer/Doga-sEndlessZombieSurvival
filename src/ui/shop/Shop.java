package ui.shop;

import java.awt.Rectangle;
import java.util.ArrayList;

import components.ShopData;
import doa.engine.scene.DoaObject;
import ui.shop.renderers.ShopRenderer;

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
	private int selectedTab;
	
	private ArrayList<ShopTab> tabs = new ArrayList<ShopTab>();
	
	final int tabTitleWidth;
	final int shopTitleHeight = 50;
	final Rectangle bounds = new Rectangle(Shop.X, Shop.Y + shopTitleHeight, Shop.SHOP_WIDTH, Shop.SHOP_HEIGHT - shopTitleHeight);
	
	public Shop(ShopData shopData) {
		this.shopData = shopData;
		setzOrder(1000);
		addComponent(new ShopRenderer(this));
		
		tabTitleWidth = Shop.SHOP_WIDTH / shopData.tabs.size();
	}

	public void initializeTabs() {
		int tab1ColCount = 2;
		int tab2ColCount = 3;
		int tab3ColCount = 2;
		int tab4ColCount = 2;
		
		CreateShopTab(tab1ColCount);
		CreateShopTab(tab2ColCount);
		CreateShopTab(tab3ColCount);
		CreateShopTab(tab4ColCount);
		
		setSelectedTab(0);
	}
	
	private void CreateShopTab(int colCount) {
		int index = tabs.size();
		
		Rectangle titleBounds = new Rectangle(Shop.X + index * tabTitleWidth, Shop.Y, tabTitleWidth, shopTitleHeight);
		ShopTab tab = new ShopTab(this, colCount, shopData.tabs.get(index), titleBounds, bounds);
		
		tab.setzOrder(getzOrder() + 1);
		getScene().add(tab);
		
		tabs.add(tab);
	}
	
	public ShopTab getSelectedTab() {
		return tabs.get(selectedTab);
	}

	public void setSelectedTab(int index) {
		selectedTab = index;
	}
	
	public void setSelectedTab(ShopTab shopTab) {
		int idx = tabs.indexOf(shopTab);
		if (idx == -1)
			throw new IllegalArgumentException("There is no such shop tab in this shop!!!");
		selectedTab = idx;
	}
}
