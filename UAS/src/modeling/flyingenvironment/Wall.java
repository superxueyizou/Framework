package modeling.flyingenvironment;

import modeling.Constants;
import modeling.Obstacle;
import modeling.Constants.EntityType;


/**
 * @author Xueyi Zou
 *
 */
public class Wall extends Obstacle {

	/**
	 * @param idNo
	 * @param typeNo
	 */
	public Wall() {
		super(100000, Constants.EntityType.TWALL);
		// TODO Auto-generated constructor stub
	}

}
