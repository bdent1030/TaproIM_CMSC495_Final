package taproim_cmsc495;
import java.sql.*;
import java.util.*;

//Gunar Gorder, Brandon Dent, Frank Montoya, Kent Kenyon, Spencer Ward
//TAMPROW IM FINAL PROJECT
//CMSC 495 6380
//9-27-15
//Inventory.java
public class Inventory {
   
    //private variable associated with inventory objects
    private String itemID;
    private String description;
    private String stockLevel;
    private String weight;
    
           
    Connection con;
    
          
    //  Establishing location, UID, password and sql command string
    String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    String userid = "gunnargo_umuc15";
    String password = "Ib7t5BRa74mTr0N9aS6";
    String sql = "SELECT * FROM gunnargo_cmsc495.Inventory";

    

        
    public void setItem(String item, String desc, String amount, String value) {
        this.itemID = item;
        this.description = desc;
        this.stockLevel = amount;
        this.weight = value;
    }
       
    public HashMap retrieveItem(int itemID){

        HashMap<String, String> results = new HashMap<String, String>();
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Inventory = " + itemID;
        
        try {
                con = DriverManager.getConnection(url, userid, password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlSelect);
                String item = rs.getString("ItemID");
                String desc = rs.getString("description");
                String amount = rs.getString("stockLevel");
                String value = rs.getString("weight");
                results.put("ItemID", item);
                results.put("Description", desc);
                results.put("StockLevel", amount);
                results.put("Weight", value);
                con.close();
            } catch (SQLException ex) {
                System.out.println("Item was not retrieved");
            }
        
        return results;
    }
    
    
        public boolean updateItem(ArrayList<String> info){
        
        String item = info.get(0);
        String desc = info.get(1);
        String amount = info.get(2);
        String value = info.get(3);
        
       
            String sqlCreate = "UPDATE  gunnargo_cmsc495.Inventory SET "
                    + "Item = " + item
                    + " Description = " + desc
                    + " StockLevel = " + amount
                    + " Weight = " + value;
        
            try {
                con = DriverManager.getConnection(url, userid, password);
                Statement stmt = con.createStatement();
                boolean success = stmt.execute(sqlCreate);
                con.close();
                return success;
            } catch (SQLException ex) {
                System.out.println("Item was not updated");
                return false; // creation failed
            }
        }
        
       
        
       
    public boolean newItem(ArrayList<String> info){
        
        String item = info.get(0);
        String desc = info.get(1);
        String amount = info.get(2);
        String value = info.get(3);
        
        boolean result = false;
        

            String sqlCreate = "INSERT INTO gunnargo_cmsc495.Inventory SET "
                    + "Item = " + item
                    + " Description = " + desc
                    + " StockLevel = " + amount
                    + " Weight = " + value;
        
            try {
                con = DriverManager.getConnection(url, userid, password);
                Statement stmt = con.createStatement();
                boolean success = stmt.execute(sqlCreate);
                con.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("Item was not updated");
                return false; // creation failed
            }

    }
    
       
    public boolean deleteItem(String itemID){


            String sqlSelect = "DELETE item FROM gunnargo_cmsc495.Inventory = " + itemID;
            //boolean success;

            try {
                con = DriverManager.getConnection(url, userid, password);
                Statement stmt = con.createStatement();
                //success = stmt.execute(sqlSelect);
                con.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("ItemID: " + itemID +"not found");
                return false; // find failed
            }
        
        
    }
        
    public boolean itemExists(String itemID){
        boolean result;
        
        String sqlSelect = "SELECT id FROM gunnargo_cmsc495.Inventory = " + itemID;

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            result = stmt.execute(sqlSelect);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Customer ID: " + itemID +"not found");
            return false; // find failed
        }
        
        return result;
    }
}
