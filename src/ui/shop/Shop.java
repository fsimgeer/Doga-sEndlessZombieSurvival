package ui.shop;

import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.fillRect;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import doa.engine.graphics.DoaFonts;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.renderers.DoaRenderer;
import ui.shop.data.ShopData;

public class Shop extends DoaObject {
	private static final long serialVersionUID = -4966597756746116724L;

	public ShopData shopData;

	public static final int X = 100;
	public static final int Y = 100;
	public static final int SHOP_WIDTH = 1920 - 2 * X;
	public static final int SHOP_HEIGHT = 1080 - 2 * Y;

	public static final int DIST_BETWEEN_ITEMS = 30;
	public static final int COLUMN_ITEM_COUNT = 2;

	public final Rectangle closeButton = new Rectangle(X + SHOP_WIDTH - 120, Y + SHOP_HEIGHT - 60, 100, 40);
	private int selectedTab;
	
	private ArrayList<ShopTab> tabs = new ArrayList<>();
	
	final int tabTitleWidth;
	private static final int SHOP_TITLE_HEIGHT = 50;
	final Rectangle bounds = new Rectangle(X, Y + SHOP_TITLE_HEIGHT, SHOP_WIDTH, SHOP_HEIGHT - SHOP_TITLE_HEIGHT);
	
	public Shop(ShopData shopData) {
		this.shopData = shopData;
		setzOrder(1000);
		addComponent(new Renderer());
		makeStatic();
		
		tabTitleWidth = SHOP_WIDTH / shopData.tabs.size();
	}

	public void initializeTabs() {
		int tab1ColCount = shopData.tabs.get(0).columns.size();
		int tab2ColCount = shopData.tabs.get(1).columns.size();
		int tab3ColCount = shopData.tabs.get(2).columns.size();
		int tab4ColCount = shopData.tabs.get(3).columns.size();
		
		createShopTab(tab1ColCount);
		createShopTab(tab2ColCount);
		createShopTab(tab3ColCount);
		createShopTab(tab4ColCount);
		
		setSelectedTab(0);
	}
	
	private void createShopTab(int colCount) {
		int index = tabs.size();
		
		Rectangle titleBounds = new Rectangle(X + index * tabTitleWidth, Y, tabTitleWidth, SHOP_TITLE_HEIGHT);
		ShopTab tab = new ShopTab(this, shopData.tabs.get(index), titleBounds, bounds);
		
		getScene().add(tab);
		
		tabs.add(tab);
	}
	
	public ShopTab getSelectedTab() { return tabs.get(selectedTab); }

	public void setSelectedTab(int index) { selectedTab = index; }
	public void setSelectedTab(ShopTab shopTab) {
		int idx = tabs.indexOf(shopTab);
		if (idx == -1) {
			throw new IllegalArgumentException("There is no such shop tab in this shop!!!");
		}
		selectedTab = idx;
	}
	
	private class Renderer extends DoaRenderer {

		private static final long serialVersionUID = 8536737218483830680L;
				
		private DoaVector margins = new DoaVector(120, 30);
		
		@Override
		public void render() {
			/* Render grey bg */
			setColor(new Color(200, 200, 200, 200));
			fillRect(Shop.X, Shop.Y, Shop.SHOP_WIDTH, Shop.SHOP_HEIGHT);

			setFont(DoaFonts.getFont("Soup").deriveFont(36f));

			/* Render close button */
			setColor(Color.YELLOW.darker());
			fill(closeButton);
			setColor(Color.BLACK);
			drawString("CLOSE", Shop.X + Shop.SHOP_WIDTH - margins.x, Shop.Y + Shop.SHOP_HEIGHT - margins.y);
		}
	}
}
