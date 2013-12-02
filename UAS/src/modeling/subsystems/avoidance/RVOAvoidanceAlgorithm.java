/**
 * 
 */
package modeling.subsystems.avoidance;

import edu.unc.cs.gamma.rvo.RVO;
import edu.unc.cs.gamma.rvo.RVOSimulator;
import edu.unc.cs.gamma.rvo.Vector2;
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
import tools.CALCULATION;

/**
 * @author Xueyi
 *
 */
public class RVOAvoidanceAlgorithm extends AvoidanceAlgorithm
{

	/**
	 * 
	 */
	private COModel state; 
	private UAS hostUAS;

	private Destination destination;
	Double2D destinationCoor;

	public RVOSimulator rvoSimulator;
	private int hostUASIDInRVOSimulator =0;
	private Vector2 targetPosInRVOSimulator;

	private boolean isGoalReached;
	
	//private Vector2 velocity;
	
	
	public RVOAvoidanceAlgorithm(SimState simstate, UAS uas) {
		// TODO Auto-generated constructor stub
		state = (COModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();
		
		targetPosInRVOSimulator = new Vector2(destinationCoor.x, destinationCoor.y);
		isGoalReached=false;		
				
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		initRVO2Simulator();
	}
	
	
	public void initRVO2Simulator()
	{
		// Create a new simulator instance.
		rvoSimulator = new RVOSimulator();
		// Specify global time step of the simulation.
		rvoSimulator.setTimeStep(1);
		
		/* Specify default parameters for agents that are subsequently added.
		 * 
		 * void RVOSimulator.setAgentDefaults(
		 * int velSampleCountDefault, 
		 * double neighborDistDefault, 
		 * int maxNeighborsDefault, 
		 * double radiusDefault,
		 * double goalRadiusDefault, 
		 * double prefSpeedDefault,
		 * double maxSpeedDefault, 
		 * double safetyFactorDefault, 
		 * double maxAccelDefault)
		 * */
		rvoSimulator.setAgentDefaults( 250, 15.0, 10, 1.667, 1.0, 1.5, 2.235, 7.5, 1.2);
		//rvoSimulator.setAgentDefaults( 250, 15.0, 10, hostUAS.getRadius(), 1.0, 1.5, hostUAS.getPerformance().getCurrentMaxSpeed(), 7.5, hostUAS.getPerformance().getCurrentMaxAccel());
		// Specify default parameters for agents that are subsequently added.
//		rvoSimulator.setAgentDefaults(hostUAS.getViewingRange(), 8, 15.0, 15.0,hostUAS.getRadius(), hostUAS.getPerformance().getCurrentMaxSpeed()); //rvoSimulator.setAgentDefaults(1.0f, 8, 10.0f, 20.0f, 0.5f, 8.0f);

		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				hostUASIDInRVOSimulator=i;
			}
			Vector2 location= new Vector2(uas.getLocation().x,uas.getLocation().y);
			rvoSimulator.addAgent(location, rvoSimulator.addGoal(new Vector2(uas.getDestination().getLocation().x, uas.getDestination().getLocation().y ) ));
						
		}
		rvoSimulator.initSimulation();
		
	}
	
	
	public Waypoint execute()
	{
		
		updateRVOSimulator(state);
		rvoSimulator.doStep();
		setPreferredVelocity(state);
		
		Vector2 vel= rvoSimulator.getAgentVelocity(hostUASIDInRVOSimulator);
		Double2D velDouble2D = new Double2D(vel.x(), vel.y());
		double speed= velDouble2D.length();
		double bearing= CALCULATION.calculateAngle(new Double2D(0,0), velDouble2D);
		
		
//		if(speed > performance.getCurrentMaxSpeed())
//		{
//			speed = performance.getCurrentMaxSpeed();
//		}
//		
//		boolean isTurnRight;
//		double bearingChange = CALCULATION.correctAngle(bearing-hostUAS.getBearing());
//		if(bearingChange > 0)
//		{
//			isTurnRight=false;
//		}
//		else
//		{
//			isTurnRight= true;
//		}
//		
//		if(bearingChange > performance.getCurrentMaxTurning())
//		{
//			bearing= CALCULATION.correctAngle(hostUAS.getBearing()+performance.getCurrentMaxTurning());
//			bearingChange = performance.getCurrentMaxTurning();
//		}
//		else if(bearingChange < -performance.getCurrentMaxTurning())
//		{
//			bearing= CALCULATION.correctAngle(hostUAS.getBearing()-performance.getCurrentMaxTurning());
//			bearingChange = performance.getCurrentMaxTurning();
//			
//		}
		
		hostUAS.setSpeed(speed);
		hostUAS.setBearing(bearing);
		
//		return CALCULATION.calculateWaypoint(hostUAS, bearingChange, isTurnRight);
		
		Vector2 loc = rvoSimulator.getAgentPosition(hostUASIDInRVOSimulator);
		Double2D newLocation = new Double2D(loc.x(), loc.y());
		
		
		Waypoint wp = new Waypoint(state.getNewID(), hostUAS.getDestination());
		wp.setLocation(newLocation);
		return wp;
	
	}

	
	public void updateRVOSimulator(COModel state)
	{
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS agent= (UAS)state.uasBag.get(i);
			
			Vector2 location;
			Vector2 velocity;
			if(agent==hostUAS)
			{
				location = new Vector2(agent.getLocation().x,agent.getLocation().y);
				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()));
				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()));
				velocity= new Vector2(velX, velY);
				
			}
			else
			{
				if (!agent.isActive)
				{
					rvoSimulator.setAgentPosition(i, new Vector2(Double.MAX_VALUE, Double.MAX_VALUE));
					rvoSimulator.setAgentVelocity(i, new Vector2(0,0));
					continue;
				}
				
				location = new Vector2(agent.getLocation().x,agent.getLocation().y);
				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()));
				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()));
			
//				location = new Vector2(agent.getLocation().x+state.random.nextGaussian(),agent.getLocation().y+state.random.nextGaussian());
//				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
//				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
				
				velocity= new Vector2(velX, velY);
			}
			rvoSimulator.setAgentPosition(i, location);
			rvoSimulator.setAgentVelocity(i, velocity);
			
		}

	}
	
	public void setPreferredVelocity(COModel state) 
	{
		if (!isGoalReached) 
		{
			if (RVO.absSq(targetPosInRVOSimulator.sub(rvoSimulator.getAgentPosition(hostUASIDInRVOSimulator))) < 1) 
			{	
				// Agent is within one radius of its goal, set preferred velocity to zero.
//				rvoSimulator.setAgentPrefVelocity(hostUASIDInRVOSimulator, new Vector2(0.0f, 0.0f));
				isGoalReached = true;
//				state.numGoalReached++;
			} 
			else 
			{
				// Agent is far away from its goal, set preferred velocity as unit vector towards agent's goal.
//				rvoSimulator.setAgentPrefVelocity(hostUASIDInRVOSimulator, RVO2.normalize(targetPosInRVOSimulator.sub(rvoSimulator.getAgentPosition(hostUASIDInRVOSimulator)).mul(1.5*1.5/Math.sqrt(2)))); 
			}
		}
	}
	
//	public Vector2 getTargetPosInRVOSimulator() {
//		return targetPosInRVOSimulator;
//	}
//
//
//	public void setTargetPosInRVOSimulator(Vector2 targetPosInRVOSimulator) {
//		this.targetPosInRVOSimulator = targetPosInRVOSimulator;
//	}


}
