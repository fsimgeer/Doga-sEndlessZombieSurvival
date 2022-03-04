package scripts;

import doa.engine.input.DoaMouse;
import doa.engine.scene.elements.scripts.DoaScript;
import ui.shop.ShopTab;

public class ShopTabBehaviour extends DoaScript {
	private static final long serialVersionUID = -4082356605165304056L;

	public ShopTab tab;
	
	public ShopTabBehaviour(ShopTab tab) {
		this.tab = tab;
	}
	
	@Override
	public void tick() {
		if(DoaMouse.MB1 && tab.bounds.contains(DoaMouse.X, DoaMouse.Y))
			tab.shop.setSelectedTab(tab);
	}
}
