/**
 * 
 */
package dominant;

import modeling.COModelWithUI;
import sim.display.Console;
import ui.SAAConfigurator;

import java.awt.EventQueue;

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
    	COModelWithUI vid = new COModelWithUI();
    	Console.DEFAULT_WIDTH=380;
    	Console.DEFAULT_HEIGHT=380;
    	Console c = new Console(vid);    	
		c.setVisible(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SAAConfigurator frame = new SAAConfigurator();
					frame.setBounds(1500+40, 380, 380, 700);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

    }

}
