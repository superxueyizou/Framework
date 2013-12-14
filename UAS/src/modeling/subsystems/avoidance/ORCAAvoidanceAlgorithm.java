/**
 * 
 */
package modeling.subsystems.avoidance;

import edu.unc.cs.gamma.orca.ORCA;
import edu.unc.cs.gamma.orca.ORCASimulator;
import edu.unc.cs.gamma.orca.Vector2;
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
public class ORCAAvoidanceAlgorithm extends AvoidanceAlgorithm{

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
	
	private int xTimes= 0;
	public ORCASimulator orcaSimulator;
	private int hostUASIDInRVOSimulator =0;
	private Vector2 targetPosInRVOSimulator;
	private boolean isGoalReached;
	
	//private Vector2 velocity;
	
	
	public ORCAAvoidanceAlgorithm(SimState simstate, UAS uas) {
		// TODO Auto-generated constructor stub
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();
		
		targetPosInRVOSimulator = new Vector2(destinationCoor.x, destinationCoor.y);
		isGoalReached=false;		
				
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		initORCASimulator();
	}
	
	
	public void initORCASimulator()
	{
		// Create a new simulator instance.
		orcaSimulator = new ORCASimulator();
		// Specify global time step of the simulation.
		orcaSimulator.setTimeStep(1);
		// Specify default parameters for agents that are subsequently added.
		orcaSimulator.setAgentDefaults(hostUAS.getViewingRange(), 8, 15.0, 15.0,hostUAS.getRadius(), hostUAS.getUasPerformance().getCurrentMaxSpeed()); //rvoSimulator.setAgentDefaults(1.0f, 8, 10.0f, 20.0f, 0.5f, 8.0f);

		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				hostUASIDInRVOSimulator=i;
			}
			Vector2 location= new Vector2(uas.getLocation().x,uas.getLocation().y);
			orcaSimulator.addAgent(location);			
		}
		
	}
	
	
	public Waypoint execute()
	{
		
		hostUASCorr = hostUAS.getLocation();
	
		viewingRange = hostUAS.getViewingRange();
		viewingAngle = hostUAS.getViewingAngle();
		performance = hostUAS.getUasPerformance();
		
		
		updateRVOSimulator(state);
		orcaSimulator.doStep();
		setPreferredVelocity(state);
		
		Vector2 vel= orcaSimulator.getAgentVelocity(hostUASIDInRVOSimulator);
		Double2D velDouble2D = new Double2D(vel.x(), vel.y());
		hostUAS.setVelocity(velDouble2D);
		
		Vector2 loc = orcaSimulator.getAgentPosition(hostUASIDInRVOSimulator);
		Double2D newLocation = new Double2D(loc.x(), loc.y());
		
		
		Waypoint wp = new Waypoint(state.getNewID(), hostUAS.getDestination());
		wp.setLocation(newLocation);
		return wp;
	
	}

	
	public void updateRVOSimulator(SAAModel state)
	{
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS agent= (UAS)state.uasBag.get(i);
			
			Vector2 location;
			Vector2 velocity;
			if(agent==hostUAS)
			{
				location = new Vector2(agent.getLocation().x,agent.getLocation().y);
				double velX = agent.getVelocity().x;
				double velY = agent.getVelocity().y;
				velocity= new Vector2(velX, velY);
				
			}
			else
			{
				if (!agent.isActive)
				{
					orcaSimulator.setAgentPosition(i, new Vector2(Double.MAX_VALUE, Double.MAX_VALUE));
					orcaSimulator.setAgentVelocity(i, new Vector2(0,0));
					continue;
				}
				
				location = new Vector2(agent.getLocation().x,agent.getLocation().y);
				double velX = agent.getVelocity().x;
				double velY = agent.getVelocity().y;
			
//				location = new Vector2(agent.getLocation().x+state.random.nextGaussian(),agent.getLocation().y+state.random.nextGaussian());
//				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
//				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
				
				velocity= new Vector2(velX, velY);
			}
			orcaSimulator.setAgentPosition(i, location);
			orcaSimulator.setAgentVelocity(i, velocity);
			
		}

	}
	
	public void setPreferredVelocity(SAAModel state) 
	{
		if (!isGoalReached) 
		{
			if (ORCA.absSq(targetPosInRVOSimulator.sub(orcaSimulator.getAgentPosition(hostUASIDInRVOSimulator))) < 1) 
			{	
				// Agent is within one radius of its goal, set preferred velocity to zero.
				orcaSimulator.setAgentPrefVelocity(hostUASIDInRVOSimulator, new Vector2(0.0, 0.0));
				isGoalReached = true;
//				state.numGoalReached++;
			} 
			else 
			{
				// Agent is far away from its goal, set preferred velocity as unit vector towards agent's goal.
				orcaSimulator.setAgentPrefVelocity(hostUASIDInRVOSimulator, ORCA.normalize(targetPosInRVOSimulator.sub(orcaSimulator.getAgentPosition(hostUASIDInRVOSimulator)).mul(1.5*1.5/Math.sqrt(2)))); 
			}
		}
	}


}
