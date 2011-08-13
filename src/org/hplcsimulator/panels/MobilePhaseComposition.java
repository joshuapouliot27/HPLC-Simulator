package org.hplcsimulator.panels;

import org.jdesktop.swingx.JXPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Point;
import javax.swing.JComboBox;

import org.hplcsimulator.*;
import org.hplcsimulator.Globals;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

public class MobilePhaseComposition extends JXPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JComboBox jcboSolventB = null;
	public JLabel jlblSolventB = null;
	public JLabel jlblSolventA = null;
	public JComboBox jcboSolventA = null;
	public JRadioButton jrdoIsocraticElution = null;
	public JRadioButton jrdoGradientElution = null;

	/**
	 * This method initializes 
	 * 
	 */
	public MobilePhaseComposition() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        jlblSolventA = new JLabel();
        jlblSolventA.setBounds(new Rectangle(8, 8, 109, 16));
        jlblSolventA.setText("Solvent A:");
        jlblSolventB = new JLabel();
        jlblSolventB.setText("Solvent B:");
        jlblSolventB.setSize(new Dimension(109, 16));
        jlblSolventB.setLocation(new Point(8, 32));
        this.setLayout(null);
        this.setSize(new Dimension(254, 102));
        this.setBackground(Color.white);
        this.add(getJcboSolventB(), null);
        this.add(jlblSolventB, null);
        this.add(jlblSolventA, null);
        this.add(getJcboSolventA(), null);
        this.add(getJrdoIsocraticElution(), null);
        this.add(getJrdoGradientElution(), null);
			
	}

	/**
	 * This method initializes jcboSolventA	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJcboSolventA() {
		if (jcboSolventA == null) {
			jcboSolventA = new JComboBox(Globals.SolventAArray);
			jcboSolventA.setBounds(new Rectangle(120, 8, 121, 21));
			jcboSolventA.setEnabled(true);
			jcboSolventA.setActionCommand("SolventAComboBoxChanged");
		}
		return jcboSolventA;
	}

	/**
	 * This method initializes jcboSolventB	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJcboSolventB() {
		if (jcboSolventB == null) {
			jcboSolventB = new JComboBox(Globals.SolventBArray);
			jcboSolventB.setBounds(new Rectangle(120, 32, 121, 21));
			jcboSolventB.setActionCommand("SolventBComboBoxChanged");
		}
		return jcboSolventB;
	}

	/**
	 * This method initializes jrdoIsocraticElution	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoIsocraticElution() {
		if (jrdoIsocraticElution == null) {
			jrdoIsocraticElution = new JRadioButton();
			jrdoIsocraticElution.setBounds(new Rectangle(8, 60, 233, 17));
			jrdoIsocraticElution.setText("Isocratic elution mode");
			jrdoIsocraticElution.setSelected(true);
			jrdoIsocraticElution.setBackground(Color.white);
		}
		return jrdoIsocraticElution;
	}

	/**
	 * This method initializes jrdoGradientElution	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoGradientElution() {
		if (jrdoGradientElution == null) {
			jrdoGradientElution = new JRadioButton();
			jrdoGradientElution.setBounds(new Rectangle(8, 80, 233, 17));
			jrdoGradientElution.setSelected(false);
			jrdoGradientElution.setText("Gradient elution mode");
			jrdoGradientElution.setActionCommand("Gradient elution mode");
			jrdoGradientElution.setRolloverEnabled(true);
			jrdoGradientElution.setBackground(Color.white);
		}
		return jrdoGradientElution;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
