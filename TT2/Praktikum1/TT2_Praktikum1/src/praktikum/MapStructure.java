package praktikum;

import com.gigaspaces.annotation.pojo.SpaceId;

public class MapStructure {
	private String id;
	private Integer roxelLength;
	private Integer roxelCountX;
	private Integer roxelCountY;
	
	public MapStructure(){
		super();
	}
	
	public MapStructure(String id, Integer roxelLength, Integer roxelCountX, Integer roxelCountY){
		this();
		this.id = id;
		this.roxelCountX = roxelCountX;
		this.roxelCountY = roxelCountY;
		this.roxelLength = roxelLength;
	}
	
	public MapStructure(String id){
		this();
		this.id = id;
		this.roxelCountX = null;
		this.roxelCountY = null;
		this.roxelLength = null;
	}


	@SpaceId
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRoxelLength() {
		return roxelLength;
	}

	public void setRoxelLength(Integer roxelLength) {
		this.roxelLength = roxelLength;
	}

	public Integer getRoxelCountX() {
		return roxelCountX;
	}

	public void setRoxelCountX(Integer roxelCountX) {
		this.roxelCountX = roxelCountX;
	}

	public Integer getRoxelCountY() {
		return roxelCountY;
	}

	public void setRoxelCountY(Integer roxelCountY) {
		this.roxelCountY = roxelCountY;
	}
	
	
	
	

}
