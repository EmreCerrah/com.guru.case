import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;

public class AddOrEditDialogController extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField stockCode;
    private JComboBox stockType;
    private JComboBox stockUnit;
    private JComboBox kdvType;
    private JTextArea declaration;
    private JFormattedTextField initDate;
    public JTextField stockName;
    private JTextField barcode;

    public AddOrEditDialogController(Item item) {
        setAddOrEditDialog();
        fillDialog(item);
    }   //This work When to Edit Item

    public AddOrEditDialogController() {
        setAddOrEditDialog();
    }   //This work When to Insert Item

    private void setAddOrEditDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setButtonListeners();
    }

    private void fillDialog(Item item) {
        stockCode.setText(item.getStockCode());
        stockName.setText(item.getName());
        stockType.setSelectedItem(item.getStockType());
        stockUnit.setSelectedItem(item.getUnit());
        barcode.setText(item.getBarcode());
        kdvType.setSelectedItem(item.getKDV());
        declaration.setText(item.getDeclaration());
        initDate.setText(item.getDate());
    }

    private void onOK() {
        // add Item to list
        String code = stockCode.getText();
        String name = stockName.getText();
        String type = stockType.getSelectedItem().toString();
        int typeInt = Integer.parseInt(type);
        String unit = stockUnit.getSelectedItem().toString();
        String barcodes = barcode.getText();
        String kdv = kdvType.getSelectedItem().toString();
        double kdvD = Double.parseDouble(kdv);
        String dec = declaration.getText();
        LocalDate date = LocalDate.now();
        Date dateF = Date.valueOf(date);

        Item newItem = new Item(code, name, typeInt, unit, barcodes, kdvD, dec, dateF);
        try {
            DataStock.getInstance().insertOrUpdateItem(newItem);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }


    private void createUIComponents() {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter("####-##-##");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        initDate = new JFormattedTextField(mask);
    }

    private void setButtonListeners() {
        // call onOK() when buttonOK is clicked
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when buttonCancel is clicked
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
