package ui;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import tools.CONFIGURATION;


public class HeadOn extends JPanel 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup headOnCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup headOnSelfSeparationAlgorithmGroup = new ButtonGroup();
	private JTextField txtHeadontimes;
	private JTextField headOnOffsetTextField;

	public HeadOn() 
	{
		setLayout(null);
		
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(12, 39, 290, 31);
			
			JCheckBox chckbxHeadonencounter = new JCheckBox("HeadOn");
			chckbxHeadonencounter.setSelected(CONFIGURATION.headOnSelected);
			chckbxHeadonencounter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox chckbxHeadonencounter = (JCheckBox)e.getSource();
					CONFIGURATION.headOnSelected=chckbxHeadonencounter.isSelected();
				}
			});
			splitPane.setLeftComponent(chckbxHeadonencounter);
			
			JSplitPane splitPane_1 = new JSplitPane();
			splitPane_1.setResizeWeight(0.6);
			splitPane.setRightComponent(splitPane_1);
			
			JLabel lblX = new JLabel("X");
			splitPane_1.setLeftComponent(lblX);
			
			JSplitPane splitPane_2 = new JSplitPane();
			splitPane_2.setResizeWeight(0.8);
			splitPane_1.setRightComponent(splitPane_2);
			
			txtHeadontimes = new JTextField();
			txtHeadontimes.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField txtHeadontimes=(JTextField) e.getSource();
					CONFIGURATION.headOnTimes=Integer.parseInt(txtHeadontimes.getText());
				}
			});
			txtHeadontimes.setText("1");
			splitPane_2.setLeftComponent(txtHeadontimes);
			txtHeadontimes.setColumns(5);
			
			JButton btnConfig = new JButton("Config");			
			btnConfig.setHorizontalAlignment(SwingConstants.RIGHT);
			btnConfig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try 
					{
						    //System.out.println(e.getActionCommand());
							IntruderConfig dialog = new IntruderConfig();
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setName("HeadonEncounter--IntruderConfig");
							dialog.textFieldInit("HeadOnEncounter--IntruderConfig");
							dialog.setModal(true);
							//dialog.setBounds(x, y, width, height)
							dialog.setVisible(true);
					}
						
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
					
				}
			});
			splitPane_2.setRightComponent(btnConfig);
			this.add(splitPane);
			
		}
		
		
		{
			JRadioButton rdbtnIsrightside = new JRadioButton("IsRightSide");
			rdbtnIsrightside.setBounds(12, 92, 105, 23);
			rdbtnIsrightside.setSelected(CONFIGURATION.headOnIsRightSide);
			rdbtnIsrightside.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnIsRightSide = true;
					}
					else
					{
						CONFIGURATION.headOnIsRightSide = false;
					}
				}
			});
			this.add(rdbtnIsrightside);
			
			JLabel lblOffset = new JLabel("Offset");
			lblOffset.setBounds(171, 96, 44, 15);
			this.add(lblOffset);
			
			headOnOffsetTextField = new JTextField();
			headOnOffsetTextField.setBounds(233, 94, 69, 19);
			headOnOffsetTextField.setText(""+CONFIGURATION.headOnOffset);
			headOnOffsetTextField.setColumns(10);
			headOnOffsetTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField offsetTextField=(JTextField) e.getSource();
					CONFIGURATION.headOnOffset=Double.parseDouble(offsetTextField.getText());
				}
			});
			this.add(headOnOffsetTextField);
		}
		
	
		
		
		{
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 153, 290, 53);
			this.add(AvoidanceAlgorithmSelectionPanel);
			AvoidanceAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnAVOAvoidanceAlgorithm = new JRadioButton("AVO");
			rdbtnAVOAvoidanceAlgorithm.setBounds(8, 22, 94, 23);
			rdbtnAVOAvoidanceAlgorithm.setSelected(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnAVOAvoidanceAlgorithm);
			headOnCollisionAvoidanceAlgorithmGroup.add(rdbtnAVOAvoidanceAlgorithm);
			rdbtnAVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 22, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection == "None");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			headOnCollisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		
		{
			JPanel selfSeparationAlgorithmSelectionPanel = new JPanel();
			selfSeparationAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "SSA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 234, 290, 59);
			this.add(selfSeparationAlgorithmSelectionPanel);
			selfSeparationAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnSVOAvoidanceAlgorithm = new JRadioButton("SVO");
			rdbtnSVOAvoidanceAlgorithm.setBounds(8, 26, 94, 30);
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.headOnSelfSeparationAlgorithmSelection == "SVOAvoidanceAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			headOnSelfSeparationAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 30, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.headOnSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone);
			headOnSelfSeparationAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
						
	}

	public HeadOn(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public HeadOn(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public HeadOn(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
