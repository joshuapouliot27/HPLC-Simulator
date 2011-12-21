package org.hplcsimulator.panels;

import org.jdesktop.swingx.JXPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class ColumnProperties extends JXPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JLabel jlblColumnLength = null;
	public JLabel jlblColumnDiameter = null;
	public JLabel jlblParticleSize = null;
	public JLabel jlblInterparticlePorosity = null;
	public JLabel jlblVoidVolume2 = null;
	public JLabel jlblVoidTime2 = null;
	public JLabel jlblVanDeemter = null;
	public JLabel jlblATerm = null;
	public JLabel jlblBTerm = null;
	public JLabel jlblCTerm = null;
	public JLabel jlblReducedPlateHeight2 = null;
	public JLabel jlblReducedPlateHeight = null;
	public JTextField jtxtColumnLength = null;
	public JTextField jtxtColumnDiameter = null;
	public JTextField jtxtParticleSize = null;
	public JTextField jtxtInterparticlePorosity = null;
	public JLabel jlblVoidVolume = null;
	public JLabel jlblVoidTime = null;
	public JTextField jtxtATerm = null;
	public JTextField jtxtBTerm = null;
	public JTextField jtxtCTerm = null;
	public JLabel jlblColumnLength2 = null;
	public JLabel jlblColumnDiameter2 = null;
	public JLabel jlblParticleSize2 = null;
	public JLabel jlblVoidVolume3 = null;
	public JLabel jlblVoidTime3 = null;
	public JLabel jlblStationaryPhase = null;
	public JComboBox jcboStationaryPhase = null;
	public JLabel jlblIntraparticlePorosity = null;
	public JTextField jtxtIntraparticlePorosity = null;
	public JLabel jlblTotalPorosity = null;
	public JLabel jlblTotalPorosityOut = null;

	/**
	 * This method initializes 
	 * 
	 */
	public ColumnProperties() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        jlblTotalPorosityOut = new JLabel();
        jlblTotalPorosityOut.setBounds(new Rectangle(136, 156, 57, 17));
        jlblTotalPorosityOut.setText("0.64");
        jlblTotalPorosityOut.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblTotalPorosity = new JLabel();
        jlblTotalPorosity.setBounds(new Rectangle(8, 156, 125, 16));
        jlblTotalPorosity.setText("Total porosity:");
        jlblIntraparticlePorosity = new JLabel();
        jlblIntraparticlePorosity.setBounds(new Rectangle(8, 136, 125, 16));
        jlblIntraparticlePorosity.setText("Intraparticle porosity:");
        jlblStationaryPhase = new JLabel();
        jlblStationaryPhase.setText("Stationary phase:");
        jlblStationaryPhase.setSize(new Dimension(125, 16));
        jlblStationaryPhase.setLocation(new Point(8, 8));
        jlblVoidTime3 = new JLabel();
        jlblVoidTime3.setText("s");
        jlblVoidTime3.setLocation(new Point(196, 196));
        jlblVoidTime3.setSize(new Dimension(45, 16));
        jlblVoidTime3.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblVoidVolume3 = new JLabel();
        jlblVoidVolume3.setText("mL");
        jlblVoidVolume3.setLocation(new Point(196, 176));
        jlblVoidVolume3.setSize(new Dimension(45, 16));
        jlblVoidVolume3.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblParticleSize2 = new JLabel();
        jlblParticleSize2.setText("\u00b5m");
        jlblParticleSize2.setLocation(new Point(196, 96));
        jlblParticleSize2.setSize(new Dimension(45, 16));
        jlblParticleSize2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblColumnDiameter2 = new JLabel();
        jlblColumnDiameter2.setText("mm");
        jlblColumnDiameter2.setLocation(new Point(196, 76));
        jlblColumnDiameter2.setSize(new Dimension(45, 16));
        jlblColumnDiameter2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblColumnLength2 = new JLabel();
        jlblColumnLength2.setText("mm");
        jlblColumnLength2.setLocation(new Point(196, 56));
        jlblColumnLength2.setSize(new Dimension(45, 16));
        jlblColumnLength2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblVoidTime = new JLabel();
        jlblVoidTime.setText("29.987");
        jlblVoidTime.setLocation(new Point(136, 196));
        jlblVoidTime.setSize(new Dimension(57, 16));
        jlblVoidTime.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblVoidVolume = new JLabel();
        jlblVoidVolume.setText("0.9987");
        jlblVoidVolume.setLocation(new Point(136, 176));
        jlblVoidVolume.setSize(new Dimension(57, 16));
        jlblVoidVolume.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblReducedPlateHeight = new JLabel();
        jlblReducedPlateHeight.setText("0.9987");
        jlblReducedPlateHeight.setLocation(new Point(136, 296));
        jlblReducedPlateHeight.setSize(new Dimension(57, 16));
        jlblReducedPlateHeight.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblReducedPlateHeight2 = new JLabel();
        jlblReducedPlateHeight2.setText("Reduced plate height:");
        jlblReducedPlateHeight2.setSize(new Dimension(125, 16));
        jlblReducedPlateHeight2.setLocation(new Point(8, 296));
        jlblCTerm = new JLabel();
        jlblCTerm.setBounds(new Rectangle(96, 276, 37, 16));
        jlblCTerm.setText("C:");
        jlblBTerm = new JLabel();
        jlblBTerm.setBounds(new Rectangle(96, 256, 37, 16));
        jlblBTerm.setText("B:");
        jlblATerm = new JLabel();
        jlblATerm.setBounds(new Rectangle(96, 236, 37, 16));
        jlblATerm.setText("A:");
        jlblVanDeemter = new JLabel();
        jlblVanDeemter.setBounds(new Rectangle(8, 216, 181, 16));
        jlblVanDeemter.setText("Reduced Van Deemter terms:");
        jlblVanDeemter.setName("");
        jlblVoidTime2 = new JLabel();
        jlblVoidTime2.setText("Void time:");
        jlblVoidTime2.setSize(new Dimension(125, 16));
        jlblVoidTime2.setLocation(new Point(8, 196));
        jlblVoidVolume2 = new JLabel();
        jlblVoidVolume2.setText("Void volume:");
        jlblVoidVolume2.setSize(new Dimension(125, 16));
        jlblVoidVolume2.setLocation(new Point(8, 176));
        jlblInterparticlePorosity = new JLabel();
        jlblInterparticlePorosity.setText("Interparticle porosity:");
        jlblInterparticlePorosity.setSize(new Dimension(125, 16));
        jlblInterparticlePorosity.setLocation(new Point(8, 116));
        jlblParticleSize = new JLabel();
        jlblParticleSize.setText("Particle size:");
        jlblParticleSize.setSize(new Dimension(125, 16));
        jlblParticleSize.setLocation(new Point(8, 96));
        jlblColumnDiameter = new JLabel();
        jlblColumnDiameter.setText("Inner diameter:");
        jlblColumnDiameter.setSize(new Dimension(125, 16));
        jlblColumnDiameter.setLocation(new Point(8, 76));
        jlblColumnLength = new JLabel();
        jlblColumnLength.setText("Length:");
        jlblColumnLength.setSize(new Dimension(125, 16));
        jlblColumnLength.setLocation(new Point(8, 56));
        this.setLayout(null);
        this.setSize(new Dimension(254, 317));
        this.setBackground(Color.white);
        this.add(jlblColumnLength, null);
        this.add(jlblColumnDiameter, null);
        this.add(jlblParticleSize, null);
        this.add(jlblInterparticlePorosity, null);
        this.add(jlblVoidVolume2, null);
        this.add(jlblVoidTime2, null);
        this.add(jlblVanDeemter, null);
        this.add(jlblATerm, null);
        this.add(jlblBTerm, null);
        this.add(jlblCTerm, null);
        this.add(jlblReducedPlateHeight2, null);
        this.add(jlblReducedPlateHeight, null);
        this.add(getJtxtColumnLength(), null);
        this.add(getJtxtColumnDiameter(), null);
        this.add(getJtxtParticleSize(), null);
        this.add(getJtxtVoidFraction(), null);
        this.add(jlblVoidVolume, null);
        this.add(jlblVoidTime, null);
        this.add(getJtxtATerm(), null);
        this.add(getJtxtBTerm(), null);
        this.add(getJtxtCTerm(), null);
        this.add(jlblColumnLength2, null);
        this.add(jlblColumnDiameter2, null);
        this.add(jlblParticleSize2, null);
        this.add(jlblVoidVolume3, null);
        this.add(jlblVoidTime3, null);
        this.add(jlblStationaryPhase, null);
        this.add(getJcboStationaryPhase(), null);
        this.add(jlblIntraparticlePorosity, null);
        this.add(getJtxtIntraparticlePorosity(), null);
        this.add(jlblTotalPorosity, null);
        this.add(jlblTotalPorosityOut, null);
			
	}

	/**
	 * This method initializes jtxtColumnLength	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtColumnLength() {
		if (jtxtColumnLength == null) {
			jtxtColumnLength = new JTextField();
			jtxtColumnLength.setText("100");
			jtxtColumnLength.setSize(new Dimension(57, 20));
			jtxtColumnLength.setLocation(new Point(136, 56));
		}
		return jtxtColumnLength;
	}

	/**
	 * This method initializes jtxtColumnDiameter	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtColumnDiameter() {
		if (jtxtColumnDiameter == null) {
			jtxtColumnDiameter = new JTextField();
			jtxtColumnDiameter.setText("4.6");
			jtxtColumnDiameter.setSize(new Dimension(57, 20));
			jtxtColumnDiameter.setLocation(new Point(136, 76));
		}
		return jtxtColumnDiameter;
	}

	/**
	 * This method initializes jtxtParticleSize	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtParticleSize() {
		if (jtxtParticleSize == null) {
			jtxtParticleSize = new JTextField();
			jtxtParticleSize.setText("3");
			jtxtParticleSize.setSize(new Dimension(57, 20));
			jtxtParticleSize.setLocation(new Point(136, 96));
		}
		return jtxtParticleSize;
	}

	/**
	 * This method initializes jtxtVoidFraction	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtVoidFraction() {
		if (jtxtInterparticlePorosity == null) {
			jtxtInterparticlePorosity = new JTextField();
			jtxtInterparticlePorosity.setText("0.4");
			jtxtInterparticlePorosity.setSize(new Dimension(57, 20));
			jtxtInterparticlePorosity.setLocation(new Point(136, 116));
		}
		return jtxtInterparticlePorosity;
	}

	/**
	 * This method initializes jtxtATerm	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtATerm() {
		if (jtxtATerm == null) {
			jtxtATerm = new JTextField();
			jtxtATerm.setText("1");
			jtxtATerm.setSize(new Dimension(57, 20));
			jtxtATerm.setLocation(new Point(136, 232));
		}
		return jtxtATerm;
	}

	/**
	 * This method initializes jtxtBTerm	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtBTerm() {
		if (jtxtBTerm == null) {
			jtxtBTerm = new JTextField();
			jtxtBTerm.setText("5");
			jtxtBTerm.setSize(new Dimension(57, 20));
			jtxtBTerm.setLocation(new Point(136, 252));
		}
		return jtxtBTerm;
	}

	/**
	 * This method initializes jtxtCTerm	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtCTerm() {
		if (jtxtCTerm == null) {
			jtxtCTerm = new JTextField();
			jtxtCTerm.setText("0.05");
			jtxtCTerm.setSize(new Dimension(57, 20));
			jtxtCTerm.setLocation(new Point(136, 272));
		}
		return jtxtCTerm;
	}

	/**
	 * This method initializes jcboStationaryPhase	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJcboStationaryPhase() {
		if (jcboStationaryPhase == null) {
			jcboStationaryPhase = new JComboBox();
			jcboStationaryPhase.addItem(new String("Agilent Zorbax SB-C8"));
			jcboStationaryPhase.setSelectedIndex(0);
			jcboStationaryPhase.setLocation(new Point(8, 26));
			jcboStationaryPhase.setSize(new Dimension(233, 27));
		}
		return jcboStationaryPhase;
	}

	/**
	 * This method initializes jtxtIntraparticlePorosity	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtIntraparticlePorosity() {
		if (jtxtIntraparticlePorosity == null) {
			jtxtIntraparticlePorosity = new JTextField();
			jtxtIntraparticlePorosity.setBounds(new Rectangle(136, 136, 57, 20));
			jtxtIntraparticlePorosity.setText("0.4");
		}
		return jtxtIntraparticlePorosity;
	}

}  //  @jve:decl-index=0:visual-constraint="-34,10"
