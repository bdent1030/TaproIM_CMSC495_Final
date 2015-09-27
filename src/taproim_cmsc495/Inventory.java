package taproim_cmsc495;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Spencer
 * This pop-out simply displays SQL table information for Inventory here
 */
public class Inventory extends JFrame {
   
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
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Item = " + itemID;
        
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
        
       
            String sqlCreate = "UPDATE  gunnargo_cmsc495.Item SET "
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
        

            String sqlCreate = "INSERT INTO gunnargo_cmsc495.Item SET "
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


            String sqlSelect = "DELETE item FROM gunnargo_cmsc495.Item = " + itemID;
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
        
        String sqlSelect = "SELECT id FROM gunnargo_cmsc495.Item = " + itemID;

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
    
    public void showInventory()
    {
        this.setTitle("TAPRO-IM Inventory Table");
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        String sql = "SELECT * FROM gunnargo_cmsc495.Item;";



        // Try command to establish JDBC connection with above provided credentials
        try (Connection connection = DriverManager.getConnection( url, userid, password );
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql ))
        {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++)
            {
                columnNames.add( md.getColumnName(i) );
            }

            //  Get row data
            while (rs.next()){
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++)
                {
                    row.add( rs.getObject(i) );
                }

                data.add( row );
            }
        }
        catch (SQLException e){
            System.out.println( e.getMessage() );}

        // Create Vectors and copy over elements from ArrayLists to them
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++)
        {
            ArrayList subArray = (ArrayList)data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ )
            columnNamesVector.add(columnNames.get(i));

        //  Create table with database data    
        JTable table = new JTable(dataVector, columnNamesVector)
        {
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };

        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );

        JPanel buttonPanel = new JPanel();
        getContentPane().add( buttonPanel, BorderLayout.SOUTH );
            
    }  
    
}