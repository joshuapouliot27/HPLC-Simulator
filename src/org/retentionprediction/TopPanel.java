package org.retentionprediction;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import javax.media.opengl.*;
import java.awt.Point;
import javax.swing.JButton;
import java.awt.Dimension;
import java.util.Vector;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;

import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.help.*;

import org.retentionprediction.TopPanel.JChemicalTable;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.media.opengl.GLCapabilities;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class TopPanel extends JPanel implements Scrollable, ComponentListener
{
	private static final long serialVersionUID = 1L;
	
	public GraphControl m_GraphControlTemp = null;
	//public DefaultTableColumnModelExt tabModel;
	
	public Vector<String> vectColumnNames = new Vector<String>();  //  @jve:decl-index=0:
	public Vector<Vector<String>> vectChemicalRows = new Vector<Vector<String>>();  //  @jve:decl-index=0:
	
	private JPanel jpanelSimulatedChromatogram = null;
	public JPanel jpanelStep1 = null;
	private JLabel jlblStationaryPhase = null;
	public JComboBox jcboStationaryPhase = null;
	private JLabel jlblInitialTemperature = null;
	public JTextField jtxtInitialTemperature = null;
	private JLabel jlblInitialTime = null;
	public JTextField jtxtInitialTime = null;
	private JLabel jlblInitialTemperatureUnit = null;
	private JLabel jlblInitialTimeUnit = null;
	public JLabel jlblFlowRate = null;
	public JTextField jtxtFlowRate = null;
	public JLabel jlblFlowRateUnit = null;
	private JPanel jpanelStep2 = null;
	public JPanel jpanelStep4 = null;
	private JScrollPane jScrollPane1 = null;
	public JTable jtableMeasuredRetentionTimes = null;
	public SpecialTableModel2 tmTemperatureProgram;
	public SpecialTableModel tmMeasuredRetentionTimes;
	public JPanel jpanelFlowProfile = null;
	public GraphControl m_GraphControlHoldUp = null;
	public GLCapabilities capsFlow = null;
	public JButton jbtnNextStep = null;
	public JButton jbtnHelp = null;
	public JButton jbtnPreloadedValues = null;
	private JLabel jlblColumnLength = null;
	public JTextField jtxtColumnLength = null;
	private JLabel jlblColumnLengthUnit = null;
	public JRadioButton jrdoConstantPressure = null;
	public JRadioButton jrdoConstantFlowRate = null;
	private JScrollPane jScrollPane = null;
	public JTable jtblTemperatureProgram = null;
	public JButton jbtnInsertRow = null;
	public JButton jbtnRemoveRow = null;
	public JLabel jlblOutletPressure = null;
	public JLabel jlblPressureUnit = null;
	public JLabel jlblPressure = null;
	public JTextField jtxtPressure = null;
	public JRadioButton jrdoVacuum = null;
	public JRadioButton jrdoAmbient = null;

	/**
	 * This is the default constructor
	 */
	public TopPanel() 
	{
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
		//this.setBounds(new Rectangle(0, 0, 943, 615));
		this.setSize(new Dimension(943, 615));
		this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(890,570));
        this.setMinimumSize(new Dimension(890,570));

        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        
		m_GraphControlTemp = new GraphControl(caps);
		m_GraphControlTemp.setBounds(new Rectangle(3, 16, 606, 293));
		m_GraphControlTemp.setControlsEnabled(false);
		
        GLCapabilities caps2 = new GLCapabilities();
        caps2.setDoubleBuffered(true);
        caps2.setHardwareAccelerated(true);
        
		m_GraphControlHoldUp = new GraphControl(caps2);
		m_GraphControlHoldUp.setBounds(new Rectangle(3, 16, 606, 233));
		m_GraphControlHoldUp.setControlsEnabled(false);
		
	    this.add(getJpanelSimulatedChromatogram(), null);
	    this.add(getJpanelStep1(), null);
	    this.add(getJpanelStep2(), null);
	    this.add(getJpanelStep4(), null);
	    this.add(getJpanelFlowProfile(), null);
	    this.add(getJbtnNextStep(), null);
	    this.add(getJbtnHelp(), null);
	    this.add(getJbtnPreloadedValues(), null);
   
		this.addComponentListener(this);

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
			jpanelSimulatedChromatogram.setBorder(BorderFactory.createTitledBorder(null, "Approximate Temperature Program", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelSimulatedChromatogram.setBackground(Color.white);
			jpanelSimulatedChromatogram.setPreferredSize(new Dimension(615, 477));
			jpanelSimulatedChromatogram.setSize(new Dimension(616, 313));
			jpanelSimulatedChromatogram.setLocation(new Point(330, 0));
			
			jpanelSimulatedChromatogram.add(m_GraphControlTemp, null);
		}
		return jpanelSimulatedChromatogram;
	}

	/**
	 * This method initializes jpanelStep1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelStep1() {
		if (jpanelStep1 == null) {
			jlblColumnLengthUnit = new JLabel();
			jlblColumnLengthUnit.setBounds(new Rectangle(256, 72, 38, 16));
			jlblColumnLengthUnit.setText("m");
			jlblColumnLength = new JLabel();
			jlblColumnLength.setBounds(new Rectangle(8, 72, 185, 16));
			jlblColumnLength.setText("Column Length:");
			jlblFlowRateUnit = new JLabel();
			jlblFlowRateUnit.setText("mL/min");
			jlblFlowRateUnit.setBounds(new Rectangle(256, 44, 53, 16));
			jlblFlowRate = new JLabel();
			jlblFlowRate.setText("Flow rate:");
			jlblFlowRate.setBounds(new Rectangle(28, 44, 165, 16));
			jlblFlowRate.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jlblInitialTimeUnit = new JLabel();
			jlblInitialTimeUnit.setText("min");
			jlblInitialTimeUnit.setBounds(new Rectangle(256, 176, 49, 16));
			jlblInitialTemperatureUnit = new JLabel();
			jlblInitialTemperatureUnit.setText("\u00b0C");
			jlblInitialTemperatureUnit.setBounds(new Rectangle(256, 156, 49, 16));
			jlblInitialTime = new JLabel();
			jlblInitialTime.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jlblInitialTime.setBounds(new Rectangle(8, 176, 185, 16));
			jlblInitialTime.setText("Initial time:");
			jlblInitialTemperature = new JLabel();
			jlblInitialTemperature.setText("Initial oven temperature:");
			jlblInitialTemperature.setBounds(new Rectangle(8, 156, 185, 16));
			jlblStationaryPhase = new JLabel();
			jlblStationaryPhase.setText("Standard column:");
			jlblStationaryPhase.setSize(new Dimension(105, 16));
			jlblStationaryPhase.setLocation(new Point(8, 24));
			jpanelStep1 = new JPanel();
			jpanelStep1.setLayout(null);
			jpanelStep1.setBorder(BorderFactory.createTitledBorder(null, "Step #1: Select Standard Column", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelStep1.setBackground(Color.white);
			jpanelStep1.setSize(new Dimension(317, 97));
			jpanelStep1.setLocation(new Point(6, 0));
			jpanelStep1.add(jlblStationaryPhase, null);
			jpanelStep1.add(getJcboStationaryPhase(), null);
			jpanelStep1.add(jlblColumnLength, null);
			jpanelStep1.add(getJtxtColumnLength(), null);
			jpanelStep1.add(jlblColumnLengthUnit, null);
		}
		return jpanelStep1;
	}


	/**
	 * This method initializes jcboStationaryPhase	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJcboStationaryPhase() {
		if (jcboStationaryPhase == null) {
			jcboStationaryPhase = new JComboBox(Globals.StationaryPhaseArray);
			jcboStationaryPhase.setBounds(new Rectangle(8, 44, 297, 21));
		}
		return jcboStationaryPhase;
	}


	/**
	 * This method initializes jtxtInitialSolventComposition	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtInitialSolventComposition() {
		if (jtxtInitialTemperature == null) {
			jtxtInitialTemperature = new JTextField();
			jtxtInitialTemperature.setBounds(new Rectangle(200, 152, 53, 20));
			jtxtInitialTemperature.setText("60.0");
		}
		return jtxtInitialTemperature;
	}


	/**
	 * This method initializes jtxtInitialTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtInitialTime() {
		if (jtxtInitialTime == null) {
			jtxtInitialTime = new JTextField();
			jtxtInitialTime.setBounds(new Rectangle(200, 172, 53, 20));
			jtxtInitialTime.setText("5");
		}
		return jtxtInitialTime;
	}


	/**
	 * This method initializes jtxtFlowRate	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtFlowRate() {
		if (jtxtFlowRate == null) {
			jtxtFlowRate = new JTextField();
			jtxtFlowRate.setText("1.0");
			jtxtFlowRate.setLocation(new Point(200, 42));
			jtxtFlowRate.setSize(new Dimension(53, 20));
		}
		return jtxtFlowRate;
	}


	/**
	 * This method initializes jpanelStep2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelStep2() {
		if (jpanelStep2 == null) {
			jlblPressure = new JLabel();
			jlblPressure.setBounds(new Rectangle(28, 84, 165, 16));
			jlblPressure.setText("Column inlet pressure:");
			jlblPressure.setEnabled(false);
			jlblPressure.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jlblPressureUnit = new JLabel();
			jlblPressureUnit.setBounds(new Rectangle(256, 84, 53, 16));
			jlblPressureUnit.setText("kPa");
			jlblPressureUnit.setEnabled(false);
			jlblPressureUnit.setVisible(true);
			jlblOutletPressure = new JLabel();
			jlblOutletPressure.setBounds(new Rectangle(8, 108, 165, 16));
			jlblOutletPressure.setText("Column outlet pressure:");
			jlblOutletPressure.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jlblOutletPressure.setVisible(true);
			jpanelStep2 = new JPanel();
			jpanelStep2.setLayout(null);
			jpanelStep2.setBorder(BorderFactory.createTitledBorder(null, "Step #2: Enter Experiment Conditions", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelStep2.setBackground(Color.white);
			jpanelStep2.setSize(new Dimension(317, 321));
			jpanelStep2.setLocation(new Point(6, 104));
			jpanelStep2.add(jlblInitialTemperature, null);
			jpanelStep2.add(getJtxtInitialSolventComposition(), null);
			jpanelStep2.add(jlblInitialTemperatureUnit, null);
			jpanelStep2.add(jlblInitialTime, null);
			jpanelStep2.add(getJtxtInitialTime(), null);
			jpanelStep2.add(jlblInitialTimeUnit, null);
			jpanelStep2.add(jlblFlowRate, null);
			jpanelStep2.add(getJtxtFlowRate(), null);
			jpanelStep2.add(jlblFlowRateUnit, null);
			jpanelStep2.add(getJrdoConstantPressure(), null);
			jpanelStep2.add(getJrdoConstantFlowRate(), null);
			jpanelStep2.add(getJScrollPane(), null);
			jpanelStep2.add(getJbtnInsertRow(), null);
			jpanelStep2.add(getJbtnRemoveRow(), null);
			jpanelStep2.add(jlblOutletPressure, null);
			jpanelStep2.add(jlblPressureUnit, null);
			jpanelStep2.add(jlblPressure, null);
			jpanelStep2.add(getJtxtPressure(), null);
			jpanelStep2.add(getJrdoVacuum(), null);
			jpanelStep2.add(getJrdoAmbient(), null);
		}
		return jpanelStep2;
	}


	class SelectCompoundsTableModel extends AbstractTableModel {
		private String[] columnNames = {"Compound", "Select"};
        private Object[][] data = new Object[Globals.CompoundNameArray.length][2];
        
        public void loadData()
        {
        	for(int i=0; i < Globals.CompoundNameArray.length; i++)
            {
            	data[i][0] = Globals.CompoundNameArray[i];
            	data[i][1] = new Boolean(false);
            }
        }
        
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
        
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */

	/**
	 * This method initializes jpanelStep4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelStep4() {
		if (jpanelStep4 == null) {
			jpanelStep4 = new JPanel();
			jpanelStep4.setLayout(null);
			jpanelStep4.setBorder(BorderFactory.createTitledBorder(null, "Step #3: Enter Measured n-Alkane Retention Times", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelStep4.setBackground(Color.white);
			jpanelStep4.setSize(new Dimension(317, 177));
			jpanelStep4.setLocation(new Point(6, 432));
			jpanelStep4.add(getJScrollPane1(), null);
		}
		return jpanelStep4;
	}


	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setLocation(new Point(8, 20));
			jScrollPane1.setSize(new Dimension(301, 149));
			jScrollPane1.setViewportView(getJtableMeasuredRetentionTimes());
		}
		return jScrollPane1;
	}

	public class SpecialTableModel2 extends DefaultTableModel 
	{
		public SpecialTableModel2(final Object[] columnNames, final int rowCount) 
	    {
	        super(convertToVector(columnNames), rowCount);
	    }
	    
	    public SpecialTableModel2(final Object[][] data, final Object[] columnNames) 
	    {
	        setDataVector(data, columnNames);
	    }

	    public boolean isCellEditable(int row, int column) 
	    {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        /*if (column < 1) {
	            return false;
	        } else {
	            return true;
	        }*/
	    	return true;
	    }
	    
	    /*
	     * JTable uses this method to determine the default renderer/
	     * editor for each cell.  If we didn't implement this method,
	     * then the last column would contain text ("true"/"false"),
	     * rather than a check box.
	     */
	    public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	}
	
	class SpecialTableModel extends DefaultTableModel 
	{
	    public SpecialTableModel(final Object[] columnNames, final int rowCount) 
	    {
	        super(convertToVector(columnNames), rowCount);
	    }
	    
	    public SpecialTableModel(final Object[][] data, final Object[] columnNames) 
	    {
	        setDataVector(data, columnNames);
	    }

	    public boolean isCellEditable(int row, int column) 
	    {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        if (column < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    
	    /*
	     * JTable uses this method to determine the default renderer/
	     * editor for each cell.  If we didn't implement this method,
	     * then the last column would contain text ("true"/"false"),
	     * rather than a check box.
	     */
	    public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	}
	
	/**
	 * This method initializes jtableMeasuredRetentionTimes	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJtableMeasuredRetentionTimes() {
		if (jtableMeasuredRetentionTimes == null) 
		{
			Object[] columnNames = {"Carbon number", "tR (min)"};
			Object[][] data = {
					{(Object)"7 - n-heptane", (Object)0.0},
					{(Object)"8 - n-octane", (Object)0.0},
					{(Object)"9 - n-nonane", (Object)0.0},
					{(Object)"10 - n-decane", (Object)0.0},
					{(Object)"11 - n-undecane", (Object)0.0},
					{(Object)"12 - n-dodecane", (Object)0.0},
					{(Object)"13 - n-tridecane", (Object)0.0},
					{(Object)"14 - n-tetradecane", (Object)0.0},
					{(Object)"15 - n-pentadecane", (Object)0.0},
					{(Object)"16 - n-hexadecane", (Object)0.0},
					{(Object)"17 - n-heptadecane", (Object)0.0},
					{(Object)"18 - n-octadecane", (Object)0.0},
					{(Object)"19 - n-nonadecane", (Object)0.0},
					{(Object)"20 - n-eicosane", (Object)0.0},
					{(Object)"21 - n-heneicosane", (Object)0.0},
					{(Object)"22 - n-docosane", (Object)0.0},
					{(Object)"23 - n-tricosane", (Object)0.0},
					{(Object)"24 - n-tetracosane", (Object)0.0},
					{(Object)"25 - n-pentacosane", (Object)0.0},
					{(Object)"26 - n-hexacosane", (Object)0.0},
					{(Object)"28 - n-octacosane", (Object)0.0},
					{(Object)"30 - n-triacontane", (Object)0.0},
					{(Object)"32 - n-dotriacontane", (Object)0.0},
					{(Object)"34 - n-tetratriacontane", (Object)0.0},
					{(Object)"36 - n-hexatriacontane", (Object)0.0}
			};
			tmMeasuredRetentionTimes = new SpecialTableModel(data, columnNames);

			jtableMeasuredRetentionTimes = new JTable(tmMeasuredRetentionTimes);

			jtableMeasuredRetentionTimes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			jtableMeasuredRetentionTimes.getTableHeader().setPreferredSize(new Dimension(jtableMeasuredRetentionTimes.getColumnModel().getTotalColumnWidth(), 22));
			jtableMeasuredRetentionTimes.getColumnModel().getColumn(0).setPreferredWidth(100);
		}
		return jtableMeasuredRetentionTimes;
	}


	/**
	 * This method initializes jpanelFlowProfile	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelFlowProfile() {
		if (jpanelFlowProfile == null) {
			jpanelFlowProfile = new JPanel();
			jpanelFlowProfile.setLayout(null);
			jpanelFlowProfile.setBackground(Color.white);
			jpanelFlowProfile.setPreferredSize(new Dimension(615, 477));
			jpanelFlowProfile.setBorder(BorderFactory.createTitledBorder(null, "Approximate Hold-Up Time", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelFlowProfile.setSize(new Dimension(615, 253));
			jpanelFlowProfile.setLocation(new Point(330, 316));
			jpanelFlowProfile.add(m_GraphControlHoldUp, null);
		}
		return jpanelFlowProfile;
	}


	/**
	 * This method initializes jbtnNextStep	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnNextStep() {
		if (jbtnNextStep == null) {
			jbtnNextStep = new JButton();
			jbtnNextStep.setText("Next Step  ");
			jbtnNextStep.setIcon(new ImageIcon(getClass().getResource("/org/retentionprediction/images/forward.png")));
			jbtnNextStep.setHorizontalTextPosition(SwingConstants.LEADING);
			jbtnNextStep.setHorizontalAlignment(SwingConstants.CENTER);
			jbtnNextStep.setLocation(new Point(760, 576));
			jbtnNextStep.setSize(new Dimension(178, 34));
			jbtnNextStep.setEnabled(false);
			jbtnNextStep.setActionCommand("Next Step");
		}
		return jbtnNextStep;
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
			jbtnHelp.setLocation(new Point(572, 576));
			jbtnHelp.setSize(new Dimension(178, 34));
			jbtnHelp.setEnabled(false);
			jbtnHelp.setForeground(Color.blue);
		}
		return jbtnHelp;
	}


	/**
	 * This method initializes jbtnPreloadedValues	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnPreloadedValues() {
		if (jbtnPreloadedValues == null) {
			jbtnPreloadedValues = new JButton();
			jbtnPreloadedValues.setHorizontalAlignment(SwingConstants.CENTER);
			jbtnPreloadedValues.setHorizontalTextPosition(SwingConstants.LEADING);
			jbtnPreloadedValues.setText("Use Preloaded Values...");
			jbtnPreloadedValues.setLocation(new Point(330, 576));
			jbtnPreloadedValues.setSize(new Dimension(178, 34));
			jbtnPreloadedValues.setActionCommand("Preloaded Values");
		}
		return jbtnPreloadedValues;
	}


	/**
	 * This method initializes jtxtColumnLength	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtColumnLength() {
		if (jtxtColumnLength == null) {
			jtxtColumnLength = new JTextField();
			jtxtColumnLength.setBounds(new Rectangle(200, 70, 53, 20));
			jtxtColumnLength.setText("30.0");
		}
		return jtxtColumnLength;
	}


	/**
	 * This method initializes jrdoConstantPressure	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoConstantPressure() {
		if (jrdoConstantPressure == null) {
			jrdoConstantPressure = new JRadioButton();
			jrdoConstantPressure.setBounds(new Rectangle(8, 64, 257, 17));
			jrdoConstantPressure.setText("Constant pressure mode");
			jrdoConstantPressure.setBackground(Color.white);
		}
		return jrdoConstantPressure;
	}


	/**
	 * This method initializes jrdoConstantFlowRate	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoConstantFlowRate() {
		if (jrdoConstantFlowRate == null) {
			jrdoConstantFlowRate = new JRadioButton();
			jrdoConstantFlowRate.setBounds(new Rectangle(8, 24, 257, 17));
			jrdoConstantFlowRate.setText("Constant flow rate mode");
			jrdoConstantFlowRate.setSelected(true);
			jrdoConstantFlowRate.setBackground(Color.white);
		}
		return jrdoConstantFlowRate;
	}


	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(8, 200, 301, 81));
			jScrollPane.setViewportView(getJtblTemperatureProgram());
		}
		return jScrollPane;
	}


	/**
	 * This method initializes jtblTemperatureProgram	
	 * 	
	 * @return javax.swing.JTable	
	 */

	private JTable getJtblTemperatureProgram() {
		if (jtblTemperatureProgram == null) 
		{
			Object[] columnNames = {"Ramp (\u00b0C/min)", "Final temp (\u00b0C)", "Hold time (min)"};
			Double[][] data = {{20.0, 260.0, 5.0}};
	        
			tmTemperatureProgram = new SpecialTableModel2(data, columnNames);

			jtblTemperatureProgram = new JTable(tmTemperatureProgram);

			jtblTemperatureProgram.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			jtblTemperatureProgram.getTableHeader().setPreferredSize(new Dimension(22, 22));
			jtblTemperatureProgram.getColumnModel().getColumn(0).setPreferredWidth(85);
		}
		return jtblTemperatureProgram;
	}

	/**
	 * This method initializes jbtnInsertRow	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnInsertRow() {
		if (jbtnInsertRow == null) {
			jbtnInsertRow = new JButton();
			jbtnInsertRow.setBounds(new Rectangle(8, 288, 138, 25));
			jbtnInsertRow.setText("Insert Row");
		}
		return jbtnInsertRow;
	}


	/**
	 * This method initializes jbtnRemoveRow	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnRemoveRow() {
		if (jbtnRemoveRow == null) {
			jbtnRemoveRow = new JButton();
			jbtnRemoveRow.setBounds(new Rectangle(172, 288, 138, 26));
			jbtnRemoveRow.setActionCommand("Remove Row");
			jbtnRemoveRow.setText("Remove Row");
		}
		return jbtnRemoveRow;
	}


	/**
	 * This method initializes jtxtPressure	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtPressure() {
		if (jtxtPressure == null) {
			jtxtPressure = new JTextField();
			jtxtPressure.setBounds(new Rectangle(200, 82, 53, 20));
			jtxtPressure.setEditable(true);
			jtxtPressure.setEnabled(false);
			jtxtPressure.setActionCommand("Inlet Pressure");
			jtxtPressure.setText("100");
		}
		return jtxtPressure;
	}


	/**
	 * This method initializes jrdoVacuum	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoVacuum() {
		if (jrdoVacuum == null) {
			jrdoVacuum = new JRadioButton();
			jrdoVacuum.setBounds(new Rectangle(8, 128, 93, 17));
			jrdoVacuum.setSelected(true);
			jrdoVacuum.setBackground(Color.white);
			jrdoVacuum.setText("Vacuum");
		}
		return jrdoVacuum;
	}


	/**
	 * This method initializes jrdoAmbient	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJrdoAmbient() {
		if (jrdoAmbient == null) {
			jrdoAmbient = new JRadioButton();
			jrdoAmbient.setBounds(new Rectangle(104, 128, 93, 17));
			jrdoAmbient.setBackground(Color.white);
			jrdoAmbient.setText("Ambient");
		}
		return jrdoAmbient;
	}


	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean getScrollableTracksViewportHeight() {
		Dimension minSize = this.getMinimumSize();
		Dimension portSize = null;
		if (getParent() instanceof JViewport) 
		{
			JViewport port = (JViewport)getParent();
			portSize = port.getSize();
		}
		else
			return false;
		
		if (portSize.height < minSize.height)
			return false;
		else
			return true;
	}


	@Override
	public boolean getScrollableTracksViewportWidth() {
		Dimension minSize = this.getMinimumSize();
		Dimension portSize = null;
		if (getParent() instanceof JViewport) 
		{
			JViewport port = (JViewport)getParent();
			portSize = port.getSize();
		}
		else
			return false;
		
		if (portSize.width < minSize.width)
			return false;
		else
			return true;
	}


	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentMoved(ComponentEvent arg0) {
		this.revalidate();
	}


	@Override
	public void componentResized(ComponentEvent arg0) 
	{
		// Respond to window resize
		if (arg0.getComponent() == this)
		{
			Dimension size = this.getSize();
			this.jpanelStep4.setSize(this.jpanelStep4.getWidth(), size.height - 438);
			this.jScrollPane1.setSize(this.jScrollPane1.getWidth(), size.height - 438 - 28);
			this.jtableMeasuredRetentionTimes.revalidate();
			this.jbtnNextStep.setLocation((int)size.getWidth() - this.jbtnNextStep.getWidth() - 6, (int)size.getHeight() - jbtnNextStep.getHeight() - 6);
			this.jbtnHelp.setLocation(this.jbtnNextStep.getLocation().x - this.jbtnHelp.getWidth() - 10, (int)size.getHeight() - jbtnHelp.getHeight() - 6);
			this.jbtnPreloadedValues.setLocation(this.jbtnPreloadedValues.getLocation().x, (int)size.getHeight() - jbtnPreloadedValues.getHeight() - 6);
			this.jpanelSimulatedChromatogram.setSize(size.width - jpanelSimulatedChromatogram.getLocation().x - 6, ((size.height - 6 - 6 - this.jbtnNextStep.getHeight()) * 5) / 10);
			this.m_GraphControlTemp.setSize(jpanelSimulatedChromatogram.getWidth() - 3 - 5, jpanelSimulatedChromatogram.getHeight() - 16 - 3);
			this.m_GraphControlTemp.repaint();
			this.jpanelFlowProfile.setLocation(jpanelFlowProfile.getX(), jpanelSimulatedChromatogram.getY() + jpanelSimulatedChromatogram.getHeight() + 6);
			this.jpanelFlowProfile.setSize(jpanelSimulatedChromatogram.getWidth(), size.height - jpanelFlowProfile.getY() - 6 - 6 - this.jbtnNextStep.getHeight());
			this.m_GraphControlHoldUp.setSize(jpanelFlowProfile.getWidth() - 3 - 5, jpanelFlowProfile.getHeight() - 16 - 3);
			this.m_GraphControlHoldUp.repaint();
		}
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}  //  @jve:decl-index=0:visual-constraint="-259,126"
