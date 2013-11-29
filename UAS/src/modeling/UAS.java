package modeling;
//MASON imports
import java.util.LinkedList;

import modeling.subsystems.avoidance.AvoidanceAlgorithm;
import modeling.subsystems.sensor.Sensor;
import sim.engine.*;
import sim.field.continuous.*;
import sim.portrayal.Oriented2D;
import sim.util.*;
import tools.CALCULATION;
import tools.CONFIGURATION;
import ui.SAAConfigurator;

/**
 *
 * @author Robert Lee
 */
public class UAS extends CircleObstacle implements Oriented2D
{
	//parameters for subsystems
	private AvoidanceAlgorithm aa;
	public AvoidanceAlgorithm getAa() {
		return aa;
	}

	public void setAa(AvoidanceAlgorithm aa) {
		this.aa = aa;
	}

	private Sensor sensor;
	
	//parameters for UAS movement
	private double bearing = 0; //will be a value between -180(inc) and 180(exc)
	private double speed=1.5; //the speed the vehicle is travelling at = 150/100
	private UASPerformance performance;//the set performance for the uas;
	
	//parameters for UAS's sensing capability. They are the result of the sensor subsystem.
	private double viewingRange=38.89; //how many units in front of the uas it can see obstacles 3889/100
	private double viewingAngle=60; //220 this is the angle for the viewing in front of the uas, viewingAngle / 2 in both directions from right in front of the uas
	private double sensitivityForCollisions=0.5; //this is used to see if the uas will collide with obstacles on it's current heading
	private double safetyRadius = 1.667;
	


	//parameters for navigation
	private Destination destination;
	private Waypoint nextWp;
	private LinkedList<Waypoint> wpQueue;
	private LinkedList<Waypoint> wpQueueP;
	
	//parameters for recording information about simulation
	private double distanceToDanger =110;// Double.MAX_VALUE; //records the closest distance to danger experienced by the uas
	
	public boolean isActive= true;
	

	private COModel state;	




	public UAS(int idNo, Destination destination, UASPerformance uasPerformance, double safetyRadius)
	{
		super(idNo,safetyRadius, Constants.EntityType.TUAS);
		this.destination = destination;
		this.performance = uasPerformance;
		this.safetyRadius= safetyRadius;
		nextWp=null;
		wpQueue=new LinkedList<Waypoint>();
		wpQueue.offer(destination);
		wpQueueP=new LinkedList<Waypoint>();

	}
	
	public void init(Sensor sensor, AvoidanceAlgorithm aa)
	{
		this.sensor=sensor;
		this.aa = aa;
	}
	

	@Override
	public void step(SimState simState)
	{
		if(this.isActive == true)
		{
			state = (COModel) simState;
			
			if (CONFIGURATION.avoidanceAlgorithmEnabler)
			{
				Waypoint wp = aa.execute();
				if(wp != null)
				{
					this.getWpQueueP().offer(wp);
					state.environment.setObjectLocation(wp, wp.getLocation());	
					
				}
				
				
			}
		
						
			{			
				nextWp= this.getNextWp();
				if (nextWp != null)
				{
					setDirection(this.location, nextWp.getLocation());
					//System.out.println("UAS (" + this.toString()+")'s bearing is: "+bearing + ", it's next waypoint is: (" +nextWp.location.x + ","+ nextWp.location.y + ")     wpQueueP elements:" + wpQueueP.size() +"     wpQueue elements:" + wpQueue.size()) ;
					
				}
				else
				{
					System.out.println("approaching the destination!");
				}
				
				
				MutableDouble2D sumForces = new MutableDouble2D(); //used to record the changes to be made to the location of the uas
				sumForces.addIn(this.location);
				double moveX = CALCULATION.xMovement(bearing, speed);
				double moveY = CALCULATION.yMovement(bearing, speed);
				sumForces.addIn(new Double2D(moveX, moveY));
				//System.out.println(sumForces.x + "ddd" + sumForces.y);
		        
				state.environment.setObjectLocation(this, new Double2D(sumForces));
				this.setLocation( new Double2D(sumForces));
				proximityToDanger(new Double2D(sumForces), state.obstacles);

//				state.environment.setObjectLocation(this, nextWp.getLocation());
//				this.setLocation( nextWp.getLocation());
//				proximityToDanger(nextWp.getLocation(), state.obstacles);
				
				if (this.location.distance(nextWp.location)<1)
				{
					if(wpQueueP.size() != 0)
					{
						wpQueueP.poll();
					}
					else
					{
						wpQueue.poll();
					}
					//System.out.println("delete waypoint: ("+ nextWp.location.x + ","+ nextWp.location.y + ")!");
				}
				
				if (this.location.distance(destination.location)<1)
				{
					this.isActive = false;
					//System.out.println("arrived at the destination!");

				}
				
			}
			
		}
		
		
		if(state!=null)
		{
			state.dealWithTermination();
		}
		
		//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
	
	
	//**************************************************************************

	public UASPerformance getStats() {
		return performance;
	}


	public void setStats(UASPerformance stats) {
		this.performance = stats;
	}


	public Destination getDestination() {
		return destination;
	}


	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public double getViewingRange() {
		return viewingRange;
	}

	public void setViewingRange(double viewingRange) {
		this.viewingRange = viewingRange;
	}
	
	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getSafetyRadius() {
		return safetyRadius;
	}

	public void setSafetyRadius(double safetyRadius) {
		this.safetyRadius = safetyRadius;
		this.radius=safetyRadius;
	}

	public UASPerformance getPerformance() {
		return performance;
	}

	public void setPerformance(UASPerformance performance) {
		this.performance = performance;
	}
	
	public double getViewingAngle() {
		return viewingAngle;
	}

	public void setViewingAngle(double viewingAngle) {
		this.viewingAngle = viewingAngle;
	}

	public double getSensitivityForCollisions() {
		return sensitivityForCollisions;
	}

	public double getDistanceToDanger() {
		return distanceToDanger;
	}

	public void setDistanceToDanger(double distanceToDanger) {
		this.distanceToDanger = distanceToDanger;
	}
	
	public LinkedList<Waypoint> getWpQueue() {
		return wpQueue;
	}

	public void setWpQueue(LinkedList<Waypoint> wpQueue) {
		this.wpQueue = wpQueue;
	}

	
	public LinkedList<Waypoint> getWpQueueP() {
		return wpQueueP;
	}

	public void setWpQueueP(LinkedList<Waypoint> wpQueueP) {
		this.wpQueueP = wpQueueP;
	}
	
	
	public COModel getState() {
		return state;
	}

	public void setState(COModel state) {
		this.state = state;
	}
	
//==================================================navigation methods======================================================
	
	public Waypoint getNextWp()
	{
		
		if (wpQueueP.size() != 0)
		{
			return (Waypoint)wpQueueP.peekFirst();
		}
		else if(wpQueue.size() != 0)
		{
			return (Waypoint)wpQueue.peekFirst();
		}
		else
		{
			return null;
		}
		
	}
	

	/**
	 * A method which moves the UAS to the direction of the waypoint.
	 * 
	 * @param loc the location of the UAS
	 * @param targ the waypoint location for the UAS
	 */
	public void setDirection(Double2D loc, Double2D targ)
	{
			double idealDirection = CALCULATION.calculateAngle(loc, targ);
			
			//first the ideal bearing for the UAS to get to it's target must be calculated
			//now based on the ideal bearing for the UAS to get to it's position it
			//must be determined if the UAS needs to be changed from the bearing it's
			//on at all
			if (idealDirection != bearing)
			{
				//then the course that the UAS is on needs correcting
				//check if it would be quicker to turn left or right
				double delta = idealDirection - bearing;
				if(delta>0)
				{
					if(delta <= 180)
					{
						turnLeft(delta);
					}

					else if (delta >180 )
					{
						turnRight(360 - delta);
					}
					
				}
				else
				{
					if (delta >= -180)
					{
						turnRight(-delta);
					}
					else
					{
						turnLeft(360+delta);
					}
				}
				
			}		
	}


	/**
	 * A method which turns the UAS to the left towards a given bearing.
	 * 
	 * @param bearing the bearing the UAS is turning onto
	 */
	private void turnLeft(double theta)
	{
		double direction =bearing;
		if(theta <= performance.getCurrentMaxTurning())
		{
			direction += theta;
		}
		else
		{
			direction += performance.getCurrentMaxTurning();
		}
		bearing = CALCULATION.correctAngle(direction);
		
	}
	
	
	/**
	 * A method which turns the UAS to the right towards a given bearing.
	 * 
	 * @param bearing the bearing the UAS is turning onto
	 */
	
	private void turnRight(double theta)
	{
		double direction =bearing;
		if(theta <= this.performance.getCurrentMaxTurning())
		{
			direction -= theta;
		}
		else
		{
			direction -= this.performance.getCurrentMaxTurning();
		}
		
		bearing = CALCULATION.correctAngle(direction);
		
	}
	
	

	/**
	 * A method which measures how far away the closest obstacle to the UAS is
	 * 
	 * @param obstacles 
	 */
	private void proximityToDanger( Double2D coord, Bag obstacles)
	{
		double tempproximityToDanger;
		
		for (int i = 0; i < obstacles.size(); i++)
		{
			Obstacle obstacle = (Obstacle) obstacles.get(i);
		    if(obstacle.equals(this))
			{
				//System.out.println(obstacles.get(i)+"this obstacle is myself, don't mind!");
				continue;
			}
    		if(!((UAS)obstacle).isActive)
			{
				//System.out.println(obstacles.get(i)+"this obstacle is dead, don't mind!");
				continue;
			}
			tempproximityToDanger = obstacle.obstacleToPoint(coord);
			if (tempproximityToDanger < distanceToDanger)
			{
				distanceToDanger = tempproximityToDanger;
				
			}
		}
		
		//System.out.println(distanceToDanger);
	}
/*****************************************
 * overide method extended from Obstacle
 */
	public boolean inShape(Double2D coord)
	{
		if (location.distance(coord) <= radius)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
//	/**
//	 * Returns the distance from the provided coordinate to the closest part of
//	 * the obstacle to that point.
//	 * 
//	 * @param coord
//	 * @return 
//	 */
//	@Override
//	public double obstacleToPoint(Double2D coord)
//	{
//		//as the shape of this obstacle is a circle the closest point is the edge
//		//of the circle to the point.
//		double val = this.location.distance(coord);
//		
//		if (val < 0)
//		{
//			return 0;
//		} 
//		else 
//		{
//			return val;
//		}
//	}
	
	
	/*************
	 * method of sim.portrayal.Oriented2D, for drawing orientation marker
	 */
	public double orientation2D()
	{
		return Math.toRadians(this.bearing);
	}
}
