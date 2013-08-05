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

public class ExtraColumnTubing extends JXPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JLabel jlblColumnLength = null;
	public JLabel jlblColumnDiameter = null;
	public JTextField jtxtTubingLength = null;
	public JTextField jtxtTubingDiameter = null;
	public JLabel jlblColumnLength2 = null;
	public JLabel jlblColumnDiameter2 = null;
	private JLabel jLabel = null;

	private JLabel jlblVolumeLabel = null;

	private JLabel jlblTubingVolumeUnits = null;

	public JLabel jlblTubingVolume = null;

	private JLabel jlblBroadeningLabel = null;

	public JLabel jlblDispersion = null;

	private JLabel jlblDispersionUnits = null;

	/**
	 * This method initializes 
	 * 
	 */
	public ExtraColumnTubing() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        jlblDispersionUnits = new JLabel();
        jlblDispersionUnits.setBounds(new Rectangle(196, 88, 45, 16));
        jlblDispersionUnits.setText("s");
        jlblDispersionUnits.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDispersionUnits.setVisible(false);
        jlblDispersion = new JLabel();
        jlblDispersion.setBounds(new Rectangle(136, 88, 57, 16));
        jlblDispersion.setText("1");
        jlblDispersion.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDispersion.setVisible(false);
        jlblBroadeningLabel = new JLabel();
        jlblBroadeningLabel.setBounds(new Rectangle(28, 88, 105, 16));
        jlblBroadeningLabel.setText("Dispersion (\u03c3):");
        jlblBroadeningLabel.setVisible(false);
        jlblTubingVolume = new JLabel();
        jlblTubingVolume.setBounds(new Rectangle(136, 68, 57, 16));
        jlblTubingVolume.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblTubingVolume.setText("1");
        jlblTubingVolumeUnits = new JLabel();
        jlblTubingVolumeUnits.setBounds(new Rectangle(196, 68, 45, 16));
        jlblTubingVolumeUnits.setText("µL");
        jlblTubingVolumeUnits.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblVolumeLabel = new JLabel();
        jlblVolumeLabel.setBounds(new Rectangle(28, 68, 105, 16));
        jlblVolumeLabel.setText("Volume:");
        jLabel = new JLabel();
        jLabel.setBounds(new Rectangle(8, 8, 196, 16));
        jLabel.setText("Post-column tubing:");
        jlblColumnDiameter2 = new JLabel();
        jlblColumnDiameter2.setText("mil");
        jlblColumnDiameter2.setLocation(new Point(196, 48));
        jlblColumnDiameter2.setSize(new Dimension(45, 16));
        jlblColumnDiameter2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblColumnLength2 = new JLabel();
        jlblColumnLength2.setText("cm");
        jlblColumnLength2.setLocation(new Point(196, 28));
        jlblColumnLength2.setSize(new Dimension(45, 16));
        jlblColumnLength2.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblColumnDiameter = new JLabel();
        jlblColumnDiameter.setText("Inner diameter:");
        jlblColumnDiameter.setSize(new Dimension(105, 16));
        jlblColumnDiameter.setLocation(new Point(28, 48));
        jlblColumnLength = new JLabel();
        jlblColumnLength.setText("Length:");
        jlblColumnLength.setSize(new Dimension(105, 16));
        jlblColumnLength.setLocation(new Point(28, 28));
        this.setLayout(null);
        this.setSize(new Dimension(254, 112));
        this.setBackground(Color.white);
        this.add(jlblColumnLength, null);
        this.add(jlblColumnDiameter, null);
        this.add(getJtxtTubingLength(), null);
        this.add(getJtxtTubingDiameter(), null);
        this.add(jlblColumnLength2, null);
        this.add(jlblColumnDiameter2, null);
        this.add(jLabel, null);
        this.add(jlblVolumeLabel, null);
        this.add(jlblTubingVolumeUnits, null);
        this.add(jlblTubingVolume, null);
        this.add(jlblBroadeningLabel, null);
        this.add(jlblDispersion, null);
        this.add(jlblDispersionUnits, null);
			
	}

	/**
	 * This method initializes jtxtTubingLength	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtTubingLength() {
		if (jtxtTubingLength == null) {
			jtxtTubingLength = new JTextField();
			jtxtTubingLength.setText("0");
			jtxtTubingLength.setSize(new Dimension(57, 20));
			jtxtTubingLength.setLocation(new Point(136, 28));
		}
		return jtxtTubingLength;
	}

	/**
	 * This method initializes jtxtTubingDiameter	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtTubingDiameter() {
		if (jtxtTubingDiameter == null) {
			jtxtTubingDiameter = new JTextField();
			jtxtTubingDiameter.setText("5");
			jtxtTubingDiameter.setSize(new Dimension(57, 20));
			jtxtTubingDiameter.setLocation(new Point(136, 48));
		}
		return jtxtTubingDiameter;
	}

}  //  @jve:decl-index=0:visual-constraint="-34,10"
