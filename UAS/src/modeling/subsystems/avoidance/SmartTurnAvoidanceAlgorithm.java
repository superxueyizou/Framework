/**
 * 
 */
package modeling.subsystems.avoidance;

import modeling.SAAModel;
import modeling.Constants;
import modeling.Destination;
import modeling.Obstacle;
import modeling.UAS;
import modeling.UASPerformance;
import modeling.Waypoint;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;
import tools.CALCULATION;

/**
 * @author Xueyi
 *
 */
public class SmartTurnAvoidanceAlgorithm extends AvoidanceAlgorithm{

	/**
	 * 
	 */
	private SAAModel state; 
	private UAS hostUAS;
	
	
	private double bearing;
	private Destination destination;
	Double2D destinationCoor;
	private double speed;
	private double sensitivityForCollisions;
	private double viewingRange;
	private double viewingAngle;
	private Double2D hostUASCorr;
	private UASPerformance performance;
	private double distanceToDanger; //records the closest distance to danger experienced by the UAS
//	private Waypoint waypoint;
	
	private int xTimes= 0;
	
	public SmartTurnAvoidanceAlgorithm(SimState simstate, UAS uas) {
		// TODO Auto-generated constructor stub
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
//		waypoint = (Waypoint) destination;
		destinationCoor = destination.getLocation();
	}
	
	public void init()
	{
		
	}
	/******************************************************************************************************************************************/
	public Waypoint execute()
	{
		
		Bag obstacles = state.obstacles;
		
		hostUASCorr = hostUAS.getLocation();
		speed = hostUAS.getSpeed();
		bearing = hostUAS.getBearing();
		
		viewingRange = hostUAS.getViewingRange();
		viewingAngle = hostUAS.getViewingAngle();
		performance = hostUAS.getPerformance();
		
		
		double delta = 0.0;					
		do
		{
			for(int i = 0; i < obstacles.size(); i++)
	  		{
				Obstacle obstacle= (Obstacle)obstacles.get(i);
			    if(obstacle.equals(hostUAS))
				{
					//System.out.println(obstacles.get(i)+"this obstacle is myself, don't mind!");
					continue;
				}
	    		if(!((UAS)obstacle).isActive)
				{
					//System.out.println(obstacles.get(i)+"this obstacle is dead, don't mind!");
					continue;
				}
					  	    	
				if(CALCULATION.onCourse(hostUAS, CALCULATION.correctAngle(bearing+delta), obstacle))
				{
					//System.out.println("turning right of SmartTurnAvoidanceAlgorithm has beed executed!");
					return CALCULATION.calculateWaypoint(hostUAS, performance.getCurrentMaxTurning(), true); //turn right
					
					
				}
				else if(CALCULATION.onCourse(hostUAS, CALCULATION.correctAngle(bearing-delta), obstacle))
				{
					//System.out.println("turning left of SmartTurnAvoidanceAlgorithm has beed executed!");
					return CALCULATION.calculateWaypoint(hostUAS, performance.getCurrentMaxTurning(), false); //turn left
									
				}
	  		}
			delta += 3.0;
		} while(delta <= 0.5*hostUAS.getViewingAngle());
		
		return CALCULATION.takeDubinsPath(hostUAS);
		
	}
	
}
