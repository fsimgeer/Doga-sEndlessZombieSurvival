package ui.shop;

import doa.engine.scene.DoaObject;
import ui.shop.data.ShopItemData;

public class ShopItem extends DoaObject {
	
	private static final long serialVersionUID = -4098643700893307002L;

	public final ShopItemData data;
	
	public final ShopTabColumn column;
		
	public ShopItem(ShopItemData data, ShopTabColumn column) {
		this.data = data;
		this.column = column;
		
		//addComponent(new ShopItemRenderer(this));
		//addComponent(new ShopItemBehaviour(this));
		makeStatic();
		
		setzOrder(column.getzOrder() + 1);
	}
}
