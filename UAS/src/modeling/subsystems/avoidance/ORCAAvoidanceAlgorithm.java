/**
 * 
 */
package modeling.subsystems.avoidance;


import edu.unc.cs.gamma.orca.ORCA;
import edu.unc.cs.gamma.orca.ORCASimulator;
import edu.unc.cs.gamma.orca.Vector2;
import modeling.SAAModel;
import modeling.Destination;
import modeling.UAS;
import modeling.Waypoint;
import sim.engine.SimState;
import sim.util.Double2D;
import tools.UTILS;

/**
 * @author Xueyi
 *
 */
public class ORCAAvoidanceAlgorithm extends AvoidanceAlgorithm
{
	/**
	 * 
	 */
	private SAAModel state; 
	private UAS hostUAS;
	private Destination destination;
	Double2D destinationCoor;

	public ORCASimulator orcaSimulator;
	private int hostUASIDInORCASimulator =0;
	private Double2D targetPosInORCASimulator;
	private boolean isGoalReached;
	
	public ORCAAvoidanceAlgorithm(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();
		
		targetPosInORCASimulator = new Double2D(destinationCoor.x, destinationCoor.y);
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
		orcaSimulator.setAgentDefaults(hostUAS.getViewingRange(), 8, 15.0, 15.0,hostUAS.getRadius(), hostUAS.getUasPerformance().getCurrentMaxSpeed()); 
		//orcaSimulator.setAgentDefaults(1.0, 8, 10.0, 20.0f, 0.5, 8.0, new Double());

		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				hostUASIDInORCASimulator=i;
			}
			Vector2 location= new Vector2(uas.getLocation().x,uas.getLocation().y);
			orcaSimulator.addAgent(location);			
		}
		
	}
	
	
	public Waypoint execute()
	{
		
		updateORCASimulator(state);
		orcaSimulator.doStep();
		setPreferredVelocity(state);

		Vector2 vel = orcaSimulator.getAgentVelocity(hostUASIDInORCASimulator);
		Double2D velDouble2D = new Double2D(vel.x(),vel.y());
		hostUAS.setOldVelocity(hostUAS.getVelocity());
		hostUAS.setVelocity(velDouble2D);

		Vector2  newLocation = orcaSimulator.getAgentPosition(hostUASIDInORCASimulator);
		Double2D newLocationDouble2D = new Double2D(newLocation.x(),newLocation.y());
		
		
		Waypoint wp = new Waypoint(state.getNewID(), hostUAS.getDestination());
		wp.setLocation(newLocationDouble2D);
		return wp;
	
	}

	
	public void updateORCASimulator(SAAModel state)
	{
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS agent= (UAS)state.uasBag.get(i);
			
			Double2D location;
			Double2D velocity;
			if(agent==hostUAS)
			{
				location = agent.getLocation();
				velocity= agent.getVelocity();
				
			}
			else
			{
				if (!agent.isActive)
				{
					orcaSimulator.setAgentPosition(i, new Vector2(Double.MAX_VALUE, Double.MAX_VALUE));
					orcaSimulator.setAgentVelocity(i, new Vector2(0,0));
					continue;
				}
				
				location = new Double2D(agent.getLocation().x,agent.getLocation().y);
				velocity= new Double2D(agent.getVelocity().x, agent.getVelocity().y);
				
//				location = new Vector2(agent.getLocation().x+state.random.nextGaussian(),agent.getLocation().y+state.random.nextGaussian());
//				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
//				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
//				velocity= new Double2D(velX, velY);
					
			}
			orcaSimulator.setAgentPosition(i, UTILS.Double2DToVector2(location));
			orcaSimulator.setAgentVelocity(i, UTILS.Double2DToVector2(velocity));
			
		}

	}
	
	public void setPreferredVelocity(SAAModel state) 
	{
		if (!isGoalReached) 
		{
			if (ORCA.absSq(UTILS.Double2DToVector2(targetPosInORCASimulator).sub(orcaSimulator.getAgentPosition(hostUASIDInORCASimulator))) < 1) 
			{	
				// Agent is within one radius of its goal, set preferred velocity to zero.
				orcaSimulator.setAgentPrefVelocity(hostUASIDInORCASimulator, new Vector2(0.0, 0.0));
				isGoalReached = true;
			} 
			else 
			{
				// Agent is far away from its goal, set preferred velocity as unit vector towards agent's goal.
				orcaSimulator.setAgentPrefVelocity(hostUASIDInORCASimulator, ORCA.normalize(UTILS.Double2DToVector2(targetPosInORCASimulator).sub(orcaSimulator.getAgentPosition(hostUASIDInORCASimulator)).mul(1.5*1.5/Math.sqrt(2)))); 
			}
		}
	}


}
