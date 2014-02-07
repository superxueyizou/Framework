/**
 * 
 */
package modeling.saa.collsionavoidance;

import avo.AVOSimulator;
import avo.VelocityObstacle;
import modeling.SAAModel;
import modeling.env.Destination;
import modeling.env.VelocityObstaclePoint;
import modeling.env.Waypoint;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.util.Double2D;
import sim.util.distribution.Normal;

/**
 * @author Xueyi
 *
 */
public class AVO extends CollisionAvoidanceAlgorithm
{
	/**
	 * 
	 */
	private SAAModel state; 
	private UAS hostUAS;

	public AVOSimulator avoSimulator;
	private int hostUASIDInAVOSimulator =0;

	VelocityObstaclePoint apex = new VelocityObstaclePoint();
	VelocityObstaclePoint side1End= new VelocityObstaclePoint();
	VelocityObstaclePoint side2End= new VelocityObstaclePoint();
	
	public AVO(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;	
	}
		
	/******************************************************************************************************************************************/
	
	public void init()
	{
		initAVOSimulator();
	}
	
	
	public void initAVOSimulator()
	{
		// Create a new simulator instance.
		avoSimulator = new AVOSimulator(state);
		// Specify global time step of the simulation.
		avoSimulator.setTimeStep(1);
		//Specify default parameters for agents that are subsequently added. 
		//neighborDist, maxNeighbors, timeHorizon, timeHorizonObst, 
		//radius, goalRadius, prefSpeed, maxSpeed, alpha,
		//optional: maxAccel, maxTurn, velocity
		avoSimulator.setAgentDefaults(hostUAS.getViewingRange(), 8, 150, 150, 
				hostUAS.getRadius()*100, 10.0, hostUAS.getSpeed(), hostUAS.getUasPerformance().getMaxSpeed(),hostUAS.getAlpha(), 
				hostUAS.getUasPerformance().getMaxAcceleration(), hostUAS.getUasPerformance().getMaxTurning(), new Double2D()); 
		
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				hostUASIDInAVOSimulator=i;
			}
			Double2D avoLocation = new Double2D(uas.getLocation().x, -uas.getLocation().y);
			Double2D avoGoalLocation = new Double2D(uas.getDestination().getLocation().x, -uas.getDestination().getLocation().y);
			avoSimulator.addAgent(avoLocation, avoSimulator.addGoal(avoGoalLocation));				
		}
		
	}
	
	
	public Waypoint execute()
	{		
		updateAVOSimulator(state);
		avoSimulator.agentDoStep(hostUASIDInAVOSimulator);
		
		if(state.runningWithUI)
		{
			state.voField.removeNode(apex);
			state.voField.removeNode(side1End);
			state.voField.removeNode(side2End);
			
			if(!avoSimulator.getAgentVelocityObstacles(hostUASIDInAVOSimulator).isEmpty())
			{
				VelocityObstacle vo =  avoSimulator.getAgentVelocityObstacles(hostUASIDInAVOSimulator).get(0);
				Double2D apexLoc = hostUAS.getLocation().add(new Double2D(vo.apex.getX(),-vo.apex.getY())) ;
				Double2D side1EndLoc = apexLoc.add(new Double2D(10000*vo.side1.getX(),-10000*vo.side1.getY()));
				Double2D side2EndLoc = apexLoc.add(new Double2D(10000*vo.side2.getX(),-10000*vo.side2.getY()));
				
				state.environment.setObjectLocation(apex, apexLoc);
				state.environment.setObjectLocation(side1End, side1EndLoc);
				state.environment.setObjectLocation(side2End, side2EndLoc);
				
				state.voField.addNode(apex);
				state.voField.addNode(side1End);
				state.voField.addNode(side1End);
				
				state.voField.addEdge(apex, side1End, null);
				state.voField.addEdge(apex, side2End, null);			
//				System.out.println("dddddddddddddd"+avoSimulator.getAgentVelocityObstacles(hostUASIDInAVOSimulator).size());
				
			}
			
		}
	
		Double2D newLocation = avoSimulator.getAgentPosition(hostUASIDInAVOSimulator);		
		Waypoint wp = new Waypoint(state.getNewID(), hostUAS.getDestination());
		wp.setLocation(new Double2D(newLocation.x, -newLocation.y));
		return wp;
	
	}

	
	public void updateAVOSimulator(SAAModel state)
	{
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS agent= (UAS)state.uasBag.get(i);
			
			Double2D avoLocation;
			Double2D avoVelocity;
			double   avoRadius;
			
			if(agent==hostUAS)
			{
				avoLocation = new Double2D(agent.getLocation().x, -agent.getLocation().y);
				avoVelocity = new Double2D(agent.getVelocity().x, -agent.getVelocity().y);
				avoRadius = agent.getRadius()*1000;
				
			}
			else
			{
				if (!agent.isActive)
				{
					avoSimulator.setAgentPosition(i, new Double2D(Double.MAX_VALUE, -Double.MAX_VALUE));
					avoSimulator.setAgentVelocity(i, new Double2D(0,0));
					avoSimulator.setAgentRadius(i, 0);
					continue;
				}
				
				avoLocation = new Double2D(agent.getOldLocation().x, -agent.getOldLocation().y);
				avoVelocity = new Double2D(agent.getOldVelocity().x, -agent.getOldVelocity().y);
				avoRadius = agent.getRadius()*1000;
				
				
//				Normal normal = new Normal(0,0.02,state.random);
//				avoLocation = new Double2D(agent.getOldLocation().x*(1+normal.nextDouble()), -agent.getOldLocation().y*(1+normal.nextDouble()));
//				avoVelocity = new Double2D(agent.getOldVelocity().x*(1+normal.nextDouble()), -agent.getOldVelocity().y*(1+normal.nextDouble()));
//				avoRadius = agent.getRadius()*(1+normal.nextDouble());
			}
			
			avoSimulator.setAgentPosition(i, avoLocation);
			avoSimulator.setAgentVelocity(i, avoVelocity);
			avoSimulator.setAgentRadius(i, avoRadius);
			
		}

	}



}
