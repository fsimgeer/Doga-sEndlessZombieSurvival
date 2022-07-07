package renderers;

import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;

import java.awt.Color;
import java.awt.Rectangle;

import doa.engine.graphics.DoaFonts;
import doa.engine.scene.elements.renderers.DoaRenderer;
import ui.shop.Shop;
import ui.shop.ShopTab;

public class ShopTabRenderer extends DoaRenderer {
	private static final long serialVersionUID = -7765764797317024431L;

	ShopTab tab;
	
	Rectangle titleBounds;

	Rectangle bounds;
	
	public ShopTabRenderer(ShopTab tab){
		this.tab = tab;
		this.titleBounds = tab.titleBounds;
	}

	@Override
	public void render() {
		setColor(Color.YELLOW.darker());
		fill(titleBounds);
		
		if (tab.isSelected()) {
			setColor(Color.YELLOW.darker().darker());
			fill(titleBounds);
		}

		setFont(DoaFonts.getFont("Soup").deriveFont(36f));
		setColor(Color.BLACK);
		drawString(tab.tabData.name.toUpperCase(), (float) titleBounds.getMinX() + 10, (float) titleBounds.getMaxY() - 10);
		
		//int tabTitleWidth = Shop.SHOP_WIDTH / tab.colCount;
		//bounds = new Rectangle(Shop.X + index * tabTitleWidth, Shop.Y, tabTitleWidth, 50);
		
	}
}
