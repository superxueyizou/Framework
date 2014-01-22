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


public class SAAConfigurator extends JFrame 
{	

	private JPanel contentPane;
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
	public SAAConfigurator() 
	{
		super("SAA Configurator");
		this.setBounds(1500+80, 404, 340,742);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 12, 326, 818);
				
		JPanel selfConfigPanel = new Self();
		tabbedPane.addTab("Self", null, selfConfigPanel, null);
		selfConfigPanel.setLayout(null);
					
		JPanel headOnPanel = new HeadOn();
		tabbedPane.addTab("HeadOn", null, headOnPanel, null);
		headOnPanel.setLayout(null);
		
		JPanel crossingPanel = new Crossing();
		tabbedPane.addTab("Crossing", null, crossingPanel, null);
		crossingPanel.setLayout(null);
		
		JPanel tailApproachPanel = new TailApproach();
		tabbedPane.addTab("TailApproach", null, tailApproachPanel, null);
		tailApproachPanel.setLayout(null);
		
		getContentPane().add(tabbedPane);
		contentPane.add(tabbedPane);

	}
}

