package ui.shop;

import java.awt.Rectangle;
import java.util.ArrayList;

import components.ShopTabData;
import doa.engine.scene.DoaObject;
import renderers.ShopTabRenderer;
import scripts.ShopTabBehaviour;

public class ShopTab extends DoaObject{
	private static final long serialVersionUID = -918011328410815649L;

	public final Shop shop;
	public final ShopTabData tabData;
	public final Rectangle titleBounds;
	public final Rectangle bounds;
	
	private ShopItem selectedItem;
	
	private final ArrayList<ShopTabColumn> cols;
	
	public ShopTab(Shop shop, int colCount, ShopTabData tabData, Rectangle titleBounds, Rectangle bounds) {
		this.shop = shop;
		cols = new ArrayList<ShopTabColumn>(colCount);
		this.tabData = tabData;
		this.titleBounds = titleBounds;
		this.bounds = bounds;
		
		addComponent(new ShopTabRenderer(this));
		addComponent(new ShopTabBehaviour(this));
		makeStatic();

		for (int i = 0; i < colCount; i++) {
			Rectangle colBounds = new Rectangle(bounds.x + (bounds.width * i / colCount), bounds.y, (bounds.width / colCount), bounds.height);
			ShopTabColumn col = new ShopTabColumn(this, colBounds);
			col.setzOrder(shop.getzOrder() + 2);
			cols.add(col);
			shop.getScene().add(col);
		}
	}
	
	public void select() {
		shop.setSelectedTab(this);
	}
	
	public boolean isSelected() {
		return shop.getSelectedTab() == this;
	}
}
