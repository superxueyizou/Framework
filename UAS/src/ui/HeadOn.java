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
			JPanel sensorSelectionPanel = new JPanel();
			sensorSelectionPanel.setBorder(new TitledBorder(null, "Sensor Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sensorSelectionPanel.setBounds(12, 101, 297, 125);
			add(sensorSelectionPanel);
			sensorSelectionPanel.setLayout(null);
			
			JCheckBox chckbxPerfectSensor = new JCheckBox("Perfect");
			chckbxPerfectSensor.setBounds(8, 20, 129, 23);
			sensorSelectionPanel.add(chckbxPerfectSensor);
			chckbxPerfectSensor.setSelected((CONFIGURATION.headOnSensorSelection&0B10000) == 0B10000);
			chckbxPerfectSensor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B10000;
					}
				}
			});
			
			
			JCheckBox chckbxAdsb = new JCheckBox("ADS-B");
			chckbxAdsb.setBounds(144, 20, 129, 23);
			sensorSelectionPanel.add(chckbxAdsb);
			chckbxAdsb.setSelected((CONFIGURATION.headOnSensorSelection&0B01000) == 0B01000);
			chckbxAdsb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B01000;
					}
				}
			});
			
			JCheckBox chckbxTcas = new JCheckBox("TCAS");
			chckbxTcas.setBounds(8, 47, 129, 23);
			sensorSelectionPanel.add(chckbxTcas);
			chckbxTcas.setSelected((CONFIGURATION.headOnSensorSelection&0B00100) == 0B00100);
			chckbxTcas.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B00100;
					}
				}
			});
			
			JCheckBox chckbxRadar = new JCheckBox("Radar");
			chckbxRadar.setBounds(144, 47, 129, 23);
			sensorSelectionPanel.add(chckbxRadar);
			chckbxRadar.setSelected((CONFIGURATION.headOnSensorSelection&0B00010) == 0B00010);
			chckbxRadar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B00010;
					}
				}
			});
			
			JCheckBox chckbxEoir = new JCheckBox("EO/IR");
			chckbxEoir.setBounds(8, 74, 129, 23);
			sensorSelectionPanel.add(chckbxEoir);
			chckbxEoir.setSelected((CONFIGURATION.headOnSensorSelection&0B00001) == 0B00001);
			chckbxEoir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnSensorSelection |= 0B00001;
					}
				}
			});
		}
		
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(12, 12, 290, 31);
			
			JCheckBox chckbxHeadonencounter = new JCheckBox("HeadOn");
			chckbxHeadonencounter.setSelected(CONFIGURATION.headOnSelected==1);
			chckbxHeadonencounter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox chckbxHeadonencounter = (JCheckBox)e.getSource();
					CONFIGURATION.headOnSelected=chckbxHeadonencounter.isSelected()?1:0;
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
			rdbtnIsrightside.setBounds(12, 58, 105, 23);
			rdbtnIsrightside.setSelected(CONFIGURATION.headOnIsRightSide==1);
			rdbtnIsrightside.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnIsRightSide = 1;
					}
					else
					{
						CONFIGURATION.headOnIsRightSide = 0;
					}
				}
			});
			this.add(rdbtnIsrightside);
			
			JLabel lblOffset = new JLabel("Offset");
			lblOffset.setBounds(171, 62, 44, 15);
			this.add(lblOffset);
			
			headOnOffsetTextField = new JTextField();
			headOnOffsetTextField.setBounds(233, 60, 69, 19);
			headOnOffsetTextField.setText(""+CONFIGURATION.headOnOffset/CONFIGURATION.lengthScale);
			headOnOffsetTextField.setColumns(10);
			headOnOffsetTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField offsetTextField=(JTextField) e.getSource();
					CONFIGURATION.headOnOffset=Double.parseDouble(offsetTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			this.add(headOnOffsetTextField);
		}
		
	
		
		
		{
			JPanel AvoidanceAlgorithmSelectionPanel = new JPanel();
			AvoidanceAlgorithmSelectionPanel.setBorder(new TitledBorder(null, "CAA Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 238, 297, 53);
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
			
			JRadioButton rdbtnSVOAvoidanceAlgorithm = new JRadioButton("SVO");
			rdbtnSVOAvoidanceAlgorithm.setBounds(108, 22, 94, 23);
			rdbtnSVOAvoidanceAlgorithm.setSelected(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection == "SVOAvoidanceAlgorithm");
			AvoidanceAlgorithmSelectionPanel.add(rdbtnSVOAvoidanceAlgorithm);
			headOnCollisionAvoidanceAlgorithmGroup.add(rdbtnSVOAvoidanceAlgorithm);
			rdbtnSVOAvoidanceAlgorithm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection = "SVOAvoidanceAlgorithm";
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
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 303, 297, 59);
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
		
		JPanel performancePanel = new JPanel();
		performancePanel.setBounds(12, 374, 297, 188);
		add(performancePanel);
		performancePanel.setLayout(null);
		JLabel lblMaxspeed = new JLabel("MaxSpeed");
		lblMaxspeed.setBounds(12, 14, 82, 15);
		performancePanel.add(lblMaxspeed);
		
		
		JTextField maxSpeedTextField_1 = new JTextField();
		maxSpeedTextField_1.setBounds(170, 14, 114, 19);
		performancePanel.add(maxSpeedTextField_1);
		maxSpeedTextField_1.setText(String.valueOf(CONFIGURATION.headOnMaxSpeed/CONFIGURATION.lengthScale));
		maxSpeedTextField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.headOnMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxSpeedTextField_1.setColumns(10);
		
		
		JLabel lblMinspeed = new JLabel("MinSpeed");
		lblMinspeed.setBounds(12, 43, 70, 19);
		performancePanel.add(lblMinspeed);
		
		
		JTextField minSpeedTextField_1 = new JTextField();
		minSpeedTextField_1.setBounds(170, 45, 114, 19);
		performancePanel.add(minSpeedTextField_1);
		minSpeedTextField_1.setText(String.valueOf(CONFIGURATION.headOnMinSpeed/CONFIGURATION.lengthScale));
		minSpeedTextField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField minSpeedTextField = (JTextField) e.getSource();
				CONFIGURATION.headOnMinSpeed = new Double(minSpeedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		minSpeedTextField_1.setColumns(10);
		
		JLabel lblPrefSpeed = new JLabel("PrefSpeed");
		lblPrefSpeed.setBounds(12, 70, 105, 15);
		performancePanel.add(lblPrefSpeed);
		
		JTextField prefSpeedTextField = new JTextField();
		prefSpeedTextField.setBounds(171, 72, 114, 19);
		performancePanel.add(prefSpeedTextField);
		prefSpeedTextField.setText(String.valueOf(CONFIGURATION.headOnPrefSpeed/CONFIGURATION.lengthScale));
		prefSpeedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField speedTextField = (JTextField) e.getSource();
				CONFIGURATION.headOnPrefSpeed = new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		prefSpeedTextField.setColumns(10);
		
		
		
		
		JLabel lblMaxClimb = new JLabel("MaxClimb");
		lblMaxClimb.setBounds(12, 97, 70, 19);
		performancePanel.add(lblMaxClimb);
		
		
		JTextField maxClimbTextField_1 = new JTextField();
		maxClimbTextField_1.setBounds(170, 99, 114, 19);
		performancePanel.add(maxClimbTextField_1);
		maxClimbTextField_1.setText(String.valueOf(CONFIGURATION.headOnMaxClimb/CONFIGURATION.lengthScale));
		maxClimbTextField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxClimbTextField = (JTextField) e.getSource();
				CONFIGURATION.headOnMaxClimb = new Double(maxClimbTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxClimbTextField_1.setColumns(10);
		
		JLabel lblMaxDescent = new JLabel("MaxDescent");
		lblMaxDescent.setBounds(12, 131, 101, 19);
		performancePanel.add(lblMaxDescent);
		
		
		JTextField maxDescentTextField_1 = new JTextField();
		maxDescentTextField_1.setBounds(170, 133, 114, 19);
		performancePanel.add(maxDescentTextField_1);
		maxDescentTextField_1.setText(String.valueOf(CONFIGURATION.headOnMaxDescent/CONFIGURATION.lengthScale));
		maxDescentTextField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField maxDescentTextField = (JTextField) e.getSource();
				CONFIGURATION.headOnMaxDescent = new Double(maxDescentTextField.getText())*CONFIGURATION.lengthScale;
			}
		});
		maxDescentTextField_1.setColumns(10);
		
		JLabel lblMaxturning = new JLabel("MaxTurning");
		lblMaxturning.setBounds(12, 162, 82, 15);
		performancePanel.add(lblMaxturning);
		
		{
			
			JTextField maxTurningTextField_1 = new JTextField();
			maxTurningTextField_1.setBounds(171, 164, 114, 19);
			performancePanel.add(maxTurningTextField_1);
			maxTurningTextField_1.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.headOnMaxTurning)*100)/100.0));
			maxTurningTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxTurningTextField = (JTextField) e.getSource();
					CONFIGURATION.headOnMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
				}
			});
			maxTurningTextField_1.setColumns(10);
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
