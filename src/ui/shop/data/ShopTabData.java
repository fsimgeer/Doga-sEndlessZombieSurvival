package ui.shop.data;

import java.util.List;

public class ShopTabData {
	
	public final String name;
	
	public final List<IShopTabColumnData> columns;
	
	public ShopTabData(String name, List<IShopTabColumnData> columns) {
		this.name = name;
		this.columns = columns;
	}
}