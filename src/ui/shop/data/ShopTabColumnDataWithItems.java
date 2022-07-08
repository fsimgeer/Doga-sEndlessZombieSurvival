package ui.shop.data;

import java.util.List;

import ui.shop.ShopTabColumn;

public final class ShopTabColumnDataWithItems implements IShopTabColumnData {

	public final String name;
	public final List<ShopItemData> items;
	
	public ShopTabColumnDataWithItems(String name, List<ShopItemData> items) {
		this.name = name;
		this.items = items;
	}

	@Override
	public void onInit(ShopTabColumn column) { items.forEach(column::createShopItem); }

	@Override
	public void onTick(ShopTabColumn column) { /* no-op */ }

	@Override
	public void onRender(ShopTabColumn column) { /* no-op */ }
}
