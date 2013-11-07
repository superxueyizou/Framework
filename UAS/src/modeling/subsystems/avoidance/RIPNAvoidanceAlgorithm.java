/**
 * 
 */
package modeling.subsystems.avoidance;

import modeling.CALCULATION;
import modeling.MathVector;
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
public class RIPNAvoidanceAlgorithm extends AvoidanceAlgorithm{

	/**
	 * 
	 */
	private COModel state; 
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
		state = (COModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		waypoint = (Waypoint) destination;
		destinationCoor = destination.getLocation();
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
		performance = hostUAS.getPerformance();
		
		
		System.out.println("---------------RIPNAvoidanceAlgorithm--------------------------------------");
		/* Find plane to avoid*/
		ThreatContainer greatestThreatContainer =findGreatestThreat(hostUAS, uASBag);
		UAS greatestThreat = greatestThreatContainer.getThreat();
		double threatZEM = greatestThreatContainer.getzEM();
		double timeToGo = greatestThreatContainer.getTimeToGo();

		if (greatestThreat != null) 
		{
			/* If there is a plane to avoid, then figure out which direction it 
			should turn*/
			boolean turnRight = shouldTurnRight(hostUAS, greatestThreat);
			/* Calculate turning radius to avoid collision*/
			double turningRadius = calculateTurningRadius(threatZEM);
			
			double turningAngle = Math.toDegrees(hostUAS.getSpeed()/turningRadius);

			/* Given turning radius and orientation of the plane, calculate 
			next collision avoidance waypoint*/
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
		/* Make a position vector representation of the current plane*/
		double selfMagnitude = new Double2D(0,0).distance(self.getLocation());
		double selfDirection = CALCULATION.calculateAngle(new Double2D(0,0), self.getLocation());
		MathVector p1 = new MathVector(selfMagnitude,selfDirection);
		/* Make a heading vector representation of the current plane*/
		MathVector d1= new MathVector(1.0,self.getBearing());
		
		/* Declare variables needed for this loop*/
		MathVector pDiff;
		MathVector dDiff;
		double timeToGo, zEM, distanceBetween, timeToDest;
		double mostDangerousZEM = -1;		
		double minimumTimeToGo = Double.MAX_VALUE; // Set the preliminary time-to-go to infinity
		double V = MPS_SPEED;
		
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

//			else if (distanceBetween < MPS_SPEED)
//			{
//				greatestThreat=intruder;
//				mostDangerousZEM = 0;
//				minimumTimeToGo = 0.1;
//				break;
//			}	

			/* Making a position vector representation of plane2*/						
			double magnitude2 = new Double2D(0,0).distance(intruder.getLocation());
			double direction2 = CALCULATION.calculateAngle(new Double2D(0,0), intruder.getLocation());
			MathVector p2 = new MathVector(magnitude2,direction2);
			/* Make a heading vector representation of the current plane*/
			MathVector d2= new MathVector(1.0, intruder.getBearing());
			
			pDiff = p1.minus(p2);
			dDiff = d1.minus(d2);
			/* Compute Time To Go*/			
			timeToGo = -1*pDiff.dotProduct(dDiff)/(V*dDiff.dotProduct(dDiff));

			/* Compute ZEM*/
			zEM = Math.sqrt(pDiff.dotProduct(pDiff) + 
				2*(V*timeToGo)* pDiff.dotProduct(dDiff) + 
				Math.pow(V*timeToGo,2)*dDiff.dotProduct(dDiff));
			
			/* If the Zero Effort Miss is less than the minimum required 
			separation, and the time to go is the least so far, then avoid this plane*/
			if((zEM <= DESIRED_SEPARATION) && (timeToGo < minimumTimeToGo) && (timeToGo > 0))
			{
				// If the plane is behind you, don't avoid it
//				if ( Math.abs(intruder.findAngle(plane1)*180/PI - toCartesian(plane1.getCurrentBearing())) > 35.0)
//				{
//					
//				}
//				
//				timeToDest = self.getLocation().distance(self.getDestination().getLocation());
//				/* If you're close to your destination and the other plane isn't much of a threat, then don't avoid it */ 
//				if ( timeToDest < 5.0 && zEM > 3.0*MPS_SPEED ) continue;

				greatestThreat = intruder;
				mostDangerousZEM = zEM;
				minimumTimeToGo = timeToGo;			
			}
		}
		
		
		ThreatContainer greatestThreatContainer= new ThreatContainer(greatestThreat,mostDangerousZEM,minimumTimeToGo);

		return greatestThreatContainer;
	}


	/* Returns true if the original plane (plane1) should turn right to avoid plane2, 
	false if otherwise. Takes original plane and its greatest threat as parameters */
	public boolean shouldTurnRight(UAS self, UAS intruder) 
	{
		
		/* For checking whether the plane should turn right or left */
		double theta_1, theta_2, theta, theta_dot, LOS;
		double selfBearing = self.getBearing();
		double intruderBearing = intruder.getBearing();
		double V = MPS_SPEED;
		
		/* Calculate theta, theta1, and theta2. Theta is the cartesian angle
		from 0 degrees (due East) to plane2 (using plane1 as the origin). This 
		may be referred to as the LOS angle. */
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
