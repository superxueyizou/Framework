/**
 * 
 */
package modeling.subsystems.avoidance;

import avo.AVOSimulator;
import avo.VelocityObstacle;
import modeling.SAAModel;
import modeling.Destination;
import modeling.UAS;
import modeling.VelocityObstaclePoint;
import modeling.Waypoint;
import sim.engine.SimState;
import sim.util.Double2D;

/**
 * @author Xueyi
 *
 */
public class AVOAvoidanceAlgorithm extends AvoidanceAlgorithm
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
	
	public AVOAvoidanceAlgorithm(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();
		
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		initORCASimulator();
	}
	
	
	public void initORCASimulator()
	{
		// Create a new simulator instance.
		avoSimulator = new AVOSimulator();
		// Specify global time step of the simulation.
		avoSimulator.setTimeStep(1);
		// Specify default parameters for agents that are subsequently added.
		avoSimulator.setAgentDefaults(hostUAS.getViewingRange(), 8, 15.0, 15.0, hostUAS.getRadius(),1.0,hostUAS.getSpeed(), hostUAS.getUasPerformance().getCurrentMaxSpeed(),hostUAS.getAlpha(),  hostUAS.getUasPerformance().getCurrentMaxSpeed(), new Double2D()); 
		//orcaSimulator.setAgentDefaults(1.0, 8, 10.0, 20.0f, 0.5, 8.0, new Double());

		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				hostUASIDInAVOSimulator=i;
			}
			Double2D location= new Double2D(uas.getLocation().x,uas.getLocation().y);
			avoSimulator.addAgent(location, avoSimulator.addGoal(uas.getDestination().getLocation()));			
		}
		
	}
	
	
	public Waypoint execute()
	{
		
		updateORCASimulator(state);
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
		
		Double2D velDouble2D = avoSimulator.getAgentVelocity(hostUASIDInAVOSimulator);
		hostUAS.setVelocity(velDouble2D);

		Double2D newLocation = avoSimulator.getAgentPosition(hostUASIDInAVOSimulator);
		
		
		Waypoint wp = new Waypoint(state.getNewID(), hostUAS.getDestination());
		wp.setLocation(newLocation);
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
					avoSimulator.setAgentPosition(i, new Double2D(Double.MAX_VALUE, Double.MAX_VALUE));
					avoSimulator.setAgentVelocity(i, new Double2D(0,0));
					continue;
				}
				
				location = new Double2D(agent.getLocation().x,agent.getLocation().y);
				velocity= new Double2D(agent.getVelocity().x, agent.getVelocity().y);
				
//				location = new Vector2(agent.getLocation().x+state.random.nextGaussian(),agent.getLocation().y+state.random.nextGaussian());
//				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
//				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
//				velocity= new Double2D(velX, velY);
					
			}
			avoSimulator.setAgentPosition(i, location);
			avoSimulator.setAgentVelocity(i, velocity);
			
		}

	}



}
