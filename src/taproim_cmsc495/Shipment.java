package taproim_cmsc495;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/**
 * 
 * @author Dent, Gorder, Kenyon, Montoya, Ward
 * This pop-out simply displays SQL table information for Shipment
 */
public class Shipment extends JFrame {
    SQLTransferManager sqlMgr = new SQLTransferManager();
    ArrayList<String> columns = new ArrayList<>();
    
    public boolean newShipment(ArrayList<String> info) {
        String sqlQ = "INSERT INTO gunnargo_cmsc495.Shipment SET "
                + "ItemID = " + info.get(0) + ", "
                + "Destination = " + info.get(1) + ", "
                + "Weight = " + info.get(2) + ", "
                + "NumItems = " + info.get(3) + ";";
        boolean success;
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            success = stmt.execute(sqlQ);
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        return success;
    }
    
    
    public boolean deleteShipment(String id) {
        String sqlQ = "DELETE FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + id.trim() + ";";
        boolean success;
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            success = stmt.execute(sqlQ);
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        return success;
    }
    
    public boolean findShipment(String id) {
        String sqlQ = "SELECT ShipID FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + id.trim() + ";";
        boolean success;
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            success = stmt.execute(sqlQ);
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        
        System.out.println(success);
        return success;
    }
    
    public HashMap<String, String> getShipment(String id) {
        String sqlQ = "SELECT * FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + id.trim() + ";";
        HashMap<String, String> data = new HashMap<>();
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQ);
            
            while (rs.next()) {
                data.put("ShipID", rs.getString("ShipID"));
                data.put("ItemID", rs.getString("ItemID"));
                data.put("CustID", rs.getString("CustID"));
                data.put("Destination", rs.getString("Destination"));
                data.put("Location", rs.getString("Location"));
                data.put("Weight", rs.getString("Weight"));
                data.put("NumItems", rs.getString("NumItems"));
                data.put("TrackingNum", rs.getString("TrackingNum"));
                data.put("Carrier", rs.getString("Carrier"));
                data.put("Signer", rs.getString("Signer"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("An exception has been reached");
        }
        
        return data;
    }
    
    
    public boolean updateShipment(HashMap<String, String> updates, String id) {
        Set<String> keys = updates.keySet();
        boolean success = false;
        
        String sqlQ = "UPDATE gunnargo_cmsc495.Shipment SET "
                + "ItemID = '" + updates.get("ItemID") + "', "
                + "CustID = '" + updates.get("CustID") + "', "
                + "Destination = '" + updates.get("Destination") + "', "
                + "Location = '" + updates.get("Location") + "', "
                + "Weight = '" + updates.get("Weight") + "', "
                + "NumItems = '" + updates.get("NumItems") + "', "
                + "TrackingNum = '" + updates.get("TrackingNum") + "', "
                + "Carrier = '" + updates.get("Carrier") + "', "
                + "Signer = '" + updates.get("Signer") + "' "
                + "WHERE ShipID = " + id.trim() + ";";
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            success = stmt.execute(sqlQ);
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
        return success;
    }
    
    /**
     * @return the fields to be added for a new shipment
     */
    public ArrayList<String> getColumns() {
        columns.add("ItemID");
        columns.add("CustID");
        columns.add("Destination");
        // columns.add("Location");
        columns.add("Weight");
        columns.add("NumItems");
        
        return columns;
    }
    
    
    
    //  Establishing location, UID, password and sql command string
    String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    String userid = "gunnargo_umuc15";
    String password = "Ib7t5BRa74mTr0N9aS6";
    Connection con;
    
    public void showShipmentInfo()
    {
        this.setTitle("TAPRO-IM Shipment Table");
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        String sql = "SELECT * FROM gunnargo_cmsc495.Shipment";

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
