import java.sql.*;

public class Item {

    private final String StockCode;
    private final String Name;
    private final int StockType;
    private final String unit;
    private final String barcode;
    private final double KDV;
    private final String declaration;
    private final Date date;

    public Item(String stockCode, String name, int stockType, String unit, String barcode, double KDV, String declaration, Date date) {
        StockCode = stockCode;
        Name = name;
        StockType = stockType;
        this.unit = unit;
        this.barcode = barcode;
        this.KDV = KDV;
        this.declaration = declaration;
        this.date = date;
    }


    @Override
    public String toString() {
        return
                "Stok kodu:" + StockCode + ", Stok Adı: " + Name;
    }

    public String getStockCode() {
        return StockCode;
    }

    public String getStockType() {
        return String.valueOf(StockType);
    }

    public int getStockTypeInt() {
        return StockType;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return Name;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getKDV() {
        return String.valueOf(KDV);
    }

    public double getKDVDouble() {
        return KDV;
    }

    public String getDeclaration() {
        return declaration;
    }

    public String getDate() {
        return String.valueOf(date);
    }

    public Date getDateFormat() {
        return date;
    }
    /*
    private void saveNewItem(Item current) {
        try (Connection conn = DriverManager.getConnection(CONNETION_TYPE + URL + "?user=" + USERNAME + "&password=" + PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO stock_card.stock_card_list (kod,ad,tip,birim,barkod,kdv_tipi,aciklama,olusturma_tarihi) VALUES (?, ?, ?, ? , ?, ?, ?, ?)")) {
            stmt.setString(1, getStockCode());
            stmt.setString(2, getName());
            stmt.setInt(3, getStockTypeInt());
            stmt.setString(4,getUnit());
            stmt.setString(5, getBarcode());
            stmt.setDouble(6, getKDVDouble());
            stmt.setString(7,getDeclaration());
            stmt.setDate(8, getDateFormat());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */

}
