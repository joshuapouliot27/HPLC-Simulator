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
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JApplet;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Globals
{
	public static HelpSet hsMainHelpSet = null;
	public static HelpBroker hbMainHelpBroker = null;
}

class Compound
{
	String strCompoundName;
	double dConcentration;
	double dSlope1;
	double dIntercept1;
	double dSlope2;
	double dIntercept2;
	double dMolarVolume;
	
	double dRetentionTime;
	double dSigma;
	double dW;
	int iCompoundIndex;

	static public int getCompoundNum()
	{
		return 22;
	}
	
	public boolean loadCompoundInfo(int iIndex)
	{
		iCompoundIndex = iIndex;
		
		switch (iIndex)
		{
		case 0: // n-benzyl formamide
			strCompoundName = "n-benzyl formamide";
			dSlope1 = 2.30258*-0.00400592560830356;
			dIntercept1 = 2.30258*0.770866320204006;
			
			dSlope2 = 2.30258*0.00210633532436242;
			dIntercept2 = 2.30258*-1.95104303919;
			dMolarVolume = 156.1;
			break;
		case 1: // benzyl alcohol
			strCompoundName = "benzyl alcohol";
			dSlope1 = 2.30258*-0.00391008226662745;
			dIntercept1 = 2.30258*0.872684428628248;
			
			dSlope2 = 2.30258*0.00167809572382528;
			dIntercept2 = 2.30258*-1.81012708002127;
			
			dMolarVolume = 125.6;
			break;
		case 2: // phenol
			strCompoundName = "phenol";
			dSlope1 = 2.30258*-0.00757157517564261;
			dIntercept1 = 2.30258*1.22597671413584;
			
			dSlope2 = 2.30258*0.00531364688463643;
			dIntercept2 = 2.30258*-2.16017252376361;
			
			dMolarVolume = 103.4;
			break;
		case 3: // 3-phenyl propanol
			strCompoundName = "3-phenyl propanol";
			dSlope1 = 2.30258*-0.00555752850448598;
			dIntercept1 = 2.30258*1.61988170742292;
			
			dSlope2 = 2.30258*0.00455901086174404;
			dIntercept2 = 2.30258*-2.71366867611246;
			
			dMolarVolume = 170;
			break;
		case 4: // acetophenone
			strCompoundName = "acetophenone";
			dSlope1 = 2.30258*-0.00656287336946379;
			dIntercept1 = 2.30258*1.61797589062985;
			
			dSlope2 = 2.30258*0.00449206618532801;
			dIntercept2 = 2.30258*-2.42017817576976;
			
			dMolarVolume = 140.4;
			break;
		case 5: // benzonitrile
			strCompoundName = "benzonitrile";
			dSlope1 = 2.30258*-0.00871713006158633;
			dIntercept1 = 2.30258*1.7633343669628;
			
			dSlope2 = 2.30258*0.00681180442435532;
			dIntercept2 = 2.30258*-2.58410471016241;
			
			dMolarVolume = 122.7;
			break;
		case 6: // p-chlorophenol
			strCompoundName = "p-chlorophenol";
			dSlope1 = 2.30258*-0.0106421316053285;
			dIntercept1 = 2.30258*2.01136494370789;
			
			dSlope2 = 2.30258*0.00985416218318515;
			dIntercept2 = 2.30258*-3.11454038455393;
			
			dMolarVolume = 124.3;
			break;
		case 7: // nitrobenzene
			strCompoundName = "nitrobenzene";
			dSlope1 = 2.30258*-0.00805477755728467;
			dIntercept1 = 2.30258*1.94228405917832;
			
			dSlope2 = 2.30258*0.00890315917768479;
			dIntercept2 = 2.30258*-2.91296540086711;
			
			dMolarVolume = 122.7;
			break;
		case 8: // methyl benzoate
			strCompoundName = "methyl benzoate";
			dSlope1 = 2.30258*-0.0083027971034418;
			dIntercept1 = 2.30258*2.12001407205127;
			
			dSlope2 = 2.30258*0.00649981299283409;
			dIntercept2 = 2.30258*-2.91436201685199;
			
			dMolarVolume = 151.2;
			break;
		case 9: // anisole
			strCompoundName = "anisole";
			dSlope1 = 2.30258* -0.00945401733563803;
			dIntercept1 = 2.30258*2.21123180417535;
			
			dSlope2 = 2.30258*0.00811158706318597;
			dIntercept2 = 2.30258*-2.94517569655072;
			
			dMolarVolume = 128.1;
			break;
		case 10: // benzene
			strCompoundName = "benzene";
			dSlope1 = 2.30258*-0.00915912944500843;
			dIntercept1 = 2.30258*2.21212067952787;
			
			dSlope2 = 2.30258*0.00764000882763618;
			dIntercept2 = 2.30258*-2.82995188862982;
			
			dMolarVolume = 96;
			break;
		case 11: // p-nitrotoluene
			strCompoundName = "p-nitrotoluene";
			dSlope1 = 2.30258*-0.0114948726028585;
			dIntercept1 = 2.30258*2.57042503991969;
			
			dSlope2 = 2.30258*0.0105913897345535;
			dIntercept2 = 2.30258*-3.43831466914758;
			
			dMolarVolume = 144.9;
			break;
		case 12: // p-nitrobenzyl chloride
			strCompoundName = "p-nitrobenzyl chloride";
			dSlope1 = 2.30258*-0.013912235537328;
			dIntercept1 = 2.30258*2.8106313167988;
			
			dSlope2 = 2.30258*0.0139444051244846;
			dIntercept2 = 2.30258*-3.87278361535332;
			
			dMolarVolume = 165.8;
			break;
		case 13: // toluene
			strCompoundName = "toluene";
			dSlope1 = 2.30258*-0.0107827743956062;
			dIntercept1 = 2.30258*2.74299315423678;
			
			dSlope2 = 2.30258*0.00966990191609268;
			dIntercept2 = 2.30258*-3.39773064943588;
			
			dMolarVolume = 118.2;
			break;
		case 14: // benzophenone
			strCompoundName = "benzophenone";
			dSlope1 = 2.30258*-0.011886387905335;
			dIntercept1 = 2.30258*3.09308565623585;
			
			dSlope2 = 2.30258*0.0111271062588277;
			dIntercept2 = 2.30258*-4.06903684711433;
			
			dMolarVolume = 206.8;
			break;
		case 15: // bromobenzene
			strCompoundName = "bromobenzene";
			dSlope1 = 2.30258*-0.012105029016898;
			dIntercept1 = 2.30258*2.94709643373498;
			
			dSlope2 = 2.30258*0.0114024149475323;
			dIntercept2 = 2.30258*-3.65690442173045;
			
			dMolarVolume = 119.3;
			break;
		case 16: // naphthalene
			strCompoundName = "naphthalene";
			dSlope1 = 2.30258*-0.0137396585492798;
			dIntercept1 = 2.30258*3.30292523655581;
			
			dSlope2 = 2.30258*0.0135250497104058;
			dIntercept2 = 2.30258*-4.1188233801337;
			
			dMolarVolume = 147.6;
			break;
		case 17: // ethyl benzene
			strCompoundName = "ethyl benzene";
			dSlope1 = 2.30258*-0.0127527136022442;
			dIntercept1 = 2.30258*3.28418356734091;
			
			dSlope2 = 2.30258*0.0123447010949239;
			dIntercept2 = 2.30258*-4.00949840654916;
			
			dMolarVolume = 140.4;
			break;
		case 18: // p-xylene
			strCompoundName = "p-xylene";
			dSlope1 = 2.30258*-0.0121697463506307;
			dIntercept1 = 2.30258*3.26322623793787;
			
			dSlope2 = 2.30258*0.0109778607049344;
			dIntercept2 = 2.30258*-3.91607670076105;
			
			dMolarVolume = 140.4;
			break;
		case 19: // p-dichlorobenzene
			strCompoundName = "p-dichlorobenzene";
			dSlope1 = 2.30258*-0.0133943283220945;
			dIntercept1 = 2.30258*3.35691395306361;
			
			dSlope2 = 2.30258*0.0127718959763792;
			dIntercept2 = 2.30258*-4.07279858731805;
			
			dMolarVolume = 137.8;
			break;
		case 20: // propylbenzene
			strCompoundName = "propylbenzene";
			dSlope1 = 2.30258*-0.0139547182068069;
			dIntercept1 = 2.30258*3.81725808879461;
			
			dSlope2 = 2.30258*0.0130172624329028;
			dIntercept2 = 2.30258*-4.52764097511215;
			
			dMolarVolume = 162.6;
			break;
		case 21: // n-butylbenzene
			strCompoundName = "n-butylbenzene";
			dSlope1 = 2.30258*-0.0141417183608352;
			dIntercept1 = 2.30258*4.26515043907842;
			
			dSlope2 = 2.30258*0.0138179700728438;
			dIntercept2 = 2.30258*-5.04537890229836;
			
			dMolarVolume = 184.8;
			break;
		default:
			return false;
		}
		
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
	
	public Vector<Compound> m_vectCompound = new Vector<Compound>();
	/**
	 * This is the xxx default constructor
	 */
	public HPLCSimulatorApplet() 
	{
		super();
		this.setPreferredSize(new Dimension(854, 703));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() 
	{
        // Load the JavaHelp
		String helpHS = "help/HPLCSimHelp.hs";
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
    	compound1.loadCompoundInfo(2);
    	compound1.dConcentration = 5;
    	this.m_vectCompound.add(compound1);
    	
    	Compound compound2 = new Compound();
    	compound2.loadCompoundInfo(3);
    	compound2.dConcentration = 25;
    	this.m_vectCompound.add(compound2);
    	
    	Compound compound3 = new Compound();
    	compound3.loadCompoundInfo(4);
    	compound3.dConcentration = 40;
    	this.m_vectCompound.add(compound3);

    	Compound compound4 = new Compound();
    	compound4.loadCompoundInfo(6);
    	compound4.dConcentration = 15;
    	this.m_vectCompound.add(compound4);

    	Compound compound5 = new Compound();
    	compound5.loadCompoundInfo(11);
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
        contentPane.jsliderTemp.addChangeListener(this);
        contentPane.jsliderMeOHFraction.addChangeListener(this);
        contentPane.jtxtTemp.addKeyListener(this);
        contentPane.jtxtTemp.addFocusListener(this);
        contentPane.jtxtMeOHFraction.addKeyListener(this);
        contentPane.jtxtMeOHFraction.addFocusListener(this);
        contentPane.jtxtColumnLength.addKeyListener(this);
        contentPane.jtxtColumnLength.addFocusListener(this);
        contentPane.jtxtColumnDiameter.addKeyListener(this);
        contentPane.jtxtColumnDiameter.addFocusListener(this);
        contentPane.jtxtVoidFraction.addKeyListener(this);
        contentPane.jtxtVoidFraction.addFocusListener(this);        
        contentPane.jtxtFlowRate.addKeyListener(this);
        contentPane.jtxtFlowRate.addFocusListener(this);        
        contentPane.jtxtParticleSize.addKeyListener(this);
        contentPane.jtxtParticleSize.addFocusListener(this);        
        contentPane.jtxtATerm.addKeyListener(this);
        contentPane.jtxtATerm.addFocusListener(this);        
        contentPane.jtxtBTerm.addKeyListener(this);
        contentPane.jtxtBTerm.addFocusListener(this);        
        contentPane.jtxtCTerm.addKeyListener(this);
        contentPane.jtxtCTerm.addFocusListener(this);        
        contentPane.jtxtInjectionVolume.addKeyListener(this);
        contentPane.jtxtInjectionVolume.addFocusListener(this);        
        contentPane.jtxtTimeConstant.addKeyListener(this);
        contentPane.jtxtTimeConstant.addFocusListener(this);        
        contentPane.jtxtNoise.addKeyListener(this);
        contentPane.jtxtNoise.addFocusListener(this);        
        contentPane.jtxtSignalOffset.addKeyListener(this);
        contentPane.jtxtSignalOffset.addFocusListener(this);   
        contentPane.jtxtInitialTime.addKeyListener(this);
        contentPane.jtxtInitialTime.addFocusListener(this);   
        contentPane.jtxtFinalTime.addKeyListener(this);
        contentPane.jtxtFinalTime.addFocusListener(this);   
        contentPane.jtxtNumPoints.addKeyListener(this);
        contentPane.jtxtNumPoints.addFocusListener(this);   
        contentPane.jtableChemicals.getSelectionModel().addListSelectionListener(this);
        contentPane.jchkAutoTimeRange.addActionListener(this);
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
		double dTemp = (double)Float.valueOf(contentPane.jtxtTemp.getText());
		dTemp = Math.floor(dTemp);
		
		if (dTemp < 10)
			dTemp = 10;
		if (dTemp > 150)
			dTemp = 150;
		
		m_dTemperature = dTemp;
		m_bSliderUpdate = false;
		contentPane.jsliderTemp.setValue((int)m_dTemperature);
		contentPane.jtxtTemp.setText(Integer.toString((int)m_dTemperature));    	
    }
    
    private void validateMeOHFraction()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtMeOHFraction.getText());
		dTemp = Math.floor(dTemp);
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > 100)
			dTemp = 100;
		
		this.m_dMeOHFraction = dTemp / 100;
		m_bSliderUpdate = false;
		contentPane.jsliderMeOHFraction.setValue((int)(m_dMeOHFraction * 100));
		contentPane.jtxtMeOHFraction.setText(Integer.toString((int)(m_dMeOHFraction * 100)));    	
    }    
    
    private void validateColumnLength()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtColumnLength.getText());
		
		if (dTemp < .01)
			dTemp = .01;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dColumnLength = dTemp;
		contentPane.jtxtColumnLength.setText(Float.toString((float)m_dColumnLength));    	
    }    

    private void validateColumnDiameter()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtColumnDiameter.getText());
		
		if (dTemp < .001)
			dTemp = .001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dColumnDiameter = dTemp;
		contentPane.jtxtColumnDiameter.setText(Float.toString((float)m_dColumnDiameter));    	
    }    

    private void validateVoidFraction()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtVoidFraction.getText());
		
		if (dTemp < .001)
			dTemp = .001;
		if (dTemp > .999)
			dTemp = .999;
		
		this.m_dVoidFraction = dTemp;
		contentPane.jtxtVoidFraction.setText(Float.toString((float)m_dVoidFraction));    	
    }    

    private void validateFlowRate()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtFlowRate.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dFlowRate = dTemp;
		contentPane.jtxtFlowRate.setText(Float.toString((float)m_dFlowRate));    	
    }    

    private void validateParticleSize()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtParticleSize.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dParticleSize = dTemp;
		contentPane.jtxtParticleSize.setText(Float.toString((float)m_dParticleSize));    	
    }    

    private void validateATerm()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtATerm.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dATerm = dTemp;
		contentPane.jtxtATerm.setText(Float.toString((float)m_dATerm));    	
    }    

    private void validateBTerm()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtBTerm.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dBTerm = dTemp;
		contentPane.jtxtBTerm.setText(Float.toString((float)m_dBTerm));    	
    } 
    
    private void validateCTerm()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtCTerm.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dCTerm = dTemp;
		contentPane.jtxtCTerm.setText(Float.toString((float)m_dCTerm));    	
    } 

    private void validateInjectionVolume()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtInjectionVolume.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dInjectionVolume = dTemp;
		contentPane.jtxtInjectionVolume.setText(Float.toString((float)m_dInjectionVolume));    	
    } 

    private void validateTimeConstant()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtTimeConstant.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dTimeConstant = dTemp;
		contentPane.jtxtTimeConstant.setText(Float.toString((float)m_dTimeConstant));    	
    } 

    private void validateNoise()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtNoise.getText());
		
		if (dTemp < .000001)
			dTemp = .000001;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dNoise = dTemp;
		contentPane.jtxtNoise.setText(Float.toString((float)m_dNoise));    	
    } 

    private void validateSignalOffset()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtSignalOffset.getText());
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > 999999)
			dTemp = 999999;
		
		this.m_dSignalOffset = dTemp;
		contentPane.jtxtSignalOffset.setText(Float.toString((float)m_dSignalOffset));    	
    } 

    private void validateStartTime()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtInitialTime.getText());
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > m_dEndTime)
			dTemp = m_dEndTime - .000001;
		
		this.m_dStartTime = dTemp;
		contentPane.jtxtInitialTime.setText(Float.toString((float)m_dStartTime));    	
    } 

    private void validateEndTime()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtFinalTime.getText());
		
		if (dTemp < m_dStartTime)
			dTemp = m_dStartTime + .000001;
		if (dTemp > 99999999)
			dTemp = 99999999;
		
		this.m_dEndTime = dTemp;
		contentPane.jtxtFinalTime.setText(Float.toString((float)m_dEndTime));    	
    } 

    private void validateNumPoints()
    {
		int iTemp = Integer.valueOf(contentPane.jtxtNumPoints.getText());
		
		if (iTemp < 2)
			iTemp = 2;
		if (iTemp > 10000000)
			iTemp = 10000000;
		
		this.m_iNumPoints = iTemp;
		contentPane.jtxtNumPoints.setText(Integer.toString(m_iNumPoints));    	
    } 

    public void actionPerformed(ActionEvent evt) 
	{
	    String strActionCommand = evt.getActionCommand();
	    if (strActionCommand == "Add Chemical")
	    {
	    	Frame[] frames = Frame.getFrames();
	    	ChemicalDialog dlgChemical = new ChemicalDialog(frames[0], false);
	    	
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
	    	newCompound.dSlope1 = dlgChemical.m_dSlope1;
	    	newCompound.dIntercept1 = dlgChemical.m_dIntercept1;
	    	newCompound.dSlope2 = dlgChemical.m_dSlope2;
	    	newCompound.dIntercept2 = dlgChemical.m_dIntercept2;
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
	    	ChemicalDialog dlgChemical = new ChemicalDialog(frames[0], true);
	    	
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
	    	newCompound.dSlope1 = dlgChemical.m_dSlope1;
	    	newCompound.dIntercept1 = dlgChemical.m_dIntercept1;
	    	newCompound.dSlope2 = dlgChemical.m_dSlope2;
	    	newCompound.dIntercept2 = dlgChemical.m_dIntercept2;
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
	    	if (contentPane.jchkAutoTimeRange.isSelected() == true)
	    	{
	    		contentPane.jtxtInitialTime.setEnabled(false);
	    		contentPane.jtxtFinalTime.setEnabled(false);
	    	}
	    	else
	    	{
	    		contentPane.jtxtInitialTime.setEnabled(true);
	    		contentPane.jtxtFinalTime.setEnabled(true);	    		
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
			
			m_dTemperature = contentPane.jsliderTemp.getValue();
			contentPane.jtxtTemp.setText(Integer.toString((int)m_dTemperature));
			performCalculations();
		}
		else if (source.getName() == "Methanol Fraction Slider")
		{
			if (m_bSliderUpdate == false)
			{
				m_bSliderUpdate = true;
				return;
			}
			
			m_dMeOHFraction = ((double)contentPane.jsliderMeOHFraction.getValue() / (double)100);
			contentPane.jtxtMeOHFraction.setText(Integer.toString((int)(m_dMeOHFraction * 100)));		
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
		contentPane.jlblVoidVolume.setText(formatter.format(m_dVoidVolume));
		
		m_dVoidTime = (m_dVoidVolume / m_dFlowRate) * 60;
		contentPane.jlblVoidTime.setText(formatter.format(m_dVoidTime));
		
		m_dFlowVelocity = (m_dColumnLength / 10) / m_dVoidTime;
		contentPane.jlblFlowVelocity.setText(formatter.format(m_dFlowVelocity));
		
		// Calculate eluent viscosity
		// TODO: this formula is for acetonitrile/water mixtures, not MeOH/Water mixtures
		// See Chen, H.; Horvath, C. Anal. Methods Instrum. 1993, 1, 213-222.
		double dTempKelvin = m_dTemperature + 273.15;
		m_dEluentViscosity = Math.exp((m_dMeOHFraction * (-3.476 + (726 / dTempKelvin))) + ((1 - m_dMeOHFraction) * (-5.414 + (1566 / dTempKelvin))) + (m_dMeOHFraction * (1 - m_dMeOHFraction) * (-1.762 + (929 / dTempKelvin))));
		contentPane.jlblEluentViscosity.setText(formatter.format(m_dEluentViscosity));

		// Calculate backpressure (in pascals) (Darcy equation)
		// See Bird, R. B.; Stewart, W. E.; Lightfoot, E. N. Transport Phenomena; Wiley & Sons: New York, 1960.
		m_dBackpressure = 500 * (m_dEluentViscosity / 1000) * (((m_dFlowVelocity / 100) * (m_dColumnLength / 1000)) / Math.pow(m_dParticleSize / 1000000, 2));
		NumberFormat bpFormatter = new DecimalFormat("#0.00");
		contentPane.jlblBackpressure.setText(bpFormatter.format(m_dBackpressure / 100000));
		
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
		contentPane.jlblDiffusionCoefficient.setText(df.format(m_dDiffusionCoefficient));
		
		m_dMu = ((m_dParticleSize / 10000) * m_dFlowVelocity) / m_dDiffusionCoefficient;
		
		m_dReducedPlateHeight = m_dATerm + (m_dBTerm / m_dMu) + (m_dCTerm * m_dMu);
		contentPane.jlblReducedPlateHeight.setText(formatter.format(m_dReducedPlateHeight));
    	
		m_dHETP = (m_dParticleSize / 10000) * m_dReducedPlateHeight;
		contentPane.jlblHETP.setText(df.format(m_dHETP));
		
		NumberFormat NFormatter = new DecimalFormat("#0");
		m_dTheoreticalPlates = (m_dColumnLength / 10) / m_dHETP;
		contentPane.jlblTheoreticalPlates.setText(NFormatter.format(m_dTheoreticalPlates));

		for (int i = 0; i < m_vectCompound.size(); i++)
		{
	    	// Calculate lnk'w1
	    	double lnkprimew1 = (m_vectCompound.get(i).dSlope1 * this.m_dTemperature) + m_vectCompound.get(i).dIntercept1;
	    	// Calculate S1
	    	double S1 = -1 * ((m_vectCompound.get(i).dSlope2 * this.m_dTemperature) + m_vectCompound.get(i).dIntercept2);
			// Calculate k'
	    	double kprime = Math.exp(lnkprimew1 - (S1 * this.m_dMeOHFraction));
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
    	
    	if (contentPane.jchkAutoTimeRange.isSelected() == true)
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
	    	
	    	contentPane.jtxtFinalTime.setText(Float.toString((float)m_dEndTime));
	    	
	    	contentPane.jtxtInitialTime.setText("0");
	    	
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
