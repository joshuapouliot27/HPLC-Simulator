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

public class IsocraticOptions extends JXPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JLabel jlblSolventBFraction = null;
	public JSlider jsliderSolventBFraction = null;
	public JTextField jtxtSolventBFraction = null;
	/**
	 * This method initializes 
	 * 
	 */
	public IsocraticOptions() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        jlblSolventBFraction = new JLabel();
        jlblSolventBFraction.setBounds(new Rectangle(8, 0, 189, 16));
        jlblSolventBFraction.setText("Solvent B fraction (% v/v):");
        this.setLayout(null);
        this.setSize(new Dimension(254, 70));
        this.setBackground(Color.white);
        this.add(jlblSolventBFraction, null);
        this.add(getJsliderSolventBFraction(), null);
        this.add(getJtxtSolventBFraction(), null);
			
	}

	/**
	 * This method initializes jsliderSolventBFraction	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJsliderSolventBFraction() {
		if (jsliderSolventBFraction == null) {
			jsliderSolventBFraction = new JSlider();
			jsliderSolventBFraction.setBounds(new Rectangle(0, 16, 200, 43));
			jsliderSolventBFraction.setFont(new Font("Dialog", Font.PLAIN, 12));
			jsliderSolventBFraction.setName("Solvent B Slider");
			jsliderSolventBFraction.setMajorTickSpacing(25);
			jsliderSolventBFraction.setMaximum(100);
			jsliderSolventBFraction.setMinorTickSpacing(5);
			jsliderSolventBFraction.setPaintLabels(true);
			jsliderSolventBFraction.setPaintTicks(true);
			jsliderSolventBFraction.setPaintTrack(true);
			jsliderSolventBFraction.setValue(50);
			jsliderSolventBFraction.setBackground(Color.white);
		}
		return jsliderSolventBFraction;
	}

	/**
	 * This method initializes jtxtSolventBFraction	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtSolventBFraction() {
		if (jtxtSolventBFraction == null) {
			jtxtSolventBFraction = new JTextField();
			jtxtSolventBFraction.setText("50");
			jtxtSolventBFraction.setLocation(new Point(200, 12));
			jtxtSolventBFraction.setSize(new Dimension(41, 20));
		}
		return jtxtSolventBFraction;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
