package taproim_cmsc495;
import java.sql.*;
import java.util.*;

/**
 * 
 * @author Dent, Gorder, Kenyon, Montoya, Ward
 * This pop-out simply displays SQL table information for Shipment
 */
public class Shipment {
    // SQLTransferManager sqlMgr = new SQLTransferManager();
    // ArrayList<String> columns = new ArrayList<>();
    private Connection con;
    private final String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    private final String userid = "gunnargo_umuc15";
    private final String password = "Ib7t5BRa74mTr0N9aS6";
    
    private String shipID, itemID, custID, destination, location, 
            weight, numItems, trackingNum, carrier, signer;
    
    
    
    // Table Field SETTER Methods 
    // @param String value to be set
    public void setShipID(String shipID)            { this.shipID = shipID.trim();           }
    public void setItemID(String itemID)            { this.itemID = itemID.trim();           }
    public void setCustID(String custID)            { this.custID = custID.trim();           }
    public void setDestination(String destination)  { this.destination = destination.trim(); }
    public void setLocation(String location)        { this.location = location.trim();       }
    public void setWeight(String weight)            { this.weight = weight.trim();           }
    public void setNumItems(String numItems)        { this.numItems = numItems.trim();       }
    public void setTrackingNum(String trackingNum)  { this.trackingNum = trackingNum.trim(); }
    public void setCarrier(String carrier)          { this.carrier = carrier.trim();         }
    public void setSigner(String signer)            { this.signer = signer.trim();           }
    
    // Table field GETTER methods 
    // @return requested field
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
    
    public void getShipment(String id) {
        String sqlQ = "SELECT * FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + id.trim() + ";";
        //HashMap<String, String> data = new HashMap<>();
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQ);
            
            while (rs.next()) {
                setShipID(rs.getString("ShipID"));
                setShipID(rs.getString("ShipID"));
                setItemID(rs.getString("ItemID"));
                setCustID(rs.getString("CustID"));
                setDestination(rs.getString("Destination"));
                setLocation(rs.getString("Location"));
                setWeight(rs.getString("Weight"));
                setNumItems(rs.getString("NumItems"));
                setTrackingNum(rs.getString("TrackingNum"));
                setCarrier(rs.getString("Carrier"));
                setSigner(rs.getString("Signer"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("An exception has been reached");
        }
        
        //return data;
    }
    
    
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
