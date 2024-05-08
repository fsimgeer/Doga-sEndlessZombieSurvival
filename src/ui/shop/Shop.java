package ui.shop;

import static doa.engine.core.DoaGraphicsFunctions.fillRect;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import doa.engine.graphics.DoaFonts;
import doa.engine.maths.DoaVector;
import doa.engine.scene.DoaObject;
import doa.engine.scene.DoaScene;
import doa.engine.scene.elements.renderers.DoaRenderer;
import event.EventDispatcher;
import event.IEvent;
import event.IEventListener;
import event.ShopClosed;
import event.WaveEnded;
import ui.shop.data.ShopData;
import ui.elements.Button;
import ui.elements.Observable;
import ui.elements.Observer;

public class Shop extends DoaObject implements Observer, IEventListener {
	private static final long serialVersionUID = -4966597756746116724L;

	public ShopData shopData;

	public static final int X = 100;
	public static final int Y = 100;
	public static final int SHOP_WIDTH = 1920 - 2 * X;
	public static final int SHOP_HEIGHT = 1080 - 2 * Y;

	public static final int DIST_BETWEEN_ITEMS = 30;
	public static final int COLUMN_ITEM_COUNT = 2;
	
	public static boolean isHidden = true;

	public final Rectangle closeButtonCA = new Rectangle(X + SHOP_WIDTH - 120, Y + SHOP_HEIGHT - 45, 100, 40);
	public final DoaVector closeButtonPosition = new DoaVector(X + SHOP_WIDTH - 120, Y + SHOP_HEIGHT - 45);
	
	private int selectedTab;

	private ArrayList<ShopTab> tabs = new ArrayList<>();

	final int tabTitleWidth;
	private static final int SHOP_TITLE_HEIGHT = 50;
	final Rectangle bounds = new Rectangle(X, Y + SHOP_TITLE_HEIGHT, SHOP_WIDTH, SHOP_HEIGHT - SHOP_TITLE_HEIGHT);
	
	public final Rectangle openButton = new Rectangle(SHOP_WIDTH, Y - 45, 100, 40);
	private Button closeButton;

	private EventDispatcher dispatcher;
	
	public Shop(EventDispatcher dispatcher, ShopData shopData) {
		this.shopData = shopData;
		setzOrder(1000);
		addComponent(new Renderer());
		makeStatic();
		
		this.dispatcher = dispatcher;
		dispatcher.RegisterListener(this);

		tabTitleWidth = SHOP_WIDTH / shopData.tabs.size();
		
		/* Play Offline Button */
		closeButton = Button
			.builder()
			.textKey("CLOSE")
			.contentArea(closeButtonCA)
			.action(source -> {
				HideShopScreen();
				dispatcher.DispatchEvent(new ShopClosed());
			})
			.build();
		//closeButton.setPosition(closeButtonPosition);
		/* --------------- */
	}
	
	@Override
	public void onAddToScene(final DoaScene scene) {
		super.onAddToScene(scene);

		scene.add(closeButton);
		closeButton.setzOrder(2000);
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
	
	public void HideShopScreen() {
		isHidden = true;
		closeButton.setVisible(false);
	}
	
	private class Renderer extends DoaRenderer {

		private static final long serialVersionUID = 8536737218483830680L;
		
		@Override
		public void render() {
			if(isHidden) {
				return;
			}
			
			/* Render grey bg */
			setColor(new Color(200, 200, 200, 200));
			fillRect(Shop.X, Shop.Y, Shop.SHOP_WIDTH, Shop.SHOP_HEIGHT);

			setFont(DoaFonts.getFont("Soup").deriveFont(36f));
		}
	}

	@Override
	public void onNotify(Observable b) {
		isHidden = false;
		closeButton.setVisible(true);
	}

	@Override
	public void onEventReceived(IEvent event) {
		if (event instanceof WaveEnded) {
			isHidden = false;
			closeButton.setVisible(true);
		}
		
	}
}
