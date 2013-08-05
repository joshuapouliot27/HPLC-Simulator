package org.hplcsimulator.panels;

import org.jdesktop.swingx.JXPanel;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class GradientOptions extends JXPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SpecialTableModel tmGradientProgram;
	private JScrollPane jScrollPane = null;
	public JTable jtableGradientProgram = null;
	public JButton jbtnInsertRow = null;
	public JButton jbtnRemoveRow = null;
	public JLabel jlblNonMixingVolume = null;
	public JTextField jtxtNonMixingVolume = null;
	public JLabel jlblNonMixingVolumeUnit = null;
	public JLabel jlblMixingVolume = null;
	public JTextField jtxtMixingVolume = null;
	public JLabel jlblMixingVolumeUnit = null;
	public JLabel jlblDwellVolume = null;
	public JLabel jlblPreColumnVolume = null;
	public JLabel jlblDwellVolumeIndicator = null;
	public JLabel jlblDwellVolumeUnit = null;
	public JLabel jlblDwellTime = null;
	public JLabel jlblDwellTimeIndicator = null;
	public JLabel jlblDwellTimeUnit = null;
	/**
	 * This method initializes 
	 * 
	 */
	public GradientOptions() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        jlblDwellTimeUnit = new JLabel();
        jlblDwellTimeUnit.setBounds(new Rectangle(196, 204, 50, 16));
        jlblDwellTimeUnit.setPreferredSize(new Dimension(50, 16));
        jlblDwellTimeUnit.setText("min");
        jlblDwellTimeUnit.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDwellTimeIndicator = new JLabel();
        jlblDwellTimeIndicator.setBounds(new Rectangle(128, 204, 65, 16));
        jlblDwellTimeIndicator.setText("400");
        jlblDwellTimeIndicator.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDwellTime = new JLabel();
        jlblDwellTime.setBounds(new Rectangle(8, 204, 117, 16));
        jlblDwellTime.setText("Dwell time:");
        jlblDwellVolumeUnit = new JLabel();
        jlblDwellVolumeUnit.setBounds(new Rectangle(196, 184, 50, 16));
        jlblDwellVolumeUnit.setPreferredSize(new Dimension(50, 16));
        jlblDwellVolumeUnit.setText("\u00b5L");
        jlblDwellVolumeUnit.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDwellVolumeIndicator = new JLabel();
        jlblDwellVolumeIndicator.setBounds(new Rectangle(128, 184, 65, 16));
        jlblDwellVolumeIndicator.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblDwellVolumeIndicator.setText("400");
        jlblPreColumnVolume = new JLabel();
        jlblPreColumnVolume.setBounds(new Rectangle(8, 124, 129, 16));
        jlblPreColumnVolume.setText("Pre-column volume:");
        jlblDwellVolume = new JLabel();
        jlblDwellVolume.setBounds(new Rectangle(8, 184, 117, 16));
        jlblDwellVolume.setText("Total dwell volume:");
        jlblMixingVolumeUnit = new JLabel();
        jlblMixingVolumeUnit.setBounds(new Rectangle(196, 144, 50, 16));
        jlblMixingVolumeUnit.setPreferredSize(new Dimension(50, 16));
        jlblMixingVolumeUnit.setText("\u00b5L");
        jlblMixingVolumeUnit.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblMixingVolume = new JLabel();
        jlblMixingVolume.setBounds(new Rectangle(24, 144, 101, 16));
        jlblMixingVolume.setText("Mixing:");
        jlblNonMixingVolumeUnit = new JLabel();
        jlblNonMixingVolumeUnit.setBounds(new Rectangle(196, 164, 50, 16));
        jlblNonMixingVolumeUnit.setPreferredSize(new Dimension(50, 16));
        jlblNonMixingVolumeUnit.setText("\u00b5L");
        jlblNonMixingVolumeUnit.setFont(new Font("Dialog", Font.PLAIN, 12));
        jlblNonMixingVolume = new JLabel();
        jlblNonMixingVolume.setBounds(new Rectangle(24, 164, 101, 16));
        jlblNonMixingVolume.setText("Non-mixing:");
        this.setLayout(null);
        this.setSize(new Dimension(254, 224));
        this.setBackground(Color.white);
        this.add(getJScrollPane(), null);
        this.add(getJbtnInsertRow(), null);
        this.add(getJbtnRemoveRow(), null);
        this.add(jlblNonMixingVolume, null);
        this.add(getJtxtNonMixingVolume(), null);
        this.add(jlblNonMixingVolumeUnit, null);
        this.add(jlblMixingVolume, null);
        this.add(getJtxtMixingVolume(), null);
        this.add(jlblMixingVolumeUnit, null);
        this.add(jlblDwellVolume, null);
        this.add(jlblPreColumnVolume, null);
        this.add(jlblDwellVolumeIndicator, null);
        this.add(jlblDwellVolumeUnit, null);
        this.add(jlblDwellTime, null);
        this.add(jlblDwellTimeIndicator, null);
        this.add(jlblDwellTimeUnit, null);
			
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(8, 4, 233, 81));
			jScrollPane.setViewportView(getJtableGradientProgram());
		}
		return jScrollPane;
	}

	public class SpecialTableModel extends DefaultTableModel 
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 9144486981092084762L;

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
	    @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	}
	
	/**
	 * This method initializes jtableGradientProgram	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJtableGradientProgram() {
		if (jtableGradientProgram == null) 
		{
			Object[] columnNames = {"Time (min)", "% B"};
			Double[][] data = {{0.0, 5.0},{5.0, 95.0}};
	        
			tmGradientProgram = new SpecialTableModel(data, columnNames);

			jtableGradientProgram = new JTable(tmGradientProgram);

			jtableGradientProgram.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			jtableGradientProgram.getTableHeader().setPreferredSize(new Dimension(22, 22));
			jtableGradientProgram.getColumnModel().getColumn(0).setPreferredWidth(150);

			jtableGradientProgram.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		}
		return jtableGradientProgram;
	}

	/**
	 * This method initializes jbtnInsertRow	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtnInsertRow() {
		if (jbtnInsertRow == null) {
			jbtnInsertRow = new JButton();
			jbtnInsertRow.setBounds(new Rectangle(8, 92, 113, 25));
			jbtnInsertRow.setActionCommand("Insert Row");
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
			jbtnRemoveRow.setBounds(new Rectangle(128, 92, 112, 25));
			jbtnRemoveRow.setActionCommand("Remove Row");
			jbtnRemoveRow.setText("Remove Row");
		}
		return jbtnRemoveRow;
	}

	/**
	 * This method initializes jtxtNonMixingVolume	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtNonMixingVolume() {
		if (jtxtNonMixingVolume == null) {
			jtxtNonMixingVolume = new JTextField();
			jtxtNonMixingVolume.setBounds(new Rectangle(128, 162, 65, 20));
			jtxtNonMixingVolume.setText("200");
		}
		return jtxtNonMixingVolume;
	}

	/**
	 * This method initializes jtxtMixingVolume	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtxtMixingVolume() {
		if (jtxtMixingVolume == null) {
			jtxtMixingVolume = new JTextField();
			jtxtMixingVolume.setBounds(new Rectangle(128, 142, 65, 20));
			jtxtMixingVolume.setText("200");
		}
		return jtxtMixingVolume;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
