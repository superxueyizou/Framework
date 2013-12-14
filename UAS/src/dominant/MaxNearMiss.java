/**
 * 
 */
package dominant;

import tools.CONFIGURATION;
import modeling.SAAModel;
import modeling.SAAModelBuilder;
import modeling.UAS;
import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.DoubleVectorIndividual;

/**
 * @author Xueyi Zou
 *
 */
public class MaxNearMiss extends Problem implements SimpleProblemForm 
{

	/* (non-Javadoc)
	 * @see ec.simple.SimpleProblemForm#evaluate(ec.EvolutionState, ec.Individual, int, int)
	 */
	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) 
	{
		// TODO Auto-generated method stub
		if (ind.evaluated) return;

        if (!(ind instanceof DoubleVectorIndividual))
            state.output.fatal("Whoa!  It's not a DoubleVectorIndividual!!!",null);        
      
        DoubleVectorIndividual ind2 = (DoubleVectorIndividual)ind;
        double distanceToDangerSum=0;
        
        double destDist= ind2.genome[0];
        double destAngle= ind2.genome[1];
        
        boolean headOnSelected = (ind2.genome[2]>0)? true: false;
        double  headOnOffset = ind2.genome[3];
    	boolean headOnIsRightSide = (ind2.genome[4]>0)? true: false;		
		double  headOnSpeed = ind2.genome[5];
		
        boolean crossingSelected = (ind2.genome[6]>0)? true: false;
        double  crossingEncounterAngle = ind2.genome[7];
   		boolean crossingIsRightSide = (ind2.genome[8]>0)? true: false;		
		double  crossingSpeed = ind2.genome[9];
		
        boolean tailApproachSelected = (ind2.genome[10]>0)? true: false;    	
		double  tailApproachOffset = ind2.genome[11];
		boolean tailApproachIsRightSide = (ind2.genome[12]>0)? true: false;
		double  tailApproachSpeed = ind2.genome[13];
		
		int times =1, dividend=0;
        for(int i=0;i<times; i++)
        {
        	long time = System.nanoTime();
    		SAAModel simState= new SAAModel(time, 150, 100, false); 	
    		
    		CONFIGURATION.selfDestDist = destDist;
    		CONFIGURATION.selfDestAngle = destAngle;
    		
    		CONFIGURATION.headOnSelected = headOnSelected;
    		CONFIGURATION.headOnOffset=headOnOffset;
    		CONFIGURATION.headOnIsRightSide= headOnIsRightSide;    		
    		CONFIGURATION.headOnSpeed =headOnSpeed;
    		
    		CONFIGURATION.crossingSelected = crossingSelected;
    		CONFIGURATION.crossingEncounterAngle=crossingEncounterAngle;
    		CONFIGURATION.crossingIsRightSide= crossingIsRightSide;    		
    		CONFIGURATION.crossingSpeed =crossingSpeed;
    		
    		CONFIGURATION.tailApproachSelected = tailApproachSelected;
    		CONFIGURATION.tailApproachOffset= tailApproachOffset;
    		CONFIGURATION.tailApproachIsRightSide=tailApproachIsRightSide;
    		CONFIGURATION.tailApproachSpeed =tailApproachSpeed;
    		
 	   		SAAModelBuilder sBuilder = new SAAModelBuilder(simState);
    		sBuilder.generateSimulation();
    		simState.start();		
    		do
    		{
    			if (!simState.schedule.step(simState))
    			{
    				break;
    			}
    		} while(simState.schedule.getSteps()< 1000);
    		
    		for(int j=0; j<simState.getUasBag().size(); j++)
    		{
    			UAS uas = (UAS)simState.getUasBag().get(j);
    			System.out.println(uas.getDistanceToDanger()-uas.getRadius());
    			distanceToDangerSum += uas.getDistanceToDanger()-uas.getRadius();
    			
//    			if(uas.getDistanceToDanger()-uas.getRadius()>0)
//				{
//    				distanceToDangerSum += uas.getDistanceToDanger()-uas.getRadius();
//				}
//    			else
//    			{
//    				distanceToDangerSum += 0;
//    			}
    			
    			dividend++;
    		}
    		
        }
        
		float rawFitness= (float)distanceToDangerSum/dividend;  
		float fitness = 1/Math.abs(1+rawFitness);
        
        
        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        ((SimpleFitness)ind2.fitness).setFitness(   state,            
										            fitness,/// ...the fitness...
										            fitness >= 1);///... is the individual ideal?  Indicate here...
        
        ind2.evaluated = true;
        System.out.println("individual result: selfDestDist("+destDist+ "), selfDestAngle("+destAngle+ "), isRightSide("+headOnIsRightSide+"), offset("+ headOnOffset+"), speed("+ headOnSpeed + "); fitness[[ " + fitness +" ]]" );
        System.out.println();

	}

}
