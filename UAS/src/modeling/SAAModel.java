package modeling;

import sim.util.*;
import sim.field.continuous.*;
import sim.engine.*;
import sim.field.network.Network;

public class SAAModel extends SimState
{
	public Bag toSchedule = new Bag(); // entities to schedule, important
	public Bag allEntities = new Bag(); // entities to load into the environment, important
	
	public Bag uasBag = new Bag();
	public Bag obstacles= new Bag();
		
	public boolean runningWithUI = false; 
	
    private int newID = 0;	
    
//	private double xDouble;
//	private double yDouble;	
	
    public Continuous2D environment;
    public Network voField;
    
//	public IntGrid2D obstacleMap;
//	private int obstacleMapResolution = 3; //multiplier used to change resolution of obstacles image
//
//	public IntGrid2D terrainMap;
//	private int terrainMapResolution = 3;
//	
//	public IntGrid2D wallMap;
//	private int wallMapResolution =3;
	
	
	
	public AccidentDetector aDetector= new AccidentDetector();
	
	/**
	 * Constructor used for setting up a simulation from the COModelBuilder object.
	 * 
	 * @param seed for random number generator
	 * @param x the width of the simulation environment
	 * @param y the height of the simulation environment
	 * @param UI pass true if the simulation is being ran with a UI false if it is not.
	 */
	public SAAModel(long seed, double x, double y, boolean UI)
    {
		super(seed);
		environment = new Continuous2D(1.0, x, y);
		voField = new Network(false);
		
//		xDouble = x;
//		yDouble = y;
//		obstacleMap = new IntGrid2D((int) (xDouble * obstacleMapResolution), (int) (yDouble * obstacleMapResolution), 0);
//		terrainMap = new IntGrid2D((int) (xDouble * terrainMapResolution), (int) (yDouble * terrainMapResolution), 0);
//		wallMap = new IntGrid2D((int) (xDouble * wallMapResolution), (int) (yDouble * wallMapResolution), 0);
		
		runningWithUI = UI;
		System.out.println("COModel(long seed, double x, double y, boolean UI) is being called!!!!!!!!!!!! the simstate is :" + this.toString());

	}
    
		
	
	public void start()
	{
		super.start();	
		environment.clear();
		voField.clear();
		loadEntities();
		scheduleEntities();	
		
		
//		buildObstacleMap(); //build the map of integers for obstacle representation
//		buildTerrainMap();
//		buildWallMap();
		
	}
		
	
	
	
		
	/**
	 * A method which resets the variables for the COModel and also clears
	 * the schedule and environment of any entities, to be called between simulations.
	 * 
	 * This method resets the newID counter so should NOT be called during a run.
	 */
	public void reset()
	{
		newID = 0;
		obstacles.clear();
		uasBag.clear();
		toSchedule.clear();
		allEntities.clear();
		environment.clear(); //clear the environment
		voField.clear();
		
		
//		terrainMap.setTo(0); //normal
	}
	
	
	/**
	 * A method which provides a different number each time it is called, this is
	 * used to ensure that different entities are given different IDs
	 * 
	 * @return a unique ID number
	 */
	public int getNewID()
	{
		int t = newID;
		newID++;
		return t;
	}
	
	
	/**
	 * A method which adds all of the entities to the simulations environment.
	 */
	public void loadEntities()
	{
		for(int i = 0; i < allEntities.size(); i++)
		{
			environment.setObjectLocation((Entity) allEntities.get(i), ((Entity) allEntities.get(i)).getLocation());
		
		}
		
	}
	
	
	/**
	 * A method which adds all the entities marked as requiring scheduling to the
	 * schedule for the simulation
	 */
	public void scheduleEntities()
	{
		//loop across all items in toSchedule and add them all to the schedule
		int i ;
		for(i = 0; i < toSchedule.size(); i++)
		{
			schedule.scheduleRepeating((Entity) toSchedule.get(i), i, 1.0);
		}	
		schedule.scheduleRepeating(aDetector,i+1, 1.0);
	}

	/**
	 * A method which returns a true or false value depending on if an obstacle is
	 * at the coordinate provided
	 * 
	 * @param coord the coordinate to check
	 * @param obstacles the obstacles to be checked
	 * @return 
	 */
	public boolean obstacleAtPoint(Double2D coord, Bag obstacles)
	{
		for (int i = 0; i < obstacles.size(); i++)
		{
			//for all of the obstacles check if the provided point is in it
			if (((Obstacle) (obstacles.get(i))).inShape(coord)) //[TODO] this might not work it depends on if whatever the object is will override inShape
			{
				return true;
			}
		}
		//at this point no cross over has been detected so false should be returned
		return false;
	}

    public void dealWithTermination()
	{
    	int noActiveAgents =0;
    	//System.out.println(COModel.toSchedule.size());
    	for(Object o: this.toSchedule)
    	{
    		if(((UAS)o).isActive)
    		{
    			noActiveAgents++;
    		}
    		
    	}
//    	System.out.println("NO. of active uasBag is: "+ noActiveAgents);
    	
		if(noActiveAgents < 1)
		{
			this.schedule.clear();
			//System.out.println("NO. of active uasBag is: "+ noActiveAgents+". Game Over!");
			//System.out.println(this.schedule.scheduleComplete());
			this.kill();
		}
	 }
    
    
	
	public Bag getUasBag() {
		return uasBag;
	}



	public void setUasBag(Bag uasBag) {
		this.uasBag = uasBag;
	}

/********************************************************************************************************************/
//	/**
//	 * A method which creates a discrete map of where obstacles are in the environment
//	 * which can be used for outputting a visual representation of the obstacles
//	 * when the simulation is ran with a UI
//	 */
//	private void buildObstacleMap()
//	{
//		//[TODO] This will need to be called more than once if the simulations can have moving obstacles
//		Bag all = environment.allObjects;
//		Bag obstacles = new Bag();
//		
//		Double2D coordinate;
//		int xInt = (int) (xDouble * obstacleMapResolution);
//		int yInt = (int) (yDouble * obstacleMapResolution);
//		obstacleMap = new IntGrid2D(xInt, yInt, 0);
//		
//		for (int i = 0; i < all.size(); i++)
//		{
//			if (((Entity) (all.get(i))).getType() == Constants.EntityType.TCIROBSTACLE)
//			{
//				obstacles.add(all.get(i));
//			}
//			
//		}
//		
//		for (int i = 0; i < xInt; i++) 
//		{
//			for (int j = 0; j < yInt; j++)
//			{
//				coordinate = new Double2D(((double) i) / obstacleMapResolution, ((double) j) / obstacleMapResolution);
//				//loop over all coordinates and check if there is an obstacle there
//				if (obstacleAtPoint(coordinate, obstacles))
//				{
//					//System.out.println("setting the point at x:" + Integer.toString(i) + " y:" + Integer.toString(j) + " to 1");
//					obstacleMap.field[i][j] = 1;
//				}				
//			}
//		}
//	}
//	
//	
//	/**
//	 * A method which draws two circles onto the terrain map for different terrain types.
//	 */
//	public void buildWallMap()
//	{
//		int xInt = (int) (xDouble * terrainMapResolution);
//		int yInt = (int) (yDouble * terrainMapResolution);
//		
//		for (int i = 0; i < xInt; i++) 
//		{
//			
//			wallMap.field[0][i]=1;
//			wallMap.field[yInt-1][i]=1;			
//		}
//		
//		for (int j = 0; j < yInt; j++) 
//		{
//			
//			wallMap.field[j][0]=1;
//			wallMap.field[j][xInt-1]=1;			
//		}
//		
//	}
//	
//	
//	public void buildTerrainMap()
//	{
//
//		int xInt = (int) (xDouble * wallMapResolution);
//		int yInt = (int) (yDouble * wallMapResolution);
//		
//		for (int i = 0; i < xInt; i++) {
//			for (int j = 0; j < yInt; j++)
//			{
//				if ((((i - (50 * terrainMapResolution)) * (i - (50 * terrainMapResolution))) + ((j - (50 * terrainMapResolution)) * (j - (50 * terrainMapResolution)))) < ((20 * terrainMapResolution) * (20 * terrainMapResolution)))
//				{
//					terrainMap.field[i][j] = 2; //gravel
//				} else if ((((i - (50 * terrainMapResolution)) * (i - (50 * terrainMapResolution))) + ((j - (50 * terrainMapResolution)) * (j - (50 * terrainMapResolution)))) < ((40 * terrainMapResolution) * (40 * terrainMapResolution))){
//					terrainMap.field[i][j] = 1; //ice
//				} else {
//					terrainMap.field[i][j] = 0; //normal
//				}
//			}
//		}
//		
//	}

//	/**
//	 * A method which checks the terrain at a given location
//	 * 
//	 * @param coord coordinate to check the terrain at
//	 * @return 
//	 */
//	public Constants.TerrainType terrainTypeAtPoint(Double2D coord)
//	{
//		int x = (int)coord.x * terrainMapResolution;
//		int y = (int)coord.y * terrainMapResolution;
//		Constants.TerrainType tTerrain =Constants.TerrainType.NORMAL;
//		try
//		{
//			int mapNo =	terrainMap.get(x, y);
//			switch(mapNo)
//			{case 0:
//				tTerrain = Constants.TerrainType.NORMAL;
//				break;
//			case 1:
//				tTerrain = Constants.TerrainType.ICE;
//				break;
//			case 2:
//				tTerrain = Constants.TerrainType.GRAVEL;
//				break;
//			default:
//					tTerrain = Constants.TerrainType.NORMAL;
//			
//			}
//		}
//		catch(ArrayIndexOutOfBoundsException e)
//		{
//			System.out.println("too fast to detect the clash with wall accident, go on!");
//		}
//		return tTerrain;
//	}

}
