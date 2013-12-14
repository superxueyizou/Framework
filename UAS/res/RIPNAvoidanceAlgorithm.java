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
import tools.MathVector;

/**
 * @author Xueyi
 *
 */
public class RIPNAvoidanceAlgorithm extends AvoidanceAlgorithm{

	/**
	 * 
	 */
	private SAAModel state; 
	private UAS hostUAS;
	
	
	private double bearing;
	private Destination destination;
	private Double2D destinationCoor;
	private double speed;
	private double sensitivityForCollisions;
	private double viewingRange;
	private double viewingAngle;
	private Double2D hostUASCorr;
	private UASPerformance performance;
	private double distanceToDanger; //records the closest distance to danger experienced by the UAS
	private Waypoint waypoint;
	
	private static final double MPS_SPEED = 1.5 ;
	private static final double LAMBDA = 0.1 ;
	private static final double DESIRED_SEPARATION = 2.5*MPS_SPEED;
	private static final double MINIMUM_TURNING_RADIUS = 17.2 ;
	

	public RIPNAvoidanceAlgorithm(SimState simstate, UAS uas) {
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
		
		Bag obstacles = state.obstacles;
		Bag uASBag = state.getUasBag();
		
		hostUASCorr = hostUAS.getLocation();
		speed = hostUAS.getSpeed();
		bearing = hostUAS.getBearing();
		
		viewingRange = hostUAS.getViewingRange();
		viewingAngle = hostUAS.getViewingAngle();
		performance = hostUAS.getUasPerformance();
		
		/* Find plane to avoid*/
		ThreatContainer greatestThreatContainer =findGreatestThreat(hostUAS, uASBag);
		UAS greatestThreat = greatestThreatContainer.getThreat();
		double threatZEM = greatestThreatContainer.getzEM();
		double timeToGo = greatestThreatContainer.getTimeToGo();

		if (greatestThreat != null) 
		{
			/* If there is a plane to avoid, then figure out which direction it should turn*/
			boolean turnRight = shouldTurnRight(hostUAS, greatestThreat);
			/* Calculate turning radius to avoid collision*/
			double turningRadius = calculateTurningRadius(threatZEM);
			
			double turningAngle = Math.toDegrees(hostUAS.getSpeed()/turningRadius);

			/* Given turning angle and orientation of the UAS, calculate next collision avoidance waypoint*/
			Waypoint wp = CALCULATION.calculateWaypoint(hostUAS, turningAngle, turnRight);

			if (findGreatestThreat(greatestThreat, obstacles).getThreat() == hostUAS) 
			{
			
				System.out.println("Planes"+ hostUAS.getID() + " and" +  greatestThreat.getID() +" are each other's greatest threats");
			}
		
			return wp;
			
		}
	
		return CALCULATION.takeDubinsPath(hostUAS);
		

	}
	
	/****************************************************************************************************************************************/

		
	/* Function that returns the most dangerous neighboring UAS and its ZEM and timeToGo */
	public ThreatContainer findGreatestThreat(UAS self, Bag uASBag)
	{
		UAS greatestThreat =null;
		double mostDangerousZEM = -1;		
		double minimumTimeToGo = Double.MAX_VALUE; // Set the preliminary time-to-go to infinity
		
		/* Make a position vector representation of the current UAS*/
		double selfMagnitude = new Double2D(0,0).distance(self.getLocation());
		double selfDirection = CALCULATION.calculateAngle(new Double2D(0,0), self.getLocation());
		MathVector p1 = new MathVector(selfMagnitude,selfDirection);
		MathVector d1= new MathVector(1.0,self.getBearing());/* Make a heading vector representation of the current plane*/
		
		/* Declare variables needed for this loop*/
		MathVector pDiff;
		MathVector dDiff;
		double timeToGo, zEM, distanceBetween;		
		double V = self.getSpeed();
		
		for(int i = 0; i < uASBag.size(); i++)
  		{
			UAS intruder= (UAS)uASBag.get(i);
		    if(intruder.equals(self))
			{
				//System.out.println(obstacles.get(i)+"this obstacle is myself, don't mind!");
				continue;
			}
    		if(!intruder.isActive)
			{
				//System.out.println(obstacles.get(i)+"this obstacle is dead, don't mind!");
				continue;
			}
						
			/* If it's not in the Check Zone, check the other plane*/
			distanceBetween = self.getLocation().distance(intruder.getLocation());
			
			if (distanceBetween > self.getViewingRange()) continue;

			/* Making a position vector representation of intruder*/						
			double magnitude2 = new Double2D(0,0).distance(intruder.getLocation());
			double direction2 = CALCULATION.calculateAngle(new Double2D(0,0), intruder.getLocation());
			MathVector p2 = new MathVector(magnitude2,direction2);			
			MathVector d2= new MathVector(1.0, intruder.getBearing());/* Make a heading vector representation of the current plane*/
			
			pDiff = p1.minus(p2);
			dDiff = d1.minus(d2);
			/* Compute Time To Go*/			
			timeToGo = -1*pDiff.dotProduct(dDiff)/(V*dDiff.dotProduct(dDiff));
			/* Compute ZEM*/
			zEM = Math.sqrt(pDiff.dotProduct(pDiff) + 
				2*(V*timeToGo)* pDiff.dotProduct(dDiff) + 
				Math.pow(V*timeToGo,2)*dDiff.dotProduct(dDiff));
			
			/* If the ZEM is less than the minimum required separation, and the timeToGo is the least so far, then avoid this intruder*/
			if((zEM <= DESIRED_SEPARATION) && (timeToGo < minimumTimeToGo) && (timeToGo > 0))
			{
				greatestThreat = intruder;
				mostDangerousZEM = zEM;
				minimumTimeToGo = timeToGo;			
			}
		}
				
		ThreatContainer greatestThreatContainer= new ThreatContainer(greatestThreat,mostDangerousZEM,minimumTimeToGo);
		return greatestThreatContainer;
	}


	/* Returns true if the original UAS (self) should turn right to avoid intruder, false if otherwise. Takes original UAS and its greatest threat as parameters */
	public boolean shouldTurnRight(UAS self, UAS intruder) 
	{		
		/* For checking whether the  UAS (self) should turn right or left */
		double theta_1, theta_2, theta, theta_dot, LOS;
		double selfBearing = self.getBearing();
		double intruderBearing = intruder.getBearing();
		double V = self.getSpeed();

		theta = CALCULATION.calculateAngle(self.getLocation(), intruder.getLocation());
		LOS = self.getLocation().distance(intruder.getLocation());
		
		
		theta_1 = CALCULATION.correctAngle(selfBearing - theta);
		theta_2 = CALCULATION.correctAngle(180-intruderBearing + theta);
		
		double vsin01 = V*Math.sin(Math.toRadians(theta_1));
		double vsin02 = V*Math.sin(Math.toRadians(theta_2));
		
		theta_dot = (vsin02-vsin01)/ LOS;

		if (theta_dot >= 0) 
		{
			return true;
		}
		else 
		{
			return false;
		}

	}

	/* Calculate the turning radius based on the zero effort miss*/
	public double calculateTurningRadius(double ZEM)
	{
		double l = LAMBDA;
		double ds = DESIRED_SEPARATION;
		return MINIMUM_TURNING_RADIUS*Math.exp(l*ZEM/ds);
	}

}

class ThreatContainer
{
	private UAS threat;
	private double zEM;
	private double timeToGo;
	public ThreatContainer(UAS threat, double zEM, double timeToGo)
	{
		this.threat=threat;
		this.zEM=zEM;
		this.timeToGo=timeToGo;
		
	}
	public UAS getThreat() {
		return threat;
	}
	public void setThreat(UAS threat) {
		this.threat = threat;
	}
	public double getzEM() {
		return zEM;
	}
	public void setzEM(double zEM) {
		this.zEM = zEM;
	}
	public double getTimeToGo() {
		return timeToGo;
	}
	public void setTimeToGo(double timeToGo) {
		this.timeToGo = timeToGo;
	}

}
