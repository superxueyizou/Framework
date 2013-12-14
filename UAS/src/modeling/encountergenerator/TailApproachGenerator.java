/**
 * 
 */
package modeling.encountergenerator;

import sim.util.Double2D;
import tools.CALCULATION;
import tools.CONFIGURATION;
import modeling.AvoidParas;
import modeling.SAAModel;
import modeling.Destination;
import modeling.SenseParas;
import modeling.UAS;
import modeling.UASPerformance;
import modeling.UASVelocity;
import modeling.subsystems.avoidance.AvoidanceAlgorithm;
import modeling.subsystems.avoidance.AvoidanceAlgorithmAdapter;
import modeling.subsystems.avoidance.HRVOAvoidanceAlgorithm;
import modeling.subsystems.avoidance.ORCAAvoidanceAlgorithm;
import modeling.subsystems.avoidance.RVOAvoidanceAlgorithm;
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
		Double2D selfMiddle = self.getLocation().add(self.getDestination().getLocation()).multiply(0.5);
		Double2D selfVector = self.getDestination().getLocation().subtract(self.getLocation());
		Double2D intruderVector = selfVector.multiply(intruderSpeed/self.getSpeed());
		Double2D offsetVector = selfVector.rotate(0.5*sideFactor*Math.PI).resize(offset);
		Double2D intruderMiddle = selfMiddle.add(offsetVector);
				
		Double2D intruderLocation = intruderMiddle.subtract(intruderVector.multiply(0.5));
		Double2D intruderDestinationLoc = intruderMiddle.add(intruderVector.multiply(0.5));
		Destination intruderDestination = new Destination(state.getNewID(), null);
		intruderDestination.setLocation(intruderDestinationLoc);
		
		UASVelocity intruderVelocity = new UASVelocity(intruderDestination.getLocation().subtract(intruderLocation).normalize().multiply(intruderSpeed));
		UASPerformance intruderPerformance = new UASPerformance(CONFIGURATION.tailApproachMaxSpeed, CONFIGURATION.tailApproachMaxAcceleration, CONFIGURATION.tailApproachMaxDeceleration, CONFIGURATION.tailApproachMaxTurning);
		SenseParas intruderSenseParas = new SenseParas(CONFIGURATION.tailApproachViewingRange,CONFIGURATION.tailApproachViewingAngle, CONFIGURATION.tailApproachSensitivityForCollisions);
		AvoidParas intruderAvoidParas = new AvoidParas(CONFIGURATION.tailApproachAlpha);
		
		UAS intruder = new UAS(state.getNewID(),CONFIGURATION.tailApproachSafetyRadius,intruderLocation, intruderDestination, intruderVelocity,intruderPerformance, intruderSenseParas,intruderAvoidParas);
		
		
		AvoidanceAlgorithm aa;
		switch(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection)
		{
//			case "TurnRightAvoidanceAlgorithm":
//				aa= new TurnRightAvoidanceAlgorithm(state, intruder);
//				break;
//			case "SmartTurnAvoidanceAlgorithm":
//				aa= new SmartTurnAvoidanceAlgorithm(state, intruder);
//				break;
//			case "RIPNAvoidanceAlgorithm":
//				aa= new RIPNAvoidanceAlgorithm(state, intruder);
//				break;
			case "ORCAAvoidanceAlgorithm":
				aa= new ORCAAvoidanceAlgorithm(state, intruder);
				break;
			case "RVOAvoidanceAlgorithm":
				aa= new RVOAvoidanceAlgorithm(state, intruder);
				break;
			case "HRVOAvoidanceAlgorithm":
				aa= new HRVOAvoidanceAlgorithm(state, intruder);
				break;
			case "None":
				aa= new AvoidanceAlgorithmAdapter(state, intruder);
				break;
			default:
				aa= new AvoidanceAlgorithmAdapter(state, intruder);
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
