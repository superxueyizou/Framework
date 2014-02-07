package modeling.uas;

import java.util.LinkedList;

import modeling.SAAModel;
import modeling.env.CircleObstacle;
import modeling.env.Constants;
import modeling.env.Destination;
import modeling.env.Waypoint;
import modeling.saa.collsionavoidance.CollisionAvoidanceAlgorithm;
import modeling.saa.selfseparation.SelfSeparationAlgorithm;
import modeling.saa.sense.Sensor;
import sim.engine.*;
import sim.portrayal.Oriented2D;
import sim.util.*;
import tools.CONFIGURATION;

/**
 *
 * @author Robert Lee
 */
public class UAS extends CircleObstacle implements Oriented2D
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//parameters for subsystems	
	private Sensor sensor;
	private SelfSeparationAlgorithm ssa;
	private CollisionAvoidanceAlgorithm caa;
	
	//parameters for UAS movement
	private UASVelocity oldUASVelocity;	
	private Double2D oldLocation;
	
	private UASVelocity uasVelocity;	

	private UASPerformance uasPerformance;//the set performance for the uas;
	
	//parameters for UAS's sensing capability. They are the result of the sensor subsystem.
	private SenseParas senseParas;
	
	//parameters for UAS's avoiding capability. 
	private AvoidParas avoidParas;	

	//parameters for navigation
	private Double2D source;
	private Destination destination;
	private Waypoint nextWp;
	private LinkedList<Waypoint> wpQueue;//for auto-pilot
	private Waypoint ssaWp = null;//for self-separation
	private Waypoint caaWp = null;//for collision avoidance
	
	
	//parameters for recording information about simulation
	private double tempDistanceToDanger = Double.MAX_VALUE; //records the closest distance to danger in each step
	private double distanceToDanger = Double.MAX_VALUE; //records the closest distance to danger experienced by the uas
	
	private double tempOscillation = 0; //records the oscillation in each step
	private double Oscillation = 0; //records the oscillation in a simulation
	
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
		this.uasVelocity = uasVelocity; 
		this.senseParas = senseParas;
		this.avoidParas = avoidParas;
		
		this.oldUASVelocity= uasVelocity;
		this.oldLocation= location;
		
		nextWp=null;
		wpQueue=new LinkedList<Waypoint>();
		wpQueue.offer(destination);
		
		this.isActive=true;

	}
	
	public void init(Sensor sensor, SelfSeparationAlgorithm ssa, CollisionAvoidanceAlgorithm caa)
	{
		this.sensor=sensor;
		this.ssa = ssa;
		this.caa = caa;
		
	}
	

	@Override
	public void step(SimState simState)
	{
		state = (SAAModel) simState;
		
		if(this.isActive == true)
		{			
			if (CONFIGURATION.collisionAvoidanceEnabler)
			{
				caaWp = caa.execute();
				
			}
			if (CONFIGURATION.selfSeparationEnabler)
			{
				ssaWp = ssa.execute();
				
			}
			
			
			if (caaWp != null)
			{
				nextWp=caaWp;
				state.environment.setObjectLocation(nextWp, nextWp.getLocation());
				oldUASVelocity = uasVelocity;
				uasVelocity= new UASVelocity(nextWp.getLocation().subtract(location));
				
				this.setOldLocation(this.location);
				this.setLocation(nextWp.getLocation());
				state.environment.setObjectLocation(this, this.location);
//				System.out.println("nextWp == caaWp" );	
			}
			else if(ssaWp != null)
			{
				nextWp=ssaWp;
				state.environment.setObjectLocation(nextWp, nextWp.getLocation());
				oldUASVelocity = uasVelocity;
				uasVelocity= new UASVelocity(nextWp.getLocation().subtract(location));
				
				this.setOldLocation(this.location);
				this.setLocation(nextWp.getLocation());
				state.environment.setObjectLocation(this, this.location);
//				System.out.println("nextWp == ssaWp" );	
			}
			else if(wpQueue.size() != 0)
			{
				nextWp=(Waypoint)wpQueue.peekFirst();
				oldUASVelocity = uasVelocity;
				Double2D candVel = nextWp.getLocation().subtract(location).normalize().multiply(CONFIGURATION.selfSpeed);
				
//				double angle = this.getVelocity().masonRotateAngleToDouble2D(candVel);
//				
//				if(Math.abs(angle) > Math.toRadians(2.5))
//				{
//					candVel = candVel.masonRotate(Math.toRadians(2.5)*angle/Math.abs(angle));
//				}
//				else
//				{
//					candVel = candVel.masonRotate(angle);
//				}
							
				Waypoint wp= new Waypoint(state.getNewID(), this.getDestination());
				wp.setLocation(location.add(candVel));
				wp.setAction(4);
				state.environment.setObjectLocation(wp,wp.getLocation());
				
				uasVelocity = new UASVelocity(candVel);
				this.setOldLocation(this.location);
				this.setLocation(wp.getLocation());
				state.environment.setObjectLocation(this, this.location);

				if (this.location.distance(nextWp.getLocation())<3*CONFIGURATION.selfSafetyRadius)
				{
					wpQueue.poll();
				}
			}
			else
			{
				System.out.println("approaching the destination (impossible)!");
			}
		
//			System.out.println("old location: "+this.oldLocation + "  old Velocity: "+ this.getOldVelocity());
//			System.out.println("new location: "+this.location+ "  new Velocity: "+ this.getVelocity());
//			System.out.println((this.getOldLocation()== this.getLocation())+" "+(this.getOldVelocity() == this.getVelocity()));
			
			if (this.location.distance(destination.getLocation())<3*CONFIGURATION.selfSafetyRadius)
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
	
	public SelfSeparationAlgorithm getSsa() {
		return ssa;
	}

	public void setSsa(SelfSeparationAlgorithm ssa) {
		this.ssa = ssa;
	}

	public CollisionAvoidanceAlgorithm getCaa() {
		return caa;
	}

	public void setCaa(CollisionAvoidanceAlgorithm aa) {
		this.caa = aa;
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

	public Double2D getSource() {
		return source;
	}

	public void setSource(Double2D source) {
		this.source = source;
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

	public Double2D getOldVelocity() {
		return oldUASVelocity.getVelocity();
	}
	
	public Double2D getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(Double2D oldLocation) {
		this.oldLocation = oldLocation;
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
	
	public double getTempDistanceToDanger() {
		return tempDistanceToDanger;
	}

	public void setTempDistanceToDanger(double tempDistanceToDanger) {
		this.tempDistanceToDanger = tempDistanceToDanger;
	}

	public double getDistanceToDanger() {
		return distanceToDanger;
	}

	public void setDistanceToDanger(double distanceToDanger) {
		this.distanceToDanger = distanceToDanger;
	}
	
	public double getTempOscillation() {
		return tempOscillation;
	}

	public void setTempOscillation(double tempOscillation) {
		this.tempOscillation = tempOscillation;
	}

	public double getOscillation() {
		return Oscillation;
	}

	public void setOscillation(double oscillation) {
		Oscillation = oscillation;
	}

	public LinkedList<Waypoint> getWpQueue() {
		return wpQueue;
	}

	public void setWpQueue(LinkedList<Waypoint> wpQueue) {
		this.wpQueue = wpQueue;
	}

	
	public SAAModel getState() {
		return state;
	}

	public void setState(SAAModel state) {
		this.state = state;
	}
	
	/*****************************************
	 * overide method extended from Obstacle
	 */
	public boolean pointInShape(Double2D coord)
	{
		if (location.distance(coord) <= safetyRadius)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	
	/*************
	 * method of sim.portrayal.Oriented2D, for drawing orientation marker
	 */
	public double orientation2D()
	{
		return this.uasVelocity.getVelocity().angle(); // this is different from the angle definition in other parts. reverse the sign.
	}

}
