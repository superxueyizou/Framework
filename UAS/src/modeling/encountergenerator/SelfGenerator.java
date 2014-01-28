/**
 * 
 */
package modeling.encountergenerator;

import sim.util.Bag;
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
public class SelfGenerator
{

	/**
	 * 
	 */
	private SAAModel state;
	private double uasX;
	private double uasY;
	private double distance;
	private double angle;
		
	
	public SelfGenerator(SAAModel state,double uasX, double uasY, double distance, double angle) 
	{		
		this.state=state;
		this.uasX=uasX;
		this.uasY=uasY;
		this.distance=distance;
		this.angle = angle;
	
		
	}
	
	public UAS execute()
	{
		Double2D location = new Double2D(uasX,uasY);
		Destination d = generateDestination(uasX, uasY,distance,angle,state.obstacles);
		UASVelocity uasVelocity = new UASVelocity(d.getLocation().subtract(location).normalize().multiply(CONFIGURATION.selfSpeed));
		UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed, CONFIGURATION.selfMinSpeed,CONFIGURATION.selfMaxClimb, 
				CONFIGURATION.selfMaxDescent,CONFIGURATION.selfMaxTurning, CONFIGURATION.selfMaxAcceleration, CONFIGURATION.selfMaxDeceleration);
		SenseParas senseParas = new SenseParas(CONFIGURATION.selfViewingRange,CONFIGURATION.selfViewingAngle, CONFIGURATION.selfSensitivityForCollisions);
		AvoidParas avoidParas = new AvoidParas(CONFIGURATION.selfAlpha);
		
		UAS self = new UAS(state.getNewID(),CONFIGURATION.selfSafetyRadius,location, d, uasVelocity,uasPerformance, senseParas,avoidParas);
		self.setSource(location);
		Sensor sensor = new SimpleSensor();
		
		SelfSeparationAlgorithm ssa; 
		switch (CONFIGURATION.selfSelfSeparationAlgorithmSelection)
		{
			case "SVOAvoidanceAlgorithm":
				ssa= new SVO(state, self);
				break;
			case "None":
				ssa= new SelfSeparationAlgorithmAdapter(state, self);
				break;
			default:
				ssa= new SelfSeparationAlgorithmAdapter(state, self);
		
		}
		
		CollisionAvoidanceAlgorithm caa;
		switch(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection)
		{
			case "AVOAvoidanceAlgorithm":
				caa= new AVO(state, self);
				break;
			case "None":
				caa= new CollisionAvoidanceAlgorithmAdapter(state, self);
				break;
			default:
				caa= new CollisionAvoidanceAlgorithmAdapter(state, self);
		}
		
		
		
		self.init(sensor,ssa,caa);
				
		state.uasBag.add(self);
		state.obstacles.add(self);
		state.allEntities.add(self);
		self.setSchedulable(true);
		state.toSchedule.add(self);
		
		return self;
		
	}
	
	public Destination generateDestination(double uasX, double uasY, double distance, double angle, Bag obstacles)
	{
		int dID = state.getNewID();
		Destination d = new Destination(dID, null);
		double desX, desY;
		double delta=0;
		do
		{
			desX = uasX + (distance+delta)* Math.cos(angle);
			desY = uasY - (distance+delta)* Math.sin(angle);
			delta += 1.0;
		}  while (state.obstacleAtPoint(new Double2D(desX,desY), obstacles));
		
		d.setLocation(new Double2D(desX,desY));
		d.setSchedulable(false);
		state.allEntities.add(d);
		return d;
	}

}
