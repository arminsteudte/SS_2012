package praktikum;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Roxel {
	private Integer x;
	private Integer y;
	private Integer occupingCar;
	private DirectionType direction;
	
	public Roxel(){
		super();
	}
	
	public Roxel(int x, int y, int occupingCar, DirectionType direction) {
		super();
		this.x = x;
		this.y = y;
		this.occupingCar = occupingCar;
		this.direction = direction;
	}
	
	public Roxel(int x, int y, int occupingCar) {
		this(x, y, occupingCar, DirectionType.TODDECIDE);
	}
	
	public Roxel(int x, int y) {
		this(x, y, 0, DirectionType.TODDECIDE);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getOccupingCar() {
		return occupingCar;
	}

	public void setOccupingCar(int occupingCar) {
		this.occupingCar = occupingCar;
	}

	public DirectionType getDirection() {
		return direction;
	}

	public void setDirection(DirectionType direction) {
		this.direction = direction;
	}
	
	public String toString(){
		return x + "-" + y + " " + direction + " " + occupingCar;
	}
	
	public Image getImage(){
		Image returnImage = null;
		
		try {
			switch (direction) {
			case WEST:
				returnImage =  new Image("resource/gfx/car_left.png");
				break;
			case EAST:
				returnImage =  new Image("resource/gfx/car_right.png");
				break;
			case NORTH:
				returnImage =  new Image("resource/gfx/car_up.png");
				break;
			case SOUTH:
				returnImage =  new Image("resource/gfx/car_down.png");
				break;
			default:
				break;
			}
		} catch (SlickException e) {
			System.out.println("Fehler: Grafik konnte nicht gefunden werden");
		}

		return returnImage;
	}


	

}
