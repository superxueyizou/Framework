package modeling;

import java.util.LinkedList;
import modeling.subsystems.avoidance.AvoidanceAlgorithm;
import modeling.subsystems.sensor.Sensor;
import sim.engine.*;
import sim.portrayal.Oriented2D;
import sim.util.*;
import tools.CALCULATION;
import tools.CONFIGURATION;

/**
 *
 * @author Robert Lee
 */
public class UAS extends CircleObstacle implements Oriented2D
{
	//parameters for subsystems
	private AvoidanceAlgorithm aa;
	private Sensor sensor;
	
	//parameters for UAS movement
	private UASVelocity uasVelocity;
	private UASPerformance uasPerformance;//the set performance for the uas;
	
	//parameters for UAS's sensing capability. They are the result of the sensor subsystem.
	private SenseParas senseParas;
	
	//parameters for UAS's avoiding capability. 
	private AvoidParas avoidParas;	

	//parameters for navigation
	private Destination destination;
	private Waypoint nextWp;
	private LinkedList<Waypoint> wpQueue;
	private LinkedList<Waypoint> wpQueueP;
	
	//parameters for recording information about simulation
	private double distanceToDanger = Double.MAX_VALUE; //records the closest distance to danger experienced by the uas
	
	private double safetyRadius;
	public boolean isActive;	

	private SAAModel state;	


	public UAS(int idNo, double safetyRadius, Double2D location, Destination destination, UASVelocity uasVelocity, UASPerformance uasPerformance, SenseParas senseParas, AvoidParas avoidParas)
	{
		super(idNo,safetyRadius, Constants.EntityType.TUAS);
		this.safetyRadius= safetyRadius;
		this.location=location;
		this.destination = destination;
		this.uasPerformance = uasPerformance;
		this.uasVelocity = uasVelocity; //new UASVelocity(new Double2D(0.0,0.0));
		this.senseParas = senseParas;
		this.avoidParas = avoidParas;
		
		nextWp=null;
		wpQueue=new LinkedList<Waypoint>();
		wpQueue.offer(destination);
		wpQueueP=new LinkedList<Waypoint>();
		
		this.isActive=true;

	}
	
	public void init(Sensor sensor, AvoidanceAlgorithm aa)
	{
		this.sensor=sensor;
		this.aa = aa;
	}
	

	@Override
	public void step(SimState simState)
	{
		state = (SAAModel) simState;
		
		if(this.isActive == true)
		{			
			if (CONFIGURATION.avoidanceAlgorithmEnabler)
			{
				Waypoint wp = aa.execute();
				if(wp != null)
				{
					this.getWpQueueP().offer(wp);
					state.environment.setObjectLocation(wp, wp.getLocation());						
				}
			}
								
			nextWp= this.getNextWp();
			if (nextWp != null)
			{
//					System.out.println(bearing);
//					setUASDirection(this.location, nextWp.getLocation());
//					System.out.println("UAS (" + this.toString()+")'s bearing is: "+bearing + ", it's next waypoint is: (" +nextWp.location.x + ","+ nextWp.location.y + ")     wpQueueP elements:" + wpQueueP.size() +"     wpQueue elements:" + wpQueue.size()) ;
				
			}
			else
			{
				System.out.println("approaching the destination!");
			}
			
			
			MutableDouble2D sumForces = new MutableDouble2D(); //used to record the changes to be made to the location of the uas
			sumForces.addIn(this.location);
			double moveX = uasVelocity.getVelocity().x; //CALCULATION.xMovement(bearing, speed);
			double moveY = uasVelocity.getVelocity().y; //CALCULATION.yMovement(bearing, speed);
			sumForces.addIn(new Double2D(moveX, moveY));						        
			state.environment.setObjectLocation(this, new Double2D(sumForces));
			this.setLocation( new Double2D(sumForces));
			proximityToDanger(new Double2D(sumForces), state.obstacles);

//			state.environment.setObjectLocation(this, nextWp.getLocation());
//			this.setLocation( nextWp.getLocation());
//			proximityToDanger(nextWp.getLocation(), state.obstacles);
			
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
		
		if(state!=null)
		{
			state.dealWithTermination();
		}
		
		//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
	
	
	//**************************************************************************

	public AvoidanceAlgorithm getAa() {
		return aa;
	}

	public void setAa(AvoidanceAlgorithm aa) {
		this.aa = aa;
	}

	public UASPerformance getStats() {
		return uasPerformance;
	}


	public void setStats(UASPerformance stats) {
		this.uasPerformance = stats;
	}


	public Destination getDestination() {
		return destination;
	}


	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public double getViewingRange() {
		return this.senseParas.getViewingRange();
	}

	public void setViewingRange(double viewingRange) {
		this.senseParas.setViewingRange(viewingRange);
	}
	
	public double getViewingAngle() {
		return this.senseParas.getViewingAngle();
	}

	public void setViewingAngle(double viewingAngle) {
		this.senseParas.setViewingAngle(viewingAngle);
	}
	
	public double getSensitivityForCollisions() {
		return this.senseParas.getSensitivityForCollisions();
	}
	
	public void setSensitivityForCollisions(double sensitivityForCollisions) {
		this.senseParas.setSensitivityForCollisions(sensitivityForCollisions);
	}

	public double getBearing() {
		return uasVelocity.getBearing();
	}

	public void setBearing(double bearing) {
		this.uasVelocity.setBearing(bearing);
	}
	
	public double getSpeed() {
		return uasVelocity.getSpeed();
	}

	public void setSpeed(double speed) {
		this.uasVelocity.setSpeed(speed);
	}
	
	public Double2D getVelocity() {
		return uasVelocity.getVelocity();
	}

	public void setVelocity(Double2D velocity) {
		this.uasVelocity.setVelocity(velocity);
	}

	public double getSafetyRadius() {
		return safetyRadius;
	}

	public void setSafetyRadius(double safetyRadius) {
		this.safetyRadius = safetyRadius;
		this.radius=safetyRadius;
	}

	public UASPerformance getUasPerformance() {
		return uasPerformance;
	}

	public void setUasPerformance(UASPerformance performance) {
		this.uasPerformance = performance;
	}
	


	public double getAlpha() {
		return this.avoidParas.getAlpha();
	}

	public void setAlpha(double alpha) {
		this.avoidParas.setAlpha(alpha);
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
	
	
	public SAAModel getState() {
		return state;
	}

	public void setState(SAAModel state) {
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
	public void setUASDirection(Double2D loc, Double2D targ)
	{
			double idealDirection = targ.subtract(loc).angle(); // CALCULATION.calculateAngle(loc, targ);
			
			//first the ideal bearing for the UAS to get to it's target must be calculated
			//now based on the ideal bearing for the UAS to get to it's position it
			//must be determined if the UAS needs to be changed from the bearing it's
			//on at all
			if (idealDirection != uasVelocity.getBearing())
			{
				//then the course that the UAS is on needs correcting
				//check if it would be quicker to turn left or right
				double delta = idealDirection - uasVelocity.getBearing();
				if(delta>0)
				{
					if(delta <= Math.PI)
					{
						turnLeft(delta);
					}

					else if (delta >Math.PI )
					{
						turnRight(2.0*Math.PI - delta);
					}
					
				}
				else
				{
					if (delta >= -Math.PI)
					{
						turnRight(-delta);
					}
					else
					{
						turnLeft(2.0*Math.PI+delta);
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
		double direction = uasVelocity.getBearing();
		if(theta <= Math.toRadians(uasPerformance.getCurrentMaxTurning()))
		{
			direction += theta;
		}
		else
		{
			direction += Math.toRadians(uasPerformance.getCurrentMaxTurning());
		}
		uasVelocity.setBearing(CALCULATION.correctAngle(direction));
				
	}
	
	
	/**
	 * A method which turns the UAS to the right towards a given bearing.
	 * 
	 * @param bearing the bearing the UAS is turning onto
	 */
	
	private void turnRight(double theta)
	{
		double direction = uasVelocity.getBearing();
		if(theta <= Math.toRadians(uasPerformance.getCurrentMaxTurning()))
		{
			direction -= theta;
		}
		else
		{
			direction -= Math.toRadians(uasPerformance.getCurrentMaxTurning());
		}
		
		uasVelocity.setBearing(CALCULATION.correctAngle(direction));
		
	}
	
	

	/**
	 * A method which measures how far away the closest obstacle to the UAS is
	 * 
	 * @param obstacles 
	 */
	private void proximityToDanger(Double2D coord, Bag obstacles)
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
		return this.uasVelocity.getBearing();
	}


}
