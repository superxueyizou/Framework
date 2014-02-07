package modeling;

import modeling.encountergenerator.CrossingGenerator;
import modeling.encountergenerator.HeadOnGenerator;
import modeling.encountergenerator.SelfGenerator;
import modeling.encountergenerator.TailApproachGenerator;
import modeling.env.Destination;
import modeling.saa.collsionavoidance.AVO;
import modeling.saa.collsionavoidance.CollisionAvoidanceAlgorithm;
import modeling.saa.collsionavoidance.CollisionAvoidanceAlgorithmAdapter;
import modeling.saa.selfseparation.SVOSep;
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
	public  SAAModel state;
	
	public static double fieldXVal = CONFIGURATION.fieldXVal; 
	public static double fieldYVal = CONFIGURATION.fieldYVal;

	
	public SAAModelBuilder(SAAModel simState)
	{
		state = simState;
		System.out.println("COModelBuilder(COModel simState) is being called!!!!!!!!!! the simstate is：" + state.toString());
	}
	
	public  void generateSimulation()
	{	
		UAS self;
		if(CONFIGURATION.tailApproachSelected)
		{
			double uasX= fieldXVal/9;
	    	double uasY= fieldYVal/2;
	    	self = new SelfGenerator(state,uasX, uasY, CONFIGURATION.selfDestDist, CONFIGURATION.selfDestAngle).execute();
	    	
		}
		else
		{
			double uasX= fieldXVal/10;
	    	double uasY= fieldYVal/2;
	    	self = new SelfGenerator(state,uasX, uasY, CONFIGURATION.selfDestDist, CONFIGURATION.selfDestAngle).execute();
	    				
		}
	
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

	


	
}
