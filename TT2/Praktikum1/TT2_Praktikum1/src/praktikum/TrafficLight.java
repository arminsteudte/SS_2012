package praktikum;

import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class TrafficLight {
	
	Integer posX;
	Integer posY;
	TrafficStatus status;
	
	
	public TrafficLight(){
		super();
	}
	
	public TrafficLight(int posX, int posY, TrafficStatus status) {
		this.posX = posX;
		this.posY = posY;
		this.status = status;
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

	@SpaceIndex(type=SpaceIndexType.BASIC)
	public TrafficStatus getStatus() {
		return status;
	}

	public void setStatus(TrafficStatus status) {
		this.status = status;
	}
	

}
