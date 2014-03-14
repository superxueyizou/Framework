package modeling.uas;

import sim.util.Double2D;
import tools.CALCULATION;
import tools.CONFIGURATION;
import modeling.env.Destination;
import modeling.env.Waypoint;

public class AutoPilot 
{
	UAS hostUAS;
	Waypoint nextDest;

	public AutoPilot(UAS uas, Waypoint nextD) 
	{
		hostUAS = uas;
		nextDest= nextD;
	}
	
	public void update(UAS uas, Destination nextD)
	{
		hostUAS = uas;
		nextDest= nextD;
	}
	
	public Waypoint executeDubins()
	{		
		return CALCULATION.takeDubinsPath(hostUAS, nextDest);
	}
	
	public Waypoint execute()
	{
		Double2D hostUASLocation = hostUAS.getLocation();		
		final Double2D nextWpLocation =nextDest.getLocation();
 		final double distSqToNextWp = nextWpLocation.distanceSq(hostUASLocation);

 		Double2D prefVelocity;
 		double prefSpeed = hostUAS.getUasPerformance().getPrefSpeed();
 		if (Math.pow(prefSpeed,2) > distSqToNextWp)
 		{
 			prefVelocity = nextWpLocation.subtract(hostUASLocation);
 			
 		}
 		else
 		{
 			prefVelocity = nextWpLocation.subtract(hostUASLocation).normalize().multiply(prefSpeed); 
 			
 		}
 		
 		Double2D newVelocity;
		double angle = hostUAS.getVelocity().masonRotateAngleToDouble2D(prefVelocity);	
		if(Math.abs(angle) > hostUAS.getUasPerformance().getMaxTurning())
		{
			newVelocity = hostUAS.getVelocity().masonRotate(hostUAS.getUasPerformance().getMaxTurning()*angle/Math.abs(angle));
		}
		else
		{
			newVelocity = hostUAS.getVelocity().masonRotate(angle);
		}
					
		Waypoint wp= new Waypoint(hostUAS.getState().getNewID(), hostUAS.getDestination());
		wp.setLocation(hostUASLocation.add(newVelocity));
		return wp;
	}
	

}
