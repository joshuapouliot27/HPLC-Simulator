package org.hplcsimulator;

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

public class ChemicalDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JComboBox jcboCompound = null;
	private JLabel jLabel1 = null;
	private JTextField jtxtConcentration = null;
	private JLabel jLabel2 = null;
	private JButton jbtnOk = null;
	private JButton jbtnCancel = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel41 = null;
	private JLabel jLabel51 = null;
	private JLabel jlblSlope1 = null;
	private JLabel jlblSlope2 = null;
	private JLabel jlblIntercept1 = null;
	private JLabel jlblIntercept2 = null;
	private JPanel jPanel = null;
	
	public Vector<Integer> m_vectCompoundsUsed = new Vector<Integer>();  //  @jve:decl-index=0:
	public boolean m_bOk = false;
	public int m_iCompound = 0;
	public String m_strCompoundName = "";  //  @jve:decl-index=0:
	public double m_dConcentration = 0;
	public double m_dLogkwvsTSlope = 0;
	public double m_dLogkwvsTIntercept = 0;
	public double m_dSvsTSlope = 0;
	public double m_dSvsTIntercept = 0;
	public double m_dMolarVolume = 0;
	private int m_iOrganicModifier = 0;
	
	/**
	 * @param owner
	 */
	public ChemicalDialog(Frame owner, boolean bEditDialog, int iOrganicModifier) 
	{
		super(owner);
		initialize();
		
		if (bEditDialog)
			this.setTitle("Edit Compound");
		
		m_iOrganicModifier = iOrganicModifier;
		jcboCompound.addActionListener(this);
		jbtnOk.addActionListener(this);
		jbtnCancel.addActionListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(299, 252);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Add New Compound");
		this.setCompoundVariables();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jlblIntercept2 = new JLabel();
			jlblIntercept2.setText("-4.492");
			jlblIntercept2.setBounds(new Rectangle(204, 92, 53, 16));
			jlblIntercept2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblIntercept1 = new JLabel();
			jlblIntercept1.setText("1.775");
			jlblIntercept1.setBounds(new Rectangle(80, 92, 53, 16));
			jlblIntercept1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblSlope2 = new JLabel();
			jlblSlope2.setText("0.0049");
			jlblSlope2.setBounds(new Rectangle(204, 72, 53, 16));
			jlblSlope2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblSlope1 = new JLabel();
			jlblSlope1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblSlope1.setBounds(new Rectangle(80, 72, 53, 16));
			jlblSlope1.setText("-0.0092");
			jLabel51 = new JLabel();
			jLabel51.setText("Intercept:");
			jLabel51.setBounds(new Rectangle(140, 92, 61, 16));
			jLabel41 = new JLabel();
			jLabel41.setText("Slope:");
			jLabel41.setBounds(new Rectangle(140, 72, 35, 16));
			jLabel6 = new JLabel();
			jLabel6.setText("S vs T");
			jLabel6.setBounds(new Rectangle(140, 52, 38, 16));
			jLabel5 = new JLabel();
			jLabel5.setText("Intercept:");
			jLabel5.setBounds(new Rectangle(16, 92, 60, 16));
			jLabel4 = new JLabel();
			jLabel4.setText("Slope:");
			jLabel4.setBounds(new Rectangle(16, 72, 38, 16));
			jLabel3 = new JLabel();
			jLabel3.setText("ln(k'w) vs T");
			jLabel3.setBounds(new Rectangle(16, 52, 94, 16));
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(212, 144, 38, 16));
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel2.setText("\u03BCM");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(28, 144, 93, 16));
			jLabel1.setText("Concentration:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(new Color(238, 238, 238));
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJtxtConcentration(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJbtnOk(), null);
			jContentPane.add(getJbtnCancel(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jcboCompound	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJcboCompound() 
	{
		if (jcboCompound == null) 
		{
			jcboCompound = new JComboBox(Globals.CompoundNameArray);
			jcboCompound.setFont(new Font("Dialog", Font.PLAIN, 12));
			jcboCompound.setBackground(Color.white);
			jcboCompound.setBounds(new Rectangle(16, 24, 229, 21));
		}
		
		return jcboCompound;
	}

	/**
	 * This method initializes jtxtConcentration	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtConcentration() {
		if (jtxtConcentration == null) {
			jtxtConcentration = new JTextField();
			jtxtConcentration.setText("1");
			jtxtConcentration.setBounds(new Rectangle(156, 142, 51, 20));
		}
		return jtxtConcentration;
	}

	/**
	 * This method initializes jbtnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnOk() {
		if (jbtnOk == null) {
			jbtnOk = new JButton();
			jbtnOk.setBounds(new Rectangle(12, 176, 125, 31));
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
			jbtnCancel.setBounds(new Rectangle(156, 176, 124, 31));
			jbtnCancel.setText("Cancel");
		}
		return jbtnCancel;
	}

	public void setSelectedCompound(int iCompoundIndex)
	{
		jcboCompound.setSelectedIndex(iCompoundIndex);
	}
	
	public void setCompoundConcentration(double dConcentration)
	{
		this.m_dConcentration = dConcentration;
		this.jtxtConcentration.setText(Float.toString((float)dConcentration));
	}
	
	private void setCompoundVariables()
	{
		this.m_iCompound = this.jcboCompound.getSelectedIndex();
		
		Compound thisCompound = new Compound();
		thisCompound.loadCompoundInfo(m_iCompound, m_iOrganicModifier);
		
		m_strCompoundName = thisCompound.strCompoundName;
		m_dLogkwvsTSlope = thisCompound.dLogkwvsTSlope;
		m_dLogkwvsTIntercept = thisCompound.dLogkwvsTIntercept;
		m_dSvsTSlope = thisCompound.dSvsTSlope;
		m_dSvsTIntercept = thisCompound.dSvsTIntercept;
		m_dMolarVolume = thisCompound.dMolarVolume;
		m_dConcentration = thisCompound.dConcentration;

		NumberFormat formatter = new DecimalFormat("#0.0000");

		jlblSlope1.setText(formatter.format(m_dLogkwvsTSlope));
		jlblIntercept1.setText(formatter.format(m_dLogkwvsTIntercept));
		jlblSlope2.setText(formatter.format(m_dSvsTSlope));
		jlblIntercept2.setText(formatter.format(m_dSvsTIntercept));
		jtxtConcentration.setText(formatter.format(m_dConcentration));
	}
	
	//@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getActionCommand() == "comboBoxChanged")
		{
			setCompoundVariables();
		}
		else if (arg0.getActionCommand() == "OK")
		{
			for (int i = 0; i < this.m_vectCompoundsUsed.size(); i++)
			{
				if (m_iCompound == m_vectCompoundsUsed.get(i).intValue())
				{
					
					JOptionPane.showMessageDialog(this,
							"That compound was already added. Please select a different compound.",
							"Compound already added",
							JOptionPane.INFORMATION_MESSAGE,
							null);
					return;
				}
			}
			m_bOk = true;
			this.m_dConcentration = Double.valueOf(this.jtxtConcentration.getText());
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
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(new Color(238, 238, 238));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "Compound:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.setBounds(new Rectangle(12, 12, 269, 121));
			jPanel.add(getJcboCompound(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(jlblIntercept1, null);
			jPanel.add(jlblIntercept2, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel51, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel41, null);
			jPanel.add(jlblSlope2, null);
			jPanel.add(jlblSlope1, null);
		}
		return jPanel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
