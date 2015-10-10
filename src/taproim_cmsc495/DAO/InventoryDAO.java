package taproim_cmsc495.DAO;
import taproim_cmsc495.DTO.InventoryDTO;
import java.sql.*;

//Gunar Gorder, Brandon Dent, Frank Montoya, Kent Kenyon, Spencer Ward
//TAMPROW IM FINAL PROJECT
//CMSC 495 6380
//9-27-15
//Inventory.java
public class InventoryDAO {
    //  Establishing location, UID, password and sql command string
    private final String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    private final String userid = "gunnargo_umuc15";
    private final String password = "Ib7t5BRa74mTr0N9aS6";
    private Connection con;
    
    /**
     * Gets the item from the database and sets all of the fields
     * @param itemID the item to searched for
     * @return an Inventory object
     */
    public InventoryDTO retrieveItem(String itemID){
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Inventory WHERE ItemID = " + itemID.trim() + ";";
        InventoryDTO dto = new InventoryDTO();
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);            
            if (rs.next()) {
                dto.setItemID(rs.getString("ItemID"));
                dto.setDescription(rs.getString("Description"));
                dto.setStockLevel(rs.getString("StockLevel"));
                dto.setWeight(rs.getString("Weight"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Item was not retrieved");
            System.out.println(ex.getMessage());
        }
        
        return dto;
    }
    
    /**
     * Updates the item in the inventory
     * @param dto the Inventory object updates
     * @return whether item has been updated successfully
     */
    public boolean updateItem(InventoryDTO dto) {
        String sqlUpdate = "UPDATE gunnargo_cmsc495.Inventory SET "
                /*+ "ItemID = '" + getItemID() + "', "*/
                + "Description = '" + dto.getDescription() + "', "
                + "StockLevel = '" + dto.getStockLevel() + "', "
                + "Weight = '" + dto.getWeight() + "'"
                + "WHERE ItemID = '" + dto.getItemID() + "';";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlUpdate);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Item was not updated");
            System.out.println(ex.getMessage());
            return false; // creation failed
        }
    }
    
    /**
     * Inserts a new item into the InventoryDAO table
     * @param dto the Inventory object to be inserted
     * @return boolean value of whether the item was inserted
     */
    public boolean newItem(InventoryDTO dto) {
        boolean result;
        String sqlCreate = "INSERT INTO gunnargo_cmsc495.Inventory SET "
                + "ItemID = '" + dto.getItemID() + "', "
                + "Description = '" + dto.getDescription() + "', "
                + "StockLevel = '" + dto.getStockLevel() + "', "
                + "Weight = '" + dto.getWeight() + "';";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlCreate);
            ResultSet rs = stmt.executeQuery("SELECT * FROM gunnargo_cmsc495.Inventory");
            if (rs.last()) dto.setItemID(rs.getString("ItemID"));
            result = !dto.getItemID().equals("");
            con.close();
        } catch (SQLException ex) {
            System.out.println("Item was not updated");
            System.out.println(ex.getMessage());
            result = false; // creation failed
        }
        
        return result;
    }
    
    /**
     * @param itemID item to be deleted
     * @return boolean value of whether the item was deleted from the table
     */
    public boolean deleteItem(String itemID){
        InventoryDTO dto = new InventoryDTO();
        dto.setItemID(itemID);
        
        String sqlSelect = "DELETE FROM gunnargo_cmsc495.Inventory WHERE ItemID = " + dto.getItemID() + ";";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlSelect);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("ItemID: " + itemID + " not found");
            System.out.println(ex.getMessage());
            return false; // find failed
        }
    }

    /**
     * @param itemID the item to check for existence
     * @return boolean value of whether item exists
     */
    public boolean itemExists(String itemID){
        InventoryDTO dto = new InventoryDTO();
        dto.setItemID(itemID);
        
        boolean result;
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Inventory WHERE ItemID = " + dto.getItemID() + ";";
        String strResult = "";
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            
            if (rs.next()){
                strResult = rs.getString("ItemID");
            }
            result = !strResult.equals("");
            con.close();
            
        } catch (SQLException ex) {
            System.out.println("Item ID: " + itemID + "not found");
            System.out.println(ex.getMessage());
            result = false; // find failed
        }
        
        return result;
    }
}
