package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modeling.CONFIGURATION;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntruderConfig extends JDialog {

	private final JPanel contentPanel = new JPanel();
//	public Double intruderMaxSpeed;
//	public Double intruderMaxAcceleration;
//	public Double intruderMaxDeceleration;
//	public Double intruderMaxTurning;
//	public Double intruderSpeed;
//	public Double intruderViewingRange;
//	public Double intruderViewingAngle;
//	public Double intruderSensitivityForCollisions;
//	public Double intruderSafetyRadius;
	
	private JTextField maxSpeedTextField;
	private JTextField maxAccelerationTextField;
	private JTextField maxDecelerationTextField;
	private JTextField maxTurningTextField;
	private JTextField viewingRangeTextField;
	private JTextField speedTextField;
	private JTextField viewingAngleTextField;
	private JTextField sensitivityForCollisionTextField;
	private JTextField safetyRadiusTextField;

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
	public IntruderConfig() {
		setModal(true);
		setBounds(100, 100, 347, 474);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			
			JLabel lblMaxspeed = new JLabel("MaxSpeed:");
			lblMaxspeed.setBounds(10, 30, 96, 14);
			contentPanel.add(lblMaxspeed);
			
			JLabel lblMaxacceleration = new JLabel("MaxAcceleration");
			lblMaxacceleration.setBounds(10, 72, 96, 14);
			contentPanel.add(lblMaxacceleration);
			
			JLabel lblMaxdecceleration = new JLabel("MaxDeceleration");
			lblMaxdecceleration.setBounds(10, 111, 107, 14);
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
			lblSensitivityForCollisions.setBounds(10, 306, 143, 14);
			contentPanel.add(lblSensitivityForCollisions);
			
			
			maxSpeedTextField = new JTextField();
			maxSpeedTextField.setText("2.235");
			maxSpeedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField maxSpeedTextField = (JTextField) e.getSource();
//					intruderMaxSpeed = new Double(maxSpeedTextField.getText());
				}
			});
			
			JLabel lblSafetyradius = new JLabel("SafetyRadius");
			lblSafetyradius.setBounds(10, 342, 107, 14);
			contentPanel.add(lblSafetyradius);
			maxSpeedTextField.setBounds(197, 27, 86, 20);
			contentPanel.add(maxSpeedTextField);
			maxSpeedTextField.setColumns(10);
			
			maxAccelerationTextField = new JTextField();
			maxAccelerationTextField.setText("0.05");
			maxAccelerationTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField maxAccelerationTextField = (JTextField) e.getSource();
//					intruderMaxAcceleration = new Double(maxAccelerationTextField.getText());
				}
			});
			maxAccelerationTextField.setBounds(197, 66, 86, 20);
			contentPanel.add(maxAccelerationTextField);
			maxAccelerationTextField.setColumns(10);
			
			maxDecelerationTextField = new JTextField();
			maxDecelerationTextField.setText("0.05");
			maxDecelerationTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField maxDecelerationTextField = (JTextField) e.getSource();
//					intruderMaxDeceleration = new Double(maxDecelerationTextField.getText());
				}
			});
			maxDecelerationTextField.setBounds(197, 108, 86, 20);
			contentPanel.add(maxDecelerationTextField);
			maxDecelerationTextField.setColumns(10);
			
			maxTurningTextField = new JTextField();
			maxTurningTextField.setText("5");
			maxTurningTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField maxTurningTextField = (JTextField) e.getSource();
//					intruderMaxTurning = new Double(maxTurningTextField.getText());
				}
			});
			maxTurningTextField.setBounds(197, 145, 86, 20);
			contentPanel.add(maxTurningTextField);
			maxTurningTextField.setColumns(10);
			
			speedTextField = new JTextField();
			speedTextField.setText("1.5");
			speedTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField speedTextField = (JTextField) e.getSource();
//					intruderSpeed = new Double(speedTextField.getText());
				}
			});
			speedTextField.setBounds(197, 182, 86, 20);
			contentPanel.add(speedTextField);
			speedTextField.setColumns(10);
			
			viewingRangeTextField = new JTextField();
			viewingRangeTextField.setText("38.89");
			viewingRangeTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField viewingRangeTextField = (JTextField) e.getSource();
//					intruderViewingRange = new Double(viewingRangeTextField.getText());
				}
			});
			viewingRangeTextField.setBounds(197, 221, 86, 20);
			contentPanel.add(viewingRangeTextField);
			viewingRangeTextField.setColumns(10);
			
			viewingAngleTextField = new JTextField();
			viewingAngleTextField.setText("60");
			viewingAngleTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField viewingAngleTextField = (JTextField) e.getSource();
//					intruderViewingAngle = new Double(viewingAngleTextField.getText());
				}
			});
			viewingAngleTextField.setBounds(197, 264, 86, 20);
			contentPanel.add(viewingAngleTextField);
			viewingAngleTextField.setColumns(10);
			
			sensitivityForCollisionTextField = new JTextField();
			sensitivityForCollisionTextField.setText("0.5");
			sensitivityForCollisionTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField sensitivityForCollisionTextField = (JTextField) e.getSource();
//					intruderSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
				}
			});
			sensitivityForCollisionTextField.setBounds(197, 303, 86, 20);
			contentPanel.add(sensitivityForCollisionTextField);
			sensitivityForCollisionTextField.setColumns(10);
			
			safetyRadiusTextField = new JTextField();
			safetyRadiusTextField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					JTextField safetyRadiusTextField = (JTextField) e.getSource();
					
				}
			});
			safetyRadiusTextField.setText("1.667");
			safetyRadiusTextField.setBounds(197, 339, 86, 20);
			contentPanel.add(safetyRadiusTextField);
			safetyRadiusTextField.setColumns(10);
		}
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println(getRootPane().getParent().getName());

						if(getRootPane().getParent().getName() == "HeadonEncounter--IntruderConfig")
						{
							CONFIGURATION.headOnMaxSpeed = new Double(maxSpeedTextField.getText());
							CONFIGURATION.headOnMaxAcceleration = new Double(maxAccelerationTextField.getText());
							CONFIGURATION.headOnMaxDeceleration = new Double(maxDecelerationTextField.getText());
							CONFIGURATION.headOnMaxTurning = new Double(maxSpeedTextField.getText());
							CONFIGURATION.headOnSpeed = new Double(maxTurningTextField.getText());
							CONFIGURATION.headOnViewingRange = new Double(viewingRangeTextField.getText());
							CONFIGURATION.headOnViewingAngle = new Double(viewingAngleTextField.getText());
							CONFIGURATION.headOnSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
							CONFIGURATION.headOnSafetyRadius= new Double(safetyRadiusTextField.getText());
							System.out.println("777777777777777777777");
							
						}
						else if(getRootPane().getParent().getName() == "CrossingEncounter--IntruderConfig")
						{
							CONFIGURATION.crossingMaxSpeed = new Double(maxSpeedTextField.getText());
							CONFIGURATION.crossingMaxAcceleration = new Double(maxAccelerationTextField.getText());
							CONFIGURATION.crossingMaxDeceleration = new Double(maxDecelerationTextField.getText());
							CONFIGURATION.crossingMaxTurning = new Double(maxSpeedTextField.getText());
							CONFIGURATION.crossingSpeed = new Double(maxTurningTextField.getText());
							CONFIGURATION.crossingViewingRange = new Double(viewingRangeTextField.getText());
							CONFIGURATION.crossingViewingAngle = new Double(viewingAngleTextField.getText());
							CONFIGURATION.crossingSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
							CONFIGURATION.crossingSafetyRadius= new Double(safetyRadiusTextField.getText());
							System.out.println("88888888888888888");
						}
						else if(getRootPane().getParent().getName() == "TailApproachEncounter--IntruderConfig")
						{
							CONFIGURATION.tailApproachMaxSpeed = new Double(maxSpeedTextField.getText());
							CONFIGURATION.tailApproachMaxAcceleration = new Double(maxAccelerationTextField.getText());
							CONFIGURATION.tailApproachMaxDeceleration = new Double(maxDecelerationTextField.getText());
							CONFIGURATION.tailApproachMaxTurning = new Double(maxSpeedTextField.getText());
							CONFIGURATION.tailApproachSpeed = new Double(maxTurningTextField.getText());
							CONFIGURATION.tailApproachViewingRange = new Double(viewingRangeTextField.getText());
							CONFIGURATION.tailApproachViewingAngle = new Double(viewingAngleTextField.getText());
							CONFIGURATION.tailApproachSensitivityForCollisions = new Double(sensitivityForCollisionTextField.getText());
							CONFIGURATION.tailApproachSafetyRadius= new Double(safetyRadiusTextField.getText());
							System.out.println("99999999999999999999999");
						}
						else
						{
							
						}
						
						System.exit(0);
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public IntruderConfig(Object object, String string, boolean b) {
		// TODO Auto-generated constructor stub
	}

}
