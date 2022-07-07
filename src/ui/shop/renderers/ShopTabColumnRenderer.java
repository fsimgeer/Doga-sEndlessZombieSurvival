package ui.shop.renderers;

import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.setColor;

import java.awt.Color;
import java.awt.Rectangle;

import doa.engine.scene.elements.renderers.DoaRenderer;
import ui.shop.ShopTabColumn;

public class ShopTabColumnRenderer extends DoaRenderer{

	private static final long serialVersionUID = -4891690134261359097L;

	ShopTabColumn column;
	Rectangle innerBounds;
	
	public ShopTabColumnRenderer(ShopTabColumn col, Rectangle innerBounds){
		this.innerBounds = innerBounds;
		column = col;
		//this.titleBounds = tab.bounds;
		///this.colCount = tab.colCount;
	}
	@Override
	public void render() {
		if(column.tab.isSelected()) {
			setColor(Color.RED);
			fill(column.bounds);
			setColor(Color.GREEN);
			fill(innerBounds);
		}
		
	}

}
