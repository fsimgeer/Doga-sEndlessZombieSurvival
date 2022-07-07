package ui.shop.renderers;

import static doa.engine.core.DoaGraphicsFunctions.drawImage;
import static doa.engine.core.DoaGraphicsFunctions.drawString;
import static doa.engine.core.DoaGraphicsFunctions.fill;
import static doa.engine.core.DoaGraphicsFunctions.setColor;
import static doa.engine.core.DoaGraphicsFunctions.setFont;

import java.awt.Color;
import java.awt.Rectangle;

import doa.engine.graphics.DoaFonts;
import doa.engine.graphics.DoaSprites;
import doa.engine.scene.elements.renderers.DoaSpriteRenderer;
import ui.shop.ShopItem;
import ui.shop.data.ShopItemData;

public class ShopItemRenderer extends DoaSpriteRenderer {
	private static final long serialVersionUID = -1729714967661329528L;
	
	private ShopItem item;
	private ShopItemData itemData;
	
	private Rectangle bounds;
	private Rectangle innerBounds;
	
	private float alpha = 0;
	private boolean alphaDirectionUp = true;
	
	public ShopItemRenderer(ShopItem item) {
		this.item = item;
		this.itemData = item.itemData;
		this.bounds = item.bounds;
		this.innerBounds = new Rectangle(bounds.x + 10, bounds.y + 10, bounds.width - 20, bounds.height - 20);
	}
	
	@Override
	public void render() {
		if(item.tab.isSelected()) {
			// setting font
			setFont(DoaFonts.getFont("Soup").deriveFont(36f));
			
			// drawing the outer bounds of the item with the color changing according the item being selected or not
			/*if (item.isSelected()) {
				setColor(new Color(200, 100, 200, 100));
			} else {
				setColor(new Color(100, 200, 200, 100));
			}
			fill(bounds);

			// drawing the inner bounds of the item with the color changing according the item being selected or not
			if (item.isSelected()) {
				setColor(new Color(160, 200, 239, 100));
			} else {
				setColor(new Color(200, 200, 200, 100));
			}
			fill(innerBounds);*/

			// item image
			drawImage(DoaSprites.getSprite(itemData.picture),
			        (float) innerBounds.x + 10,
			        (float) (innerBounds.y + 10),
			        115, 115);

			// item name & upgrade costs
			setColor(Color.BLACK);
			drawString(itemData.name, (int)innerBounds.getMinX() + 130, (int)innerBounds.getMinY() + 32);
			
			if (itemData.numOfUpgrades < itemData.costs.size()) {
				drawString("Cost: " + itemData.costs.get(itemData.numOfUpgrades), (int)innerBounds.getMinX() + 130, (int)innerBounds.getMinY() + 82);
			} else {
				drawString("Cost: MAX", (int)innerBounds.getMinX() + 130, (int)innerBounds.getMinY() + 82);
			}
			
			// item upgrade effects
			if(alphaDirectionUp)
				if(alpha + 0.4 > 255) {
					alpha = 255;
					alphaDirectionUp = false;
				}
				else
					alpha += 0.4;
			else {
				if(alpha - 0.4 < 0) {
					alpha = 0;
					alphaDirectionUp = true;
				}
				else
					alpha -= 0.4;
			}
			
			for(int i = 0; i < itemData.upgrades.size(); i++) {
				setColor(Color.BLACK);
				drawString(itemData.upgrades.get(i).name, (int)innerBounds.getMinX() + 10, (int)innerBounds.getMinY() + i * 50 + 160);
				/*if(item.isSelected()) {
					setColor(new Color(0, 255, 0, (int)alpha));
					drawString("+" + itemData.upgrades.get(i).effects.get(itemData.numOfUpgrades), (int)innerBounds.getMinX() + itemData.upgrades.get(i).name.length() * 23, (int)innerBounds.getMinY() + i * 50 + 160);
				}*/
			}

			// upgrade boxes (outer) which show the number of upgrades
			setColor(new Color(0, 0, 0, 100));
			for (int k = 0; k < itemData.costs.size(); k++) {
				if(itemData.costs.size() > 1)
					fill(new Rectangle(innerBounds.x + 15 + k * 40, (int)innerBounds.getMaxY() - 40, 20, 20));
			}

			// upgrade boxes (inner) which show the number of upgrades bought
			setColor(new Color(255, 255, 255, 100));
			for (int k = 0; k < itemData.numOfUpgrades; k++) {
				if(itemData.costs.size() > 1)
					fill(new Rectangle(innerBounds.x + 20 + k * 40, (int)innerBounds.getMaxY() - 35, 10, 10));
			}
		}
	}
}
