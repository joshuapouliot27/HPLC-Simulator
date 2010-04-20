package org.hplcsimulator;

import org.hplcsimulator.panels.*;

import javax.swing.JPanel;

import java.awt.BorderLayout;
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

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.help.*;

import org.jdesktop.swingx.*;

public class TopPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public GraphControl m_GraphControl = null;
	private JScrollPane jScrollPane = null;
	public JXTaskPaneContainer taskpanecontainer = null;
	public JXPanel jControlPanel = null;
	public JTable jtableChemicals = null;
	public JButton jbtnAddChemical = null;
	public JButton jbtnEditChemical = null;
	public JButton jbtnRemoveChemical = null;
	public DefaultTableModel tabModel;
	
	public Vector<String> vectColumnNames = new Vector<String>();
	public Vector<Vector<String>> vectChemicalRows = new Vector<Vector<String>>();  //  @jve:decl-index=0:
	
	private JPanel jpanelCompounds = null;
	public JToggleButton jbtnAutoscale = null;
	public JToggleButton jbtnZoomIn = null;
	public JToggleButton jbtnZoomOut = null;
	public JToggleButton jbtnPan = null;
	private JPanel jpanelSimulatedChromatogram = null;
	public JToggleButton jbtnAutoscaleX = null;
	public JToggleButton jbtnAutoscaleY = null;
	public JButton jbtnHelp = null;
	public JButton jbtnContextHelp = null;
	public JButton jbtnTutorials = null;
	
	public ChromatographyProperties jxpanelChromatographyProperties = null;
	public ColumnProperties jxpanelColumnProperties = null;
	public GeneralProperties jxpanelGeneralProperties = null;
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
		this.setBounds(new Rectangle(0, 0, 900, 650));
		this.setBackground(Color.white);
		
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        
		m_GraphControl = new GraphControl(caps);
		m_GraphControl.setBounds(new Rectangle(3, 16, 606, 421));
		
	    this.add(getJpanelCompounds(), null);
	    this.add(getJpanelSimulatedChromatogram(), null);
	    this.add(getControlPanel(), null);

	    CSH.setHelpIDString(jxpanelChromatographyProperties, "controls.chromatography");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jtxtOrganicFraction, "controls.meohfraction");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblOrganicFraction, "controls.meohfraction");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jsliderOrganicFraction, "controls.meohfraction");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jtxtTemp, "controls.temperature");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblTemperature, "controls.temperature");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jsliderTemp, "controls.temperature");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblInjectionVolume, "controls.injectionvolume");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jtxtInjectionVolume, "controls.injectionvolume");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblInjectionVolume2, "controls.injectionvolume");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblFlowRate, "controls.flowrate");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jtxtFlowRate, "controls.flowrate");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblFlowRate2, "controls.flowrate");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblFlowVelocity, "controls.flowvelocity");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblFlowVelocity2, "controls.flowvelocity");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblFlowVelocity3, "controls.flowvelocity");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblHETP, "controls.hetp");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblHETP2, "controls.hetp");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblHETP3, "controls.hetp");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblTheoreticalPlates, "controls.theoreticalplates");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblTheoreticalPlates2, "controls.theoreticalplates");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblBackpressure, "controls.backpressure");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblBackpressure, "controls.backpressure");
	    CSH.setHelpIDString(jxpanelChromatographyProperties.jlblBackpressure, "controls.backpressure");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblEluentViscosity, "controls.eluentviscosity");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblEluentViscosity2, "controls.eluentviscosity");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblEluentViscosity3, "controls.eluentviscosity");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblDiffusionCoefficient, "controls.diffusioncoefficient");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblDiffusionCoefficient2, "controls.diffusioncoefficient");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblDiffusionCoefficient3, "controls.diffusioncoefficient");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jtxtTimeConstant, "controls.timeconstant");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblTimeConstant, "controls.timeconstant");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblTimeConstant2, "controls.timeconstant");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jtxtSignalOffset, "controls.signaloffset");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblSignalOffset, "controls.signaloffset");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblSignalOffset2, "controls.signaloffset");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jtxtNoise, "controls.noise");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblNoise, "controls.noise");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblNoise2, "controls.noise");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jchkAutoTimeRange, "controls.automatictimespan");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jtxtInitialTime, "controls.initialtime");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblInitialTime, "controls.initialtime");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblInitialTime2, "controls.initialtime");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jtxtFinalTime, "controls.finaltime");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblFinalTime, "controls.finaltime");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblFinalTime2, "controls.finaltime");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jtxtNumPoints, "controls.numdatapoints");
	    CSH.setHelpIDString(jxpanelGeneralProperties.jlblNumPoints, "controls.numdatapoints");
	    CSH.setHelpIDString(jxpanelColumnProperties.jtxtColumnLength, "controls.columnlength");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblColumnLength, "controls.columnlength");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblColumnLength2, "controls.columnlength");
	    CSH.setHelpIDString(jxpanelColumnProperties.jtxtColumnDiameter, "controls.columndiameter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblColumnDiameter, "controls.columndiameter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblColumnDiameter2, "controls.columndiameter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jtxtParticleSize, "controls.particlesize");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblParticleSize, "controls.particlesize");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblParticleSize2, "controls.particlesize");
	    CSH.setHelpIDString(jxpanelColumnProperties.jtxtVoidFraction, "controls.voidfraction");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVoidFraction, "controls.voidfraction");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVoidVolume, "controls.voidvolume");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVoidVolume2, "controls.voidvolume");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVoidVolume3, "controls.voidvolume");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVoidTime, "controls.voidtime");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVoidTime2, "controls.voidtime");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVoidTime3, "controls.voidtime");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblVanDeemter, "controls.vandeemter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblATerm, "controls.vandeemter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jtxtATerm, "controls.vandeemter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblBTerm, "controls.vandeemter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jtxtBTerm, "controls.vandeemter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblCTerm, "controls.vandeemter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jtxtCTerm, "controls.vandeemter");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblReducedPlateHeight, "controls.reducedplateheight");
	    CSH.setHelpIDString(jxpanelColumnProperties.jlblReducedPlateHeight2, "controls.reducedplateheight");
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(12, 20, 593, 105));
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
			jtableChemicals.getColumnModel().getColumn(0).setPreferredWidth(170);
			jtableChemicals.getColumnModel().getColumn(1).setPreferredWidth(85);
			jtableChemicals.getColumnModel().getColumn(2).setPreferredWidth(80);
			jtableChemicals.getColumnModel().getColumn(3).setPreferredWidth(80);
			jtableChemicals.getColumnModel().getColumn(4).setPreferredWidth(80);
			jtableChemicals.getColumnModel().getColumn(5).setPreferredWidth(80);
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
			jbtnAddChemical.setLocation(new Point(12, 132));
			jbtnAddChemical.setSize(new Dimension(81, 25));
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
			jbtnEditChemical.setBounds(new Rectangle(100, 132, 81, 25));
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
			jbtnRemoveChemical.setBounds(new Rectangle(188, 132, 81, 25));
			jbtnRemoveChemical.setText("Remove");
		}
		return jbtnRemoveChemical;
	}

	/**
	 * This method initializes jbtnAutoscale	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnAutoscale() {
		if (jbtnAutoscale == null) {
			jbtnAutoscale = new JToggleButton();
			jbtnAutoscale.setIcon(new ImageIcon(getClass().getResource("/org/hplcsimulator/images/autoscale.png")));
			jbtnAutoscale.setRolloverEnabled(false);
			jbtnAutoscale.setSelected(true);
			jbtnAutoscale.setText("Autoscale");
			jbtnAutoscale.setBounds(new Rectangle(12, 444, 112, 25));
			jbtnAutoscale.setToolTipText("");
		}
		return jbtnAutoscale;
	}

	/**
	 * This method initializes jbtnAutoscaleX	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnAutoscaleX() 
	{
		if (jbtnAutoscaleX == null) {
			jbtnAutoscaleX = new JToggleButton();
			jbtnAutoscaleX.setIcon(new ImageIcon(getClass().getResource("/org/hplcsimulator/images/autoscaleX.png")));
			jbtnAutoscaleX.setRolloverEnabled(false);
			jbtnAutoscaleX.setSelected(true);
			jbtnAutoscaleX.setText("");
			jbtnAutoscaleX.setActionCommand("Autoscale X");
			jbtnAutoscaleX.setBounds(new Rectangle(132, 444, 32, 25));
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
			jbtnAutoscaleY.setActionCommand("Autoscale Y");
			jbtnAutoscaleY.setIcon(new ImageIcon(getClass().getResource("/org/hplcsimulator/images/autoscaleY.png")));
			jbtnAutoscaleY.setRolloverEnabled(false);
			jbtnAutoscaleY.setSelected(true);
			jbtnAutoscaleY.setText("");
			jbtnAutoscaleY.setBounds(new Rectangle(172, 444, 32, 25));
			jbtnAutoscaleY.setToolTipText("");
		}
		return jbtnAutoscaleY;
	}

	/**
	 * This method initializes jbtnZoomIn	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnZoomIn() {
		if (jbtnZoomIn == null) {
			jbtnZoomIn = new JToggleButton();
			jbtnZoomIn.setIcon(new ImageIcon(getClass().getResource("/org/hplcsimulator/images/zoomin.png")));
			jbtnZoomIn.setRolloverEnabled(false);
			jbtnZoomIn.setSelected(false);
			jbtnZoomIn.setText("Zoom in");
			jbtnZoomIn.setBounds(new Rectangle(372, 444, 112, 25));
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
			jbtnZoomOut.setIcon(new ImageIcon(getClass().getResource("/org/hplcsimulator/images/zoomout.png")));
			jbtnZoomOut.setRolloverEnabled(false);
			jbtnZoomOut.setSelected(false);
			jbtnZoomOut.setText("Zoom out");
			jbtnZoomOut.setLocation(new Point(492, 444));
			jbtnZoomOut.setSize(new Dimension(112, 25));
			jbtnZoomOut.setToolTipText("");
		}
		return jbtnZoomOut;
	}

	/**
	 * This method initializes jbtnPan	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJbtnPan() 
	{
		if (jbtnPan == null) 
		{
			jbtnPan = new JToggleButton();
			jbtnPan.setIcon(new ImageIcon(getClass().getResource("/org/hplcsimulator/images/pan.png")));
			jbtnPan.setRolloverEnabled(false);
			jbtnPan.setSelected(true);
			jbtnPan.setText("Pan");
			jbtnPan.setBounds(new Rectangle(252, 444, 112, 25));
			jbtnPan.setToolTipText("");
		}
		return jbtnPan;
	}

	/**
	 * This method initializes jpanelCompounds	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelCompounds() 
	{
		if (jpanelCompounds == null) {
			jpanelCompounds = new JPanel();
			jpanelCompounds.setLayout(null);
			jpanelCompounds.setBorder(BorderFactory.createTitledBorder(null, "Compounds", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelCompounds.setBackground(Color.white);
			jpanelCompounds.setBounds(new Rectangle(284, 480, 616, 170));
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
	 * This method initializes jSimulatedChromatogram	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelSimulatedChromatogram() 
	{
		if (jpanelSimulatedChromatogram == null) 
		{
			jpanelSimulatedChromatogram = new JPanel();
			jpanelSimulatedChromatogram.setLayout(null);
			jpanelSimulatedChromatogram.setBounds(new Rectangle(284, 0, 616, 481));
			jpanelSimulatedChromatogram.setBorder(BorderFactory.createTitledBorder(null, "Simulated Chromatogram", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelSimulatedChromatogram.setBackground(Color.white);
			jpanelSimulatedChromatogram.setPreferredSize(new Dimension(615, 477));
			
			jpanelSimulatedChromatogram.add(getJbtnAutoscale(), null);
			jpanelSimulatedChromatogram.add(getJbtnPan(), null);
			jpanelSimulatedChromatogram.add(getJbtnAutoscaleX(), null);
			jpanelSimulatedChromatogram.add(getJbtnAutoscaleY(), null);
			jpanelSimulatedChromatogram.add(m_GraphControl, null);
			jpanelSimulatedChromatogram.add(getJbtnZoomOut(), null);
			jpanelSimulatedChromatogram.add(getJbtnZoomIn(), null);
		}
		return jpanelSimulatedChromatogram;
	}

	/**
	 * This method initializes jControlPanel	
	 * 	
	 * @return org.desktop.swingx.JXPanel	
	 */
	private JXPanel getControlPanel() 
	{
		if (jControlPanel == null) 
		{
			jControlPanel = new JXPanel();
		    jControlPanel.setLayout(new BorderLayout());
		    jControlPanel.setBackground(Color.white);
		    jControlPanel.setLocation(new Point(0, 0));
		    jControlPanel.setSize(new Dimension(280, 650));
		    jControlPanel.setBorder(BorderFactory.createTitledBorder(null, "Controls", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			
		    VerticalLayout verticalLayout = new VerticalLayout();
		    verticalLayout.setGap(0);
		    		    
		    // create a taskpanecontainer
		    taskpanecontainer = new JXTaskPaneContainer();
		    // create a taskpane, and set it's title and icon
		    taskpanecontainer.setBorder(null);
		    taskpanecontainer.setPaintBorderInsets(true);
		    taskpanecontainer.setLayout(verticalLayout);
		    
		    JXTaskPane taskpane = new JXTaskPane();
		    taskpane.setAnimated(false);
		    taskpane.setTitle("Chromatographic Properties");
		    taskpane.getContentPane().setBackground(new Color(255,255,255));
		    ((JComponent)taskpane.getContentPane()).setBorder(null);
		    
		    jxpanelChromatographyProperties = new ChromatographyProperties();
		    jxpanelChromatographyProperties.setPreferredSize(new Dimension(254,281));
		    taskpane.add(jxpanelChromatographyProperties);
		    
		    JXTaskPane taskpane2 = new JXTaskPane();
		    taskpane2.setAnimated(false);
		    taskpane2.setTitle("General Properties");
		    taskpane2.getContentPane().setBackground(new Color(255, 255, 255));
		    ((JComponent)taskpane2.getContentPane()).setBorder(null);

		    jxpanelGeneralProperties = new GeneralProperties();
		    jxpanelGeneralProperties.setPreferredSize(new Dimension(254, 190));
		    taskpane2.add(jxpanelGeneralProperties, BorderLayout.NORTH);
		    
		    JXTaskPane taskpane3 = new JXTaskPane();
		    taskpane3.setAnimated(false);
		    taskpane3.setTitle("Column Properties");
		    taskpane3.getContentPane().setBackground(new Color(255,255,255));
		    ((JComponent)taskpane3.getContentPane()).setBorder(null);

		    jxpanelColumnProperties = new ColumnProperties();
		    jxpanelColumnProperties.setPreferredSize(new Dimension(254,232));
		    taskpane3.add(jxpanelColumnProperties);

		    // add the task pane to the taskpanecontainer
		    taskpanecontainer.add(taskpane, null);
		    taskpanecontainer.add(taskpane2, null);
		    taskpanecontainer.add(taskpane3, null);
		    	
		    JScrollPane jsclControlPanel = new JScrollPane(taskpanecontainer);
		    jControlPanel.add(jsclControlPanel);
		}
		return jControlPanel;
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
			jbtnHelp.setBounds(new Rectangle(536, 132, 69, 25));
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
			jbtnContextHelp.setIcon(new ImageIcon(getClass().getResource("/org/hplcsimulator/images/help.gif")));
			jbtnContextHelp.setSize(new Dimension(33, 25));
			jbtnContextHelp.setLocation(new Point(496, 132));
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
			jbtnTutorials.setBounds(new Rectangle(396, 132, 93, 25));
		}
		return jbtnTutorials;
	}
}  //  @jve:decl-index=0:visual-constraint="-259,126"
