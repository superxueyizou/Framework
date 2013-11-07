package modeling;
import sim.util.*;

/**
 *
 * @author Robert Lee
 */
public class Destination extends Waypoint
{	
	public Destination(int idNo, Waypoint next)
	{
		super(idNo, null);
	}
	
	public Waypoint getNextWaypoint() {return null;}
}
