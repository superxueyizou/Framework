/**
 * 
 */
package modeling.saa.selfseparation;

import sim.engine.Steppable;
import modeling.env.Waypoint;


/**
 * @author Xueyi
 *
 */
public abstract class SelfSeparationAlgorithm implements Steppable
{

	/**
	 * 
	 */

	
	public SelfSeparationAlgorithm() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public abstract void init();
	
	public abstract Waypoint execute();
	

}
