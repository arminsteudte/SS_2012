package praktikum;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

@SpaceClass
public class Roxel {
	private Integer x;
	private Integer y;
	private Integer occupingCar;
	private DirectionType direction;
	private Boolean crossing;
	
	public Roxel(){
		super();
	}
	
	public Roxel(int x, int y, int occupingCar, DirectionType direction, Boolean crossing) {
		super();
		this.x = x;
		this.y = y;
		this.occupingCar = occupingCar;
		this.direction = direction;
		this.crossing = crossing;
	}
	
	public Roxel(int x, int y, int occupingCar, DirectionType direction) {
		this(x, y, occupingCar, direction, null);
	}
	
	public Roxel(int x, int y, int occupingCar) {
		this(x, y, occupingCar, DirectionType.TODDECIDE, null);
	}
	
	public Roxel(int x, int y) {
		this(x, y, 0, DirectionType.TODDECIDE, null);
	}

	@SpaceIndex(type=SpaceIndexType.BASIC)
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@SpaceIndex(type=SpaceIndexType.BASIC)
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@SpaceIndex(type=SpaceIndexType.EXTENDED)
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
	
	@SpaceIndex(type=SpaceIndexType.BASIC)
	public Boolean getCrossing() {
		return crossing;
	}

	public void setCrossing(Boolean isCrossing) {
		this.crossing = isCrossing;
	}

	public Image getImage(){
		if(occupingCar == Simulation.PLAYER1_ID){
			return getPlayer1Image();
		}else if(occupingCar == Simulation.PLAYER2_ID){
			return getPlayer2Image();
		}else{
			return getNormalImage();
		}
	}
	
	private Image getPlayer2Image(){
		Image returnImage = null;
		
		try {
			switch (direction) {
			case WEST:
				returnImage =  new Image("resource/gfx/player2_left.png");
				break;
			case EAST:
				returnImage =  new Image("resource/gfx/player2_right.png");
				break;
			case NORTH:
				returnImage =  new Image("resource/gfx/player2_up.png");
				break;
			case SOUTH:
				returnImage =  new Image("resource/gfx/player2_down.png");
				break;
			default:
				break;
			}
		} catch (SlickException e) {
			System.out.println("Fehler: Grafik konnte nicht gefunden werden");
		}
		return returnImage;
	}
	
	private Image getPlayer1Image(){
		Image returnImage = null;
		
		try {
			switch (direction) {
			case WEST:
				returnImage =  new Image("resource/gfx/player1_left.png");
				break;
			case EAST:
				returnImage =  new Image("resource/gfx/player1_right.png");
				break;
			case NORTH:
				returnImage =  new Image("resource/gfx/player1_up.png");
				break;
			case SOUTH:
				returnImage =  new Image("resource/gfx/player1_down.png");
				break;
			default:
				break;
			}
		} catch (SlickException e) {
			System.out.println("Fehler: Grafik konnte nicht gefunden werden");
		}
		return returnImage;
	}
	
	private Image getNormalImage(){
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
