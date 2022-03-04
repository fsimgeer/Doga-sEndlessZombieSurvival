package renderers;

import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;

import java.awt.Color;
import java.awt.Rectangle;

import doa.engine.graphics.DoaFonts;
import doa.engine.scene.elements.renderers.DoaRenderer;
import ui.shop.ShopTab;

public class ShopTabRenderer extends DoaRenderer {
	private static final long serialVersionUID = -7765764797317024431L;

	ShopTab tab;
	
	Rectangle bounds;
	
	public ShopTabRenderer(ShopTab tab){
		this.tab = tab;
		this.bounds = tab.bounds;
	}

	@Override
	public void render() {
		setColor(Color.YELLOW.darker());
		fill(bounds);
		
		if(tab.isSelected()) {
			setColor(Color.YELLOW.darker().darker());
			fill(bounds);

		}

		setFont(DoaFonts.getFont("Soup").deriveFont(36f));
		setColor(Color.BLACK);
		drawString(tab.tabData.name.toUpperCase(), (float) bounds.getMinX() + 10, (float) bounds.getMaxY() - 10);
		
	}
}
