/**
 * 
 */
package modeling.encountergenerator;

import sim.util.Double2D;
import tools.CONFIGURATION;
import modeling.AvoidParas;
import modeling.SAAModel;
import modeling.Destination;
import modeling.SenseParas;
import modeling.UAS;
import modeling.UASPerformance;
import modeling.UASVelocity;
import modeling.subsystems.avoidance.AVOAvoidanceAlgorithm;
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
public class HeadOnGenerator extends EncounterGenerator {

	/**
	 * 
	 */
	private SAAModel state;
	
	private UAS self;
	
	private double offset;
	private int sideFactor;
	private double intruderSpeed;
		
	
	public HeadOnGenerator(SAAModel state, UAS uas, double offset, boolean isRightSide, double intruderSpeed) 
	{		
		this.state=state;
		this.self=uas;
		this.offset=offset;
		this.intruderSpeed = intruderSpeed;
		this.sideFactor = isRightSide? +1:-1;
		
	}
	
	public void execute()
	{
		Double2D selfMiddle = self.getLocation().add(self.getDestination().getLocation()).multiply(0.5);
		Double2D selfVector = self.getDestination().getLocation().subtract(self.getLocation());
		Double2D intruderVector = selfVector.negate().multiply(intruderSpeed/self.getSpeed());
		Double2D offsetVector = selfVector.rotate(0.5*sideFactor*Math.PI).resize(offset);
		Double2D intruderMiddle = selfMiddle.add(offsetVector);
				
		Double2D intruderLocation = intruderMiddle.subtract(intruderVector.multiply(0.5));
		Double2D intruderDestinationLoc = intruderMiddle.add(intruderVector.multiply(0.5));
		Destination intruderDestination = new Destination(state.getNewID(), null);
		intruderDestination.setLocation(intruderDestinationLoc);
		
		UASVelocity intruderVelocity = new UASVelocity(intruderVector.resize(intruderSpeed));
		UASPerformance intruderPerformance = new UASPerformance(CONFIGURATION.headOnMaxSpeed, CONFIGURATION.headOnMaxAcceleration, CONFIGURATION.headOnMaxDeceleration, CONFIGURATION.headOnMaxTurning);
		SenseParas intruderSenseParas = new SenseParas(CONFIGURATION.headOnViewingRange,CONFIGURATION.headOnViewingAngle, CONFIGURATION.headOnSensitivityForCollisions);
		AvoidParas intruderAvoidParas = new AvoidParas(CONFIGURATION.headOnAlpha);
		
		UAS intruder = new UAS(state.getNewID(),CONFIGURATION.headOnSafetyRadius,intruderLocation, intruderDestination, intruderVelocity,intruderPerformance, intruderSenseParas,intruderAvoidParas);
		
		AvoidanceAlgorithm aa;
		switch(CONFIGURATION.headOnAvoidanceAlgorithmSelection)
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
			case "AVOAvoidanceAlgorithm":
				aa= new AVOAvoidanceAlgorithm(state, intruder);
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
