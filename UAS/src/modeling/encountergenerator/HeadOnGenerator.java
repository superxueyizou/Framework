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
public class HeadOnGenerator extends EncounterGenerator {

	/**
	 * 
	 */
	private UAS intruder;
	private COModel state;
	private UAS self;
	private double offset;
	private double intruderSpeed;
	private int sideFactor;
	
	
	public HeadOnGenerator(COModel state, UAS uas, double offset, boolean isRightSide, double intruderSpeed) {
		// TODO Auto-generated constructor stub
		this.state=state;
		this.self=uas;
		this.offset=offset;
		this.intruderSpeed = intruderSpeed;
		this.sideFactor = isRightSide? +1:-1;
		
	}
	
	public void execute()
	{
		double middleX = (self.getLocation().x + self.getDestination().getLocation().x)/2;
		double middleY = (self.getLocation().y + self.getDestination().getLocation().y)/2;
		double encounterTime = (self.getLocation().distance(new Double2D(middleX, middleY))/ self.getSpeed());
		double intruderHalf = encounterTime *intruderSpeed;
		
		Destination intruderDestination = new Destination(state.getNewID(), null);
		double tMX = middleX + sideFactor*offset* Math.sin(Math.toRadians(self.getBearing())); 
		double tMY = middleY + sideFactor*offset* Math.cos(Math.toRadians(self.getBearing()));
		
		double tX = tMX - intruderHalf* Math.cos(Math.toRadians(self.getBearing()));// intruderDestination's x
		double tY = tMY + intruderHalf* Math.sin(Math.toRadians(self.getBearing()));// intruderDestination's y
		intruderDestination.setLocation(new Double2D(tX , tY));
		
		UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed, CONFIGURATION.headOnMaxAcceleration, CONFIGURATION.headOnMaxDeceleration, CONFIGURATION.headOnMaxTurning);
		intruder = new UAS(state.getNewID(), intruderDestination, uasPerformance, CONFIGURATION.headOnSafetyRadius);
		double x = tMX + intruderHalf* Math.cos(Math.toRadians(self.getBearing())); // intruder's x
		double y = tMY - intruderHalf* Math.sin(Math.toRadians(self.getBearing())); // intruder's y
		intruder.setLocation(new Double2D(x,y));
		intruder.setBearing(CALCULATION.correctAngle(self.getBearing()+180));
		intruder.setSpeed(intruderSpeed);
		
		//AvoidanceAlgorithm aa = new RIPNAvoidanceAlgorithm(state, intruder);
		AvoidanceAlgorithm aa;
		switch(CONFIGURATION.headOnAvoidanceAlgorithmSelection)
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
		
		state.uasBag.add(intruder);
		state.obstacles.add(intruder);		
		state.allEntities.add(intruderDestination);
		state.allEntities.add(intruder);
		intruder.setSchedulable(true);
		state.toSchedule.add(intruder);
				
		
		
	}

}
