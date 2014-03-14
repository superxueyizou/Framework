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
import javax.swing.JCheckBox;

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
	private JTextField prefSpeedTextField;
	private JTextField safetyRadiusTextField;
	private JTextField alphaTextField;
	private JTextField minSpeedTextField;
	private JTextField maxClimbTextField;
	private JTextField maxDescentTextField;

	public Self() 
	{
		setLayout(null);
		
		{
			JPanel sensorSelectionPanel = new JPanel();
			sensorSelectionPanel.setBorder(new TitledBorder(null, "Sensor Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sensorSelectionPanel.setBounds(12, 12, 297, 125);
			add(sensorSelectionPanel);
			sensorSelectionPanel.setLayout(null);
			
			JCheckBox chckbxPerfectSensor = new JCheckBox("Perfect");
			chckbxPerfectSensor.setBounds(8, 20, 129, 23);
			sensorSelectionPanel.add(chckbxPerfectSensor);
			chckbxPerfectSensor.setSelected((CONFIGURATION.selfSensorSelection&0B10000) == 0B10000);
			chckbxPerfectSensor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B10000;
					}
				}
			});
			
			
			JCheckBox chckbxAdsb = new JCheckBox("ADS-B");
			chckbxAdsb.setBounds(144, 20, 129, 23);
			sensorSelectionPanel.add(chckbxAdsb);
			chckbxAdsb.setSelected((CONFIGURATION.selfSensorSelection&0B01000) == 0B01000);
			chckbxAdsb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B01000;
					}
				}
			});
			
			JCheckBox chckbxTcas = new JCheckBox("TCAS");
			chckbxTcas.setBounds(8, 47, 129, 23);
			sensorSelectionPanel.add(chckbxTcas);
			chckbxTcas.setSelected((CONFIGURATION.selfSensorSelection&0B00100) == 0B00100);
			chckbxTcas.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B00100;
					}
				}
			});
			
			JCheckBox chckbxRadar = new JCheckBox("Radar");
			chckbxRadar.setBounds(144, 47, 129, 23);
			sensorSelectionPanel.add(chckbxRadar);
			chckbxRadar.setSelected((CONFIGURATION.selfSensorSelection&0B00010) == 0B00010);
			chckbxRadar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B00010;
					}
				}
			});
			
			JCheckBox chckbxEoir = new JCheckBox("EO/IR");
			chckbxEoir.setBounds(8, 74, 129, 23);
			sensorSelectionPanel.add(chckbxEoir);
			chckbxEoir.setSelected((CONFIGURATION.selfSensorSelection&0B00001) == 0B00001);
			chckbxEoir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.selfSensorSelection |= 0B00001;
					}
				}
			});
		}
		
	
		
		{
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 144, 297, 53);
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

			
			JRadioButton rdbtnSVOAvoidanceAlgorithm = new JRadioButton("SVO");
			rdbtnSVOAvoidanceAlgorithm.setBounds(108, 22, 94, 23);
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection == "SVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			selfCollisionAvoidanceAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection = "SVOAvoidanceAlgorithm";
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
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 209, 297, 62);
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
		
		JPanel performancePanel = new JPanel();
		performancePanel.setBounds(12, 283, 297, 248);
		add(performancePanel);
		performancePanel.setLayout(null);
		
		JLabel lblMaxspeed = new JLabel("MaxSpeed");
		lblMaxspeed.setBounds(12, 12, 73, 15);
		performancePanel.add(lblMaxspeed);
		
		
		JLabel lblMinspeed = new JLabel("MinSpeed");
		lblMinspeed.setBounds(12, 37, 70, 19);
		performancePanel.add(lblMinspeed);
		
		JLabel lblPrefSpeed = new JLabel("PrefSpeed");
		lblPrefSpeed.setBounds(12, 64, 101, 15);
		performancePanel.add(lblPrefSpeed);
		
		
		JLabel lblMaxClimb = new JLabel("MaxClimb");
		lblMaxClimb.setBounds(12, 91, 70, 19);
		performancePanel.add(lblMaxClimb);
		
		JLabel lblMaxDescent = new JLabel("MaxDescent");
		lblMaxDescent.setBounds(12, 125, 101, 19);
		performancePanel.add(lblMaxDescent);
		
		JLabel lblMaxturning = new JLabel("MaxTurning");
		lblMaxturning.setBounds(12, 156, 82, 15);
		performancePanel.add(lblMaxturning);
		
		
		JLabel lblMaxacceleration = new JLabel("MaxAcceleration");
		lblMaxacceleration.setBounds(12, 183, 116, 15);
		performancePanel.add(lblMaxacceleration);
		
		JLabel lblMaxdecceleration = new JLabel("MaxDeceleration");
		lblMaxdecceleration.setBounds(12, 210, 119, 15);
		performancePanel.add(lblMaxdecceleration);
		
		
		maxSpeedTextField = new JTextField();
		maxSpeedTextField.setBounds(170, 12, 114, 19);
		performancePanel.add(maxSpeedTextField);
		maxSpeedTextField.setText(String.valueOf(CONFIGURATION.selfMaxSpeed/CONFIGURATION.lengthScale));
		maxSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxSpeedTextField.setColumns(10);
		
		
		minSpeedTextField = new JTextField();
		minSpeedTextField.setBounds(170, 43, 114, 19);
		performancePanel.add(minSpeedTextField);
		minSpeedTextField.setText(String.valueOf(CONFIGURATION.selfMinSpeed/CONFIGURATION.lengthScale));
		minSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField minSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMinSpeed = new Double(minSpeedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		minSpeedTextField.setColumns(10);
		
		prefSpeedTextField = new JTextField();
		prefSpeedTextField.setBounds(171, 70, 114, 19);
		performancePanel.add(prefSpeedTextField);
		prefSpeedTextField.setText(String.valueOf(CONFIGURATION.selfPrefSpeed/CONFIGURATION.lengthScale));
		prefSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField speedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfPrefSpeed = new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		prefSpeedTextField.setColumns(10);
		
		
		maxClimbTextField = new JTextField();
		maxClimbTextField.setBounds(170, 97, 114, 19);
		performancePanel.add(maxClimbTextField);
		maxClimbTextField.setText(String.valueOf(CONFIGURATION.selfMaxClimb/CONFIGURATION.lengthScale));
		maxClimbTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxClimbTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxClimb = new Double(maxClimbTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxClimbTextField.setColumns(10);
		
		
		maxDescentTextField = new JTextField();
		maxDescentTextField.setBounds(170, 131, 114, 19);
		performancePanel.add(maxDescentTextField);
		maxDescentTextField.setText(String.valueOf(CONFIGURATION.selfMaxDescent/CONFIGURATION.lengthScale));
		maxDescentTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxDescentTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxDescent = new Double(maxDescentTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxDescentTextField.setColumns(10);
		
		maxTurningTextField = new JTextField();
		maxTurningTextField.setBounds(171, 162, 114, 19);
		performancePanel.add(maxTurningTextField);
		maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.selfMaxTurning)*100)/100.0));
		maxTurningTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxTurningTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
			}
		});
		maxTurningTextField.setColumns(10);
		
		maxAccelerationTextField = new JTextField();
		maxAccelerationTextField.setBounds(171, 189, 114, 19);
		performancePanel.add(maxAccelerationTextField);
		maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.selfMaxAcceleration/CONFIGURATION.lengthScale));
		maxAccelerationTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxAccelerationTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxAcceleration = new Double(maxAccelerationTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxAccelerationTextField.setColumns(10);
		
		maxDecelerationTextField = new JTextField();
		maxDecelerationTextField.setBounds(171, 216, 114, 19);
		performancePanel.add(maxDecelerationTextField);
		maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.selfMaxDeceleration/CONFIGURATION.lengthScale));
		maxDecelerationTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxDecelerationTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxDeceleration = new Double(maxDecelerationTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxDecelerationTextField.setColumns(10);
		
		JLabel lblSafetyradius = new JLabel("SafetyRadius");
		lblSafetyradius.setBounds(18, 543, 94, 15);
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
		safetyRadiusTextField.setBounds(195, 545, 114, 19);
		this.add(safetyRadiusTextField);
		safetyRadiusTextField.setColumns(10);
		
		JLabel lblAlpha = new JLabel("Alpha");
		lblAlpha.setBounds(18, 570, 40, 15);
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
		alphaTextField.setBounds(195, 572, 114, 19);
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
