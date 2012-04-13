package praktikum;

import java.awt.Point;
import java.util.ArrayList;

import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class Crossing extends Thread {
	
	private static final Point[] HORIZONTAL_OFFSETS = {new Point(-2,1), new Point(-1,1), new Point(0,1), new Point(1,0), new Point(2,0), new Point(3,0), } ;
	private static final Point[] VERTICAL_OFFSETS = {new Point(0,-2), new Point(0,-1), new Point(0,0), new Point(1,1), new Point(1,2), new Point(1,3)};
	
	private Integer posX;
	private Integer posY;
	TrafficLight[] horizontalTrafficLights = new TrafficLight[2];
	TrafficLight[] verticalTrafficLights = new TrafficLight[2];
	private TupelSpaceAdapter tsAdapter;
	
	
	
	public Crossing(int posX, int posY, TupelSpaceAdapter tsAdapter) {
		this.posX = posX;
		this.posY = posY;
		this.tsAdapter = tsAdapter;
		
		initTrafficLights();
		
	}
	
	private void initTrafficLights(){
		verticalTrafficLights[0] = new TrafficLight(posX-1, posY-1, TrafficStatus.GREEN); //Ampel links oben
		tsAdapter.writeTrafficToSpace(verticalTrafficLights[0]);
		
		horizontalTrafficLights[0] = new TrafficLight(posX+2, posY-1, TrafficStatus.GREEN); //Ampel rechts oben
		tsAdapter.writeTrafficToSpace(horizontalTrafficLights[0]);
		
		verticalTrafficLights[1] = new TrafficLight(posX+2, posY+2, TrafficStatus.GREEN); //Ampel rechts unten
		tsAdapter.writeTrafficToSpace(verticalTrafficLights[1]);
		
		horizontalTrafficLights[1] = new TrafficLight(posX-1, posY+2, TrafficStatus.GREEN); //Ampel links unten
		tsAdapter.writeTrafficToSpace(horizontalTrafficLights[1]);
	}
	
	private void watchCrossing(){
		
		Roxel rox = null;
		
		ArrayList<Roxel> horizontalRoxels = new ArrayList<Roxel>();
		ArrayList<Roxel> verticalRoxels = new ArrayList<Roxel>();
		
		for (int i = 0; i < VERTICAL_OFFSETS.length; i++) {
			
			rox = tsAdapter.readOccupiedRoxel(posX + VERTICAL_OFFSETS[i].x, posY + VERTICAL_OFFSETS[i].y);
			if(rox != null)
				verticalRoxels.add(rox);
			
		}
		
		for (int i = 0; i < HORIZONTAL_OFFSETS.length; i++) {
			
			rox = tsAdapter.readOccupiedRoxel(posX + HORIZONTAL_OFFSETS[i].x, posY + HORIZONTAL_OFFSETS[i].y);
			if(rox != null)	
				horizontalRoxels.add(rox);
			
		}
		
		int countHorizontal = horizontalRoxels.size();
		int countVertical = verticalRoxels.size();
		
//		System.out.println("countHorizontal: "+countHorizontal);
//		System.out.println("countvertical: "+countVertical);
		
		//prüfen ob Ampel benötigt wird
		if(countHorizontal > 0 && countVertical > 0) {
			if(countHorizontal >= countVertical){
				for (TrafficLight trafficlight : horizontalTrafficLights) {
					tsAdapter.takeTrafficLight(trafficlight);
					trafficlight.setStatus(TrafficStatus.GREEN);
					tsAdapter.writeTrafficToSpace(trafficlight);
				}
				for (TrafficLight trafficlight : verticalTrafficLights) {
					tsAdapter.takeTrafficLight(trafficlight);
					trafficlight.setStatus(TrafficStatus.RED);
					tsAdapter.writeTrafficToSpace(trafficlight);
				}
			} else {
				for (TrafficLight trafficlight : horizontalTrafficLights) {
					tsAdapter.takeTrafficLight(trafficlight);
					trafficlight.setStatus(TrafficStatus.RED);
					tsAdapter.writeTrafficToSpace(trafficlight);
				}
				for (TrafficLight trafficlight : verticalTrafficLights) {
					tsAdapter.takeTrafficLight(trafficlight);
					trafficlight.setStatus(TrafficStatus.GREEN);
					tsAdapter.writeTrafficToSpace(trafficlight);
				}
			}
		}else{
			for (TrafficLight trafficlight : horizontalTrafficLights) {
				tsAdapter.takeTrafficLight(trafficlight);
				trafficlight.setStatus(TrafficStatus.GREEN);
				tsAdapter.writeTrafficToSpace(trafficlight);
			}
			for (TrafficLight trafficlight : verticalTrafficLights) {
				tsAdapter.takeTrafficLight(trafficlight);
				trafficlight.setStatus(TrafficStatus.GREEN);
				tsAdapter.writeTrafficToSpace(trafficlight);
			}
		}
	}

	@SpaceIndex(type=SpaceIndexType.BASIC)
	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	@SpaceIndex(type=SpaceIndexType.BASIC)
	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public TrafficLight[] getHorizontal() {
		return horizontalTrafficLights;
	}

	public void setHorizontal(TrafficLight[] horizontal) {
		this.horizontalTrafficLights = horizontal;
	}

	public TrafficLight[] getVertical() {
		return verticalTrafficLights;
	}

	public void setVertical(TrafficLight[] vertical) {
		this.verticalTrafficLights = vertical;
	}
	
	@Override
	public void run() {
		
		while(true){
			this.watchCrossing();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
