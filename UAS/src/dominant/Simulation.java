/**
 * 
 */
package dominant;


import java.io.File;
import javax.swing.JOptionPane;

import tools.CONFIGURATION;
import tools.UTILS;
import ec.*;
import ec.util.*;

/**
 * @author xueyi
 * simulation with GA as harness
 *
 */
public class Simulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		String[] params = new String[]{"-file", "src/dominant/MaxOscillation.params"};
		ParameterDatabase database = Evolve.loadParameterDatabase(params);
		EvolutionState eState= Evolve.initialize(database, 0);
		eState.startFresh();
		int result=EvolutionState.R_NOTDONE;
		int i=1;
		while(result == EvolutionState.R_NOTDONE)
		{
			result=eState.evolve();
			System.out.println("simulation of generation "+i +" finished :)&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println();
			i++;
		}		
		eState.finish(result);
		
		int confirmationResult = JOptionPane.showConfirmDialog(null, "Evolution search has finished Do you like recurrence with GUI?", "Recurrent With GUI",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		
		if (confirmationResult == JOptionPane.YES_OPTION )
		{
			String str = UTILS.readLastLine(new File("out.stat"), "utf-8").trim();
			String[] pArr= str.split(" ");
			//System.out.println(pArr[3]);
					
			CONFIGURATION.selfDestDist= Double.parseDouble(pArr[0]);
			CONFIGURATION.selfDestAngle=Double.parseDouble(pArr[1]);
			
			CONFIGURATION.headOnSelected= Double.parseDouble(pArr[2])>0? true: false;
			CONFIGURATION.headOnOffset=Double.parseDouble(pArr[3]);
			CONFIGURATION.headOnIsRightSide= Double.parseDouble(pArr[4])>0? true: false;			
			CONFIGURATION.headOnSpeed=Double.parseDouble(pArr[5]);
			
    		CONFIGURATION.crossingSelected = Double.parseDouble(pArr[6])>0? true: false;
    		CONFIGURATION.crossingEncounterAngle=Double.parseDouble(pArr[7]);
    		CONFIGURATION.crossingIsRightSide= Double.parseDouble(pArr[8])>0? true: false;
    		CONFIGURATION.crossingSpeed =Double.parseDouble(pArr[9]);
    		
    		CONFIGURATION.tailApproachSelected = Double.parseDouble(pArr[10])>0? true: false;
    		CONFIGURATION.tailApproachOffset= Double.parseDouble(pArr[11]);
    		CONFIGURATION.tailApproachIsRightSide=Double.parseDouble(pArr[12])>0? true: false;
    		CONFIGURATION.tailApproachSpeed =Double.parseDouble(pArr[13]);
			
			System.out.println("\nRecurrenceWithGUI");
			SimulationWithUI.main(null);
		}		
				
		Evolve.cleanup(eState);	
	}
	


	

}
