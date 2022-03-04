package ui.shop;

import java.awt.Rectangle;

import components.ShopTabData;
import doa.engine.scene.DoaObject;
import renderers.ShopTabRenderer;
import scripts.ShopTabBehaviour;

public class ShopTab extends DoaObject{
	private static final long serialVersionUID = -918011328410815649L;

	public final Shop shop;
	public final ShopTabData tabData;
	public final Rectangle bounds;
	
	private ShopItem selectedItem;
	
	public ShopTab(Shop shop, int index) {
		this.shop = shop;
		this.tabData = shop.shopData.tabs.get(index);

		int tabWidth = Shop.SHOP_WIDTH / shop.shopData.tabs.size();
		bounds = new Rectangle(Shop.X + index * tabWidth, Shop.Y, tabWidth, 50);
		
		addComponent(new ShopTabRenderer(this));
		addComponent(new ShopTabBehaviour(this));
		makeStatic();

		for (int i = 0; i < tabData.items.size(); i++) {
			ShopItem newItem = new ShopItem(this, i);
			newItem.setzOrder(shop.getzOrder() + 2);
			shop.getScene().add(newItem);
		}
	}
	
	public void select() {
		shop.setSelectedTab(this);
	}
	
	public boolean isSelected() {
		return shop.getSelectedTab() == this;
	}

	public void setSelectedItem(ShopItem item) {
		selectedItem = item;
	}

	public ShopItem getSelectedItem() {
		return selectedItem;
	}
}
