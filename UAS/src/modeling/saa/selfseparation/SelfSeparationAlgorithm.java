/**
 * 
 */
package modeling.saa.selfseparation;

import modeling.env.Waypoint;


/**
 * @author Xueyi
 *
 */
public abstract class SelfSeparationAlgorithm 
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
