package ui.shop;

import java.awt.Rectangle;

import components.ShopItemData;
import doa.engine.scene.DoaObject;
import renderers.ShopItemRenderer;
import scripts.ShopItemBehaviour;

public class ShopItem extends DoaObject{
	private static final long serialVersionUID = -4098643700893307002L;
	
	public final ShopTab tab;
	public final ShopItemData itemData;
	public final Rectangle bounds;
	
	private int columnItemCount = Shop.COLUMN_ITEM_COUNT;
	
	public ShopItem(ShopTab tab, int index) {
		this.tab = tab;
		this.itemData = tab.tabData.items.get(index);

		int rowCount = (tab.tabData.items.size() <= columnItemCount)	// items in the tab fits in one row
						? 1
						: (tab.tabData.items.size() % columnItemCount == 0)
						? (tab.tabData.items.size() / columnItemCount)
						: (tab.tabData.items.size() / columnItemCount) + 1;
		int itemWidth = (Shop.SHOP_WIDTH - (columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS) / columnItemCount;
		int itemHeight = (Shop.SHOP_HEIGHT - (rowCount + 1) * Shop.DIST_BETWEEN_ITEMS - 100) / rowCount;

		bounds = new Rectangle((index % columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (index % columnItemCount) * itemWidth + 100,
								(index / columnItemCount + 1) * Shop.DIST_BETWEEN_ITEMS + (index / columnItemCount) * itemHeight + 120 + Shop.DIST_BETWEEN_ITEMS,
								itemWidth, itemHeight);
		
		addComponent(new ShopItemRenderer(this));
		addComponent(new ShopItemBehaviour(this));
		makeStatic();
	}
	
	public void select() {
		tab.setSelectedItem(this);
	}
	
	public boolean isSelected() {
		return tab.getSelectedItem() == this;
	}
}
