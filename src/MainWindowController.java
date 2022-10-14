import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class MainWindowController extends JFrame {
    private ListModel models = new ListModel(DataStock.getInstance().getItemList());
    private JPanel panel;
    private JTextField codeField;
    private JTextField decField;
    private JTextField dateField;
    private JButton addButton;
    private JTextField nameField;
    private JTextField typeField;
    private JTextField unitField;
    private JTextField barcodeField;
    private JTextField kdvField;
    private JTable tableList;
    private JButton deleteButton;
    private JButton editButton;
    private JButton copyButton;
    private JButton searchButton;
    private JTextField searchField;

    public MainWindowController() {
        setMainWindow();
        setButtons(false);
    }

    void setMainWindow() {
        setSize(800, 400);
        setContentPane(panel);
        setTitle("Stok Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSearchField();
        setTable(models);
        setButtonAction();
    }

    private void setSearchField() {
        searchField.setText("Stok Kodu Ara");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Stok Kodu Ara")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Stok Kodu Ara");
                }
            }
        });
    }

    private void setTableList(ListModel models) {
        codeField.setText(models.getItem(tableList.getSelectedRow()).getStockCode());
        nameField.setText(models.getItem(tableList.getSelectedRow()).getName());
        typeField.setText(models.getItem(tableList.getSelectedRow()).getStockType());
        unitField.setText(models.getItem(tableList.getSelectedRow()).getUnit());
        barcodeField.setText(models.getItem(tableList.getSelectedRow()).getBarcode());
        kdvField.setText(models.getItem(tableList.getSelectedRow()).getKDV());
        decField.setText(models.getItem(tableList.getSelectedRow()).getDeclaration());
        dateField.setText(models.getItem(tableList.getSelectedRow()).getDate());
    }

    private void setTable(ListModel models) {
        tableList.setModel(models);
        tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setTableList(models);
                setButtons(true);
            }
        });
    }

    private void setButtons(boolean status) {
        editButton.setEnabled(status);
        deleteButton.setEnabled(status);
        copyButton.setEnabled(status);
    }

    private void setButtonAction() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDialog();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDialog(models.getItem(tableList.getSelectedRow()));
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ConfirmDialogInFrame("Listeden silmek istediğinize emin misiniz?", "Stok Sil!") == 0) {
                    DataStock.getInstance().deleteItem(models.getItem(tableList.getSelectedRow()));
                    models.IsEdited(true,DataStock.getInstance().getItemList());
                }
            }
        });
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ConfirmDialogInFrame("Kopyalamak istediğinize emin misiniz?", "Stok Kopyala!") == 0) {
                    Item current = models.getItem(tableList.getSelectedRow());
                    Item coppy = new Item(current.getStockCode() + "_copy", current.getName(), current.getStockTypeInt(), current.getUnit(), current.getBarcode(), current.getKDVDouble(), current.getDeclaration(),current.getDateFormat());
                    try {
                        DataStock.getInstance().insertOrUpdateItem(coppy);
                        models.fireTableDataChanged();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item current = DataStock.getInstance().FindItem(searchField.getText());
                if (current==null){
                    JOptionPane.showConfirmDialog (null, "Kodu Doğru ve Büyük harf ile yazdığınızdan emin olunuz", "Arama Hatası", JOptionPane.DEFAULT_OPTION);
                }else{
                createDialog(current);
                }
            }
        });
    }

    private void createDialog(Item item) {
        AddOrEditDialogController dialog = new AddOrEditDialogController(item);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void createDialog() {
        AddOrEditDialogController dialog = new AddOrEditDialogController();
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private int ConfirmDialogInFrame(String message, String title) {
        int input = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        return input;
    }
}



