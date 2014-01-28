/**
 * 
 */
package dominant;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
public class Simulation
{
	protected static List<String> simDataSet = new ArrayList<>(200);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		String[] params = new String[]{"-file", "src/dominant/MaxNMAC.params"}; //MaxOscillation, MaxNMAC
		ParameterDatabase database = Evolve.loadParameterDatabase(params);
		EvolutionState eState= Evolve.initialize(database, 0);
		eState.startFresh();
		int result=EvolutionState.R_NOTDONE;
		
		
		String title = "generation,selfDestDist,selfDestAngle,"+
				   "headOnSelected,headOnOffset,headOnIsRightSide,headOnSpeed,"+
                "crossingSelected,crossingEncounterAngle,crossingIsRightSide,crossingSpeed,"+
				   "tailApproachSelected,tailApproachOffset,tailApproachIsRightSide,tailApproachSpeed,"+
                "fitness," +"accident"+"\n";
		boolean isAppending = false;
		String label = database.getLabel();
		String fileName= (String) label.subSequence(label.lastIndexOf("/")+1, label.lastIndexOf("."));
		
		int i=0;
		
		while(result == EvolutionState.R_NOTDONE)
		{
			result=eState.evolve();
			System.out.println("simulation of generation "+i +" finished :)&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
						
			if(simDataSet.size()>=200)
			{  				
				UTILS.writeDataSet2CSV(fileName + "Dataset.csv", title, simDataSet,isAppending);
				isAppending =true;
				simDataSet.clear();
			}
			i++;
		}	
		
//		for(int j=0; j<MyStatistics.accidents.length; j++)
//		{
//			System.out.print(MyStatistics.accidents[j]);
//		}
				
		eState.finish(result);		
		Object[] options= new Object[]{"Recurrence","Weka","Close"};
		int confirmationResult = JOptionPane.showOptionDialog(null, "choose the next step", "What's next", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, 0);
		
		if (confirmationResult == 0 )
		{
			String str = UTILS.readLastLine(new File(fileName+"Statics.stat"), "utf-8").trim();
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
		else if (confirmationResult == 1)
		{
			DataMiner dm = new DataMiner();
			dm.execute(fileName);
			
		}
		else if (confirmationResult == 2 )
		{
			Evolve.cleanup(eState);	
		}
				
	
		
	}
	


}
