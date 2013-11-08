/**
 * 
 */
package modeling.encountergenerator;

import sim.util.Double2D;
import tools.CALCULATION;
import tools.CONFIGURATION;
import modeling.COModel;
import modeling.Destination;
import modeling.UAS;
import modeling.UASPerformance;
import modeling.subsystems.avoidance.AvoidanceAlgorithm;
import modeling.subsystems.avoidance.AvoidanceAlgorithmAdapter;
import modeling.subsystems.avoidance.RIPNAvoidanceAlgorithm;
import modeling.subsystems.avoidance.SimpleAvoidanceAlgorithm;
import modeling.subsystems.avoidance.SmartTurnAvoidanceAlgorithm;
import modeling.subsystems.avoidance.TurnRightAvoidanceAlgorithm;
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
	private UAS intruder;
	private COModel state;
	private UAS self;
	private double distance;
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
	public CrossingGenerator(COModel state, UAS uas, double distance, double encounterAngle, double intruderSpeed) {
		// TODO Auto-generated constructor stub
		this.state=state;
		this.self=uas;
		this.distance=distance;
		this.encounterAngle = encounterAngle;
		this.intruderSpeed = intruderSpeed;
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
		double tX = middleX - intruderHalf* Math.cos(Math.toRadians(CALCULATION.correctAngle(180-encounterAngle))); // intruderDestination's x
		double tY = middleY + intruderHalf* Math.sin(Math.toRadians(CALCULATION.correctAngle(180-encounterAngle))); // intruderDestination's y
		intruderDestination.setLocation(new Double2D(tX , tY));
		
		UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed, CONFIGURATION.selfMaxAcceleration, CONFIGURATION.selfMaxDeceleration, CONFIGURATION.selfMaxTurning);
		intruder = new UAS(state.getNewID(), intruderDestination, uasPerformance);
		double x = middleX + intruderHalf* Math.cos(Math.toRadians(CALCULATION.correctAngle(180-encounterAngle))); // intruder's x
		double y = middleY - intruderHalf* Math.sin(Math.toRadians(CALCULATION.correctAngle(180-encounterAngle))); // intruder's y
		intruder.setLocation(new Double2D(x,y));
		intruder.setBearing(CALCULATION.correctAngle(CALCULATION.calculateAngle(new Double2D(x,y), new Double2D(tX,tY))));
		intruder.setSpeed(intruderSpeed);
		
		AvoidanceAlgorithm aa;
		switch(CONFIGURATION.crossingAvoidanceAlgorithmSelection)
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
			default:
				aa= new RIPNAvoidanceAlgorithm(state, intruder);
		}
		Sensor sensor = new SimpleSensor();
		intruder.init(sensor, aa);
		intruder.setSchedulable(true);
		System.out.println("intruder:"+intruder);
				
		state.allEntities.add(intruderDestination);
		state.allEntities.add(intruder);
		state.toSchedule.add(intruder);
		state.uasBag.add(intruder);
		state.obstacles.add(intruder);		
	}

}
