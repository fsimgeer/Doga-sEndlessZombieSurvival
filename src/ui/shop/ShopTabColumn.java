package ui.shop;

import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.setColor;

import java.awt.Color;
import java.awt.Rectangle;

import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.renderers.DoaRenderer;
import doa.engine.scene.elements.scripts.DoaScript;
import ui.shop.data.IShopTabColumnData;
import ui.shop.data.ShopItemData;

public class ShopTabColumn extends DoaObject {
	
	private static final long serialVersionUID = -8607278238202199155L;

	private final IShopTabColumnData data;
	
	private final ShopTab tab;
	private final Rectangle contentBounds;
	
	public ShopTabColumn(IShopTabColumnData data, ShopTab tab, Rectangle contentBounds) {
		this.data = data;
		this.tab = tab;
		this.contentBounds = contentBounds;

		setzOrder(tab.getzOrder() + 1);
		addComponent(new Script());
		addComponent(new Renderer());
		makeStatic();

		data.onInit(this);		
	}
	
	public void createShopItem(ShopItemData data) {
		ShopItem newItem = new ShopItem(data, this);
		tab.shop.getScene().add(newItem);
	}
	
	private final class Script extends DoaScript {

		private static final long serialVersionUID = 1775263802127104225L;

		@Override
		public void tick() {
			if (!tab.isSelected()) { return; }
			
			data.onTick(ShopTabColumn.this);
		}
	}
	
	private final class Renderer extends DoaRenderer {

		private static final long serialVersionUID = 1608609419903035433L;
		private static final Rectangle MARGINS = new Rectangle(10, 10, 20, 20);
		
		private Rectangle innerBounds;
		
		public Renderer() {
			innerBounds = new Rectangle(
				contentBounds.x + MARGINS.x,
				contentBounds.y + MARGINS.y,
				contentBounds.width - MARGINS.width,
				contentBounds.height - MARGINS.height
			);
		}
		
		@Override
		public void render() {
			if (!tab.isSelected()) { return; }
			setColor(Color.RED);
			fill(contentBounds);
			setColor(Color.GREEN);
			fill(innerBounds);
			
			data.onRender(ShopTabColumn.this);
		}
	}
}
