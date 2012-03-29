package praktikum;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class World {
	
	private int[][] map;
	private int tileWidth;		//Breite eines Tiles
	private int tileHeight;		//Hoehe eines Tiles
	
	private ArrayList<Car> cars;
	
	
	public World(int width, int height){
		this.tileWidth = width;
		this.tileHeight = height;
		map = new int[][] {
				{1,0,1,0,1,0,1,0,1},
				{0,0,0,0,0,0,0,0,0},
				{1,0,1,0,1,0,1,0,1},
				{0,0,0,0,0,0,0,0,0},
				{1,0,1,0,1,0,1,0,1}
		};
		initCars();
	}
	
	public int[][] getMap(){
		return map;
	}
	
	public ArrayList<Car> getCars(){
		return cars;
	}
	
	private void initWorld(){
		
	}
	
	private void initCars(){
		cars = new ArrayList<Car>();
		cars.add(new Car(DirectionType.DOWN, 7, 0));
		cars.add(new Car(DirectionType.LEFT, 8, 1));
	}
}
