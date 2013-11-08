package modeling;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.SimplePortrayal2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.FastValueGridPortrayal2D;
import sim.portrayal.simple.CircledPortrayal2D;
import sim.portrayal.simple.HexagonalPortrayal2D;
import sim.portrayal.simple.ImagePortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.OrientedPortrayal2D;
import tools.CONFIGURATION;

/**
 * A class for running a simulation with a UI, run to see a simulation with a UI
 * showing it running.
 * 
 * @author Robert Lee
 */
public class COModelWithUI extends GUIState
{	
	protected COModelBuilder sBuilder; // = new COModelBuilder((COModel) state);	
	
	public Display2D display;
	public JFrame displayFrame;
	ContinuousPortrayal2D environmentPortrayal = new ContinuousPortrayal2D();
	FastValueGridPortrayal2D obstaclesPortrayal = new FastValueGridPortrayal2D("Obstacle", true);  // immutable
	FastValueGridPortrayal2D terrainPortrayal = new FastValueGridPortrayal2D("Terrain", true);  // immutable
	FastValueGridPortrayal2D wallPortrayal = new FastValueGridPortrayal2D("Wall", true);  // immutable
	    
	
   
    public COModelWithUI() 
    {   
        super(new COModel(785945568, COModelBuilder.worldXVal, COModelBuilder.worldYVal, true)); 	
    	//super(new COModel(System.nanoTime(), COModelBuilder.worldXVal, COModelBuilder.worldYVal, true)); 
    	System.out.println("COModelWithUI() is being called!"+ "it's state(model)is: "+ state.toString());
    	sBuilder = new COModelBuilder((COModel) state);
    }
    
    
    public COModelWithUI(SimState state) 
    {
    	super(state); 
    	System.out.println("COModelWithUI(SimState state) is being called!"+ "it's state(model)is: "+ state.toString());    
    }    
    
    public static String getName() { return "UAS-SAA-Sim"; } 
  
    
	public void start()
	{
		System.out.println("COModelWithUI.start is called  "+ sBuilder.state);
		sBuilder.state.reset();
		//sBuilder.testSim();
		sBuilder.generateSimulation(CONFIGURATION.sObstacleNo,CONFIGURATION.sUASNo);
		
		super.start();
		setupPortrayals();
		
		
	}

	/**
	 * I do not know if this method is required by MASON at all, and the simulation
	 * with UI appears to run correctly even when it is removed, however all of 
	 * the example simulations that MASON comes with include a load method in the
	 * with UI class so I have done as well even though I have not found a reason
	 * as to if it is important to have one.
	 */
	public void load(SimState state)
	{
		sBuilder.state.reset();
		//sBuilder.testSim();
		sBuilder.generateSimulation(CONFIGURATION.sObstacleNo,CONFIGURATION.sUASNo);
		super.load(state);
		setupPortrayals();
	}

	
	
	/**
	 * A method which sets up the portrayals of the different layers in the UI,
	 * this is where details of the simulation are coloured and set to different
	 * parts of the UI
	 */
	public void setupPortrayals()
	{		
		COModel simulation = (COModel) state;

		// tell the portrayals what to portray and how to portray them
		environmentPortrayal.setField( simulation.environment );
		
//		Image img = null;
//		try{
//		img = ImageIO.read(new File("res/UAS1.jpg"));
//		}
//		catch (IOException e)
//		{
//			System.out.println(e.toString());
//		}
//		SimplePortrayal2D imgPortrayal = new ImagePortrayal2D(img, 2)
//		{
//			public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
//			{
//				if(((UAS)object).isActive==true)
//				{
//					paint = new Color(0xEA, 0xFF, 0x00);
//				}
//				else
//				{
//					paint = new Color(0,0,0);
//				}
//								
//			    super.draw(object, graphics, info);
//			}
//	    };
		
		environmentPortrayal.setPortrayalForClass
		(UAS.class, 
				new CircledPortrayal2D(
										//new OrientedPortrayal2D(
																	(SimplePortrayal2D)new HexagonalPortrayal2D(0.7)
																	{
																		public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
																		{
																			paint = new Color(0, 156, 0);		
																		    super.draw(object, graphics, info);
																		}
																	}, 
																	//1, 1, new Color(0,0,0), OrientedPortrayal2D.SHAPE_COMPASS
															  // ),
									   1.667*10,1.0,new Color(255, 20, 0),false
									  )	
		                              {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
			{
				paint = ((UAS)object).isActive? new Color(255, 0, 0):new Color(128, 128, 128);			
			    super.draw(object, graphics, info);
			}
			
		                              }
				
		);
		
		environmentPortrayal.setPortrayalForClass(Destination.class, new LabelledPortrayal2D( new HexagonalPortrayal2D(0.7)
		{
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
			{
				paint = new Color(0x0E, 0xEC, 0xF0);			
			    super.draw(object, graphics, info);
			}
		}, "T", new Color(0, 0, 0), false) 				
		
		);
		
		environmentPortrayal.setPortrayalForClass(Waypoint.class, new HexagonalPortrayal2D(0.3)
		{
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
			{
				paint = new Color(10, 10, 10);			
			    super.draw(object, graphics, info);
			}
		});
		
		
		obstaclesPortrayal.setField(simulation.obstacleMap);
		obstaclesPortrayal.setMap(new sim.util.gui.SimpleColorMap(
				0,
				1,
				new Color(0,0,0,0),
				new Color(0,0,255,255)
				));

		terrainPortrayal.setField(simulation.terrainMap);
		terrainPortrayal.setMap(new sim.util.gui.SimpleColorMap(
				0,
				2,// Constants.TerrainType.GRAVEL
				new Color(0,0,0,0),
				new Color(0,255,0,255)
				));
        
		wallPortrayal.setField(simulation.wallMap);
		wallPortrayal.setMap(new sim.util.gui.SimpleColorMap(
				0,
				1,
				new Color(0,0,0,0),
				new Color(255,0,0,255)
				));		
		
		// reschedule the displayer
		display.reset();
		// redraw the display
		display.repaint();
	}
	
	

    public void init(Controller c)
        {
        super.init(c);

        // make the displayer
        display = new Display2D(1500,1000,this);
        // turn off clipping
        display.setClipping(false);
        display.setBackdrop(new Color(0,150,255));

        displayFrame = display.createFrame();
        displayFrame.setTitle("Environment Display");
        c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
        displayFrame.setVisible(true);
		
		//adding the different layers to the display
		display.attach(terrainPortrayal,"Terrain");
		display.attach(obstaclesPortrayal,"Obstacles");		
        display.attach(environmentPortrayal, "Environment" );
        display.attach(wallPortrayal,"Wall");	
        
        System.out.println("COModelWithUI.init is called!");
        }
    
    
    

    public void quit()
        {
        super.quit();

        if (displayFrame!=null) displayFrame.dispose();
        displayFrame = null;
        display = null;
        }
    
    
    
    public Object getSimulationInspectedObject(){return state;}
    
    public Inspector getInspector()
    {
    	Inspector i = super.getInspector();
    	i.setVolatile(true);
    	return i;
    }   

}
