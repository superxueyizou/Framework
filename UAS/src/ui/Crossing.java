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
			crossingAngleTextField.setText(""+Math.round(Math.toDegrees(CONFIGURATION.crossingEncounterAngle)*100)/100.0);
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
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 106, 290, 53);
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
			
			JRadioButton rdbtnSVOAvoidanceAlgorithm = new JRadioButton("SVO");
			rdbtnSVOAvoidanceAlgorithm.setBounds(108, 22, 94, 23);
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.crossingCollisionAvoidanceAlgorithmSelection == "SVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			crossingCollisionAvoidanceAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingCollisionAvoidanceAlgorithmSelection = "SVOAvoidanceAlgorithm";
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
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 187, 290, 59);
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
		
		{
			JLabel lblMaxspeed = new JLabel("MaxSpeed");
			lblMaxspeed.setBounds(12, 271, 82, 15);
			this.add(lblMaxspeed);
			
			
			JTextField maxSpeedTextField = new JTextField();
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.crossingMaxSpeed/CONFIGURATION.lengthScale));
			maxSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxSpeedTextField.setBounds(188, 269, 114, 19);
			this.add(maxSpeedTextField);
			maxSpeedTextField.setColumns(10);
			
			
			JLabel lblMinspeed = new JLabel("MinSpeed");
			lblMinspeed.setBounds(12, 300, 70, 19);
			this.add(lblMinspeed);
			
			
			JTextField minSpeedTextField = new JTextField();
			minSpeedTextField.setText(String.valueOf(CONFIGURATION.crossingMinSpeed/CONFIGURATION.lengthScale));
			minSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMinSpeed = new Double(minSpeedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			
			JLabel lblSpeed = new JLabel("Speed");
			lblSpeed.setBounds(12, 327, 45, 15);
			this.add(lblSpeed);
			
			JTextField speedTextField = new JTextField();
			speedTextField.setText(String.valueOf(CONFIGURATION.crossingSpeed/CONFIGURATION.lengthScale));
			speedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField speedTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingSpeed = new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
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
			maxClimbTextField.setText(String.valueOf(CONFIGURATION.crossingMaxClimb/CONFIGURATION.lengthScale));
			maxClimbTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMaxClimb = new Double(maxClimbTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxClimbTextField.setBounds(188, 354, 114, 19);
			this.add(maxClimbTextField);
			maxClimbTextField.setColumns(10);
			
			JLabel lblMaxDescent = new JLabel("MaxDescent");
			lblMaxDescent.setBounds(12, 388, 101, 19);
			this.add(lblMaxDescent);
			
			
			JTextField maxDescentTextField = new JTextField();
			maxDescentTextField.setText(String.valueOf(CONFIGURATION.crossingMaxDescent/CONFIGURATION.lengthScale));
			maxDescentTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMaxDescent = new Double(maxDescentTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxDescentTextField.setBounds(188, 388, 114, 19);
			this.add(maxDescentTextField);
			maxDescentTextField.setColumns(10);
			
			JLabel lblMaxturning = new JLabel("MaxTurning");
			lblMaxturning.setBounds(12, 419, 82, 15);
			this.add(lblMaxturning);
			
			JTextField maxTurningTextField = new JTextField();
			maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.crossingMaxTurning)*100)/100.0));
			maxTurningTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxTurningTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
				}
			});
			maxTurningTextField.setBounds(189, 419, 114, 19);
			this.add(maxTurningTextField);
			maxTurningTextField.setColumns(10);
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
