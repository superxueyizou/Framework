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
	private JTextField alphaTextField;

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
		
		JLabel lblAlpha = new JLabel("Alpha");
		lblAlpha.setBounds(10, 383, 70, 15);
		contentPanel.add(lblAlpha);
		
		
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
				
		alphaTextField = new JTextField();
		alphaTextField.setBounds(197, 379, 86, 20);
		contentPanel.add(alphaTextField);
		alphaTextField.setColumns(10);


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
					CONFIGURATION.headOnMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.headOnMaxAcceleration = new Double(maxAccelerationTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.headOnMaxDeceleration = new Double(maxDecelerationTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.headOnMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
					CONFIGURATION.headOnPrefSpeed =  new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.headOnViewingRange = new Double(viewingRangeTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.headOnViewingAngle = Math.toRadians(new Double(viewingAngleTextField.getText()));
					CONFIGURATION.headOnSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.headOnSafetyRadius= new Double(safetyRadiusTextField.getText())*CONFIGURATION.lengthScale; 
					CONFIGURATION.headOnAlpha= new Double(alphaTextField.getText()); 					
					//System.out.println(viewingRangeTextField.getText()+"777777777777777777777");
					
				}
				else if(getRootPane().getParent().getName() == "CrossingEncounter--IntruderConfig")
				{
					CONFIGURATION.crossingMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.crossingMaxAcceleration = new Double(maxAccelerationTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.crossingMaxDeceleration = new Double(maxDecelerationTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.crossingMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
					CONFIGURATION.crossingPrefSpeed = new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.crossingViewingRange = new Double(viewingRangeTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.crossingViewingAngle = Math.toRadians(new Double(viewingAngleTextField.getText()));
					CONFIGURATION.crossingSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.crossingSafetyRadius= new Double(safetyRadiusTextField.getText())*CONFIGURATION.lengthScale;
					CONFIGURATION.crossingAlpha= new Double(alphaTextField.getText()); 
					//System.out.println("88888888888888888");
				}
//				else if(getRootPane().getParent().getName() == "TailApproachEncounter--IntruderConfig")
//				{
//					CONFIGURATION.tailApproachMaxSpeed = new Double(maxSpeedTextField.getText())*CONFIGURATION.lengthScale;
//					CONFIGURATION.tailApproachMaxAcceleration = new Double(maxAccelerationTextField.getText())*CONFIGURATION.lengthScale;
//					CONFIGURATION.tailApproachMaxDeceleration = new Double(maxDecelerationTextField.getText())*CONFIGURATION.lengthScale;
//					CONFIGURATION.tailApproachMaxTurning = Math.toRadians(new Double(maxTurningTextField.getText()));
//					CONFIGURATION.tailApproachPrefSpeed =  new Double(speedTextField.getText())*CONFIGURATION.lengthScale;
//					CONFIGURATION.tailApproachViewingRange = new Double(viewingRangeTextField.getText())*CONFIGURATION.lengthScale;
//					CONFIGURATION.tailApproachViewingAngle = Math.toRadians(new Double(viewingAngleTextField.getText()));
//					CONFIGURATION.tailApproachSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText())*CONFIGURATION.lengthScale;
//					CONFIGURATION.tailApproachSafetyRadius= new Double(safetyRadiusTextField.getText())*CONFIGURATION.lengthScale;
//					CONFIGURATION.tailApproachAlpha= new Double(alphaTextField.getText());
//					//System.out.println("99999999999999999999999");
//				}
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
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.headOnMaxSpeed/CONFIGURATION.lengthScale));
			maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.headOnMaxAcceleration/CONFIGURATION.lengthScale));
			maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.headOnMaxDeceleration/CONFIGURATION.lengthScale));
			maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.headOnMaxTurning)*100)/100.0));
			speedTextField.setText(String.valueOf(CONFIGURATION.headOnPrefSpeed/CONFIGURATION.lengthScale));
			viewingRangeTextField.setText(String.valueOf(CONFIGURATION.headOnViewingRange/CONFIGURATION.lengthScale));
			viewingAngleTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.headOnViewingAngle)*100)/100.0));
			sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.headOnSensitivityForCollisions/CONFIGURATION.lengthScale));
			safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.headOnSafetyRadius/CONFIGURATION.lengthScale));
			alphaTextField.setText(String.valueOf(CONFIGURATION.headOnAlpha));
			break;
			
		case "CrossingEncounter--IntruderConfig":
			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.crossingMaxSpeed/CONFIGURATION.lengthScale));
			maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.crossingMaxAcceleration/CONFIGURATION.lengthScale));
			maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.crossingMaxDeceleration/CONFIGURATION.lengthScale));
			maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.crossingMaxTurning)*100)/100.0));
			speedTextField.setText(String.valueOf(CONFIGURATION.crossingPrefSpeed/CONFIGURATION.lengthScale));
			viewingRangeTextField.setText(String.valueOf(CONFIGURATION.crossingViewingRange/CONFIGURATION.lengthScale));
			viewingAngleTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.crossingViewingAngle)*100)/100.0));
			sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.crossingSensitivityForCollisions/CONFIGURATION.lengthScale));
			safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.crossingSafetyRadius/CONFIGURATION.lengthScale));
			alphaTextField.setText(String.valueOf(CONFIGURATION.crossingAlpha));
			break;
			
//		case "TailApproachEncounter--IntruderConfig":
//			maxSpeedTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxSpeed/CONFIGURATION.lengthScale));
//			maxAccelerationTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxAcceleration/CONFIGURATION.lengthScale));
//			maxDecelerationTextField.setText(String.valueOf(CONFIGURATION.tailApproachMaxDeceleration/CONFIGURATION.lengthScale));
//			maxTurningTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.tailApproachMaxTurning)*100)/100.0));
//			speedTextField.setText(String.valueOf(CONFIGURATION.tailApproachPrefSpeed/CONFIGURATION.lengthScale));
//			viewingRangeTextField.setText(String.valueOf(CONFIGURATION.tailApproachViewingRange/CONFIGURATION.lengthScale));
//			viewingAngleTextField.setText(String.valueOf(Math.round(Math.toDegrees(CONFIGURATION.tailApproachViewingAngle)*100)/100.0));
//			sensitivityForCollisionTextField.setText(String.valueOf(CONFIGURATION.tailApproachSensitivityForCollisions/CONFIGURATION.lengthScale));
//			safetyRadiusTextField.setText(String.valueOf(CONFIGURATION.tailApproachSafetyRadius/CONFIGURATION.lengthScale));
//			alphaTextField.setText(String.valueOf(CONFIGURATION.tailApproachAlpha));
//			break;
			
			default:
						
		}
	}
	
	
	

}
