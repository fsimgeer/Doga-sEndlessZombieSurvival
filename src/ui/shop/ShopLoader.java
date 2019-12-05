package ui.shop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class ShopLoader {
	
	private ShopLoader() {}
	
	public static void readShopData(File shopFolder) {
		try {
			String path = "res/maps/" + shopFolder.getPath().replaceAll("\\\\", "/");
			createShop(new File(path + "/shop.xml"));
		} catch (JDOMException | IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void createShop(File shopFile) throws JDOMException, IOException {
		Document shopDocument = new SAXBuilder().build(shopFile);
		Element shopElement = shopDocument.getRootElement();
		shopElement.getChildren().forEach(el -> {
			
		});
	}
}
