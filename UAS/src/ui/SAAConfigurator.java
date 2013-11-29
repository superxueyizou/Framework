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

import tools.CONFIGURATION;


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
		this.setBounds(1500+70, 380, 380, 700);
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
		
		ButtonGroup collisionSelection = new ButtonGroup();

	     /*******************************************End of static avoidance Setting***********************************************************************/	

		
		/*******************************************dynamic avoidance Setting***********************************************************************/	
     /*******************************************End of dynamic avoidance Setting***********************************************************************/	
		

/*******************************************End of Avoidance Configuration***********************************************************************************/	
		
		
/*******************************************Start of Encounter Configuration***********************************************************************************/	
		JPanel avoidanceConfigPanel = new JPanel();
		tabbedPane.addTab("AvoidanceConfig", null, avoidanceConfigPanel, null);
		avoidanceConfigPanel.setLayout(null);
		JRadioButton staticAvoidanceRadioButton = new JRadioButton("staticAvoidance");
		staticAvoidanceRadioButton.setSelected(CONFIGURATION.staticAvoidance);
		staticAvoidanceRadioButton.setBounds(0, 7, 134, 51);
		staticAvoidanceRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					CONFIGURATION.staticAvoidance = true;
					CONFIGURATION.dynamicAvoidance = false;
				}
				else
				{
					CONFIGURATION.staticAvoidance = false;
					CONFIGURATION.dynamicAvoidance = true;
				}
			}
		});
		collisionSelection.add(staticAvoidanceRadioButton);
		avoidanceConfigPanel.add(staticAvoidanceRadioButton);
		
			JLabel lblUasNo = new JLabel("UAS No. :");
			lblUasNo.setBounds(27, 55, 74, 14);
			avoidanceConfigPanel.add(lblUasNo);
			
			JLabel lblNewLabel = new JLabel("Obstacle No. :");
			lblNewLabel.setBounds(28, 89, 128, 14);
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
			
			uASNoTextField.setBounds(208, 53, 86, 20);
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
			
			obstacleNoTextField.setBounds(208, 87, 86, 20);
			avoidanceConfigPanel.add(obstacleNoTextField);
			obstacleNoTextField.setColumns(10);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(6, 126, 331, 2);
			avoidanceConfigPanel.add(separator);
			JRadioButton dynamicAvoidanceRadioButton = new JRadioButton("dynamicAvoidance");
			dynamicAvoidanceRadioButton.setSelected(CONFIGURATION.dynamicAvoidance);
			dynamicAvoidanceRadioButton.setBounds(0, 135, 156, 23);
			dynamicAvoidanceRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.staticAvoidance = false;
						CONFIGURATION.dynamicAvoidance = true;
						
					}
					else
					{
						CONFIGURATION.staticAvoidance = true;
						CONFIGURATION.dynamicAvoidance = false;
					}
					
				}
			});
			collisionSelection.add(dynamicAvoidanceRadioButton);
			avoidanceConfigPanel.add(dynamicAvoidanceRadioButton);
			
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "Self's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(10, 165, 327, 80);
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
			rdbtnSmartturnavoidancealgorithm.setBounds(220, 22, 99, 23);
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
			rdbtnRIPNAvoidanceAlgorithm.setBounds(8, 49, 57, 23);
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
			rdbtnRVOAvoidanceAlgorithm.setBounds(108, 49, 62, 23);
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
			rdbtnNone.setBounds(220, 49, 62, 23);
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
			
			JLabel lblSafetyradius = new JLabel("SafetyRadius");
			lblSafetyradius.setBounds(27, 588, 107, 14);
			avoidanceConfigPanel.add(lblSafetyradius);
			
			
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
			maxTurningTextField.setText(String.valueOf(CONFIGURATION.selfMaxTurning));
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
			viewingAngleTextField.setText(String.valueOf(CONFIGURATION.selfViewingAngle));
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
		JPanel encounterConfigPanel = new JPanel();
		tabbedPane.addTab("EncounterConfig", null, encounterConfigPanel, null);
		encounterConfigPanel.setLayout(null);
				
		{   
				JSplitPane splitPane = new JSplitPane();
				splitPane.setBounds(10, 11, 339, 25);
				encounterConfigPanel.add(splitPane);
				
				JCheckBox chckbxHeadonencounter = new JCheckBox("HeadOn");
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
				
				
				
				JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
				AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				AvoidanceAlgorithmSelectionPanel1.setBounds(20, 47, 314, 80);
				encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
				AvoidanceAlgorithmSelectionPanel1.setLayout(null);
				

				JRadioButton rdbtnORCAavoidancealgorithem1 = new JRadioButton("ORCA");
				rdbtnORCAavoidancealgorithem1.setBounds(8, 22, 70, 23);
				rdbtnORCAavoidancealgorithem1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "ORCAAvoidanceAlgorithm");
				headOnAvoidanceAlgorithmGroup.add(rdbtnORCAavoidancealgorithem1);
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnORCAavoidancealgorithem1);
				rdbtnORCAavoidancealgorithem1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "ORCAAvoidanceAlgorithm";
					}
				});
		
				
				JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
				rdbtnTurnrightavoidancealgorithm1.setBounds(109, 22, 94, 23);
				rdbtnTurnrightavoidancealgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "TurnRightAvoidanceAlgorithm");
				headOnAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
				rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
					}
				});
				
				
				
				JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
				rdbtnSmartturnavoidancealgorithm1.setBounds(207, 22, 99, 23);
				rdbtnSmartturnavoidancealgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "SmartTurnAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
				rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
					}
				});
				
				
				JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
				rdbtnRIPNAvoidanceAlgorithm1.setBounds(8, 49, 57, 23);
				rdbtnRIPNAvoidanceAlgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "RIPNAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
				rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
					}
				});
				

				JRadioButton rdbtnRVONAvoidanceAlgorithm1 = new JRadioButton("RVO");
				rdbtnRVONAvoidanceAlgorithm1.setBounds(109, 49, 62, 23);
				rdbtnRVONAvoidanceAlgorithm1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "RVOAvoidanceAlgorithm");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnRVONAvoidanceAlgorithm1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnRVONAvoidanceAlgorithm1);
				rdbtnRVONAvoidanceAlgorithm1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "RVONAvoidanceAlgorithm";
					}
				});
				
				
				JRadioButton rdbtnNone1 = new JRadioButton("None");
				rdbtnNone1.setBounds(207, 49, 62, 23);
				rdbtnNone1.setSelected(CONFIGURATION.headOnAvoidanceAlgorithmSelection == "None");
				AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
				headOnAvoidanceAlgorithmGroup.add(rdbtnNone1);
				rdbtnNone1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CONFIGURATION.headOnAvoidanceAlgorithmSelection = "None";
					}
				});
				
		}
			
			
		{   JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(10, 190, 339, 25);
			encounterConfigPanel.add(splitPane);
			
			JCheckBox chckbxCrossingEncounter = new JCheckBox("Crossing");
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
			
			JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
			AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel1.setBounds(20, 226, 314, 80);
			encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
			AvoidanceAlgorithmSelectionPanel1.setLayout(null);
			

			
			JRadioButton rdbtnORCAavoidancealgorithem1 = new JRadioButton("ORCA");
			rdbtnORCAavoidancealgorithem1.setBounds(8, 22, 69, 23);
			rdbtnORCAavoidancealgorithem1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "ORCAAvoidanceAlgorithm");
			crossingAvoidanceAlgorithmGroup.add(rdbtnORCAavoidancealgorithem1);
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnORCAavoidancealgorithem1);
			rdbtnORCAavoidancealgorithem1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "ORCAAvoidanceAlgorithm";
				}
			});
			
			JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
			rdbtnTurnrightavoidancealgorithm1.setBounds(109, 22, 94, 23);
			rdbtnTurnrightavoidancealgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "TurnRightAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
			rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
				}
			});
			
			
			
			JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
			rdbtnSmartturnavoidancealgorithm1.setBounds(207, 22, 99, 23);
			rdbtnSmartturnavoidancealgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "SmartTurnAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
			rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
				}
			});
			
			
			JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
			rdbtnRIPNAvoidanceAlgorithm1.setBounds(8, 49, 57, 23);
			rdbtnRIPNAvoidanceAlgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "RIPNAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
			rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
				}
			});
			
			
			JRadioButton rdbtnRVOAvoidanceAlgorithm1 = new JRadioButton("RVO");
			rdbtnRVOAvoidanceAlgorithm1.setBounds(109, 49, 62, 23);
			rdbtnRVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "RVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnRVOAvoidanceAlgorithm1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnRVOAvoidanceAlgorithm1);
			rdbtnRVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "RVOAvoidanceAlgorithm";
				}
			});
			
			JRadioButton rdbtnNone1 = new JRadioButton("None");
			rdbtnNone1.setBounds(207, 49, 62, 23);
			rdbtnNone1.setSelected(CONFIGURATION.crossingAvoidanceAlgorithmSelection == "None");
			AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
			crossingAvoidanceAlgorithmGroup.add(rdbtnNone1);
			rdbtnNone1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CONFIGURATION.crossingAvoidanceAlgorithmSelection = "None";
				}
			});
			
	}
		
		
		
		
	{   JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 378, 339, 25);
		encounterConfigPanel.add(splitPane);
		
		JCheckBox chckbxTailApproachEncounter = new JCheckBox("TailApproach");
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
	
	JPanel AvoidanceAlgorithmSelectionPanel1 = new JPanel();
	AvoidanceAlgorithmSelectionPanel1.setBorder(new TitledBorder(null, "Intruder's Avoidance Algorithm Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	AvoidanceAlgorithmSelectionPanel1.setBounds(20, 416, 314, 80);
	encounterConfigPanel.add(AvoidanceAlgorithmSelectionPanel1);
	AvoidanceAlgorithmSelectionPanel1.setLayout(null);
	

	
	JRadioButton rdbtnORCAavoidancealgorithem1 = new JRadioButton("ORCA");
	rdbtnORCAavoidancealgorithem1.setBounds(8, 22, 69, 23);
	rdbtnORCAavoidancealgorithem1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "ORCAAvoidanceAlgorithm");
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnORCAavoidancealgorithem1);
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnORCAavoidancealgorithem1);
	rdbtnORCAavoidancealgorithem1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "ORCAAvoidanceAlgorithm";
		}
	});
	
	JRadioButton rdbtnTurnrightavoidancealgorithm1 = new JRadioButton("TurnRight");
	rdbtnTurnrightavoidancealgorithm1.setBounds(109, 22, 94, 23);
	rdbtnTurnrightavoidancealgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "TurnRightAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnTurnrightavoidancealgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnTurnrightavoidancealgorithm1);
	rdbtnTurnrightavoidancealgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "TurnRightAvoidanceAlgorithm";
		}
	});
	
	
	
	JRadioButton rdbtnSmartturnavoidancealgorithm1 = new JRadioButton("SmartTurn");
	rdbtnSmartturnavoidancealgorithm1.setBounds(207, 22, 99, 23);
	rdbtnSmartturnavoidancealgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "SmartTurnAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnSmartturnavoidancealgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnSmartturnavoidancealgorithm1);
	rdbtnSmartturnavoidancealgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "SmartTurnAvoidanceAlgorithm";
		}
	});
	
	
	JRadioButton rdbtnRIPNAvoidanceAlgorithm1 = new JRadioButton("RIPN");
	rdbtnRIPNAvoidanceAlgorithm1.setBounds(8, 49, 57, 23);
	rdbtnRIPNAvoidanceAlgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "RIPNAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnRIPNAvoidanceAlgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnRIPNAvoidanceAlgorithm1);
	rdbtnRIPNAvoidanceAlgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "RIPNAvoidanceAlgorithm";
		}
	});
	
	
	JRadioButton rdbtnRVOAvoidanceAlgorithm1 = new JRadioButton("RVO");
	rdbtnRVOAvoidanceAlgorithm1.setBounds(109, 49, 62, 23);
	rdbtnRVOAvoidanceAlgorithm1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "RVOAvoidanceAlgorithm");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnRVOAvoidanceAlgorithm1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnRVOAvoidanceAlgorithm1);
	rdbtnRVOAvoidanceAlgorithm1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "RVOAvoidanceAlgorithm";
		}
	});
	
	JRadioButton rdbtnNone1 = new JRadioButton("None");
	rdbtnNone1.setBounds(207, 49, 62, 23);
	rdbtnNone1.setSelected(CONFIGURATION.tailApproachAvoidanceAlgorithmSelection == "None");
	AvoidanceAlgorithmSelectionPanel1.add(rdbtnNone1);
	tailApproachAvoidanceAlgorithmGroup.add(rdbtnNone1);
	rdbtnNone1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			CONFIGURATION.tailApproachAvoidanceAlgorithmSelection = "None";
		}
	});

  }
}
