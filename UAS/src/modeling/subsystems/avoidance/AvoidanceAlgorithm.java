/**
 * 
 */
package modeling.subsystems.avoidance;

import modeling.COModel;
import modeling.Destination;
import modeling.UAS;
import modeling.UASPerformance;
import modeling.Waypoint;
import sim.engine.SimState;
import sim.util.Double2D;

/**
 * @author Xueyi
 *
 */
public abstract class AvoidanceAlgorithm {

	/**
	 * 
	 */
	private COModel state; 
	private UAS hostUAS;
	
	
	private double direction;
	private Destination destination;
	Double2D destinationCoor;
	private double speed;
	private double sensitivityForCollisions;
	private double viewingRange;
	private double viewingAngle;
	private Double2D hostUASCorr;
	private UASPerformance performance;
	private double distanceToDanger; //records the closest distance to danger experienced by the UAS
	private Waypoint waypoint;
	
	
	public AvoidanceAlgorithm() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract Waypoint execute();
	
	public abstract void init();
	

}
