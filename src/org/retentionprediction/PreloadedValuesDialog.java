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

public class PreloadedValuesDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jbtnOk = null;
	private JButton jbtnCancel = null;
	private JPanel jPanel = null;
	
	public boolean m_bOk = false;
	private JLabel jLabel1 = null;
	private JComboBox jcboTemperatureProgram = null;
	public int m_iInstrument = 0;
	public int m_iCondition = 0;
	
	/**
	 * @param owner
	 */
	public PreloadedValuesDialog(Frame owner) 
	{
		super(owner);
		initialize();
		
		jbtnOk.addActionListener(this);
		jbtnCancel.addActionListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() 
	{
		this.setSize(691, 179);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Load Predefined Values");
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
			jbtnOk.setBounds(new Rectangle(412, 112, 125, 31));
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
			jbtnCancel.setBounds(new Rectangle(548, 112, 124, 31));
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
			this.m_iCondition = this.jcboTemperatureProgram.getSelectedIndex();
			this.setVisible(false);
			this.dispose(); 
		}
		else if (arg0.getActionCommand() == "Cancel")
		{
			this.setVisible(false);
			this.dispose(); 			
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(12, 28, 172, 16));
			jLabel1.setText("Select an experiment:");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(new Color(238, 238, 238));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "Experimental conditions:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.setBounds(new Rectangle(12, 12, 661, 93));
			jPanel.add(jLabel1, null);
			jPanel.add(getJcboTemperatureProgram(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jcboTemperatureProgram	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJcboTemperatureProgram() {
		if (jcboTemperatureProgram == null) {
			jcboTemperatureProgram = new JComboBox(Globals.strPredefinedValues);
			jcboTemperatureProgram.setBounds(new Rectangle(12, 48, 637, 21));
			jcboTemperatureProgram.setFont(new Font("Dialog", Font.PLAIN, 12));
			jcboTemperatureProgram.setBackground(Color.white);
		}
		return jcboTemperatureProgram;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
