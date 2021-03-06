package hu.bernatzoltan.dijkalkulator.ui.swing;

import hu.bernatzoltan.dijkalkulator.model.Allocation;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelIF;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelListenerIF;
import hu.bernatzoltan.dijkalkulator.model.CapacityType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BZoli
 */
public class PointsTableModel extends javax.swing.table.AbstractTableModel implements AllocationModelListenerIF {

    private AllocationModelIF model;
    private static final String SUMMA_STR = "összesen";
    Class[] types = new Class[]{String.class, Double.class};
    boolean[] canEdit = new boolean[]{false, false};
    String[] columnNames = new String[]{"Pont", "Összesen"};

    public PointsTableModel(AllocationModelIF model) {
        super();
        this.model = model;
        model.addModelListener(this);
    }

    @Override
    public void allocationsLoaded(List<Allocation> allocations) {
        fireTableDataChanged();
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return types.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        //a pontokra osszegzett sorok +1 a szummanak
        return model.getTotalByPoint().size() + 1;
    }

    @Override
    public Object getValueAt(int row, int col) {
        //az ucso sor az ossszeg
        if (row == model.getTotalByPoint().size()) {
            if (col == 0) {
                return SUMMA_STR;
            }
            Double summ = 0D;
            for (Double value : model.getTotalByPoint().values()) {
                summ += value;
            }
            return summ;
        }

        
        List<String> sortedKeys = new ArrayList(model.getTotalByPoint().keySet());
        Collections.sort(sortedKeys);
        switch (col) {
            case 0:
                return sortedKeys.get(row);
            case 1:
                return model.getTotalByPoint().get(sortedKeys.get(row));
            default:
                throw new IllegalArgumentException("Bad cell (" + row + ", " + col + ")");
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        //nem editalhatoak a cellak
    }
}
