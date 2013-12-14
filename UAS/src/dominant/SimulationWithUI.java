/**
 * 
 */
package dominant;

import modeling.SAAModelWithUI;
import sim.display.Console;
import ui.SAAConfigurator;

/**
 * @author xueyi
 *Simulation with UI but without the use of GA
 */
public class SimulationWithUI {

	/**
	 * @param args
	 */
	
    public static void main(String[] args)
    {
    	SAAModelWithUI saaModelWithUI = new SAAModelWithUI();
    	saaModelWithUI.setDisplayBound(1500, 1050);
    	
    	Console c = new Console(saaModelWithUI); 
    	c.setBounds(1500+80, 20, 340, 380); // for windows: c.setBounds(1500+40, 0, 380, 380);
		c.setVisible(true);
		
		SAAConfigurator frame = new SAAConfigurator();
		frame.setBounds(1500+80, 404, 340,742); // for windows: frame.setBounds(1500+40, 380, 380,700);
		frame.setVisible(true);

		
    }
}
