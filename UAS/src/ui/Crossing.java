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
			JPanel sensorSelectionPanel = new JPanel();
			sensorSelectionPanel.setBorder(new TitledBorder(null, "Sensor Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sensorSelectionPanel.setBounds(12, 82, 297, 125);
			add(sensorSelectionPanel);
			sensorSelectionPanel.setLayout(null);
			
			JCheckBox chckbxPerfectSensor = new JCheckBox("Perfect");
			chckbxPerfectSensor.setBounds(8, 20, 129, 23);
			sensorSelectionPanel.add(chckbxPerfectSensor);
			chckbxPerfectSensor.setSelected((CONFIGURATION.crossingSensorSelection&0B10000) == 0B10000);
			chckbxPerfectSensor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingSensorSelection |= 0B10000;
					}
				}
			});
			
			
			JCheckBox chckbxAdsb = new JCheckBox("ADS-B");
			chckbxAdsb.setBounds(144, 20, 129, 23);
			sensorSelectionPanel.add(chckbxAdsb);
			chckbxAdsb.setSelected((CONFIGURATION.crossingSensorSelection&0B01000) == 0B01000);
			chckbxAdsb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingSensorSelection |= 0B01000;
					}
				}
			});
			
			JCheckBox chckbxTcas = new JCheckBox("TCAS");
			chckbxTcas.setBounds(8, 47, 129, 23);
			sensorSelectionPanel.add(chckbxTcas);
			chckbxTcas.setSelected((CONFIGURATION.crossingSensorSelection&0B00100) == 0B00100);
			chckbxTcas.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingSensorSelection |= 0B00100;
					}
				}
			});
			
			JCheckBox chckbxRadar = new JCheckBox("Radar");
			chckbxRadar.setBounds(144, 47, 129, 23);
			sensorSelectionPanel.add(chckbxRadar);
			chckbxRadar.setSelected((CONFIGURATION.crossingSensorSelection&0B00010) == 0B00010);
			chckbxRadar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingSensorSelection |= 0B00010;
					}
				}
			});
			
			JCheckBox chckbxEoir = new JCheckBox("EO/IR");
			chckbxEoir.setBounds(8, 74, 129, 23);
			sensorSelectionPanel.add(chckbxEoir);
			chckbxEoir.setSelected((CONFIGURATION.crossingSensorSelection&0B00001) == 0B00001);
			chckbxEoir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(((JCheckBox)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingSensorSelection |= 0B00001;
					}
				}
			});
		}
		
		
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(12, 12, 290, 31);
			this.add(splitPane);
			
			JCheckBox chckbxCrossingEncounter = new JCheckBox("Crossing");
			chckbxCrossingEncounter.setSelected(CONFIGURATION.crossingSelected==1);
			chckbxCrossingEncounter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox chckbxCrossingEncounter = (JCheckBox)e.getSource();
					CONFIGURATION.crossingSelected=chckbxCrossingEncounter.isSelected()?1:0;
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
			lblEncounterangel.setBounds(152, 55, 50, 15);
			this.add(lblEncounterangel);
			
			JRadioButton rdbtnIsrightside_1 = new JRadioButton("IsRightSide");
			rdbtnIsrightside_1.setBounds(12, 51, 105, 23);
			rdbtnIsrightside_1.setSelected(CONFIGURATION.crossingIsRightSide==1);
			this.add(rdbtnIsrightside_1);
			rdbtnIsrightside_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((JRadioButton)e.getSource()).isSelected())
					{
						CONFIGURATION.crossingIsRightSide = 1;
					}
					else
					{
						CONFIGURATION.crossingIsRightSide = 0;
					}
				}
			});
			
			crossingAngleTextField = new JTextField();
			crossingAngleTextField.setBounds(220, 55, 82, 19);
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
			AvoidanceAlgorithmSelectionPanel.setBounds(12, 222, 297, 53);
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
			selfSeparationAlgorithmSelectionPanel.setBounds(12, 287, 297, 59);
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
		
		JPanel performancePanel = new JPanel();
		performancePanel.setBounds(12, 358, 297, 220);
		add(performancePanel);
		performancePanel.setLayout(null);
		JLabel lblMaxspeed = new JLabel("MaxSpeed");
		lblMaxspeed.setBounds(12, 14, 82, 15);
		performancePanel.add(lblMaxspeed);
		
		
		JLabel lblMinspeed = new JLabel("MinSpeed");
		lblMinspeed.setBounds(12, 43, 70, 19);
		performancePanel.add(lblMinspeed);
		
		JLabel lblPrefSpeed = new JLabel("PrefSpeed");
		lblPrefSpeed.setBounds(12, 70, 105, 15);
		performancePanel.add(lblPrefSpeed);
		
			
			
			JLabel lblMaxClimb = new JLabel("MaxClimb");
			lblMaxClimb.setBounds(12, 97, 70, 19);
			performancePanel.add(lblMaxClimb);
			
			JLabel lblMaxDescent = new JLabel("MaxDescent");
			lblMaxDescent.setBounds(12, 131, 101, 19);
			performancePanel.add(lblMaxDescent);
			
			JLabel lblMaxturning = new JLabel("MaxTurning");
			lblMaxturning.setBounds(12, 162, 82, 15);
			performancePanel.add(lblMaxturning);
			
			
			JTextField maxSpeedTextField_1 = new JTextField();
			maxSpeedTextField_1.setBounds(170, 12, 114, 19);
			performancePanel.add(maxSpeedTextField_1);
			maxSpeedTextField_1.setText(String.valueOf(CONFIGURATION.crossingMaxSpeed/CONFIGURATION.lengthScale));
			maxSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxSpeedTextField_1.setColumns(10);
			
			
			JTextField minSpeedTextField_1 = new JTextField();
			minSpeedTextField_1.setBounds(170, 43, 114, 19);
			performancePanel.add(minSpeedTextField_1);
			minSpeedTextField_1.setText(String.valueOf(CONFIGURATION.crossingMinSpeed/CONFIGURATION.lengthScale));
			minSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMinSpeed = new Double(minSpeedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			minSpeedTextField_1.setColumns(10);
			
			JTextField prefSpeedTextField = new JTextField();
			prefSpeedTextField.setBounds(171, 70, 114, 19);
			performancePanel.add(prefSpeedTextField);
			prefSpeedTextField.setText(String.valueOf(CONFIGURATION.crossingPrefSpeed/CONFIGURATION.lengthScale));
			prefSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField speedTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingPrefSpeed = new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			prefSpeedTextField.setColumns(10);
			
			
			JTextField maxClimbTextField_1 = new JTextField();
			maxClimbTextField_1.setBounds(170, 97, 114, 19);
			performancePanel.add(maxClimbTextField_1);
			maxClimbTextField_1.setText(String.valueOf(CONFIGURATION.crossingMaxClimb/CONFIGURATION.lengthScale));
			maxClimbTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMaxClimb = new Double(maxClimbTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxClimbTextField_1.setColumns(10);
			
			
			JTextField maxDescentTextField_1 = new JTextField();
			maxDescentTextField_1.setBounds(170, 131, 114, 19);
			performancePanel.add(maxDescentTextField_1);
			maxDescentTextField_1.setText(String.valueOf(CONFIGURATION.crossingMaxDescent/CONFIGURATION.lengthScale));
			maxDescentTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					CONFIGURATION.crossingMaxDescent = new Double(maxDescentTextField.getText())*CONFIGURATION.lengthScale;
				}
			});
			maxDescentTextField_1.setColumns(10);
			
			{
				
				JTextField maxTurningTextField_1 = new JTextField();
				maxTurningTextField_1.setBounds(171, 162, 114, 19);
				performancePanel.add(maxTurningTextField_1);
				maxTurningTextField_1.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.crossingMaxTurning)*100)/100.0));
				maxTurningTextField_1.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField maxTurningTextField = (JTextField) e.getSource();
						CONFIGURATION.crossingMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
					}
				});
				maxTurningTextField_1.setColumns(10);
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
