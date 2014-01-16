/**
 * 
 */
package modeling.subsystems.avoidance;

import modeling.Waypoint;


/**
 * @author Xueyi
 *
 */
public abstract class AvoidanceAlgorithm {

	/**
	 * 
	 */

	
	public AvoidanceAlgorithm() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract Waypoint execute();
	
	public abstract void init();
	

}
