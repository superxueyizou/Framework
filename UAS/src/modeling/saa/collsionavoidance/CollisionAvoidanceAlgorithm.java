/**
 * 
 */
package modeling.saa.collsionavoidance;

import modeling.env.Waypoint;


/**
 * @author Xueyi
 *
 */
public abstract class CollisionAvoidanceAlgorithm 
{

	/**
	 * 
	 */

	
	public CollisionAvoidanceAlgorithm() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public abstract void init();
	
	public abstract Waypoint execute();
	

}
