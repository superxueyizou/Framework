package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import tools.CONFIGURATION;
import tools.UTILS;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class SAAConfigurator extends JFrame {	

	private JPanel contentPane;
	private final ButtonGroup selfAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup headOnAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup crossingAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup tailApproachAvoidanceAlgorithmGroup = new ButtonGroup();
	private JTextField maxSpeedTextField;
	private JTextField maxAccelerationTextField;
	private JTextField maxDecelerationTextField;
	private JTextField maxTurningTextField;
	private JTextField speedTextField;
	private JTextField viewingRangeTextField;
	private JTextField viewingAngleTextField;
	private JTextField sensitivityForCollisionTextField;
	private JTextField safetyRadiusTextField;
	private JTextField txtHeadontimes;
	private JTextField txtCrossingtimes;
	private JTextField txtTailApproachTimes;
	private JTextField alphaTextField;
	private JTextField headOnOffsetTextField;
	private JTextField crossingAngleTextField;
	private JTextField tailApproachOffsetTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SAAConfigurator frame = new SAAConfigurator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SAAConfigurator() {
		super("SAA Configurator");
		this.setBounds(1500+80, 404, 340,742);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("hello");
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE))
		);
		
	JPanel avoidanceConfigPanel = new JPanel();
		tabbedPane.addTab("AvoidanceConfig", null, avoidanceConfigPanel, null);
		avoidanceConfigPanel.setLayout(null);
			
			JLabel lblModelbuilderSetting = new JLabel("ModelBuilder Setting");
			lblModelbuilderSetting.setBounds(12, 12, 154, 15);
			avoidanceConfigPanel.add(lblModelbuilderSetting);
			
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
			btnLoad.setBounds(184, 7, 70, 25);
			avoidanceConfigPanel.add(btnLoad);
			
			JLabel lblDestDist = new JLabel("Dest Dist");
			lblDestDist.setBounds(27, 48, 70, 15);
			avoidanceConfigPanel.add(lblDestDist);
			
			JLabel lblDestAngle = new JLabel("Dest Angle");
			lblDestAngle.setBounds(27, 75, 86, 15);
			avoidanceConfigPanel.add(lblDestAngle);
			
			JSlider destDistSlider = new JSlider();
			destDistSlider.setValue((int)CONFIGURATION.selfDestDist);
			destDistSlider.setMaximum(70);
			destDistSlider.setMinimum(20);
			destDistSlider.setBounds(111, 47, 200, 16);
			avoidanceConfigPanel.add(destDistSlider);
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
			destAngleSlider.setBounds(111, 75, 200, 16);
			avoidanceConfigPanel.add(destAngleSlider);
			destAngleSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					CONFIGURATION.selfDestAngle = Math.toRadians(source.getValue());
				}
			});
			
			JRadioButton rdbtnCAEnable = new JRadioButton("CA Enable?");
			rdbtnCAEnable.setBounds(12, 114, 149, 23);
			avoidanceConfigPanel.add(rdbtnCAEnable);
			rdbtnCAEnable.setSelected(CONFIGURATION.avoidanceAlgorithmEnabler);
			rdbtnCAEnable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmEnabler = true;
					} else {
						
						CONFIGURATION.avoidanceAlgorithmEnabler = false;
					}
				}
			});
		
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "Self's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(10, 165, 301, 107);
			avoidanceConfigPanel.add(AvoidanceAlgorithmSelectionPanel);
			AvoidanceAlgorithmSelectionPanel.setLayout(null);
			
			JRadioButton rdbtnORCAAvoidanceAlgorithm = new JRadioButton("ORCA");
			rdbtnORCAAvoidanceAlgorithm.setBounds(8, 22, 73, 23);
			rdbtnORCAAvoidanceAlgorithm.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "ORCAAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnORCAAvoidanceAlgorithm);
			selfAvoidanceAlgorithmGroup.add(rdbtnORCAAvoidanceAlgorithm);
			rdbtnORCAAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "ORCAAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnTurnrightavoidancealgorithm = new JRadioButton("TurnRight");
			rdbtnTurnrightavoidancealgorithm.setEnabled(false);
			rdbtnTurnrightavoidancealgorithm.setBounds(108, 22, 94, 23);
			rdbtnTurnrightavoidancealgorithm.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "TurnRightAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnTurnrightavoidancealgorithm);
			selfAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm);
			rdbtnTurnrightavoidancealgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
					}
				}
			});
			
			
			JRadioButton rdbtnSmartturnavoidancealgorithm = new JRadioButton("SmartTurn");
			rdbtnSmartturnavoidancealgorithm.setEnabled(false);
			rdbtnSmartturnavoidancealgorithm.setBounds(108, 49, 99, 23);
			rdbtnSmartturnavoidancealgorithm.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "SmartTurnAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnSmartturnavoidancealgorithm);
			selfAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm);		
			rdbtnSmartturnavoidancealgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
					}
				}
			});
			
			
			
			JRadioButton rdbtnRIPNAvoidanceAlgorithm = new JRadioButton("RIPN");
			rdbtnRIPNAvoidanceAlgorithm.setEnabled(false);
			rdbtnRIPNAvoidanceAlgorithm.setBounds(225, 22, 57, 23);
			rdbtnRIPNAvoidanceAlgorithm.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "RIPNAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnRIPNAvoidanceAlgorithm);
			selfAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm);
			rdbtnRIPNAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
					}
				}
			});
			
			
			JRadioButton rdbtnRVOAvoidanceAlgorithm = new JRadioButton("RVO");
			rdbtnRVOAvoidanceAlgorithm.setBounds(8, 49, 62, 23);
			rdbtnRVOAvoidanceAlgorithm.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "RVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnRVOAvoidanceAlgorithm);
			selfAvoidanceAlgorithmGroup.add(rdbtnRVOAvoidanceAlgorithm);
			rdbtnRVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "RVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnNone = new JRadioButton("None");
			rdbtnNone.setBounds(225, 49, 62, 23);
			rdbtnNone.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "None");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
			selfAvoidanceAlgorithmGroup.add(rdbtnNone);
			rdbtnNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "None";
					}
				}
			});
			
			
			JRadioButton rdbtnHRVOAvoidanceAlgorithm = new JRadioButton("HRVO");
			rdbtnHRVOAvoidanceAlgorithm.setBounds(8, 76, 79, 23);
			rdbtnHRVOAvoidanceAlgorithm.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "HRVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnHRVOAvoidanceAlgorithm);
			selfAvoidanceAlgorithmGroup.add(rdbtnHRVOAvoidanceAlgorithm);
			rdbtnHRVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "HRVOAvoidanceAlgorithm";
					}
				}
			});
			
			JRadioButton rdbtnAVOAvoidanceAlgorithm = new JRadioButton("AVO");
			rdbtnAVOAvoidanceAlgorithm.setBounds(108, 76, 94, 23);
			rdbtnAVOAvoidanceAlgorithm.setSelected(CONFIGURATION.avoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnAVOAvoidanceAlgorithm);
			selfAvoidanceAlgorithmGroup.add(rdbtnAVOAvoidanceAlgorithm);
			rdbtnAVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.avoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
					}
				}
			});
			
			
			
			JLabel lblMaxspeed = new JLabel("MaxSpeed");
			lblMaxspeed.setBounds(27, 284, 96, 14);
			avoidanceConfigPanel.add(lblMaxspeed);
			
			JLabel lblMaxacceleration = new JLabel("MaxAcceleration");
			lblMaxacceleration.setBounds(27, 322, 129, 14);
			avoidanceConfigPanel.add(lblMaxacceleration);
			
			JLabel lblMaxdecceleration = new JLabel("MaxDeceleration");
			lblMaxdecceleration.setBounds(27, 364, 163, 14);
			avoidanceConfigPanel.add(lblMaxdecceleration);
			
			JLabel lblMaxturning = new JLabel("MaxTurning");
			lblMaxturning.setBounds(27, 400, 107, 14);
			avoidanceConfigPanel.add(lblMaxturning);
			
			JLabel lblSpeed = new JLabel("Speed");
			lblSpeed.setBounds(27, 436, 107, 14);
			avoidanceConfigPanel.add(lblSpeed);
			
			JLabel lblViewingRange = new JLabel("ViewingRange");
			lblViewingRange.setBounds(27, 473, 107, 14);
			avoidanceConfigPanel.add(lblViewingRange);
			
			JLabel lblViewingAngle = new JLabel("ViewingAngle");
			lblViewingAngle.setBounds(27, 512, 107, 14);
			avoidanceConfigPanel.add(lblViewingAngle);
			
			JLabel lblSensitivityForCollisions = new JLabel("SensitivityForCollisions");
			lblSensitivityForCollisions.setBounds(27, 551, 177, 14);
			avoidanceConfigPanel.add(lblSensitivityForCollisions);
			
			JLabel lblSafetyradius = new JLabel("SafetyRadius");
			lblSafetyradius.setBounds(27, 588, 107, 14);
			avoidanceConfigPanel.add(lblSafetyradius);
			
			JLabel lblAlpha = new JLabel("Alpha");
			lblAlpha.setBounds(27, 623, 70, 15);
			avoidanceConfigPanel.add(lblAlpha);
			
			
			maxSpeedTextField = new JTextField();
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.selfMaxSpeed));
			maxSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.selfMaxSpeed = new Double(maxSpeedTextField.getText());
				}
			});
			
			
			maxSpeedTextField.setBounds(208, 278, 86, 20);
			avoidanceConfigPanel.add(maxSpeedTextField);
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
			maxAccelerationTextField.setBounds(208, 316, 86, 20);
			avoidanceConfigPanel.add(maxAccelerationTextField);
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
			maxDecelerationTextField.setBounds(208, 358, 86, 20);
			avoidanceConfigPanel.add(maxDecelerationTextField);
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
			maxTurningTextField.setBounds(208, 394, 86, 20);
			avoidanceConfigPanel.add(maxTurningTextField);
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
			speedTextField.setBounds(208, 430, 86, 20);
			avoidanceConfigPanel.add(speedTextField);
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
			viewingRangeTextField.setBounds(208, 467, 86, 20);
			avoidanceConfigPanel.add(viewingRangeTextField);
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
			viewingAngleTextField.setBounds(208, 506, 86, 20);
			avoidanceConfigPanel.add(viewingAngleTextField);
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
			sensitivityForCollisionTextField.setBounds(208, 545, 86, 20);
			avoidanceConfigPanel.add(sensitivityForCollisionTextField);
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
			safetyRadiusTextField.setBounds(208, 582, 86, 20);
			avoidanceConfigPanel.add(safetyRadiusTextField);
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
			alphaTextField.setBounds(208, 621, 86, 19);
			avoidanceConfigPanel.add(alphaTextField);
			alphaTextField.setColumns(10);
			
		
			
			
		JPanel encounterConfigPanel = new JPanel();
		tabbedPane.addTab("EncounterConfig", null, encounterConfigPanel, null);
		encounterConfigPanel.setLayout(null);
				
		{   
				JSplitPane splitPane = new JSplitPane();
				splitPane.setBounds(10, 11, 301, 25);
				encounterConfigPanel.add(splitPane);
				
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
				
				JRadioButton rdbtnIsrightside = new JRadioButton("IsRightSide");
				rdbtnIsrightside.setBounds(20, 44, 115, 23);
				rdbtnIsrightside.setSelected(CONFIGURATION.headOnIsRightSide);
				encounterConfigPanel.add(rdbtnIsrightside);
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
				
				JLabel lblOffset = new JLabel("Offset");
				lblOffset.setBounds(165, 48, 54, 19);
				encounterConfigPanel.add(lblOffset);
				
				headOnOffsetTextField = new JTextField();
				headOnOffsetTextField.setBounds(225, 46, 86, 19);
				headOnOffsetTextField.setText(""+CONFIGURATION.headOnOffset);
				encounterConfigPanel.add(headOnOffsetTextField);
				headOnOffsetTextField.setColumns(10);
				headOnOffsetTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField offsetTextField=(JTextField) e.getSource();
						CONFIGURATION.headOnOffset=Double.parseDouble(offsetTextField.getText());
					}
				});
				
				
				
				JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
				AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				AvoidanceAlgorithmSelectionPanel1.setBounds(10, 75, 314, 117);
				encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
				AvoidanceAlgorithmSelectionPanel1.setLayout(null);
				

				JRadioButton rdbtnORCAavoidancealgorithem1 = new JRadioButton("ORCA");
				rdbtnORCAavoidancealgorithem1.setBounds(8, 22, 70, 23);
				rdbtnORCAavoidancealgorithem1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "ORCAAvoidanceAlgorithm");
				headOnAvoidanceAlgorithmGroup.add(rdbtnORCAavoidancealgorithem1);
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnORCAavoidancealgorithem1);
				rdbtnORCAavoidancealgorithem1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "ORCAAvoidanceAlgorithm";
						}
					}
				});
		
				
				JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
				rdbtnTurnrightavoidancealgorithm1.setEnabled(false);
				rdbtnTurnrightavoidancealgorithm1.setBounds(109, 22, 94, 23);
				rdbtnTurnrightavoidancealgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "TurnRightAvoidanceAlgorithm");
				headOnAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
				rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
						}
						
					}
				});
				
				
				
				JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
				rdbtnSmartturnavoidancealgorithm1.setEnabled(false);
				rdbtnSmartturnavoidancealgorithm1.setBounds(109, 49, 99, 23);
				rdbtnSmartturnavoidancealgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "SmartTurnAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
				rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
						}
						
					}
				});
				
				
				JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
				rdbtnRIPNAvoidanceAlgorithm1.setEnabled(false);
				rdbtnRIPNAvoidanceAlgorithm1.setBounds(227, 22, 57, 23);
				rdbtnRIPNAvoidanceAlgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "RIPNAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
				rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
						}
						
					}
				});
				

				JRadioButton rdbtnRVONAvoidanceAlgorithm1 = new JRadioButton("RVO");
				rdbtnRVONAvoidanceAlgorithm1.setBounds(8, 49, 62, 23);
				rdbtnRVONAvoidanceAlgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "RVOAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnRVONAvoidanceAlgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnRVONAvoidanceAlgorithm1);
				rdbtnRVONAvoidanceAlgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "RVOAvoidanceAlgorithm";
						}
						
					}
				});
				
				
				JRadioButton rdbtnHRVONAvoidanceAlgorithm1 = new JRadioButton("HRVO");
				rdbtnHRVONAvoidanceAlgorithm1.setBounds(8, 76, 80, 23);
				rdbtnHRVONAvoidanceAlgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "HRVOAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnHRVONAvoidanceAlgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnHRVONAvoidanceAlgorithm1);
				rdbtnHRVONAvoidanceAlgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "HRVOAvoidanceAlgorithm";
						}
						
					}
				});
				
				
				JRadioButton rdbtnAVONAvoidanceAlgorithm1 = new JRadioButton("AVO");
				rdbtnAVONAvoidanceAlgorithm1.setBounds(109, 76, 80, 23);
				rdbtnAVONAvoidanceAlgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnAVONAvoidanceAlgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnAVONAvoidanceAlgorithm1);
				rdbtnAVONAvoidanceAlgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
						}
						
					}
				});
				
				JRadioButton rdbtnNone1 = new JRadioButton("None");
				rdbtnNone1.setBounds(227, 49, 62, 23);
				rdbtnNone1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "None");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnNone1);
				rdbtnNone1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(((JRadioButton)e.getSource()).isSelected())
						{
							CONFIGURATION.headOnAvoidanceAlgorithmSelection = "None";
						}
						
					}
				});
				
		}
			
			
		{   JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(10, 219, 301, 25);
			encounterConfigPanel.add(splitPane);
			
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
			
			JRadioButton rdbtnIsrightside_1 = new JRadioButton("IsRightSide");
			rdbtnIsrightside_1.setBounds(20, 255, 115, 23);
			rdbtnIsrightside_1.setSelected(CONFIGURATION.crossingIsRightSide);
			encounterConfigPanel.add(rdbtnIsrightside_1);
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
			
			JLabel lblEncounterangel = new JLabel("Angle");
			lblEncounterangel.setBounds(165, 256, 47, 20);
			encounterConfigPanel.add(lblEncounterangel);
			
			crossingAngleTextField = new JTextField();
			crossingAngleTextField.setBounds(225, 256, 86, 19);
			crossingAngleTextField.setText(""+Math.round(Math.toDegrees(CONFIGURATION.crossingEncounterAngle)));
			encounterConfigPanel.add(crossingAngleTextField);
			crossingAngleTextField.setColumns(10);
			crossingAngleTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField offsetTextField=(JTextField) e.getSource();
					CONFIGURATION.crossingEncounterAngle= Math.toRadians(Double.parseDouble(offsetTextField.getText()));
				}
			});
			
			
			JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
			AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel1.setBounds(10, 286, 314, 127);
			encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
			AvoidanceAlgorithmSelectionPanel1.setLayout(null);
			

			
			JRadioButton rdbtnORCAavoidancealgorithem1 = new JRadioButton("ORCA");
			rdbtnORCAavoidancealgorithem1.setBounds(8, 22, 69, 23);
			rdbtnORCAavoidancealgorithem1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "ORCAAvoidanceAlgorithm");
			crossingAvoidanceAlgorithmGroup.add(rdbtnORCAavoidancealgorithem1);
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnORCAavoidancealgorithem1);
			rdbtnORCAavoidancealgorithem1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "ORCAAvoidanceAlgorithm";
					}
					
				}
			});
			
			JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
			rdbtnTurnrightavoidancealgorithm1.setEnabled(false);
			rdbtnTurnrightavoidancealgorithm1.setBounds(109, 22, 94, 23);
			rdbtnTurnrightavoidancealgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "TurnRightAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
			rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
					}
					
				}
			});
			
			
			
			JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
			rdbtnSmartturnavoidancealgorithm1.setEnabled(false);
			rdbtnSmartturnavoidancealgorithm1.setBounds(109, 49, 99, 23);
			rdbtnSmartturnavoidancealgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "SmartTurnAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
			rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
					}
					
				}
			});
			
			
			JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
			rdbtnRIPNAvoidanceAlgorithm1.setEnabled(false);
			rdbtnRIPNAvoidanceAlgorithm1.setBounds(228, 22, 57, 23);
			rdbtnRIPNAvoidanceAlgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "RIPNAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
			rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
					}
					
				}
			});
			
			
			JRadioButton rdbtnRVOAvoidanceAlgorithm1 = new JRadioButton("RVO");
			rdbtnRVOAvoidanceAlgorithm1.setBounds(8, 49, 62, 23);
			rdbtnRVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "RVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnRVOAvoidanceAlgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnRVOAvoidanceAlgorithm1);
			rdbtnRVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "RVOAvoidanceAlgorithm";
					}
					
				}
			});
			
			
			JRadioButton rdbtnHRVOAvoidanceAlgorithm1 = new JRadioButton("HRVO");
			rdbtnHRVOAvoidanceAlgorithm1.setBounds(8, 76, 80, 23);
			rdbtnHRVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "HRVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnHRVOAvoidanceAlgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnHRVOAvoidanceAlgorithm1);
			rdbtnHRVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "HRVOAvoidanceAlgorithm";
					}
					
				}
			});
			
			
			
			JRadioButton rdbtnAVOAvoidanceAlgorithm1 = new JRadioButton("AVO");
			rdbtnAVOAvoidanceAlgorithm1.setBounds(109, 76, 80, 23);
			rdbtnAVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnAVOAvoidanceAlgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnAVOAvoidanceAlgorithm1);
			rdbtnAVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
					}
					
				}
			});
			
			JRadioButton rdbtnNone1 = new JRadioButton("None");
			rdbtnNone1.setBounds(228, 49, 62, 23);
			rdbtnNone1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "None");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnNone1);			
			rdbtnNone1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingAvoidanceAlgorithmSelection = "None";
					}
					
				}
			});
			
	}
		
		
		
		
	{   JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 452, 301, 25);
		encounterConfigPanel.add(splitPane);
		
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
	}
				
	
	contentPane.setLayout(gl_contentPane);
	
	JRadioButton rdbtnIsrightside_2 = new JRadioButton("IsRightSide");
	rdbtnIsrightside_2.setBounds(20, 485, 105, 23);
	rdbtnIsrightside_2.setSelected(CONFIGURATION.tailApproachIsRightSide);
	encounterConfigPanel.add(rdbtnIsrightside_2);
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
	lblOffset_1.setBounds(165, 489, 54, 19);
	encounterConfigPanel.add(lblOffset_1);
	
	tailApproachOffsetTextField = new JTextField();
	tailApproachOffsetTextField.setBounds(225, 489, 86, 19);
	tailApproachOffsetTextField.setText(""+CONFIGURATION.tailApproachOffset);
	encounterConfigPanel.add(tailApproachOffsetTextField);
	tailApproachOffsetTextField.setColumns(10);
	tailApproachOffsetTextField.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			JTextField offsetTextField=(JTextField) e.getSource();
			CONFIGURATION.tailApproachOffset=Double.parseDouble(offsetTextField.getText());
		}
	});
	
	JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
	AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	AvoidanceAlgorithmSelectionPanel1.setBounds(10, 525, 314, 127);
	encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
	AvoidanceAlgorithmSelectionPanel1.setLayout(null);
	

	
	JRadioButton rdbtnORCAavoidancealgorithem1 = new JRadioButton("ORCA");
	rdbtnORCAavoidancealgorithem1.setBounds(8, 22, 69, 23);
	rdbtnORCAavoidancealgorithem1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "ORCAAvoidanceAlgorithm");
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnORCAavoidancealgorithem1);
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnORCAavoidancealgorithem1);
	rdbtnORCAavoidancealgorithem1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "ORCAAvoidanceAlgorithm";
			}
			
		}
	});
	
	JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
	rdbtnTurnrightavoidancealgorithm1.setEnabled(false);
	rdbtnTurnrightavoidancealgorithm1.setBounds(109, 22, 94, 23);
	rdbtnTurnrightavoidancealgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "TurnRightAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
	rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
			}
			
		}
	});
	
	
	
	JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
	rdbtnSmartturnavoidancealgorithm1.setEnabled(false);
	rdbtnSmartturnavoidancealgorithm1.setBounds(109, 49, 99, 23);
	rdbtnSmartturnavoidancealgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "SmartTurnAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
	rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
			}
			
		}
	});
	
	
	JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
	rdbtnRIPNAvoidanceAlgorithm1.setEnabled(false);
	rdbtnRIPNAvoidanceAlgorithm1.setBounds(229, 22, 57, 23);
	rdbtnRIPNAvoidanceAlgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "RIPNAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
	rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
			}
			
		}
	});
	
	
	JRadioButton rdbtnRVOAvoidanceAlgorithm1 = new JRadioButton("RVO");
	rdbtnRVOAvoidanceAlgorithm1.setBounds(8, 49, 62, 23);
	rdbtnRVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "RVOAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnRVOAvoidanceAlgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnRVOAvoidanceAlgorithm1);
	rdbtnRVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "RVOAvoidanceAlgorithm";
			}
			
		}
	});
	
	
	
	JRadioButton rdbtnHRVOAvoidanceAlgorithm1 = new JRadioButton("HRVO");
	rdbtnHRVOAvoidanceAlgorithm1.setBounds(8, 76, 75, 23);
	rdbtnHRVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "HRVOAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnHRVOAvoidanceAlgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnHRVOAvoidanceAlgorithm1);
	rdbtnHRVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "HRVOAvoidanceAlgorithm";
			}
			
		}
	});
	
	
	JRadioButton rdbtnAVOAvoidanceAlgorithm1 = new JRadioButton("AVO");
	rdbtnAVOAvoidanceAlgorithm1.setBounds(109, 76, 75, 23);
	rdbtnAVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "AVOAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnAVOAvoidanceAlgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnAVOAvoidanceAlgorithm1);
	rdbtnAVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "AVOAvoidanceAlgorithm";
			}
			
		}
	});
	
	
	JRadioButton rdbtnNone1 = new JRadioButton("None");
	rdbtnNone1.setBounds(229, 49, 62, 23);
	rdbtnNone1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "None");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnNone1);
	rdbtnNone1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((JRadioButton)e.getSource()).isSelected())
			{
				CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "None";
			}
			
		}
	});

  }
}
