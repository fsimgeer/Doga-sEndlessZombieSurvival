package ui.shop.renderers;

import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.fillRect;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;

import java.awt.Color;
import java.awt.Rectangle;

import doa.engine.graphics.DoaFonts;
import doa.engine.scene.elements.renderers.DoaRenderer;
import ui.shop.Shop;
import ui.shop.ShopTab;

public class ShopRenderer extends DoaRenderer {

	private static final long serialVersionUID = 8536737218483830680L;
	
	Shop shop;
	
	float X = Shop.X;
	float Y = Shop.Y;
	int shopWidth = Shop.SHOP_WIDTH;
	int shopHeight = Shop.SHOP_HEIGHT;

	int DIST_BETWEEN_ITEMS = Shop.DIST_BETWEEN_ITEMS;
	
	public ShopRenderer(Shop shop) {
		this.shop = shop;
	}

	@Override
	public void render() {
		setColor(new Color(200, 200, 200, 200));
		fillRect(X, Y, shopWidth, shopHeight);

		setFont(DoaFonts.getFont("Soup").deriveFont(36f));

		setColor(Color.YELLOW.darker());
		fill(shop.closeButton);
		setColor(Color.BLACK);
		drawString("CLOSE", (int)X + shopWidth - 120, Y + shopHeight - 30);
	}
}
