/**
 * 
 */
package dominant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import ec.*;
import ec.util.*;

import modeling.*;

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
		int i=0;
		while(result == EvolutionState.R_NOTDONE)
		{
			result=eState.evolve();
			System.out.println("run "+" finished :)&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			
		}
		eState.finish(result);
		Evolve.cleanup(eState);		
		
	}

}
