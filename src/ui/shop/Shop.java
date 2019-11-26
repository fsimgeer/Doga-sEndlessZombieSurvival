package ui.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.input.DoaMouse;
import com.doa.engine.scene.DoaObject;

import gameplay.BulletSpecs;
import gameplay.EnemySpawner;
import gameplay.Player;
import main.Main;

public class Shop extends DoaObject {

	private static final long serialVersionUID = -2410862007259184762L;

	public static final float X = 75;
	public static final float Y = 25;
	public static final int SHOP_WIDTH = Main.INNER_WIDTH - 150;
	public static final int SHOP_HEIGHT = Main.INNER_HEIGHT - 150;
	
	private static final int tabCount = 4;
	private static ShopTab[] tabs = new ShopTab[tabCount];
	
	private static ShopItem[] itemListTab1;
	private static Gun[] itemListTab2;
	private static ShopItem[] itemListTab3;
	private static ShopItem[] itemListTab4;
	
	public static final int DIST_BETWEEN_ITEMS = 30;

	private static final Rectangle CLOSE_SHOP_BUTTON = new Rectangle((int) X + SHOP_WIDTH - 120, (int) Y + SHOP_HEIGHT - 60, 100, 40);

	private ShopTab active;
	
	private static boolean visible = false;
	
	private static Player player = Player.getInstance();

	public Shop() {
		super(X, Y, SHOP_WIDTH, SHOP_HEIGHT, 1);
		setFixed(true);
		
		// First Tab Items
		itemListTab1 = new ShopItem[] {
			new ShopItem("MAX HEALTH UP", 8, () -> {
				player.setHealthMAX(player.getHealthMAX() + 100);
			}),
			new ShopItem("HEAL", 0, () -> {
				if(player.getHealth() != player.getHealthMAX()) {
					player.setHealth(player.getHealth() + Math.min(50, player.getHealthMAX() - player.getHealth()));
				}
			}),
			new ShopItem("ARMOR UP", 8, () -> {
				if(player.getHealth() != player.getHealthMAX()) {
					player.getBulletSpecs().setCooldown(player.getBulletSpecs().getCooldown() - player.getBulletSpecs().getCooldown() / 3);
				}
			}),
			new ShopItem("MOVE SPEED UP", 8, () -> {
				if(player.getHealth() != player.getHealthMAX()) {
					player.getBulletSpecs().setCooldown(player.getBulletSpecs().getCooldown() - player.getBulletSpecs().getCooldown() / 3);
				}
			})
		};
		
		// Second Tab Items
		itemListTab2 = new Gun[] {
				new Gun("PISTOL", 8, () -> {
					
				}, new BulletSpecs(6, 6, Color.RED, 2f, 1000, 500, 0, 100, false, false), true),
				new Gun("MACHINEGUN", 8, () -> {

				}, new BulletSpecs(10, 10, Color.BLACK, 3f, 100, 10, 10, 100, false, false), false),
				new Gun("SHOTGUN", 8, () -> {
					
				}, new BulletSpecs(16, 16, Color.BLUE, 2f, 1000, 500, 30, 100, true, false), false),
				new Gun("SNIPER", 8, () -> {
					
				}, new BulletSpecs(14, 40, Color.DARK_GRAY, 5f, 2000, 500, 0, 500, true, false), false)
		};
		
		// Third Tab Items
		itemListTab3 =  new ShopItem[] {
				new ShopItem("BOOM GRENADE", 8, () -> {
					
			}), new ShopItem("FREEZE GRENADE", 8, () -> {
				
			}), new ShopItem("MOLOTOV", 8, () -> {
				
			}), new ShopItem("BURN BEACON", 8, () -> {
				
			}), new ShopItem("FREEZE BEACON", 8, () -> {
				
			}), new ShopItem("POISON BEACON", 8, () -> {
				
			})
		};
		
		// Fourth Tab Items
		itemListTab4 =  new ShopItem[] {
				new ShopItem("DOUBLE COIN CHANCE UP", 8, () -> {
					
				}), new ShopItem("RED ORB", 8, () -> {
					
				}), new ShopItem("GREEN ORB", 8, () -> {
					
				}), new ShopItem("BLUE ORB", 8, () -> {
					
				})
		};
		
		int tabWidth = (int) (SHOP_WIDTH / tabCount);
		tabs[0] = new ShopTab("Health - Armor", 2, new Rectangle((int) X , (int) Y, tabWidth, 50), itemListTab1);
		tabs[1] = new ShopTab("Guns", 2, new Rectangle((int) X + tabWidth, (int) Y, tabWidth, 50), itemListTab2);
		tabs[2] = new ShopTab("Grenade - Beacon", 2, new Rectangle((int) X + 2 * tabWidth, (int) Y, tabWidth, 50), itemListTab3);
		tabs[3] = new ShopTab("Specials", 2, new Rectangle((int) X + 3 * tabWidth, (int) Y, tabWidth, 50), itemListTab4);
		
		tabs[0].setActive(true);
		active = tabs[0];
	}

	public static void show() {
		visible = true;
	}

	public static void hide() {
		visible = false;
	}

	public static boolean isVisible() {
		return visible;
	}

	@Override
	public void tick() {
		final Point mousePoint = new Point((int) DoaMouse.X, (int) DoaMouse.Y);
		if(visible) {
			if (DoaMouse.MB1 && getBounds().contains(mousePoint)) {
				if (CLOSE_SHOP_BUTTON.contains(mousePoint)) {
					hide();
					EnemySpawner.Difficulty++;
					EnemySpawner.EnemiesLeftToSpawn = (int) Math.exp(EnemySpawner.Difficulty);
					EnemySpawner.EnemiesLeftInWave = EnemySpawner.EnemiesLeftToSpawn;
				}else if(tabs[0].getBounds().contains(mousePoint)) {
					active.setActive(false);
					active = tabs[0];
					active.setActive(true);
				}else if(tabs[1].getBounds().contains(mousePoint)) {
					active.setActive(false);
					active = tabs[1];
					active.setActive(true);
				}else if(tabs[2].getBounds().contains(mousePoint)) {
					active.setActive(false);
					active = tabs[2];
					active.setActive(true);
				}else if(tabs[3].getBounds().contains(mousePoint)) {
					active.setActive(false);
					active = tabs[3];
					active.setActive(true);
				}
			}
			active.tick();
		}
	}

	@Override
	public void render(final DoaGraphicsContext g) {
		if (visible) {
			g.setColor(new Color(200, 200, 200, 100));
			g.fillRect(X, Y, SHOP_WIDTH, SHOP_HEIGHT);

			g.setFont(new Font("Soup of Justice", Font.BOLD, 32));
			
			for(int i = 0; i < tabs.length; i++) {
				tabs[i].render(g);
			}
			
			g.setColor(Color.YELLOW.darker());
			g.fill(CLOSE_SHOP_BUTTON);
			g.setColor(Color.BLACK);
			g.drawString("CLOSE", X + SHOP_WIDTH - 120, Y + SHOP_HEIGHT - 30);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) X, (int) Y, SHOP_WIDTH, SHOP_HEIGHT);
	}
	
	public static Gun[] getGunList() {
		return itemListTab2;
	}
}
