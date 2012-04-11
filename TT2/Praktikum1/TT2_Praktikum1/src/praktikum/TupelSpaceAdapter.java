package praktikum;

import java.util.concurrent.TimeUnit;

import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.gsm.GridServiceManager;
import org.openspaces.admin.pu.ProcessingUnit;
import org.openspaces.admin.pu.ProcessingUnitAlreadyDeployedException;
import org.openspaces.admin.space.SpaceDeployment;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.dao.DataAccessException;

import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;

public class TupelSpaceAdapter {
	
	private String tupelSpaceName;
	private GigaSpace space;
	
	public TupelSpaceAdapter(String tupelSpaceName){
		this.tupelSpaceName = tupelSpaceName;
		this.space = null;
	}
	
	public void init(){
		try {
		    //create an admin instance to interact with the cluster
		    Admin admin = new AdminFactory().createAdmin();
		    
		    //locate a grid service manager and deploy a partioned data grid with 2 primaries and one backup for each primary
		    GridServiceManager esm = admin.getGridServiceManagers().waitForAtLeastOne();
		    ProcessingUnit pu = esm.deploy(new SpaceDeployment(tupelSpaceName).partitioned(2, 1));
		    
		    //Once your data grid has been deployed, wait for 4 instances (2 primaries and 2 backups)
			pu.waitFor(4, 30, TimeUnit.SECONDS);

			//and finally, obtain a reference to it
			space = pu.waitForSpace().getGigaSpace();
		    
		} catch (ProcessingUnitAlreadyDeployedException e)  { 
		    getSpace(); 
		}
		
	}
	
	public void clearSpace(){
		space.clear(null);
	}
	
	private void getSpace() {
		IJSpace jSpace = new UrlSpaceConfigurer("jini://*/*/" + tupelSpaceName).space();
		
		space = new GigaSpaceConfigurer(jSpace).gigaSpace();
	}	

	public void writeToSpace(Object tupel) throws IllegalStateException {
		
		if( space != null ) {
			space.write(tupel);	
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
	}
	
	
	public <T> T readFromSpace(T template) throws IllegalStateException {
		T tupel = null;
		
		if( space != null ) {
			tupel = space.read(template);
			
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return tupel;
	}
	
	public void writeTrafficToSpace(TrafficLight trafficLight){
		if( space != null ) {
			space.write(trafficLight);	
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
	}
	
	public Roxel takeRoxel(int posX, int posY) throws IllegalStateException {
		Roxel rox = null;
		
		SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? AND y = ?");
		query = query.setParameter(1, posX).setParameter(2, posY);
		
		if( space != null ) {
			rox = space.takeIfExists(query);			
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return rox;
	}
	
	public Roxel takeRoxel(int posX, int posY, int identifier, DirectionType direction) throws IllegalStateException {
		Roxel rox = null;
		
		SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? AND y = ? AND occupingCar = ? AND direction = ?");
		query = query.setParameter(1, posX).setParameter(2, posY).setParameter(3, identifier).setParameter(4, direction);
		
		if( space != null ) {
			rox = space.takeIfExists(query);			
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return rox;
	}
	
	public Roxel takeRoxel(Roxel template) throws RuntimeException {
		Roxel rox = null;
		
		if( space != null ) {
			try {
				rox = space.takeIfExists(template);
			} catch (DataAccessException e) {
				//nix
			}
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return rox;
	}
	
	public Roxel readOccupiedRoxel(int posX, int posY) throws IllegalStateException {
		Roxel rox = null;
		SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? AND y = ? AND occupingCar > ?");
		query = query.setParameter(1, posX).setParameter(2, posY).setParameter(3, 0);
		
		if( space != null ) {
			rox = space.readIfExists(query);			
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return rox;
	}
	
	public <T> T readIfExistFromSpace(T template) throws RuntimeException {
		T tupel = null;
		
		if( space != null ) {
			try {
				tupel = space.readIfExists(template);
			} catch (DataAccessException e) {
				//nix
			}
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return tupel;
	}
	
	public Roxel readRoxelByPosition(int posX, int posY)  {
		Roxel rox = null;
		SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? AND y = ?");
		query = query.setParameter(1, posX).setParameter(2, posY);
		
		if( space != null ) {
			rox = space.readIfExists(query);			
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return rox;
	}
	
	/*
	public <T> ArrayList<T> readManyFromSpace(T template) throws RuntimeException {
		T[] temp = null; 
		
		if( space != null ) {
			try {
				temp = space.readMultiple(template);
			} catch (DataAccessException e) {
				//nix
			}
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return new ArrayList<T>(Arrays.asList(temp));
	}*/
	
	public TrafficLight[] getAllTrafficLights() throws RuntimeException{
		TrafficLight[] temp = null;
		SQLQuery<TrafficLight> query = new SQLQuery<TrafficLight>(TrafficLight.class, "status >= ?");
		query.setParameter(1, TrafficStatus.GREEN);
		
		if( space != null ) {
			try {
				temp = space.readMultiple(query);
			} catch (DataAccessException e) {
				//nix
			}
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return temp;
	}
	
	public Roxel[] getAllOccupiedRoxels() throws RuntimeException{
		Roxel[] temp = null; 
		SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "occupingCar > 0");
		
		
		if( space != null ) {
			try {
				temp = space.readMultiple(query);
			} catch (DataAccessException e) {
				//nix
			}
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return temp;
	}
	
	public TrafficLight readTrafficLight(TrafficLight template) {
		
		TrafficLight light = null;
		
		if( space != null ) {
			light = space.readIfExists(template);			
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return light;
		
	}
	
	public TrafficLight takeTrafficLight(TrafficLight template) {
		
		TrafficLight light = null;
		
		if( space != null ) {
			light = space.takeIfExists(template);			
		} else {
			throw new IllegalStateException("TupelSpaceAdapter not initialiazed");
		}
		
		return light;
		
	}
	
	
	}
	
	
