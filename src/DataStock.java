import java.sql.*;
import java.util.ArrayList;


public class DataStock {
    private static final DataStock instance = new DataStock();
    private final String USERNAME = "root";
    private final String PASSWORD = "Mysql";
    private final String CONNETION_TYPE = "jdbc:mysql:";
    private final String URL = "//localhost:3306";
    private final ArrayList<Item> itemList = new ArrayList<>();

    private DataStock() {
        try {
            loadAllItems();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DataStock getInstance() {
        return instance;
    }

    public Item FindItem(String stockCode) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getStockCode().compareTo(stockCode)==0) {
                return itemList.get(i);
            } else {
                continue;
            }
        }
        return null;
    }

    public void insertOrUpdateItem(Item o) throws Exception {
        System.out.println(hasItem(o));
        insertOrUpdateItemToDB(o);
        this.itemList.add(o);
    }

    public void deleteItem(Item o) {
        deleteItemToDB(o);
        itemList.remove(o);
    }

    public void loadAllItems() throws Exception {
        try (Connection con = DriverManager.getConnection(CONNETION_TYPE + URL + "?user=" + USERNAME + "&password=" + PASSWORD);
             Statement sorgu = con.createStatement()) {
            ResultSet sonuc = sorgu.executeQuery("SELECT * FROM stock_card.stock_card_list;");

            while (sonuc.next()) {
                String stockCode = sonuc.getString(1);
                String name = sonuc.getString(2);
                int stockType = sonuc.getInt(3);
                String unit = sonuc.getString(4);
                String barcode = sonuc.getString(5);
                double KDV = sonuc.getDouble(6);
                String declaration = sonuc.getString(7);
                Date date = sonuc.getDate(8);

                Item item = new Item(stockCode, name, stockType, unit, barcode, KDV, declaration, date);
                this.itemList.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertOrUpdateItemToDB(Item current) throws Exception {
        if (hasItem(current)) {
            saveEditedItem(current);
        } else {
            saveNewItem(current);
        }
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    private boolean hasItem(Item current) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getStockCode().compareTo(current.getStockCode()) == 0) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    private void saveNewItem(Item current) {
        try (Connection conn = DriverManager.getConnection(CONNETION_TYPE + URL + "?user=" + USERNAME + "&password=" + PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO stock_card.stock_card_list (kod,ad,tip,birim,barkod,kdv_tipi,aciklama,olusturma_tarihi) VALUES (?, ?, ?, ? , ?, ?, ?, ?)")) {
            stmt.setString(1, current.getStockCode());
            stmt.setString(2, current.getName());
            stmt.setInt(3, current.getStockTypeInt());
            stmt.setString(4, current.getUnit());
            stmt.setString(5, current.getBarcode());
            stmt.setDouble(6, current.getKDVDouble());
            stmt.setString(7, current.getDeclaration());
            stmt.setDate(8, current.getDateFormat());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveEditedItem(Item current) {
        try (Connection conn = DriverManager.getConnection(CONNETION_TYPE + URL + "?user=" + USERNAME + "&password=" + PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE `stock_card`.`stock_card_list` SET `ad` = ? , `tip` = ? , `birim` = ? , `barkod` = ? , `kdv_tipi` = ? ,aciklama = ? ,olusturma_tarihi = ? WHERE ( `kod` = ? )")) {
            stmt.setString(1, current.getName());                               // UPDATE `stock_card`.`stock_card_list` SET `ad` = 'fdf' WHERE (`kod` = '45');
            stmt.setInt(2, current.getStockTypeInt());
            stmt.setString(3, current.getUnit());
            stmt.setString(4, current.getBarcode());
            stmt.setDouble(5, current.getKDVDouble());
            stmt.setString(6, current.getDeclaration());
            stmt.setDate(7, current.getDateFormat());
            //Condition
            stmt.setString(8, current.getStockCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItemToDB(Item current) {
        try (Connection conn = DriverManager.getConnection(CONNETION_TYPE + URL + "?user=" + USERNAME + "&password=" + PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM stock_card.stock_card_list WHERE ( `kod` = ? );")) {
            stmt.setString(1, current.getStockCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}