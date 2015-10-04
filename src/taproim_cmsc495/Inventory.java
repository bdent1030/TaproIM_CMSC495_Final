package taproim_cmsc495;
import java.sql.*;

//Gunar Gorder, Brandon Dent, Frank Montoya, Kent Kenyon, Spencer Ward
//TAMPROW IM FINAL PROJECT
//CMSC 495 6380
//9-27-15
//Inventory.java
public class Inventory {
    //private variable associated with inventory objects
    private String itemID, description, stockLevel, weight;
    
    //  Establishing location, UID, password and sql command string
    private final String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    private final String userid = "gunnargo_umuc15";
    private final String password = "Ib7t5BRa74mTr0N9aS6";
    private Connection con;
    
    public void setItemID(String itemID)            { this.itemID        = cleanInput(itemID);      }
    public void setDescription(String description)  { this.description   = cleanInput(description); }
    public void setStockLevel(String stockLevel)    { this.stockLevel    = cleanInput(stockLevel);  }
    public void setWeight(String weight)            { this.weight        = cleanInput(weight);      }
    
    public String getItemID()                       { return this.itemID;       }
    public String getDescription()                  { return this.description;  }
    public String getStockLevel()                   { return this.stockLevel;   }
    public String getWeight()                       { return this.weight;       }
    
      /**
     * @param text - the text to be "cleaned"
     * @return the "cleaned" text
     */
    private String cleanInput(String input) {
        String output = "";
        char[] characters = {
            ':', ';', '/', '\\', '\'', '?', '!', 
            '#', '$', '%', '^', '&',  '*',  '(', ')'
        };
        char[] cleaning = input.toCharArray();
        
        for (int i = 0; i < cleaning.length; i++) 
            for (char sc : characters) 
                if (cleaning[i] == sc) cleaning[i] = '~';
        for (int i = 0; i < cleaning.length; i ++) 
            if (cleaning[i] != '~') output += cleaning[i];
        
        return output;
    }
    
    /**
     * Gets the item from the database and sets all of the fields
     * @param itemID the item to searched for
     */
    public void retrieveItem(String itemID){
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Inventory WHERE ItemID = " + itemID.trim() + ";";
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            if (rs.next()) {
                setItemID(rs.getString("ItemID"));
                setDescription(rs.getString("Description"));
                setStockLevel(rs.getString("StockLevel"));
                setWeight(rs.getString("Weight"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Item was not retrieved");
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Updates the item in the inventory
     * @return whether item has been updated successfully
     */
    public boolean updateItem(){
        String sqlUpdate = "UPDATE gunnargo_cmsc495.Inventory SET "
                /*+ "ItemID = '" + getItemID() + "', "*/
                + "Description = '" + getDescription() + "', "
                + "StockLevel = '" + getStockLevel() + "', "
                + "Weight = '" + getWeight() + "'"
                + "WHERE ItemID = '" + getItemID() + "';";

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
     * Inserts a new item into the Inventory table
     * @return boolean value of whether the item was inserted
     */
    public boolean newItem() {
        boolean result;
        String sqlCreate = "INSERT INTO gunnargo_cmsc495.Inventory SET "
                + "ItemID = '" + getItemID() + "', "
                + "Description = '" + getDescription() + "', "
                + "StockLevel = '" + getStockLevel() + "', "
                + "Weight = '" + getWeight() + "';";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlCreate);
            ResultSet rs = stmt.executeQuery("SELECT * FROM gunnargo_cmsc495.Inventory");
            if (rs.last()) setItemID(rs.getString("ItemID"));
            result = !getItemID().equals("");
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
        String sqlSelect = "DELETE FROM gunnargo_cmsc495.Inventory WHERE ItemID = " + itemID.trim() + ";";
        boolean success;

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            success = stmt.execute(sqlSelect);
            con.close();
        } catch (SQLException ex) {
            System.out.println("ItemID: " + itemID + " not found");
            System.out.println(ex.getMessage());
            success = false; // find failed
        }
        return success;
    }

    /**
     * @param itemID the item to check for existence
     * @return boolean value of whether item exists
     */
    public boolean itemExists(String itemID){
        boolean result;
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Inventory WHERE ItemID = " + itemID.trim() + ";";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            if (rs.next()) setItemID(rs.getString("ItemID"));
            result = !getItemID().equals("");
            con.close();
        } catch (SQLException ex) {
            System.out.println("Item ID: " + itemID + "not found");
            System.out.println(ex.getMessage());
            result = false; // find failed
        }
        return result;
    }
}
