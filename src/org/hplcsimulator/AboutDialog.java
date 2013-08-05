package org.hplcsimulator;

import javax.swing.JPanel;
import java.awt.Frame;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;
import java.awt.Dimension;
import java.lang.String;
import javax.swing.JCheckBox;
import java.awt.Point;
import java.awt.GridBagLayout;
import javax.swing.SwingConstants;

public class AboutDialog extends JDialog implements ActionListener, KeyListener, FocusListener
{
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jbtnOk = null;
	
	private JLabel jLabel = null;

	/**
	 * @param owner
	 */
	public AboutDialog(Frame owner) 
	{
		super(owner);
		initialize();
		
		jbtnOk.addActionListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() 
	{
		this.setSize(284, 147);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("About HPLC Simulator");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(76, 32, 121, 16));
			jLabel.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel.setText("Version 1.1.3");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(new Color(238, 238, 238));
			jContentPane.add(getJbtnOk(), null);
			jContentPane.add(jLabel, null);
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
			jbtnOk.setBounds(new Rectangle(76, 80, 125, 31));
			jbtnOk.setActionCommand("OK");
			jbtnOk.setText("OK");
		}
		return jbtnOk;
	}

	//@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getActionCommand() == "OK")
		{
			this.setVisible(false);
			this.dispose(); 
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		/*if (!((Character.isDigit(e.getKeyChar()) ||
				(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ||
				(e.getKeyChar() == KeyEvent.VK_DELETE) ||
				(e.getKeyChar() == KeyEvent.VK_PERIOD))))
		{
	        e.consume();
		}*/
		
		if (e.getKeyChar() == KeyEvent.VK_ENTER)
		{
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) 
	{
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
