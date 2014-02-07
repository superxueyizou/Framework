package modeling.observer;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Obstacle;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;


public class ProximityMeasurer implements Constants,Steppable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SAAModel state;

	public ProximityMeasurer()
	{
		
	}
	
	@Override
	public void step(SimState simState) 
	{
		this.state = (SAAModel)simState;
		
		Obstacle obstacle;
		UAS uas1;		

	    for(int i=0; i<state.uasBag.size(); i++)
		{	
	    	
			uas1= (UAS)state.uasBag.get(i);
			if(!uas1.isActive)
			{
				continue;
			}

			for(int j=0; j<state.obstacles.size(); j++)
			{
				obstacle=(Obstacle)state.obstacles.get(j);
				if(obstacle.type == Constants.EntityType.TUAS && !((UAS)obstacle).isActive)
				{
						//System.out.println(state.obstacles.get(j)+": this obstacle is UAS, don't mind!");
						continue;
				}						
			
			    if(obstacle.equals(uas1))
				{
			    	continue;
				}
	  
			    double tempProximityToDanger=Double.MAX_VALUE;
			    tempProximityToDanger = obstacle.pointToObstacle(uas1.getLocation())-uas1.getSafetyRadius();
//			    System.out.println(uas1+"--"+obstacle+"-------*"+tempProximityToDanger);
			    
				uas1.setTempDistanceToDanger(tempProximityToDanger);
				if (tempProximityToDanger < uas1.getDistanceToDanger())
				{
					uas1.setDistanceToDanger(tempProximityToDanger);
					
				}					
				
			}
							
		}
		
	}



}
