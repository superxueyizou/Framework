package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import tools.CONFIGURATION;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntruderConfig extends JDialog 
{

	private final JPanel contentPanel = new JPanel();
	
	private JTextField maxSpeedTextField;
	private JTextField maxAccelerationTextField;
	private JTextField maxDecelerationTextField;
	private JTextField maxTurningTextField;
	private JTextField viewingRangeTextField;
	private JTextField speedTextField;
	private JTextField viewingAngleTextField;
	private JTextField sensitivityForCollisionTextField;
	private JTextField safetyRadiusTextField;
	private JTextField alfaTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IntruderConfig dialog = new IntruderConfig();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IntruderConfig() 
	{
		setModal(true);
		setBounds(1236, 401, 347, 474); // for windows: setBounds(1160, 380, 347, 474); 
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
			
		JLabel lblMaxspeed = new JLabel("MaxSpeed");
		lblMaxspeed.setBounds(10, 30, 96, 14);
		contentPanel.add(lblMaxspeed);
		
		JLabel lblMaxacceleration = new JLabel("MaxAcceleration");
		lblMaxacceleration.setBounds(10, 72, 179, 14);
		contentPanel.add(lblMaxacceleration);
		
		JLabel lblMaxdecceleration = new JLabel("MaxDeceleration");
		lblMaxdecceleration.setBounds(10, 111, 169, 14);
		contentPanel.add(lblMaxdecceleration);
		
		JLabel lblMaxturning = new JLabel("MaxTurning");
		lblMaxturning.setBounds(10, 148, 107, 14);
		contentPanel.add(lblMaxturning);
		
		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(10, 185, 107, 14);
		contentPanel.add(lblSpeed);
		
		JLabel lblViewingRange = new JLabel("ViewingRange");
		lblViewingRange.setBounds(10, 224, 107, 14);
		contentPanel.add(lblViewingRange);
		
		JLabel lblViewingAngle = new JLabel("ViewingAngle");
		lblViewingAngle.setBounds(10, 267, 107, 14);
		contentPanel.add(lblViewingAngle);
		
		JLabel lblSensitivityForCollisions = new JLabel("SensitivityForCollisions");
		lblSensitivityForCollisions.setBounds(10, 306, 179, 14);
		contentPanel.add(lblSensitivityForCollisions);
		

		JLabel lblSafetyradius = new JLabel("SafetyRadius");
		lblSafetyradius.setBounds(10, 342, 107, 14);
		contentPanel.add(lblSafetyradius);
		
		JLabel lblAlfa = new JLabel("Alfa");
		lblAlfa.setBounds(10, 383, 70, 15);
		contentPanel.add(lblAlfa);
		
		
		maxSpeedTextField = new JTextField();
		maxSpeedTextField.setBounds(197, 27, 86, 20);
		contentPanel.add(maxSpeedTextField);
		maxSpeedTextField.setColumns(10);
		
		maxAccelerationTextField = new JTextField();		
		maxAccelerationTextField.setBounds(197, 66, 86, 20);
		contentPanel.add(maxAccelerationTextField);
		maxAccelerationTextField.setColumns(10);
		
		maxDecelerationTextField = new JTextField();
		maxDecelerationTextField.setBounds(197, 108, 86, 20);
		contentPanel.add(maxDecelerationTextField);
		maxDecelerationTextField.setColumns(10);
		
		maxTurningTextField = new JTextField();
		maxTurningTextField.setBounds(197, 145, 86, 20);
		contentPanel.add(maxTurningTextField);
		maxTurningTextField.setColumns(10);
		
		speedTextField = new JTextField();
		speedTextField.setBounds(197, 182, 86, 20);
		contentPanel.add(speedTextField);
		speedTextField.setColumns(10);
		
		viewingRangeTextField = new JTextField();		
		viewingRangeTextField.setBounds(197, 221, 86, 20);
		contentPanel.add(viewingRangeTextField);
		viewingRangeTextField.setColumns(10);
		
		viewingAngleTextField = new JTextField();		
		viewingAngleTextField.setBounds(197, 264, 86, 20);
		contentPanel.add(viewingAngleTextField);
		viewingAngleTextField.setColumns(10);
		
		sensitivityForCollisionTextField = new JTextField();		
		sensitivityForCollisionTextField.setBounds(197, 303, 86, 20);
		contentPanel.add(sensitivityForCollisionTextField);
		sensitivityForCollisionTextField.setColumns(10);
		
		safetyRadiusTextField = new JTextField();		
		safetyRadiusTextField.setBounds(197, 339, 86, 20);
		contentPanel.add(safetyRadiusTextField);
		safetyRadiusTextField.setColumns(10);
				
		alfaTextField = new JTextField();
		alfaTextField.setBounds(197, 379, 86, 20);
		contentPanel.add(alfaTextField);
		alfaTextField.setColumns(10);


		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(getRootPane().getParent().getName());

				if(getRootPane().getParent().getName() == "HeadonEncounter--IntruderConfig")
				{
					CONFIGURATION.headOnMaxSpeed = new Double(maxSpeedTextField.getText());
					CONFIGURATION.headOnMaxAcceleration = new Double(maxAccelerationTextField.getText());
					CONFIGURATION.headOnMaxDeceleration = new Double(maxDecelerationTextField.getText());
					CONFIGURATION.headOnMaxTurning = new Double(maxTurningTextField.getText());
					CONFIGURATION.headOnSpeed =  new Double(speedTextField.getText());
					CONFIGURATION.headOnViewingRange = new Double(viewingRangeTextField.getText());
					CONFIGURATION.headOnViewingAngle = new Double(viewingAngleTextField.getText());
					CONFIGURATION.headOnSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
					CONFIGURATION.headOnSafetyRadius= new Double(safetyRadiusTextField.getText()); 
					CONFIGURATION.headOnAlfa= new Double(alfaTextField.getText()); 					
					System.out.println("777777777777777777777");
					
				}
				else if(getRootPane().getParent().getName() == "CrossingEncounter--IntruderConfig")
				{
					CONFIGURATION.crossingMaxSpeed = new Double(maxSpeedTextField.getText());
					CONFIGURATION.crossingMaxAcceleration = new Double(maxAccelerationTextField.getText());
					CONFIGURATION.crossingMaxDeceleration = new Double(maxDecelerationTextField.getText());
					CONFIGURATION.crossingMaxTurning = new Double(maxTurningTextField.getText());
					CONFIGURATION.crossingSpeed = new Double(speedTextField.getText());
					CONFIGURATION.crossingViewingRange = new Double(viewingRangeTextField.getText());
					CONFIGURATION.crossingViewingAngle = new Double(viewingAngleTextField.getText());
					CONFIGURATION.crossingSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
					CONFIGURATION.crossingSafetyRadius= new Double(safetyRadiusTextField.getText());
					CONFIGURATION.crossingAlfa= new Double(alfaTextField.getText()); 
					System.out.println("88888888888888888");
				}
				else if(getRootPane().getParent().getName() == "TailApproachEncounter--IntruderConfig")
				{
					CONFIGURATION.tailApproachMaxSpeed = new Double(maxSpeedTextField.getText());
					CONFIGURATION.tailApproachMaxAcceleration = new Double(maxAccelerationTextField.getText());
					CONFIGURATION.tailApproachMaxDeceleration = new Double(maxDecelerationTextField.getText());
					CONFIGURATION.tailApproachMaxTurning = new Double(maxTurningTextField.getText());
					CONFIGURATION.tailApproachSpeed =  new Double(speedTextField.getText());
					CONFIGURATION.tailApproachViewingRange = new Double(viewingRangeTextField.getText());
					CONFIGURATION.tailApproachViewingAngle = new Double(viewingAngleTextField.getText());
					CONFIGURATION.tailApproachSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
					CONFIGURATION.tailApproachSafetyRadius= new Double(safetyRadiusTextField.getText());
					CONFIGURATION.tailApproachAlfa= new Double(alfaTextField.getText());
					System.out.println("99999999999999999999999");
				}
				else
				{
					
				}
			
				dispose();
			//setVisible(false);
							
			}
		});
		
	
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}

	public IntruderConfig(Object object, String string, boolean b)
	{
		// TODO Auto-generated constructor stub
	}
	
	public void textFieldInit(String str)
	{
		// TODO Auto-generated constructor stub
		switch (str)
		{
		case "HeadOnEncounter--IntruderConfig":
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.headOnMaxSpeed));
			maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.headOnMaxAcceleration));
			maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.headOnMaxDeceleration));
			maxTurningTextField.setText(String.valueOf(CONFIGURATION.headOnMaxTurning));
			speedTextField.setText(String.valueOf(CONFIGURATION.headOnSpeed));
			viewingRangeTextField.setText(String.valueOf(CONFIGURATION.headOnViewingRange));
			viewingAngleTextField.setText(String.valueOf(CONFIGURATION.headOnViewingAngle));
			sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.headOnSensitivityForCollisions));
			safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.headOnSafetyRadius));
			alfaTextField.setText(String.valueOf(CONFIGURATION.headOnAlfa));
			break;
			
		case "CrossingEncounter--IntruderConfig":
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.crossingMaxSpeed));
			maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.crossingMaxAcceleration));
			maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.crossingMaxDeceleration));
			maxTurningTextField.setText(String.valueOf(CONFIGURATION.crossingMaxTurning));
			speedTextField.setText(String.valueOf(CONFIGURATION.crossingSpeed));
			viewingRangeTextField.setText(String.valueOf(CONFIGURATION.crossingViewingRange));
			viewingAngleTextField.setText(String.valueOf(CONFIGURATION.crossingViewingAngle));
			sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.crossingSensitivityForCollisions));
			safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.crossingSafetyRadius));
			alfaTextField.setText(String.valueOf(CONFIGURATION.crossingAlfa));
			break;
			
		case "TailApproachEncounter--IntruderConfig":
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxSpeed));
			maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxAcceleration));
			maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxDeceleration));
			maxTurningTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxTurning));
			speedTextField.setText(String.valueOf(CONFIGURATION.tailApproachSpeed));
			viewingRangeTextField.setText(String.valueOf(CONFIGURATION.tailApproachViewingRange));
			viewingAngleTextField.setText(String.valueOf(CONFIGURATION.tailApproachViewingAngle));
			sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.tailApproachSensitivityForCollisions));
			safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.tailApproachSafetyRadius));
			alfaTextField.setText(String.valueOf(CONFIGURATION.tailApproachAlfa));
			break;
			
			default:
						
		}
	}
	
	
	

}
