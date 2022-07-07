package ui.shop;

import java.awt.Rectangle;

import doa.engine.scene.DoaObject;
import renderers.ShopTabColumnRenderer;

public class ShopTabColumn extends DoaObject{
	private static final long serialVersionUID = -8607278238202199155L;

	public ShopTab tab;
	public Rectangle bounds;
	
	public ShopTabColumn(ShopTab tab, Rectangle bounds) {
		this.tab = tab;
		this.bounds = bounds;
		
		Rectangle innerBounds = new Rectangle(bounds.x + 10, bounds.y + 10, bounds.width - 20, bounds.height - 20);
		
		addComponent(new ShopTabColumnRenderer(this, innerBounds));
		//addComponent(new ShopTabBehaviour(this));
		makeStatic();

		for (int i = 0; i < tab.tabData.items.size(); i++) {
			ShopItem newItem = new ShopItem(this, i);
			newItem.setzOrder(tab.shop.getzOrder() + 3);
			tab.shop.getScene().add(newItem);
		}
	}
}
