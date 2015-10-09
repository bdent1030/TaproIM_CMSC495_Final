package taproim_cmsc495.DAO;
import taproim_cmsc495.DTO.ShipmentDTO;
import java.sql.*;

/**
 * This class provides the SQL commands to transfer, update, and insert
 * data into the MySQL tables.
 * @author Dent, Gorder, Kenyon, Montoya, Ward
 */
public class ShipmentDAO {
    private Connection con;
    private final String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    private final String userid = "gunnargo_umuc15";
    private final String password = "Ib7t5BRa74mTr0N9aS6";
    
    /**
     * Attempts to add a new shipment to the table
     * @param dto - the shipment to add to the database
     * @return boolean value for whether the shipment was added
     */
    public boolean newShipment(ShipmentDTO dto) {
        boolean success;
                
        String sqlQ = "INSERT INTO gunnargo_cmsc495.Shipment SET "
                + "ItemID = '" + dto.getItemID() + "', "
                + "CustID = '" + dto.getCustID() + "', "
                + "Destination = '" + dto.getDestination() + "', "
                + "Weight = '" + dto.getWeight() + "', "
                + "NumItems = '" + dto.getNumItems() + "';";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlQ);
            ResultSet rs = stmt.executeQuery("SELECT * FROM gunnargo_cmsc495.Shipment");
            if (rs.last()) dto.setShipID(rs.getString("ShipID"));
            success = !dto.getShipID().equals("");
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        return success;
    }
    
    /**
     * @param id - the ShipmentDAO ID to be deleted from the table
     * @return boolean value for whether the command was successfully completed
     */
    public boolean deleteShipment(String id) {
        ShipmentDTO dto = new ShipmentDTO();
        dto.setShipID(id);
        
        String sqlQ = "DELETE FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + dto.getShipID() + ";";
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
     * @param id - the ShipmentDAO ID to be found
     * @return boolean value for whether or not the shipment was found
     */
    public boolean findShipment(String id) {
        ShipmentDTO dto = new ShipmentDTO();
        dto.setShipID(id);
        
        String sqlQ = "SELECT ShipID FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + dto.getShipID() + ";";
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
     * Updates the various fields for the provided ShipmentDAO ID
     * @param id - the ShipmentDAO to be populated
     * @return the ShipmentDTO
     */
    public ShipmentDTO getShipment(String id) {
        ShipmentDTO dto = new ShipmentDTO();
        String sqlQ = "SELECT * FROM gunnargo_cmsc495.Shipment WHERE ShipID = " + id.trim() + ";";
        //HashMap<String, String> data = new HashMap<>();
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQ);
            
            while (rs.next()) {
                try { dto.setShipID(rs.getString("ShipID")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setItemID(rs.getString("ItemID")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setCustID(rs.getString("CustID")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setDestination(rs.getString("Destination")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setLocation(rs.getString("Location")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setWeight(rs.getString("Weight")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setNumItems(rs.getString("NumItems")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setTrackingNum(rs.getString("TrackingNum")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setCarrier(rs.getString("Carrier")); } catch (Exception ex) { /* do nothing */ }
                try { dto.setSigner(rs.getString("Signer")); } catch (Exception ex) { /* do nothing */ }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("An exception has been reached");
        }
        return dto;
    }
    
    /**
     * Updates the currently displayed shipment of the ID searched
     * @param dto - the ShipmentDAO to update
     * @return boolean value of success for update
     */
    public boolean updateShipment(ShipmentDTO dto) {
        boolean success;
        
        String sqlQ = "UPDATE gunnargo_cmsc495.Shipment SET "
                + "ItemID = '" + dto.getItemID() + "', "
                + "CustID = '" + dto.getCustID() + "', "
                + "Destination = '" + dto.getDestination() + "', "
                + "Location = '" + dto.getLocation() + "', "
                + "Weight = '" + dto.getWeight() + "', "
                + "NumItems = '" + dto.getNumItems() + "', "
                + "TrackingNum = '" + dto.getTrackingNum() + "', "
                + "Carrier = '" + dto.getCarrier() + "', "
                + "Signer = '" + dto.getSigner() + "' "
                + "WHERE ShipID = " + dto.getShipID() + ";";
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlQ);
            con.close();
            success = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        
        return success;
    }
    
    /**
     * @param dto - the shipment to check
     * @return boolean value of whether the stock level is high enough
     */
    public boolean checkStockLevel(ShipmentDTO dto){
        
        if(dto.getItemID().equals("")){
            System.out.println("No item id entered to check stock level");
            return false;
        }
        
        if(dto.getNumItems().equals("")){
            System.out.println("No number of items entered to evaluate stock level");
            return false;
        }
        
        //Check inventory level
        String sqlGetInventory = "SELECT StockLevel "
                + "FROM gunnargo_cmsc495.Inventory "
                + "WHERE ItemID = '" + dto.getItemID()+"';";
        
        String invLevel = "";
        
        try {
                con = DriverManager.getConnection(url, userid, password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlGetInventory);
                if(rs.next()){
                    invLevel = rs.getString("StockLevel");
                    if(invLevel.equals("")){
                        System.out.println("No inventory");
                        return false;
                     }
                }
                con.close();
            } catch (SQLException ex) {
                System.out.println("Current inventory was not retrieved");
                System.out.println(ex);
            }
        
        if((Integer.parseInt(invLevel) - Integer.parseInt(dto.getNumItems()) < 0)){
            System.out.println("Not enough inventory for this order");
            return false;
        }
        
        return true;
    }
}
