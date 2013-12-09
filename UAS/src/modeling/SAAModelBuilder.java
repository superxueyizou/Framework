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
import modeling.subsystems.avoidance.RIPNAvoidanceAlgorithm;
import modeling.subsystems.avoidance.ORCAAvoidanceAlgorithm;
import modeling.subsystems.avoidance.RVOAvoidanceAlgorithm;
import modeling.subsystems.avoidance.SimpleAvoidanceAlgorithm;
import modeling.subsystems.avoidance.SmartTurnAvoidanceAlgorithm;
import modeling.subsystems.avoidance.TurnRightAvoidanceAlgorithm;
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
	//private int noUAS=3;
	
		
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
	
	public  void generateSimulation(int noObstacles, int noUAS)
	{		
		
		double x =0;
		double y =0;		
		
		if (CONFIGURATION.staticAvoidance)
		{
			for (int i = 0; i < noObstacles; i++)
			{
				x = state.random.nextDouble() * worldXVal;
				y = state.random.nextDouble() * worldYVal;
				CircleObstacle cb = new CircleObstacle(state.getNewID(), state.random.nextInt(9) + 1);
				cb.setLocation(new Double2D(x,y));
				cb.setSchedulable(false);
				state.allEntities.add(cb);
				state.obstacles.add(cb);
				//System.out.println("obstacle " + Integer.toString(i) + " is at (" + Double.toString(x) + ", " + Double.toString(y) + "), ID is"+ ob.ID);
			}
			
			for(int i=1; i<=noUAS; i++)
			{
				do
				{
					x=state.random.nextDouble() * worldXVal;
					y=state.random.nextDouble() * worldYVal;
				}  while (state.obstacleAtPoint(new Double2D(x,y), state.obstacles));
				
				Destination d = generateDestination(state.obstacles);
				UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed, CONFIGURATION.selfMaxAcceleration, CONFIGURATION.selfMaxDeceleration, CONFIGURATION.selfMaxTurning);
				UAS self = new UAS(state.getNewID(), d, uasPerformance, 1.667);	
				self.setLocation(new Double2D(x,y));
				self.setSpeed(CONFIGURATION.selfSpeed);
				self.setViewingRange(CONFIGURATION.selfViewingRange);
				self.setViewingAngle(CONFIGURATION.selfViewingAngle);
				AvoidanceAlgorithm aa = new SimpleAvoidanceAlgorithm(state, self);
				Sensor sensor = new SimpleSensor();
				self.init(sensor, aa);
				self.setBearing(CALCULATION.calculateAngle(self.getLocation(), d.getLocation()));
				System.out.println("self's bearing:"+self.getBearing());
				
				state.uasBag.add(self);
				state.allEntities.add(self);
				self.setSchedulable(true);
				state.toSchedule.add(self);
				state.obstacles.add(self);
				//System.out.println("uas is at (" + Double.toString(x) + ", " + Double.toString(y) + ")ID is"+ uas.ID);
				//System.out.println("COModelBuilder.genereteSimulation is called, uas's max speed is: " + state.uasStats.getMaxSpeed());
			}
		}		
		else if (CONFIGURATION.dynamicAvoidance)
		{
			x = 1/3.0*worldXVal; 
			y = 0.5*worldYVal;
			
			Destination d = new Destination(state.getNewID(), null);
			d.setLocation(new Double2D(x+40,y-20));
			d.setSchedulable(false);
			state.allEntities.add(d);
			
			UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfMaxSpeed, CONFIGURATION.selfMaxAcceleration, CONFIGURATION.selfMaxDeceleration, CONFIGURATION.selfMaxTurning);
			UAS self = new UAS(state.getNewID(), d, uasPerformance, CONFIGURATION.selfSafetyRadius);	
			self.setLocation(new Double2D(x,y));
			self.setSpeed(CONFIGURATION.selfSpeed);
			self.setViewingRange(CONFIGURATION.selfViewingRange);
			self.setViewingAngle(CONFIGURATION.selfViewingAngle);
			//self.setSafetyRadius(CONFIGURATION.selfSafetyRadius);
			AvoidanceAlgorithm aa;
			switch(CONFIGURATION.avoidanceAlgorithmSelection)
			{
				case "TurnRightAvoidanceAlgorithm":
					aa= new TurnRightAvoidanceAlgorithm(state, self);
					break;
				case "SmartTurnAvoidanceAlgorithm":
					aa= new SmartTurnAvoidanceAlgorithm(state, self);
					break;
				case "None":
					aa= new AvoidanceAlgorithmAdapter();
					break;
				case "RIPNAvoidanceAlgorithm":
					aa= new RIPNAvoidanceAlgorithm(state, self);
					break;
				case "ORCAAvoidanceAlgorithm":
					aa= new ORCAAvoidanceAlgorithm(state, self);
					break;
				case "RVOAvoidanceAlgorithm":
					aa= new RVOAvoidanceAlgorithm(state, self);
					break;
				case "HRVOAvoidanceAlgorithm":
					aa= new HRVOAvoidanceAlgorithm(state, self);
					break;
				default:
					aa= new AvoidanceAlgorithmAdapter();
			}
			Sensor sensor = new SimpleSensor();
			self.init(sensor, aa);
			self.setBearing(CALCULATION.calculateAngle(self.getLocation(), d.getLocation()));
			
			state.uasBag.add(self);
			state.obstacles.add(self);
			state.allEntities.add(self);
			self.setSchedulable(true);
			state.toSchedule.add(self);
			//System.out.println("self's bearing:"+self.getBearing());
						
			
		    if(CONFIGURATION.headOnSelected)
		    {
		    	if(state.runningWithUI)
		    	{
		    		double offset = 0;
			    	boolean isRightSide = true;
			    	double speedCoefficient=1;
			    	for(int i=0; i<CONFIGURATION.headOnTimes; i++)
			    	{
			    		new HeadOnGenerator(state, self, offset, isRightSide, speedCoefficient*CONFIGURATION.headOnSpeed).execute();
			    		offset = offset + 2;
			    		isRightSide = !isRightSide;
			    		speedCoefficient += 0.2;
			    	}
		    	}
		    	else
		    	{
		    		double offset = CONFIGURATION.headOnOffset ;
			    	boolean isRightSide = CONFIGURATION.headOnIsRightSide;
			    	double speed= CONFIGURATION.headOnSpeed;
			    	new HeadOnGenerator(state, self, offset, isRightSide, speed).execute();
			    		
		    	}
		    
		    }
		    if(CONFIGURATION.crossingSelected)
		    {
		       	if(state.runningWithUI)
		    	{
		       		double distance = 30;
			    	double encouterAngle = 180;
			    	double speedCoefficient=1;
			    	for(int i=0; i<CONFIGURATION.crossingTimes; i++)
			    	{
			    		new CrossingGenerator(state, self, distance, encouterAngle,speedCoefficient*CONFIGURATION.crossingSpeed).execute();
			    		distance = distance+10;
			    		encouterAngle = encouterAngle-20;
			    		speedCoefficient += 0.2;
			    	}
		    	}
		    	else
		    	{
		    		double distance = CONFIGURATION.crossingDistance;
		    		double encounterAngle = CONFIGURATION.crossingEncounterAngle;
		    		double speed= CONFIGURATION.crossingSpeed;
		    		new CrossingGenerator(state, self, distance, encounterAngle,speed).execute();
		    		
		    	}
		    	
		    }
		    if(CONFIGURATION.tailApproachSelected)
		    {
		     	if(state.runningWithUI)
		    	{
		     		double offset = 3;
			    	boolean isRightSide = true;
			    	double speedCoefficient=1.2;
			    	for(int i=0; i<CONFIGURATION.tailApproachTimes; i++)
			    	{
			    		new TailApproachGenerator(state, self, offset, isRightSide, speedCoefficient*CONFIGURATION.tailApproachSpeed).execute();
			    		offset = offset + 2;
			    		isRightSide = !isRightSide;
			    		speedCoefficient += 0.2;
			    	}
		    	}
		    	else
		    	{
		    		double offset = CONFIGURATION.tailApproachOffset;
			    	boolean isRightSide = CONFIGURATION.tailApproachIsRightSide;
			    	double speed= CONFIGURATION.tailApproachSpeed;
			    	new TailApproachGenerator(state, self, offset, isRightSide, speed).execute();
		    	}
		    	
		    }
		
		    for(Object o : state.uasBag)
		    {
		    	UAS uas = (UAS)o;
		    	uas.getAa().init();		    	
		    }
			
		
			
			//System.out.println("uas is at (" + Double.toString(x) + ", " + Double.toString(y) + ")ID is"+ uas.ID);
			//System.out.println("COModelBuilder.genereteSimulation is called, uas's max speed is: " + state.uasStats.getMaxSpeed());
		}
		else
		{
			System.out.println("Please select one of the avoidances, static or dynamic?");
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
	
	public Destination generateDestination(Bag obstacles)
	{
		int dID = state.getNewID();
		Destination d = new Destination(dID, null);
		double x, y;
		do
		{
			x = state.random.nextDouble() * worldXVal;
			y = state.random.nextDouble() * worldYVal;
		}  while (state.obstacleAtPoint(new Double2D(x,y), obstacles));
		
		d.setLocation(new Double2D(x,y));
		d.isSchedulable = false;
		state.allEntities.add(d);
		return d;
	}

	
}
