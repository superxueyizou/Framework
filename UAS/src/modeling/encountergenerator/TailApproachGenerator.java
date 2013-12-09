/**
 * 
 */
package modeling.encountergenerator;

import sim.util.Double2D;
import tools.CALCULATION;
import tools.CONFIGURATION;
import modeling.SAAModel;
import modeling.Destination;
import modeling.UAS;
import modeling.UASPerformance;
import modeling.subsystems.avoidance.AvoidanceAlgorithm;
import modeling.subsystems.avoidance.AvoidanceAlgorithmAdapter;
import modeling.subsystems.avoidance.RIPNAvoidanceAlgorithm;
import modeling.subsystems.avoidance.ORCAAvoidanceAlgorithm;
import modeling.subsystems.avoidance.RVOAvoidanceAlgorithm;
import modeling.subsystems.avoidance.SimpleAvoidanceAlgorithm;
import modeling.subsystems.avoidance.SmartTurnAvoidanceAlgorithm;
import modeling.subsystems.avoidance.TurnRightAvoidanceAlgorithm;
import modeling.subsystems.sensor.Sensor;
import modeling.subsystems.sensor.SimpleSensor;

/**
 * @author Xueyi
 *
 */
public class TailApproachGenerator extends EncounterGenerator {

	/**
	 * 
	 */
	private UAS intruder;
	private SAAModel state;
	private UAS self;
	private double offset;
	private double intruderSpeed;
	private int sideFactor;
	/************
	 * 
	 * @param state
	 * @param uas
	 * @param offset
	 * @param isRightSide Whether the intrude is on the right hand side of the self UAS
	 * @param intruderSpeed
	 * 
	 */
	public TailApproachGenerator(SAAModel state, UAS uas, double offset, boolean isRightSide, double intruderSpeed) {
		// TODO Auto-generated constructor stub
		this.state=state;
		this.self=uas;
		this.offset=offset;
		this.intruderSpeed = intruderSpeed;
		this.sideFactor = isRightSide? +1:-1;
	}
	
	/*********
	 * self and intruder will encounter at the middle point of each's journey
	 */
	public void execute()
	{
		double middleX = (self.getLocation().x + self.getDestination().getLocation().x)/2;
		double middleY = (self.getLocation().y + self.getDestination().getLocation().y)/2;
		double encounterTime = (self.getLocation().distance(new Double2D(middleX, middleY))/ self.getSpeed());
		double intruderHalf = encounterTime *intruderSpeed;
		
		Destination intruderDestination = new Destination(state.getNewID(), null);
		double tMX = middleX + sideFactor*offset* Math.sin(Math.toRadians(self.getBearing())); 
		double tMY = middleY + sideFactor*offset* Math.cos(Math.toRadians(self.getBearing()));
		
		double tX = tMX + intruderHalf* Math.cos(Math.toRadians(self.getBearing()));// intruderDestination's x
		double tY = tMY - intruderHalf* Math.sin(Math.toRadians(self.getBearing()));// intruderDestination's y
		intruderDestination.setLocation(new Double2D(tX , tY));
		
		UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed,  CONFIGURATION.tailApproachMaxAcceleration,  CONFIGURATION.tailApproachMaxDeceleration,  CONFIGURATION.tailApproachMaxTurning);
		intruder = new UAS(state.getNewID(), intruderDestination, uasPerformance, CONFIGURATION.tailApproachSafetyRadius);
		double x = tMX - intruderHalf* Math.cos(Math.toRadians(self.getBearing())); // intruder's x
		double y = tMY + intruderHalf* Math.sin(Math.toRadians(self.getBearing())); // intruder's y
		intruder.setLocation(new Double2D(x,y));
		intruder.setBearing(self.getBearing());
		intruder.setSpeed(intruderSpeed);
		
		AvoidanceAlgorithm aa;
		switch(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection)
		{
			case "TurnRightAvoidanceAlgorithm":
				aa= new TurnRightAvoidanceAlgorithm(state, intruder);
				break;
			case "SmartTurnAvoidanceAlgorithm":
				aa= new SmartTurnAvoidanceAlgorithm(state, intruder);
				break;
			case "None":
				aa= new AvoidanceAlgorithmAdapter();
				break;
			case "RIPNAvoidanceAlgorithm":
				aa= new RIPNAvoidanceAlgorithm(state, intruder);
				break;
			case "ORCAAvoidanceAlgorithm":
				aa= new ORCAAvoidanceAlgorithm(state, intruder);
				break;
			case "RVOAvoidanceAlgorithm":
				aa= new RVOAvoidanceAlgorithm(state, intruder);
				break;
			default:
				aa= new AvoidanceAlgorithmAdapter();
		}
		Sensor sensor = new SimpleSensor();
		intruder.init(sensor, aa);
		
		
		state.uasBag.add(intruder);
		state.obstacles.add(intruder);		
		state.allEntities.add(intruderDestination);
		state.allEntities.add(intruder);
		intruder.setSchedulable(true);
		state.toSchedule.add(intruder);
		
	}

}
