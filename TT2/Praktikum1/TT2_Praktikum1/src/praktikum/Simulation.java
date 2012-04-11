package praktikum;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Simulation extends BasicGame {
	
	private static int TILE_ZOOM = 15;
	
	private static int WIDTH = 800 ;
	private static int HEIGHT = 600;
	
	public static final int BLOCK_FLAG = 1;
	public static final int STREET_FLAG = 0;
	public static final int CROSSING_FLAG = 2;
	
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
				case STREET_FLAG:
					g.setColor(COLOR_STREET);
					g.fill(new Rectangle(pixelX, pixelY, world.getRoxelLength()*TILE_ZOOM, world.getRoxelLength()*TILE_ZOOM));
					break;
				case CROSSING_FLAG:
					g.setColor(COLOR_STREET);
					g.fill(new Rectangle(pixelX, pixelY, world.getRoxelLength()*TILE_ZOOM, world.getRoxelLength()*TILE_ZOOM));
					break;
				case BLOCK_FLAG:
					g.setColor(COLOR_BLOCK);
					g.fill(new Rectangle(pixelX, pixelY, world.getRoxelLength()*TILE_ZOOM, world.getRoxelLength()*TILE_ZOOM));
					break;
				default:
					
					break;
				}
				pixelX += world.getRoxelLength()*TILE_ZOOM;
			}
			pixelY += world.getRoxelLength()*TILE_ZOOM;
			pixelX = 0;
		}
		
		Roxel[] occupiedRoxels = world.getAllOccupiedRoxels();
		if(occupiedRoxels.length > 0 ){
			for (Roxel roxel : occupiedRoxels) {
				//System.out.println(roxel.toString());
				g.setColor(Color.red);
				//g.drawImage(roxel.getImage(), roxel.getX()*world.getRoxelLength()*TILE_ZOOM, roxel.getY()*world.getRoxelLength()*TILE_ZOOM);	
				Image car = roxel.getImage().getScaledCopy(TILE_ZOOM * world.getRoxelLength(), TILE_ZOOM*world.getRoxelLength());
				g.drawImage(car, roxel.getX()*world.getRoxelLength()*TILE_ZOOM, roxel.getY()*world.getRoxelLength()*TILE_ZOOM);
			}
		}else{
			System.out.println("Keine belegten Roxel gefunden.");
		}
		
		TrafficLight[] trafficLights = world.getAllTrafficLights();
		if(trafficLights.length > 0 ){
			
			TrafficStatus status = null;
			int pixelFactor = world.getRoxelLength()*TILE_ZOOM;
			int centerOffset = (world.getRoxelLength()*TILE_ZOOM/2);
			
					
			for (TrafficLight trafficLight : trafficLights) {
				
				status = trafficLight.getStatus();
				
				switch (status) {
				case GREEN:
					g.setColor(Color.green);
					break;
				case RED:
					g.setColor(Color.red);
					break;
				case OFF:
					g.setColor(Color.gray);
					break;
				default:
					break;
				}
				
				g.fill(new Circle(trafficLight.getPosX()*pixelFactor + centerOffset , trafficLight.getPosY() * pixelFactor + centerOffset, TILE_ZOOM / 2));
				
			}
		}
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		world = new World();
		world.initWorld();
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {

	}

	
	public static void main(String[] args) throws SlickException {
		AppGameContainer sim = new AppGameContainer(new Simulation(
				"Strasse"));
		sim.setDisplayMode(WIDTH, HEIGHT, false);
		sim.setVSync(true);
		sim.setShowFPS(false);
		sim.start();
	}

}
