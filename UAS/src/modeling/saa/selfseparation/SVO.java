/**
 * 
 */
package modeling.saa.selfseparation;

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
public class SVO extends SelfSeparationAlgorithm
{
	/**
	 * 
	 */
	private SAAModel state; 
	private UAS hostUAS;
	
	private Destination destination;
	Double2D destinationCoor;

	public AVOSimulator avoSimulator;
	private int hostUASIDInAVOSimulator =0;

	VelocityObstaclePoint apex = new VelocityObstaclePoint();
	VelocityObstaclePoint side1End= new VelocityObstaclePoint();
	VelocityObstaclePoint side2End= new VelocityObstaclePoint();
	
	public SVO(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();
		
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		initSVOSimulator();
	}
	
	
	public void initSVOSimulator()
	{
		// Create a new simulator instance.
		avoSimulator = new AVOSimulator(state);
		// Specify global time step of the simulation.
		avoSimulator.setTimeStep(1);
		// Specify default parameters for agents that are subsequently added.
		avoSimulator.setAgentDefaults(hostUAS.getViewingRange(), 8, 15, 15, hostUAS.getRadius(),1.0,hostUAS.getSpeed(), hostUAS.getUasPerformance().getCurrentMaxSpeed(),-1,  hostUAS.getUasPerformance().getMaxAcceleration(), new Double2D()); 
		//orcaSimulator.setAgentDefaults(1.0, 8, 10.0, 20.0f, 0.5, 8.0, new Double());

		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				hostUASIDInAVOSimulator=i;
			}

			avoSimulator.addAgent(uas.getLocation(), avoSimulator.addGoal(uas.getDestination().getLocation()));			
		}
		
	}
	
	
	public Waypoint execute()
	{		
		updateSVOSimulator(state);
		avoSimulator.doStep();
		
		if(state.runningWithUI)
		{
			state.voField.removeNode(apex);
			state.voField.removeNode(side1End);
			state.voField.removeNode(side2End);
			
			if(!avoSimulator.getAgentVelocityObstacles(hostUASIDInAVOSimulator).isEmpty())
			{
				VelocityObstacle vo =  avoSimulator.getAgentVelocityObstacles(hostUASIDInAVOSimulator).get(0);
				Double2D apexLoc = hostUAS.getLocation().add(new Double2D(vo.apex.getX(),vo.apex.getY())) ;
				Double2D side1EndLoc = apexLoc.add(new Double2D(100*vo.side1.getX(),100*vo.side1.getY()));
				Double2D side2EndLoc = apexLoc.add(new Double2D(100*vo.side2.getX(),100*vo.side2.getY()));
				
				state.environment.setObjectLocation(apex, apexLoc);
				state.environment.setObjectLocation(side1End, side1EndLoc);
				state.environment.setObjectLocation(side2End, side2EndLoc);
				
				state.voField.addNode(apex);
				state.voField.addNode(side1End);
				state.voField.addNode(side1End);
				
				state.voField.addEdge(apex, side1End, null);
				state.voField.addEdge(apex, side2End, null);			
				
//				System.out.println(avoSimulator.getAgentVelocityObstacles(hostUASIDInavoSimulator).size());
			}
			
		}


		Double2D newLocation = avoSimulator.getAgentPosition(hostUASIDInAVOSimulator);		
		Waypoint wp = new Waypoint(state.getNewID(), hostUAS.getDestination());
		wp.setLocation(newLocation);
		return wp;
	
	}

	
	public void updateSVOSimulator(SAAModel state)
	{
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS agent= (UAS)state.uasBag.get(i);
			
			Double2D location;
			Double2D velocity;
			double   radius;
			
			if(agent==hostUAS)
			{
				location = agent.getLocation();
				velocity = agent.getVelocity();
				radius = agent.getRadius();
				
			}
			else
			{
				if (!agent.isActive)
				{
					avoSimulator.setAgentPosition(i, new Double2D(Double.MAX_VALUE, Double.MAX_VALUE));
					avoSimulator.setAgentVelocity(i, new Double2D(0,0));
					avoSimulator.setAgentRadius(i, 0);
					continue;
				}
				
				location = new Double2D(agent.getOldLocation().x,agent.getOldLocation().y);
				velocity= new Double2D(agent.getOldVelocity().x, agent.getOldVelocity().y);
				radius = agent.getRadius();
				
//				Normal normal = new Normal(0,0.02,state.random);
//				location = new Double2D(agent.getOldLocation().x*(1+normal.nextDouble()), agent.getOldLocation().y*(1+normal.nextDouble()));
//				velocity = new Double2D(agent.getOldVelocity().x*(1+normal.nextDouble()), agent.getOldVelocity().y*(1+normal.nextDouble()));
//				radius = agent.getRadius()*(1+normal.nextDouble());
			}
			
			avoSimulator.setAgentPosition(i, location);
			avoSimulator.setAgentVelocity(i, velocity);
			avoSimulator.setAgentRadius(i, radius);
			
		}

	}



}
