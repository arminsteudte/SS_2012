package praktikum;

import java.util.ArrayList;

public class World {


	private int[][] map;
	private int roxelLength;
	private TupelSpaceAdapter tsAdapter;
	private int[][] streetStructure;

	private ArrayList<Car> cars;

	public World() {
		tsAdapter = new TupelSpaceAdapter("TestTupelSpace");
		this.roxelLength = 0;
		map = null;
		this.streetStructure = new int[][]{
				{Simulation.BLOCK_FLAG, Simulation.BLOCK_FLAG, Simulation.STREET_FLAG, Simulation.STREET_FLAG},
				{Simulation.BLOCK_FLAG, Simulation.BLOCK_FLAG, Simulation.STREET_FLAG, Simulation.STREET_FLAG},
				{Simulation.STREET_FLAG, Simulation.STREET_FLAG, Simulation.CROSSING_FLAG, Simulation.CROSSING_FLAG},
				{Simulation.STREET_FLAG, Simulation.STREET_FLAG, Simulation.CROSSING_FLAG, Simulation.CROSSING_FLAG}
		};
	}

	public int getRoxelLength() {
		return roxelLength;
	}

	public int[][] getMap() {
		return map;
	}
	
	public int getMapHeight(){
		return map.length;
	}
	
	public int getMapWidth(){
		return map[0].length;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public void initWorld() {
		tsAdapter.init();
		tsAdapter.clearSpace();		//initiales Löschen des Tupelspace!
		createMap();
		createRoxelRepresentation();
		initCrossings();
		initCars();
		
		/*
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				sb.append(map[i][j]);
				sb.append(" ");
			}
			System.out.println(sb);
			sb = new StringBuilder();
		}*/
	}

	public void createMap() {
		MapStructure mapStruct = tsAdapter
				.readIfExistFromSpace(new MapStructure("Strukturtupel"));

		if (mapStruct != null) {
			
		} else {
			mapStruct = new MapStructure("Strukturtupel", 2, 24, 24);
			tsAdapter.writeToSpace(mapStruct);
		}
		roxelLength = mapStruct.getRoxelLength();
		
		//Berechnen wie häufig die definierte Strassen-Struktur in die Vorgabe durch das Strukturtupel passt
		int countRoxelX = (mapStruct.getRoxelCountX() / streetStructure[0].length) * streetStructure[0].length;
		int countRoxelY = (mapStruct.getRoxelCountX() / streetStructure.length) * streetStructure.length;
		
		//Map initialisieren
		map = new int[countRoxelY][countRoxelX];

		//erste Zeilen nach Vorgabe der Strassen-Struktur erstellen
		int timesForX = 0;
		while(timesForX < (mapStruct.getRoxelCountX() / streetStructure[0].length)){
			for (int i = 0; i < streetStructure.length; i++) {
				System.arraycopy(streetStructure[i], 0, map[i], timesForX * streetStructure[i].length, streetStructure[i].length);
			}
			timesForX++;
		}
		
		//nachdem die ersten Zeilen erstellt wurden diese in Y-Richtung nach unten kopieren
		int timesFory = 1;
		while(timesFory < (mapStruct.getRoxelCountY() / streetStructure.length)){
			for (int i = 0; i < streetStructure.length; i++) {
				System.arraycopy(map[i], 0, map[timesFory * streetStructure.length + i] , 0, map[i].length);
			}
			timesFory++;
		}
		
		/*
		map = new int[mapStruct.getRoxelCountY()][mapStruct.getRoxelCountX()];
		boolean block = true;
		for (int y = 0; y < mapStruct.getRoxelCountY(); y++) {
			for (int x = 0; x < mapStruct.getRoxelCountX(); x++) {
				if (y % 2 == 0) {
					if (block) {
						map[y][x] = Simulation.BLOCK_FLAG;
						block = false;
					} else {
						map[y][x] = Simulation.STREET_FLAG;
						block = true;
					}
				} else {
					map[y][x] = Simulation.STREET_FLAG;
				}
			}
			block = true;
		}*/
	}
	
	public TrafficLight[] getAllTrafficLights(){
		return tsAdapter.getAllTrafficLights();
	}
	
	public Roxel[] getAllOccupiedRoxels(){
		return tsAdapter.getAllOccupiedRoxels(); 
	}
	
	private void createRoxelRepresentation(){
		
		Roxel tempRoxel = null;
		
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if(map[y][x]==Simulation.STREET_FLAG){
					tempRoxel = new Roxel(x, y, 0, DirectionType.TODDECIDE, false);
					tsAdapter.writeToSpace(tempRoxel);
				} else if(map[y][x]==Simulation.CROSSING_FLAG){
					tempRoxel = new Roxel(x, y, 0, DirectionType.TODDECIDE, true);
					tsAdapter.writeToSpace(tempRoxel);
				}

			}	
		}
	}
	
	private void initCrossings(){
		Crossing tempCrossing = new Crossing(2, 2, tsAdapter);
		tempCrossing.start();
	}

	private void initCars() {
		cars = new ArrayList<Car>();
		Car car = null;

		
		car = createCar(1, DirectionType.EAST, 1, 0, 3);
		cars.add(car);
		
		car = createCar(2, DirectionType.WEST, 2, 23, 2);
		cars.add(car);
		
		car = createCar(3, DirectionType.SOUTH, 1, 2, 0);
		cars.add(car);
		
		car = createCar(4, DirectionType.NORTH, 3, 11, 19);
		cars.add(car);
		
		car = createCar(5, DirectionType.NORTH, 1, 11, 18);
		cars.add(car);
		
		car = createCar(6, DirectionType.WEST, 4, 23, 10);
		cars.add(car);
		
		for (Car c : cars) {
			c.start();
		}
		
		
	}
	
	private Car createCar(int id, DirectionType direction, int sleepTime, int posX, int posY){
		Roxel returnRoxel = null;
		
		Car tempCar = new Car(id, direction, calcCarSleepTime(sleepTime), posX, posY, tsAdapter, this);
		
		returnRoxel = tsAdapter.readRoxelByPosition(posX, posY);
		returnRoxel.setDirection(tempCar.getDirection());
		returnRoxel.setOccupingCar(tempCar.getIdentifier());
		tsAdapter.writeToSpace(returnRoxel);
		
		return tempCar;
	}
	
	private long calcCarSleepTime(int velocity){
		return Math.round((1.0/velocity)*roxelLength*1000);
	}
}
