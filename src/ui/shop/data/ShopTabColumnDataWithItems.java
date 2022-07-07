package ui.shop.data;

import java.util.List;

import ui.shop.ShopTabColumn;

public final class ShopTabColumnDataWithItems implements IShopTabColumnData {

	public final List<ShopItemData> items;
	
	public ShopTabColumnDataWithItems(List<ShopItemData> items) { this.items = items; }

	@Override
	public void onInit(ShopTabColumn column) { items.forEach(column::createShopItem); }

	@Override
	public void onTick(ShopTabColumn column) { /* no-op */ }

	@Override
	public void onRender(ShopTabColumn column) { /* no-op */ }
}
