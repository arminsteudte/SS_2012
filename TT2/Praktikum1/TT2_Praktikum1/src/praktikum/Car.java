package praktikum;

import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Car extends Thread {

	private DirectionType direction;
	private int identifier;
	private Image img;
	private long sleepTime;
	private int currentX;
	private int currentY;
	
	private TupelSpaceAdapter tsAdapter;
	

	public Car(int id, DirectionType dir, long sleepTime, int currentX, int currentY, TupelSpaceAdapter tsa) {
		this.identifier = id;
		this.direction = dir;
		this.sleepTime = sleepTime;
		this.currentX = currentX;
		this.currentY = currentY;
		this.tsAdapter = tsa;
		
	}
	
	
	public DirectionType getDirection() {
		return direction;
	}


	public void setDirection(DirectionType direction) {
		this.direction = direction;
	}


	@SpaceId
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



	public void move(){
		switch (direction) {
		case WEST:
			currentX -= 1;
			break;
		case EAST:
			currentX += 1;
			break;
		case NORTH:
			currentY -= 1;
			break;
		case SOUTH:
			currentY += 1;
			break;

		default:
			break;
		}
	}
	
	public Point getPosition(){
		return new Point(currentX, currentY);
	}

	@Override
	public void run() {
		
		while(true){
			this.move();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
