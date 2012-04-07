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
				{Simulation.BLOCK_FLAG, Simulation.STREET_FLAG},
				{Simulation.STREET_FLAG, Simulation.STREET_FLAG}
		};
	}

	public int getRoxelLength() {
		return roxelLength;
	}

	public int[][] getMap() {
		return map;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public void initWorld() {
		tsAdapter.init();
		createMap();
		createRoxelRepresentation();
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
			mapStruct = new MapStructure("Strukturtupel", 2, 11, 11);
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
	
	public ArrayList<Roxel> getAllRoxels(){
		return tsAdapter.readManyFromSpace(new Roxel()); 
	}
	
	private void createRoxelRepresentation(){
		
		Roxel tempRoxel = null;
		
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				if(map[y][x]==Simulation.STREET_FLAG){
					tempRoxel = new Roxel(x, y, 0, DirectionType.TODDECIDE);
					tsAdapter.writeToSpace(tempRoxel);
				}

			}	
		}
	}

	private void initCars() {
		cars = new ArrayList<Car>();
		Car car = null;
		Roxel templateRoxel = null;
		Roxel returnRoxel = null;
		
		templateRoxel = new Roxel(7,0);
		car = new Car(1, DirectionType.SOUTH, calcCarSleepTime(2), 7, 0, tsAdapter);
		returnRoxel = tsAdapter.readIfExistFromSpace(templateRoxel);
		returnRoxel.setDirection(car.getDirection());
		returnRoxel.setOccupingCar(car.getIdentifier());
		tsAdapter.writeToSpace(returnRoxel);
		cars.add(car);
		
		templateRoxel = new Roxel(0,1);
		car = new Car(2, DirectionType.EAST, calcCarSleepTime(1), 0, 1, tsAdapter);
		returnRoxel = tsAdapter.readIfExistFromSpace(templateRoxel);
		returnRoxel.setDirection(car.getDirection());
		returnRoxel.setOccupingCar(car.getIdentifier());
		tsAdapter.writeToSpace(returnRoxel);
		cars.add(car);
		
		
		for (Car c : cars) {
			c.start();
		}
		
		
	}
	
	private long calcCarSleepTime(int velocity){
		return Math.round((1.0/velocity)*roxelLength*1000);
	}
}
