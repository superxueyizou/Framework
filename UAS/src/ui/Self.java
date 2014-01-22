package ui;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import tools.CONFIGURATION;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Self extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup selfCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup selfSelfSeparationAlgorithmGroup = new ButtonGroup();
	
	private JTextField maxSpeedTextField;
	private JTextField maxAccelerationTextField;
	private JTextField maxDecelerationTextField;
	private JTextField maxTurningTextField;
	private JTextField speedTextField;
	private JTextField viewingRangeTextField;
	private JTextField viewingAngleTextField;
	private JTextField sensitivityForCollisionTextField;
	private JTextField safetyRadiusTextField;
	private JTextField alphaTextField;

	public Self() 
	{
		setLayout(null);
		
		JRadioButton rdbtnCAEnable = new JRadioButton("CA Enable?");
		rdbtnCAEnable.setBounds(12, 19, 102, 15);
		this.add(rdbtnCAEnable);
		rdbtnCAEnable.setSelected(CONFIGURATION.collisionAvoidanceEnabler);
		rdbtnCAEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					CONFIGURATION.collisionAvoidanceEnabler = true;
				} else {
					
					CONFIGURATION.collisionAvoidanceEnabler = false;
				}
			}
		});
		
		
		JRadioButton rdbtnSSEnable = new JRadioButton("SS Enable?");
		rdbtnSSEnable.setBounds(164, 19, 102, 15);
		this.add(rdbtnSSEnable);
		rdbtnSSEnable.setSelected(CONFIGURATION.selfSeparationEnabler);
		rdbtnSSEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					CONFIGURATION.selfSeparationEnabler = true;
				} else {
					
					CONFIGURATION.selfSeparationEnabler = false;
				}
			}
		});
		// TODO Auto-generated constructor stub
	
		JLabel lblModelbuilderSetting = new JLabel("ModelBuilder Setting");
		lblModelbuilderSetting.setBounds(12, 53, 165, 15);
		this.add(lblModelbuilderSetting);
			
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String result = JOptionPane.showInputDialog(null, "copy and paste:", "Genome",JOptionPane.PLAIN_MESSAGE).trim();
//					System.out.println(result);
				if(!result.isEmpty())
				{
					String[] pArr= result.split("\\s+");
//						System.out.println(pArr[1]);
													
					CONFIGURATION.selfDestDist= Double.parseDouble(pArr[0]);
					CONFIGURATION.selfDestAngle=Double.parseDouble(pArr[1]);
					
					CONFIGURATION.headOnSelected= Double.parseDouble(pArr[2])>0? true: false;
					CONFIGURATION.headOnOffset=Double.parseDouble(pArr[3]);
					CONFIGURATION.headOnIsRightSide= Double.parseDouble(pArr[4])>0? true: false;			
					CONFIGURATION.headOnSpeed=Double.parseDouble(pArr[5]);
					
		    		CONFIGURATION.crossingSelected = Double.parseDouble(pArr[6])>0? true: false;
		    		CONFIGURATION.crossingEncounterAngle=Double.parseDouble(pArr[7]);
		    		CONFIGURATION.crossingIsRightSide= Double.parseDouble(pArr[8])>0? true: false;
		    		CONFIGURATION.crossingSpeed =Double.parseDouble(pArr[9]);
		    		
		    		CONFIGURATION.tailApproachSelected = Double.parseDouble(pArr[10])>0? true: false;
		    		CONFIGURATION.tailApproachOffset= Double.parseDouble(pArr[11]);
		    		CONFIGURATION.tailApproachIsRightSide=Double.parseDouble(pArr[12])>0? true: false;
		    		CONFIGURATION.tailApproachSpeed =Double.parseDouble(pArr[13]);
		    		
				}
//					System.out.println(CONFIGURATION.selfDestDist);
			}
		});
		btnLoad.setBounds(226, 48, 69, 25);
		this.add(btnLoad);
		
		JLabel lblDestDist = new JLabel("Dest Dist");
		lblDestDist.setBounds(12, 79, 65, 15);
		this.add(lblDestDist);
		
		JLabel lblDestAngle = new JLabel("Dest Angle");
		lblDestAngle.setBounds(12, 106, 77, 15);
		this.add(lblDestAngle);
		
		JSlider destDistSlider = new JSlider();
		destDistSlider.setValue((int)CONFIGURATION.selfDestDist);
		destDistSlider.setMaximum(70);
		destDistSlider.setMinimum(20);
		destDistSlider.setBounds(95, 80, 200, 16);
		this.add(destDistSlider);
		destDistSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				CONFIGURATION.selfDestDist = source.getValue();
			}
		});
		
		JSlider destAngleSlider = new JSlider();
		destAngleSlider.setValue((int)Math.toDegrees(CONFIGURATION.selfDestAngle));
		destAngleSlider.setMinimum(-180);
		destAngleSlider.setMaximum(180);
		destAngleSlider.setBounds(95, 106, 200, 16);
		this.add(destAngleSlider);
		destAngleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				CONFIGURATION.selfDestAngle = Math.toRadians(source.getValue());
			}
		});
	
		
		{
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 134, 290, 53);
			this.add(AvoidanceAlgorithmSelectionPanel);
			AvoidanceAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnAVOAvoidanceAlgorithm = new JRadioButton("AVO");
			rdbtnAVOAvoidanceAlgorithm.setBounds(8, 22, 94, 23);
			rdbtnAVOAvoidanceAlgorithm.setSelected(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnAVOAvoidanceAlgorithm);
			selfCollisionAvoidanceAlgorithmGroup.add(rdbtnAVOAvoidanceAlgorithm);
			rdbtnAVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 22, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection == "None");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			selfCollisionAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		
		{
			JPanel selfSeparationAlgorithmSelectionPanel = new JPanel();
			selfSeparationAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "SSA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 199, 290, 62);
			this.add(selfSeparationAlgorithmSelectionPanel);
			selfSeparationAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnSVOAvoidanceAlgorithm = new JRadioButton("SVO");
			rdbtnSVOAvoidanceAlgorithm.setBounds(8, 24, 94, 30);
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.selfSelfSeparationAlgorithmSelection == "SVOAvoidanceAlgorithm");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			selfSelfSeparationAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSelfSeparationAlgorithmSelection = "SVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(209, 30, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.selfSelfSeparationAlgorithmSelection == "None");
			selfSeparationAlgorithmSelectionPanel.add(rdbtnNone);
			selfSelfSeparationAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSelfSeparationAlgorithmSelection = "None";
					}
				}
			});
		}
		
		
		JLabel lblMaxspeed = new JLabel("MaxSpeed");
		lblMaxspeed.setBounds(12, 285, 73, 15);
		this.add(lblMaxspeed);
		
		JLabel lblMaxacceleration = new JLabel("MaxAcceleration");
		lblMaxacceleration.setBounds(12, 312, 116, 15);
		this.add(lblMaxacceleration);
		
		JLabel lblMaxdecceleration = new JLabel("MaxDeceleration");
		lblMaxdecceleration.setBounds(12, 339, 119, 15);
		this.add(lblMaxdecceleration);
		
		JLabel lblMaxturning = new JLabel("MaxTurning");
		lblMaxturning.setBounds(12, 365, 82, 15);
		this.add(lblMaxturning);
		
		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(12, 392, 45, 15);
		this.add(lblSpeed);
		
		JLabel lblViewingRange = new JLabel("ViewingRange");
		lblViewingRange.setBounds(12, 421, 101, 15);
		this.add(lblViewingRange);
		
		JLabel lblViewingAngle = new JLabel("ViewingAngle");
		lblViewingAngle.setBounds(12, 448, 96, 15);
		this.add(lblViewingAngle);
		
		JLabel lblSensitivityForCollisions = new JLabel("SensitivityForCollisions");
		lblSensitivityForCollisions.setBounds(12, 475, 165, 15);
		this.add(lblSensitivityForCollisions);
		
		JLabel lblSafetyradius = new JLabel("SafetyRadius");
		lblSafetyradius.setBounds(12, 515, 94, 15);
		this.add(lblSafetyradius);
		
		JLabel lblAlpha = new JLabel("Alpha");
		lblAlpha.setBounds(12, 542, 40, 15);
		this.add(lblAlpha);
		
		
		maxSpeedTextField = new JTextField();
		maxSpeedTextField.setText(String.valueOf(CONFIGURATION.selfMaxSpeed));
		maxSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxSpeed = new Double(maxSpeedTextField.getText());
			}
		});
		
		
		maxSpeedTextField.setBounds(189, 285, 114, 19);
		this.add(maxSpeedTextField);
		maxSpeedTextField.setColumns(10);
		
		maxAccelerationTextField = new JTextField();
		maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.selfMaxAcceleration));
		maxAccelerationTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxAccelerationTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxAcceleration = new Double(maxAccelerationTextField.getText());
			}
		});
		maxAccelerationTextField.setBounds(189, 312, 114, 19);
		this.add(maxAccelerationTextField);
		maxAccelerationTextField.setColumns(10);
		
		maxDecelerationTextField = new JTextField();
		maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.selfMaxDeceleration));
		maxDecelerationTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxDecelerationTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxDeceleration = new Double(maxDecelerationTextField.getText());
			}
		});
		maxDecelerationTextField.setBounds(189, 339, 114, 19);
		this.add(maxDecelerationTextField);
		maxDecelerationTextField.setColumns(10);
		
		maxTurningTextField = new JTextField();
		maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.selfMaxTurning))));
		maxTurningTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxTurningTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
			}
		});
		maxTurningTextField.setBounds(189, 365, 114, 19);
		this.add(maxTurningTextField);
		maxTurningTextField.setColumns(10);
		
		speedTextField = new JTextField();
		speedTextField.setText(String.valueOf(CONFIGURATION.selfSpeed));
		speedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField speedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfSpeed = new Double(speedTextField.getText());
			}
		});
		speedTextField.setBounds(189, 392, 114, 19);
		this.add(speedTextField);
		speedTextField.setColumns(10);
		
		viewingRangeTextField = new JTextField();
		viewingRangeTextField.setText(String.valueOf(CONFIGURATION.selfViewingRange));
		viewingRangeTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField viewingRangeTextField = (JTextField) e.getSource();
				CONFIGURATION.selfViewingRange = new Double(viewingRangeTextField.getText());
			}
		});
		viewingRangeTextField.setBounds(189, 421, 114, 19);
		this.add(viewingRangeTextField);
		viewingRangeTextField.setColumns(10);
		
		viewingAngleTextField = new JTextField();
		viewingAngleTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.selfViewingAngle))));
		viewingAngleTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField viewingAngleTextField = (JTextField) e.getSource();
				CONFIGURATION.selfViewingAngle = Math.toRadians(new Double(viewingAngleTextField.getText()));
			}
		});
		viewingAngleTextField.setBounds(189, 448, 114, 19);
		this.add(viewingAngleTextField);
		viewingAngleTextField.setColumns(10);
		
		sensitivityForCollisionTextField = new JTextField();
		sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.selfSensitivityForCollisions));
		sensitivityForCollisionTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField sensitivityForCollisionTextField = (JTextField) e.getSource();
				CONFIGURATION.selfSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
			}
		});
		sensitivityForCollisionTextField.setBounds(189, 477, 114, 19);
		this.add(sensitivityForCollisionTextField);
		sensitivityForCollisionTextField.setColumns(10);
		
		safetyRadiusTextField = new JTextField();
		safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.selfSafetyRadius));
		safetyRadiusTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField safetyRadiusTextField = (JTextField) e.getSource();
				CONFIGURATION.selfSafetyRadius = new Double(safetyRadiusTextField.getText());
			}
		});
		safetyRadiusTextField.setBounds(189, 517, 114, 19);
		this.add(safetyRadiusTextField);
		safetyRadiusTextField.setColumns(10);
			
		alphaTextField = new JTextField();
		alphaTextField.setText(String.valueOf(CONFIGURATION.selfAlpha));
		alphaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField alfaTextField = (JTextField) e.getSource();
				CONFIGURATION.selfAlpha = new Double(alfaTextField.getText());
				
			}
		});
		alphaTextField.setBounds(189, 544, 114, 19);
		this.add(alphaTextField);
		alphaTextField.setColumns(10);
	}

	public Self(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Self(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Self(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
