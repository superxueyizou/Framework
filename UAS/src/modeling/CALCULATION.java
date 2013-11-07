/**
 * 
 */
package modeling;

import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

/**
 * @author Xueyi
 *
 */
public class CALCULATION {

	/**providing calculation tool by some static methods
	 * 
	 */
	public CALCULATION() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Calculates the bearing the vehicle should be travelling on to move directly
	 * from a location to another.
	 * 
	 * @param point1
	 * @param point2
	 * @return 
	 */
	public static double calculateAngle(Double2D point1, Double2D point2)
	{
		Double2D vector = point2.subtract(point1);
		double absX=Math.abs(vector.x);
		double absY=Math.abs(vector.y);
		double angle;
		
		if(absX != 0)
		{
			angle = Math.toDegrees(Math.atan(absY/absX));
			
			if(vector.x >0)
			{
				if (vector.y >0) 
				{	
					angle =  - angle;
					
				} 
				
				
			}
			else
			{
				if (vector.y <0) 
				{	
					angle = 180 - angle;
					
				}
				else
				{
					angle = angle - 180;
				}
			}
			
			
		
		} 
		else 
		{
			//the UAS is either in line with the destination horizontally or vertically
			if (vector.y >0)
			{
			    angle = -90;			    
			}
			else
			{
				angle = 90;
			}
		}
		
		return angle;
}


/****************************************************************************************************************************************/
	
		/** 
	 * A method which changes a bearing to be in the range of -180 (EXclusive) to 180 (INclusive)
	 * 
	 * @param b the bearing to be corrected
	 * @return a bearing equivalent to b which has been converted to be in the correct range
	 */
	public static double correctAngle(double b)
	{
		if (b > 180)
		{
			return (b - 360);
		}
		
		if (b <= -180)
		{
			return (b + 360);
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
		
		if (angle >=0 && angle <= 90) 
		{
			xChange = (speed * Math.cos(Math.toRadians(angle)));
		} else if (angle >90 && angle <= 180) {
			xChange = (-1*speed * Math.cos(Math.toRadians(180 - angle)));
		} else if (angle < 0 && angle >= -90) {
			xChange = (speed * Math.cos(Math.toRadians(-angle)));
		} else {
			xChange = (-1 * speed * Math.cos(Math.toRadians(180 + angle)));
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
		if (angle >=0 && angle <= 90) 
		{
			yChange = (-1 * speed * Math.sin(Math.toRadians(angle)));
		} else if (angle >90 && angle <= 180) {
			yChange = (-1 * speed * Math.sin(Math.toRadians(180 - angle)));
		} else if (angle < 0 && angle >= -90) {
			yChange = (speed * Math.sin(Math.toRadians(-angle)));
		} else {
			yChange = (speed * Math.sin(Math.toRadians(180+ angle)));
		}	
		return yChange;
    }
	
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
			delta += 3.0;
		} while(delta <= 0.5*self.getViewingAngle());
	
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
  			delta += 3.0;
  		} while(delta <= 0.5*self.getViewingAngle());
  	
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
			xComponent = uas.getSpeed()*Math.cos(Math.toRadians(CALCULATION.correctAngle(uas.getBearing()-turningAngle)));
			yComponent = -1*uas.getSpeed()*Math.sin(Math.toRadians(CALCULATION.correctAngle(uas.getBearing()-turningAngle)));
			
		}
		else
		{
			xComponent = uas.getSpeed()*Math.cos(Math.toRadians(CALCULATION.correctAngle(uas.getBearing()+turningAngle)));
			yComponent = -1*uas.getSpeed()*Math.sin(Math.toRadians(CALCULATION.correctAngle(uas.getBearing()+turningAngle)));
		}

		//System.out.println("ccccccccccccccccccccccccccccc"+xComponent + " , "+ yComponent);
		coord.addIn(xComponent, yComponent);
			
		Waypoint wp = new Waypoint(uas.getState().getNewID(), uas.getDestination());
		wp.setLocation(new Double2D(coord));
		System.out.println("new waypoint's coordination is ("+coord.x + " , "+ coord.y +")");
		return wp;
	}


    /* This function is calculates any maneuvers that are necessary for the 
    current plane to avoid looping. Returns a waypoint based on calculations. 
    If no maneuvers are necessary, then the function returns the current 
    destination*/
   public static Waypoint takeDubinsPath(UAS uas) 
   {
    	/* Initialize variables*/    	
    	Waypoint dest = uas.getDestination();
		double currentBearing = uas.getBearing();	
       	double destBearing = calculateAngle(uas.getLocation(), dest.getLocation());
       	if (Math.abs(destBearing - currentBearing) < 5)
       	{
       		System.out.println("destBearing == currentBearingdestBearing == currentBearingdestBearing == currentBearingdestBearing == currentBearing");
       		return null;
       	}
    	
    	boolean isDestOnRight;
    	if(currentBearing>=0)
    	{
    		double delta = currentBearing-destBearing;
    		if ((delta > 0) && (delta < 180.0))
        	{
        		//System.out.println("(destBearing < currentBearing) && (destBearing > correctAngle(currentBearing + 180.0))(destBearing < currentBearing) & (destBearing > correctAngle(currentBearing + 180.0))");
        		isDestOnRight = true;
        	}
        	else 
    		{
        		isDestOnRight = false;
    		}
    		
    	}
    	else
    	{
    		double delta = currentBearing+180-destBearing;
    		if ((delta > 0) && (delta < 180.0))
        	{
        		//System.out.println("(destBearing < currentBearing) && (destBearing > correctAngle(currentBearing + 180.0))(destBearing < currentBearing) & (destBearing > correctAngle(currentBearing + 180.0))");
        		isDestOnRight = false;
        	}
        	else 
    		{
        		isDestOnRight = true;
    		}
    	}
    	
    	
      	/* Calculate the center of the circle of minimum turning radius on the side that the waypoint is on*/	
    	Double2D cPlusCenter;
    	double minTurningRadius = uas.getSpeed()/Math.toRadians(uas.getPerformance().getCurrentMaxTurning());
    	double x, y;
    	if (isDestOnRight) 
    	{
    		x =uas.getLocation().x + minTurningRadius*Math.sin(Math.toRadians(uas.getBearing()));
    		y =uas.getLocation().y + minTurningRadius*Math.cos(Math.toRadians(uas.getBearing()));
       	}
    	else 
    	{
    		x =uas.getLocation().x - minTurningRadius*Math.sin(Math.toRadians(uas.getBearing()));
    		y =uas.getLocation().y - minTurningRadius*Math.cos(Math.toRadians(uas.getBearing()));
    	}
    	cPlusCenter = new Double2D(x,y);

    	/* If destination is inside circle, must fly opposite direction before we can reach destination*/
    	if ( minTurningRadius -cPlusCenter.distance(dest.getLocation()) > 0) 
    	{
    		System.out.println("isDestOnRight = "+isDestOnRight);
    		System.out.println("cPlusCenter.distance(dest.getLocation()) < minTurningRadius");

    		return calculateWaypoint(uas, uas.getPerformance().getCurrentMaxTurning(), !isDestOnRight);
    	}
    	else
    	{
    		return calculateWaypoint(uas, uas.getPerformance().getCurrentMaxTurning(), isDestOnRight);
    	}
   
    	   		
    	
    }


}
