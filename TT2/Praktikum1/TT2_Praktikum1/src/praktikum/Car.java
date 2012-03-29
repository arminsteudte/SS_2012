package praktikum;

import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Car {

	public DirectionType direction;
	public Image img;
	public int currentX;
	public int currentY;

	public Car(DirectionType dir, int currentX, int currentY) {
		this.direction = dir;
		this.currentX = currentX;
		this.currentY = currentY;
		initImage();
	}
	
	public void move(){
		switch (direction) {
		case LEFT:
			currentX -= 1;
			break;
		case RIGHT:
			currentX += 1;
			break;
		case UP:
			currentY -= 1;
			break;
		case DOWN:
			currentY += 1;
			break;

		default:
			break;
		}
	}
	
	public void initImage(){
		try {
			switch (direction) {
			case LEFT:
				img =  new Image("resource/gfx/car_left.png");
				break;
			case RIGHT:
				img =  new Image("resource/gfx/car_right.png");
				break;
			case UP:
				img =  new Image("resource/gfx/car_up.png");
				break;
			case DOWN:
				img =  new Image("resource/gfx/car_down.png");
				break;
			default:
				break;
			}
		} catch (SlickException e) {
			System.out.println("Fehler: Grafik konnte nicht gefunden werden");
		}

	}
	
	public Image getImage(){
		return img;
	}
	
	public Point getPosition(){
		return new Point(currentX, currentY);
	}
}
