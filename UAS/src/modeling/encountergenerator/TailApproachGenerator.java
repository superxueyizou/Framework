/**
 * 
 */
package modeling.encountergenerator;

import sim.util.Double2D;
import tools.CONFIGURATION;
import modeling.SAAModel;
import modeling.env.Destination;
import modeling.saa.collsionavoidance.AVO;
import modeling.saa.collsionavoidance.CollisionAvoidanceAlgorithm;
import modeling.saa.collsionavoidance.CollisionAvoidanceAlgorithmAdapter;
import modeling.saa.selfseparation.SVO;
import modeling.saa.selfseparation.SelfSeparationAlgorithm;
import modeling.saa.selfseparation.SelfSeparationAlgorithmAdapter;
import modeling.saa.sense.Sensor;
import modeling.saa.sense.SimpleSensor;
import modeling.uas.AvoidParas;
import modeling.uas.SenseParas;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;
import modeling.uas.UASVelocity;


/**
 * @author Xueyi
 *
 */
public class TailApproachGenerator extends EncounterGenerator 
{

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
		
		Sensor sensor = new SimpleSensor();

		SelfSeparationAlgorithm ssa; 
		switch (CONFIGURATION.tailApproachSelfSeparationAlgorithmSelection)
		{
			case "SVOAvoidanceAlgorithm":
				ssa= new SVO(state, intruder);
				break;
			case "None":
				ssa= new SelfSeparationAlgorithmAdapter(state, intruder);
				break;
			default:
				ssa= new SelfSeparationAlgorithmAdapter(state, intruder);
		
		}
		
		CollisionAvoidanceAlgorithm caa;
		switch(CONFIGURATION.tailApproachCollisionAvoidanceAlgorithmSelection)
		{
		
			case "AVOAvoidanceAlgorithm":
				caa= new AVO(state, intruder);
				break;
			case "None":
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
				break;
//			case "TurnRightAvoidanceAlgorithm":
//				aa= new TurnRightAvoidanceAlgorithm(state, intruder);
//				break;
//			case "SmartTurnAvoidanceAlgorithm":
//				aa= new SmartTurnAvoidanceAlgorithm(state, intruder);
//				break;
//			case "RIPNAvoidanceAlgorithm":
//				aa= new RIPNAvoidanceAlgorithm(state, intruder);
//				break;
//			case "ORCAAvoidanceAlgorithm":
//				aa= new ORCAAvoidanceAlgorithm(state, intruder);
//				break;
//			case "RVOAvoidanceAlgorithm":
//				aa= new RVOAvoidanceAlgorithm(state, intruder);
//				break;
//			case "HRVOAvoidanceAlgorithm":
//				aa= new HRVOAvoidanceAlgorithm(state, intruder);
//				break;

			default:
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
		}

		intruder.init(sensor,ssa,caa);
		
		
		state.uasBag.add(intruder);
		state.obstacles.add(intruder);		
		state.allEntities.add(intruderDestination);
		state.allEntities.add(intruder);
		intruder.setSchedulable(true);
		state.toSchedule.add(intruder);
		
	}

}
