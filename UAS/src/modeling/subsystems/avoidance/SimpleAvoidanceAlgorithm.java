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
public class SimpleAvoidanceAlgorithm extends AvoidanceAlgorithm{

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
	private Waypoint waypoint;

	
	
	public SimpleAvoidanceAlgorithm(SimState simstate, UAS uas) {
		// TODO Auto-generated constructor stub
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		waypoint = (Waypoint) destination;
		destinationCoor = destination.getLocation();
	}
	
	public void init()
	{
		
	}
	
	/**
	 * @return ****************************************************************************************************************************************/
	public Waypoint execute()
	{
		
		Bag everything = state.allEntities; //this will get all the entities in the simulation
		Bag obstacles = state.obstacles;
		
		hostUASCorr = hostUAS.getLocation();
		speed = hostUAS.getSpeed();
		sensitivityForCollisions = hostUAS.getSensitivityForCollisions();
		viewingRange = hostUAS.getViewingRange();
		viewingAngle = hostUAS.getViewingAngle();
		performance = hostUAS.getPerformance();
		distanceToDanger =hostUAS.getDistanceToDanger();
		bearing = hostUAS.getBearing();
		
			
//     	for(int i = 0; i < everything.size(); i++)
//		{
//			System.out.println(everything.get(i).toString());
//		
//		}
//		
//     	for(int i = 0; i < obstacles.size(); i++)
//		{
//			System.out.println("obstacles:" + obstacles.get(i).toString());
//		
//		}
//		System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");		   
	
		if (CALCULATION.checkCourse(hostUAS, bearing, obstacles)) 
		{
			//only check to see if the UAS is to hit something if it onto it's
			//course to the next waypoint, in the case that it isn't on course then it may turn out of
			//the way of things in it's current path
			System.out.println("alterCourse has beed executed!");
			return alterCourse(hostUAS,obstacles);	
			
		}
		
		return null;
		
		//System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		
		
	}
	
	/****************************************************************************************************************************************/


	/**
	 * Method which adds a Waypoint for the UAS to travel via on it's path to
	 * prevent it from hitting an obstacle in it's way
	 * 
	 * @param self The hostUAS
	 * @param obstacles All of the obstacles in the environment
	 */
	public Waypoint alterCourse(UAS self,Bag obstacles)
	{
		Double2D myLocation = self.getLocation();
		double resolution = 0.1;
		MutableDouble2D coord = new MutableDouble2D(myLocation);
		
		double xComponent = 0;
		double yComponent = 0;
	
		
		for(double i = 0; i < (performance.getCurrentMaxTurning() - 5); i += resolution)
		{
			if (CALCULATION.checkCourse(self, CALCULATION.correctAngle(bearing - i), obstacles) == false)
			{
				//then moving right gives a clear path
				//set wp and return				
				//first must find out where to put wp
				xComponent = CALCULATION.xMovement(CALCULATION.correctAngle(bearing - (i+5)), (viewingRange / 1));
				yComponent = CALCULATION.yMovement(CALCULATION.correctAngle(bearing - (i+5)), (viewingRange / 1));
								
			} 
            else if (CALCULATION.checkCourse(self, CALCULATION.correctAngle(bearing + i), obstacles) == false) 
            {
				//then moving left gives a clear path
				//set wp and return				
				xComponent = CALCULATION.xMovement(CALCULATION.correctAngle(bearing + (i+5)), (viewingRange / 1));
				yComponent = CALCULATION.yMovement(CALCULATION.correctAngle(bearing + (i+5)), (viewingRange / 1));
				
			}
			
			coord.addIn(xComponent, yComponent);
			if (!(waypoint instanceof Destination))
			{
				state.environment.remove(waypoint);
			}
			waypoint = new Waypoint(state.getNewID(), destination);
			waypoint.setLocation(new Double2D(coord));
			return waypoint;
//			hostUAS.getWpQueueP().offer(waypoint);
//			state.environment.setObjectLocation(waypoint, new Double2D(coord));
			
		}
		
		//no path that can be immediately turned onto is clear
		//therefore see if it is possible for the UAS to see a clear path even
		//if it can't be immediately turned onto
		for(double i = (performance.getCurrentMaxTurning()-5); i < (viewingAngle / 2); i += resolution)
		{
			if (CALCULATION.checkCourse(self, CALCULATION.correctAngle(bearing - i), obstacles) == false)
			{
				//then moving right gives a clear path
				//set wp and return
				
				//first must find out where to put wp
				
				xComponent = CALCULATION.xMovement(CALCULATION.correctAngle(bearing - (performance.getCurrentMaxTurning()+5)), (viewingRange / 1));
				yComponent = CALCULATION.yMovement(CALCULATION.correctAngle(bearing - (performance.getCurrentMaxTurning()+5)), (viewingRange / 1));
				coord.addIn(xComponent, yComponent);
				
			} 
			else if (CALCULATION.checkCourse(self, CALCULATION.correctAngle(bearing + i), obstacles) == false) 
			{
				//then moving left gives a clear path
				//set wp and return
				
				xComponent = CALCULATION.xMovement(CALCULATION.correctAngle(bearing + (performance.getCurrentMaxTurning()+5)), (viewingRange / 1));
				yComponent = CALCULATION.yMovement(CALCULATION.correctAngle(bearing + (performance.getCurrentMaxTurning()+5)), (viewingRange / 1));
				
			}
			coord.addIn(xComponent, yComponent);
			if (!(waypoint instanceof Destination))
			{
				state.environment.remove(waypoint);
			}
			waypoint = new Waypoint(state.getNewID(), destination);
			waypoint.setLocation(new Double2D(coord));
			return waypoint;
//			hostUAS.getWpQueueP().offer(waypoint);
//			state.environment.setObjectLocation(waypoint, new Double2D(coord));
			
		}
		
		return null;
	}

		
}
