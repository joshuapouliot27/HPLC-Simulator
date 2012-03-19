package org.retentionprediction;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Rectangle;

import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.media.opengl.GLCapabilities;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.retentionprediction.TopPanel.SpecialTableModel;

import java.awt.GridBagConstraints;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class TopPanel2 extends JPanel implements TableModelListener, Scrollable, ComponentListener
{

	private static final long serialVersionUID = 1L;
	public JButton jbtnNextStep = null;
	public JButton jbtnPreviousStep = null;
	public JPanel jpanelGradientProfile = null;
	public GraphControl m_GraphControlTemp = null;
	public JPanel jpanelFlowProfile = null;
	public GraphControl m_GraphControlHoldUp = null;
	public GLCapabilities caps2 = null;
	public JPanel jpanelStep5 = null;
	public JScrollPane jScrollPane = null;
	public JTable jtableOutput = null;
	public JButton jbtnCalculate = null;
	public NoEditTableModel tmOutputModel = null;
	public JLabel jlblIterationNumber = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel111 = null;
	public JLabel jlblVariance = null;
	public JLabel jlblPhase = null;
	public JLabel jlblTimeElapsed = null;
	private JLabel jLabel12 = null;
	public JLabel jlblLastVariance = null;
	private JLabel jLabel121 = null;
	public JLabel jlblPercentImprovement = null;
	public JButton jbtnHelp = null;
	public JProgressBar jProgressBar = null;
	private JLabel jLabel1111 = null;
	public JPanel jpanelStep6 = null;
	public JScrollPane jScrollPane1 = null;
	public JTable jtablePredictions = null;
	public JProgressBar jProgressBar2 = null;
	public JButton jbtnPredict = null;
	private JLabel jLabel11111 = null;
	public NoEditTableModel tmPredictionModel = null;
	/**
	 * This is the default constructor
	 */
	public TopPanel2() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBounds(new Rectangle(0, 0, 943, 615));
        this.setPreferredSize(new Dimension(890,570));
        this.setMinimumSize(new Dimension(890,570));

        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        
		m_GraphControlTemp = new GraphControl(caps);
		m_GraphControlTemp.setBounds(new Rectangle(4, 16, 461, 285));
		m_GraphControlTemp.setControlsEnabled(false);

        GLCapabilities caps2 = new GLCapabilities();
        caps2.setDoubleBuffered(true);
        caps2.setHardwareAccelerated(true);
        
		m_GraphControlHoldUp = new GraphControl(caps2);
		m_GraphControlHoldUp.setBounds(new Rectangle(3, 16, 462, 241));
		m_GraphControlHoldUp.setControlsEnabled(false);

		this.add(getJbtnNextStep(), null);
		this.add(getJbtnPreviousStep(), null);
		this.add(getJpanelGradientProfile(), null);
		this.add(getJpanelFlowProfile(), null);
		this.setVisible(true);
		this.add(getJbtnHelp(), null);
		this.add(getJpanelStep6(), null);
		this.add(getJpanelStep5(), null);
		
		this.tmOutputModel.addTableModelListener(this);
		this.addComponentListener(this);
	}

	/**
	 * This method initializes jbtnNextStep	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnNextStep() {
		if (jbtnNextStep == null) {
			jbtnNextStep = new JButton();
			jbtnNextStep.setHorizontalAlignment(SwingConstants.CENTER);
			jbtnNextStep.setHorizontalTextPosition(SwingConstants.LEADING);
			jbtnNextStep.setIcon(new ImageIcon(getClass().getResource("/org/retentionprediction/images/forward.png")));
			jbtnNextStep.setText("Next Step  ");
			jbtnNextStep.setEnabled(false);
			jbtnNextStep.setLocation(new Point(760, 576));
			jbtnNextStep.setSize(new Dimension(178, 34));
			jbtnNextStep.setActionCommand("Next Step2");
		}
		return jbtnNextStep;
	}

	/**
	 * This method initializes jbtnPreviousStep	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnPreviousStep() {
		if (jbtnPreviousStep == null) {
			jbtnPreviousStep = new JButton();
			jbtnPreviousStep.setHorizontalAlignment(SwingConstants.CENTER);
			jbtnPreviousStep.setHorizontalTextPosition(SwingConstants.TRAILING);
			jbtnPreviousStep.setIcon(new ImageIcon(getClass().getResource("/org/retentionprediction/images/back.png")));
			jbtnPreviousStep.setText("  Previous Step");
			jbtnPreviousStep.setSize(new Dimension(178, 34));
			jbtnPreviousStep.setLocation(new Point(6, 576));
			jbtnPreviousStep.setActionCommand("Previous Step");
		}
		return jbtnPreviousStep;
	}

	/**
	 * This method initializes jpanelGradientProfile	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelGradientProfile() {
		if (jpanelGradientProfile == null) {
			jpanelGradientProfile = new JPanel();
			jpanelGradientProfile.setLayout(null);
			jpanelGradientProfile.setPreferredSize(new Dimension(615, 477));
			jpanelGradientProfile.setBorder(BorderFactory.createTitledBorder(null, "Temperature Program", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelGradientProfile.setBackground(Color.white);
			jpanelGradientProfile.setSize(new Dimension(469, 305));
			jpanelGradientProfile.setLocation(new Point(6, 0));
			jpanelGradientProfile.add(m_GraphControlTemp, null);
		}
		return jpanelGradientProfile;
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
			jpanelFlowProfile.setPreferredSize(new Dimension(615, 477));
			jpanelFlowProfile.setBorder(BorderFactory.createTitledBorder(null, "Hold-up Time vs. Temperature", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelFlowProfile.setBackground(Color.white);
			jpanelFlowProfile.setSize(new Dimension(469, 261));
			jpanelFlowProfile.setLocation(new Point(6, 308));
			jpanelFlowProfile.add(m_GraphControlHoldUp, null);
		}
		return jpanelFlowProfile;
	}

	/**
	 * This method initializes jpanelStep5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelStep5() {
		if (jpanelStep5 == null) {
			jLabel1111 = new JLabel();
			jLabel1111.setBounds(new Rectangle(68, 476, 173, 16));
			jLabel1111.setText("Status:");
			jlblPercentImprovement = new JLabel();
			jlblPercentImprovement.setBounds(new Rectangle(248, 416, 145, 17));
			jlblPercentImprovement.setText("");
			jLabel121 = new JLabel();
			jLabel121.setBounds(new Rectangle(68, 416, 173, 16));
			jLabel121.setText("% improvement:");
			jlblLastVariance = new JLabel();
			jlblLastVariance.setBounds(new Rectangle(248, 396, 145, 17));
			jlblLastVariance.setText("");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(68, 396, 173, 16));
			jLabel12.setText("Last iteration variance:");
			jlblTimeElapsed = new JLabel();
			jlblTimeElapsed.setBounds(new Rectangle(248, 456, 145, 17));
			jlblTimeElapsed.setText("");
			jlblPhase = new JLabel();
			jlblPhase.setBounds(new Rectangle(248, 436, 145, 17));
			jlblPhase.setText("I");
			jlblVariance = new JLabel();
			jlblVariance.setBounds(new Rectangle(248, 376, 145, 16));
			jlblVariance.setText("");
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(68, 456, 173, 16));
			jLabel111.setText("Time Elapsed:");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(68, 436, 173, 16));
			jLabel11.setText("Phase:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(68, 376, 173, 16));
			jLabel1.setText("Variance (\u03C3\u00B2):");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(68, 356, 173, 16));
			jLabel.setText("Iteration #:");
			jlblIterationNumber = new JLabel();
			jlblIterationNumber.setBounds(new Rectangle(248, 356, 145, 16));
			jlblIterationNumber.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jlblIterationNumber.setText("");
			jpanelStep5 = new JPanel();
			jpanelStep5.setLayout(null);
			jpanelStep5.setBorder(BorderFactory.createTitledBorder(null, "Step #4: Back-Calculate Gradient and Flow Profiles", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelStep5.setBackground(Color.white);
			jpanelStep5.setBounds(new Rectangle(476, 0, 461, 569));
			jpanelStep5.add(getJScrollPane(), null);
			jpanelStep5.add(getJbtnCalculate(), null);
			jpanelStep5.add(jlblIterationNumber, null);
			jpanelStep5.add(jLabel, null);
			jpanelStep5.add(jLabel1, null);
			jpanelStep5.add(jLabel11, null);
			jpanelStep5.add(jLabel111, null);
			jpanelStep5.add(jlblVariance, null);
			jpanelStep5.add(jlblPhase, null);
			jpanelStep5.add(jlblTimeElapsed, null);
			jpanelStep5.add(jLabel12, null);
			jpanelStep5.add(jlblLastVariance, null);
			jpanelStep5.add(jLabel121, null);
			jpanelStep5.add(jlblPercentImprovement, null);
			jpanelStep5.add(getJProgressBar(), null);
			jpanelStep5.add(jLabel1111, null);
		}
		return jpanelStep5;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(8, 20, 445, 329));
			jScrollPane.setViewportView(getJtableOutput());
		}
		return jScrollPane;
	}

	class NoEditTableModel extends DefaultTableModel 
	{
	    public NoEditTableModel(final Object[] columnNames, final int rowCount) 
	    {
	        super(convertToVector(columnNames), rowCount);
	    }
	    
	    public NoEditTableModel(final Object[][] data, final Object[] columnNames) 
	    {
	        setDataVector(data, columnNames);
	    }

	    public boolean isCellEditable(int row, int column) 
	    {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        return false;
	    }
	    
	    /*
	     * JTable uses this method to determine the default renderer/
	     * editor for each cell.  If we didn't implement this method,
	     * then the last column would contain text ("true"/"false"),
	     * rather than a check box.
	     */
	    //public Class getColumnClass(int c) {
	    //    return getValueAt(0, c).getClass();
	    //}

	}
	
	/**
	 * This method initializes jtableOutput	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJtableOutput() {
		if (jtableOutput == null) 
		{
			Object[] columnNames = {"Compound", "Expt tR (min)", "Calc tR (min)", "Diff (min)"};
			tmOutputModel = new NoEditTableModel(columnNames, 0);
			jtableOutput = new JTable(tmOutputModel);
			
			jtableOutput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			jtableOutput.setBounds(new Rectangle(0, 0, 20, 20));
			
			jtableOutput.getColumnModel().getColumn(0).setPreferredWidth(200);
			
			jtableOutput.setAutoCreateColumnsFromModel(false);
		}
		return jtableOutput;
	}

	/**
	 * This method initializes jbtnCalculate	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnCalculate() {
		if (jbtnCalculate == null) {
			jbtnCalculate = new JButton();
			jbtnCalculate.setText("Back-Calculate Profiles");
			jbtnCalculate.setBounds(new Rectangle(144, 524, 178, 34));
			jbtnCalculate.setActionCommand("Calculate");
		}
		return jbtnCalculate;
	}

	@Override
	public void tableChanged(TableModelEvent arg0) 
	{
	    //Vector data = tmOutputModel.getDataVector();
	    //Collections.sort(data, new ColumnSorter(1));
	}

	class ColumnSorter implements Comparator 
	{
		int colIndex;

		ColumnSorter(int colIndex) 
		{
			this.colIndex = colIndex;
		}

		public int compare(Object a, Object b) 
		{
		    Vector v1 = (Vector) a;
		    Vector v2 = (Vector) b;
		    Object o1 = v1.get(colIndex);
		    Object o2 = v2.get(colIndex);
	
		    if (o1 instanceof String && ((String) o1).length() == 0) {
		      o1 = null;
		    }
		    if (o2 instanceof String && ((String) o2).length() == 0) {
		      o2 = null;
		    }
	
		    if (o1 == null && o2 == null) {
		    	return 0;
		    } else if (o1 == null) {
		    	return 1;
		    } else if (o2 == null) {
		    	return -1;
		    } else if (o1 instanceof Comparable) {
	
		    	return ((Comparable) o1).compareTo(o2);
		    } else {
	
		    	return o1.toString().compareTo(o2.toString());
		    }
		}
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
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setBounds(new Rectangle(68, 496, 325, 19));
			jProgressBar.setBackground(Color.white);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jpanelStep6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpanelStep6() {
		if (jpanelStep6 == null) {
			jLabel11111 = new JLabel();
			jLabel11111.setBounds(new Rectangle(68, 476, 173, 16));
			jLabel11111.setText("Status:");
			jpanelStep6 = new JPanel();
			jpanelStep6.setLayout(null);
			jpanelStep6.setBounds(new Rectangle(476, 0, 461, 569));
			jpanelStep6.setBorder(BorderFactory.createTitledBorder(null, "Step #5: Predict Retention of Other Database Compounds", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jpanelStep6.setBackground(Color.white);
			jpanelStep6.setVisible(true);
			jpanelStep6.add(getJScrollPane1(), null);
			jpanelStep6.add(getJProgressBar2(), null);
			jpanelStep6.add(getJbtnPredict(), null);
			jpanelStep6.add(jLabel11111, null);
		}
		return jpanelStep6;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(8, 20, 445, 449));
			jScrollPane1.setViewportView(getJtablePredictions());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jtablePredictions	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJtablePredictions() {
		if (jtablePredictions == null) 
		{
			//Object[] columnNames = {"Compound", "Predicted tR (min)"};
			Object[] columnNames = {"Compound", "Predicted tR (min)", "Min error (min)"};
			tmPredictionModel = new NoEditTableModel(columnNames, 0);
			jtablePredictions = new JTable(tmPredictionModel);
			
			jtablePredictions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			jtablePredictions.setCellSelectionEnabled(true);

			jtablePredictions.setBounds(new Rectangle(0, 0, 20, 20));
			
			jtablePredictions.getColumnModel().getColumn(0).setPreferredWidth(120);
			//jtablePredictions.getColumnModel().getColumn(0).setPreferredWidth(200);
			
			jtablePredictions.setAutoCreateColumnsFromModel(false);
		}
		return jtablePredictions;
	}

	/**
	 * This method initializes jProgressBar2	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar2() {
		if (jProgressBar2 == null) {
			jProgressBar2 = new JProgressBar();
			jProgressBar2.setBackground(Color.white);
			jProgressBar2.setBounds(new Rectangle(68, 496, 325, 19));
		}
		return jProgressBar2;
	}

	/**
	 * This method initializes jbtnPredict	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnPredict() {
		if (jbtnPredict == null) {
			jbtnPredict = new JButton();
			jbtnPredict.setText("Predict Retention Times");
			jbtnPredict.setBounds(new Rectangle(144, 524, 178, 34));
			jbtnPredict.setActionCommand("Predict");
		}
		return jbtnPredict;
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
			this.jbtnPreviousStep.setLocation(jbtnPreviousStep.getX(), size.height - jbtnPreviousStep.getHeight() - 6);
			this.jbtnNextStep.setLocation(size.width - 6 - jbtnNextStep.getWidth(), size.height - jbtnNextStep.getHeight() - 6);
			this.jbtnHelp.setLocation(jbtnNextStep.getX() - 10 - jbtnNextStep.getWidth(), size.height - jbtnHelp.getHeight() - 6);
			this.jpanelStep5.setLocation(((size.width) / 2) + 3, jpanelStep5.getY());
			this.jpanelStep5.setSize(size.width - jpanelStep5.getX() - 6, size.height - 6 - jbtnNextStep.getHeight() - 6);
			this.jbtnCalculate.setLocation((jpanelStep5.getWidth() / 2) - (jbtnCalculate.getWidth() / 2), jpanelStep5.getHeight() - 15 - jbtnCalculate.getHeight());
			this.jProgressBar.setLocation((jpanelStep5.getWidth() / 2) - (jProgressBar.getWidth() / 2), jbtnCalculate.getY() - jProgressBar.getHeight() - 12);
			this.jLabel1111.setLocation(jProgressBar.getX(), jProgressBar.getY() - 4 - jLabel1111.getHeight());
			this.jLabel111.setLocation(jLabel1111.getX(), jLabel1111.getY() - 4 - jLabel111.getHeight());
			this.jLabel11.setLocation(jLabel111.getX(), jLabel111.getY() - 4 - jLabel11.getHeight());
			this.jLabel121.setLocation(jLabel11.getX(), jLabel11.getY() - 4 - jLabel121.getHeight());
			this.jLabel12.setLocation(jLabel121.getX(), jLabel121.getY() - 4 - jLabel12.getHeight());
			this.jLabel1.setLocation(jLabel12.getX(), jLabel12.getY() - 4 - jLabel1.getHeight());
			this.jLabel.setLocation(jLabel1.getX(), jLabel1.getY() - 4 - jLabel.getHeight());
			this.jScrollPane.setSize(jpanelStep5.getWidth() - 8 - 8, jLabel.getY() - 6 - jScrollPane.getY());
			this.jlblTimeElapsed.setLocation((jProgressBar.getX() + jProgressBar.getWidth()) - this.jlblTimeElapsed.getWidth(), jLabel111.getY());
			this.jlblPhase.setLocation((jProgressBar.getX() + jProgressBar.getWidth()) - this.jlblPhase.getWidth(), jLabel11.getY());
			this.jlblPercentImprovement.setLocation((jProgressBar.getX() + jProgressBar.getWidth()) - this.jlblPercentImprovement.getWidth(), jLabel121.getY());
			this.jlblLastVariance.setLocation((jProgressBar.getX() + jProgressBar.getWidth()) - this.jlblLastVariance.getWidth(), jLabel12.getY());
			this.jlblVariance.setLocation((jProgressBar.getX() + jProgressBar.getWidth()) - this.jlblVariance.getWidth(), jLabel1.getY());
			this.jlblIterationNumber.setLocation((jProgressBar.getX() + jProgressBar.getWidth()) - this.jlblIterationNumber.getWidth(), jLabel.getY());
			this.jpanelStep6.setLocation(jpanelStep5.getLocation());
			this.jpanelStep6.setSize(jpanelStep5.getSize());
			this.jbtnPredict.setLocation((jpanelStep6.getWidth() / 2) - (jbtnPredict.getWidth() / 2), jpanelStep6.getHeight() - 15 - jbtnPredict.getHeight());
			this.jProgressBar2.setLocation((jpanelStep6.getWidth() / 2) - (jProgressBar2.getWidth() / 2), jbtnPredict.getY() - jProgressBar2.getHeight() - 12);
			this.jLabel11111.setLocation(jProgressBar2.getX(), jProgressBar2.getY() - 4 - jLabel11111.getHeight());
			this.jScrollPane1.setSize(jpanelStep6.getWidth() - 8 - 8, jLabel11111.getY() - 6 - jScrollPane1.getY());
			this.jpanelGradientProfile.setSize(size.width - jpanelStep5.getX() - 6, (size.height - 6 - jbtnNextStep.getHeight() - 6 - 6) / 2);
			this.m_GraphControlTemp.setSize(jpanelGradientProfile.getWidth() - 3 - 5, jpanelGradientProfile.getHeight() - 16 - 3);
			this.m_GraphControlTemp.repaint();
			this.jpanelFlowProfile.setLocation(jpanelFlowProfile.getX(), jpanelGradientProfile.getY() + jpanelGradientProfile.getHeight() + 6);
			this.jpanelFlowProfile.setSize(jpanelGradientProfile.getWidth(), size.height - jpanelFlowProfile.getY() - 6 - jbtnNextStep.getHeight() - 6);
			this.m_GraphControlHoldUp.setSize(jpanelFlowProfile.getWidth() - 3 - 5, jpanelFlowProfile.getHeight() - 16 - 3);
			this.m_GraphControlHoldUp.repaint();
			/*this.jpanelStep4.setSize(this.jpanelStep4.getWidth(), size.height - 438);
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
			this.m_GraphControlHoldUp.repaint();*/
		}
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
