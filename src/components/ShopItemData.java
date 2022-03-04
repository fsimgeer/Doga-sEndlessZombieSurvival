package components;

import java.util.ArrayList;
import java.util.List;

public class ShopItemData{
	public String name;
	public String picture;
	public List<ShopUpgradeData> upgrades= new ArrayList<ShopUpgradeData>();
	public List<Integer> costs = new ArrayList<Integer>();
	public int numOfUpgrades = 0;
}