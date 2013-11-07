package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;

import modeling.CONFIGURATION;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;


public class SAAConfigurator extends JFrame {
	
	

	private JPanel contentPane;
	private final ButtonGroup selfAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup headOnAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup crossingAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup tailApproachAvoidanceAlgorithmGroup = new ButtonGroup();
	private JTextField uASNoTextField;
	private JTextField obstacleNoTextField;
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
		this.setBounds(1500+40, 380, 380, 700);
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
/******************************************* Avoidance Configuration***********************************************************************************/
		/*******************************************static avoidance setting***********************************************************************************/
		JPanel avoidanceConfigPanel = new JPanel();
		tabbedPane.addTab("AvoidanceConfig", null, avoidanceConfigPanel, null);
		avoidanceConfigPanel.setLayout(null);
		
		ButtonGroup collisionSelection = new ButtonGroup();
		JRadioButton staticAvoidanceRadioButton = new JRadioButton("staticAvoidance");
		staticAvoidanceRadioButton.setBounds(0, 7, 134, 51);
		staticAvoidanceRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CONFIGURATION.staticAvoidance = true;
				CONFIGURATION.dynamicAvoidance = false;
			}
		});
		collisionSelection.add(staticAvoidanceRadioButton);
		avoidanceConfigPanel.add(staticAvoidanceRadioButton);
	
		JLabel lblUasNo = new JLabel("UAS No. :");
		lblUasNo.setBounds(27, 55, 74, 14);
		avoidanceConfigPanel.add(lblUasNo);
		
		JLabel lblNewLabel = new JLabel("Obstacle No. :");
		lblNewLabel.setBounds(28, 89, 93, 14);
		avoidanceConfigPanel.add(lblNewLabel);
		
		uASNoTextField = new JTextField();
		uASNoTextField.setText("8");
		uASNoTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				JTextField uASNoTextField = (JTextField) arg0.getSource();
				CONFIGURATION.sUASNo = new Integer(uASNoTextField.getText());
			}
		});
		
		uASNoTextField.setBounds(140, 52, 86, 20);
		avoidanceConfigPanel.add(uASNoTextField);
		uASNoTextField.setColumns(10);
		
		obstacleNoTextField = new JTextField();
		obstacleNoTextField.setText("20");
		obstacleNoTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField obstacleNoTextField = (JTextField) e.getSource();
				CONFIGURATION.sObstacleNo = new Integer(obstacleNoTextField.getText());
			}
		});
		
		obstacleNoTextField.setBounds(140, 86, 86, 20);
		avoidanceConfigPanel.add(obstacleNoTextField);
		obstacleNoTextField.setColumns(10);
			
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 126, 331, 2);
		avoidanceConfigPanel.add(separator);

	     /*******************************************End of static avoidance Setting***********************************************************************/	

		
		/*******************************************dynamic avoidance Setting***********************************************************************/	
		JRadioButton dynamicAvoidanceRadioButton = new JRadioButton("dynamicAvoidance",true);
		dynamicAvoidanceRadioButton.setBounds(0, 135, 156, 23);
		dynamicAvoidanceRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CONFIGURATION.staticAvoidance = false;
				CONFIGURATION.dynamicAvoidance = true;
			}
		});
		collisionSelection.add(dynamicAvoidanceRadioButton);
		avoidanceConfigPanel.add(dynamicAvoidanceRadioButton);
		
		JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
		AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "Self's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		AvoidanceAlgorithmSelectionPanel.setBounds(10, 165, 327, 80);
		avoidanceConfigPanel.add(AvoidanceAlgorithmSelectionPanel);
		
		JRadioButton rdbtnNone = new JRadioButton("None");
		AvoidanceAlgorithmSelectionPanel.add(rdbtnNone);
		selfAvoidanceAlgorithmGroup.add(rdbtnNone);
		rdbtnNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CONFIGURATION.avoidanceAlgorithmSelection = "None";
			}
		});
		
		JRadioButton rdbtnTurnrightavoidancealgorithm = new JRadioButton("TurnRight");
		AvoidanceAlgorithmSelectionPanel.add(rdbtnTurnrightavoidancealgorithm);
		rdbtnTurnrightavoidancealgorithm.setSelected(true);
		selfAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm);
		rdbtnTurnrightavoidancealgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CONFIGURATION.avoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
			}
		});
		
		
		
		JRadioButton rdbtnSmartturnavoidancealgorithm = new JRadioButton("SmartTurn");
		AvoidanceAlgorithmSelectionPanel.add(rdbtnSmartturnavoidancealgorithm);
		selfAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm);
		
		JRadioButton rdbtnRIPNAvoidanceAlgorithm = new JRadioButton("RIPN");
		AvoidanceAlgorithmSelectionPanel.add(rdbtnRIPNAvoidanceAlgorithm);
		selfAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm);
		rdbtnRIPNAvoidanceAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CONFIGURATION.avoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
			}
		});
		rdbtnSmartturnavoidancealgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CONFIGURATION.avoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
			}
		});
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 265, 303, 2);
		avoidanceConfigPanel.add(separator_1);
		
		
		
		JLabel lblMaxspeed = new JLabel("MaxSpeed:");
		lblMaxspeed.setBounds(27, 284, 96, 14);
		avoidanceConfigPanel.add(lblMaxspeed);
		
		JLabel lblMaxacceleration = new JLabel("MaxAcceleration");
		lblMaxacceleration.setBounds(27, 322, 96, 14);
		avoidanceConfigPanel.add(lblMaxacceleration);
		
		JLabel lblMaxdecceleration = new JLabel("MaxDeceleration");
		lblMaxdecceleration.setBounds(27, 364, 107, 14);
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
		lblSensitivityForCollisions.setBounds(27, 551, 143, 14);
		avoidanceConfigPanel.add(lblSensitivityForCollisions);
		
		
		maxSpeedTextField = new JTextField();
		maxSpeedTextField.setText("2.235");
		maxSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxSpeed = new Double(maxSpeedTextField.getText());
			}
		});
		
		JLabel lblSafetyradius = new JLabel("SafetyRadius");
		lblSafetyradius.setBounds(27, 588, 107, 14);
		avoidanceConfigPanel.add(lblSafetyradius);
		maxSpeedTextField.setBounds(208, 278, 86, 20);
		avoidanceConfigPanel.add(maxSpeedTextField);
		maxSpeedTextField.setColumns(10);
		
		maxAccelerationTextField = new JTextField();
		maxAccelerationTextField.setText("0.05");
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
		maxDecelerationTextField.setText("0.05");
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
		maxTurningTextField.setText("5");
		maxTurningTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxTurningTextField = (JTextField) e.getSource();
				CONFIGURATION.selfMaxTurning = new Double(maxTurningTextField.getText());
			}
		});
		maxTurningTextField.setBounds(208, 394, 86, 20);
		avoidanceConfigPanel.add(maxTurningTextField);
		maxTurningTextField.setColumns(10);
		
		speedTextField = new JTextField();
		speedTextField.setText("1.5");
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
		viewingRangeTextField.setText("38.89");
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
		viewingAngleTextField.setText("60");
		viewingAngleTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField viewingAngleTextField = (JTextField) e.getSource();
				CONFIGURATION.selfViewingAngle = new Double(viewingAngleTextField.getText());
			}
		});
		viewingAngleTextField.setBounds(208, 506, 86, 20);
		avoidanceConfigPanel.add(viewingAngleTextField);
		viewingAngleTextField.setColumns(10);
		
		sensitivityForCollisionTextField = new JTextField();
		sensitivityForCollisionTextField.setText("0.5");
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
		safetyRadiusTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField safetyRadiusTextField = (JTextField) e.getSource();
				CONFIGURATION.selfSafetyRadius = new Double(safetyRadiusTextField.getText());
			}
		});
		safetyRadiusTextField.setText("1.667");
		safetyRadiusTextField.setBounds(208, 582, 86, 20);
		avoidanceConfigPanel.add(safetyRadiusTextField);
		safetyRadiusTextField.setColumns(10);
     /*******************************************End of dynamic avoidance Setting***********************************************************************/	
		

/*******************************************End of Avoidance Configuration***********************************************************************************/	
		
		
/*******************************************Start of Encounter Configuration***********************************************************************************/	
		JPanel encounterConfigPanel = new JPanel();
		tabbedPane.addTab("EncounterConfig", null, encounterConfigPanel, null);
		encounterConfigPanel.setLayout(null);
		
//		final RadioButtonPanel encounterPanel = new RadioButtonPanel("Encounter Selection", new String[]{"HeadOnEncounter", "CrossingEncounter","TailApproachEncounter" }); 
//		encounterPanel.setBounds(10, 11, 196, 104);
//		//		JCheckBox chckbxNewCheckBox = new JCheckBox("HeadOnEncounter");
//		//		chckbxNewCheckBox.setBounds(6, 34, 184, 23);
//		//		encounterConfigPanel.add(chckbxNewCheckBox);
//		//		
//		//		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("CrossingEncounter");
//		//		chckbxNewCheckBox_1.setBounds(6, 80, 138, 23);
//		//		encounterConfigPanel.add(chckbxNewCheckBox_1);
//		//		
//		//		JCheckBox chckbxTailapproachencounter = new JCheckBox("TailApproachEncounter");
//		//		chckbxTailapproachencounter.setBounds(6, 126, 184, 23);
//		//		encounterConfigPanel.add(chckbxTailapproachencounter);
//				encounterConfigPanel.add(encounterPanel);
//				
//				JButton btnOk = new JButton("OK");
//				btnOk.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent arg0) {
//						CONFIGURATION.encounterSelection=encounterPanel.getSelection();
//					}
//				});
//				btnOk.setBounds(216, 86, 89, 23);
//				encounterConfigPanel.add(btnOk);
//				
//				JButton btnReset = new JButton("Reset");
//				btnReset.setBounds(216, 39, 89, 23);
//				encounterConfigPanel.add(btnReset);
	/***************************************************************************************************************************/			
		{   
				JSplitPane splitPane = new JSplitPane();
				splitPane.setBounds(10, 11, 339, 25);
				encounterConfigPanel.add(splitPane);
				
				JCheckBox chckbxHeadonencounter = new JCheckBox("HeadOnEncounter");
				chckbxHeadonencounter.setSelected(true);
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
						try {
							IntruderConfig dialog = new IntruderConfig();
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setName("HeadonEncounter--IntruderConfig");
							dialog.setModal(true);
							dialog.setVisible(true);
							
		
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						
					}
				});
				splitPane_2.setRightComponent(btnConfig);
				
				JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
				AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				AvoidanceAlgorithmSelectionPanel1.setBounds(20, 47, 314, 80);
				encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
				
				JRadioButton rdbtnNone1 = new JRadioButton("None");
				rdbtnNone1.setSelected(true);
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnNone1);
				rdbtnNone1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "None";
					}
				});
				
				JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
				rdbtnTurnrightavoidancealgorithm1.setSelected(true);
				headOnAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
				rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
					}
				});
				
				
				
				JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
				
				JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
				rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
					}
				});
				rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
					}
				});
		}
			
			
		{   JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(10, 190, 339, 25);
			encounterConfigPanel.add(splitPane);
			
			JCheckBox chckbxCrossingEncounter = new JCheckBox("CrossingEncounter");
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
						dialog.setModal(true);
						dialog.setVisible(true);
						
	
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}
			});
			splitPane_2.setRightComponent(btnConfig);
			
			JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
			AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel1.setBounds(20, 226, 314, 80);
			encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
			
			JRadioButton rdbtnNone1 = new JRadioButton("None");
			rdbtnNone1.setSelected(true);
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnNone1);
			rdbtnNone1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "None";
				}
			});
			
			JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
			rdbtnTurnrightavoidancealgorithm1.setSelected(true);
			crossingAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
			rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
				}
			});
			
			
			
			JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
			
			JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
			rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
				}
			});
			rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
				}
			});
	}
		
		
		
		
	{   JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 378, 339, 25);
		encounterConfigPanel.add(splitPane);
		
		JCheckBox chckbxTailApproachEncounter = new JCheckBox("TailApproachEncounter");
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
	
	JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
	AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	AvoidanceAlgorithmSelectionPanel1.setBounds(20, 416, 314, 80);
	encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
	
	JRadioButton rdbtnNone1 = new JRadioButton("None");
	rdbtnNone1.setSelected(true);
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnNone1);
	rdbtnNone1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "None";
		}
	});
	
	JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
	rdbtnTurnrightavoidancealgorithm1.setSelected(true);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
	rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
		}
	});
	
	
	
	JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
	
	JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
	rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
		}
	});
	rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
		}
	});
  }
}
