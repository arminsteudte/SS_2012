package praktikum;

import java.util.prefs.BackingStoreException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Simulation extends BasicGame {
	
	private static int TILE_WIDTH = 64;
	private static int TILE_HEIGHT = 64;
	
	private static int WIDTH = 800 ;
	private static int HEIGHT = 600;
	
	private static Color COLOR_BACKGROUND = Color.lightGray;
	private static Color COLOR_STREET = Color.darkGray;
	private static Color COLOR_BLOCK = Color.black;
	
	
	private World world;

	public Simulation(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		int[][] map = world.getMap();
		int mapWidth = map[0].length;
		int mapHeigth = map.length;
		
		int pixelX = 0;
		int pixelY=0;
		
		g.setBackground(COLOR_BACKGROUND);
		for (int y = 0; y < mapHeigth; y++) {
			for (int x = 0; x < mapWidth; x++) {
				int value = map[y][x];
				switch (value) {
				case 0:
					g.setColor(COLOR_STREET);
					g.fill(new Rectangle(pixelX, pixelY, TILE_WIDTH, TILE_HEIGHT));
					break;
				case 1:
					g.setColor(COLOR_BLOCK);
					g.fill(new Rectangle(pixelX, pixelY, TILE_WIDTH, TILE_HEIGHT));
					break;
				default:
					
					break;
				}
				pixelX += TILE_WIDTH;
			}
			pixelY += TILE_HEIGHT;
			pixelX = 0;
		}
		
		for (Car car : world.getCars()) {
			g.drawImage(car.getImage(), car.currentX*TILE_WIDTH, car.currentY*TILE_HEIGHT);
		}

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		world = new World(TILE_WIDTH,TILE_HEIGHT);

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		for (Car car : world.getCars()) {
			car.move();
		}

	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer sim = new AppGameContainer(new Simulation(
				"Strasse"));
		sim.setDisplayMode(WIDTH, HEIGHT, false);
		sim.setMaximumLogicUpdateInterval(1000);
		sim.setMinimumLogicUpdateInterval(1000);
		sim.setVSync(true);
		sim.setShowFPS(false);
		sim.start();
	}

}
