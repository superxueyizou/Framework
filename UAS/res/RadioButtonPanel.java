/**
 * 
 */
package ui;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @author Xueyi
 *
 */
public class RadioButtonPanel extends JPanel {

	/**
	 * 
	 */
	private ButtonGroup group;
	public RadioButtonPanel(String title, String[] options) {
		// TODO Auto-generated constructor stub
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		group=new ButtonGroup();
		for (int i=0; options!=null && i<options.length; i++)
		{
			JRadioButton b= new JRadioButton(options[i]);
			b.setActionCommand(options[i]);
			add(b);
			group.add(b);
			b.setSelected(i == 0);
		}
	}

	/**
	 * @param arg0
	 */
	public RadioButtonPanel(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public RadioButtonPanel(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RadioButtonPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		
	}
	
	public String getSelection()
	{
		return group.getSelection().getActionCommand();
	}

}
