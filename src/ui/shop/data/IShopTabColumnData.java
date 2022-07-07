package ui.shop.data;

import ui.shop.ShopTabColumn;

public interface IShopTabColumnData {
	
	void onInit(ShopTabColumn column);
	void onTick(ShopTabColumn column);
	void onRender(ShopTabColumn column);
}
