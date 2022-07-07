package ui.shop;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import doa.engine.log.DoaLogger;
import doa.engine.scene.DoaScene;
import ui.shop.data.ShopData;
import ui.shop.data.ShopItemData;
import ui.shop.data.ShopTabData;
import ui.shop.data.ShopUpgradeData;
import ui.shop.upgrade.ShopUpgradeBank;

public class ShopLoader {
	
	private ShopLoader() {}
	
	public static void createShop(DoaScene scene) {
		try {
			Shop s = new Shop(createShop(new File("res/shop/shop.xml")));
			scene.add(s);
			s.initializeTabs();
		} catch (JDOMException | IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static ShopData createShop(File shopFile) throws JDOMException, IOException {
		Document shopDocument = new SAXBuilder().build(shopFile);
		Element shopElement = shopDocument.getRootElement();
		ShopData shop = new ShopData();
		shopElement.getChildren().forEach(tabElement -> {
			ShopTabData shopTab = new ShopTabData();
			shopTab.name = tabElement.getChildText("tab-name");
			tabElement.getChildren("shop-item").forEach(shopItemElement -> {
				
				ShopItemData shopItem = new ShopItemData();
				shopItem.name = shopItemElement.getChildText("item-name");
				shopItem.picture = shopItemElement.getChildText("picture");

				shopItemElement.getChild("upgrade-costs").getChildren("cost").forEach(costElement -> {
					try{
						int cost = Integer.parseInt(costElement.getText());
						shopItem.costs.add(cost);
					} catch(NumberFormatException ex){
						DoaLogger.LOGGER.severe("cost - " + ex);
					}
				});
				
				shopItemElement.getChildren("upgrades").forEach(upgradesElement -> {
					upgradesElement.getChildren("upgrade").forEach(upgradeElement -> {
				
						ShopUpgradeData shopUpgrade = new ShopUpgradeData();
						shopUpgrade.action = ShopUpgradeBank.get(upgradeElement.getChildText("upgrade-action"));
						shopUpgrade.name = upgradeElement.getChildText("upgrade-name");
						upgradeElement.getChild("upgrade-effects").getChildren("effect").forEach(effectElement -> {
							try{
								int effect = Integer.parseInt(effectElement.getText());
								shopUpgrade.effects.add(effect);
							} catch(NumberFormatException ex){
								DoaLogger.LOGGER.severe("effect - " + ex);
							}
							
						});
						
						shopItem.upgrades.add(shopUpgrade);
					});
				shopTab.items.add(shopItem);
				});
			});
			shop.tabs.add(shopTab);
		});
		return shop;
	}
}
