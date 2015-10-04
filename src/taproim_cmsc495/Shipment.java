package taproim_cmsc495;
import java.sql.*;

/**
 * 
 * @author Dent, Gorder, Kenyon, Montoya, Ward
 * This pop-out simply displays SQL table information for Shipment
 */
public class Shipment {
    private Connection con;
    private final String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    private final String userid = "gunnargo_umuc15";
    private final String password = "Ib7t5BRa74mTr0N9aS6";
    
    private String shipID, itemID, custID, destination, location, 
            weight, numItems, trackingNum, carrier, signer;
    
    // Table Field SETTER Methods @param String value to be set
    public void setShipID(String shipID)            { this.shipID = cleanInput(shipID);           }
    public void setItemID(String itemID)            { this.itemID = cleanInput(itemID);           }
    public void setCustID(String custID)            { this.custID = cleanInput(custID);           }
    public void setDestination(String destination)  { this.destination = cleanInput(destination); }
    public void setLocation(String location)        { this.location = cleanInput(location);       }
    public void setWeight(String weight)            { this.weight = cleanInput(weight);           }
    public void setNumItems(String numItems)        { this.numItems = cleanInput(numItems);       }
    public void setTrackingNum(String trackingNum)  { this.trackingNum = cleanInput(trackingNum); }
    public void setCarrier(String carrier)          { this.carrier = cleanInput(carrier);         }
    public void setSigner(String signer)            { this.signer = cleanInput(signer);           }
    
    // Table field GETTER methods @return requested field
    public String getShipID()                       { return shipID;        }
    public String getItemID()                       { return itemID;        }
    public String getCustID()                       { return custID;        }
    public String getDestination()                  { return destination;   }
    public String getLocation()                     { return location;      }
    public String getWeight()                       { return weight;        }
    public String getNumItems()                     { return numItems;      }
    public String getTrackingNum()                  { return trackingNum;   }
    public String getCarrier()                      { return carrier;       }
    public String getSigner()                       { return signer;        }
    
    /**
     * @param text - the text to be "cleaned"
     * @return the "cleaned" text
     */
    private String cleanInput(String input) {
        String output = "";
        char[] characters = {
            '@', ':', ';', '/', '\\', '\'', '?', '!', 
            '#', '$', '%', '^', '&',  '*',  '(', ')'
        };
        char[] cleaning = input.toCharArray();
        
        for (int i = 0; i < cleaning.length; i++) 
            for (char sc : characters) 
                if (cleaning[i] == sc) cleaning[i] = ' ';
        for (int i = 0; i < cleaning.length; i ++) 
            if (cleaning[i] != ' ') output += cleaning[i];
        
        return output;
    }
    
    /**
     * Attempts to add a new shipment to the table
     * @return boolean value for whether the shipment was added
     */
    public boolean newShipment() {
        String sqlQ = "INSERT INTO gunnargo_cmsc495.Shipment SET "
                + "ItemID = '" + getItemID() + "', "
                + "Destination = '" + getDestination() + "', "
                + "Weight = '" + getWeight() + "', "
                + "NumItems = '" + getNumItems() + "';";
        boolean success;
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlQ);
            ResultSet rs = stmt.executeQuery("SELECT * FROM gunnargo_cmsc495.Shipment");
            if (rs.last()) setShipID(rs.getString("ShipID"));
            success = !getShipID().equals("");
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        return success;
    }
    
    /**
     * @param id - the Shipment ID to be deleted from the table
     * @return boolean value for whether the command was successfully completed
     */
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
    
    /**
     * Searches for a shipment ID in the SQL table for Shipments
     * @param id - the Shipment ID to be found
     * @return boolean value for whether or not the shipment was found
     */
    public boolean findShipment(String id) {
        String sqlQ = "SELECT ShipID FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + id.trim() + ";";
        boolean success;
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQ);
            if (rs.next()) success = !rs.getString("ShipID").equals((""));
            else success = false;
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        return success;
    }
    
    /**
     * Updates the various fields for the provided Shipment ID
     * @param id - the Shipment ID to be populated
     */
    public void getShipment(String id) {
        String sqlQ = "SELECT * FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + id.trim() + ";";
        //HashMap<String, String> data = new HashMap<>();
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQ);
            
            while (rs.next()) {
                try { setShipID(rs.getString("ShipID")); } catch (Exception ex) { /* do nothing */ }
                try { setItemID(rs.getString("ItemID")); } catch (Exception ex) { /* do nothing */ }
                try { setCustID(rs.getString("CustID")); } catch (Exception ex) { /* do nothing */ }
                try { setDestination(rs.getString("Destination")); } catch (Exception ex) { /* do nothing */ }
                try { setLocation(rs.getString("Location")); } catch (Exception ex) { /* do nothing */ }
                try { setWeight(rs.getString("Weight")); } catch (Exception ex) { /* do nothing */ }
                try { setNumItems(rs.getString("NumItems")); } catch (Exception ex) { /* do nothing */ }
                try { setTrackingNum(rs.getString("TrackingNum")); } catch (Exception ex) { /* do nothing */ }
                try { setCarrier(rs.getString("Carrier")); } catch (Exception ex) { /* do nothing */ }
                try { setSigner(rs.getString("Signer")); } catch (Exception ex) { /* do nothing */ }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("An exception has been reached");
        }
    }
    
    /**
     * Updates the currently displayed shipment of the ID searched
     * @return boolean value of success for update
     */
    public boolean updateShipment() {
        boolean success;
        
        String sqlQ = "UPDATE gunnargo_cmsc495.Shipment SET "
                + "ItemID = '" + getItemID() + "', "
                + "CustID = '" + getCustID() + "', "
                + "Destination = '" + getDestination() + "', "
                + "Location = '" + getLocation() + "', "
                + "Weight = '" + getWeight() + "', "
                + "NumItems = '" + getNumItems() + "', "
                + "TrackingNum = '" + getTrackingNum() + "', "
                + "Carrier = '" + getCarrier() + "', "
                + "Signer = '" + getSigner() + "' "
                + "WHERE ShipID = " + getShipID() + ";";
        
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
}
