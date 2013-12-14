package modeling;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import modeling.encountergenerator.CrossingGenerator;
import modeling.encountergenerator.HeadOnGenerator;
import modeling.encountergenerator.TailApproachGenerator;
import modeling.subsystems.avoidance.AvoidanceAlgorithm;
import modeling.subsystems.avoidance.AvoidanceAlgorithmAdapter;
import modeling.subsystems.avoidance.HRVOAvoidanceAlgorithm;
import modeling.subsystems.avoidance.ORCAAvoidanceAlgorithm;
import modeling.subsystems.avoidance.RVOAvoidanceAlgorithm;
import modeling.subsystems.sensor.Sensor;
import modeling.subsystems.sensor.SimpleSensor;

import sim.util.*;
import tools.CALCULATION;
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
	
	public static double worldXVal = 150;
	public static double worldYVal = 105;
	
		
//	public COModelBuilder()
//	{
//		System.out.println("COModelBuilder() is being called!!!!!!!!!! the simstate is：" + state.toString());
//		setUpSim((Calendar.SECOND * 1000)+ Calendar.MILLISECOND);	
//	}
	
	
	public SAAModelBuilder(SAAModel simState)
	{
		state = simState;
		System.out.println("COModelBuilder(COModel simState) is being called!!!!!!!!!! the simstate is：" + state.toString());
	}
	
	public  void generateSimulation()
	{		
		
		double x = worldXVal/3.0; ;
		double y = worldYVal/2.0;		

		Double2D location = new Double2D(x,y);
		Destination d = generateDestination(x, y, CONFIGURATION.selfDestDist, CONFIGURATION.selfDestAngle,state.obstacles);
		UASVelocity uasVelocity = new UASVelocity(d.getLocation().subtract(location).normalize().multiply(CONFIGURATION.selfSpeed));
		UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed, CONFIGURATION.selfMaxAcceleration, CONFIGURATION.selfMaxDeceleration, CONFIGURATION.selfMaxTurning);
		SenseParas senseParas = new SenseParas(CONFIGURATION.selfViewingRange,CONFIGURATION.selfViewingAngle, CONFIGURATION.selfSensitivityForCollisions);
		AvoidParas avoidParas = new AvoidParas(CONFIGURATION.selfAlpha);
		
		UAS self = new UAS(state.getNewID(),CONFIGURATION.selfSafetyRadius,location, d, uasVelocity,uasPerformance, senseParas,avoidParas);
				
		AvoidanceAlgorithm aa;
		switch(CONFIGURATION.avoidanceAlgorithmSelection)
		{
//			case "TurnRightAvoidanceAlgorithm":
//				aa= new TurnRightAvoidanceAlgorithm(state, self);
//				break;
//			case "SmartTurnAvoidanceAlgorithm":
//				aa= new SmartTurnAvoidanceAlgorithm(state, self);
//				break;
//			
//			case "RIPNAvoidanceAlgorithm":
//				aa= new RIPNAvoidanceAlgorithm(state, self);
//				break;
			case "ORCAAvoidanceAlgorithm":
				aa= new ORCAAvoidanceAlgorithm(state, self);
				break;
			case "RVOAvoidanceAlgorithm":
				aa= new RVOAvoidanceAlgorithm(state, self);
				break;
			case "HRVOAvoidanceAlgorithm":
				aa= new HRVOAvoidanceAlgorithm(state, self);
				break;
			case "None":
				aa= new AvoidanceAlgorithmAdapter(state, self);
				break;
			default:
				aa= new AvoidanceAlgorithmAdapter(state, self);
		}
		Sensor sensor = new SimpleSensor();
		self.init(sensor, aa);
				
		state.uasBag.add(self);
		state.obstacles.add(self);
		state.allEntities.add(self);
		self.setSchedulable(true);
		state.toSchedule.add(self);
		//System.out.println("self's bearing:"+self.getBearing());
					
		
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
	    	uas.getAa().init();		    	
	    }
				
		System.out.println("Simulation stepping begins!");
		System.out.println("====================================================================================================");
		
			
	}
	
	

	/**
	 * 
	 * @param args
	 * @return 
	 */
	public static String[] addEndTime(String[] args)
	{
		String[] x = new String[args.length + 2];
		
		if (args.length != 0)
		{
			x = args;
		}
		
		x[args.length] = "-for";
		x[args.length + 1] = simLength;
		
		return x;
	}
	
	
	public SAAModel getSim() {return state;}
	
	public Destination generateDestination(double uasX, double uasY, double distance, double angle,Bag obstacles)
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
		d.isSchedulable = false;
		state.allEntities.add(d);
		return d;
	}

	
}
