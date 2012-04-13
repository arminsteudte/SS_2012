package praktikum;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class Simulation extends BasicGame {
	
	private static int TILE_ZOOM = 15;
	
	private static int WIDTH = 800 ;
	private static int HEIGHT = 800;
	
	public static final int BLOCK_FLAG = 1;
	public static final int STREET_H_FLAG = 2;
	public static final int STREET_V_FLAG = 3;
	public static final int CROSSING_FLAG = 4;
	public static final int PLAYER1_ID = 100;
	public static final int PLAYER2_ID = 200;
	
	private static Color COLOR_BACKGROUND = Color.darkGray;

	private World world;
	private Car player1Car;
	private Car player2Car;

	public Simulation(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		int[][] map = world.getMap();
		int mapWidth = map[0].length;
		int mapHeigth = map.length;
		Image street_v = new Image("resource/gfx/street_v.gif").getScaledCopy(TILE_ZOOM * world.getRoxelLength(), TILE_ZOOM*world.getRoxelLength());
		Image street_h = new Image("resource/gfx/street_h.gif").getScaledCopy(TILE_ZOOM * world.getRoxelLength(), TILE_ZOOM*world.getRoxelLength());
		Image crossing = new Image("resource/gfx/crossing.gif").getScaledCopy(TILE_ZOOM * world.getRoxelLength(), TILE_ZOOM*world.getRoxelLength());
		Image block = new Image("resource/gfx/block.png").getScaledCopy(TILE_ZOOM * world.getRoxelLength(), TILE_ZOOM*world.getRoxelLength()); 
		
		int pixelX = 0;
		int pixelY=0;
		
		g.setBackground(COLOR_BACKGROUND);
		for (int y = 0; y < mapHeigth; y++) {
			for (int x = 0; x < mapWidth; x++) {
				int value = map[y][x];
				switch (value) {
				case STREET_H_FLAG:
					g.drawImage(street_h, pixelX, pixelY);
					break;	
				case STREET_V_FLAG:
					g.drawImage(street_v, pixelX, pixelY);
					break;
				case CROSSING_FLAG:
					g.drawImage(crossing, pixelX, pixelY);
					break;
				case BLOCK_FLAG:
					g.drawImage(block, pixelX, pixelY);
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
		player1Car = world.getPlayer1Car();
		player2Car = world.getPlayer2Car();
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		Input input = gc.getInput();
		
		// Spieler1 bewegen
		if (input.isKeyPressed(Input.KEY_UP)) {
			//versuche nach oben zu fahren
			player1Car.moveToDirection(DirectionType.NORTH);
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			//versuche nach unten zu fahren
			player1Car.moveToDirection(DirectionType.SOUTH);
		}
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			//versuche nach links zu fahren
			player1Car.moveToDirection(DirectionType.WEST);
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			//versuche nach rechts zu fahren
			player1Car.moveToDirection(DirectionType.EAST);
		}

	
		// Spieler2 bewegen
		if (input.isKeyPressed(Input.KEY_W)) {
			//versuche nach oben zu fahren
			player2Car.moveToDirection(DirectionType.NORTH);
		}
		if (input.isKeyPressed(Input.KEY_S)) {
			//versuche nach unten zu fahren
			player2Car.moveToDirection(DirectionType.SOUTH);
		}
		if (input.isKeyPressed(Input.KEY_A)) {
			//versuche nach links zu fahren
			player2Car.moveToDirection(DirectionType.WEST);
		}
		if (input.isKeyPressed(Input.KEY_D)) {
			//versuche nach rechts zu fahren
			player2Car.moveToDirection(DirectionType.EAST);
		}
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
