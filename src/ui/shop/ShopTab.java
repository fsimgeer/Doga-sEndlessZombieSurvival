package ui.shop;

import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;

import java.awt.Color;
import java.awt.Rectangle;

import doa.engine.graphics.DoaFonts;
import doa.engine.input.DoaMouse;
import doa.engine.scene.DoaObject;
import doa.engine.scene.elements.renderers.DoaRenderer;
import doa.engine.scene.elements.scripts.DoaScript;
import ui.shop.data.ShopTabData;

public class ShopTab extends DoaObject {
	
	private static final long serialVersionUID = -918011328410815649L;

	final Shop shop;
	private final ShopTabData data;
	
	private Rectangle titleBounds;
	private Rectangle contentBounds;
	
	private final Renderer renderer;
	
	public ShopTab(Shop shop, ShopTabData data, Rectangle titleBounds, Rectangle contentBounds) {
		this.shop = shop;
		this.data = data;
		this.titleBounds = titleBounds;
		this.contentBounds = contentBounds;
		
		setzOrder(shop.getzOrder() + 1);
		
		renderer = new Renderer();
		addComponent(renderer);
		addComponent(new SelectMeOnClick());
		makeStatic();

		int colCount = data.columns.size();
		for (int i = 0; i < colCount; i++) {
			Rectangle colBounds = new Rectangle(
				contentBounds.x + (contentBounds.width * i / colCount),
				contentBounds.y,
				(contentBounds.width / colCount),
				contentBounds.height
			);
			
			ShopTabColumn col = new ShopTabColumn(data.columns.get(i), this, colBounds);
			shop.getScene().add(col);
		}
	}
	
	public ShopItem getSelectedItem() { return renderer.selectedItem; }
	public void setSelectedItem(ShopItem selectedItem) { renderer.selectedItem = selectedItem; }
	
	public boolean isSelected() { return ShopTab.this.equals(shop.getSelectedTab()); }
	
	private final class SelectMeOnClick extends DoaScript {

		private static final long serialVersionUID = -388641316413841604L;

		@Override
		public void tick() {
			if (!DoaMouse.MB1) return;
			if (!titleBounds.contains(DoaMouse.X, DoaMouse.Y)) return;
			
			shop.setSelectedTab(ShopTab.this);
		}
	}
	
	private final class Renderer extends DoaRenderer {

		private static final long serialVersionUID = -91946851310815649L;
		
		private ShopItem selectedItem;
		
		@Override
		public void render() {
			setColor(Color.YELLOW.darker());
			fill(titleBounds);
			
			if (isSelected()) {
				setColor(Color.YELLOW.darker().darker());
				fill(titleBounds);
			}

			setFont(DoaFonts.getFont("Soup").deriveFont(36f));
			setColor(Color.BLACK);
			drawString(data.name.toUpperCase(), (float) titleBounds.getMinX() + 10, (float) titleBounds.getMaxY() - 10);		
		}
	}
}
