package praktikum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import java.lang.IllegalStateException;

import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.gsm.GridServiceManager;
import org.openspaces.admin.pu.ProcessingUnit;
import org.openspaces.admin.pu.ProcessingUnitAlreadyDeployedException;
import org.openspaces.admin.space.SpaceDeployment;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.*;
import org.springframework.dao.DataAccessException;

import com.j_spaces.core.IJSpace;

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
	}
	
	
	}
	
	
