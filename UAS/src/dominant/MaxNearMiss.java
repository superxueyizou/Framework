/**
 * 
 */
package dominant;

import tools.CONFIGURATION;
import modeling.COModel;
import modeling.COModelBuilder;
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
    	boolean isRightSide = (ind2.genome[0]>0)? true: false;
		double  offset = ind2.genome[1];
		double  speed = ind2.genome[2];
		
		int times =1, dividend=0;
        for(int i=0;i<times; i++)
        {
        	long time = System.nanoTime();
    		COModel simState= new COModel(time, 150, 100, false); 	
    		
    		CONFIGURATION.headOnSelected = true;
    		CONFIGURATION.headOnIsRightSide= isRightSide;
    		CONFIGURATION.headOnOffset=offset;
    		CONFIGURATION.headOnSpeed =speed;
    		
 	   		COModelBuilder sBuilder = new COModelBuilder(simState);
    		sBuilder.generateSimulation(1,1);
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
    			System.out.println(uas.getDistanceToDanger());
    			distanceToDangerSum += uas.getDistanceToDanger();
    			dividend++;
    		}
    		
        }
        
		float rawFitness= (float)distanceToDangerSum/dividend;  
		float fitness = 1/(1+rawFitness);
        
        
        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        ((SimpleFitness)ind2.fitness).setFitness(   state,            
										            fitness,/// ...the fitness...
										            fitness == 1);///... is the individual ideal?  Indicate here...
        
        ind2.evaluated = true;
        System.out.println("individual result: isRightSide("+isRightSide+"), offset("+ offset+"), speed( "+ speed + "); fitness[  " + fitness +" ]" );
        

	}

}
