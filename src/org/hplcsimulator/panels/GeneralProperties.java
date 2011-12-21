package org.hplcsimulator.panels;

import org.jdesktop.swingx.JXPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JCheckBox;
import java.awt.Point;
import java.awt.Font;
import javax.swing.JTextField;

public class GeneralProperties extends JXPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JLabel jlblEluentViscosity2 = null;
	public JLabel jlblDiffusionCoefficient2 = null;
	public JLabel jlblTimeConstant = null;
	public JLabel jlblSignalOffset = null;
	public JLabel jlblNoise = null;
	public JCheckBox jchkAutoTimeRange = null;
	public JLabel jlblInitialTime = null;
	public JLabel jlblFinalTime = null;
	public JLabel jlblNumPoints = null;
	public JLabel jlblEluentViscosity = null;
	public JLabel jlblDiffusionCoefficient = null;
	public JTextField jtxtTimeConstant = null;
	public JTextField jtxtSignalOffset = null;
	public JTextField jtxtNoise = null;
	public JTextField jtxtInitialTime = null;
	public JTextField jtxtFinalTime = null;
	public JTextField jtxtNumPoints = null;
	public JLabel jlblEluentViscosity3 = null;
	public JLabel jlblDiffusionCoefficient3 = null;
	public JLabel jlblTimeConstant2 = null;
	public JLabel jlblSignalOffset2 = null;
	public JLabel jlblNoise2 = null;
	public JLabel jlblInitialTime2 = null;
	public JLabel jlblFinalTime2 = null;

	/**
	 * This method initializes 
	 * 
	 */
	public GeneralProperties() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        jlblFinalTime2 = new JLabel();
        jlblFinalTime2.setText("s");
        jlblFinalTime2.setLocation(new Point(196, 148));
        jlblFinalTime2.setSize(new Dimension(45, 16));
        jlblFinalTime2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblInitialTime2 = new JLabel();
        jlblInitialTime2.setText("s");
        jlblInitialTime2.setLocation(new Point(196, 128));
        jlblInitialTime2.setSize(new Dimension(45, 16));
        jlblInitialTime2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblNoise2 = new JLabel();
        jlblNoise2.setText("");
        jlblNoise2.setLocation(new Point(196, 88));
        jlblNoise2.setSize(new Dimension(45, 16));
        jlblNoise2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblSignalOffset2 = new JLabel();
        jlblSignalOffset2.setText("munits");
        jlblSignalOffset2.setLocation(new Point(196, 68));
        jlblSignalOffset2.setSize(new Dimension(45, 16));
        jlblSignalOffset2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblTimeConstant2 = new JLabel();
        jlblTimeConstant2.setText("s");
        jlblTimeConstant2.setLocation(new Point(196, 48));
        jlblTimeConstant2.setSize(new Dimension(45, 16));
        jlblTimeConstant2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDiffusionCoefficient3 = new JLabel();
        jlblDiffusionCoefficient3.setText("cm²/s");
        jlblDiffusionCoefficient3.setLocation(new Point(196, 28));
        jlblDiffusionCoefficient3.setSize(new Dimension(45, 16));
        jlblDiffusionCoefficient3.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblEluentViscosity3 = new JLabel();
        jlblEluentViscosity3.setText("cP");
        jlblEluentViscosity3.setLocation(new Point(196, 8));
        jlblEluentViscosity3.setSize(new Dimension(45, 16));
        jlblEluentViscosity3.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDiffusionCoefficient = new JLabel();
        jlblDiffusionCoefficient.setText("0.00001");
        jlblDiffusionCoefficient.setLocation(new Point(136, 28));
        jlblDiffusionCoefficient.setSize(new Dimension(57, 16));
        jlblDiffusionCoefficient.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblEluentViscosity = new JLabel();
        jlblEluentViscosity.setText("0.9987");
        jlblEluentViscosity.setBounds(new Rectangle(136, 8, 57, 16));
        jlblEluentViscosity.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblNumPoints = new JLabel();
        jlblNumPoints.setText("Plot points:");
        jlblNumPoints.setSize(new Dimension(125, 16));
        jlblNumPoints.setLocation(new Point(8, 168));
        jlblFinalTime = new JLabel();
        jlblFinalTime.setText("Final time:");
        jlblFinalTime.setSize(new Dimension(125, 16));
        jlblFinalTime.setLocation(new Point(8, 148));
        jlblInitialTime = new JLabel();
        jlblInitialTime.setText("Initial time:");
        jlblInitialTime.setSize(new Dimension(125, 16));
        jlblInitialTime.setLocation(new Point(8, 128));
        jlblNoise = new JLabel();
        jlblNoise.setText("Noise:");
        jlblNoise.setSize(new Dimension(125, 16));
        jlblNoise.setLocation(new Point(8, 88));
        jlblSignalOffset = new JLabel();
        jlblSignalOffset.setText("Signal offset:");
        jlblSignalOffset.setSize(new Dimension(125, 16));
        jlblSignalOffset.setLocation(new Point(8, 68));
        jlblTimeConstant = new JLabel();
        jlblTimeConstant.setText("Time constant:");
        jlblTimeConstant.setSize(new Dimension(125, 16));
        jlblTimeConstant.setLocation(new Point(8, 48));
        jlblDiffusionCoefficient2 = new JLabel();
        jlblDiffusionCoefficient2.setText("Avg. diffusion coeff.:");
        jlblDiffusionCoefficient2.setSize(new Dimension(125, 16));
        jlblDiffusionCoefficient2.setLocation(new Point(8, 28));
        jlblEluentViscosity2 = new JLabel();
        jlblEluentViscosity2.setText("Eluent viscosity:");
        jlblEluentViscosity2.setSize(new Dimension(125, 16));
        jlblEluentViscosity2.setLocation(new Point(8, 8));
        this.setLayout(null);
        this.setSize(new Dimension(254, 190));
        this.setBackground(Color.white);
        this.add(jlblEluentViscosity2, null);
        this.add(jlblDiffusionCoefficient2, null);
        this.add(jlblTimeConstant, null);
        this.add(jlblSignalOffset, null);
        this.add(jlblNoise, null);
        this.add(jlblInitialTime, null);
        this.add(jlblFinalTime, null);
        this.add(jlblNumPoints, null);
        this.add(jlblEluentViscosity, null);
        this.add(jlblDiffusionCoefficient, null);
        this.add(getJtxtTimeConstant(), null);
        this.add(getJtxtSignalOffset(), null);
        this.add(getJtxtNoise(), null);
        this.add(getJtxtInitialTime(), null);
        this.add(getJtxtFinalTime(), null);
        this.add(getJtxtNumPoints(), null);
        this.add(jlblEluentViscosity3, null);
        this.add(jlblDiffusionCoefficient3, null);
        this.add(jlblTimeConstant2, null);
        this.add(jlblSignalOffset2, null);
        this.add(jlblNoise2, null);
        this.add(jlblInitialTime2, null);
        this.add(jlblFinalTime2, null);
        this.add(getJchkAutoTimeRange(), null);
			
	}

	/**
	 * This method initializes jchkAutoTimeRange	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJchkAutoTimeRange() {
		if (jchkAutoTimeRange == null) {
			jchkAutoTimeRange = new JCheckBox();
			jchkAutoTimeRange.setBounds(new Rectangle(8, 104, 243, 24));
			jchkAutoTimeRange.setName("jchkAutoTimeRange");
			jchkAutoTimeRange.setSelected(true);
			jchkAutoTimeRange.setText("Automatically determine time span");
			jchkAutoTimeRange.setBackground(Color.white);
		}
		return jchkAutoTimeRange;
	}

	/**
	 * This method initializes jtxtTimeConstant	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtTimeConstant() {
		if (jtxtTimeConstant == null) {
			jtxtTimeConstant = new JTextField();
			jtxtTimeConstant.setText("0.1");
			jtxtTimeConstant.setSize(new Dimension(57, 20));
			jtxtTimeConstant.setLocation(new Point(136, 48));
		}
		return jtxtTimeConstant;
	}

	/**
	 * This method initializes jtxtSignalOffset	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtSignalOffset() {
		if (jtxtSignalOffset == null) {
			jtxtSignalOffset = new JTextField();
			jtxtSignalOffset.setText("0");
			jtxtSignalOffset.setSize(new Dimension(57, 20));
			jtxtSignalOffset.setLocation(new Point(136, 68));
		}
		return jtxtSignalOffset;
	}

	/**
	 * This method initializes jtxtNoise	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtNoise() {
		if (jtxtNoise == null) {
			jtxtNoise = new JTextField();
			jtxtNoise.setText("2");
			jtxtNoise.setSize(new Dimension(57, 20));
			jtxtNoise.setLocation(new Point(136, 88));
		}
		return jtxtNoise;
	}

	/**
	 * This method initializes jtxtInitialTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtInitialTime() {
		if (jtxtInitialTime == null) {
			jtxtInitialTime = new JTextField();
			jtxtInitialTime.setText("0");
			jtxtInitialTime.setLocation(new Point(136, 128));
			jtxtInitialTime.setSize(new Dimension(57, 20));
			jtxtInitialTime.setEnabled(false);
		}
		return jtxtInitialTime;
	}

	/**
	 * This method initializes jtxtFinalTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtFinalTime() {
		if (jtxtFinalTime == null) {
			jtxtFinalTime = new JTextField();
			jtxtFinalTime.setText("0");
			jtxtFinalTime.setLocation(new Point(136, 148));
			jtxtFinalTime.setSize(new Dimension(57, 20));
			jtxtFinalTime.setEnabled(false);
		}
		return jtxtFinalTime;
	}

	/**
	 * This method initializes jtxtNumPoints	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtNumPoints() {
		if (jtxtNumPoints == null) {
			jtxtNumPoints = new JTextField();
			jtxtNumPoints.setText("3000");
			jtxtNumPoints.setSize(new Dimension(57, 20));
			jtxtNumPoints.setLocation(new Point(136, 168));
		}
		return jtxtNumPoints;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
