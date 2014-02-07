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

public class TailApproach extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup tailApproachCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup tailApproachSelfSeparationAlgorithmGroup = new ButtonGroup();
	private JTextField txtTailApproachTimes;
	private JTextField tailApproachOffsetTextField;

	public TailApproach()
	{   
		setLayout(null);
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(10, 26, 290, 31);
			this.add(splitPane);
			
			JCheckBox chckbxTailApproachEncounter = new JCheckBox("TailApproach");
			chckbxTailApproachEncounter.setSelected(CONFIGURATION.tailApproachSelected);
			chckbxTailApproachEncounter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox chckbxTailApproachEncounter = (JCheckBox)e.getSource();
					CONFIGURATION.tailApproachSelected=chckbxTailApproachEncounter.isSelected();
				}
			});
			splitPane.setLeftComponent(chckbxTailApproachEncounter);
			
			JSplitPane splitPane_1 = new JSplitPane();
			splitPane_1.setResizeWeight(0.6);
			splitPane.setRightComponent(splitPane_1);
			
			JLabel lblX = new JLabel("X");
			splitPane_1.setLeftComponent(lblX);
			
			JSplitPane splitPane_2 = new JSplitPane();
			splitPane_2.setResizeWeight(0.8);
			splitPane_1.setRightComponent(splitPane_2);
			
			txtTailApproachTimes = new JTextField();
			txtTailApproachTimes.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField txtTailApproachTimes=(JTextField) e.getSource();
					CONFIGURATION.tailApproachTimes=Integer.parseInt(txtTailApproachTimes.getText());
				}
			});
			txtTailApproachTimes.setText("1");
			splitPane_2.setLeftComponent(txtTailApproachTimes);
			txtTailApproachTimes.setColumns(5);
			
			JButton btnConfig = new JButton("Config");
			btnConfig.setHorizontalAlignment(SwingConstants.RIGHT);
			btnConfig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						IntruderConfig dialog = new IntruderConfig();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setName("TailApproachEncounter--IntruderConfig");
						dialog.textFieldInit("TailApproachEncounter--IntruderConfig");
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
			JRadioButton rdbtnIsrightside_2 = new JRadioButton("IsRightSide");
			rdbtnIsrightside_2.setBounds(10, 66, 105, 23);
			rdbtnIsrightside_2.setSelected(CONFIGURATION.tailApproachIsRightSide);
			this.add(rdbtnIsrightside_2);
			rdbtnIsrightside_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.tailApproachIsRightSide = true;
					}
					else
					{
						CONFIGURATION.tailApproachIsRightSide = false;
					}
				}
			});
			
			JLabel lblOffset_1 = new JLabel("Offset");
			lblOffset_1.setBounds(174, 70, 44, 15);
			this.add(lblOffset_1);
			
			tailApproachOffsetTextField = new JTextField();
			tailApproachOffsetTextField.setBounds(236, 68, 64, 19);
			tailApproachOffsetTextField.setText(""+CONFIGURATION.tailApproachOffset/CONFIGURATION.lengthScale);
			this.add(tailApproachOffsetTextField);
			tailApproachOffsetTextField.setColumns(10);
			tailApproachOffsetTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField offsetTextField=(JTextField) e.getSource();
					CONFIGURATION.tailApproachOffset=Double.parseDouble(offsetTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
		}
	
		
		
		{
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(10, 97, 290, 53);
			this.add(AvoidanceAlgorithmSelectionPanel);
			AvoidanceAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnAVOAvoidanceAlgorithm = new JRadioButton("AVO");
			rdbtnAVOAvoidanceAlgorithm.setBounds(8, 22, 94, 23);
			rdbtnAVOAvoidanceAlgorithm.setSelected(CONFIGURATION.tailApproachCollisionAvoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnAVOAvoidanceAlgorithm);
			tailApproachCollisionAvoidanceAlgorithmGroup.add(rdbtnAVOAvoidanceAlgorithm);
			rdbtnAVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.tailApproachCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnSVOAvoidanceAlgorithm = new JRadioButton("SVO");
			rdbtnSVOAvoidanceAlgorithm.setBounds(108, 22, 94, 23);
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.tailApproachCollisionAvoidanceAlgorithmSelection == "SVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			tailApproachCollisionAvoidanceAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.tailApproachCollisionAvoidanceAlgorithmSelection = "SVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 22, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.tailApproachCollisionAvoidanceAlgorithmSelection == "None");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			tailApproachCollisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.tailApproachCollisionAvoidanceAlgorithmSelection = "None";
					}
				}
			});
		}
			
		
		{
			JPanel selfSeparationAlgorithmSelectionPanel = new JPanel();
			selfSeparationAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "SSA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			selfSeparationAlgorithmSelectionPanel.setBounds(10, 162, 290, 59);
			this.add(selfSeparationAlgorithmSelectionPanel);
			selfSeparationAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnSVOAvoidanceAlgorithm = new JRadioButton("SVO");
			rdbtnSVOAvoidanceAlgorithm.setBounds(8, 26, 94, 30);
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.tailApproachSelfSeparationAlgorithmSelection == "SVOAvoidanceAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			tailApproachSelfSeparationAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.tailApproachSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 30, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.tailApproachSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone);
			tailApproachSelfSeparationAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.tailApproachSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		{
			JLabel lblMaxspeed = new JLabel("MaxSpeed");
			lblMaxspeed.setBounds(12, 271, 82, 15);
			this.add(lblMaxspeed);
			
			
			JTextField maxSpeedTextField = new JTextField();
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxSpeed/CONFIGURATION.lengthScale));
			maxSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.tailApproachMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxSpeedTextField.setBounds(188, 269, 114, 19);
			this.add(maxSpeedTextField);
			maxSpeedTextField.setColumns(10);
			
			
			JLabel lblMinspeed = new JLabel("MinSpeed");
			lblMinspeed.setBounds(12, 300, 70, 19);
			this.add(lblMinspeed);
			
			
			JTextField minSpeedTextField = new JTextField();
			minSpeedTextField.setText(String.valueOf(CONFIGURATION.tailApproachMinSpeed/CONFIGURATION.lengthScale));
			minSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.tailApproachMinSpeed = new Double(minSpeedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			
			JLabel lblSpeed = new JLabel("Speed");
			lblSpeed.setBounds(12, 327, 45, 15);
			this.add(lblSpeed);
			
			JTextField speedTextField = new JTextField();
			speedTextField.setText(String.valueOf(CONFIGURATION.tailApproachSpeed/CONFIGURATION.lengthScale));
			speedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField speedTextField = (JTextField) e.getSource();
					CONFIGURATION.tailApproachSpeed = new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			speedTextField.setBounds(189, 327, 114, 19);
			this.add(speedTextField);
			speedTextField.setColumns(10);
			minSpeedTextField.setBounds(188, 300, 114, 19);
			this.add(minSpeedTextField);
			minSpeedTextField.setColumns(10);
			
			
			JLabel lblMaxClimb = new JLabel("MaxClimb");
			lblMaxClimb.setBounds(12, 354, 70, 19);
			this.add(lblMaxClimb);
			
			
			JTextField maxClimbTextField = new JTextField();
			maxClimbTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxClimb/CONFIGURATION.lengthScale));
			maxClimbTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					CONFIGURATION.tailApproachMaxClimb = new Double(maxClimbTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxClimbTextField.setBounds(188, 354, 114, 19);
			this.add(maxClimbTextField);
			maxClimbTextField.setColumns(10);
			
			JLabel lblMaxDescent = new JLabel("MaxDescent");
			lblMaxDescent.setBounds(12, 388, 101, 19);
			this.add(lblMaxDescent);
			
			
			JTextField maxDescentTextField = new JTextField();
			maxDescentTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxDescent/CONFIGURATION.lengthScale));
			maxDescentTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					CONFIGURATION.tailApproachMaxDescent = new Double(maxDescentTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxDescentTextField.setBounds(188, 388, 114, 19);
			this.add(maxDescentTextField);
			maxDescentTextField.setColumns(10);
			
			JLabel lblMaxturning = new JLabel("MaxTurning");
			lblMaxturning.setBounds(12, 419, 82, 15);
			this.add(lblMaxturning);
			
			JTextField maxTurningTextField = new JTextField();
			maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.tailApproachMaxTurning)*100)/100.0));
			maxTurningTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxTurningTextField = (JTextField) e.getSource();
					CONFIGURATION.tailApproachMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
				}
			});
			maxTurningTextField.setBounds(189, 419, 114, 19);
			this.add(maxTurningTextField);
			maxTurningTextField.setColumns(10);
		}

    }

	public TailApproach(LayoutManager layout) 
	{
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public TailApproach(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public TailApproach(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
