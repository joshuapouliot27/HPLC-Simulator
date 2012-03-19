package org.retentionprediction;

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
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import javax.help.CSH;
import javax.help.HelpSet;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.SwingWorker;

class Compound
{
	String strCompoundName;
	int iCompoundIndex;

	static public int getCompoundNum()
	{
		return Globals.CompoundNameArray.length;
	}
	
	public boolean loadCompoundInfo(int iIndex)
	{
		iCompoundIndex = iIndex;
		
		strCompoundName = Globals.CompoundNameArray[iIndex];
		
		return true;
	}
}

public class RetentionPredictorApplet extends JApplet implements ActionListener, KeyListener, FocusListener, ListSelectionListener, AutoScaleListener, TableModelListener
{
	private static final long serialVersionUID = 1L;

	TopPanel contentPane = null;
	TopPanel2 contentPane2 = null;
	public JScrollPane jMainScrollPane = null;
	public JPanel jBackPanel = null;
	public int m_iStationaryPhase = 0;
	public double m_dInitialTemperature = 60;
	public double m_dInitialTime = 1;
	public double m_dColumnLength = 30;
	public double m_dProgramTime = 20;
	public double m_dFlowRate = 1; // in mL/min
	public double m_dInletPressure = 45; // in kPa
	public double m_dOutletPressure = 0.000001; // in kPa
	public int m_iNumPoints = 3000;
	public Vector<Object[]> m_vectCalCompounds = new Vector<Object[]>();
    private Task task;
    private TaskPredict taskPredict;
    public int m_iStage = 1;
    public double m_dtstep = 0.01;
    public double m_V0 = 1; // in mL
    public double[][] m_dIdealTemperatureProfileArray;
    public LinearInterpolationFunction m_InterpolatedIdealTempProfile; //Linear
    public LinearInterpolationFunction m_InterpolatedTemperatureProfile; // Linear
    public InterpolationFunction m_InitialInterpolatedHoldUpVsTempProfile;
    public double[][] m_dTemperatureProfileArray;
    public double[][] m_dHoldUpArray;
    public InterpolationFunction m_InterpolatedHoldUp;
    public InterpolationFunction[] m_AlkaneIsothermalDataInterpolated;
    public InterpolationFunction[] m_CompoundIsothermalDataInterpolated;
    public InterpolationFunction m_Vm;
	
    public double[][][] m_dHoldUpArrayStore;
    public double[][][] m_dTemperatureArrayStore;
    public double[] m_dRetentionErrorStore;

    public int m_iInterpolatedTempProgramSeries = 0;
    public int m_iTempProgramMarkerSeries = 0;
    public int m_iInterpolatedHoldUpSeries = 0;
    public int m_iHoldUpMarkerSeries = 0;
    
    public double m_dPlotXMax = 0;
    public double m_dPlotXMax2 = 0;
    
    public boolean m_bDoNotChangeTable = false;
    public final double m_dGoldenRatio = (1 + Math.sqrt(5)) / 2;

	public Vector<Compound> m_vectCompound = new Vector<Compound>();
	/**
	 * This is the xxx default constructor
	 */
	public RetentionPredictorApplet() 
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

		this.setPreferredSize(new Dimension(943, 615));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() 
	{
        // Load the JavaHelp
		String helpHS = "org/retentionprediction/help/RetentionPredictorHelp.hs";
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
        
        performValidations();
        
    }
    
    private void createGUI()
    {
        //Create and set up the first content pane (steps 1-4).
    	jMainScrollPane = new JScrollPane();
    	jMainScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	jMainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    	contentPane = new TopPanel();
        contentPane.setOpaque(true);
        jMainScrollPane.setViewportView(contentPane);
    	setContentPane(jMainScrollPane);
    	jMainScrollPane.revalidate();

        contentPane.jtxtInitialTemperature.addFocusListener(this);
        contentPane.jtxtInitialTemperature.addKeyListener(this);
        contentPane.jtxtInitialTime.addFocusListener(this);
        contentPane.jtxtInitialTime.addKeyListener(this);
        contentPane.jtxtColumnLength.addFocusListener(this);
        contentPane.jtxtColumnLength.addKeyListener(this);
        contentPane.jtxtFlowRate.addFocusListener(this);
        contentPane.jtxtFlowRate.addKeyListener(this);
        contentPane.jtxtPressure.addFocusListener(this);
        contentPane.jtxtPressure.addKeyListener(this);
        contentPane.jrdoConstantFlowRate.addActionListener(this);
        contentPane.jrdoConstantPressure.addActionListener(this);
        contentPane.jrdoAmbient.addActionListener(this);
        contentPane.jrdoVacuum.addActionListener(this);
        contentPane.jbtnHelp.addActionListener(this);
        contentPane.jbtnInsertRow.addActionListener(this);
        contentPane.jbtnRemoveRow.addActionListener(this);
       
        contentPane.m_GraphControlTemp.addAutoScaleListener(this);
        contentPane.m_GraphControlHoldUp.addAutoScaleListener(this);
        contentPane.tmTemperatureProgram.addTableModelListener(this);
        contentPane.tmMeasuredRetentionTimes.addTableModelListener(this);
        contentPane.jbtnNextStep.addActionListener(this);
        contentPane.jbtnPreloadedValues.addActionListener(this);
        
        contentPane.m_GraphControlTemp.setYAxisTitle("Column Temperature");
        contentPane.m_GraphControlTemp.setYAxisBaseUnit("\u00B0C", "\u00B0C");
        contentPane.m_GraphControlTemp.setYAxisRangeLimits(0, 105);
        contentPane.m_GraphControlTemp.setYAxisRangeIndicatorsVisible(true);
        contentPane.m_GraphControlTemp.setXAxisRangeIndicatorsVisible(false);
        contentPane.m_GraphControlTemp.setAutoScaleY(true);
        //contentPane.m_GraphControl.setVisibleWindow(0, 300, 0, 110);
        contentPane.m_GraphControlTemp.repaint();

        contentPane.m_GraphControlHoldUp.setYAxisTitle("Hold-up Time");
        contentPane.m_GraphControlHoldUp.setYAxisBaseUnit("seconds", "s");
        contentPane.m_GraphControlHoldUp.setYAxisRangeLimits(0, 10000);
        contentPane.m_GraphControlHoldUp.setYAxisRangeIndicatorsVisible(true);
        contentPane.m_GraphControlHoldUp.setXAxisRangeIndicatorsVisible(false);
        contentPane.m_GraphControlHoldUp.setAutoScaleY(true);
        //contentPane.m_GraphControlFlow.setVisibleWindow(0, 300, 0.195 / 1000, 0.205 / 1000);
        contentPane.m_GraphControlHoldUp.repaint();
        
        //Create and set up the second content pane
        contentPane2 = new TopPanel2();
        contentPane2.setOpaque(true); 
        contentPane2.jpanelStep6.setVisible(false);
        
        contentPane2.jbtnCalculate.addActionListener(this);
        contentPane2.jbtnNextStep.addActionListener(this);
        contentPane2.jbtnPreviousStep.addActionListener(this);
        contentPane2.jbtnPredict.addActionListener(this);
        contentPane2.jbtnHelp.addActionListener(this);

        contentPane2.m_GraphControlTemp.setYAxisTitle("Column Temperature");
        contentPane2.m_GraphControlTemp.setYAxisBaseUnit("\u00b0C", "\u00b0C");
        contentPane2.m_GraphControlTemp.setYAxisRangeLimits(0, 105);
        contentPane2.m_GraphControlTemp.setYAxisRangeIndicatorsVisible(true);
        contentPane2.m_GraphControlTemp.setXAxisRangeIndicatorsVisible(false);
        contentPane2.m_GraphControlTemp.setAutoScaleY(true);
        //contentPane.m_GraphControl.setVisibleWindow(0, 300, 0, 110);
        contentPane2.m_GraphControlTemp.repaint();

        contentPane2.m_GraphControlHoldUp.setYAxisTitle("Hold-up Time");
        contentPane2.m_GraphControlHoldUp.setYAxisBaseUnit("seconds", "s");
        contentPane2.m_GraphControlHoldUp.setYAxisRangeLimits(0, 100);
        contentPane2.m_GraphControlHoldUp.setYAxisRangeIndicatorsVisible(true);
        contentPane2.m_GraphControlHoldUp.setAutoScaleY(true);
        contentPane2.m_GraphControlHoldUp.setXAxisType(false);
        contentPane2.m_GraphControlHoldUp.setXAxisBaseUnit("\u00b0C", "\u00b0C");
        contentPane2.m_GraphControlHoldUp.setXAxisRangeLimits(0, 1000);
        contentPane2.m_GraphControlHoldUp.setXAxisTitle("Temperature");
        contentPane2.m_GraphControlHoldUp.setXAxisRangeIndicatorsVisible(false);

        //contentPane.m_GraphControlFlow.setVisibleWindow(0, 300, 0.195 / 1000, 0.205 / 1000);
        contentPane2.m_GraphControlHoldUp.repaint();       
    }

    private void validateInitialTemperature()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtInitialTemperature.getText());
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > 500)
			dTemp = 500;
		
		if (contentPane.tmTemperatureProgram.getRowCount() > 0)
		{
			double dFirstTemp = (Double)contentPane.tmTemperatureProgram.getValueAt(0, 1);
			if (dTemp > dFirstTemp)
				dTemp = dFirstTemp;
		}
		
		this.m_dInitialTemperature = dTemp;
		contentPane.jtxtInitialTemperature.setText(Float.toString((float)dTemp));    	
    }

    private void validateInitialTime()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtInitialTime.getText());
		
		if (dTemp < 0)
			dTemp = 0;
		if (dTemp > 1000)
			dTemp = 1000;
		
		this.m_dInitialTime = dTemp;
		contentPane.jtxtInitialTime.setText(Float.toString((float)dTemp));    	
    }

    private void validateColumnLength()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtColumnLength.getText());
		
		if (dTemp < 0.1)
			dTemp = 0.1;
		if (dTemp > 10000)
			dTemp = 10000;
		
		this.m_dColumnLength = dTemp;
		contentPane.jtxtColumnLength.setText(Float.toString((float)m_dColumnLength));    	
    }

    private void validateFlowRate()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtFlowRate.getText());
		
		if (dTemp < 0.000000001)
			dTemp = 0.000000001;
		if (dTemp > 10000)
			dTemp = 10000;
		
		this.m_dFlowRate = dTemp;
		contentPane.jtxtFlowRate.setText(Float.toString((float)m_dFlowRate));    	
    }

    private void validateInletPressure()
    {
		double dTemp = (double)Float.valueOf(contentPane.jtxtPressure.getText());
		
		if (dTemp < 0.000000001)
			dTemp = 0.000000001;
		if (dTemp > 100000)
			dTemp = 100000;
	
		this.m_dInletPressure = dTemp;			
		contentPane.jtxtPressure.setText(Float.toString((float)m_dInletPressure));    	
    }
    
    public void nextStepButtonPressed()
    {
    	// Set the graph and flow profiles to contain the correct data
    	m_iStage = 2;

    	// Set initial control values
    	contentPane2.jbtnCalculate.setText("Back-Calculate Profiles");
    	contentPane2.jbtnCalculate.setEnabled(true);
    	contentPane2.jlblIterationNumber.setText("1");
    	contentPane2.jlblLastVariance.setText("");
    	contentPane2.jlblPercentImprovement.setText("");
    	contentPane2.jlblPhase.setText("I");
    	contentPane2.jlblTimeElapsed.setText("");
    	contentPane2.jlblVariance.setText("");
    	contentPane2.jProgressBar.setString("");
    	contentPane2.jProgressBar.setIndeterminate(false);
    	contentPane2.jbtnNextStep.setEnabled(false);
    	contentPane2.m_GraphControlTemp.RemoveAllSeries();
    	contentPane2.m_GraphControlHoldUp.RemoveAllSeries();
        contentPane2.jpanelStep6.setVisible(false);
        contentPane2.jpanelStep5.setVisible(true);
		
    	// Set the table to contain the correct data
    	contentPane2.tmOutputModel.getDataVector().clear();
    	m_vectCalCompounds.clear();
    	
    	for (int i = 0; i < contentPane.tmMeasuredRetentionTimes.getRowCount(); i++)
    	{
    		if ((Double)contentPane.tmMeasuredRetentionTimes.getValueAt(i, 1) <= (Double)0.0)
    			continue;
    		
    		Object[] newRow = {contentPane.tmMeasuredRetentionTimes.getValueAt(i, 0), contentPane.tmMeasuredRetentionTimes.getValueAt(i, 1), null, null};

    		contentPane2.tmOutputModel.addRow(newRow);
    		
			Object[] newSolute = {i, contentPane.tmMeasuredRetentionTimes.getValueAt(i, 1)};
			m_vectCalCompounds.add(newSolute);
    	}
    	
		m_dPlotXMax2 = (((Double)m_vectCalCompounds.get(m_vectCalCompounds.size() - 1)[1]));
    	
    	// Here is where we set the value of m_dtstep
    	m_dtstep = m_dPlotXMax2 * 0.001;

    	int iIdealPlotIndexTemp = contentPane2.m_GraphControlTemp.AddSeries("Ideal Temperature Program", new Color(0, 0, 0), 1, false, false);
    	int iIdealPlotIndexHoldUp = contentPane2.m_GraphControlHoldUp.AddSeries("Ideal Hold-up Time", new Color(0, 0, 0), 1, false, false);
    	
    	m_iInterpolatedTempProgramSeries = contentPane2.m_GraphControlTemp.AddSeries("Interpolated Temperature Program", new Color(255,0,0), 1, false, false);
	    m_iTempProgramMarkerSeries = contentPane2.m_GraphControlTemp.AddSeries("Temp Program Markers", new Color(255,0,0), 1, true, false);
	    
    	m_iInterpolatedHoldUpSeries = contentPane2.m_GraphControlHoldUp.AddSeries("Interpolated Hold-up", new Color(255,0,0), 1, false, false);
	    m_iHoldUpMarkerSeries = contentPane2.m_GraphControlHoldUp.AddSeries("Hold-up Markers", new Color(255,0,0), 1, true, false);

	    // Add in data points for the ideal temperature program series
    	this.m_dIdealTemperatureProfileArray = new double[(contentPane.tmTemperatureProgram.getRowCount() * 2) + 2][2];
    	int iPointCount = 0;

    	contentPane2.m_GraphControlTemp.AddDataPoint(iIdealPlotIndexTemp, 0, m_dInitialTemperature);
    	this.m_dIdealTemperatureProfileArray[iPointCount][0] = 0.0;
    	this.m_dIdealTemperatureProfileArray[iPointCount][1] = m_dInitialTemperature;
		iPointCount++;
		
    	contentPane2.m_GraphControlTemp.AddDataPoint(iIdealPlotIndexTemp, m_dInitialTime * 60, m_dInitialTemperature);
    	this.m_dIdealTemperatureProfileArray[iPointCount][0] = m_dInitialTime;
    	this.m_dIdealTemperatureProfileArray[iPointCount][1] = m_dInitialTemperature;
		iPointCount++;

    	double dTotalTime = m_dInitialTime;
    	double dLastTemp = m_dInitialTemperature;
    	double dFinalTemp = m_dInitialTemperature;
    	
    	// Go through the temperature program table and create an array that contains temp vs. time
		for (int i = 0; i < contentPane.tmTemperatureProgram.getRowCount(); i++)
		{
			double dRamp = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 0);
			dFinalTemp = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 1);
			double dFinalTime = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 2);

			if (dRamp != 0)
			{
				dTotalTime += (dFinalTemp - dLastTemp) / dRamp;
				contentPane2.m_GraphControlTemp.AddDataPoint(iIdealPlotIndexTemp, dTotalTime * 60, dFinalTemp);
				this.m_dIdealTemperatureProfileArray[iPointCount][0] = dTotalTime;
				this.m_dIdealTemperatureProfileArray[iPointCount][1] = dFinalTemp;
				iPointCount++;
			}
			
			if (dFinalTime != 0)
			{
				if (i < contentPane.tmTemperatureProgram.getRowCount() - 1)
				{
					dTotalTime += dFinalTime;
					contentPane2.m_GraphControlTemp.AddDataPoint(iIdealPlotIndexTemp, dTotalTime * 60, dFinalTemp);				
					this.m_dIdealTemperatureProfileArray[iPointCount][0] = dTotalTime;
					this.m_dIdealTemperatureProfileArray[iPointCount][1] = dFinalTemp;
					iPointCount++;						
				}
			}
			
			dLastTemp = dFinalTemp;
		}
		
    	contentPane2.m_GraphControlTemp.AddDataPoint(iIdealPlotIndexTemp, m_dPlotXMax2 * 60, dFinalTemp);
    	this.m_dIdealTemperatureProfileArray[iPointCount][0] = m_dPlotXMax2;
    	this.m_dIdealTemperatureProfileArray[iPointCount][1] = dFinalTemp;
		iPointCount++;

		// Ideal series finished
		// Now cut it down to the correct size
		double tempArray[][] = new double[iPointCount][2];
		for (int i = 0; i < iPointCount; i++)
		{
			tempArray[i][0] = this.m_dIdealTemperatureProfileArray[i][0];
			tempArray[i][1] = this.m_dIdealTemperatureProfileArray[i][1];
		}
		this.m_dIdealTemperatureProfileArray = tempArray;
		
		// Make the interpolated temperature profile
    	this.m_InterpolatedIdealTempProfile = new LinearInterpolationFunction(this.m_dIdealTemperatureProfileArray);

    	// Select number of data points for the gradient and flow profiles
		int iTotalDataPoints = m_vectCalCompounds.size();
		
		// 11/15ths of the data points should be on the gradient profile
		int iNumTempProgramDataPoints = (int)(((double)11/(double)15)*(double)iTotalDataPoints);
		int iNumFlowDataPoints = iTotalDataPoints - iNumTempProgramDataPoints;
		
		if (iNumFlowDataPoints < 3)
		{
			iNumFlowDataPoints = 3;
			iNumTempProgramDataPoints = iTotalDataPoints - iNumFlowDataPoints;
		}
		
		// Create initial gradient and flow rate arrays
		
		// First make an array with the correct number of data points.
		m_dTemperatureProfileArray = new double [iNumTempProgramDataPoints][2];
		m_dHoldUpArray = new double [iNumFlowDataPoints][2];
		
		// Set the value of the first data point
		m_dTemperatureProfileArray[0][0] = 0;
		m_dTemperatureProfileArray[0][1] = this.m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[0][0]);
		
		for (int i = 1; i < iNumTempProgramDataPoints - 1; i++)
		{
			// Find the two nearest alkanes
			double dAlkaneNum = ((double)i / ((double)iNumTempProgramDataPoints - 1)) * (double)iTotalDataPoints;
			double dOneGreater = (int)Math.ceil(dAlkaneNum);
			double dOneLesser = (int)Math.floor(dAlkaneNum);
			double dRtOneLesser = (Double)m_vectCalCompounds.get((int)dOneLesser)[1];
			double dRtOneGreater = (Double)m_vectCalCompounds.get((int)dOneGreater)[1];
		
			// Check to see if we landed exactly on an alkane
			if (dOneGreater == dOneLesser)
			{
				// If so, set the time of this point to the alkane we landed on
				m_dTemperatureProfileArray[i][0] = dRtOneLesser;
			}
			else
			{
				// Otherwise, find the time of this point in between the surrounding alkanes
				double dPosition = ((dAlkaneNum - dOneLesser) / (dOneGreater - dOneLesser));
				m_dTemperatureProfileArray[i][0] = (dPosition * dRtOneGreater) + ((1 - dPosition) * dRtOneLesser);
			}
			
			// Subtract half a hold-up time
			m_dTemperatureProfileArray[i][0] = m_dTemperatureProfileArray[i][0] - (0.5 * (this.m_InitialInterpolatedHoldUpVsTempProfile.getAt(m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[i][0])) / 60));
			m_dTemperatureProfileArray[i][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[i][0]);
		}
		
		// Add the last data point at the position of the last-eluting compound
		m_dTemperatureProfileArray[iNumTempProgramDataPoints - 1][0] = (Double)m_vectCalCompounds.get(m_vectCalCompounds.size() - 1)[1];
		m_dTemperatureProfileArray[iNumTempProgramDataPoints - 1][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[iNumTempProgramDataPoints - 1][0]);
		
		// Now adjust to get points at the corners
		int iPointIndex = 1;
		// Run through each corner in the ideal temperature profile array
		for (int i = 1; i < this.m_dIdealTemperatureProfileArray.length - 1; i++)
		{
			double dFirst = 0;
			double dNext = 0;
			
			// Find the first point after the corner (dNext) and the first point before the corner (dFirst)
			while (dNext < this.m_dIdealTemperatureProfileArray[i][0] && iPointIndex < m_dTemperatureProfileArray.length - 1)
			{
				dFirst = m_dTemperatureProfileArray[iPointIndex][0];
				dNext = m_dTemperatureProfileArray[iPointIndex + 1][0];
				
				iPointIndex++;
			}
			
			// Remove the last increment
			iPointIndex--;
			
			// Find the distances between the corner and the two points
			double dDistFirst = this.m_dIdealTemperatureProfileArray[i][0] - dFirst;
			double dDistNext = dNext - this.m_dIdealTemperatureProfileArray[i][0];
			
			// Find the distances between the two points and their next further point
			double dDistFirstBefore = m_dTemperatureProfileArray[iPointIndex][0] - m_dTemperatureProfileArray[iPointIndex - 1][0];
			double dDistNextAfter;
			if (iPointIndex + 2 < m_dTemperatureProfileArray.length)
				dDistNextAfter = m_dTemperatureProfileArray[iPointIndex + 2][0] - m_dTemperatureProfileArray[iPointIndex + 1][0];
			else
				dDistNextAfter = 0;
			
			double dScoreFirst = dDistFirst + dDistFirstBefore;
			double dScoreNext = dDistNext + dDistNextAfter;
			
			// Point with lower score moves
			if (dScoreFirst < dScoreNext)
			{
				// Move the first point
				m_dTemperatureProfileArray[iPointIndex][0] = this.m_dIdealTemperatureProfileArray[i][0];
				m_dTemperatureProfileArray[iPointIndex][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[iPointIndex][0]);

				// Move the one before it right in between it and the last point
				if (iPointIndex >= 2)
				{
					m_dTemperatureProfileArray[iPointIndex - 1][0] = (m_dTemperatureProfileArray[iPointIndex - 2][0] + m_dTemperatureProfileArray[iPointIndex][0]) / 2;
					m_dTemperatureProfileArray[iPointIndex - 1][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[iPointIndex - 1][0]);
				}
				
				// Move the one after it in between it and the next point
				if (iPointIndex <= m_dTemperatureProfileArray.length - 3)
				{
					m_dTemperatureProfileArray[iPointIndex + 1][0] = (m_dTemperatureProfileArray[iPointIndex][0] + m_dTemperatureProfileArray[iPointIndex + 2][0]) / 2;
					m_dTemperatureProfileArray[iPointIndex + 1][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[iPointIndex + 1][0]);
				}
				
			}
			else
			{
				// Move the next point
				m_dTemperatureProfileArray[iPointIndex + 1][0] = this.m_dIdealTemperatureProfileArray[i][0];
				m_dTemperatureProfileArray[iPointIndex + 1][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[iPointIndex + 1][0]);

				// Move the one before it right in between it and the last point
				if (iPointIndex >= 1)
				{
					m_dTemperatureProfileArray[iPointIndex][0] = (m_dTemperatureProfileArray[iPointIndex - 1][0] + m_dTemperatureProfileArray[iPointIndex + 1][0]) / 2;
					m_dTemperatureProfileArray[iPointIndex][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[iPointIndex][0]);
				}
				
				// Move the one after it in between it and the next point
				if (iPointIndex <= m_dTemperatureProfileArray.length - 4)
				{
					m_dTemperatureProfileArray[iPointIndex + 2][0] = (m_dTemperatureProfileArray[iPointIndex + 1][0] + m_dTemperatureProfileArray[iPointIndex + 3][0]) / 2;
					m_dTemperatureProfileArray[iPointIndex + 2][1] = m_InterpolatedIdealTempProfile.getAt(m_dTemperatureProfileArray[iPointIndex + 2][0]);
				}
			}
		}

    	// Now for the flow rate vs. temp profile:
    	// First use m_InitialInterpolatedFlowRateVsTempProfile to figure out where to put the points
		double dMinTemp = this.m_dIdealTemperatureProfileArray[0][1];
		double dMaxTemp = this.m_dIdealTemperatureProfileArray[this.m_dIdealTemperatureProfileArray.length - 1][1];
		
    	// Add the initial interpolated hold-up time profile to the graph control
	    int iNumPoints = 1000;
	    for (int i = 0; i < iNumPoints; i++)
	    {
	    	double dXPos = (((double)i / (double)(iNumPoints - 1)) * (dMaxTemp - dMinTemp)) + dMinTemp;
	    	contentPane2.m_GraphControlHoldUp.AddDataPoint(iIdealPlotIndexHoldUp, dXPos, m_InitialInterpolatedHoldUpVsTempProfile.getAt(dXPos));
	    }

	    for (int i = 0; i < iNumFlowDataPoints; i++)
		{
			m_dHoldUpArray[i][0] = ((dMaxTemp - dMinTemp) * ((double)i/((double)iNumFlowDataPoints - 1))) + dMinTemp;
			m_dHoldUpArray[i][1] = this.m_InitialInterpolatedHoldUpVsTempProfile.getAt(m_dHoldUpArray[i][0]);
		}
		
		m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
		m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);

		//setContentPane(contentPane2);
		this.jMainScrollPane.setViewportView(contentPane2);
		this.UpdateGraphs();
    }
    
    public void actionPerformed(ActionEvent evt) 
	{
	    String strActionCommand = evt.getActionCommand();

	    if (strActionCommand == "Help")
	    {
			Globals.hbMainHelpBroker.setCurrentID("step-by-step");
			Globals.hbMainHelpBroker.setDisplayed(true);
	    }
	    else if (strActionCommand == "Next Step")
	    {
	    	nextStepButtonPressed();
	    }
	    else if (strActionCommand == "Previous Step")
	    {
			if (m_iStage == 2)
			{
				//setContentPane(contentPane);
				this.jMainScrollPane.setViewportView(contentPane);

				if (task != null)
				{
					task.cancel(true);	
				}
				m_iStage = 1;
			}
			else if (m_iStage == 3)
			{
				if (taskPredict != null)
				{
					taskPredict.cancel(true);
				}
				
				contentPane2.jpanelStep6.setVisible(false);
				contentPane2.jpanelStep5.setVisible(true);
		    	contentPane2.jbtnNextStep.setVisible(true);
				m_iStage = 2;
			}
	    }
	    else if (strActionCommand == "Calculate")
	    {
	    	Frame[] frames = Frame.getFrames();
	    	BackCalculationOptionsDialog dlgBackCalculationOptions = new BackCalculationOptionsDialog(frames[0]);
	    	
	    	// Show the dialog.
	    	dlgBackCalculationOptions.setVisible(true);
	    	
	    	if (dlgBackCalculationOptions.m_bOk == false)
	    		return;

	    	beginBackCalculation(dlgBackCalculationOptions.m_bFlowRateProfileFirst);
	    }
	    else if (strActionCommand == "Stop Calculations")
	    {
	    	task.cancel(true);
	    }
	    else if (strActionCommand == "Next Step2")
	    {
	    	// Fill in the table with the solutes that weren't selected
	    	contentPane2.tmPredictionModel.getDataVector().clear();
	    	for (int i = 0; i < Globals.CompoundIsothermalDataArray.length; i++)
	    	{
	    		Object[] newRow = {Globals.CompoundNameArray[i], null};
    			contentPane2.tmPredictionModel.addRow(newRow);
	    	}
	    	
	    	contentPane2.jpanelStep5.setVisible(false);
	    	contentPane2.jpanelStep6.setVisible(true);
	    	contentPane2.jbtnNextStep.setVisible(false);
	    	contentPane2.jbtnPredict.setEnabled(true);
	    	contentPane2.jProgressBar2.setString("");
	    	contentPane2.jProgressBar2.setIndeterminate(false);
	    	
	    	m_iStage++;
	    }
	    else if (strActionCommand == "Predict")
	    {
	    	contentPane2.jbtnPredict.setEnabled(false);
			contentPane2.jProgressBar2.setIndeterminate(true);
			contentPane2.jProgressBar2.setStringPainted(true);
			contentPane2.jProgressBar2.setString("Please wait, calculating retention...");
			taskPredict = new TaskPredict();
			taskPredict.execute();
	    }
	    else if (strActionCommand == "Preloaded Values")
	    {
	    	Frame[] frames = Frame.getFrames();
	    	PreloadedValuesDialog dlgPreloadedValues = new PreloadedValuesDialog(frames[0]);
	    	
	    	// Show the dialog.
	    	dlgPreloadedValues.setVisible(true);
	    	
	    	if (dlgPreloadedValues.m_bOk == false)
	    		return;
	    	
	    	// Change settings
    		if (Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][0][0] == 0.0)
    		{
    			switchToConstantFlowRateMode();
    	    	contentPane.jtxtFlowRate.setText(((Double)Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][0][1]).toString());
    		}
    		else
    		{
    			switchToConstantPressureMode();    			
    	    	contentPane.jtxtPressure.setText(((Double)Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][0][1]).toString());
    		}
    		
    		if (Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][0][2] == 0.0)
    		{
    			vacuumOutletPressure();
    		}
    		else
    		{
    			ambientOutletPressure();    			
    		}

    		contentPane.jtxtColumnLength.setText(((Double)Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][0][3]).toString());
    		contentPane.jtxtInitialTemperature.setText(((Double)Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][1][0]).toString());
    		contentPane.jtxtInitialTime.setText(((Double)Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][1][1]).toString());
    		int rowcount = contentPane.tmTemperatureProgram.getRowCount();
    		for (int i = 0; i < rowcount; i++)
    		{
        		contentPane.tmTemperatureProgram.removeRow(0);
    		}
    		
    		for (int i = 2; i < Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition].length; i++)
    		{
        		Object[] rowData = {
        				Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][i][0],
        				Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][i][1],
        				Globals.dTemperaturePrograms[dlgPreloadedValues.m_iCondition][i][2]};
        		
    			contentPane.tmTemperatureProgram.addRow(rowData);
    		}
    		
	    	// Put in default values
	    	for (int i = 0; i < contentPane.tmMeasuredRetentionTimes.getRowCount(); i++)
	    	{
	    		contentPane.tmMeasuredRetentionTimes.setValueAt(0.0, i, 1);
	    	}
	    	
			for (int i = 0; i < Globals.dPredefinedValues[dlgPreloadedValues.m_iCondition].length; i++)
			{
	            contentPane.tmMeasuredRetentionTimes.setValueAt(Globals.dPredefinedValues[dlgPreloadedValues.m_iCondition][i], i, 1);
			}
			
	    	performValidations();
	    	
	    }
	    else if (strActionCommand == "Insert Row")
	    {
	    	int iSelectedRow = contentPane.jtblTemperatureProgram.getSelectedRow();
	    	
	    	if (contentPane.jtblTemperatureProgram.getRowCount() == 0)
	    	{
		    	Double dRowValue1 = 20.0;
		    	Double dRowValue2 = m_dInitialTemperature;
		    	Double dRowValue3 = 5.0;
		    	Double dRowData[] = {dRowValue1, dRowValue2, dRowValue3};
		    	contentPane.tmTemperatureProgram.addRow(dRowData);	    		
	    	}
	    	else if (iSelectedRow == -1)
	    	{
		    	Double dRowValue1 = 0.0;
		    	Double dRowValue2 = (Double) contentPane.jtblTemperatureProgram.getValueAt(contentPane.jtblTemperatureProgram.getRowCount() - 1, 1);
		    	Double dRowValue3 = 5.0;
		    	Double dRowData[] = {dRowValue1, dRowValue2, dRowValue3};
		    	contentPane.tmTemperatureProgram.addRow(dRowData);	    		
	    	}
	    	else
	    	{
		    	Double dRowValue1 = 0.0;
		    	Double dRowValue2 = (Double) contentPane.jtblTemperatureProgram.getValueAt(iSelectedRow, 1);
		    	Double dRowValue3 = 5.0;
		    	Double dRowData[] = {dRowValue1, dRowValue2, dRowValue3};
		    	contentPane.tmTemperatureProgram.insertRow(iSelectedRow, dRowData);	    		
	    	}	
	    }
	    else if (strActionCommand == "Remove Row")
	    {
	    	int iSelectedRow = contentPane.jtblTemperatureProgram.getSelectedRow();
	    	
	    	if (iSelectedRow == -1)
	    		iSelectedRow = contentPane.jtblTemperatureProgram.getRowCount() - 1;
	    	
	    	if (iSelectedRow >= 0)
	    	{
	    		contentPane.tmTemperatureProgram.removeRow(iSelectedRow);
	    	}
	    }
	    else if (strActionCommand == "Constant flow rate mode")
	    {
	    	switchToConstantFlowRateMode();
	    }
	    else if (strActionCommand == "Constant pressure mode")
	    {
	    	switchToConstantPressureMode();
	    }
	    else if (strActionCommand == "Vacuum")
	    {
	    	vacuumOutletPressure();
	    }
	    else if (strActionCommand == "Ambient")
	    {
	    	ambientOutletPressure();
	    }
	}

    public void vacuumOutletPressure()
    {
    	contentPane.jrdoVacuum.setSelected(true);
    	contentPane.jrdoAmbient.setSelected(false);
    	this.m_dOutletPressure = .000001;
    	performValidations();
    }
    
    public void ambientOutletPressure()
    {
    	contentPane.jrdoVacuum.setSelected(false);
    	contentPane.jrdoAmbient.setSelected(true);
    	this.m_dOutletPressure = 100; // 100 kPa
    	performValidations();
    }
    
    public void switchToConstantPressureMode()
    {
    	contentPane.jrdoConstantPressure.setSelected(true);
    	contentPane.jrdoConstantFlowRate.setSelected(false);
    	contentPane.jlblFlowRate.setEnabled(false);
    	contentPane.jlblFlowRateUnit.setEnabled(false);
    	contentPane.jtxtFlowRate.setEnabled(false);
    	contentPane.jlblPressure.setEnabled(true);
    	contentPane.jtxtPressure.setEnabled(true);
    	contentPane.jlblPressureUnit.setEnabled(true);
    	performValidations();
    }
    
    public void switchToConstantFlowRateMode()
    {
    	contentPane.jrdoConstantFlowRate.setSelected(true);
    	contentPane.jrdoConstantPressure.setSelected(false);
    	contentPane.jlblFlowRate.setEnabled(true);
    	contentPane.jlblFlowRateUnit.setEnabled(true);
    	contentPane.jtxtFlowRate.setEnabled(true);
    	contentPane.jlblPressure.setEnabled(false);
    	contentPane.jtxtPressure.setEnabled(false);
    	contentPane.jlblPressureUnit.setEnabled(false);
    	performValidations();
    }
    
	//@Override
	public void keyPressed(KeyEvent arg0) 
	{
		
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
			performValidations();
		}
		
	}

	public void performValidations()
	{
		validateInitialTemperature();
		validateInitialTime();
		validateColumnLength();
		validateFlowRate();
		validateInletPressure();
		
		double dInnerDiameter = 0.025; // in cm
		m_V0 = (Math.PI * Math.pow(dInnerDiameter / 2, 2) * (this.m_dColumnLength * 100)) / 1000; // gives the volume in the column (in L)

		int iNumPoints = 10;
		double dLowTemp = 0;
		double dHighTemp = 500;
		
		double[][] dHoldUpArray = new double[iNumPoints][2];
		
		for (int i = 0; i < iNumPoints; i++)
		{
			dHoldUpArray[i][0] = ((dHighTemp - dLowTemp) * ((double)i / (double)(iNumPoints - 1))) + dLowTemp;
			double dGasViscosity = 18.69 * Math.pow(10, -6) * Math.pow((dHoldUpArray[i][0] + 273.15) / 273.15, 0.6958 + -0.0071 * (((dHoldUpArray[i][0] + 273.15) - 273.15) / 273.15));
			double dOmega = this.m_dColumnLength * dGasViscosity * (32.0 / Math.pow(dInnerDiameter / 100, 2));
			double dDeadTime = 0;
			if (contentPane.jrdoConstantFlowRate.isSelected())
			{	// Constant flow rate mode
				double dFirstTerm = (Math.pow(Math.PI, 2) * Math.pow(dInnerDiameter / 10, 4) * Math.pow(this.m_dOutletPressure * 1000.0, 3)) / (48 * 32 * dGasViscosity * Math.pow(101325, 2) * Math.pow(((dHoldUpArray[i][0] + 273.15) / (25.0 + 273.15)) * ((this.m_dFlowRate / (60.0 * 1000.0)) / (dInnerDiameter / 10.0)), 2)); 
				double dSecondTerm = Math.pow(1.0 + ((8.0 * 32.0 * this.m_dColumnLength * dGasViscosity * 101325.0 * (((dHoldUpArray[i][0] + 273.15) / (25.0 + 273.15)) * ((this.m_dFlowRate / (60.0 * 1000.0)) / (dInnerDiameter / 100.0))))/(Math.PI * Math.pow(dInnerDiameter / 10, 3) * Math.pow(this.m_dOutletPressure * 1000.0, 2))), 3.0/2.0) - 1.0;
				dDeadTime = dFirstTerm * dSecondTerm;
			}
			else
			{	// Constant pressure mode
				dDeadTime = (4 * dOmega * this.m_dColumnLength * (Math.pow(this.m_dInletPressure * 1000.0 + 101325.0, 3) - Math.pow(this.m_dOutletPressure * 1000.0, 3))) / (3 * Math.pow(Math.pow(this.m_dInletPressure * 1000.0 + 101325.0, 2) - Math.pow(this.m_dOutletPressure * 1000.0, 2), 2));				
			}

			dHoldUpArray[i][1] = dDeadTime; // in seconds
		}
		
		this.m_InitialInterpolatedHoldUpVsTempProfile = new InterpolationFunction(dHoldUpArray);

		double dMax = 0;
		for (int i = 0; i < contentPane.tmMeasuredRetentionTimes.getRowCount(); i++)
		{
			double dValue = (Double)contentPane.tmMeasuredRetentionTimes.getValueAt(i, 1);
			if (dValue > dMax)
				dMax = dValue;
		}

		// Calculate total time of temperature program
		double dTotalTime = this.m_dInitialTime;
		double dLastTemp = this.m_dInitialTemperature;

		for (int i = 0; i < contentPane.tmTemperatureProgram.getRowCount(); i++)
		{
			double dRamp = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 0);
			double dFinalTemp = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 1);
			double dFinalTime = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 2);
			
			if (dRamp != 0)
				dTotalTime += (dFinalTemp - dLastTemp) / dRamp;
			
			dTotalTime += dFinalTime;
			
			dLastTemp = dFinalTemp;
		}
		
		m_dPlotXMax = Math.max(dTotalTime * 1.2, dMax * 1.02);
		
    	contentPane.m_GraphControlTemp.RemoveAllSeries();
    	contentPane.m_GraphControlHoldUp.RemoveAllSeries();

    	int iIdealPlotIndex = contentPane.m_GraphControlTemp.AddSeries("Ideal Gradient", new Color(0, 0, 0), 1, false, false);
    	int iIdealPlotIndexHoldUp = contentPane.m_GraphControlHoldUp.AddSeries("Ideal Hold Up", new Color(0, 0, 0), 1, false, false);

    	contentPane.m_GraphControlTemp.AddDataPoint(iIdealPlotIndex, 0, m_dInitialTemperature);
    	contentPane.m_GraphControlHoldUp.AddDataPoint(iIdealPlotIndexHoldUp, 0, m_InitialInterpolatedHoldUpVsTempProfile.getAt(m_dInitialTemperature));
    	
    	contentPane.m_GraphControlTemp.AddDataPoint(iIdealPlotIndex, m_dInitialTime * 60, m_dInitialTemperature);
    	contentPane.m_GraphControlHoldUp.AddDataPoint(iIdealPlotIndexHoldUp, m_dInitialTime * 60, m_InitialInterpolatedHoldUpVsTempProfile.getAt(m_dInitialTemperature));

    	dTotalTime = m_dInitialTime;
    	dLastTemp = m_dInitialTemperature;
    	double dFinalTemp = m_dInitialTemperature;

    	for (int i = 0; i < contentPane.tmTemperatureProgram.getRowCount(); i++)
		{
			double dRamp = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 0);
			dFinalTemp = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 1);
			double dFinalTime = (Double)contentPane.tmTemperatureProgram.getValueAt(i, 2);

			if (dRamp != 0)
			{
				double dLastTime = dTotalTime;
				dTotalTime += (dFinalTemp - dLastTemp) / dRamp;
				contentPane.m_GraphControlTemp.AddDataPoint(iIdealPlotIndex, dTotalTime * 60, dFinalTemp);
		    	
				// Add many data points to show flow rate.
				for (int j = 1; j < 500; j++)
				{
					double dTime = ((dTotalTime - dLastTime) * ((double)j / 500.0)) + dLastTime;
					double dTemperature = ((dFinalTemp - dLastTemp) * ((double)j / 500.0)) + dLastTemp;
					contentPane.m_GraphControlHoldUp.AddDataPoint(iIdealPlotIndexHoldUp, dTime * 60, m_InitialInterpolatedHoldUpVsTempProfile.getAt(dTemperature));
				}
			}
			
			if (dFinalTime != 0)
			{
				dTotalTime += dFinalTime;
				contentPane.m_GraphControlTemp.AddDataPoint(iIdealPlotIndex, dTotalTime * 60, dFinalTemp);				
		    	contentPane.m_GraphControlHoldUp.AddDataPoint(iIdealPlotIndexHoldUp, dTotalTime * 60, m_InitialInterpolatedHoldUpVsTempProfile.getAt(dFinalTemp));
			}
			
			dLastTemp = dFinalTemp;
		}

		if (contentPane.tmTemperatureProgram.getRowCount() > 0)
		{
			contentPane.m_GraphControlTemp.AddDataPoint(iIdealPlotIndex, m_dPlotXMax * 60, dFinalTemp);
    		contentPane.m_GraphControlHoldUp.AddDataPoint(iIdealPlotIndexHoldUp, m_dPlotXMax * 60, m_InitialInterpolatedHoldUpVsTempProfile.getAt(dFinalTemp));
		}
    	else
    	{
			contentPane.m_GraphControlTemp.AddDataPoint(iIdealPlotIndex, m_dPlotXMax * 60, m_dInitialTemperature);
	    	contentPane.m_GraphControlHoldUp.AddDataPoint(iIdealPlotIndexHoldUp, m_dPlotXMax * 60, m_InitialInterpolatedHoldUpVsTempProfile.getAt(m_dInitialTemperature));
    	}

   		contentPane.m_GraphControlTemp.AutoScaleX();
   		contentPane.m_GraphControlTemp.AutoScaleY();
    	
    	contentPane.m_GraphControlTemp.repaint();   
    	
   		contentPane.m_GraphControlHoldUp.AutoScaleX();
   		contentPane.m_GraphControlHoldUp.AutoScaleY();
    	
    	contentPane.m_GraphControlHoldUp.repaint();   	
	}

	//@Override
	public void focusGained(FocusEvent e) 
	{
		
	}

	//@Override
	public void focusLost(FocusEvent e) 
	{
		performValidations();
	}

	//@Override
	public void valueChanged(ListSelectionEvent arg0) 
	{
		performValidations();
		
	}
	
	//@Override
	public void autoScaleChanged(AutoScaleEvent event) 
	{
		//if(event.getSource()==contentPane.m_GraphControl)
		//{
/*			if (event.getAutoScaleXState() == true)
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
		else if (event.getSource()==contentPane.m_GraphControlFlow)
		{
			if (event.getAutoScaleXState() == true)
				contentPane.jbtnAutoscaleXFlow.setSelected(true);
			else
				contentPane.jbtnAutoscaleXFlow.setSelected(false);
			
			if (event.getAutoScaleYState() == true)
				contentPane.jbtnAutoscaleYFlow.setSelected(true);
			else
				contentPane.jbtnAutoscaleYFlow.setSelected(false);
			
			if (event.getAutoScaleXState() == true && event.getAutoScaleYState() == true)
				contentPane.jbtnAutoscaleFlow.setSelected(true);			
			else
				contentPane.jbtnAutoscaleFlow.setSelected(false);				
		}*/
	}

	@Override
	public void tableChanged(TableModelEvent arg0) 
	{
		if (m_bDoNotChangeTable)
		{
			m_bDoNotChangeTable = false;
			return;
		}
		
		if(arg0.getSource() == contentPane.tmTemperatureProgram)
		{
			int row = arg0.getFirstRow();
			int column = arg0.getColumn();
			
			if (column == 0)
			{
				Double dNewValue = (Double) contentPane.tmTemperatureProgram.getValueAt(row, 0);
				
				double dTemp = dNewValue;
				if (dTemp < 0)
					dTemp = 0;
				if (dTemp > 1000)
					dTemp = 1000;
				
		    	m_bDoNotChangeTable = true;
				contentPane.tmTemperatureProgram.setValueAt(dTemp, row, column);
			}
			else if (column == 1)
			{
				Double dNewValue = (Double) contentPane.tmTemperatureProgram.getValueAt(row, 1);
				
				double dTemp = dNewValue;
				if (dTemp < 0)
					dTemp = 0;
				if (dTemp > 500)
					dTemp = 500;
				if (row == 0)
				{
					if (dTemp < (Double)this.m_dInitialTemperature)
					{
						dTemp = this.m_dInitialTemperature;
					}
				}
				if (row < contentPane.tmTemperatureProgram.getRowCount() - 1)
				{
					if (dTemp > (Double)contentPane.tmTemperatureProgram.getValueAt(row + 1, column))
					{
						dTemp = (Double)contentPane.tmTemperatureProgram.getValueAt(row + 1, column);
					}
				}
				if (row > 0)
				{
					if (dTemp < (Double)contentPane.tmTemperatureProgram.getValueAt(row - 1, column))
					{
						dTemp = (Double)contentPane.tmTemperatureProgram.getValueAt(row - 1, column);
					}
				}
		    	m_bDoNotChangeTable = true;
				contentPane.tmTemperatureProgram.setValueAt(dTemp, row, column);
			}
			else if (column == 2)
			{
				Double dNewValue = (Double) contentPane.tmTemperatureProgram.getValueAt(row, 2);
				
				double dTemp = dNewValue;
				if (dTemp < 0)
					dTemp = 0;
				if (dTemp > 1000)
					dTemp = 1000;
				
		    	m_bDoNotChangeTable = true;
				contentPane.tmTemperatureProgram.setValueAt(dTemp, row, column);
			}
		}
		else if (arg0.getSource() == contentPane.tmMeasuredRetentionTimes)
		{
			
		}
		
		performValidations();
		
		contentPane.m_GraphControlTemp.removeAllLineMarkers();
		contentPane.m_GraphControlHoldUp.removeAllLineMarkers();
		
		for (int i = 0; i < contentPane.tmMeasuredRetentionTimes.getRowCount(); i++)
		{
			contentPane.m_GraphControlTemp.addLineMarker((Double)contentPane.tmMeasuredRetentionTimes.getValueAt(i,1), (String)contentPane.tmMeasuredRetentionTimes.getValueAt(i,0));
			contentPane.m_GraphControlHoldUp.addLineMarker((Double)contentPane.tmMeasuredRetentionTimes.getValueAt(i,1), (String)contentPane.tmMeasuredRetentionTimes.getValueAt(i,0));
		}

		contentPane.m_GraphControlTemp.repaint();
		contentPane.m_GraphControlHoldUp.repaint();
		
		// Make sure there are at least 6 unique measured retention times
		int iNumDifferent = 0;
		
		for (int i = 0; i < contentPane.tmMeasuredRetentionTimes.getRowCount(); i++)
		{
			double dValue = (Double)contentPane.tmMeasuredRetentionTimes.getValueAt(i, 1);
			boolean bSame = false;
			
			for (int j = 0; j < contentPane.tmMeasuredRetentionTimes.getRowCount(); j++)
			{
				if (j == i)
					continue;
				
				if ((Double)contentPane.tmMeasuredRetentionTimes.getValueAt(j, 1) == dValue)
				{
					bSame = true;
					break;
				}
			}
			
			if (bSame == false)
				iNumDifferent++;
		}
		
		if (iNumDifferent >= 6)
			contentPane.jbtnNextStep.setEnabled(true);
		else
			contentPane.jbtnNextStep.setEnabled(false);
	}
	
	public void UpdateGraphs()
	{
		synchronized(contentPane2.m_GraphControlTemp.lockObject)
		{
		synchronized(contentPane2.m_GraphControlHoldUp.lockObject)
		{
		// Update the graphs with the new m_dGradientArray markers and the m_InterpolatedGradient (and the same with the flow graph)
		contentPane2.m_GraphControlTemp.RemoveSeries(m_iInterpolatedTempProgramSeries);
		contentPane2.m_GraphControlTemp.RemoveSeries(m_iTempProgramMarkerSeries);
		
		contentPane2.m_GraphControlHoldUp.RemoveSeries(m_iInterpolatedHoldUpSeries);
		contentPane2.m_GraphControlHoldUp.RemoveSeries(m_iHoldUpMarkerSeries);
		
	    m_iInterpolatedTempProgramSeries = contentPane2.m_GraphControlTemp.AddSeries("Interpolated Gradient", new Color(255,0,0), 1, false, false);
	    m_iTempProgramMarkerSeries = contentPane2.m_GraphControlTemp.AddSeries("Gradient Markers", new Color(255,0,0), 1, true, false);

	    m_iInterpolatedHoldUpSeries = contentPane2.m_GraphControlHoldUp.AddSeries("Interpolated Flow", new Color(255,0,0), 1, false, false);
	    m_iHoldUpMarkerSeries = contentPane2.m_GraphControlHoldUp.AddSeries("Flow Rate Markers", new Color(255,0,0), 1, true, false);

	    double dMinTemp = this.m_dHoldUpArray[0][0];
	    double dMaxTemp = this.m_dHoldUpArray[m_dHoldUpArray.length - 1][0];
	    
	    int iNumPoints = 1000;
	    for (int i = 0; i < iNumPoints; i++)
	    {
	    	double dXPos = ((double)i / (double)(iNumPoints - 1)) * (m_dPlotXMax2 * 60);
	    	contentPane2.m_GraphControlTemp.AddDataPoint(m_iInterpolatedTempProgramSeries, dXPos, m_InterpolatedTemperatureProfile.getAt(dXPos / 60));
	    	dXPos = (((double)i / (double)(iNumPoints - 1)) * (dMaxTemp - dMinTemp)) + dMinTemp;
	    	contentPane2.m_GraphControlHoldUp.AddDataPoint(m_iInterpolatedHoldUpSeries, dXPos, m_InterpolatedHoldUp.getAt(dXPos));
	    }
	    
	    for (int i = 0; i < m_dTemperatureProfileArray.length; i++)
	    {
	    	contentPane2.m_GraphControlTemp.AddDataPoint(m_iTempProgramMarkerSeries, m_dTemperatureProfileArray[i][0] * 60, m_dTemperatureProfileArray[i][1]);
	    }
		
	    for (int i = 0; i < m_dHoldUpArray.length; i++)
	    {
	    	contentPane2.m_GraphControlHoldUp.AddDataPoint(m_iHoldUpMarkerSeries, m_dHoldUpArray[i][0], m_dHoldUpArray[i][1]);
	    }
	    
	    contentPane2.m_GraphControlTemp.AutoScaleX();
	    contentPane2.m_GraphControlTemp.AutoScaleY();
	    contentPane2.m_GraphControlHoldUp.AutoScaleX();
	    contentPane2.m_GraphControlHoldUp.AutoScaleY();
     
	    contentPane2.m_GraphControlTemp.repaint();
	    contentPane2.m_GraphControlHoldUp.repaint();
		}
		}
	}
	
	public void beginBackCalculation(boolean bFlowRateProfileBackCalculationFirst)
	{
		contentPane2.jbtnCalculate.setEnabled(false);
		//contentPane2.jbtnCalculate.setText("Please wait...");
		contentPane2.jProgressBar.setIndeterminate(true);
		contentPane2.jProgressBar.setStringPainted(true);
		contentPane2.jProgressBar.setString("Please wait, optimization in progress...");
		task = new Task();
		task.setOptimizationOrder(bFlowRateProfileBackCalculationFirst);
        task.execute();
	}
	
    class Task extends SwingWorker<Void, Void> 
    {
        /*
         * Main task. Executed in background thread.
         */
    	private boolean bFlowRateProfileBackCalculationFirst = true;
    	
    	public void setOptimizationOrder(boolean bFlowRateProfileBackCalculationFirst)
    	{
    		this.bFlowRateProfileBackCalculationFirst = bFlowRateProfileBackCalculationFirst;
    	}
    	
        @Override
        public Void doInBackground() 
        {
            backCalculate(this, bFlowRateProfileBackCalculationFirst);

            return null;
        }
        
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() 
        {
    		contentPane2.jbtnNextStep.setEnabled(true);
    		contentPane2.jProgressBar.setIndeterminate(false);
    		contentPane2.jProgressBar.setStringPainted(true);
    		contentPane2.jProgressBar.setString("Optimization complete! Continue to next step.");
        }
    }

    class TaskPredict extends SwingWorker<Void, Void> 
    {
        // Main task. Executed in background thread.
        
    	@Override
        public Void doInBackground() 
        {
    		NumberFormat formatter1 = new DecimalFormat("#0.000");
    		NumberFormat formatter2 = new DecimalFormat("#0.0000");
    		
    		double dPercentErrorInk = 0.042;
    		
    		int iNumCompounds = Globals.CompoundIsothermalDataArray.length;
    		for (int iCompound = 0; iCompound < iNumCompounds; iCompound++)
    		{
				if (taskPredict.isCancelled())
				{
					return null;
				}

				InterpolationFunction IsocraticData = new InterpolationFunction(Globals.CompoundIsothermalDataArray[iCompound]);
				
				double dtRFinal = 0;
				double dXPosition = 0;
				double dLastXPosition = 0;
				double dXMovement = 0;
				Boolean bIsEluted = false;
				double dTcA = 0;
				double dTcB = 0;
				double dCurVal = 0;
				
				// {low temp, high temp, fraction, abs error in tr contributed by this segment}
				double[][] dFractionkMovedCompound = {
						{0,70,0,0,0},
						{70,90,0,0,0},
						{90,110,0,0,0},
						{110,130,0,0,0},
						{130,150,0,0,0},
						{150,170,0,0,0},
						{170,190,0,0,0},
						{190,210,0,0,0},
						{210,230,0,0,0},
						{230,250,0,0,0},
						{250,270,0,0,0},
						{270,290,0,0,0},
						{290,310,0,0,0},
						{310,800,0,0,0}};
				
				// Grab the first temp
				dTcA = m_InterpolatedTemperatureProfile.getAt(0);

				for (double t = 0; t <= (Double) m_vectCalCompounds.get(m_vectCalCompounds.size() - 1)[1] * 1.5; t += m_dtstep)
				{
					// Grab the second temp
					dTcB = m_InterpolatedTemperatureProfile.getAt(t + m_dtstep);
					
					// Find the average of the two temps
					double dTc = (dTcA + dTcB) / 2;
					
					// Figure out which k value we're using
					int thisIndex = 0;
					for (int i = 0; i < dFractionkMovedCompound.length; i++)
					{
						if (dFractionkMovedCompound[i][0] <= dTc && dFractionkMovedCompound[i][1] > dTc)
						{
							thisIndex = i;
							break;
						}
					}

					// Get the hold-up time at this temp
					double dHc = m_InterpolatedHoldUp.getAt(dTc) / 60;
					// Get the amount of dead time traveled in dtstep
					dCurVal = m_dtstep / (1 + Math.pow(10, IsocraticData.getAt(dTc)));
					// Determine what fraction of the column it moved
					dXMovement = dCurVal / dHc;
					
					dFractionkMovedCompound[thisIndex][2] += dXMovement;
					double dBigFraction = (dFractionkMovedCompound[thisIndex][2] - dXMovement) / dFractionkMovedCompound[thisIndex][2];
					double dSmallFraction = 1 - dBigFraction;
					dFractionkMovedCompound[thisIndex][3] = (dFractionkMovedCompound[thisIndex][3] * dBigFraction) + ((Math.pow(10, IsocraticData.getAt(dTc)) * dHc * (dPercentErrorInk / 100)) * dSmallFraction);
					dFractionkMovedCompound[thisIndex][4] = (dFractionkMovedCompound[thisIndex][4] * dBigFraction) + (Math.pow(10, IsocraticData.getAt(dTc)) * dSmallFraction);
					                                   
					// Add that to the running total
					dLastXPosition = dXPosition;
					dXPosition += dXMovement;
					
					if (dXPosition >= 1)
					{
						dtRFinal = (((1 - dLastXPosition)/(dXPosition - dLastXPosition)) * m_dtstep) + t;
						bIsEluted = true;
						break;
					}
					
					dTcA = dTcB;
				}
				
    			if (bIsEluted)
    			{
    				contentPane2.tmPredictionModel.setValueAt(formatter1.format(dtRFinal), iCompound, 1);
    			}
    			else
    			{
    				contentPane2.tmPredictionModel.setValueAt("Did not elute", iCompound, 1);
    			}

    			// Now calculate final error in the projection
    			double dFinalError = 0;
    			double dkavg = 0;
				for (int i = 0; i < dFractionkMovedCompound.length; i++)
				{
					dFinalError += Math.pow(dFractionkMovedCompound[i][3] * dFractionkMovedCompound[i][2], 2);
					dkavg += dFractionkMovedCompound[i][4] * dFractionkMovedCompound[i][2];
				}
				dFinalError = Math.sqrt(dFinalError);
				contentPane2.tmPredictionModel.setValueAt(/*"\u00b1" + */formatter2.format(dFinalError), iCompound, 2);

				/*dtRFinal = 0;
				dXPosition = 0;
				dLastXPosition = 0;
				dXMovement = 0;
				bIsEluted = false;
				dTcA = 0;
				dTcB = 0;
				dCurVal = 0;
				
				dTcA = m_InterpolatedIdealTempProfile.getAt(0);
				
				for (double t = 0; t <= (Double) m_vectCalCompounds.get(m_vectCalCompounds.size() - 1)[1] * 1.5; t += m_dtstep)
				{
					dTcB = m_InterpolatedIdealTempProfile.getAt(t + m_dtstep);
					
					double dTc = (dTcA + dTcB) / 2;
					
					dCurVal = m_dtstep / (1 + Math.pow(10, IsocraticData.getAt(dTc)));
					
					double dt0 = m_InitialInterpolatedHoldUpVsTempProfile.getAt(m_InterpolatedIdealTempProfile.getAt(t)) / 60;
					
					dXMovement = dCurVal / dt0;
					
					dLastXPosition = dXPosition;
					dXPosition += dXMovement;
					
					if (dXPosition >= 1)
					{
						dtRFinal = (((1 - dLastXPosition)/(dXPosition - dLastXPosition)) * m_dtstep) + t;
						bIsEluted = true;
						break;
					}
					
					dTcA = dTcB;
				}
				
    			if (bIsEluted)
    			{
    				contentPane2.tmPredictionModel.setValueAt(formatter.format(dtRFinal), iCompound, 2);
    			}
    			else
    			{
    				contentPane2.tmPredictionModel.setValueAt("Did not elute", iCompound, 2);
    			}*/
    		}
    				
            return null;
        }
        
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() 
        {
    		contentPane2.jProgressBar2.setIndeterminate(false);
    		contentPane2.jProgressBar2.setStringPainted(true);
    		contentPane2.jProgressBar2.setString("Retention predictions complete.");
        }
    }
    
    /*public double getFlowRate(double dTime)
    {
    	double dFlowRate = (this.m_InterpolatedFlowRate.getAt(dTime) * this.m_InterpolatedFlowRateVsTempProfile.getAt(this.m_InterpolatedTemperatureProfile.getAt(dTime))) + this.m_InterpolatedFlowRateVsTempProfile.getAt(this.m_InterpolatedTemperatureProfile.getAt(dTime));
    	return dFlowRate;
    }*/
    
    public double calcRetentionError(double dtstep, int iNumCompoundsToInclude)
    {
		NumberFormat formatter = new DecimalFormat("#0.0000");
    	double dRetentionError = 0;
		
		for (int iCompound = 0; iCompound < iNumCompoundsToInclude; iCompound++)
		{
			double dtRFinal = 0;
			double dXPosition = 0;
			double dLastXPosition = 0;
			double dXMovement = 0;
			boolean bIsEluted = false;
			double dTcA = 0;
			double dTcB = 0;
			double dCurVal = 0;
			
			dTcA = m_InterpolatedTemperatureProfile.getAt(0);
			
			for (double t = 0; t <= (Double) m_vectCalCompounds.get(m_vectCalCompounds.size() - 1)[1] * 1.5; t += dtstep)
			{
				dTcB = m_InterpolatedTemperatureProfile.getAt(t + dtstep);

				double dTc = (dTcA + dTcB) / 2;
				double dHc = m_InterpolatedHoldUp.getAt(dTc) / 60;

				dCurVal = dtstep / (1 + Math.pow(10, m_AlkaneIsothermalDataInterpolated[iCompound].getAt(dTc)));
				
				dXMovement = dCurVal / dHc;
				
				dLastXPosition = dXPosition;
				dXPosition += dXMovement;
				
				if (dXPosition >= 1)
				{
					dtRFinal = (((1 - dLastXPosition)/(dXPosition - dLastXPosition)) * dtstep) + t;
					bIsEluted = true;
					break;
				}
				
				dTcA = dTcB;
			}
			
			if (bIsEluted)
			{
				dRetentionError += Math.pow(dtRFinal - (Double)m_vectCalCompounds.get(iCompound)[1], 2);
				contentPane2.tmOutputModel.setValueAt(formatter.format(dtRFinal), iCompound, 2);
				contentPane2.tmOutputModel.setValueAt(formatter.format(dtRFinal - (Double)m_vectCalCompounds.get(iCompound)[1]), iCompound, 3);
			}
			else
			{
				dRetentionError += Math.pow((Double)m_vectCalCompounds.get(iCompound)[1], 2);
				contentPane2.tmOutputModel.setValueAt("Did not elute", iCompound, 2);
				contentPane2.tmOutputModel.setValueAt("-", iCompound, 3);
			}
		}
				
    	return dRetentionError;
    }
    
    public void UpdateTime(long starttime)
    {
		NumberFormat timeformatter = new DecimalFormat("00");
		
		long currentTime = System.currentTimeMillis();
		long lNumSecondsPassed = (currentTime - starttime) / 1000;
		long lNumDaysPassed = lNumSecondsPassed / (24 * 60 * 60);
		lNumSecondsPassed -= lNumDaysPassed * (24 * 60 * 60);
		long lNumHoursPassed = lNumSecondsPassed / (60 * 60);
		lNumSecondsPassed -= lNumHoursPassed * (60 * 60);
		long lNumMinutesPassed = lNumSecondsPassed / (60);
		lNumSecondsPassed -= lNumMinutesPassed * (60);
		
		String strProgress2 = "";
		strProgress2 += timeformatter.format(lNumHoursPassed) + ":" + timeformatter.format(lNumMinutesPassed) + ":" + timeformatter.format(lNumSecondsPassed);
		contentPane2.jlblTimeElapsed.setText(strProgress2);
    }
    
    public double calcAngleDifferenceHoldUp(int iIndex)
    {
    	double dTotalAngleError = 0;
    	double dHoldUpRange = 20;
    	
    	for (int i = 0; i < this.m_dHoldUpArray.length; i++)
    	{
        	if (i < 2)
        		continue;
        	
        	double dTime2 = this.m_dHoldUpArray[i][0];
        	double dHoldUp2 = this.m_dHoldUpArray[i][1];
        	double dTime1 = this.m_dHoldUpArray[i - 1][0];
        	double dHoldUp1 = this.m_dHoldUpArray[i - 1][1];
        	double dTime0 = this.m_dHoldUpArray[i - 2][0];
        	double dHoldUp0 = this.m_dHoldUpArray[i - 2][1];
        	
        	// First determine angle of previous segment
    		double dPreviousAdjacent = (dHoldUp1 - dHoldUp0) / dHoldUpRange;
    		double dPreviousOpposite = dTime1 - dTime0;
    		double dPreviousAngle;
    		if (dPreviousAdjacent == 0)
    			dPreviousAngle = Math.PI / 2; // 90 degrees
    		else
    			dPreviousAngle = Math.atan(dPreviousOpposite / dPreviousAdjacent);
    		
    		if (dPreviousAngle < 0)
    			dPreviousAngle = Math.PI + dPreviousAngle;
    		
    		double dAdjacent = (dHoldUp2 - dHoldUp1) / dHoldUpRange;
    		double dOpposite = dTime2 - dTime1;
    		double dNewAngle;
    		if (dAdjacent == 0)
    			dNewAngle = Math.PI / 2; // 90 degrees
    		else
    			dNewAngle = Math.atan(dOpposite / dAdjacent);
    		
    		if (dNewAngle < 0)
    			dNewAngle = Math.PI + dNewAngle;
    		
    		double dFactor1 = 300;
    		double dAngleError = Math.pow((Math.abs(dNewAngle - dPreviousAngle) / (Math.PI)) * dFactor1, 2) + 1;
    		dTotalAngleError += dAngleError;
    	}
    	
     	return dTotalAngleError;
    }
    
    public double calcAngleDifferenceTemp(int iIndex)
    {
    	double dTotalAngleError = 0;
    	double dMaxRampRate = 70;
    	
    	for (int i = 0; i < this.m_dTemperatureProfileArray.length; i++)
    	{
        	if (i < 2)
        		continue;
        	
        	double dTime2 = this.m_dTemperatureProfileArray[i][0];
        	double dTemp2 = this.m_dTemperatureProfileArray[i][1];
        	double dTime1 = this.m_dTemperatureProfileArray[i - 1][0];
        	double dTemp1 = this.m_dTemperatureProfileArray[i - 1][1];
        	double dTime0 = this.m_dTemperatureProfileArray[i - 2][0];
        	double dTemp0 = this.m_dTemperatureProfileArray[i - 2][1];
        	
        	// Check if the previous point is a corner
        	// If it is, then don't worry about the angle - return 0
    		boolean bIsCorner = false;
    		
        	for (int j = 0; j < this.m_dIdealTemperatureProfileArray.length - 1; j++)
    		{
    			if (this.m_dIdealTemperatureProfileArray[j][0] == dTime1)
    			{
    				bIsCorner = true;
    				break;
    			}
    		}
        	
        	if (bIsCorner)
        		continue;

    	   	// First determine angle of previous segment
    		double dPreviousAdjacent = (dTemp1 - dTemp0) / dMaxRampRate;
    		double dPreviousOpposite = dTime1 - dTime0;
    		double dPreviousAngle;
    		if (dPreviousAdjacent == 0)
    			dPreviousAngle = Math.PI / 2; // 90 degrees
    		else
    			dPreviousAngle = Math.atan(dPreviousOpposite / dPreviousAdjacent);
    		
    		if (dPreviousAngle < 0)
    			dPreviousAngle = Math.PI + dPreviousAngle;
    		
    		double dAdjacent = (dTemp2 - dTemp1) / dMaxRampRate;
    		double dOpposite = dTime2 - dTime1;
    		double dNewAngle;
    		if (dAdjacent == 0)
    			dNewAngle = Math.PI / 2; // 90 degrees
    		else
    			dNewAngle = Math.atan(dOpposite / dAdjacent);
    		
    		if (dNewAngle < 0)
    			dNewAngle = Math.PI + dNewAngle;
    		
    		double dFactor1 = 30;
    		double dAngleError = Math.pow((Math.abs(dNewAngle - dPreviousAngle) / (Math.PI)) * dFactor1, 2) + 1;
    		dTotalAngleError += dAngleError;
    	}
    	
     	return dTotalAngleError;
    }
    
/*    public double calcAngleDifference3(int iIndex)
    {
    	if (iIndex <= 1)
    		return 1;
    	
    	double dMaxRampRate = 70;
    	
    	double dTime2 = this.m_dTemperatureProfileArray[iIndex][0];
    	double dTemp2 = this.m_dTemperatureProfileArray[iIndex][1];
    	double dTime1 = this.m_dTemperatureProfileArray[iIndex - 1][0];
    	double dTemp1 = this.m_dTemperatureProfileArray[iIndex - 1][1];
    	double dTime0 = this.m_dTemperatureProfileArray[iIndex - 2][0];
    	double dTemp0 = this.m_dTemperatureProfileArray[iIndex - 2][1];
    	
    	// Check if the previous point is a corner
    	// If it is, then don't worry about the angle - return 0
		for (int i = 0; i < this.m_dIdealTemperatureProfileArray.length - 1; i++)
		{
			if (this.m_dIdealTemperatureProfileArray[i][0] == dTime1)
				return 1;
		}
    	
    	// First determine angle of previous segment
		double dPreviousAdjacent = (dTemp1 - dTemp0) / dMaxRampRate;
		double dPreviousOpposite = dTime1 - dTime0;
		double dPreviousAngle;
		if (dPreviousAdjacent == 0)
			dPreviousAngle = Math.PI / 2; // 90 degrees
		else
			dPreviousAngle = Math.atan(dPreviousOpposite / dPreviousAdjacent);
		
		if (dPreviousAngle < 0)
			dPreviousAngle = Math.PI + dPreviousAngle;
		
		double dAdjacent = (dTemp2 - dTemp1) / dMaxRampRate;
		double dOpposite = dTime2 - dTime1;
		double dNewAngle;
		if (dAdjacent == 0)
			dNewAngle = Math.PI / 2; // 90 degrees
		else
			dNewAngle = Math.atan(dOpposite / dAdjacent);
		
		if (dNewAngle < 0)
			dNewAngle = Math.PI + dNewAngle;
		
		double dFactor1 = 1;
		double dAngleError = Math.pow((Math.abs(dNewAngle - dPreviousAngle) / (Math.PI)) * dFactor1, 2) + 1;
    	return dAngleError;
    }*/
    
 	public double goldenSectioningSearchTemperatureProfile(int iIndex, double dStep, double dPrecision, double dMaxChangeAtOnce, boolean bMinimizeAngles)
 	{
		double dRetentionError = 1;
		double x1;
		double x2;
		double x3;
		double dRetentionErrorX1;
		double dRetentionErrorX2;
		double dRetentionErrorX3;
		double dAngleErrorX1;
		double dAngleErrorX2;
		double dAngleErrorX3;
		
		double dLastTempGuess = m_dTemperatureProfileArray[iIndex][1];
		
		// Find bounds
		x1 = m_dTemperatureProfileArray[iIndex][1];
		dRetentionErrorX1 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
		if (bMinimizeAngles)
			dAngleErrorX1 = calcAngleDifferenceTemp(iIndex);
		else
			dAngleErrorX1 = 1;
		
		x2 = x1 + dStep;
		m_dTemperatureProfileArray[iIndex][1] = x2;
		m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
		dRetentionErrorX2 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
		if (bMinimizeAngles)
			dAngleErrorX2 = calcAngleDifferenceTemp(iIndex);
		else
			dAngleErrorX2 = 1;
		
		if (dRetentionErrorX2 * dAngleErrorX2 < dRetentionErrorX1 * dAngleErrorX1)
		{
			// We're going in the right direction
			x3 = x2;
			dRetentionErrorX3 = dRetentionErrorX2;
			dAngleErrorX3 = dAngleErrorX2;
			
			x2 = (x3 - x1) * this.m_dGoldenRatio + x3;
			
			m_dTemperatureProfileArray[iIndex][1] = x2;
			m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
			dRetentionErrorX2 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			if (bMinimizeAngles)
				dAngleErrorX2 = calcAngleDifferenceTemp(iIndex);
			else
				dAngleErrorX2 = 1;
			

			while (dRetentionErrorX2 * dAngleErrorX2 < dRetentionErrorX3 * dAngleErrorX3 && x2 < dLastTempGuess + dMaxChangeAtOnce)
			{
				x1 = x3;
				dRetentionErrorX1 = dRetentionErrorX3;
				dAngleErrorX1 = dAngleErrorX3;
				x3 = x2;
				dRetentionErrorX3 = dRetentionErrorX2;
				dAngleErrorX3 = dAngleErrorX2;
				
				x2 = (x3 - x1) * this.m_dGoldenRatio + x3;
				
				m_dTemperatureProfileArray[iIndex][1] = x2;
				m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
				dRetentionErrorX2 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
				if (bMinimizeAngles)
					dAngleErrorX2 = calcAngleDifferenceTemp(iIndex);
				else
					dAngleErrorX2 = 1;
			}
		}
		else
		{
			// We need to go in the opposite direction
			x3 = x1;
			dRetentionErrorX3 = dRetentionErrorX1;
			dAngleErrorX3 = dAngleErrorX1;
			
			x1 = x3 - (x2 - x3) * this.m_dGoldenRatio;
			
			m_dTemperatureProfileArray[iIndex][1] = x1;
			m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
			dRetentionErrorX1 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			if (bMinimizeAngles)
				dAngleErrorX1 = calcAngleDifferenceTemp(iIndex);
			else
				dAngleErrorX1 = 1;

			while (dRetentionErrorX1 * dAngleErrorX1 < dRetentionErrorX3 * dAngleErrorX3 && x1 > dLastTempGuess - dMaxChangeAtOnce)
			{
				x2 = x3;
				dRetentionErrorX2 = dRetentionErrorX3;
				dAngleErrorX2 = dAngleErrorX3;
				x3 = x1;
				dRetentionErrorX3 = dRetentionErrorX1;
				dAngleErrorX3 = dAngleErrorX1;

				x1 = x3 - (x2 - x3) * this.m_dGoldenRatio;
				
				m_dTemperatureProfileArray[iIndex][1] = x1;
				m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
				dRetentionErrorX1 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
				if (bMinimizeAngles)
					dAngleErrorX1 = calcAngleDifferenceTemp(iIndex);
				else
					dAngleErrorX1 = 1;
			}
		}
		
		// Now we have our bounds (x1 to x2) and the results of one guess (x3)
		if (x2 > dLastTempGuess + dMaxChangeAtOnce)
		{
			m_dTemperatureProfileArray[iIndex][1] = dLastTempGuess + dMaxChangeAtOnce;
			m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
			dRetentionError = calcRetentionError(m_dtstep, m_vectCalCompounds.size());

			return dRetentionError;
		}
		
		if (x1 < dLastTempGuess - dMaxChangeAtOnce)
		{
			m_dTemperatureProfileArray[iIndex][1] = dLastTempGuess - dMaxChangeAtOnce;
			m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
			dRetentionError = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			
			return dRetentionError;
		}
		
		// Loop of optimization
		while ((x2 - x1) > dPrecision)
		{
			double x4;
			double dRetentionErrorX4;
			double dAngleErrorX4;
			
			// Is the bigger gap between x3 and x2 or x3 and x1?
			if (x2 - x3 > x3 - x1) 
			{
				// x3 and x2, so x4 must be placed between them
				x4 = x3 + (2 - this.m_dGoldenRatio) * (x2 - x3);
			}
			else 
			{
				// x1 and x3, so x4 must be placed between them
				x4 = x3 - (2 - this.m_dGoldenRatio) * (x3 - x1);
			}

			
			m_dTemperatureProfileArray[iIndex][1] = x4;
			m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
			dRetentionErrorX4 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			if (bMinimizeAngles)
				dAngleErrorX4 = calcAngleDifferenceTemp(iIndex);
			else
				dAngleErrorX4 = 1;
			
			// Decide what to do next
			if (dRetentionErrorX4 * dAngleErrorX4 < dRetentionErrorX3 * dAngleErrorX3)
			{
				// Our new guess was better
				// Where did we put our last guess again?
				if (x2 - x3 > x3 - x1) 
				{
					// x4 was in between x3 and x2
					x1 = x3;
					dRetentionErrorX1 = dRetentionErrorX3;
					dAngleErrorX1 = dAngleErrorX3;
					x3 = x4;
					dRetentionErrorX3 = dRetentionErrorX4;
					dAngleErrorX3 = dAngleErrorX4;
				}
				else
				{
					// x4 was in between x1 and x3
					x2 = x3;
					dRetentionErrorX2 = dRetentionErrorX3;							
					dAngleErrorX2 = dAngleErrorX3;
					x3 = x4;
					dRetentionErrorX3 = dRetentionErrorX4;
					dAngleErrorX3 = dAngleErrorX4;
				}
			}
			else
			{
				// Our new guess was worse
				if (x2 - x3 > x3 - x1) 
				{
					// x4 was in between x3 and x2
					x2 = x4;
					dRetentionErrorX2 = dRetentionErrorX4;
					dAngleErrorX2 = dAngleErrorX4;
				}
				else
				{
					// x4 was in between x1 and x3
					x1 = x4;
					dRetentionErrorX1 = dRetentionErrorX4;							
					dAngleErrorX1 = dAngleErrorX4;
				}
			}
		}
		
		// Restore profile to best value
		m_dTemperatureProfileArray[iIndex][1] = x3;
		m_InterpolatedTemperatureProfile = new LinearInterpolationFunction(m_dTemperatureProfileArray);
		dRetentionError = dRetentionErrorX3;
 		
 		return dRetentionError;
 	}
 	
 	public double goldenSectioningSearchHoldUp(int iIndex, double dStep, double dPrecision, double dMaxChangeAtOnce, boolean bMinimizeAngles)
 	{
		double dRetentionError = 1;
		double x1;
		double x2;
		double x3;
		double dRetentionErrorX1;
		double dRetentionErrorX2;
		double dRetentionErrorX3;
		double dAngleErrorX1;
		double dAngleErrorX2;
		double dAngleErrorX3;
		
		double dLastFGuess = m_dHoldUpArray[iIndex][1];

		// Find bounds
		x1 = this.m_dHoldUpArray[iIndex][1];
		dRetentionErrorX1 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
		if (bMinimizeAngles)
			dAngleErrorX1 = calcAngleDifferenceHoldUp(iIndex);
		else
			dAngleErrorX1 = 1;
		
		x2 = x1 + dStep;
		m_dHoldUpArray[iIndex][1] = x2;
		this.m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
		dRetentionErrorX2 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
		if (bMinimizeAngles)
			dAngleErrorX2 = calcAngleDifferenceHoldUp(iIndex);
		else
			dAngleErrorX2 = 1;
		
		if (dRetentionErrorX2 * dAngleErrorX2 < dRetentionErrorX1 * dAngleErrorX1)
		{
			// We're going in the right direction
			x3 = x2;
			dRetentionErrorX3 = dRetentionErrorX2;
			dAngleErrorX3 = dAngleErrorX2;
			
			x2 = (x3 - x1) * this.m_dGoldenRatio + x3;
			
			m_dHoldUpArray[iIndex][1] = x2;
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionErrorX2 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			if (bMinimizeAngles)
				dAngleErrorX2 = calcAngleDifferenceHoldUp(iIndex);
			else
				dAngleErrorX2 = 1;

			while (dRetentionErrorX2 * dAngleErrorX2 < dRetentionErrorX3 * dAngleErrorX3 && x2 < dLastFGuess + dMaxChangeAtOnce)
			{
				x1 = x3;
				dRetentionErrorX1 = dRetentionErrorX3;
				dAngleErrorX1 = dAngleErrorX3;
				
				x3 = x2;
				dRetentionErrorX3 = dRetentionErrorX2;
				dAngleErrorX3 = dAngleErrorX2;
				
				x2 = (x3 - x1) * this.m_dGoldenRatio + x3;
				
				m_dHoldUpArray[iIndex][1] = x2;
				m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
				dRetentionErrorX2 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
				if (bMinimizeAngles)
					dAngleErrorX2 = calcAngleDifferenceHoldUp(iIndex);
				else
					dAngleErrorX2 = 1;
			}
		}
		else
		{
			// We need to go in the opposite direction
			x3 = x1;
			dRetentionErrorX3 = dRetentionErrorX1;
			dAngleErrorX3 = dAngleErrorX1;
			
			x1 = x3 - (x2 - x3) * this.m_dGoldenRatio;
			
			m_dHoldUpArray[iIndex][1] = x1;
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionErrorX1 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			if (bMinimizeAngles)
				dAngleErrorX1 = calcAngleDifferenceHoldUp(iIndex);
			else
				dAngleErrorX1 = 1;

			while (dRetentionErrorX1 * dAngleErrorX1 < dRetentionErrorX3 * dAngleErrorX3 && x1 > dLastFGuess - dMaxChangeAtOnce)
			{
				x2 = x3;
				dRetentionErrorX2 = dRetentionErrorX3;
				dAngleErrorX2 = dAngleErrorX3;
				x3 = x1;
				dRetentionErrorX3 = dRetentionErrorX1;
				dAngleErrorX3 = dAngleErrorX1;
				
				x1 = x3 - (x2 - x3) * this.m_dGoldenRatio;
				
				m_dHoldUpArray[iIndex][1] = x1;
				m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
				dRetentionErrorX1 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
				if (bMinimizeAngles)
					dAngleErrorX1 = calcAngleDifferenceHoldUp(iIndex);
				else
					dAngleErrorX1 = 1;
			}
		}
		
		// Now we have our bounds (x1 to x2) and the results of one guess (x3)
		if (x2 > dLastFGuess + dMaxChangeAtOnce)
		{
			m_dHoldUpArray[iIndex][1] = dLastFGuess + dMaxChangeAtOnce;
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionError = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			
			return dRetentionError;
		}
		
		if (x1 < dLastFGuess - dMaxChangeAtOnce)
		{
			m_dHoldUpArray[iIndex][1] = dLastFGuess - dMaxChangeAtOnce;
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionError = calcRetentionError(m_dtstep, m_vectCalCompounds.size());

			return dRetentionError;
		}
		
		// Loop of optimization
		while ((x2 - x1) > dPrecision)
		{
			double x4;
			double dRetentionErrorX4;
			double dAngleErrorX4;
			
			// Is the bigger gap between x3 and x2 or x3 and x1?
			if (x2 - x3 > x3 - x1) 
			{
				// x3 and x2, so x4 must be placed between them
				x4 = x3 + (2 - this.m_dGoldenRatio) * (x2 - x3);
			}
			else 
			{
				// x1 and x3, so x4 must be placed between them
				x4 = x3 - (2 - this.m_dGoldenRatio) * (x3 - x1);
			}
			
			m_dHoldUpArray[iIndex][1] = x4;
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionErrorX4 = calcRetentionError(m_dtstep, m_vectCalCompounds.size());
			if (bMinimizeAngles)
				dAngleErrorX4 = calcAngleDifferenceHoldUp(iIndex);
			else
				dAngleErrorX4 = 1;

			// Decide what to do next
			if (dRetentionErrorX4 * dAngleErrorX4 < dRetentionErrorX3 * dAngleErrorX3)
			{
				// Our new guess was better
				// Where did we put our last guess again?
				if (x2 - x3 > x3 - x1) 
				{
					// x4 was in between x3 and x2
					x1 = x3;
					dRetentionErrorX1 = dRetentionErrorX3;
					dAngleErrorX1 = dAngleErrorX3;
					x3 = x4;
					dRetentionErrorX3 = dRetentionErrorX4;
					dAngleErrorX3 = dAngleErrorX4;
				}
				else
				{
					// x4 was in between x1 and x3
					x2 = x3;
					dRetentionErrorX2 = dRetentionErrorX3;
					dAngleErrorX2 = dAngleErrorX3;
					x3 = x4;
					dRetentionErrorX3 = dRetentionErrorX4;
					dAngleErrorX3 = dAngleErrorX4;
				}
			}
			else
			{
				// Our new guess was worse
				if (x2 - x3 > x3 - x1) 
				{
					// x4 was in between x3 and x2
					x2 = x4;
					dRetentionErrorX2 = dRetentionErrorX4;
					dAngleErrorX2 = dAngleErrorX4;
				}
				else
				{
					// x4 was in between x1 and x3
					x1 = x4;
					dRetentionErrorX1 = dRetentionErrorX4;
					dAngleErrorX1 = dAngleErrorX4;
				}
			}
		}
		
		// Restore profile to best value
		m_dHoldUpArray[iIndex][1] = x3;
		m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
		dRetentionError = dRetentionErrorX3;
		
		return dRetentionError;
 	}
    
 	public double goldenSectioningSearchHoldUpOffset(double dStep, double dPrecision, int iNumCompoundsToUse)
 	{
		double dRetentionError = 1;
 		double F1;
		double F2;
		double F3;
		double dRetentionErrorF1;
		double dRetentionErrorF2;
		double dRetentionErrorF3;
		
		// Find bounds
		F1 = m_dHoldUpArray[0][1];
		dRetentionErrorF1 = calcRetentionError(m_dtstep, iNumCompoundsToUse);

		F2 = F1 + dStep;
		
		double dDiff = F2 - m_dHoldUpArray[0][1];
		for (int i = 0; i < m_dHoldUpArray.length; i++)
		{
			m_dHoldUpArray[i][1] += dDiff;
		}
		m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
		dRetentionErrorF2 = calcRetentionError(m_dtstep, iNumCompoundsToUse);
		
		if (dRetentionErrorF2 < dRetentionErrorF1)
		{
			// We're going in the right direction
			F3 = F2;
			dRetentionErrorF3 = dRetentionErrorF2;
			
			F2 = (F3 - F1) * this.m_dGoldenRatio + F3;
			
			dDiff = F2 - m_dHoldUpArray[0][1];
			for (int i = 0; i < m_dHoldUpArray.length; i++)
			{
				m_dHoldUpArray[i][1] += dDiff;
			}
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionErrorF2 = calcRetentionError(m_dtstep, iNumCompoundsToUse);

			while (dRetentionErrorF2 < dRetentionErrorF3)
			{
				F1 = F3;
				dRetentionErrorF1 = dRetentionErrorF3;
				F3 = F2;
				dRetentionErrorF3 = dRetentionErrorF2;
				
				F2 = (F3 - F1) * this.m_dGoldenRatio + F3;
				
				dDiff = F2 - m_dHoldUpArray[0][1];
				for (int i = 0; i < m_dHoldUpArray.length; i++)
				{
					m_dHoldUpArray[i][1] += dDiff;
				}
				m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
				dRetentionErrorF2 = calcRetentionError(m_dtstep, iNumCompoundsToUse);
			}
		}
		else
		{
			// We need to go in the opposite direction
			F3 = F1;
			dRetentionErrorF3 = dRetentionErrorF1;
			
			F1 = F3 - (F2 - F3) * this.m_dGoldenRatio;
			
			dDiff = F1 - m_dHoldUpArray[0][1];
			for (int i = 0; i < m_dHoldUpArray.length; i++)
			{
				m_dHoldUpArray[i][1] += dDiff;
			}
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionErrorF1 = calcRetentionError(m_dtstep, iNumCompoundsToUse);

			while (dRetentionErrorF1 < dRetentionErrorF3)
			{
				F2 = F3;
				dRetentionErrorF2 = dRetentionErrorF3;
				F3 = F1;
				dRetentionErrorF3 = dRetentionErrorF1;
				
				F1 = F3 - (F2 - F3) * this.m_dGoldenRatio;
				
				dDiff = F1 - m_dHoldUpArray[0][1];
				for (int i = 0; i < m_dHoldUpArray.length; i++)
				{
					m_dHoldUpArray[i][1] += dDiff;
				}
				m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
				dRetentionErrorF1 = calcRetentionError(m_dtstep, iNumCompoundsToUse);
			}
		}
		
		// Loop of optimization
		while ((F2 - F1) > dPrecision)
		{
			double F4;
			double dRetentionErrorF4;
			// Is the bigger gap between F3 and F2 or F3 and F1?
			if (F2 - F3 > F3 - F1) 
			{
				// F3 and F2, so F4 must be placed between them
				F4 = F3 + (2 - this.m_dGoldenRatio) * (F2 - F3);
			}
			else 
			{
				// F1 and F3, so F4 must be placed between them
				F4 = F3 - (2 - this.m_dGoldenRatio) * (F3 - F1);
			}

			
			dDiff = F4 - m_dHoldUpArray[0][1];
			for (int i = 0; i < m_dHoldUpArray.length; i++)
			{
				m_dHoldUpArray[i][1] += dDiff;
			}
			m_InterpolatedHoldUp = new InterpolationFunction(m_dHoldUpArray);
			dRetentionErrorF4 = calcRetentionError(m_dtstep, iNumCompoundsToUse);
			
			// Decide what to do next
			if (dRetentionErrorF4 < dRetentionErrorF3)
			{
				// Our new guess was better
				// Where did we put our last guess again?
				if (F2 - F3 > F3 - F1) 
				{
					// F4 was in between F3 and F2
					F1 = F3;
					dRetentionErrorF1 = dRetentionErrorF3;
					F3 = F4;
					dRetentionErrorF3 = dRetentionErrorF4;
				}
				else
				{
					// F4 was in between F1 and F3
					F2 = F3;
					dRetentionErrorF2 = dRetentionErrorF3;							
					F3 = F4;
					dRetentionErrorF3 = dRetentionErrorF4;
				}
			}
			else
			{
				// Our new guess was worse
				if (F2 - F3 > F3 - F1) 
				{
					// F4 was in between F3 and F2
					F2 = F4;
					dRetentionErrorF2 = dRetentionErrorF4;
				}
				else
				{
					// F4 was in between F1 and F3
					F1 = F4;
					dRetentionErrorF1 = dRetentionErrorF4;							
				}
			}
		}
		
		dRetentionError = dRetentionErrorF3;
		
		return dRetentionError;
 	}
 	
    // Iterative - in sequence, *Golden* Sectioning Search algorithm
    // Start by optimizing the entire flow rate error profile.
	public void backCalculate(Task task, boolean bHoldUpProfileFirst)
	{
		long starttime = System.currentTimeMillis();

		m_dTemperatureArrayStore = new double[300][m_dTemperatureProfileArray.length][2];
		m_dHoldUpArrayStore = new double[300][m_dHoldUpArray.length][2];
		m_dRetentionErrorStore = new double[300];
		
		boolean bBackCalculateTempProfile = false;
		boolean bBackCalculateHoldUpProfile = false;
		boolean bMinimizeAnglesTempProfile = true;
		boolean bMinimizeAnglesHoldUpProfile = true;
		
    	m_dtstep = m_dPlotXMax2 * 0.01;

		if (bHoldUpProfileFirst)
		{
			bBackCalculateTempProfile = false;
			bBackCalculateHoldUpProfile = true;
		}
		else
		{
			bBackCalculateTempProfile = true;
			bBackCalculateHoldUpProfile = false;
		}
		
		NumberFormat formatter1 = new DecimalFormat("#0.000000");
		NumberFormat formatter2 = new DecimalFormat("0.0000E0");
		NumberFormat percentFormatter = new DecimalFormat("0.00");
		
		// Step #1: Create interpolating functions for the isocratic data of each gradient calibration solute
		m_AlkaneIsothermalDataInterpolated = new InterpolationFunction[contentPane2.tmOutputModel.getRowCount()];
		
		for (int i = 0; i < m_AlkaneIsothermalDataInterpolated.length; i++)
		{
			Integer iIndex = (Integer) m_vectCalCompounds.get(i)[0];
			m_AlkaneIsothermalDataInterpolated[i] = new InterpolationFunction(Globals.AlkaneIsothermalDataArray[iIndex]);
		}

		double dRetentionErrorTest = calcRetentionError(m_dtstep, m_vectCalCompounds.size());

		// Step #2: Optimize the flow rate profile offset
		if (true)
		{
			double dHoldUpStep = .1;
			double dHoldUpPrecision = 0.0001;
			int iNumCompoundsToUse = 3;
			
			double dRetentionError = goldenSectioningSearchHoldUpOffset(dHoldUpStep, dHoldUpPrecision, iNumCompoundsToUse);
			
			UpdateTime(starttime);
			
			String str;
			double dNum = dRetentionError / m_vectCalCompounds.size();
			if (dNum < 0.0001)
				str = formatter2.format(dNum);
			else
				str = formatter1.format(dNum);
			
			contentPane2.jlblVariance.setText(str);
			this.UpdateGraphs();
			
			if (task.isCancelled())
			{
				return;
			}
		}
		
		// Step #4: Begin back-calculation
		// If bHoldUpProfileFirst then back-calculate the hold up profile first, otherwise do the temperature profile first
		
		int iPhase = 1;
		int iIteration = 0;
		double dLastFullIterationError = 0;
		double dRetentionError = 0;
		
		while (true)
		{
			iIteration++;
			contentPane2.jlblIterationNumber.setText(((Integer)iIteration).toString());
			dLastFullIterationError = dRetentionError;
			
			// Run once for each data point. Start with the earliest data point.
			
			// Now we optimize the hold-up time vs. temp profile
			if (bBackCalculateHoldUpProfile)
			{
				for (int iTimePoint = 0; iTimePoint < m_dHoldUpArray.length; iTimePoint++)
				{
					double dHoldUpStep = .1;
					double dHoldUpPrecision = .0001;
					double dMaxChangeAtOnce = 2;
					
					dRetentionError = goldenSectioningSearchHoldUp(iTimePoint, dHoldUpStep, dHoldUpPrecision, dMaxChangeAtOnce, bMinimizeAnglesHoldUpProfile);
					
					UpdateTime(starttime);
					
					String str;
					double dNum = dRetentionError / m_vectCalCompounds.size();
					if (dNum < 0.0001)
						str = formatter2.format(dNum);
					else
						str = formatter1.format(dNum);
					
					contentPane2.jlblVariance.setText(str);
					this.UpdateGraphs();
					
					if (task.isCancelled())
					{
						return;
					}
				}
			}
			
			if (bBackCalculateTempProfile)
			{
				for (int iTimePoint = 0; iTimePoint < m_dTemperatureProfileArray.length; iTimePoint++)
				{
					double dTempStep = .5;
					double dMaxChangeAtOnce = 5;
					double dTempPrecision = 0.001;
					
					dRetentionError = goldenSectioningSearchTemperatureProfile(iTimePoint, dTempStep, dTempPrecision, dMaxChangeAtOnce, bMinimizeAnglesTempProfile);
					
					UpdateTime(starttime);
					
					String str;
					double dNum = dRetentionError / m_vectCalCompounds.size();
					if (dNum < 0.0001)
						str = formatter2.format(dNum);
					else
						str = formatter1.format(dNum);
					
					contentPane2.jlblVariance.setText(str);
					this.UpdateGraphs();
					
					if (task.isCancelled())
					{
						return;
					}
				}
			}
			
			String str;
			double dNum = dRetentionError / m_vectCalCompounds.size();
			
			if (dNum == 0)
				str = "";
			else if (dNum < 0.0001)
				str = formatter2.format(dNum);
			else
				str = formatter1.format(dNum);
			
			contentPane2.jlblLastVariance.setText(str);
			
			{
				// Save the new temperature profile
				for (int i = 0; i < m_dTemperatureProfileArray.length; i++)
				{
					m_dTemperatureArrayStore[iIteration - 1][i][0] = m_dTemperatureProfileArray[i][0];
					m_dTemperatureArrayStore[iIteration - 1][i][1] = m_dTemperatureProfileArray[i][1];
				}
	
				// Save the new hold-up time profile
				for (int i = 0; i < m_dHoldUpArray.length; i++)
				{
					m_dHoldUpArrayStore[iIteration - 1][i][0] = m_dHoldUpArray[i][0];
					m_dHoldUpArrayStore[iIteration - 1][i][1] = m_dHoldUpArray[i][1];
				}
				
				// Save the retention error for this iteration
				m_dRetentionErrorStore[iIteration - 1] = dRetentionError;
			}
			
			// Calculate the percent improvement
			if (dLastFullIterationError != 0)
			{
				double dPercentImprovement = (1 - (dRetentionError / dLastFullIterationError)) * 100;
				contentPane2.jlblPercentImprovement.setText(percentFormatter.format(dPercentImprovement) + "%");
				
				if (iPhase == 1)
				{
					if (bHoldUpProfileFirst)
					{
						if (dPercentImprovement < 5 && dPercentImprovement >= 0)
						{
							iPhase = 2;
							contentPane2.jlblPhase.setText("II");
							bBackCalculateTempProfile = true;
							bBackCalculateHoldUpProfile = true;
							bMinimizeAnglesTempProfile = true;
							bMinimizeAnglesHoldUpProfile = true;
						}
					}
					else
					{
						if (dPercentImprovement < 15 && dPercentImprovement >= 0)
						{
							iPhase = 2;
							contentPane2.jlblPhase.setText("II");
							bBackCalculateTempProfile = true;
							bBackCalculateHoldUpProfile = true;
							bMinimizeAnglesTempProfile = true;
							bMinimizeAnglesHoldUpProfile = true;
						}
					}
				}
				else if (iPhase == 2)
				{
					if (dPercentImprovement < 2 && dPercentImprovement >= 0)
					{
						iPhase = 3;
						contentPane2.jlblPhase.setText("III");
						if (bHoldUpProfileFirst)
						{
							bBackCalculateTempProfile = true;
							bBackCalculateHoldUpProfile = true;
						}
						else
						{
							bBackCalculateTempProfile = true;
							bBackCalculateHoldUpProfile = true;
						}
						bMinimizeAnglesTempProfile = false;
						bMinimizeAnglesHoldUpProfile = false;
				    	m_dtstep = m_dPlotXMax2 * 0.001;
					}					
				}
				else
				{
					if (dPercentImprovement < 1 && dPercentImprovement >= 0)
					{
						// Optimization is complete.
						break;
					}
				}
			}
		}
	}
}
