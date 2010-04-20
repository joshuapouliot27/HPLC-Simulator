package org.hplcsimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Vector;

import javax.help.CSH;
import javax.help.HelpSet;
import javax.swing.JApplet;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Compound
{
	String strCompoundName;
	double dConcentration;
	double dLogkwvsTSlope;
	double dLogkwvsTIntercept;
	double dSvsTSlope;
	double dSvsTIntercept;
	double dMolarVolume;
	
	double dRetentionTime;
	double dSigma;
	double dW;
	int iCompoundIndex;

	static public int getCompoundNum()
	{
		return 22;
	}
	
	public boolean loadCompoundInfo(int iIndex, int iOrganicModifier)
	{
		iCompoundIndex = iIndex;
		
		strCompoundName = Globals.CompoundNameArray[iIndex];
		dLogkwvsTSlope = Globals.LSSTDataArray[iIndex][iOrganicModifier][0];
		dLogkwvsTIntercept = Globals.LSSTDataArray[iIndex][iOrganicModifier][1];
		dSvsTSlope = Globals.LSSTDataArray[iIndex][iOrganicModifier][2];
		dSvsTIntercept = Globals.LSSTDataArray[iIndex][iOrganicModifier][3];
		dMolarVolume = Globals.MolarVolumeArray[iIndex];
		
		return true;
	}
}

public class HPLCSimulatorApplet extends JApplet implements ActionListener, ChangeListener, KeyListener, FocusListener, ListSelectionListener, AutoScaleListener
{
	private static final long serialVersionUID = 1L;

	private boolean m_bSliderUpdate;

	TopPanel contentPane = null;
	public double m_dTemperature = 25;
	public double m_dMeOHFraction = 0.5;
	public double m_dColumnLength = 100;
	public double m_dColumnDiameter = 4.6;
	public double m_dVoidFraction = 0.6;
	public double m_dFlowRate = 2;
	public double m_dVoidVolume;
	public double m_dVoidTime;
	public double m_dFlowVelocity;
	public double m_dParticleSize = 5;
	public double m_dDiffusionCoefficient = 0.00001;
	public double m_dATerm = 1;
	public double m_dBTerm = 5;
	public double m_dCTerm = 0.05;
	public double m_dMu;
	public double m_dReducedPlateHeight;
	public double m_dTheoreticalPlates;
	public double m_dHETP;
	public double m_dInjectionVolume = 5;
	public double m_dTimeConstant = 0.5;
	public double m_dStartTime = 0;
	public double m_dEndTime = 0;
	public double m_dNoise = 3;
	public double m_dSignalOffset = 30;
	public int m_iNumPoints = 3000;
	public double m_dEluentViscosity = 1;
	public double m_dBackpressure = 400;
	public int m_iOrganicModifier = 0; // 0 = Acetonitrile, 1 = Methanol
	
	public Vector<Compound> m_vectCompound = new Vector<Compound>();
	/**
	 * This is the xxx default constructor
	 */
	public HPLCSimulatorApplet() 
	{
	    super();
	    
		/*try {
	        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        UIManager.setLookAndFeel("org.jdesktop.swingx.plaf.nimbus");
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }*/

		this.setPreferredSize(new Dimension(900, 650));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() 
	{
        // Load the JavaHelp
		String helpHS = "org/hplcsimulator/help/HPLCSimHelp.hs";
		ClassLoader cl = TopPanel.class.getClassLoader();
		try {
			URL hsURL = HelpSet.findHelpSet(cl, helpHS);
			Globals.hsMainHelpSet = new HelpSet(null, hsURL);
		} catch (Exception ee) {
			System.out.println( "HelpSet " + ee.getMessage());
			System.out.println("HelpSet "+ helpHS +" not found");
			return;
		}
		Globals.hbMainHelpBroker = Globals.hsMainHelpSet.createHelpBroker();

		//Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
        //    SwingUtilities.invokeAndWait(new Runnable() 
        //    {
        //        public void run() {
                	createGUI();
        //        }
        //    });
        } catch (Exception e) { 
            System.err.println("createGUI didn't complete successfully");
            System.err.println(e.getMessage());
            System.err.println(e.getLocalizedMessage());
            System.err.println(e.toString());
            System.err.println(e.getStackTrace());
            System.err.println(e.getCause());
        }
        
    	Compound compound1 = new Compound();
    	compound1.loadCompoundInfo(2, m_iOrganicModifier);
    	compound1.dConcentration = 5;
    	this.m_vectCompound.add(compound1);
    	
    	Compound compound2 = new Compound();
    	compound2.loadCompoundInfo(3, m_iOrganicModifier);
    	compound2.dConcentration = 25;
    	this.m_vectCompound.add(compound2);
    	
    	Compound compound3 = new Compound();
    	compound3.loadCompoundInfo(4, m_iOrganicModifier);
    	compound3.dConcentration = 40;
    	this.m_vectCompound.add(compound3);

    	Compound compound4 = new Compound();
    	compound4.loadCompoundInfo(6, m_iOrganicModifier);
    	compound4.dConcentration = 15;
    	this.m_vectCompound.add(compound4);

    	Compound compound5 = new Compound();
    	compound5.loadCompoundInfo(11, m_iOrganicModifier);
    	compound5.dConcentration = 10;
    	this.m_vectCompound.add(compound5);

    	for (int i = 0; i < m_vectCompound.size(); i++)
    	{
        	// Add the table space for the compound. Fill it in later with performCalculations().
        	Vector<String> vectNewRow = new Vector<String>();
        	vectNewRow.add(m_vectCompound.get(i).strCompoundName);
        	vectNewRow.add(Float.toString((float)m_vectCompound.get(i).dConcentration));
        	vectNewRow.add("");
        	vectNewRow.add("");
        	vectNewRow.add("");
        	vectNewRow.add("");
        	vectNewRow.add("");
        	
        	contentPane.vectChemicalRows.add(vectNewRow);    		
    	}
    	
        performCalculations();
    }
    
    private void createGUI()
    {
        //Create and set up the content pane.
        contentPane = new TopPanel();
        contentPane.setOpaque(true); 
        setContentPane(contentPane);  
        
        contentPane.jbtnAddChemical.addActionListener(this);
        contentPane.jbtnEditChemical.addActionListener(this);
        contentPane.jbtnRemoveChemical.addActionListener(this);
        contentPane.jxpanelChromatographyProperties.jcboOrganicModifier.addActionListener(this);
        contentPane.jxpanelChromatographyProperties.jsliderTemp.addChangeListener(this);
        contentPane.jxpanelChromatographyProperties.jsliderOrganicFraction.addChangeListener(this);
        contentPane.jxpanelChromatographyProperties.jtxtTemp.addKeyListener(this);
        contentPane.jxpanelChromatographyProperties.jtxtTemp.addFocusListener(this);
        contentPane.jxpanelChromatographyProperties.jtxtOrganicFraction.addKeyListener(this);
        contentPane.jxpanelChromatographyProperties.jtxtOrganicFraction.addFocusListener(this);
        contentPane.jxpanelColumnProperties.jtxtColumnLength.addKeyListener(this);
        contentPane.jxpanelColumnProperties.jtxtColumnLength.addFocusListener(this);
        contentPane.jxpanelColumnProperties.jtxtColumnDiameter.addKeyListener(this);
        contentPane.jxpanelColumnProperties.jtxtColumnDiameter.addFocusListener(this);
        contentPane.jxpanelColumnProperties.jtxtVoidFraction.addKeyListener(this);
        contentPane.jxpanelColumnProperties.jtxtVoidFraction.addFocusListener(this);        
        contentPane.jxpanelChromatographyProperties.jtxtFlowRate.addKeyListener(this);
        contentPane.jxpanelChromatographyProperties.jtxtFlowRate.addFocusListener(this);        
        contentPane.jxpanelColumnProperties.jtxtParticleSize.addKeyListener(this);
        contentPane.jxpanelColumnProperties.jtxtParticleSize.addFocusListener(this);        
        contentPane.jxpanelColumnProperties.jtxtATerm.addKeyListener(this);
        contentPane.jxpanelColumnProperties.jtxtATerm.addFocusListener(this);        
        contentPane.jxpanelColumnProperties.jtxtBTerm.addKeyListener(this);
        contentPane.jxpanelColumnProperties.jtxtBTerm.addFocusListener(this);        
        contentPane.jxpanelColumnProperties.jtxtCTerm.addKeyListener(this);
        contentPane.jxpanelColumnProperties.jtxtCTerm.addFocusListener(this);        
        contentPane.jxpanelChromatographyProperties.jtxtInjectionVolume.addKeyListener(this);
        contentPane.jxpanelChromatographyProperties.jtxtInjectionVolume.addFocusListener(this);        
        contentPane.jxpanelGeneralProperties.jtxtTimeConstant.addKeyListener(this);
        contentPane.jxpanelGeneralProperties.jtxtTimeConstant.addFocusListener(this);        
        contentPane.jxpanelGeneralProperties.jtxtNoise.addKeyListener(this);
        contentPane.jxpanelGeneralProperties.jtxtNoise.addFocusListener(this);        
        contentPane.jxpanelGeneralProperties.jtxtSignalOffset.addKeyListener(this);
        contentPane.jxpanelGeneralProperties.jtxtSignalOffset.addFocusListener(this);   
        contentPane.jxpanelGeneralProperties.jtxtInitialTime.addKeyListener(this);
        contentPane.jxpanelGeneralProperties.jtxtInitialTime.addFocusListener(this);   
        contentPane.jxpanelGeneralProperties.jtxtFinalTime.addKeyListener(this);
        contentPane.jxpanelGeneralProperties.jtxtFinalTime.addFocusListener(this);   
        contentPane.jxpanelGeneralProperties.jtxtNumPoints.addKeyListener(this);
        contentPane.jxpanelGeneralProperties.jtxtNumPoints.addFocusListener(this);   
        contentPane.jtableChemicals.getSelectionModel().addListSelectionListener(this);
        contentPane.jxpanelGeneralProperties.jchkAutoTimeRange.addActionListener(this);
        contentPane.jbtnPan.addActionListener(this);
        contentPane.jbtnZoomIn.addActionListener(this);
        contentPane.jbtnZoomOut.addActionListener(this);
        contentPane.jbtnAutoscale.addActionListener(this);
        contentPane.jbtnAutoscaleX.addActionListener(this);
        contentPane.jbtnAutoscaleY.addActionListener(this);
        contentPane.jbtnHelp.addActionListener(this);
        contentPane.jbtnTutorials.addActionListener(this);
        contentPane.m_GraphControl.addAutoScaleListener(this);
        
        contentPane.jbtnContextHelp.addActionListener(new CSH.DisplayHelpAfterTracking(Globals.hbMainHelpBroker));
    }

    private void validateTemp()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelChromatographyProperties.jtxtTemp.getText());
		dTemp = Math.floor(dTemp);
		
		if (dTemp < 10)
			dTemp = 10;
		if (dTemp > 150)
			dTemp = 150;
		
		m_dTemperature = dTemp;
		m_bSliderUpdate = false;
		contentPane.jxpanelChromatographyProperties.jsliderTemp.setValue((int)m_dTemperature);
		contentPane.jxpanelChromatographyProperties.jtxtTemp.setText(Integer.toString((int)m_dTemperature));    	
    }
    
    private void validateMeOHFraction()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelChromatographyProperties.jtxtOrganicFraction.getText());
		dTemp = Math.floor(dTemp);
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > 100)
			dTemp = 100;
		
		this.m_dMeOHFraction = dTemp / 100;
		m_bSliderUpdate = false;
		contentPane.jxpanelChromatographyProperties.jsliderOrganicFraction.setValue((int)(m_dMeOHFraction * 100));
		contentPane.jxpanelChromatographyProperties.jtxtOrganicFraction.setText(Integer.toString((int)(m_dMeOHFraction * 100)));    	
    }    
    
    private void validateColumnLength()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelColumnProperties.jtxtColumnLength.getText());
		
		if (dTemp < .01)
			dTemp = .01;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dColumnLength = dTemp;
		contentPane.jxpanelColumnProperties.jtxtColumnLength.setText(Float.toString((float)m_dColumnLength));    	
    }    

    private void validateColumnDiameter()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelColumnProperties.jtxtColumnDiameter.getText());
		
		if (dTemp < .001)
			dTemp = .001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dColumnDiameter = dTemp;
		contentPane.jxpanelColumnProperties.jtxtColumnDiameter.setText(Float.toString((float)m_dColumnDiameter));    	
    }    

    private void validateVoidFraction()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelColumnProperties.jtxtVoidFraction.getText());
		
		if (dTemp < .001)
			dTemp = .001;
		if (dTemp > .999)
			dTemp = .999;
		
		this.m_dVoidFraction = dTemp;
		contentPane.jxpanelColumnProperties.jtxtVoidFraction.setText(Float.toString((float)m_dVoidFraction));    	
    }    

    private void validateFlowRate()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelChromatographyProperties.jtxtFlowRate.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dFlowRate = dTemp;
		contentPane.jxpanelChromatographyProperties.jtxtFlowRate.setText(Float.toString((float)m_dFlowRate));    	
    }    

    private void validateParticleSize()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelColumnProperties.jtxtParticleSize.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dParticleSize = dTemp;
		contentPane.jxpanelColumnProperties.jtxtParticleSize.setText(Float.toString((float)m_dParticleSize));    	
    }    

    private void validateATerm()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelColumnProperties.jtxtATerm.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dATerm = dTemp;
		contentPane.jxpanelColumnProperties.jtxtATerm.setText(Float.toString((float)m_dATerm));    	
    }    

    private void validateBTerm()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelColumnProperties.jtxtBTerm.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dBTerm = dTemp;
		contentPane.jxpanelColumnProperties.jtxtBTerm.setText(Float.toString((float)m_dBTerm));    	
    } 
    
    private void validateCTerm()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelColumnProperties.jtxtCTerm.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dCTerm = dTemp;
		contentPane.jxpanelColumnProperties.jtxtCTerm.setText(Float.toString((float)m_dCTerm));    	
    } 

    private void validateInjectionVolume()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelChromatographyProperties.jtxtInjectionVolume.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dInjectionVolume = dTemp;
		contentPane.jxpanelChromatographyProperties.jtxtInjectionVolume.setText(Float.toString((float)m_dInjectionVolume));    	
    } 

    private void validateTimeConstant()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelGeneralProperties.jtxtTimeConstant.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dTimeConstant = dTemp;
		contentPane.jxpanelGeneralProperties.jtxtTimeConstant.setText(Float.toString((float)m_dTimeConstant));    	
    } 

    private void validateNoise()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelGeneralProperties.jtxtNoise.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dNoise = dTemp;
		contentPane.jxpanelGeneralProperties.jtxtNoise.setText(Float.toString((float)m_dNoise));    	
    } 

    private void validateSignalOffset()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelGeneralProperties.jtxtSignalOffset.getText());
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dSignalOffset = dTemp;
		contentPane.jxpanelGeneralProperties.jtxtSignalOffset.setText(Float.toString((float)m_dSignalOffset));    	
    } 

    private void validateStartTime()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelGeneralProperties.jtxtInitialTime.getText());
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > m_dEndTime)
			dTemp = m_dEndTime - .000001;
		
		this.m_dStartTime = dTemp;
		contentPane.jxpanelGeneralProperties.jtxtInitialTime.setText(Float.toString((float)m_dStartTime));    	
    } 

    private void validateEndTime()
    {
		double dTemp = (double)Float.valueOf(contentPane.jxpanelGeneralProperties.jtxtFinalTime.getText());
		
		if (dTemp < m_dStartTime)
			dTemp = m_dStartTime + .000001;
		if (dTemp > 99999999)
			dTemp = 99999999;
		
		this.m_dEndTime = dTemp;
		contentPane.jxpanelGeneralProperties.jtxtFinalTime.setText(Float.toString((float)m_dEndTime));    	
    } 

    private void validateNumPoints()
    {
		int iTemp = Integer.valueOf(contentPane.jxpanelGeneralProperties.jtxtNumPoints.getText());
		
		if (iTemp < 2)
			iTemp = 2;
		if (iTemp > 10000000)
			iTemp = 10000000;
		
		this.m_iNumPoints = iTemp;
		contentPane.jxpanelGeneralProperties.jtxtNumPoints.setText(Integer.toString(m_iNumPoints));    	
    } 

    public void actionPerformed(ActionEvent evt) 
	{
	    String strActionCommand = evt.getActionCommand();
	    if (strActionCommand == "Add Chemical")
	    {
	    	Frame[] frames = Frame.getFrames();
	    	ChemicalDialog dlgChemical = new ChemicalDialog(frames[0], false, m_iOrganicModifier);
	    	
	    	// Make a list of the chemical indices already used
	    	for (int i = 0; i < m_vectCompound.size(); i++)
	    	{
	    		Integer k = new Integer(m_vectCompound.get(i).iCompoundIndex);
	    		dlgChemical.m_vectCompoundsUsed.add(k);
	    	}
	    	
	    	// Show the dialog.
	    	dlgChemical.setVisible(true);
	    	
	    	if (dlgChemical.m_bOk == false)
	    		return;
	    	
	    	// Add the compound properties to the m_vectCompound array
	    	Compound newCompound = new Compound();
	    	newCompound.strCompoundName = dlgChemical.m_strCompoundName;
	    	newCompound.dConcentration = dlgChemical.m_dConcentration;
	    	newCompound.dLogkwvsTSlope = dlgChemical.m_dLogkwvsTSlope;
	    	newCompound.dLogkwvsTIntercept = dlgChemical.m_dLogkwvsTIntercept;
	    	newCompound.dSvsTSlope = dlgChemical.m_dSvsTSlope;
	    	newCompound.dSvsTIntercept = dlgChemical.m_dSvsTIntercept;
	    	newCompound.dMolarVolume = dlgChemical.m_dMolarVolume;
	    	newCompound.iCompoundIndex = dlgChemical.m_iCompound;
	    	
	    	this.m_vectCompound.add(newCompound);
	    	
	    	// Add the table space for the compound. Fill it in later with performCalculations().
	    	Vector<String> vectNewRow = new Vector<String>();
	    	vectNewRow.add(dlgChemical.m_strCompoundName);
	    	vectNewRow.add(Float.toString((float)dlgChemical.m_dConcentration));
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	
	    	contentPane.vectChemicalRows.add(vectNewRow);
	    	
	    	performCalculations();
	    }
	    else if (strActionCommand == "Edit Chemical")
	    {
	    	int iRowSel = contentPane.jtableChemicals.getSelectedRow();
	    	if (iRowSel < 0 || iRowSel >= contentPane.vectChemicalRows.size())
	    		return;

	    	Frame[] frames = Frame.getFrames();
	    	ChemicalDialog dlgChemical = new ChemicalDialog(frames[0], true, m_iOrganicModifier);
	    	
	    	dlgChemical.setSelectedCompound(this.m_vectCompound.get(iRowSel).iCompoundIndex);
	    	dlgChemical.setCompoundConcentration(this.m_vectCompound.get(iRowSel).dConcentration);
	    	
	    	// Make a list of the chemical indices already used
	    	for (int i = 0; i < m_vectCompound.size(); i++)
	    	{
	    		// Don't add the currently selected row to the list
	    		if (i == iRowSel)
	    			continue;
	    		
	    		Integer k = new Integer(m_vectCompound.get(i).iCompoundIndex);
	    		dlgChemical.m_vectCompoundsUsed.add(k);
	    	}
	    	
	    	// Show the dialog.
	    	dlgChemical.setVisible(true);
	    	
	    	if (dlgChemical.m_bOk == false)
	    		return;
	    	
	    	// Add the compound properties to the m_vectCompound array
	    	Compound newCompound = new Compound();
	    	newCompound.strCompoundName = dlgChemical.m_strCompoundName;
	    	newCompound.dConcentration = dlgChemical.m_dConcentration;
	    	newCompound.dLogkwvsTSlope = dlgChemical.m_dLogkwvsTSlope;
	    	newCompound.dLogkwvsTIntercept = dlgChemical.m_dLogkwvsTIntercept;
	    	newCompound.dSvsTSlope = dlgChemical.m_dSvsTSlope;
	    	newCompound.dSvsTIntercept = dlgChemical.m_dSvsTIntercept;
	    	newCompound.dMolarVolume = dlgChemical.m_dMolarVolume;
	    	newCompound.iCompoundIndex = dlgChemical.m_iCompound;
	    	
	    	this.m_vectCompound.set(iRowSel, newCompound);
	    	
	    	// Add the table space for the compound. Fill it in later with performCalculations().
	    	Vector<String> vectNewRow = new Vector<String>();
	    	vectNewRow.add(dlgChemical.m_strCompoundName);
	    	vectNewRow.add(Float.toString((float)dlgChemical.m_dConcentration));
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	vectNewRow.add("");
	    	
	    	contentPane.vectChemicalRows.set(iRowSel, vectNewRow);
	    	
	    	performCalculations();
	    }
	    else if (strActionCommand == "Remove Chemical")
	    {
	    	int iRowSel = contentPane.jtableChemicals.getSelectedRow();
	    	if (iRowSel < 0 || iRowSel >= contentPane.vectChemicalRows.size())
	    		return;
	    	
	    	contentPane.vectChemicalRows.remove(iRowSel);
	    	contentPane.jtableChemicals.addNotify();
	    	
	    	if (iRowSel >= m_vectCompound.size())
	    		return;
	    	
	    	m_vectCompound.remove(iRowSel);	    		
	    	
	    	performCalculations();
	    }
	    else if (strActionCommand == "Automatically determine time span")
	    {
	    	if (contentPane.jxpanelGeneralProperties.jchkAutoTimeRange.isSelected() == true)
	    	{
	    		contentPane.jxpanelGeneralProperties.jtxtInitialTime.setEnabled(false);
	    		contentPane.jxpanelGeneralProperties.jtxtFinalTime.setEnabled(false);
	    	}
	    	else
	    	{
	    		contentPane.jxpanelGeneralProperties.jtxtInitialTime.setEnabled(true);
	    		contentPane.jxpanelGeneralProperties.jtxtFinalTime.setEnabled(true);	    		
	    	}
	    	
	    	performCalculations();
	    }
	    else if (strActionCommand == "Autoscale")
	    {
	    	if (contentPane.jbtnAutoscale.isSelected() == true)
	    	{
	    		contentPane.m_GraphControl.setAutoScaleX(true);
	    		contentPane.m_GraphControl.setAutoScaleY(true);
	    		contentPane.m_GraphControl.repaint();
	    	}
	    	else
	    	{
	    		contentPane.m_GraphControl.setAutoScaleX(false);
    			contentPane.m_GraphControl.setAutoScaleY(false);
	    	}
	    }
	    else if (strActionCommand == "Autoscale X")
	    {
	    	if (contentPane.jbtnAutoscaleX.isSelected() == true)
	    	{
	    		contentPane.m_GraphControl.setAutoScaleX(true);
	    		contentPane.m_GraphControl.repaint();
	    	}
	    	else
	    	{
	    		contentPane.m_GraphControl.setAutoScaleX(false);
	    	}
	    }
	    else if (strActionCommand == "Autoscale Y")
	    {
	    	if (contentPane.jbtnAutoscaleY.isSelected() == true)
	    	{
	    		contentPane.m_GraphControl.setAutoScaleY(true);
	    		contentPane.m_GraphControl.repaint();
	    	}
	    	else
	    	{
	    		contentPane.m_GraphControl.setAutoScaleY(false);
	    	}
	    }
	    else if (strActionCommand == "Pan")
	    {
	    	contentPane.jbtnPan.setSelected(true);
	    	contentPane.jbtnZoomIn.setSelected(false);
	    	contentPane.jbtnZoomOut.setSelected(false);
	    	contentPane.m_GraphControl.selectPanMode();
	    }
	    else if (strActionCommand == "Zoom in")
	    {
	    	contentPane.jbtnPan.setSelected(false);
	    	contentPane.jbtnZoomIn.setSelected(true);
	    	contentPane.jbtnZoomOut.setSelected(false);	    	
	    	contentPane.m_GraphControl.selectZoomInMode();
	    }
	    else if (strActionCommand == "Zoom out")
	    {
	    	contentPane.jbtnPan.setSelected(false);
	    	contentPane.jbtnZoomIn.setSelected(false);
	    	contentPane.jbtnZoomOut.setSelected(true);	    		    	
	    	contentPane.m_GraphControl.selectZoomOutMode();
	    }
	    else if (strActionCommand == "Help")
	    {
			Globals.hbMainHelpBroker.setCurrentID("getting_started");
			Globals.hbMainHelpBroker.setDisplayed(true);
	    }
	    else if (strActionCommand == "Tutorials")
	    {
			Globals.hbMainHelpBroker.setCurrentID("tutorials");
			Globals.hbMainHelpBroker.setDisplayed(true);
	    }
	    else if (strActionCommand == "OrganicModifierComboBoxChanged")
	    {
	    	m_iOrganicModifier = contentPane.jxpanelChromatographyProperties.jcboOrganicModifier.getSelectedIndex();
	    	// Change the organic modifier fraction label 
	    	String strLabel = "";
	    	strLabel += Globals.OrganicModifierArray[m_iOrganicModifier];
	    	strLabel += " fraction (% v/v):";
	    	contentPane.jxpanelChromatographyProperties.jlblOrganicFraction.setText(strLabel);
	    	
	    	// Change all the Compound information
	    	for (int i = 0; i < m_vectCompound.size(); i++)
	    	{
	    		m_vectCompound.get(i).loadCompoundInfo(m_vectCompound.get(i).iCompoundIndex, m_iOrganicModifier);
	    	}
	    	
	    	// Update all the indicators
	    	performCalculations();
	    }
	}

	//@Override
	public void stateChanged(ChangeEvent e) 
	{
		JSlider source = (JSlider)e.getSource();
		if (source.getName() == "Temperature Slider")
		{
			if (m_bSliderUpdate == false)
			{
				m_bSliderUpdate = true;
				return;
			}
			
			m_dTemperature = contentPane.jxpanelChromatographyProperties.jsliderTemp.getValue();
			contentPane.jxpanelChromatographyProperties.jtxtTemp.setText(Integer.toString((int)m_dTemperature));
			performCalculations();
		}
		else if (source.getName() == "Methanol Fraction Slider")
		{
			if (m_bSliderUpdate == false)
			{
				m_bSliderUpdate = true;
				return;
			}
			
			m_dMeOHFraction = ((double)contentPane.jxpanelChromatographyProperties.jsliderOrganicFraction.getValue() / (double)100);
			contentPane.jxpanelChromatographyProperties.jtxtOrganicFraction.setText(Integer.toString((int)(m_dMeOHFraction * 100)));		
			performCalculations();
		}
	}

	//@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void keyReleased(KeyEvent e) 
	{

	}

	//@Override
	public void keyTyped(KeyEvent e) 
	{
		//JTextField source = (JTextField)e.getSource();
		
		if (!((Character.isDigit(e.getKeyChar()) ||
				(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ||
				(e.getKeyChar() == KeyEvent.VK_DELETE) ||
				(e.getKeyChar() == KeyEvent.VK_PERIOD))))
		{
	        e.consume();
		}
		
		if (e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			performCalculations();
		}
		
	}

	public void performCalculations()
	{
		NumberFormat formatter = new DecimalFormat("#0.0000");
		
		validateTemp();
		validateMeOHFraction();
		validateColumnLength();
		validateColumnDiameter();
		validateVoidFraction();
		validateFlowRate();
		validateParticleSize();
		validateATerm();
		validateBTerm();
		validateCTerm();
		validateInjectionVolume();
		validateTimeConstant();
		validateNoise();
		validateSignalOffset();
		validateStartTime();
		validateEndTime();
		validateNumPoints();
		
		m_dVoidVolume = Math.PI * Math.pow(((m_dColumnDiameter / 10) / 2), 2) * (m_dColumnLength / 10) * m_dVoidFraction;
		contentPane.jxpanelColumnProperties.jlblVoidVolume.setText(formatter.format(m_dVoidVolume));
		
		m_dVoidTime = (m_dVoidVolume / m_dFlowRate) * 60;
		contentPane.jxpanelColumnProperties.jlblVoidTime.setText(formatter.format(m_dVoidTime));
		
		m_dFlowVelocity = (m_dColumnLength / 10) / m_dVoidTime;
		contentPane.jxpanelChromatographyProperties.jlblFlowVelocity.setText(formatter.format(m_dFlowVelocity));
		
		double dTempKelvin = m_dTemperature + 273.15;

		// Calculate eluent viscosity
		if (m_iOrganicModifier == 0)
		{
			// This formula is for acetonitrile/water mixtures:
			// See Chen, H.; Horvath, C. Anal. Methods Instrum. 1993, 1, 213-222.
			m_dEluentViscosity = Math.exp((m_dMeOHFraction * (-3.476 + (726 / dTempKelvin))) + ((1 - m_dMeOHFraction) * (-5.414 + (1566 / dTempKelvin))) + (m_dMeOHFraction * (1 - m_dMeOHFraction) * (-1.762 + (929 / dTempKelvin))));
		}
		else if (m_iOrganicModifier == 1)
		{
			// This formula is for methanol/water mixtures:
			// Based on fit of data (at 1 bar) in Journal of Chromatography A, 1210 (2008) 30�44.
			m_dEluentViscosity = Math.exp((m_dMeOHFraction * (-4.597 + (1211 / dTempKelvin))) + ((1 - m_dMeOHFraction) * (-5.961 + (1736 / dTempKelvin))) + (m_dMeOHFraction * (1 - m_dMeOHFraction) * (-6.215 + (2809 / dTempKelvin))));
		}
		contentPane.jxpanelGeneralProperties.jlblEluentViscosity.setText(formatter.format(m_dEluentViscosity));
		
		// Calculate backpressure (in pascals) (Darcy equation)
		// See Bird, R. B.; Stewart, W. E.; Lightfoot, E. N. Transport Phenomena; Wiley & Sons: New York, 1960.
		m_dBackpressure = 500 * (m_dEluentViscosity / 1000) * (((m_dFlowVelocity / 100) * (m_dColumnLength / 1000)) / Math.pow(m_dParticleSize / 1000000, 2));
		NumberFormat bpFormatter = new DecimalFormat("#0.00");
		contentPane.jxpanelChromatographyProperties.jlblBackpressure.setText(bpFormatter.format(m_dBackpressure / 100000));
		
		// Calculate the average diffusion coefficient using Wilke-Chang empirical determination
		// See Wilke, C. R.; Chang, P. AICHE J. 1955, 1, 264-270.
		
		// First, determine association parameter
		double dAssociationParameter = ((1 - m_dMeOHFraction) * (2.6 - 1.9)) + 1.9;
		
		// Determine weighted average molecular weight of solvent
		double dSolventMW = (m_dMeOHFraction * (32 - 18)) + 18;
		
		// Determine the average molar volume
		double dAverageMolarVolume = 0;
		for (int i = 0; i < m_vectCompound.size(); i++)
		{
			dAverageMolarVolume += m_vectCompound.get(i).dMolarVolume;
		}
		dAverageMolarVolume = dAverageMolarVolume / m_vectCompound.size();
		
		// Now determine the average diffusion coefficient
		m_dDiffusionCoefficient = 0.000000074 * (Math.pow(dAssociationParameter * dSolventMW, 0.5) * dTempKelvin) / (m_dEluentViscosity * Math.pow(dAverageMolarVolume, 0.6));
		DecimalFormat df = new DecimalFormat("0.000E0");
		contentPane.jxpanelGeneralProperties.jlblDiffusionCoefficient.setText(df.format(m_dDiffusionCoefficient));
		
		m_dMu = ((m_dParticleSize / 10000) * m_dFlowVelocity) / m_dDiffusionCoefficient;
		
		m_dReducedPlateHeight = m_dATerm + (m_dBTerm / m_dMu) + (m_dCTerm * m_dMu);
		contentPane.jxpanelColumnProperties.jlblReducedPlateHeight.setText(formatter.format(m_dReducedPlateHeight));
    	
		m_dHETP = (m_dParticleSize / 10000) * m_dReducedPlateHeight;
		contentPane.jxpanelChromatographyProperties.jlblHETP.setText(df.format(m_dHETP));
		
		NumberFormat NFormatter = new DecimalFormat("#0");
		m_dTheoreticalPlates = (m_dColumnLength / 10) / m_dHETP;
		contentPane.jxpanelChromatographyProperties.jlblTheoreticalPlates.setText(NFormatter.format(m_dTheoreticalPlates));

		for (int i = 0; i < m_vectCompound.size(); i++)
		{
	    	// Calculate lnk'w1
	    	double lnkprimew1 = (m_vectCompound.get(i).dLogkwvsTSlope * this.m_dTemperature) + m_vectCompound.get(i).dLogkwvsTIntercept;
	    	// Calculate S1
	    	double S1 = -1 * ((m_vectCompound.get(i).dSvsTSlope * this.m_dTemperature) + m_vectCompound.get(i).dSvsTIntercept);
			// Calculate k'
	    	double kprime = Math.pow(10, lnkprimew1 - (S1 * this.m_dMeOHFraction));
	    	contentPane.vectChemicalRows.get(i).set(2, formatter.format(kprime));
	    	
	    	double dRetentionTime = m_dVoidTime * (1 + kprime);
	    	m_vectCompound.get(i).dRetentionTime = dRetentionTime;
	    	contentPane.vectChemicalRows.get(i).set(3, formatter.format(dRetentionTime));
	    	
	    	double dSigma = Math.sqrt(Math.pow(dRetentionTime / Math.sqrt(m_dTheoreticalPlates), 2) + Math.pow(m_dTimeConstant, 2) + Math.pow(0.017 * m_dInjectionVolume / m_dFlowRate, 2));
	    	m_vectCompound.get(i).dSigma = dSigma;	    	
	    	contentPane.vectChemicalRows.get(i).set(4, formatter.format(dSigma));
	    	
	    	double dW = m_dInjectionVolume * m_vectCompound.get(i).dConcentration;
	    	m_vectCompound.get(i).dW = dW;
	    	contentPane.vectChemicalRows.get(i).set(5, formatter.format(dW));
		}
		
    	// First calculate the time period we're going to be looking at:
    	
    	if (contentPane.jxpanelGeneralProperties.jchkAutoTimeRange.isSelected() == true)
    	{
	    	// Find the compound with the longest tR
	    	double dLongestRetentionTime = 0;
	    	
	    	for (int i = 0; i < m_vectCompound.size(); i++)
	    	{
	    		if (m_vectCompound.get(i).dRetentionTime > dLongestRetentionTime)
	    		{
	    			dLongestRetentionTime = m_vectCompound.get(i).dRetentionTime;
	    		}
	    	}
	    	
	    	m_dEndTime = dLongestRetentionTime * 1.1;
	    	
	    	contentPane.jxpanelGeneralProperties.jtxtFinalTime.setText(Float.toString((float)m_dEndTime));
	    	
	    	contentPane.jxpanelGeneralProperties.jtxtInitialTime.setText("0");
	    	
	    	m_dStartTime = 0;
    	}
    	
    	// Calculate each data point
    	Random random = new Random();
    	contentPane.m_GraphControl.RemoveAllSeries();
    	
    	if (this.m_vectCompound.size() > 0)
    	{
	    	int iTotalPlotIndex = contentPane.m_GraphControl.AddSeries("Plot", new Color(98, 101, 214), 1);
	    	int iSinglePlotIndex = -1;
	    	// Find if a chemical is selected
	    	int iRowSel = contentPane.jtableChemicals.getSelectedRow();
	    	if (iRowSel >= 0 && iRowSel < contentPane.vectChemicalRows.size())
	    	{
		    	iSinglePlotIndex = contentPane.m_GraphControl.AddSeries("Single", new Color(206, 70, 70), 1);	    		
	    	}
	    	
	    	for (int i = 0; i < this.m_iNumPoints; i++)
	    	{
	    		double dTime = m_dStartTime + (double)i * ((m_dEndTime - m_dStartTime) / (double)this.m_iNumPoints);
	    		double dNoise = random.nextGaussian() * (m_dNoise / 1000);
	    		double dCTotal = (dNoise / Math.sqrt(m_dTimeConstant)) + m_dSignalOffset;
	    		
	    		// Add the contribution from each compound to the peak
	    		for (int j = 0; j < m_vectCompound.size(); j++)
	    		{
	    			Compound curCompound = m_vectCompound.get(j);
	    			double dCthis = ((curCompound.dW / 1000000) / (curCompound.dSigma * (m_dFlowRate / (60 * 1000)))) * Math.exp(-(0.5 * Math.pow((dTime - curCompound.dRetentionTime) / curCompound.dSigma, 2)));
	    			dCTotal += dCthis;
	    			
	    			// If a compound is selected, then show it in a different color and without noise.
	    			if (iSinglePlotIndex >= 0 && j == iRowSel)
	    		    	contentPane.m_GraphControl.AddDataPoint(iSinglePlotIndex, dTime, (dCthis + m_dSignalOffset) / 1000);
	    		}
	    		
		    	contentPane.m_GraphControl.AddDataPoint(iTotalPlotIndex, dTime, dCTotal / 1000);
	    	}
	    	
	    	if (contentPane.jbtnAutoscaleX.isSelected() == true)
	    		contentPane.m_GraphControl.AutoScaleX();	//AutoScaleToSeries(iTotalPlotIndex);
	    	if (contentPane.jbtnAutoscaleY.isSelected() == true)
	    		contentPane.m_GraphControl.AutoScaleY();
    	}
    	
    	contentPane.m_GraphControl.repaint();   	
    	contentPane.jtableChemicals.addNotify();
	}

	//@Override
	public void focusGained(FocusEvent e) 
	{
		
	}

	//@Override
	public void focusLost(FocusEvent e) 
	{
		performCalculations();
	}

	//@Override
	public void valueChanged(ListSelectionEvent arg0) 
	{
		performCalculations();
		
	}

	//@Override
	public void autoScaleChanged(AutoScaleEvent event) 
	{
		if (event.getAutoScaleXState() == true)
			contentPane.jbtnAutoscaleX.setSelected(true);
		else
			contentPane.jbtnAutoscaleX.setSelected(false);
		
		if (event.getAutoScaleYState() == true)
			contentPane.jbtnAutoscaleY.setSelected(true);
		else
			contentPane.jbtnAutoscaleY.setSelected(false);
		
		if (event.getAutoScaleXState() == true && event.getAutoScaleYState() == true)
			contentPane.jbtnAutoscale.setSelected(true);			
		else
			contentPane.jbtnAutoscale.setSelected(false);						
	}
}