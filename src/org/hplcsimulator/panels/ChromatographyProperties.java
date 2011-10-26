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

public class ChromatographyProperties extends JXPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JLabel jlblTemperature = null;
	public JSlider jsliderTemp = null;
	public JTextField jtxtTemp = null;
	public JLabel jlblInjectionVolume = null;
	public JTextField jtxtInjectionVolume = null;
	public JLabel jlblInjectionVolume2 = null;
	public JLabel jlblFlowRate = null;
	public JTextField jtxtFlowRate = null;
	public JLabel jlblFlowRate2 = null;
	public JLabel jlblFlowVelocity2 = null;
	public JLabel jlblFlowVelocity = null;
	public JLabel jlblFlowVelocity3 = null;
	public JLabel jlblHETP2 = null;
	public JLabel jlblHETP = null;
	public JLabel jlblHETP3 = null;
	public JLabel jlblTheoreticalPlates2 = null;
	public JLabel jlblTheoreticalPlates = null;
	public JLabel jlblBackpressure2 = null;
	public JLabel jlblBackpressure = null;
	public JLabel jlblBackpressure3 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public ChromatographyProperties() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        jlblBackpressure3 = new JLabel();
        jlblBackpressure3.setText("bar");
        jlblBackpressure3.setLocation(new Point(188, 172));
        jlblBackpressure3.setSize(new Dimension(50, 16));
        jlblBackpressure3.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblBackpressure = new JLabel();
        jlblBackpressure.setText("400");
        jlblBackpressure.setLocation(new Point(120, 172));
        jlblBackpressure.setSize(new Dimension(65, 16));
        jlblBackpressure.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblBackpressure2 = new JLabel();
        jlblBackpressure2.setText("Backpressure:");
        jlblBackpressure2.setSize(new Dimension(109, 16));
        jlblBackpressure2.setLocation(new Point(8, 172));
        jlblTheoreticalPlates = new JLabel();
        jlblTheoreticalPlates.setText("19000");
        jlblTheoreticalPlates.setLocation(new Point(120, 152));
        jlblTheoreticalPlates.setSize(new Dimension(65, 16));
        jlblTheoreticalPlates.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblTheoreticalPlates2 = new JLabel();
        jlblTheoreticalPlates2.setText("Theoretical plates:");
        jlblTheoreticalPlates2.setSize(new Dimension(109, 16));
        jlblTheoreticalPlates2.setLocation(new Point(8, 152));
        jlblHETP3 = new JLabel();
        jlblHETP3.setText("cm");
        jlblHETP3.setLocation(new Point(188, 132));
        jlblHETP3.setSize(new Dimension(50, 16));
        jlblHETP3.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblHETP = new JLabel();
        jlblHETP.setText("0.0005");
        jlblHETP.setLocation(new Point(120, 132));
        jlblHETP.setSize(new Dimension(65, 16));
        jlblHETP.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblHETP2 = new JLabel();
        jlblHETP2.setText("HETP:");
        jlblHETP2.setSize(new Dimension(109, 16));
        jlblHETP2.setLocation(new Point(8, 132));
        jlblFlowVelocity3 = new JLabel();
        jlblFlowVelocity3.setText("cm/s");
        jlblFlowVelocity3.setLocation(new Point(188, 112));
        jlblFlowVelocity3.setSize(new Dimension(50, 16));
        jlblFlowVelocity3.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblFlowVelocity = new JLabel();
        jlblFlowVelocity.setText("0.0005");
        jlblFlowVelocity.setLocation(new Point(120, 112));
        jlblFlowVelocity.setSize(new Dimension(65, 16));
        jlblFlowVelocity.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblFlowVelocity2 = new JLabel();
        jlblFlowVelocity2.setText("Flow velocity:");
        jlblFlowVelocity2.setSize(new Dimension(109, 16));
        jlblFlowVelocity2.setLocation(new Point(8, 112));
        jlblFlowRate2 = new JLabel();
        jlblFlowRate2.setText("mL/min");
        jlblFlowRate2.setPreferredSize(new Dimension(50, 16));
        jlblFlowRate2.setLocation(new Point(188, 92));
        jlblFlowRate2.setSize(new Dimension(50, 16));
        jlblFlowRate2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblFlowRate = new JLabel();
        jlblFlowRate.setText("Flow rate:");
        jlblFlowRate.setSize(new Dimension(109, 16));
        jlblFlowRate.setLocation(new Point(8, 92));
        jlblInjectionVolume2 = new JLabel();
        jlblInjectionVolume2.setText("\u00b5L");
        jlblInjectionVolume2.setPreferredSize(new Dimension(50, 16));
        jlblInjectionVolume2.setLocation(new Point(188, 72));
        jlblInjectionVolume2.setSize(new Dimension(49, 16));
        jlblInjectionVolume2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblInjectionVolume = new JLabel();
        jlblInjectionVolume.setText("Injection volume:");
        jlblInjectionVolume.setSize(new Dimension(109, 16));
        jlblInjectionVolume.setLocation(new Point(8, 72));
        jlblTemperature = new JLabel();
        jlblTemperature.setBounds(new Rectangle(8, 8, 189, 16));
        jlblTemperature.setText("Temperature (°C):");
        jlblTemperature.setFont(new Font("Dialog", Font.BOLD, 12));
        this.setLayout(null);
        this.setSize(new Dimension(254, 191));
        this.setBackground(Color.white);
        this.add(jlblTemperature, null);
        this.add(getJsliderTemp(), null);
        this.add(getJtxtTemp(), null);
        this.add(jlblInjectionVolume, null);
        this.add(getJtxtInjectionVolume(), null);
        this.add(jlblInjectionVolume2, null);
        this.add(jlblFlowRate, null);
        this.add(getJtxtFlowRate(), null);
        this.add(jlblFlowRate2, null);
        this.add(jlblFlowVelocity2, null);
        this.add(jlblFlowVelocity, null);
        this.add(jlblFlowVelocity3, null);
        this.add(jlblHETP2, null);
        this.add(jlblHETP, null);
        this.add(jlblHETP3, null);
        this.add(jlblTheoreticalPlates2, null);
        this.add(jlblTheoreticalPlates, null);
        this.add(jlblBackpressure2, null);
        this.add(jlblBackpressure, null);
        this.add(jlblBackpressure3, null);
			
	}

	/**
	 * This method initializes jsliderTemp	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJsliderTemp() {
		if (jsliderTemp == null) {
			jsliderTemp = new JSlider();
			jsliderTemp.setBounds(new Rectangle(0, 24, 200, 43));
			jsliderTemp.setFont(new Font("Dialog", Font.PLAIN, 12));
			jsliderTemp.setName("Temperature Slider");
			jsliderTemp.setMajorTickSpacing(20);
			jsliderTemp.setMaximum(150);
			jsliderTemp.setMinimum(10);
			jsliderTemp.setMinorTickSpacing(10);
			jsliderTemp.setPaintLabels(true);
			jsliderTemp.setPaintTicks(true);
			jsliderTemp.setSnapToTicks(false);
			jsliderTemp.setValue(25);
			jsliderTemp.setBackground(Color.white);
		}
		return jsliderTemp;
	}

	/**
	 * This method initializes jtxtTemp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtTemp() {
		if (jtxtTemp == null) {
			jtxtTemp = new JTextField();
			jtxtTemp.setToolTipText("");
			jtxtTemp.setText("25");
			jtxtTemp.setActionCommand("test");
			jtxtTemp.setLocation(new Point(200, 20));
			jtxtTemp.setSize(new Dimension(41, 20));
			jtxtTemp.setName("Temperature TextField");
		}
		return jtxtTemp;
	}

	/**
	 * This method initializes jtxtInjectionVolume	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtInjectionVolume() {
		if (jtxtInjectionVolume == null) {
			jtxtInjectionVolume = new JTextField();
			jtxtInjectionVolume.setText("5");
			jtxtInjectionVolume.setSize(new Dimension(65, 20));
			jtxtInjectionVolume.setLocation(new Point(120, 72));
		}
		return jtxtInjectionVolume;
	}

	/**
	 * This method initializes jtxtFlowRate	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtFlowRate() {
		if (jtxtFlowRate == null) {
			jtxtFlowRate = new JTextField();
			jtxtFlowRate.setText("2");
			jtxtFlowRate.setSize(new Dimension(65, 20));
			jtxtFlowRate.setLocation(new Point(120, 92));
		}
		return jtxtFlowRate;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
