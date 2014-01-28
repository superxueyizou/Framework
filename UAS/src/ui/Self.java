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
	private JTextField minSpeedTextField;
	private JTextField maxClimbTextField;
	private JTextField maxDescentTextField;

	public Self() 
	{
		setLayout(null);
	
		
		{
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 30, 290, 53);
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
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 102, 290, 62);
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
		lblMaxspeed.setBounds(12, 188, 82, 15);
		this.add(lblMaxspeed);
		
		
		maxSpeedTextField = new JTextField();
		maxSpeedTextField.setText(String.valueOf(CONFIGURATION.selfMaxSpeed/CONFIGURATION.lengthScale));
		maxSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxSpeedTextField.setBounds(188, 186, 114, 19);
		this.add(maxSpeedTextField);
		maxSpeedTextField.setColumns(10);
		
		
		JLabel lblMinspeed = new JLabel("MinSpeed");
		lblMinspeed.setBounds(12, 217, 70, 19);
		this.add(lblMinspeed);
		
		
		minSpeedTextField = new JTextField();
		minSpeedTextField.setText(String.valueOf(CONFIGURATION.selfMinSpeed/CONFIGURATION.lengthScale));
		minSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField minSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMinSpeed = new Double(minSpeedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		
		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(12, 244, 45, 15);
		this.add(lblSpeed);
		
		speedTextField = new JTextField();
		speedTextField.setText(String.valueOf(CONFIGURATION.selfSpeed/CONFIGURATION.lengthScale));
		speedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField speedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfSpeed = new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		speedTextField.setBounds(189, 244, 114, 19);
		this.add(speedTextField);
		speedTextField.setColumns(10);
		minSpeedTextField.setBounds(188, 217, 114, 19);
		this.add(minSpeedTextField);
		minSpeedTextField.setColumns(10);
		
		
		JLabel lblMaxClimb = new JLabel("MaxClimb");
		lblMaxClimb.setBounds(12, 271, 70, 19);
		this.add(lblMaxClimb);
		
		
		maxClimbTextField = new JTextField();
		maxClimbTextField.setText(String.valueOf(CONFIGURATION.selfMaxClimb/CONFIGURATION.lengthScale));
		maxClimbTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxClimbTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxClimb = new Double(maxClimbTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxClimbTextField.setBounds(188, 271, 114, 19);
		this.add(maxClimbTextField);
		maxClimbTextField.setColumns(10);
		
		JLabel lblMaxDescent = new JLabel("MaxDescent");
		lblMaxDescent.setBounds(12, 305, 101, 19);
		this.add(lblMaxDescent);
		
		
		maxDescentTextField = new JTextField();
		maxDescentTextField.setText(String.valueOf(CONFIGURATION.selfMaxDescent/CONFIGURATION.lengthScale));
		maxDescentTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxDescentTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxDescent = new Double(maxDescentTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxDescentTextField.setBounds(188, 305, 114, 19);
		this.add(maxDescentTextField);
		maxDescentTextField.setColumns(10);
		
		JLabel lblMaxturning = new JLabel("MaxTurning");
		lblMaxturning.setBounds(12, 336, 82, 15);
		this.add(lblMaxturning);
		
		maxTurningTextField = new JTextField();
		maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.selfMaxTurning)*100)/100.0));
		maxTurningTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxTurningTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
			}
		});
		maxTurningTextField.setBounds(189, 336, 114, 19);
		this.add(maxTurningTextField);
		maxTurningTextField.setColumns(10);
		
		
		JLabel lblMaxacceleration = new JLabel("MaxAcceleration");
		lblMaxacceleration.setBounds(12, 426, 116, 15);
		this.add(lblMaxacceleration);
		
		maxAccelerationTextField = new JTextField();
		maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.selfMaxAcceleration/CONFIGURATION.lengthScale));
		maxAccelerationTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxAccelerationTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxAcceleration = new Double(maxAccelerationTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxAccelerationTextField.setBounds(189, 426, 114, 19);
		this.add(maxAccelerationTextField);
		maxAccelerationTextField.setColumns(10);
		
		JLabel lblMaxdecceleration = new JLabel("MaxDeceleration");
		lblMaxdecceleration.setBounds(12, 453, 119, 15);
		this.add(lblMaxdecceleration);
		
		maxDecelerationTextField = new JTextField();
		maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.selfMaxDeceleration/CONFIGURATION.lengthScale));
		maxDecelerationTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxDecelerationTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxDeceleration = new Double(maxDecelerationTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxDecelerationTextField.setBounds(189, 453, 114, 19);
		this.add(maxDecelerationTextField);
		maxDecelerationTextField.setColumns(10);
		
		JLabel lblViewingRange = new JLabel("ViewingRange");
		lblViewingRange.setBounds(12, 484, 101, 15);
		this.add(lblViewingRange);
		
		viewingRangeTextField = new JTextField();
		viewingRangeTextField.setText(String.valueOf(CONFIGURATION.selfViewingRange/CONFIGURATION.lengthScale));
		viewingRangeTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField viewingRangeTextField = (JTextField) e.getSource();
				CONFIGURATION.selfViewingRange = new Double(viewingRangeTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		viewingRangeTextField.setBounds(189, 484, 114, 19);
		this.add(viewingRangeTextField);
		viewingRangeTextField.setColumns(10);
		
		JLabel lblViewingAngle = new JLabel("ViewingAngle");
		lblViewingAngle.setBounds(12, 511, 96, 15);
		this.add(lblViewingAngle);
		
		viewingAngleTextField = new JTextField();
		viewingAngleTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.selfViewingAngle)*100)/100.0));
		viewingAngleTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField viewingAngleTextField = (JTextField) e.getSource();
				CONFIGURATION.selfViewingAngle = Math.toRadians(new Double(viewingAngleTextField.getText()));
			}
		});
		viewingAngleTextField.setBounds(189, 511, 114, 19);
		this.add(viewingAngleTextField);
		viewingAngleTextField.setColumns(10);
		
		JLabel lblSensitivityForCollisions = new JLabel("SensitivityForCollisions");
		lblSensitivityForCollisions.setBounds(12, 538, 165, 15);
		this.add(lblSensitivityForCollisions);
		
		sensitivityForCollisionTextField = new JTextField();
		sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.selfSensitivityForCollisions/CONFIGURATION.lengthScale));
		sensitivityForCollisionTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField sensitivityForCollisionTextField = (JTextField) e.getSource();
				CONFIGURATION.selfSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		sensitivityForCollisionTextField.setBounds(189, 540, 114, 19);
		this.add(sensitivityForCollisionTextField);
		sensitivityForCollisionTextField.setColumns(10);
		
		JLabel lblSafetyradius = new JLabel("SafetyRadius");
		lblSafetyradius.setBounds(12, 578, 94, 15);
		this.add(lblSafetyradius);
		
		safetyRadiusTextField = new JTextField();
		safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.selfSafetyRadius/CONFIGURATION.lengthScale));
		safetyRadiusTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField safetyRadiusTextField = (JTextField) e.getSource();
				CONFIGURATION.selfSafetyRadius = new Double(safetyRadiusTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		safetyRadiusTextField.setBounds(189, 580, 114, 19);
		this.add(safetyRadiusTextField);
		safetyRadiusTextField.setColumns(10);
		
		JLabel lblAlpha = new JLabel("Alpha");
		lblAlpha.setBounds(12, 605, 40, 15);
		this.add(lblAlpha);
			
		alphaTextField = new JTextField();
		alphaTextField.setText(String.valueOf(CONFIGURATION.selfAlpha));
		alphaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField alfaTextField = (JTextField) e.getSource();
				CONFIGURATION.selfAlpha = new Double(alfaTextField.getText());
				
			}
		});
		alphaTextField.setBounds(189, 607, 114, 19);
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
