/**
 * 
 */
package modeling.subsystems.avoidance;

import modeling.CALCULATION;
import modeling.COModel;
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

/**
 * @author Xueyi
 *
 */
public class TurnRightAvoidanceAlgorithm extends AvoidanceAlgorithm{

	/**
	 * 
	 */
	private COModel state; 
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
	//private Waypoint waypoint;
	
	private int xTimes= 0;
	
	public TurnRightAvoidanceAlgorithm(SimState simstate, UAS uas) {
		// TODO Auto-generated constructor stub
		state = (COModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		//waypoint = (Waypoint) destination;
		destinationCoor = destination.getLocation();
	}
	
	
	/**
	 * @return ****************************************************************************************************************************************/
	public Waypoint execute()
	{
		
		Bag obstacles = state.obstacles;
		
		hostUASCorr = hostUAS.getLocation();
		speed = hostUAS.getSpeed();
		bearing = hostUAS.getBearing();
		
		viewingRange = hostUAS.getViewingRange();
		viewingAngle = hostUAS.getViewingAngle();
		performance = hostUAS.getPerformance();
		
		
		if (CALCULATION.checkCourse(hostUAS, bearing, obstacles)) 
		{
			//only check to see if the UAS is to hit something if it onto it's
			//course to the next waypoint, in the case that it isn't on course then it may turn out of
			//the way of things in it's current path
			if(xTimes<Integer.MAX_VALUE)
			{
				xTimes++;
				System.out.println("alterCourse of TurnRightAvoidanceAlgorithm has beed executed!");
				//return alterCourse(hostUAS);	
				return CALCULATION.calculateWaypoint(hostUAS, performance.getCurrentMaxTurning(), true); //turn right
			
			}
			
		}
		
		return CALCULATION.takeDubinsPath(hostUAS);
			
	}
	
	/****************************************************************************************************************************************/

//
//	/**
//	 * Method which adds a Waypoint for the UAS to travel via on it's path to
//	 * prevent it from hitting an obstacle in it's way
//	 * 
//	 * @param self The hostUAS
//	 */
//	public Waypoint alterCourse(UAS self)
//	{
//		Double2D myLocation = self.getLocation();		
//		MutableDouble2D coord = new MutableDouble2D(myLocation);
//		double turnAngle = performance.getCurrentMaxTurning();
//		double xComponent = speed*Math.cos(Math.toRadians(CALCULATION.correctAngle(bearing-turnAngle)));
//		double yComponent = -1*speed*Math.sin(Math.toRadians(CALCULATION.correctAngle(bearing-turnAngle)));
//		//System.out.println("ccccccccccccccccccccccccccccc"+xComponent + " , "+ yComponent);
//		coord.addIn(xComponent, yComponent);
//			
//		Waypoint wp = new Waypoint(state.getNewID(), destination);
//		wp.setLocation(new Double2D(coord));
//		return wp;
////		self.getWpQueueP().offer(wp);
////		state.environment.setObjectLocation(wp, new Double2D(coord));
//			
//	}
		
}
