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

public class Crossing extends JPanel 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup crossingCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup crossingSelfSeparationAlgorithmGroup = new ButtonGroup();
	private JTextField txtCrossingtimes;
	private JTextField crossingAngleTextField;

	public Crossing()
	{  
		setLayout(null);
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(12, 36, 290, 31);
			this.add(splitPane);
			
			JCheckBox chckbxCrossingEncounter = new JCheckBox("Crossing");
			chckbxCrossingEncounter.setSelected(CONFIGURATION.crossingSelected);
			chckbxCrossingEncounter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox chckbxCrossingEncounter = (JCheckBox)e.getSource();
					CONFIGURATION.crossingSelected=chckbxCrossingEncounter.isSelected();
				}
			});
			splitPane.setLeftComponent(chckbxCrossingEncounter);
			
			JSplitPane splitPane_1 = new JSplitPane();
			splitPane_1.setResizeWeight(0.6);
			splitPane.setRightComponent(splitPane_1);
			
			JLabel lblX = new JLabel("X");
			splitPane_1.setLeftComponent(lblX);
			
			JSplitPane splitPane_2 = new JSplitPane();
			splitPane_2.setResizeWeight(0.8);
			splitPane_1.setRightComponent(splitPane_2);
			
			txtCrossingtimes = new JTextField();
			txtCrossingtimes.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField txtCrossingtimes=(JTextField) e.getSource();
					CONFIGURATION.crossingTimes=Integer.parseInt(txtCrossingtimes.getText());
				}
			});
			txtCrossingtimes.setText("1");
			splitPane_2.setLeftComponent(txtCrossingtimes);
			txtCrossingtimes.setColumns(5);
			
			JButton btnConfig = new JButton("Config");
			btnConfig.setHorizontalAlignment(SwingConstants.RIGHT);
			btnConfig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						IntruderConfig dialog = new IntruderConfig();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setName("CrossingEncounter--IntruderConfig");
						dialog.textFieldInit("CrossingEncounter--IntruderConfig");
						dialog.setModal(true);
						dialog.setVisible(true);
						

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}
			});
			splitPane_2.setRightComponent(btnConfig);
			this.add(splitPane);
		
		}
		
		{
			JLabel lblEncounterangel = new JLabel("Angle");
			lblEncounterangel.setBounds(152, 79, 50, 15);
			this.add(lblEncounterangel);
			
			JRadioButton rdbtnIsrightside_1 = new JRadioButton("IsRightSide");
			rdbtnIsrightside_1.setBounds(12, 75, 105, 23);
			rdbtnIsrightside_1.setSelected(CONFIGURATION.crossingIsRightSide);
			this.add(rdbtnIsrightside_1);
			rdbtnIsrightside_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingIsRightSide = true;
					}
					else
					{
						CONFIGURATION.crossingIsRightSide = false;
					}
				}
			});
			
			crossingAngleTextField = new JTextField();
			crossingAngleTextField.setBounds(220, 79, 82, 19);
			crossingAngleTextField.setText(""+Math.round(Math.toDegrees(CONFIGURATION.crossingEncounterAngle)));
			this.add(crossingAngleTextField);
			crossingAngleTextField.setColumns(10);
			crossingAngleTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField offsetTextField=(JTextField) e.getSource();
					CONFIGURATION.crossingEncounterAngle= Math.toRadians(Double.parseDouble(offsetTextField.getText()));
				}
			});
		}
		
		
		
		{
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 153, 290, 53);
			this.add(AvoidanceAlgorithmSelectionPanel);
			AvoidanceAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnAVOAvoidanceAlgorithm = new JRadioButton("AVO");
			rdbtnAVOAvoidanceAlgorithm.setBounds(8, 22, 94, 23);
			rdbtnAVOAvoidanceAlgorithm.setSelected(CONFIGURATION.crossingCollisionAvoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnAVOAvoidanceAlgorithm);
			crossingCollisionAvoidanceAlgorithmGroup.add(rdbtnAVOAvoidanceAlgorithm);
			rdbtnAVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 22, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.crossingCollisionAvoidanceAlgorithmSelection == "None");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			crossingCollisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingCollisionAvoidanceAlgorithmSelection = "None";
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
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.crossingSelfSeparationAlgorithmSelection == "SVOAvoidanceAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			crossingSelfSeparationAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 30, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.crossingSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone);
			crossingSelfSeparationAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
		
    }
	
	public Crossing(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Crossing(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Crossing(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
