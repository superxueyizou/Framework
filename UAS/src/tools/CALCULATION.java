/**
 * 
 */
package tools;

import modeling.env.Obstacle;
import modeling.env.Waypoint;
import modeling.uas.UAS;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

/**
 * @author Xueyi
 *
 */
public class CALCULATION 
{
	
	final static double epsilon = 1.0e-6;

	/**providing calculation tool by some static methods
	 * 
	 */
	public CALCULATION() {
		// TODO Auto-generated constructor stub
	}	
	
/****************************************************************************************************************************************/
	
		/** 
	 * A method which changes a bearing to be in the range of -pi (INclusive) to pi (EXclusive)
	 * 
	 * @param b the bearing to be corrected
	 * @return a bearing equivalent to b which has been converted to be in the correct range [-pi, pi)
	 */
	public static double correctAngle(double b)
	{
		if (b >= Math.PI)
		{
			return (b - 2.0*Math.PI);
		}
		
		if (b < -Math.PI)
		{
			return (b + 2.0*Math.PI);
		}
		
		return b;
	}
	
	
	 /**
     * A function which based on the direction the UAS is facing and the speed it
	 * is travelling at 
	 * it returns a value for how much the x position should change in one step.
	 * 
	 * @param speed the speed
     * @return the change in x coordinate of the UAS in the world
     */
	public static double xMovement(double angle, double speed)
	{
		double xChange;
		
		if (angle >=0 && angle <= 0.5*Math.PI) 
		{
			xChange = (speed * Math.cos(angle));
		} else if (angle >0.5*Math.PI && angle <= Math.PI) {
			xChange = (-1*speed * Math.cos(Math.PI - angle));
		} else if (angle < 0 && angle >= -0.5*Math.PI) {
			xChange = (speed * Math.cos(-angle));
		} else {
			xChange = (-1 * speed * Math.cos(Math.PI + angle));
		}	
		return xChange;
    }
	
    
	/**
	 * The y axis equivalent of the xMovement method
	 * 
	 * @return the change in y coordinate of the UAS in the world
	 */
	public static double yMovement(double angle, double speed)
	{
		double yChange;
		if (angle >=0 && angle <= 0.5*Math.PI) 
		{
			yChange = (-1 * speed * Math.sin(angle));
		} else if (angle >0.5*Math.PI && angle <= Math.PI) {
			yChange = (-1 * speed * Math.sin(Math.PI - angle));
		} else if (angle < 0 && angle >= -0.5*Math.PI) {
			yChange = (speed * Math.sin(-angle));
		} else {
			yChange = (speed * Math.sin(Math.PI+ angle));
		}	
		return yChange;
    }
	
	protected static double det(Double2D v1, Double2D v2)
    {
        return v1.x * v2.y - v1.y * v2.x;
    }
	
    /*
     * a first point of a line
     * b second point of a line
     * c testing point
     */
	public static boolean leftOf(Double2D a, Double2D b, Double2D c)
    {
        return (det(a.subtract(c), b.subtract(a))>epsilon);
    }
	
	public static boolean rightOf(Double2D a, Double2D b, Double2D c)
    {
        return (-det(a.subtract(c), b.subtract(a))>epsilon);
    }
	
	
//	public static double  getAngle(Double2D p1, Double2D p2)
//    {
// 	   	double x1 = p1.x, y1= p1.y,x2=p2.x,y2=p2.y;
// 	    final double nyPI = Math.PI;
// 	    double dist, dot, degree, angle;
// 	    
// 	    // normalize
// 	    dist = Math.sqrt( x1 * x1 + y1 * y1 );
// 	    x1 /= dist;
// 	    y1 /= dist;
// 	    dist = Math.sqrt( x2 * x2 + y2 * y2 );
// 	    x2 /= dist;
// 	    y2 /= dist;
// 	    // dot product
// 	    dot = x1 * x2 + y1 * y2;
// 	    if ( Math.abs(dot-1.0) <= epsilon )
// 	    {
// 	    	angle = 0.0;
// 	    }	     
// 	    else if ( Math.abs(dot+1.0) <= epsilon )
// 	    {
// 	    	angle = nyPI;	    	
// 	    }
// 	    else 
// 	    {
// 		     angle = Math.acos(dot);
// 		}
// 	    degree = Math.toDegrees(angle);
// 	    return degree;
//    }

//    /*
//    * 计算向量p1到p2的旋转角，算法如下：
//    * 首先通过点乘和arccosine的得到两个向量之间的夹角
//    * 然后判断通过差乘来判断两个向量之间的位置关系
//    * 如果p2在p1的顺时针方向, 返回arccose的角度值, 范围0 ~ 180.0(根据右手定理,可以构成正的面积)
//    * 否则返回 360.0 - arecose的值, 返回180到360(根据右手定理,面积为负)
//    */ 
//   public static double  getRotateAngle(Double2D p1, Double2D p2)
//   {
//	   	double x1 = p1.x, y1= p1.y,x2=p2.x,y2=p2.y;
//	    
//	    final double nyPI = Math.PI;
//	    double dist, dot, degree, angle;
//	    
//	    // normalize
//	    dist = Math.sqrt( x1 * x1 + y1 * y1 );
//	    x1 /= dist;
//	    y1 /= dist;
//	    dist = Math.sqrt( x2 * x2 + y2 * y2 );
//	    x2 /= dist;
//	    y2 /= dist;
//	    // dot product
//	    dot = x1 * x2 + y1 * y2;
//	    if ( Math.abs(dot-1.0) <= epsilon )
//	    {
//	    	angle = 0.0;
//	    }	     
//	    else if ( Math.abs(dot+1.0) <= epsilon )
//	    {
//	    	angle = nyPI;	    	
//	    }
//	    else 
//	    {
//		     double cross;
//		     
//		     angle = Math.acos(dot);
//		     //cross product
//		     cross = x1 * y2 - x2 * y1;
//		     // vector p2 is clockwise from vector p1 
//		     // with respect to the origin (0.0)
//		     if (cross < 0 ) 
//		     { 
//		      angle = 2 * nyPI - angle;
//		     }    
//	    }
//	    degree = Math.toDegrees(angle);
//	    return degree;
//   }
	//this method tests a course to see if any of the obstacles in the bag will be hit
	//by the UAS if it moves from it's position on the bearing provided
	/**
	 * 
	 * @param self
	 * @param obstacles
	 * @return true if going to hit something in obstacles, false if not
	 */
    public static boolean checkCourse(UAS self, double direction, Bag obstacles)
	{
	    for(int i = 0; i < obstacles.size(); i++)
		{
	    	Obstacle obstacle= (Obstacle)obstacles.get(i);
	    	if(obstacle instanceof UAS)
			{
	    		if(obstacle.equals(self))
				{
					//System.out.println(obstacles.get(i)+"this obstacle is myself, don't mind!");
					continue;
				}
	    		if(!((UAS)obstacle).isActive)
				{
					//System.out.println(obstacles.get(i)+"this obstacle is dead, don't mind!");
					continue;
				}
				
			}
			
			if (onCourse(self, direction, obstacle))
			{
				return true;
			}
		}
		
		return false;
	}
    
	//this method tests the sensor field to see if any of the obstacles in the bag will be possibly hit
	//by the UAS if it moves from it's position on the bearing provided
	/**
	 * 
	 * @param self
	 * @param obstacles
	 * @return true if going to hit something in obstacles, false if not
	 */
    public static boolean checkField(UAS self, double direction, Bag obstacles)
	{
   		if(checkLeftField(self, direction, obstacles))
		{
			return true;
		}
		else if(checkRightField(self, direction, obstacles))
		{
			return true;
		}
		else
		{
			return false;
		}
  		
	}

  //this method tests the sensor's left-hand field to see if any of the obstacles in the bag will be possibly hit
  	//by the UAS if it moves from it's position on the bearing provided
  	/**
  	 * 
  	 * @param self
  	 * @param obstacles
  	 * @return true if going to hit something in obstacles, false if not
  	 */
    public static boolean checkLeftField(UAS self, double direction, Bag obstacles)
  	{
    	double delta = 0.0;					
		do
		{
			/***left-hand side check*/
			for(int i = 0; i < obstacles.size(); i++)
	  		{
				Obstacle obstacle= (Obstacle)obstacles.get(i);
				if(obstacle instanceof UAS)
				{
		    		if(obstacle.equals(self))
					{
						//System.out.println(obstacles.get(i)+"this obstacle is myself, don't mind!");
						continue;
					}
		    		if(!((UAS)obstacle).isActive)
					{
						//System.out.println(obstacles.get(i)+"this obstacle is dead, don't mind!");
						continue;
					}
					
				}
	  	    	
				if(onCourse(self, CALCULATION.correctAngle(direction+delta), obstacle))
				{
					return true;
				}
	  		}
			delta += Math.toRadians(3.0);
		} while(delta <= 0.5*Math.toRadians(self.getViewingAngle()));
	
  		return false;
  	}
      
    //this method tests the sensor's right-hand field to see if any of the obstacles in the bag will be possibly hit
  	//by the UAS if it moves from it's position on the bearing provided
  	/**
  	 * 
  	 * @param self
  	 * @param obstacles
  	 * @return true if going to hit something in obstacles, false if not
  	 */
    public static boolean checkRightField(UAS self, double direction, Bag obstacles)
  	{
    	double delta = 0.0;					
  		do
  		{
  			/***left-hand side check*/
  			for(int i = 0; i < obstacles.size(); i++)
  	  		{
  	  	    	Obstacle obstacle= (Obstacle)obstacles.get(i);
	  	  	    if(obstacle instanceof UAS)
				{
		    		if(obstacle.equals(self))
					{
						//System.out.println(obstacles.get(i)+"this obstacle is myself, don't mind!");
						continue;
					}
		    		if(!((UAS)obstacle).isActive)
					{
						//System.out.println(obstacles.get(i)+"this obstacle is dead, don't mind!");
						continue;
					}
					
				}
  				if(onCourse(self, CALCULATION.correctAngle(direction-delta), obstacle))
  				{
  					return true;
  				}
  	  		}
  			delta += Math.toRadians(3.0);
  		} while(delta <= 0.5*Math.toRadians(self.getViewingAngle()));
  	
    	return false;
  	}


	/** this method will analyse an obstacle and will see if the UAS will hit it
	 *  on the course specified as bearing
	 * 
	 * @param o
	 * @param bearing
	 * @return true if going to hit o on provided course (as far as it can see) false if not
	 */
    public static boolean onCourse(UAS self, double direction, Obstacle o)
	{
		//simple and dirty method which checks the coordinates between 0 and 
		//the viewing range away from the destination in certain increments and see 
		//if they're in the obstacle
		double sensitivityForCollisions =self.getSensitivityForCollisions();
		double viewingRange = self.getViewingRange();
		MutableDouble2D testCoord = new MutableDouble2D(0,0);		
		testCoord.addIn(self.getLocation());
		
		Double2D amountAdd = new Double2D(CALCULATION.xMovement(direction, sensitivityForCollisions), CALCULATION.yMovement(direction, sensitivityForCollisions));
		for(double i = 0; i < viewingRange; i += sensitivityForCollisions)
		{
			//keep adding the amountAdd on and seeing if the coordinate is in the obstacle o
			//going to need to change obstacles to be a subset of entities now so that one 
			//can use the inShape with all of them
			if (o.inCollision(new Double2D(testCoord), self.getRadius()))
			{
				return true; //the testing doesn't need to continue if it would hit the obstacle at one point
			}
			testCoord.addIn(amountAdd);
			
		}
		
		return false; //the UAS does not hit the obstacle at any point it can see on it's current course
	}
	
    
    /* Find the new collision avoidance waypoint for the UAS to go to */
	public static Waypoint calculateWaypoint(UAS uas, double turningAngle, boolean isTurnRight)
	{
		Double2D myLocation = uas.getLocation();		
		MutableDouble2D coord = new MutableDouble2D(myLocation);
		double xComponent, yComponent;
		if(isTurnRight)
		{
			xComponent = uas.getSpeed()*Math.cos(CALCULATION.correctAngle(uas.getBearing()-turningAngle));
			yComponent = -1*uas.getSpeed()*Math.sin(CALCULATION.correctAngle(uas.getBearing()-turningAngle));
			
		}
		else
		{
			xComponent = uas.getSpeed()*Math.cos(CALCULATION.correctAngle(uas.getBearing()+turningAngle));
			yComponent = -1*uas.getSpeed()*Math.sin(CALCULATION.correctAngle(uas.getBearing()+turningAngle));
		}

		
		coord.addIn(xComponent, yComponent);
			
		Waypoint wp = new Waypoint(uas.getState().getNewID(), uas.getDestination());
		wp.setLocation(new Double2D(coord));
		//System.out.println("new waypoint's coordination is ("+coord.x + " , "+ coord.y +")");
		return wp;
	}   

}
