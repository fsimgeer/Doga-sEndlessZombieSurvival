package ui.shop.data;

import java.util.List;

public class ShopItemData {
	
	public final String name;
	public final String imageName;
	public final List<ShopUpgradeData> upgrades;
	List<Integer> costs;
	
	public ShopItemData(String name, String imageName, List<ShopUpgradeData> upgrades, List<Integer> costs) {
		this.name = name;
		this.imageName = imageName;
		this.upgrades = upgrades;
		this.costs = costs;
	}
}