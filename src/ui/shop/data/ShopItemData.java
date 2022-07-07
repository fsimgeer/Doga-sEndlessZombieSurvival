package ui.shop.data;

import java.util.List;

public class ShopItemData {
	
	public final String name;
	public final String imageName;
	public final List<ShopUpgradeData> upgrades;
	
	public ShopItemData(String name, String imageName, List<ShopUpgradeData> upgrades) {
		this.name = name;
		this.imageName = imageName;
		this.upgrades = upgrades;
	}
}