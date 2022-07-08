package ui.shop.data;

import java.util.List;

public class ShopUpgradeData {
	public final String name;
	public final List<Integer> effects;
	
	public ShopUpgradeData(String name, List<Integer> effects) {
		this.name = name;
		this.effects = effects;
	}
}
