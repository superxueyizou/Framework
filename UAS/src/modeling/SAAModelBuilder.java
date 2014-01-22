package modeling;

import modeling.encountergenerator.CrossingGenerator;
import modeling.encountergenerator.HeadOnGenerator;
import modeling.encountergenerator.TailApproachGenerator;
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


import sim.util.*;
import tools.CONFIGURATION;
/**
 *
 * @author Robert Lee
 * This class is used to build/initiate the simulation.
 * Called for by simulationWithUI.class
 */
public class SAAModelBuilder
{
	private static String simLength = "1000";
	
	public  SAAModel state;
	
	public static double worldXVal = 150; //150,000m
	public static double worldYVal = 105; //105,000m

	
	public SAAModelBuilder(SAAModel simState)
	{
		state = simState;
		System.out.println("COModelBuilder(COModel simState) is being called!!!!!!!!!! the simstate is：" + state.toString());
	}
	
	public  void generateSimulation()
	{		
		
		double x = worldXVal/2.0; ;
		double y = worldYVal/2.0;		

		Double2D location = new Double2D(x,y);
		Destination d = generateDestination(x, y, CONFIGURATION.selfDestDist, CONFIGURATION.selfDestAngle,state.obstacles);
		UASVelocity uasVelocity = new UASVelocity(d.getLocation().subtract(location).normalize().multiply(CONFIGURATION.selfSpeed));
		UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed, CONFIGURATION.selfMaxAcceleration, CONFIGURATION.selfMaxDeceleration, CONFIGURATION.selfMaxTurning);
		SenseParas senseParas = new SenseParas(CONFIGURATION.selfViewingRange,CONFIGURATION.selfViewingAngle, CONFIGURATION.selfSensitivityForCollisions);
		AvoidParas avoidParas = new AvoidParas(CONFIGURATION.selfAlpha);
		
		UAS self = new UAS(state.getNewID(),CONFIGURATION.selfSafetyRadius,location, d, uasVelocity,uasPerformance, senseParas,avoidParas);
		
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
		
		
	    if(CONFIGURATION.headOnSelected)
	    {
	    	double offset = CONFIGURATION.headOnOffset ;
	    	boolean isRightSide = CONFIGURATION.headOnIsRightSide;
	    	double speed= CONFIGURATION.headOnSpeed;
	    	
	    	if(state.runningWithUI)
	    	{
		    	for(int i=0; i<CONFIGURATION.headOnTimes; i++)
		    	{
		    		new HeadOnGenerator(state, self, offset, isRightSide, speed).execute();
		    		offset += 2;
		    		isRightSide = !isRightSide;
		    		speed += 0.2*speed;
		    	}
	    	}
	    	else
	    	{
	    	  	new HeadOnGenerator(state, self, offset, isRightSide, speed).execute();
		    		
	    	}
	    
	    }
	    if(CONFIGURATION.crossingSelected)
	    {
    		double encounterAngle = CONFIGURATION.crossingEncounterAngle;
    		boolean isRightSide = CONFIGURATION.crossingIsRightSide;
    		double speed= CONFIGURATION.crossingSpeed;
	   
	       	if(state.runningWithUI)
	    	{
		    	for(int i=0; i<CONFIGURATION.crossingTimes; i++)
		    	{
		    		new CrossingGenerator(state, self, encounterAngle,isRightSide,speed).execute();
		    		isRightSide = !isRightSide;
		    		encounterAngle -= Math.toRadians(20);
		    		speed += 0.2*speed;
		    	}
	    	}
	    	else
	    	{
	    		new CrossingGenerator(state, self, encounterAngle,isRightSide,speed).execute();
	    	}
	    	
	    }
	    if(CONFIGURATION.tailApproachSelected)
	    {
	    	double offset = CONFIGURATION.tailApproachOffset;
	    	boolean isRightSide = CONFIGURATION.tailApproachIsRightSide;
	    	double speed= CONFIGURATION.tailApproachSpeed;
	     	if(state.runningWithUI)
	    	{	     		
		    	for(int i=0; i<CONFIGURATION.tailApproachTimes; i++)
		    	{
		    		new TailApproachGenerator(state, self, offset, isRightSide, speed).execute();
		    		offset += 2;
		    		isRightSide = !isRightSide;
		    		speed += 0.2*speed;
		    	}
	    	}
	    	else
	    	{	    		
		    	new TailApproachGenerator(state, self, offset, isRightSide, speed).execute();
	    	}
	    	
	    }
	
	    for(Object o : state.uasBag)
	    {
	    	UAS uas = (UAS)o;
	    	uas.getCaa().init();	
	    	uas.getSsa().init();
	    }
				
		System.out.println("Simulation stepping begins!");
		System.out.println("====================================================================================================");
		
			
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
