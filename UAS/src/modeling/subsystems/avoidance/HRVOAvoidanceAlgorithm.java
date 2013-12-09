/**
 * 
 */
package modeling.subsystems.avoidance;

import edu.unc.cs.gamma.hrvo.HRVO;
import edu.unc.cs.gamma.hrvo.HRVOSimulator;
import edu.unc.cs.gamma.hrvo.Vector2;
import modeling.SAAModel;
import modeling.Destination;
import modeling.UAS;
import modeling.Waypoint;
import sim.engine.SimState;
import sim.util.Double2D;
import tools.CALCULATION;
import tools.CONFIGURATION;

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
	private Vector2 targetPosInHRVOSimulator;

	private boolean isGoalReached;
	
	//private Vector2 velocity;
	
	
	public HRVOAvoidanceAlgorithm(SimState simstate, UAS uas) {
		// TODO Auto-generated constructor stub
		state = (SAAModel) simstate;
		hostUAS = uas;
		
		destination = hostUAS.getDestination();
		destinationCoor = destination.getLocation();
		
		targetPosInHRVOSimulator = new Vector2(destinationCoor.x, destinationCoor.y);
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
		 * \param[in]  uncertaintyOffset  The default uncertainty offset of a new agent.
		 * \param[in]  maxAccel           The default maximum acceleration of a new agent.
		 * \param[in]  velocity           The default initial velocity of a new agent.
		 * \param[in]  orientation        The default initial orientation (in radians) of a new agent.
		 */
		hrvoSimulator.setAgentDefaults( hostUAS.getViewingRange(), 10, hostUAS.getRadius(), 1.0, hostUAS.getSpeed(),  hostUAS.getPerformance().getCurrentMaxSpeed());
//		rvoSimulator.setAgentDefaults( 250, hostUAS.getViewingRange(), 10, hostUAS.getRadius(), 1.0, hostUAS.getSpeed(), hostUAS.getPerformance().getCurrentMaxSpeed(), 7.5, CONFIGURATION.selfAlfa, hostUAS.getPerformance().getCurrentMaxAccel());
		//rvoSimulator.setAgentDefaults( 250, 15.0, 10, hostUAS.getRadius(), 1.0, 1.5, hostUAS.getPerformance().getCurrentMaxSpeed(), 7.5, hostUAS.getPerformance().getCurrentMaxAccel());
		// Specify default parameters for agents that are subsequently added.

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
		//rvoSimulator.initSimulation();
		
	}
	
	
	public Waypoint execute()
	{
		
		updateRVOSimulator(state);
		hrvoSimulator.doStep();
		setPreferredVelocity(state);
		
		Vector2 vel= hrvoSimulator.getAgentVelocity(hostUASIDInHRVOSimulator);
		Double2D velDouble2D = new Double2D(vel.x(), vel.y());
		double speed= velDouble2D.length();
		double bearing= CALCULATION.calculateAngle(new Double2D(0,0), velDouble2D);
		
		hostUAS.setSpeed(speed);
		hostUAS.setBearing(bearing);
		
		
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
				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()));
				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()));
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
				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()));
				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()));
			
//				location = new Vector2(agent.getLocation().x+state.random.nextGaussian(),agent.getLocation().y+state.random.nextGaussian());
//				double velX = agent.getSpeed()*Math.cos(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
//				double velY = agent.getSpeed()*Math.sin(Math.toRadians(agent.getBearing()))+state.random.nextGaussian();
				
				velocity= new Vector2(velX, velY);
			}
			hrvoSimulator.setAgentPosition(i, location);
			hrvoSimulator.setAgentVelocity(i, velocity);
			
		}

	}
	
	public void setPreferredVelocity(SAAModel state) 
	{
		if (!isGoalReached) 
		{
			if (HRVO.absSq(targetPosInHRVOSimulator.sub(hrvoSimulator.getAgentPosition(hostUASIDInHRVOSimulator))) < 1) 
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

}
