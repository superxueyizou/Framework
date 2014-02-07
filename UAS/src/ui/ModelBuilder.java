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

import sim.display.GUIState;
import sim.engine.SimState;
import tools.CONFIGURATION;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import modeling.SAAModelWithUI;

public class ModelBuilder extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup selfCollisionAvoidanceAlgorithmGroup = new ButtonGroup();
	private final ButtonGroup selfSelfSeparationAlgorithmGroup = new ButtonGroup();

	/**
	 * @wbp.parser.constructor
	 */
	public ModelBuilder(SimState state, GUIState stateWithUI) 
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
		

		JRadioButton rdbtnAccidentDetectorEnable = new JRadioButton("AccidentDetector Enable?");
		rdbtnAccidentDetectorEnable.setBounds(12, 53, 229, 15);
		this.add(rdbtnAccidentDetectorEnable);
		rdbtnAccidentDetectorEnable.setSelected(CONFIGURATION.accidentDetectorEnabler);
		rdbtnAccidentDetectorEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					CONFIGURATION.accidentDetectorEnabler = true;
				} else {
					
					CONFIGURATION.accidentDetectorEnabler = false;
				}
			}
		});
	
		JLabel lblModelbuilderSetting = new JLabel("ModelBuilder Setting");
		lblModelbuilderSetting.setBounds(12, 76, 165, 15);
		this.add(lblModelbuilderSetting);
			
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String result = JOptionPane.showInputDialog(null, "copy and paste:", "Genome",JOptionPane.PLAIN_MESSAGE);

				if(result!=null && !result.isEmpty())
				{
					result = result.trim();
					String[] pArr= result.split("\\s+");
//						System.out.println(pArr[1]);
													
					CONFIGURATION.selfDestDist= Double.parseDouble(pArr[0]);
					CONFIGURATION.selfSpeed=Double.parseDouble(pArr[1]);
					
					CONFIGURATION.headOnSelected= Double.parseDouble(pArr[2])==1? true: false;
					CONFIGURATION.headOnOffset=Double.parseDouble(pArr[3]);
					CONFIGURATION.headOnIsRightSide= Double.parseDouble(pArr[4])==10? true: false;			
					CONFIGURATION.headOnSpeed=Double.parseDouble(pArr[5]);
					
		    		CONFIGURATION.crossingSelected = Double.parseDouble(pArr[6])==10? true: false;
		    		CONFIGURATION.crossingEncounterAngle=Double.parseDouble(pArr[7]);
		    		CONFIGURATION.crossingIsRightSide= Double.parseDouble(pArr[8])==10? true: false;
		    		CONFIGURATION.crossingSpeed =Double.parseDouble(pArr[9]);
		    		
		    		CONFIGURATION.tailApproachSelected = Double.parseDouble(pArr[10])==1? true: false;
		    		CONFIGURATION.tailApproachOffset= Double.parseDouble(pArr[11]);
		    		CONFIGURATION.tailApproachIsRightSide=Double.parseDouble(pArr[12])==1? true: false;
		    		CONFIGURATION.tailApproachSpeed =Double.parseDouble(pArr[13]);
		    		
				}
//					System.out.println(CONFIGURATION.selfDestDist);
			}
		});
		btnLoad.setBounds(226, 71, 69, 25);
		this.add(btnLoad);
		
		JLabel lblDestDist = new JLabel("Dest Dist");
		lblDestDist.setBounds(12, 102, 65, 15);
		this.add(lblDestDist);
		
		JLabel lblDestAngle = new JLabel("Dest Angle");
		lblDestAngle.setBounds(12, 129, 77, 15);
		this.add(lblDestAngle);
		
		JSlider destDistSlider = new JSlider();
		destDistSlider.setSnapToTicks(true);
		destDistSlider.setPaintLabels(true);		
		destDistSlider.setMaximum((int)(1.1*CONFIGURATION.selfDestDist/CONFIGURATION.lengthScale));
		destDistSlider.setMinimum((int)(0.5*CONFIGURATION.selfDestDist/CONFIGURATION.lengthScale));
		destDistSlider.setValue((int)(CONFIGURATION.selfDestDist/CONFIGURATION.lengthScale));
		destDistSlider.setBounds(95, 103, 200, 16);
		this.add(destDistSlider);
		destDistSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				CONFIGURATION.selfDestDist = source.getValue()*CONFIGURATION.lengthScale;
			}
		});
		
		JSlider destAngleSlider = new JSlider();
		destAngleSlider.setValue((int)Math.toDegrees(CONFIGURATION.selfDestAngle));
		destAngleSlider.setMinimum(-180);
		destAngleSlider.setMaximum(180);
		destAngleSlider.setBounds(95, 129, 200, 16);
		this.add(destAngleSlider);
		destAngleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				CONFIGURATION.selfDestAngle = Math.toRadians(source.getValue());
			}
		});
		
		
		
	}

	public ModelBuilder(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public ModelBuilder(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public ModelBuilder(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
