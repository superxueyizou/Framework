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
public class CrossingGenerator extends EncounterGenerator {

	/**
	 * 
	 */
	private SAAModel state;
	private UAS self;
	private double sideFactor;
	private double encounterAngle;
	double intruderSpeed;
	/************
	 * 
	 * @param state
	 * @param uas
	 * @param distance
	 * @param encounterAngle It's ABSOLUTE value is supposed to belong to (0,180). 0 -- tail approach, 180 -- head on
	 * @param intruderSpeed
	 */
	public CrossingGenerator(SAAModel state, UAS uas, double encounterAngle,boolean isRightSide, double intruderSpeed) 
	{
		this.state=state;
		this.self=uas;
		this.sideFactor = isRightSide ? +1:-1; 
		this.encounterAngle = encounterAngle;
		this.intruderSpeed = intruderSpeed;
	}
	
	/*********
	 * self and intruder will encounter at the middle point of each's journey
	 */
	public void execute()
	{
		
		Double2D selfMiddle = self.getLocation().add(self.getDestination().getLocation()).multiply(0.5);
		Double2D selfVector = self.getDestination().getLocation().subtract(self.getLocation());
		Double2D intruderVector = selfVector.rotate(-sideFactor*encounterAngle).multiply(intruderSpeed/self.getSpeed());
		Double2D intruderMiddle = selfMiddle;
				
		Double2D intruderLocation = intruderMiddle.subtract(intruderVector.multiply(0.5));
		Double2D intruderDestinationLoc = intruderMiddle.add(intruderVector.multiply(0.5));
		Destination intruderDestination = new Destination(state.getNewID(), null);
		intruderDestination.setLocation(intruderDestinationLoc);
		UASVelocity intruderVelocity = new UASVelocity(intruderDestination.getLocation().subtract(intruderLocation).normalize().multiply(intruderSpeed));
		UASPerformance intruderPerformance = new UASPerformance(CONFIGURATION.crossingMaxSpeed, CONFIGURATION.crossingMaxAcceleration, CONFIGURATION.crossingMaxDeceleration, CONFIGURATION.crossingMaxTurning);
		SenseParas intruderSenseParas = new SenseParas(CONFIGURATION.crossingViewingRange,CONFIGURATION.crossingViewingAngle, CONFIGURATION.crossingSensitivityForCollisions);
		AvoidParas intruderAvoidParas = new AvoidParas(CONFIGURATION.crossingAlpha);
		
		UAS intruder = new UAS(state.getNewID(),CONFIGURATION.crossingSafetyRadius,intruderLocation, intruderDestination, intruderVelocity,intruderPerformance, intruderSenseParas,intruderAvoidParas);
		
		AvoidanceAlgorithm aa;
		switch(CONFIGURATION.crossingAvoidanceAlgorithmSelection)
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
