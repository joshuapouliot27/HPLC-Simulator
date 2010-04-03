import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.Color;
import java.awt.Rectangle;
import javax.media.opengl.*;
import java.awt.Point;
import javax.swing.JButton;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.help.*;

public class TopPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public GraphControl m_GraphControl = null;
	private JPanel jpanelColumnProperties = null;
	public JTextField jtxtColumnLength = null;
	private JLabel jlblColumnLength = null;
	private JLabel jlblColumnDiameter = null;
	public JTextField jtxtColumnDiameter = null;
	private JLabel jlblColumnLength2 = null;
	private JLabel jlblColumnDiameter2 = null;
	private JLabel jlblParticleSize = null;
	public JTextField jtxtParticleSize = null;
	private JLabel jlblParticleSize2 = null;
	private JLabel jlblVoidFraction = null;
	public JTextField jtxtVoidFraction = null;
	private JLabel jlblVanDeemter = null;
	private JLabel jlblATerm = null;
	private JLabel jlblBTerm = null;
	private JLabel jlblCTerm = null;
	public JTextField jtxtATerm = null;
	public JTextField jtxtBTerm = null;
	public JTextField jtxtCTerm = null;
	private JLabel jlblVoidVolume2 = null;
	public JLabel jlblVoidVolume = null;
	private JLabel jlblVoidVolume3 = null;
	private JLabel jlblVoidTime2 = null;
	public JLabel jlblVoidTime = null;
	private JLabel jlblVoidTime3 = null;
	private JLabel jlblReducedPlateHeight2 = null;
	public JLabel jlblReducedPlateHeight = null;
	private JPanel jpanelChromatography = null;
	public JSlider jsliderMeOHFraction = null;
	private JLabel jlblMeOHFraction = null;
	public JTextField jtxtMeOHFraction = null;
	public JSlider jsliderTemp = null;
	private JLabel jlblTemperature = null;
	public JTextField jtxtTemp = null;
	private JLabel jlblFlowRate = null;
	private JLabel jlblFlowRate2 = null;
	public JTextField jtxtFlowRate = null;
	private JLabel jlblHETP2 = null;
	private JLabel jlblHETP3 = null;
	public JLabel jlblHETP = null;
	private JLabel jlblFlowVelocity2 = null;
	public JLabel jlblFlowVelocity = null;
	private JLabel jlblFlowVelocity3 = null;
	private JPanel jpanelGeneral = null;
	private JLabel jlblTimeConstant = null;
	public JTextField jtxtTimeConstant = null;
	private JLabel jlblTimeConstant2 = null;
	private JLabel jlblSignalOffset = null;
	public JTextField jtxtSignalOffset = null;
	private JLabel jlblSignalOffset2 = null;
	private JLabel jlblNoise = null;
	public JTextField jtxtNoise = null;
	private JLabel jlblNoise2 = null;
	private JLabel jlblInitialTime = null;
	private JLabel jlblFinalTime = null;
	public JTextField jtxtInitialTime = null;
	public JTextField jtxtFinalTime = null;
	private JLabel jlblInitialTime2 = null;
	private JLabel jlblFinalTime2 = null;
	private JLabel jlblInjectionVolume = null;
	public JTextField jtxtInjectionVolume = null;
	private JLabel jlblInjectionVolume2 = null;
	private JScrollPane jScrollPane = null;
	public JTable jtableChemicals = null;
	public JButton jbtnAddChemical = null;
	public JButton jbtnEditChemical = null;
	public JButton jbtnRemoveChemical = null;
	public DefaultTableModel tabModel;
	
	public Vector<String> vectColumnNames = new Vector<String>();
	public Vector<Vector<String>> vectChemicalRows = new Vector<Vector<String>>();  //  @jve:decl-index=0:
	
	private JPanel jpanelCompounds = null;
	public JCheckBox jchkAutoTimeRange = null;
	private JLabel jlblNumPoints = null;
	public JTextField jtxtNumPoints = null;
	public JToggleButton jbtnAutoscale = null;
	public JToggleButton jbtnZoomIn = null;
	public JToggleButton jbtnZoomOut = null;
	public JToggleButton jbtnPan = null;
	private JPanel jPanel = null;
	public JToggleButton jbtnAutoscaleX = null;
	public JToggleButton jbtnAutoscaleY = null;
	private JLabel jlblEluentViscosity2 = null;
	private JLabel jlblEluentViscosity3 = null;
	public JLabel jlblEluentViscosity = null;
	private JLabel jlblBackpressure2 = null;
	public JLabel jlblBackpressure = null;
	private JLabel jlblBackpressure3 = null;
	private JLabel jlblDiffusionCoefficient2 = null;
	public JLabel jlblDiffusionCoefficient = null;
	private JLabel jlblDiffusionCoefficient3 = null;
	public JButton jbtnHelp = null;
	public JButton jbtnContextHelp = null;
	public JButton jbtnTutorials = null;
	private JLabel jlblTheoreticalPlates2 = null;
	public JLabel jlblTheoreticalPlates = null;
	/**
	 * This is the default constructor
	 */
	public TopPanel() {
		super();
		initialize();
	}

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() 
	{
		this.setLayout(null);
		
		this.setVisible(true);
		this.setBounds(new Rectangle(0, 0, 854, 689));
		this.setBackground(Color.white);
		
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        
		m_GraphControl = new GraphControl(caps);
		m_GraphControl.setBounds(new Rectangle(3, 16, 586, 459));
		
	    this.add(getJpanelColumnProperties(), null);
	    this.add(getJpanelChromatography(), null);
	    this.add(getJpanelGeneral(), null);
	    this.add(getJpanelCompounds(), null);
	    this.add(getJPanel(), null);
	    
	    CSH.setHelpIDString(this.jpanelChromatography, "controls.chromatography");
	    CSH.setHelpIDString(this.jtxtMeOHFraction, "controls.meohfraction");
	    CSH.setHelpIDString(this.jlblMeOHFraction, "controls.meohfraction");
	    CSH.setHelpIDString(this.jsliderMeOHFraction, "controls.meohfraction");
	    CSH.setHelpIDString(this.jtxtTemp, "controls.temperature");
	    CSH.setHelpIDString(this.jlblTemperature, "controls.temperature");
	    CSH.setHelpIDString(this.jsliderTemp, "controls.temperature");
	    CSH.setHelpIDString(this.jlblInjectionVolume, "controls.injectionvolume");
	    CSH.setHelpIDString(this.jtxtInjectionVolume, "controls.injectionvolume");
	    CSH.setHelpIDString(this.jlblInjectionVolume2, "controls.injectionvolume");
	    CSH.setHelpIDString(this.jlblFlowRate, "controls.flowrate");
	    CSH.setHelpIDString(this.jtxtFlowRate, "controls.flowrate");
	    CSH.setHelpIDString(this.jlblFlowRate2, "controls.flowrate");
	    CSH.setHelpIDString(this.jlblFlowVelocity, "controls.flowvelocity");
	    CSH.setHelpIDString(this.jlblFlowVelocity2, "controls.flowvelocity");
	    CSH.setHelpIDString(this.jlblFlowVelocity3, "controls.flowvelocity");
	    CSH.setHelpIDString(this.jlblHETP, "controls.hetp");
	    CSH.setHelpIDString(this.jlblHETP2, "controls.hetp");
	    CSH.setHelpIDString(this.jlblHETP3, "controls.hetp");
	    CSH.setHelpIDString(this.jlblTheoreticalPlates, "controls.theoreticalplates");
	    CSH.setHelpIDString(this.jlblTheoreticalPlates2, "controls.theoreticalplates");
	    CSH.setHelpIDString(this.jlblBackpressure, "controls.backpressure");
	    CSH.setHelpIDString(this.jlblBackpressure, "controls.backpressure");
	    CSH.setHelpIDString(this.jlblBackpressure, "controls.backpressure");
	    CSH.setHelpIDString(this.jlblEluentViscosity, "controls.eluentviscosity");
	    CSH.setHelpIDString(this.jlblEluentViscosity2, "controls.eluentviscosity");
	    CSH.setHelpIDString(this.jlblEluentViscosity3, "controls.eluentviscosity");
	    CSH.setHelpIDString(this.jlblDiffusionCoefficient, "controls.diffusioncoefficient");
	    CSH.setHelpIDString(this.jlblDiffusionCoefficient2, "controls.diffusioncoefficient");
	    CSH.setHelpIDString(this.jlblDiffusionCoefficient3, "controls.diffusioncoefficient");
	    CSH.setHelpIDString(this.jtxtTimeConstant, "controls.timeconstant");
	    CSH.setHelpIDString(this.jlblTimeConstant, "controls.timeconstant");
	    CSH.setHelpIDString(this.jlblTimeConstant2, "controls.timeconstant");
	    CSH.setHelpIDString(this.jtxtSignalOffset, "controls.signaloffset");
	    CSH.setHelpIDString(this.jlblSignalOffset, "controls.signaloffset");
	    CSH.setHelpIDString(this.jlblSignalOffset2, "controls.signaloffset");
	    CSH.setHelpIDString(this.jtxtNoise, "controls.noise");
	    CSH.setHelpIDString(this.jlblNoise, "controls.noise");
	    CSH.setHelpIDString(this.jlblNoise2, "controls.noise");
	    CSH.setHelpIDString(this.jchkAutoTimeRange, "controls.automatictimespan");
	    CSH.setHelpIDString(this.jtxtInitialTime, "controls.initialtime");
	    CSH.setHelpIDString(this.jlblInitialTime, "controls.initialtime");
	    CSH.setHelpIDString(this.jlblInitialTime2, "controls.initialtime");
	    CSH.setHelpIDString(this.jtxtFinalTime, "controls.finaltime");
	    CSH.setHelpIDString(this.jlblFinalTime, "controls.finaltime");
	    CSH.setHelpIDString(this.jlblFinalTime2, "controls.finaltime");
	    CSH.setHelpIDString(this.jtxtNumPoints, "controls.numdatapoints");
	    CSH.setHelpIDString(this.jlblNumPoints, "controls.numdatapoints");
	    CSH.setHelpIDString(this.jtxtColumnLength, "controls.columnlength");
	    CSH.setHelpIDString(this.jlblColumnLength, "controls.columnlength");
	    CSH.setHelpIDString(this.jlblColumnLength2, "controls.columnlength");
	    CSH.setHelpIDString(this.jtxtColumnDiameter, "controls.columndiameter");
	    CSH.setHelpIDString(this.jlblColumnDiameter, "controls.columndiameter");
	    CSH.setHelpIDString(this.jlblColumnDiameter2, "controls.columndiameter");
	    CSH.setHelpIDString(this.jtxtParticleSize, "controls.particlesize");
	    CSH.setHelpIDString(this.jlblParticleSize, "controls.particlesize");
	    CSH.setHelpIDString(this.jlblParticleSize2, "controls.particlesize");
	    CSH.setHelpIDString(this.jtxtVoidFraction, "controls.voidfraction");
	    CSH.setHelpIDString(this.jlblVoidFraction, "controls.voidfraction");
	    CSH.setHelpIDString(this.jlblVoidVolume, "controls.voidvolume");
	    CSH.setHelpIDString(this.jlblVoidVolume2, "controls.voidvolume");
	    CSH.setHelpIDString(this.jlblVoidVolume3, "controls.voidvolume");
	    CSH.setHelpIDString(this.jlblVoidTime, "controls.voidtime");
	    CSH.setHelpIDString(this.jlblVoidTime2, "controls.voidtime");
	    CSH.setHelpIDString(this.jlblVoidTime3, "controls.voidtime");
	    CSH.setHelpIDString(this.jlblVanDeemter, "controls.vandeemter");
	    CSH.setHelpIDString(this.jlblATerm, "controls.vandeemter");
	    CSH.setHelpIDString(this.jtxtATerm, "controls.vandeemter");
	    CSH.setHelpIDString(this.jlblBTerm, "controls.vandeemter");
	    CSH.setHelpIDString(this.jtxtBTerm, "controls.vandeemter");
	    CSH.setHelpIDString(this.jlblCTerm, "controls.vandeemter");
	    CSH.setHelpIDString(this.jtxtCTerm, "controls.vandeemter");
	    CSH.setHelpIDString(this.jlblReducedPlateHeight, "controls.reducedplateheight");
	    CSH.setHelpIDString(this.jlblReducedPlateHeight2, "controls.reducedplateheight");
	}

	/**
	 * This method initializes jpanelColumnProperties	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelColumnProperties() {
		if (jpanelColumnProperties == null) {
			jlblReducedPlateHeight = new JLabel();
			jlblReducedPlateHeight.setText("0.9987");
			jlblReducedPlateHeight.setSize(new Dimension(45, 16));
			jlblReducedPlateHeight.setLocation(new Point(197, 130));
			jlblReducedPlateHeight.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblReducedPlateHeight2 = new JLabel();
			jlblReducedPlateHeight2.setText("Reduced plate height:");
			jlblReducedPlateHeight2.setLocation(new Point(197, 108));
			jlblReducedPlateHeight2.setSize(new Dimension(126, 16));
			jlblVoidTime3 = new JLabel();
			jlblVoidTime3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblVoidTime3.setLocation(new Point(144, 130));
			jlblVoidTime3.setSize(new Dimension(25, 16));
			jlblVoidTime3.setText("s");
			jlblVoidTime = new JLabel();
			jlblVoidTime.setText("29.987");
			jlblVoidTime.setSize(new Dimension(45, 16));
			jlblVoidTime.setLocation(new Point(96, 130));
			jlblVoidTime.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblVoidTime2 = new JLabel();
			jlblVoidTime2.setText("Void time:");
			jlblVoidTime2.setLocation(new Point(12, 130));
			jlblVoidTime2.setSize(new Dimension(81, 16));
			jlblVoidVolume3 = new JLabel();
			jlblVoidVolume3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblVoidVolume3.setLocation(new Point(144, 108));
			jlblVoidVolume3.setSize(new Dimension(25, 16));
			jlblVoidVolume3.setText("mL");
			jlblVoidVolume = new JLabel();
			jlblVoidVolume.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblVoidVolume.setSize(new Dimension(45, 16));
			jlblVoidVolume.setLocation(new Point(96, 108));
			jlblVoidVolume.setText("0.9987");
			jlblVoidVolume2 = new JLabel();
			jlblVoidVolume2.setText("Void volume:");
			jlblVoidVolume2.setLocation(new Point(12, 108));
			jlblVoidVolume2.setSize(new Dimension(81, 16));
			jlblCTerm = new JLabel();
			jlblCTerm.setText("C:");
			jlblCTerm.setLocation(new Point(197, 86));
			jlblCTerm.setSize(new Dimension(45, 16));
			jlblBTerm = new JLabel();
			jlblBTerm.setText("B:");
			jlblBTerm.setLocation(new Point(197, 64));
			jlblBTerm.setSize(new Dimension(45, 16));
			jlblATerm = new JLabel();
			jlblATerm.setText("A:");
			jlblATerm.setLocation(new Point(197, 42));
			jlblATerm.setSize(new Dimension(45, 16));
			jlblVanDeemter = new JLabel();
			jlblVanDeemter.setBounds(new Rectangle(196, 20, 117, 16));
			jlblVanDeemter.setName("");
			jlblVanDeemter.setText("Van Deemter terms:");
			jlblVoidFraction = new JLabel();
			jlblVoidFraction.setText("Void fraction:");
			jlblVoidFraction.setLocation(new Point(12, 86));
			jlblVoidFraction.setSize(new Dimension(81, 16));
			jlblParticleSize2 = new JLabel();
			jlblParticleSize2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblParticleSize2.setLocation(new Point(144, 64));
			jlblParticleSize2.setSize(new Dimension(25, 16));
			jlblParticleSize2.setText("\u03bcm");
			jlblParticleSize = new JLabel();
			jlblParticleSize.setBounds(new Rectangle(12, 64, 81, 16));
			jlblParticleSize.setText("Particle size:");
			jlblColumnDiameter2 = new JLabel();
			jlblColumnDiameter2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblColumnDiameter2.setBounds(new Rectangle(144, 42, 25, 16));
			jlblColumnDiameter2.setText("mm");
			jlblColumnLength2 = new JLabel();
			jlblColumnLength2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblColumnLength2.setLocation(new Point(144, 21));
			jlblColumnLength2.setSize(new Dimension(25, 16));
			jlblColumnLength2.setText("mm");
			jlblColumnDiameter = new JLabel();
			jlblColumnDiameter.setBounds(new Rectangle(12, 42, 81, 16));
			jlblColumnDiameter.setText("Diameter:");
			jlblColumnLength = new JLabel();
			jlblColumnLength.setBounds(new Rectangle(12, 20, 81, 16));
			jlblColumnLength.setText("Length:");
			jpanelColumnProperties = new JPanel();
			jpanelColumnProperties.setLayout(null);
			jpanelColumnProperties.setBorder(BorderFactory.createTitledBorder(null, "Column Properties", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelColumnProperties.setBackground(Color.white);
			jpanelColumnProperties.setSize(new Dimension(333, 173));
			jpanelColumnProperties.setLocation(new Point(0, 514));
			jpanelColumnProperties.add(getJtxtColumnLength(), null);
			jpanelColumnProperties.add(jlblColumnLength, null);
			jpanelColumnProperties.add(jlblColumnDiameter, null);
			jpanelColumnProperties.add(getJtxtColumnDiameter(), null);
			jpanelColumnProperties.add(jlblColumnLength2, null);
			jpanelColumnProperties.add(jlblColumnDiameter2, null);
			jpanelColumnProperties.add(jlblParticleSize, null);
			jpanelColumnProperties.add(getJtxtParticleSize(), null);
			jpanelColumnProperties.add(jlblParticleSize2, null);
			jpanelColumnProperties.add(jlblVoidFraction, null);
			jpanelColumnProperties.add(getJtxtVoidFraction(), null);
			jpanelColumnProperties.add(jlblVanDeemter, null);
			jpanelColumnProperties.add(jlblATerm, null);
			jpanelColumnProperties.add(jlblBTerm, null);
			jpanelColumnProperties.add(jlblCTerm, null);
			jpanelColumnProperties.add(getJtxtATerm(), null);
			jpanelColumnProperties.add(getJtxtBTerm(), null);
			jpanelColumnProperties.add(getJtxtCTerm(), null);
			jpanelColumnProperties.add(jlblVoidVolume2, null);
			jpanelColumnProperties.add(jlblVoidVolume, null);
			jpanelColumnProperties.add(jlblVoidVolume3, null);
			jpanelColumnProperties.add(jlblVoidTime2, null);
			jpanelColumnProperties.add(jlblVoidTime, null);
			jpanelColumnProperties.add(jlblVoidTime3, null);
			jpanelColumnProperties.add(jlblReducedPlateHeight2, null);
			jpanelColumnProperties.add(jlblReducedPlateHeight, null);
		}
		return jpanelColumnProperties;
	}

	/**
	 * This method initializes jtxtColumnLength	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtColumnLength() 
	{
		if (jtxtColumnLength == null) {
			jtxtColumnLength = new JTextField();
			jtxtColumnLength.setText("100");
			jtxtColumnLength.setLocation(new Point(96, 20));
			jtxtColumnLength.setSize(new Dimension(45, 20));
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
			jtxtColumnDiameter.setBounds(new Rectangle(96, 42, 45, 20));
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
			jtxtParticleSize.setLocation(new Point(96, 64));
			jtxtParticleSize.setSize(new Dimension(45, 20));
		}
		return jtxtParticleSize;
	}

	/**
	 * This method initializes jtxtVoidFraction	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtVoidFraction() {
		if (jtxtVoidFraction == null) {
			jtxtVoidFraction = new JTextField();
			jtxtVoidFraction.setText("0.6");
			jtxtVoidFraction.setLocation(new Point(96, 86));
			jtxtVoidFraction.setSize(new Dimension(45, 20));
		}
		return jtxtVoidFraction;
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
			jtxtATerm.setLocation(new Point(245, 42));
			jtxtATerm.setSize(new Dimension(45, 20));
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
			jtxtBTerm.setText("3");
			jtxtBTerm.setLocation(new Point(245, 64));
			jtxtBTerm.setSize(new Dimension(45, 20));
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
			jtxtCTerm.setLocation(new Point(245, 86));
			jtxtCTerm.setSize(new Dimension(45, 20));
		}
		return jtxtCTerm;
	}

	/**
	 * This method initializes jpanelChromatography	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelChromatography() {
		if (jpanelChromatography == null) {
			jlblTheoreticalPlates = new JLabel();
			jlblTheoreticalPlates.setText("19000");
			jlblTheoreticalPlates.setSize(new Dimension(65, 16));
			jlblTheoreticalPlates.setLocation(new Point(124, 236));
			jlblTheoreticalPlates.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblTheoreticalPlates2 = new JLabel();
			jlblTheoreticalPlates2.setText("Theoretical plates:");
			jlblTheoreticalPlates2.setLocation(new Point(12, 236));
			jlblTheoreticalPlates2.setSize(new Dimension(109, 16));
			jlblBackpressure3 = new JLabel();
			jlblBackpressure3.setText("bar");
			jlblBackpressure3.setSize(new Dimension(49, 16));
			jlblBackpressure3.setLocation(new Point(192, 258));
			jlblBackpressure3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblBackpressure = new JLabel();
			jlblBackpressure.setText("400");
			jlblBackpressure.setSize(new Dimension(65, 16));
			jlblBackpressure.setLocation(new Point(124, 258));
			jlblBackpressure.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblBackpressure2 = new JLabel();
			jlblBackpressure2.setText("Backpressure:");
			jlblBackpressure2.setLocation(new Point(12, 258));
			jlblBackpressure2.setSize(new Dimension(109, 16));
			jlblInjectionVolume2 = new JLabel();
			jlblInjectionVolume2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblInjectionVolume2.setSize(new Dimension(49, 16));
			jlblInjectionVolume2.setLocation(new Point(192, 148));
			jlblInjectionVolume2.setText("\u03bcL");
			jlblInjectionVolume = new JLabel();
			jlblInjectionVolume.setBounds(new Rectangle(12, 148, 109, 16));
			jlblInjectionVolume.setText("Injection volume:");
			jlblFlowVelocity3 = new JLabel();
			jlblFlowVelocity3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblFlowVelocity3.setLocation(new Point(192, 192));
			jlblFlowVelocity3.setSize(new Dimension(50, 16));
			jlblFlowVelocity3.setText("cm/s");
			jlblFlowVelocity = new JLabel();
			jlblFlowVelocity.setText("0.0005");
			jlblFlowVelocity.setLocation(new Point(124, 192));
			jlblFlowVelocity.setSize(new Dimension(65, 16));
			jlblFlowVelocity.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblFlowVelocity2 = new JLabel();
			jlblFlowVelocity2.setText("Flow velocity:");
			jlblFlowVelocity2.setLocation(new Point(12, 192));
			jlblFlowVelocity2.setSize(new Dimension(109, 16));
			jlblHETP = new JLabel();
			jlblHETP.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblHETP.setSize(new Dimension(65, 16));
			jlblHETP.setLocation(new Point(124, 214));
			jlblHETP.setText("0.0005");
			jlblHETP3 = new JLabel();
			jlblHETP3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblHETP3.setLocation(new Point(192, 214));
			jlblHETP3.setSize(new Dimension(50, 16));
			jlblHETP3.setText("cm");
			jlblHETP2 = new JLabel();
			jlblHETP2.setText("HETP:");
			jlblHETP2.setLocation(new Point(12, 214));
			jlblHETP2.setSize(new Dimension(109, 16));
			jlblFlowRate2 = new JLabel();
			jlblFlowRate2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblFlowRate2.setSize(new Dimension(50, 16));
			jlblFlowRate2.setLocation(new Point(192, 170));
			jlblFlowRate2.setText("mL/min");
			jlblFlowRate = new JLabel();
			jlblFlowRate.setText("Flow rate:");
			jlblFlowRate.setLocation(new Point(12, 170));
			jlblFlowRate.setSize(new Dimension(109, 16));
			jlblTemperature = new JLabel();
			jlblTemperature.setBounds(new Rectangle(12, 80, 109, 16));
			jlblTemperature.setFont(new Font("Dialog", Font.BOLD, 12));
			jlblTemperature.setText("Temperature (°C):");
			jlblMeOHFraction = new JLabel();
			jlblMeOHFraction.setBounds(new Rectangle(12, 20, 113, 16));
			jlblMeOHFraction.setText("MeOH fraction (%):");
			jpanelChromatography = new JPanel();
			jpanelChromatography.setLayout(null);
			jpanelChromatography.setBounds(new Rectangle(0, 0, 253, 285));
			jpanelChromatography.setBorder(BorderFactory.createTitledBorder(null, "Chromatographic Properties", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelChromatography.setBackground(Color.white);
			jpanelChromatography.add(getJsliderMeOHFraction(), null);
			jpanelChromatography.add(jlblMeOHFraction, null);
			jpanelChromatography.add(getJtxtMeOHFraction(), null);
			jpanelChromatography.add(getJsliderTemp(), null);
			jpanelChromatography.add(jlblTemperature, null);
			jpanelChromatography.add(getJtxtTemp(), null);
			jpanelChromatography.add(jlblFlowRate, null);
			jpanelChromatography.add(jlblFlowRate2, null);
			jpanelChromatography.add(getJtxtFlowRate(), null);
			jpanelChromatography.add(jlblHETP2, null);
			jpanelChromatography.add(jlblHETP3, null);
			jpanelChromatography.add(jlblHETP, null);
			jpanelChromatography.add(jlblFlowVelocity2, null);
			jpanelChromatography.add(jlblFlowVelocity, null);
			jpanelChromatography.add(jlblFlowVelocity3, null);
			jpanelChromatography.add(jlblInjectionVolume, null);
			jpanelChromatography.add(getJtxtInjectionVolume(), null);
			jpanelChromatography.add(jlblInjectionVolume2, null);
			jpanelChromatography.add(jlblBackpressure2, null);
			jpanelChromatography.add(jlblBackpressure, null);
			jpanelChromatography.add(jlblBackpressure3, null);
			jpanelChromatography.add(jlblTheoreticalPlates2, null);
			jpanelChromatography.add(jlblTheoreticalPlates, null);
		}
		return jpanelChromatography;
	}

	/**
	 * This method initializes jsliderMeOHFraction	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJsliderMeOHFraction() {
		if (jsliderMeOHFraction == null) {
			jsliderMeOHFraction = new JSlider();
			jsliderMeOHFraction.setBounds(new Rectangle(4, 36, 189, 48));
			jsliderMeOHFraction.setMaximum(100);
			jsliderMeOHFraction.setMajorTickSpacing(25);
			jsliderMeOHFraction.setMinorTickSpacing(5);
			jsliderMeOHFraction.setValue(50);
			jsliderMeOHFraction.setPaintTicks(true);
			jsliderMeOHFraction.setPaintLabels(true);
			jsliderMeOHFraction.setFont(new Font("Dialog", Font.PLAIN, 12));
			jsliderMeOHFraction.setPaintTrack(true);
			jsliderMeOHFraction.setName("Methanol Fraction Slider");
			jsliderMeOHFraction.setBackground(Color.white);
		}
		return jsliderMeOHFraction;
	}

	/**
	 * This method initializes jtxtMeOHFraction	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtMeOHFraction() {
		if (jtxtMeOHFraction == null) {
			jtxtMeOHFraction = new JTextField();
			jtxtMeOHFraction.setText("50");
			jtxtMeOHFraction.setSize(new Dimension(45, 20));
			jtxtMeOHFraction.setName("Methanol Fraction TextField");
			jtxtMeOHFraction.setLocation(new Point(196, 32));
		}
		return jtxtMeOHFraction;
	}

	/**
	 * This method initializes jsliderTemp	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJsliderTemp() {
		if (jsliderTemp == null) {
			jsliderTemp = new JSlider();
			jsliderTemp.setBounds(new Rectangle(4, 96, 189, 48));
			jsliderTemp.setMajorTickSpacing(20);
			jsliderTemp.setMaximum(150);
			jsliderTemp.setMinimum(10);
			jsliderTemp.setMinorTickSpacing(10);
			jsliderTemp.setValue(25);
			jsliderTemp.setSnapToTicks(false);
			jsliderTemp.setPaintTicks(true);
			jsliderTemp.setPaintLabels(true);
			jsliderTemp.setFont(new Font("Dialog", Font.PLAIN, 12));
			jsliderTemp.setName("Temperature Slider");
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
			jtxtTemp.setText("25");
			jtxtTemp.setSize(new Dimension(45, 20));
			jtxtTemp.setName("Temperature TextField");
			jtxtTemp.setActionCommand("test");
			jtxtTemp.setToolTipText("");
			jtxtTemp.setLocation(new Point(196, 92));
		}
		return jtxtTemp;
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
			jtxtFlowRate.setLocation(new Point(124, 170));
		}
		return jtxtFlowRate;
	}

	/**
	 * This method initializes jpanelGeneral	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelGeneral() {
		if (jpanelGeneral == null) {
			jlblDiffusionCoefficient3 = new JLabel();
			jlblDiffusionCoefficient3.setText("cm²/s");
			jlblDiffusionCoefficient3.setSize(new Dimension(45, 16));
			jlblDiffusionCoefficient3.setLocation(new Point(196, 42));
			jlblDiffusionCoefficient3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblDiffusionCoefficient = new JLabel();
			jlblDiffusionCoefficient.setText("0.00001");
			jlblDiffusionCoefficient.setSize(new Dimension(57, 16));
			jlblDiffusionCoefficient.setLocation(new Point(136, 42));
			jlblDiffusionCoefficient.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblDiffusionCoefficient2 = new JLabel();
			jlblDiffusionCoefficient2.setText("Avg. diffusion coeff.:");
			jlblDiffusionCoefficient2.setLocation(new Point(12, 42));
			jlblDiffusionCoefficient2.setSize(new Dimension(121, 16));
			jlblEluentViscosity = new JLabel();
			jlblEluentViscosity.setBounds(new Rectangle(136, 20, 57, 16));
			jlblEluentViscosity.setText("0.9987");
			jlblEluentViscosity.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblEluentViscosity3 = new JLabel();
			jlblEluentViscosity3.setBounds(new Rectangle(196, 20, 45, 16));
			jlblEluentViscosity3.setText("cP");
			jlblEluentViscosity3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblEluentViscosity2 = new JLabel();
			jlblEluentViscosity2.setBounds(new Rectangle(12, 20, 121, 16));
			jlblEluentViscosity2.setText("Eluent viscosity:");
			jlblNumPoints = new JLabel();
			jlblNumPoints.setText("Data points:");
			jlblNumPoints.setLocation(new Point(12, 196));
			jlblNumPoints.setSize(new Dimension(120, 16));
			jlblFinalTime2 = new JLabel();
			jlblFinalTime2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblFinalTime2.setSize(new Dimension(45, 16));
			jlblFinalTime2.setLocation(new Point(196, 174));
			jlblFinalTime2.setText("s");
			jlblInitialTime2 = new JLabel();
			jlblInitialTime2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblInitialTime2.setSize(new Dimension(45, 16));
			jlblInitialTime2.setLocation(new Point(196, 152));
			jlblInitialTime2.setText("s");
			jlblFinalTime = new JLabel();
			jlblFinalTime.setText("Final time:");
			jlblFinalTime.setLocation(new Point(12, 174));
			jlblFinalTime.setSize(new Dimension(121, 16));
			jlblInitialTime = new JLabel();
			jlblInitialTime.setText("Initial time:");
			jlblInitialTime.setLocation(new Point(12, 152));
			jlblInitialTime.setSize(new Dimension(121, 16));
			jlblNoise2 = new JLabel();
			jlblNoise2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblNoise2.setSize(new Dimension(45, 16));
			jlblNoise2.setLocation(new Point(196, 108));
			jlblNoise2.setText("");
			jlblNoise = new JLabel();
			jlblNoise.setText("Noise:");
			jlblNoise.setLocation(new Point(12, 108));
			jlblNoise.setSize(new Dimension(121, 16));
			jlblSignalOffset2 = new JLabel();
			jlblSignalOffset2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblSignalOffset2.setSize(new Dimension(44, 16));
			jlblSignalOffset2.setLocation(new Point(197, 86));
			jlblSignalOffset2.setText("munits");
			jlblSignalOffset = new JLabel();
			jlblSignalOffset.setText("Signal offset:");
			jlblSignalOffset.setLocation(new Point(12, 86));
			jlblSignalOffset.setSize(new Dimension(121, 16));
			jlblTimeConstant2 = new JLabel();
			jlblTimeConstant2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jlblTimeConstant2.setSize(new Dimension(45, 16));
			jlblTimeConstant2.setLocation(new Point(196, 64));
			jlblTimeConstant2.setText("s");
			jlblTimeConstant = new JLabel();
			jlblTimeConstant.setText("Time constant:");
			jlblTimeConstant.setLocation(new Point(12, 64));
			jlblTimeConstant.setSize(new Dimension(121, 16));
			jpanelGeneral = new JPanel();
			jpanelGeneral.setLayout(null);
			jpanelGeneral.setBounds(new Rectangle(0, 288, 253, 223));
			jpanelGeneral.setBorder(BorderFactory.createTitledBorder(null, "General Properties", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelGeneral.setBackground(Color.white);
			jpanelGeneral.add(jlblTimeConstant, null);
			jpanelGeneral.add(getJtxtTimeConstant(), null);
			jpanelGeneral.add(jlblTimeConstant2, null);
			jpanelGeneral.add(jlblSignalOffset, null);
			jpanelGeneral.add(getJtxtSignalOffset(), null);
			jpanelGeneral.add(jlblSignalOffset2, null);
			jpanelGeneral.add(jlblNoise, null);
			jpanelGeneral.add(getJtxtNoise(), null);
			jpanelGeneral.add(jlblNoise2, null);
			jpanelGeneral.add(jlblInitialTime, null);
			jpanelGeneral.add(jlblFinalTime, null);
			jpanelGeneral.add(getJtxtInitialTime(), null);
			jpanelGeneral.add(getJtxtFinalTime(), null);
			jpanelGeneral.add(jlblInitialTime2, null);
			jpanelGeneral.add(jlblFinalTime2, null);
			jpanelGeneral.add(getJchkAutoTimeRange(), null);
			jpanelGeneral.add(jlblNumPoints, null);
			jpanelGeneral.add(getJtxtNumPoints(), null);
			jpanelGeneral.add(jlblEluentViscosity2, null);
			jpanelGeneral.add(jlblEluentViscosity3, null);
			jpanelGeneral.add(jlblEluentViscosity, null);
			jpanelGeneral.add(jlblDiffusionCoefficient2, null);
			jpanelGeneral.add(jlblDiffusionCoefficient, null);
			jpanelGeneral.add(jlblDiffusionCoefficient3, null);
		}
		return jpanelGeneral;
	}

	/**
	 * This method initializes jtxtTimeConstant	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtTimeConstant() {
		if (jtxtTimeConstant == null) {
			jtxtTimeConstant = new JTextField();
			jtxtTimeConstant.setText("0.5");
			jtxtTimeConstant.setLocation(new Point(136, 64));
			jtxtTimeConstant.setSize(new Dimension(57, 20));
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
			jtxtSignalOffset.setText("1");
			jtxtSignalOffset.setLocation(new Point(136, 86));
			jtxtSignalOffset.setSize(new Dimension(57, 20));
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
			jtxtNoise.setText("40");
			jtxtNoise.setLocation(new Point(136, 108));
			jtxtNoise.setSize(new Dimension(57, 20));
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
			jtxtInitialTime.setLocation(new Point(136, 152));
			jtxtInitialTime.setEnabled(false);
			jtxtInitialTime.setSize(new Dimension(57, 20));
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
			jtxtFinalTime.setLocation(new Point(136, 174));
			jtxtFinalTime.setEnabled(false);
			jtxtFinalTime.setSize(new Dimension(57, 20));
		}
		return jtxtFinalTime;
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
			jtxtInjectionVolume.setLocation(new Point(124, 148));
		}
		return jtxtInjectionVolume;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(12, 24, 488, 105));
			jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane.setViewportView(getJtableChemicals());
			jScrollPane.setBorder(null);
		}
		return jScrollPane;
	}
	
	public class JChemicalTable extends JTable
	{
		private static final long serialVersionUID = 1L;
		
		public JChemicalTable(DefaultTableModel tabModel) {
			super(tabModel);
		}

		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	}

	/**
	 * This method initializes jtableChemicals	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJtableChemicals() 
	{
		if (jtableChemicals == null) 
		{
			String[] columnNames = 
			{ 
			"Compound", 
			"Conc. (\u03BCM)",
			"k'",
			"tR (s)",
			"<html>&#x03C3<sub>total</sub> (s)</html>",
			"W (\u03BCmol)"
			};
			
			for (int i = 0; i < columnNames.length; i++)
			{
				vectColumnNames.add(columnNames[i]);
			}
			
			tabModel = new DefaultTableModel();
			tabModel.setDataVector(vectChemicalRows, vectColumnNames);

			jtableChemicals = new JChemicalTable(tabModel);
			jtableChemicals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jtableChemicals.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			jtableChemicals.getTableHeader().setPreferredSize(new Dimension(jtableChemicals.getColumnModel().getTotalColumnWidth(), 22));
			jtableChemicals.getColumnModel().getColumn(0).setPreferredWidth(135);
			jtableChemicals.getColumnModel().getColumn(1).setPreferredWidth(75);
			jtableChemicals.getColumnModel().getColumn(2).setPreferredWidth(60);
			jtableChemicals.getColumnModel().getColumn(3).setPreferredWidth(60);
			jtableChemicals.getColumnModel().getColumn(4).setPreferredWidth(70);
			jtableChemicals.getColumnModel().getColumn(5).setPreferredWidth(70);
		}
		return jtableChemicals;
	}
	
	/**
	 * This method initializes jbtnAddChemical	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnAddChemical() {
		if (jbtnAddChemical == null) {
			jbtnAddChemical = new JButton();
			jbtnAddChemical.setActionCommand("Add Chemical");
			jbtnAddChemical.setBounds(new Rectangle(12, 136, 81, 26));
			jbtnAddChemical.setText("Add");
		}
		return jbtnAddChemical;
	}

	/**
	 * This method initializes jbtnEditChemical	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnEditChemical() {
		if (jbtnEditChemical == null) {
			jbtnEditChemical = new JButton();
			jbtnEditChemical.setActionCommand("Edit Chemical");
			jbtnEditChemical.setBounds(new Rectangle(100, 136, 81, 26));
			jbtnEditChemical.setText("Edit");
		}
		return jbtnEditChemical;
	}

	/**
	 * This method initializes jbtnRemoveChemical	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnRemoveChemical() {
		if (jbtnRemoveChemical == null) {
			jbtnRemoveChemical = new JButton();
			jbtnRemoveChemical.setActionCommand("Remove Chemical");
			jbtnRemoveChemical.setBounds(new Rectangle(188, 136, 81, 26));
			jbtnRemoveChemical.setText("Remove");
		}
		return jbtnRemoveChemical;
	}

	/**
	 * This method initializes jpanelCompounds	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelCompounds() {
		if (jpanelCompounds == null) {
			jpanelCompounds = new JPanel();
			jpanelCompounds.setLayout(null);
			jpanelCompounds.setBorder(BorderFactory.createTitledBorder(null, "Compounds", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelCompounds.setBackground(Color.white);
			jpanelCompounds.setSize(new Dimension(513, 173));
			jpanelCompounds.setLocation(new Point(340, 514));
			jpanelCompounds.add(getJScrollPane(), null);
			jpanelCompounds.add(getJbtnAddChemical(), null);
			jpanelCompounds.add(getJbtnEditChemical(), null);
			jpanelCompounds.add(getJbtnRemoveChemical(), null);
			jpanelCompounds.add(getJbtnTutorials(), null);
			jpanelCompounds.add(getJbtnContextHelp(), null);
			jpanelCompounds.add(getJbtnHelp(), null);
		}
		return jpanelCompounds;
	}

	/**
	 * This method initializes jchkAutoTimeRange	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJchkAutoTimeRange() {
		if (jchkAutoTimeRange == null) {
			jchkAutoTimeRange = new JCheckBox();
			jchkAutoTimeRange.setText("Automatically determine time span");
			jchkAutoTimeRange.setSelected(true);
			jchkAutoTimeRange.setName("jchkAutoTimeRange");
			jchkAutoTimeRange.setSize(new Dimension(223, 21));
			jchkAutoTimeRange.setLocation(new Point(8, 128));
			jchkAutoTimeRange.setBackground(Color.white);
		}
		return jchkAutoTimeRange;
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
			jtxtNumPoints.setLocation(new Point(136, 196));
			jtxtNumPoints.setSize(new Dimension(57, 20));
		}
		return jtxtNumPoints;
	}

	/**
	 * This method initializes jbtnAutoscale	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnAutoscale() {
		if (jbtnAutoscale == null) {
			jbtnAutoscale = new JToggleButton();
			jbtnAutoscale.setIcon(new ImageIcon(getClass().getResource("/images/autoscale.png")));
			jbtnAutoscale.setRolloverEnabled(false);
			jbtnAutoscale.setSelected(true);
			jbtnAutoscale.setText("Autoscale");
			jbtnAutoscale.setBounds(new Rectangle(8, 480, 112, 23));
			jbtnAutoscale.setToolTipText("");
		}
		return jbtnAutoscale;
	}

	/**
	 * This method initializes jbtnZoomIn	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnZoomIn() {
		if (jbtnZoomIn == null) {
			jbtnZoomIn = new JToggleButton();
			jbtnZoomIn.setIcon(new ImageIcon(getClass().getResource("/images/zoomin.png")));
			jbtnZoomIn.setRolloverEnabled(false);
			jbtnZoomIn.setSelected(false);
			jbtnZoomIn.setText("Zoom in");
			jbtnZoomIn.setBounds(new Rectangle(350, 480, 112, 23));
			jbtnZoomIn.setToolTipText("");
		}
		return jbtnZoomIn;
	}

	/**
	 * This method initializes jbtnZoomOut	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnZoomOut() {
		if (jbtnZoomOut == null) {
			jbtnZoomOut = new JToggleButton();
			jbtnZoomOut.setIcon(new ImageIcon(getClass().getResource("/images/zoomout.png")));
			jbtnZoomOut.setRolloverEnabled(false);
			jbtnZoomOut.setSelected(false);
			jbtnZoomOut.setText("Zoom out");
			jbtnZoomOut.setBounds(new Rectangle(470, 480, 112, 23));
			jbtnZoomOut.setToolTipText("");
		}
		return jbtnZoomOut;
	}

	/**
	 * This method initializes jbtnPan	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnPan() {
		if (jbtnPan == null) {
			jbtnPan = new JToggleButton();
			jbtnPan.setIcon(new ImageIcon(getClass().getResource("/images/pan.png")));
			jbtnPan.setRolloverEnabled(false);
			jbtnPan.setSelected(true);
			jbtnPan.setText("Pan");
			jbtnPan.setBounds(new Rectangle(230, 480, 112, 23));
			jbtnPan.setToolTipText("");
		}
		return jbtnPan;
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
			jPanel.setBounds(new Rectangle(260, 0, 593, 511));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "Simulated Chromatogram", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.setBackground(Color.white);
			jPanel.add(getJbtnAutoscale(), null);
			jPanel.add(getJbtnPan(), null);
			jPanel.add(getJbtnZoomIn(), null);
			jPanel.add(getJbtnZoomOut(), null);
			jPanel.add(m_GraphControl, null);
			jPanel.add(getJbtnAutoscaleX(), null);
			jPanel.add(getJbtnAutoscaleY(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jbtnAutoscaleX	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnAutoscaleX() {
		if (jbtnAutoscaleX == null) {
			jbtnAutoscaleX = new JToggleButton();
			jbtnAutoscaleX.setBounds(new Rectangle(128, 480, 32, 23));
			jbtnAutoscaleX.setIcon(new ImageIcon(getClass().getResource("/images/autoscaleX.png")));
			jbtnAutoscaleX.setRolloverEnabled(false);
			jbtnAutoscaleX.setSelected(true);
			jbtnAutoscaleX.setText("");
			jbtnAutoscaleX.setActionCommand("Autoscale X");
			jbtnAutoscaleX.setToolTipText("");
		}
		return jbtnAutoscaleX;
	}

	/**
	 * This method initializes jbtnAutoscaleY	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnAutoscaleY() {
		if (jbtnAutoscaleY == null) {
			jbtnAutoscaleY = new JToggleButton();
			jbtnAutoscaleY.setBounds(new Rectangle(168, 480, 32, 23));
			jbtnAutoscaleY.setActionCommand("Autoscale Y");
			jbtnAutoscaleY.setIcon(new ImageIcon(getClass().getResource("/images/autoscaleY.png")));
			jbtnAutoscaleY.setRolloverEnabled(false);
			jbtnAutoscaleY.setSelected(true);
			jbtnAutoscaleY.setText("");
			jbtnAutoscaleY.setToolTipText("");
		}
		return jbtnAutoscaleY;
	}


	/**
	 * This method initializes jbtnHelp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnHelp() {
		if (jbtnHelp == null) {
			jbtnHelp = new JButton();
			jbtnHelp.setText("Help");
			jbtnHelp.setForeground(Color.blue);
			jbtnHelp.setBounds(new Rectangle(428, 136, 69, 26));
		}
		return jbtnHelp;
	}


	/**
	 * This method initializes jbtnContextHelp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnContextHelp() {
		if (jbtnContextHelp == null) {
			jbtnContextHelp = new JButton();
			jbtnContextHelp.setIcon(new ImageIcon(getClass().getResource("/images/help.gif")));
			jbtnContextHelp.setBounds(new Rectangle(388, 136, 33, 26));
		}
		return jbtnContextHelp;
	}


	/**
	 * This method initializes jbtnTutorials	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnTutorials() {
		if (jbtnTutorials == null) {
			jbtnTutorials = new JButton();
			jbtnTutorials.setText("Tutorials");
			jbtnTutorials.setForeground(Color.blue);
			jbtnTutorials.setBounds(new Rectangle(288, 136, 93, 26));
		}
		return jbtnTutorials;
	}
}  //  @jve:decl-index=0:visual-constraint="-259,126"
