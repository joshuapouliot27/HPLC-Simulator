package org.retentionprediction;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;
import java.awt.Dimension;
import java.lang.String;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class BackCalculationOptionsDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	public JButton jbtnOk = null;
	public JButton jbtnCancel = null;
	private JPanel jPanel = null;
	
	public boolean m_bOk = false;
	public boolean m_bFlowRateProfileFirst = true;
	public JRadioButton jrdoTemperatureProgramFirst = null;
	public JRadioButton jrdoFlowRateProfileFirst = null;
	/**
	 * @param owner
	 */
	public BackCalculationOptionsDialog(Frame owner) 
	{
		super(owner);
		initialize();
		
		jbtnOk.addActionListener(this);
		jbtnCancel.addActionListener(this);
		jrdoTemperatureProgramFirst.addActionListener(this);
		jrdoFlowRateProfileFirst.addActionListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() 
	{
		this.setSize(399, 170);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Back-Calculation Options");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(new Color(238, 238, 238));
			jContentPane.add(getJbtnOk(), null);
			jContentPane.add(getJbtnCancel(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jbtnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnOk() {
		if (jbtnOk == null) {
			jbtnOk = new JButton();
			jbtnOk.setBounds(new Rectangle(120, 100, 125, 31));
			jbtnOk.setActionCommand("OK");
			jbtnOk.setText("OK");
		}
		return jbtnOk;
	}

	/**
	 * This method initializes jbtnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnCancel() {
		if (jbtnCancel == null) {
			jbtnCancel = new JButton();
			jbtnCancel.setBounds(new Rectangle(256, 100, 124, 31));
			jbtnCancel.setText("Cancel");
		}
		return jbtnCancel;
	}

	//@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getActionCommand() == "OK")
		{
			m_bOk = true;
			this.m_bFlowRateProfileFirst = this.jrdoFlowRateProfileFirst.isSelected();
			this.setVisible(false);
			this.dispose(); 
		}
		else if (arg0.getActionCommand() == "Cancel")
		{
			this.setVisible(false);
			this.dispose(); 			
		}
		else if (arg0.getActionCommand() == "FlowRateProfileFirst")
		{
			this.jrdoFlowRateProfileFirst.setSelected(true);
			this.jrdoTemperatureProgramFirst.setSelected(false);
		}
		else if (arg0.getActionCommand() == "TemperatureProfileFirst")
		{
			this.jrdoFlowRateProfileFirst.setSelected(false);
			this.jrdoTemperatureProgramFirst.setSelected(true);
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(new Color(238, 238, 238));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "Order of optimization:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.setBounds(new Rectangle(12, 12, 369, 77));
			jPanel.add(getJrdoTemperatureProgramFirst(), null);
			jPanel.add(getJrdoFlowRateProfileFirst(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jrdoTemperatureProgramFirst	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoTemperatureProgramFirst() {
		if (jrdoTemperatureProgramFirst == null) {
			jrdoTemperatureProgramFirst = new JRadioButton();
			jrdoTemperatureProgramFirst.setBounds(new Rectangle(12, 44, 265, 21));
			jrdoTemperatureProgramFirst.setActionCommand("TemperatureProfileFirst");
			jrdoTemperatureProgramFirst.setText("Optimize the temperature profile first");
		}
		return jrdoTemperatureProgramFirst;
	}

	/**
	 * This method initializes jrdoFlowRateProfileFirst	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoFlowRateProfileFirst() {
		if (jrdoFlowRateProfileFirst == null) {
			jrdoFlowRateProfileFirst = new JRadioButton();
			jrdoFlowRateProfileFirst.setBounds(new Rectangle(12, 24, 309, 17));
			jrdoFlowRateProfileFirst.setSelected(true);
			jrdoFlowRateProfileFirst.setActionCommand("FlowRateProfileFirst");
			jrdoFlowRateProfileFirst.setText("Optimize the flow rate profile first (default)");
		}
		return jrdoFlowRateProfileFirst;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
