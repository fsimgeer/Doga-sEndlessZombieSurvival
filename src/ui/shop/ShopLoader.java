package ui.shop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import doa.engine.log.DoaLogger;
import doa.engine.scene.DoaScene;
import event.EventDispatcher;
import ui.shop.data.IShopTabColumnData;
import ui.shop.data.ShopData;
import ui.shop.data.ShopItemData;
import ui.shop.data.ShopTabColumnDataWithItems;
import ui.shop.data.ShopTabData;
import ui.shop.data.ShopUpgradeData;

public class ShopLoader {

	private ShopLoader() {}

	public static void createShop(EventDispatcher dispatcher, DoaScene scene) {
		try {
			Shop s = new Shop(dispatcher, createShop(new File("res/shop/shop.xml")));
			scene.add(s);
			s.initializeTabs();
		} catch (JDOMException | IOException ex) {
			ex.printStackTrace();
		}
	}

	private static ShopData createShop(File shopFile) throws JDOMException, IOException {
		List<ShopTabData> tabs = new ArrayList<>();

		Document shopDocument = new SAXBuilder().build(shopFile);
		Element shopElement = shopDocument.getRootElement();
		shopElement.getChildren().forEach(tabElement -> {
			String tabName = tabElement.getChildText("tab-name");
			List<IShopTabColumnData> columns = new ArrayList<>();
			tabElement.getChildren("tab-column").forEach(columnElement -> {
				String columnName = tabElement.getChildText("column-name");
				String contentName = tabElement.getChildText("content");
				//float widthFactor = Float.parseFloat(tabElement.getChildText("width-factor"));
				List<ShopItemData> items = new ArrayList<>();

				tabElement.getChildren("items").forEach(itemElement ->{
					String itemName = itemElement.getChildText("item-name");
					String imageName = itemElement.getChildText("item-image");
					List<ShopUpgradeData> upgrades = new ArrayList<>();
					List<Integer> costs = new ArrayList<>();

					itemElement.getChild("upgrade-costs").getChildren("cost").forEach(costElement -> {
						try{
							int cost = Integer.parseInt(costElement.getText());
							costs.add(cost);
						} catch(NumberFormatException ex){
							DoaLogger.LOGGER.severe("cost - " + ex);
						}
					});

					itemElement.getChildren("upgrades").forEach(upgradesElement -> {
						upgradesElement.getChildren("upgrade").forEach(upgradeElement -> {
							//shopUpgrade.action = ShopUpgradeBank.get(upgradeElement.getChildText("upgrade-action"));
							String upgradeName = upgradeElement.getChildText("upgrade-name");
							List<Integer> effects = new ArrayList<>();
							upgradeElement.getChild("upgrade-effects").getChildren("effect").forEach(effectElement -> {
								try{
									int effect = Integer.parseInt(effectElement.getText());
									effects.add(effect);
								} catch(NumberFormatException ex){
									DoaLogger.LOGGER.severe("effect - " + ex);
								}

							});

							ShopUpgradeData shopUpgrade = new ShopUpgradeData(upgradeName, effects);
							upgrades.add(shopUpgrade);
						});

					});

					ShopItemData shopItem = new ShopItemData(itemName, imageName, upgrades, costs);
					items.add(shopItem);
				});

				//ShopTabColumnDataWithItems shopItem = new ShopItemData(columnName, contentName, widthFactor, items);
				ShopTabColumnDataWithItems column = new ShopTabColumnDataWithItems(columnName, items);
				columns.add(column);
			});
			ShopTabData shopTab = new ShopTabData(tabName, columns);
			tabs.add(shopTab);
		});

		ShopData shop = new ShopData(tabs);
		return shop;
	}
}
