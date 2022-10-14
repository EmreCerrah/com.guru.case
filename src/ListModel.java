import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ListModel extends AbstractTableModel {
    String[] cols = {"Stok Kodu", "Stok İsmi"};
    ArrayList<Item> list;

    public ListModel(ArrayList<Item> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return list.get(rowIndex).getStockCode();
        } else if (columnIndex == 1) {
            return list.get(rowIndex).getName();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void setList(ArrayList<Item> list) {
        this.list = list;
    }
    public void IsEdited(boolean b, ArrayList list) {
        if (b == true) {
            this.list=list;
            for(int i=0 ;i<list.size();i++){
            fireTableCellUpdated(i,i);
            fireTableStructureChanged();
            }
        }
    }

    public Item getItem(int rowIndex) {
        if (rowIndex < list.size()) {
            return list.get(rowIndex);
        } else return null;

    }
}
