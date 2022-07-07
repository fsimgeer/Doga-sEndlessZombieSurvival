package ui.shop.scripts;

import doa.engine.input.DoaMouse;
import doa.engine.scene.elements.scripts.DoaScript;
import ui.shop.Shop;

public class ShopBehaviour extends DoaScript {
	private static final long serialVersionUID = 7184950302229076516L;
	public Shop shop;

	public ShopBehaviour(Shop shop) {
		this.shop = shop;
	}
	
	@Override
	public void tick() {
		if (DoaMouse.MB1 && shop.closeButton.contains(DoaMouse.X, DoaMouse.Y)) {
			
		}
	}
}
