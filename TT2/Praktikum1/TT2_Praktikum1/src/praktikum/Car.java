package praktikum;

import java.awt.Point;

public class Car extends Thread {

	private DirectionType direction;
	private int identifier;
	private long sleepTime;
	private int currentX;
	private int currentY;
	
	private TupelSpaceAdapter tsAdapter;
	private World world;
	

	public Car(int id, DirectionType dir, long sleepTime, int currentX, int currentY, TupelSpaceAdapter tsa, World world) {
		this.identifier = id;
		this.direction = dir;
		this.sleepTime = sleepTime;
		this.currentX = currentX;
		this.currentY = currentY;
		this.tsAdapter = tsa;
		this.world = world;
		
	}
	
	
	public DirectionType getDirection() {
		return direction;
	}


	public void setDirection(DirectionType direction) {
		this.direction = direction;
	}

	public int getIdentifier() {
		return identifier;
	}


	public void setIdentifier(int id) {
		this.identifier = id;
	}



	public int getCurrentX() {
		return currentX;
	}



	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}



	public int getCurrentY() {
		return currentY;
	}



	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}


	public void tryToMove(DirectionType oldDirection){
		if (crossingFreeToPass()){
			move(oldDirection);
			}
	}
	
	public void tryToMove(){
		this.tryToMove(direction);
	}
	
	public void moveToDirection(DirectionType direction){
		DirectionType oldDirection = this.direction;
		this.direction = direction;
		tryToMove(oldDirection);
	}
	
	private void move(DirectionType oldDirection){
		int nextposX = currentX;
		int nextposY = currentY;
		
		if(currentY==world.getMapHeight()-1 && direction == DirectionType.SOUTH){
			nextposY = 0;
		} else if(currentY==0 && direction == DirectionType.NORTH) {
			nextposY = world.getMapHeight()-1;
		}else if(currentX==0 && direction == DirectionType.WEST){
			nextposX = world.getMapWidth()-1;
		}else if(currentX==world.getMapWidth()-1 && direction == DirectionType.EAST){
			nextposX =0;
		}else {
			switch (direction) {
			case WEST:
				nextposX -= 1;
				break;
			case EAST:
				nextposX += 1;
				break;
			case NORTH:
				nextposY -= 1;
				break;
			case SOUTH:
				nextposY += 1;
				break;
			default:
				break;
			}
		}
		
		Roxel newRoxel = tsAdapter.takeRoxel(new Roxel(nextposX, nextposY));
		
		if(newRoxel!=null){
			//neuen Roxel belegen
			newRoxel.setOccupingCar(identifier);
			newRoxel.setDirection(direction);
			tsAdapter.writeToSpace(newRoxel);
			
			Roxel oldRoxel = tsAdapter.takeRoxel(new Roxel(currentX, currentY, identifier, oldDirection));
			
			if(oldRoxel!=null){
				//alten Roxel freigeben
				oldRoxel.setOccupingCar(0);
				oldRoxel.setDirection(DirectionType.TODDECIDE);
				tsAdapter.writeToSpace(oldRoxel);
				
				currentX = nextposX;
				currentY = nextposY;
			}else{
				System.out.println("Altes Roxel nicht gefunden! ID:" + identifier + " " + nextposX + " " + nextposY);
				this.direction = oldDirection;
			}
			
		}else{
			System.out.println("Neues Roxel nicht gefunden! ID:" + identifier + " " + currentX + " " + currentY + " " + direction);
			this.direction = oldDirection;
		}
	}
	
	
	private boolean crossingFreeToPass(){
		int offsetX = 0;
		int offsetY = 0;
		
		int crossingOffsetX = 0;
		int crossingOffsetY = 0;
		
		boolean passable = false;		
		
		switch (direction) {
		case NORTH:
			offsetX = 1;
			crossingOffsetY = -1;
			break;
		case WEST:
			offsetY = -1;
			crossingOffsetX = -1;
			break;
		case EAST:
			offsetY = 1;
			crossingOffsetX = 1;
			break;
		case SOUTH:
			offsetX = -1;
			crossingOffsetY = 1;
			break;
		default:
			break;
		}
		
		TrafficLight template = new TrafficLight(currentX+offsetX, currentY+offsetY, null);
		TrafficLight trafficLight = tsAdapter.readTrafficLight(template);
		
		//Auf Kreuzung prüfen
		Roxel previewRoxel = tsAdapter.readRoxelByPosition(currentX + crossingOffsetX, currentY + crossingOffsetY);
		
		if(trafficLight==null){
			passable = true;
		}else{
			if(trafficLight.getStatus() == TrafficStatus.RED && previewRoxel.getCrossing()) {
				passable = false;
			}else{
				passable = true;
			}
		}
		
		return passable;
	}
	
	public Point getPosition(){
		return new Point(currentX, currentY);
	}

	@Override
	public void run() {
		
		while(true){
			this.tryToMove();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
