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
    	Console c = new Console(vid); 
    	c.setBounds(1500+80, 0, 340, 380); // for windows: c.setBounds(1500+40, 0, 380, 380);
		c.setVisible(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SAAConfigurator frame = new SAAConfigurator();
					frame.setBounds(1500+80, 400, 340,690); // for windows: frame.setBounds(1500+40, 380, 380,700);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

    }

}
