/**
 * 
 */
package modeling.subsystems.avoidance;

import modeling.SAAModel;
import modeling.Destination;
import modeling.UAS;
import modeling.Waypoint;
import sim.engine.SimState;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

/**
 * @author Xueyi
 *
 */
public class AvoidanceAlgorithmAdapter extends AvoidanceAlgorithm
{

	/**
	 * 
	 */
	private SAAModel state; 
	private UAS hostUAS;

	private Destination destination;
	Double2D destinationCoor;
	
	public AvoidanceAlgorithmAdapter(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();
	}
	
	public Waypoint execute()
	{
		MutableDouble2D sumForces = new MutableDouble2D(); //used to record the changes to be made to the location of the uas
		sumForces.addIn(hostUAS.getLocation());
		double moveX = hostUAS.getVelocity().x; //CALCULATION.xMovement(bearing, speed);
		double moveY = hostUAS.getVelocity().y; //CALCULATION.yMovement(bearing, speed);
		sumForces.addIn(new Double2D(moveX, moveY));
		Double2D newLocation= new Double2D(sumForces);
		
		Waypoint wp = new Waypoint(state.getNewID(), hostUAS.getDestination());
		wp.setLocation(newLocation);
		return wp;
		
	}
	
	public void init()
	{
		
	}
	

}
