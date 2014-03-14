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
import modeling.saa.collsionavoidance.SVO;
import modeling.saa.selfseparation.SVOSep;
import modeling.saa.selfseparation.SelfSeparationAlgorithm;
import modeling.saa.selfseparation.SelfSeparationAlgorithmAdapter;
import modeling.saa.sense.ADS_B;
import modeling.saa.sense.EOIR;
import modeling.saa.sense.PerfectSensor;
import modeling.saa.sense.Radar;
import modeling.saa.sense.Sensor;
import modeling.saa.sense.SensorSet;
import modeling.saa.sense.SimpleSensor;
import modeling.saa.sense.TCAS;
import modeling.uas.AvoidParas;
import modeling.uas.SenseParas;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;
import modeling.uas.UASVelocity;

/**
 * @author Xueyi
 *
 */
public class HeadOnGenerator extends EncounterGenerator 
{

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
		UASPerformance intruderPerformance = new UASPerformance(CONFIGURATION.headOnMaxSpeed, CONFIGURATION.headOnMinSpeed, intruderSpeed, CONFIGURATION.headOnMaxClimb, CONFIGURATION.headOnMaxDescent,CONFIGURATION.headOnMaxTurning, CONFIGURATION.headOnMaxAcceleration, CONFIGURATION.headOnMaxDeceleration);
		SenseParas intruderSenseParas = new SenseParas(CONFIGURATION.headOnViewingRange,CONFIGURATION.headOnViewingAngle, CONFIGURATION.headOnSensitivityForCollisions);
		AvoidParas intruderAvoidParas = new AvoidParas(CONFIGURATION.headOnAlpha);
		
		UAS intruder = new UAS(state.getNewID(),CONFIGURATION.headOnSafetyRadius,intruderLocation, intruderDestination, intruderVelocity,intruderPerformance, intruderSenseParas,intruderAvoidParas);
		intruder.setSource(intruderLocation);
		
		SensorSet sensorSet = new SensorSet();
		if((CONFIGURATION.headOnSensorSelection&0B10000) == 0B10000)
		{
			sensorSet.perfectSensor=new PerfectSensor();
		}
		if((CONFIGURATION.headOnSensorSelection&0B01000) == 0B01000)
		{
			sensorSet.ads_b=new ADS_B();
		}
		if((CONFIGURATION.headOnSensorSelection&0B00100) == 0B00100)
		{
			sensorSet.tcas=new TCAS();
		}
		if((CONFIGURATION.headOnSensorSelection&0B00010) == 0B00010)
		{
			sensorSet.radar=new Radar();
		}
		if((CONFIGURATION.headOnSensorSelection&0B00001) == 0B00001)
		{
			sensorSet.eoir=new EOIR();
		}
		sensorSet.synthesize();
		
		SelfSeparationAlgorithm ssa; 
		switch (CONFIGURATION.headOnSelfSeparationAlgorithmSelection)
		{
			case "SVOAvoidanceAlgorithm":
				ssa= new SVOSep(state, intruder);
				break;
			case "None":
				ssa= new SelfSeparationAlgorithmAdapter(state, intruder);
				break;
			default:
				ssa= new SelfSeparationAlgorithmAdapter(state, intruder);
		
		}
		
		CollisionAvoidanceAlgorithm caa;
		switch(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection)
		{
		
			case "AVOAvoidanceAlgorithm":
				caa= new AVO(state, intruder);
				break;
			case "SVOAvoidanceAlgorithm":
				caa= new SVO(state, intruder);
				break;
			case "None":
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
				break;
			default:
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
		}

		intruder.init(sensorSet,ssa, caa);
		
		state.uasBag.add(intruder);
		state.obstacles.add(intruder);		
		state.allEntities.add(intruderDestination);
		state.allEntities.add(intruder);
		intruder.setSchedulable(true);
		state.toSchedule.add(intruder);
				
		
		
	}

}
