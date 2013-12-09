/**
 * 
 */
package dominant;


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
		
		String[] params = new String[]{"-file", "src/dominant/UAS.params"};//D:/MyLibrary/KLive/EclipseWorkspace/UAS-Obstacle/src/dominant/
		ParameterDatabase database = Evolve.loadParameterDatabase(params);
		EvolutionState eState= Evolve.initialize(database, 0);
		eState.startFresh();
		int result=EvolutionState.R_NOTDONE;
		int i=1;
		while(result == EvolutionState.R_NOTDONE)
		{
			result=eState.evolve();
			System.out.println("run "+i +" finished :)&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			i++;
		}
		eState.finish(result);
		Evolve.cleanup(eState);		
		
	}

}
