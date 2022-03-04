package ui.shop.upgrade;

import java.util.HashMap;

import components.PlayerData;
import objects.Player;

public class ShopUpgradeBank {
	private static HashMap<String, ShopUpgradeAction> upgrades;
	static {
		upgrades = new HashMap<>();
		PlayerData player = Player.getInstance().data;
		upgrades.put("MaxHealthUpAction", (int amount) -> {player.setHealthMAX(player.getHealthMAX() + amount);
															return true;});
		upgrades.put("HealAction", (int amount) -> {if(player.getHealth() == player.getHealthMAX()) return false;
													player.setHealth(player.getHealth() + amount);
													return true;});
		upgrades.put("MoveSpeedUpAction", (int amount) -> {player.setSpeed(player.getSpeed() + amount);
															return true;});
		upgrades.put("UnknownAction", (int amount) -> {return false;});
	}
	
	public static ShopUpgradeAction get(String action) {
		return upgrades.get(action);
	}
}
