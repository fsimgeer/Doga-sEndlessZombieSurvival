package ui.shop.scripts;

import components.ShopItemData;
import doa.engine.input.DoaMouse;
import doa.engine.scene.elements.scripts.DoaScript;
import objects.Player;
import ui.shop.ShopItem;

public class ShopItemBehaviour extends DoaScript {
	private static final long serialVersionUID = -1745222211277845531L;
	
	private ShopItem item;
	private ShopItemData itemData;
	private Player player = Player.getInstance();

	public ShopItemBehaviour(ShopItem item) {
		this.item = item;
		this.itemData = item.itemData;
	}
	
	@Override
	public void tick() {
		if (DoaMouse.MB1 && item.tab.isSelected() && item.bounds.contains(DoaMouse.X, DoaMouse.Y)) {
			//item.select();
			if(itemData.numOfUpgrades < itemData.costs.size() && player.data.getCoins() >= itemData.costs.get(itemData.numOfUpgrades)) {	// if upgrade not already maxed out & player has enough money
				for(int i = 0; i < itemData.upgrades.size(); i++) {
					if(itemData.upgrades.get(i).action.execute(itemData.upgrades.get(i).effects.get(itemData.numOfUpgrades))) {
						player.data.setCoins(player.data.getCoins() - itemData.costs.get(itemData.numOfUpgrades));	// decrease the coins only if the action has been completed successfully
						
						if(itemData.costs.size() > 1)	// increase numOfUpgrades only if this upgrade can be bought a limited amount of time
							itemData.numOfUpgrades++;
					}
				}
			}
		}
	}
}