/**
 * 
 */
package modeling.subsystems.avoidance;

import edu.unc.cs.gamma.hrvo.HRVOSimulator;
import edu.unc.cs.gamma.hrvo.Vector2;
import edu.unc.cs.gamma.hrvo.VelocityObstacle;
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
public class HRVOAvoidanceAlgorithm extends AvoidanceAlgorithm
{

	/**
	 * 
	 */
	private SAAModel state; 
	private UAS hostUAS;

	private Destination destination;
	Double2D destinationCoor;

	public HRVOSimulator hrvoSimulator;
	private int hostUASIDInHRVOSimulator =0;
	
	VelocityObstaclePoint apex = new VelocityObstaclePoint();
	VelocityObstaclePoint side1End= new VelocityObstaclePoint();
	VelocityObstaclePoint side2End= new VelocityObstaclePoint();
	
	
	public HRVOAvoidanceAlgorithm(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();	
				
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		initHRVOSimulator();
	}
	
	
	public void initHRVOSimulator()
	{
		// Create a new simulator instance.
		hrvoSimulator = new HRVOSimulator();
		// Specify global time step of the simulation.
		hrvoSimulator.setTimeStep(1);
		
		/**
		 * \brief      Sets the default properties for any new agent that is added.
		 * \param[in]  neighborDist       The default maximum neighbor distance of a new agent.
		 * \param[in]  maxNeighbors       The default maximum neighbor count of a new agent.
		 * \param[in]  radius             The default radius of a new agent.
		 * \param[in]  goalRadius         The default goal radius of a new agent.
		 * \param[in]  prefSpeed          The default preferred speed of a new agent.
		 * \param[in]  maxSpeed           The default maximum speed of a new agent.
		 * \param[in]  alpha              The default alpha of a new agent.
		 * \param[in]  uncertaintyOffset  The default uncertainty offset of a new agent (optional).
		 * \param[in]  maxAccel           The default maximum acceleration of a new agent (optional).
		 * \param[in]  velocity           The default initial velocity of a new agent (optional).
		 * \param[in]  orientation        The default initial orientation (in radians) of a new agent (optional).
		 */
		hrvoSimulator.setAgentDefaults(hostUAS.getViewingRange(), 10, hostUAS.getRadius(), 1.0, hostUAS.getSpeed(),  hostUAS.getUasPerformance().getMaxSpeed(), hostUAS.getAlpha(),0.0, hostUAS.getUasPerformance().getMaxAcceleration());
//		hrvoSimulator.setAgentDefaults(hostUAS.getViewingRange(), 10, hostUAS.getRadius(), 1.0, hostUAS.getSpeed(),  hostUAS.getPerformance().getMaxSpeed(), hostUAS.getAlpha());
//		hrvoSimulator.setAgentDefaults(10, 10, 1.667, 1, 1.5, 2.235, 1.0, 0.0);

		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				hostUASIDInHRVOSimulator=i;
			}
			Vector2 location= new Vector2(uas.getLocation().x,uas.getLocation().y);
			hrvoSimulator.addAgent(location, hrvoSimulator.addGoal(new Vector2(uas.getDestination().getLocation().x, uas.getDestination().getLocation().y ) ));
						
		}
	
	}
	
	
	public Waypoint execute()
	{
		
		updateRVOSimulator(state);
		
		hrvoSimulator.doStep();
//		setPreferredVelocity(state);
		if(state.runningWithUI)
		{
			state.voField.removeNode(apex);
			state.voField.removeNode(side1End);
			state.voField.removeNode(side2End);
			
			if(!hrvoSimulator.getAgentVelocityObstacles(hostUASIDInHRVOSimulator).isEmpty())
			{
				VelocityObstacle vo =  hrvoSimulator.getAgentVelocityObstacles(hostUASIDInHRVOSimulator).get(0);
				Double2D apexLoc = hostUAS.getLocation().add(new Double2D(vo.getApex().getX(),vo.getApex().getY())) ;
				Double2D side1EndLoc = apexLoc.add(new Double2D(100*vo.getSide1().getX(),100*vo.getSide1().getY()));
				Double2D side2EndLoc = apexLoc.add(new Double2D(100*vo.getSide2().getX(),100*vo.getSide2().getY()));
				
				state.environment.setObjectLocation(apex, apexLoc);
				state.environment.setObjectLocation(side1End, side1EndLoc);
				state.environment.setObjectLocation(side2End, side2EndLoc);
				
				state.voField.addNode(apex);
				state.voField.addNode(side1End);
				state.voField.addNode(side1End);
				
				state.voField.addEdge(apex, side1End, null);
				state.voField.addEdge(apex, side2End, null);			
				
//				System.out.println(hrvoSimulator.getAgentVelocityObstacles(hostUASIDInHRVOSimulator).size());
			}
			
		}
		
		
		Vector2 vel= hrvoSimulator.getAgentVelocity(hostUASIDInHRVOSimulator);
		Double2D velDouble2D = new Double2D(vel.x(), vel.y());
		hostUAS.setVelocity(velDouble2D);

		Vector2 loc = hrvoSimulator.getAgentPosition(hostUASIDInHRVOSimulator);
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
					hrvoSimulator.setAgentPosition(i, new Vector2(Double.MAX_VALUE, Double.MAX_VALUE));
					hrvoSimulator.setAgentVelocity(i, new Vector2(0,0));
					continue;
				}
				
				location = new Vector2(agent.getLocation().x,agent.getLocation().y);
				double velX = agent.getVelocity().x;
				double velY = agent.getVelocity().y;
			
//				location = new Vector2(agent.getLocation().x+state.random.nextGaussian(),agent.getLocation().y+state.random.nextGaussian());
//				double velX = agent.getVelocity().x + state.random.nextGaussian();
//				double velY = agent.getVelocity().y + state.random.nextGaussian();
				
				velocity= new Vector2(velX, velY);
			}
			hrvoSimulator.setAgentPosition(i, location);
			hrvoSimulator.setAgentVelocity(i, velocity);
			
		}

	}
	
}
