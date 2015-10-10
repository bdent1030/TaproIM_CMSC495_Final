package taproim_cmsc495.DAO;

import taproim_cmsc495.DTO.CustomerDTO;
import java.sql.*;

//Gunar Gorder, Brandon Dent, Frank Montoya, Kent Kenyon, Spencer Ward
//TAMPROW IM FINAL PROJECT
//CMSC 495 6380
//9-27-15
//Customer.java
public class CustomerDAO{
    //  Establishing location, UID, password and sql command string
    private final String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    private final String userid = "gunnargo_umuc15";
    private final String password = "Ib7t5BRa74mTr0N9aS6";
    private Connection con;
    
    public CustomerDAO(){}
    
    public boolean addCustomerRecord(CustomerDTO dto){
        String sqlCreate = "INSERT INTO gunnargo_cmsc495.Customer SET "
                + " Name = '" + dto.getCustName()
                + "', Address = '" + dto.getCustAddress()
                + "', Email = '" + dto.getCustEmail() + "';";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlCreate); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM gunnargo_cmsc495.Customer WHERE Email = '" + dto.getCustEmail() + "';"); 
            if (rs.next()) dto.setCustID(rs.getString("CustID"));
            return custExists(dto.getCustID());
        } catch (SQLException ex) {
            System.out.println("Customer was not added");
            System.out.println(ex);
            return false; // creation failed
        }
    }
    
    public CustomerDTO retrieveCust(String custID){
        CustomerDTO dto = new CustomerDTO();
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Customer WHERE CustID = " + custID + ";";
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            if(rs.next()){
                dto.setCustID(rs.getString("CustID"));
                dto.setCustName(rs.getString("Name"));
                dto.setCustAddress(rs.getString("Address"));
                dto.setCustEmail(rs.getString("Email"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Customer was not retrieved");
            System.out.println(ex);
        }
        
        return dto;
    }
    
    public String getCustIdFromEmail(String email){
        CustomerDTO dto = new CustomerDTO();
        dto.setCustEmail(email);
        String sqlSelect = "SELECT CustID FROM gunnargo_cmsc495.Customer WHERE EMail = '" + dto.getCustEmail() + "';";
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            if(rs.next()){
                dto.setCustID(rs.getString("CustID"));
                if(dto.getCustID().equals("")){
                    System.out.println("No such customer email ("+ email +")");
                    return null;
                 }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Customer ID was not retrieved");
            System.out.println(ex);
        }
        
        return dto.getCustID();
    }
    
    public boolean updateCustomer(CustomerDTO dto){
        String sqlCreate = "UPDATE gunnargo_cmsc495.Customer SET "
                + "Name = '" + dto.getCustName()
                + "', Address = '" + dto.getCustAddress()
                + "', Email = '" + dto.getCustEmail()
                + "' WHERE CustID = " + dto.getCustID() + ";";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlCreate);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Customer was not updated");
            System.out.println(ex);
            return false; // creation failed
        }
    }
    
    public boolean deleteCustomer(String custID){          
        CustomerDTO dto = new CustomerDTO();
        dto.setCustID(custID);
        
        String sqlSelect = "DELETE FROM gunnargo_cmsc495.Customer WHERE CustID =" + dto.getCustID() + ";";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlSelect);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Connot delete Customer ID: " + custID );
            System.out.println(ex.toString());
            return false; // find failed
        }
    }
    
    public boolean custExists(String id){
        boolean result;
        CustomerDTO dto = new CustomerDTO();
        dto.setCustID(id);
        String sqlSelect = "SELECT CustID FROM gunnargo_cmsc495.Customer WHERE CustID = " + dto.getCustID() + ";";
        String strResult = "";
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            //result = stmt.execute(sqlSelect);
            //con.close();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            if (rs.next()){
                strResult = rs.getString("CustID");
            }
            con.close();
            return !strResult.equals("");
        } catch (SQLException ex) {
            System.out.println("Customer ID: " + id +" not found");
            return false; // find failed
        }
        
        //return result;
    }
    
    public boolean custExistsEmail(String email) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustEmail(email);
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Customer WHERE Email = '" + dto.getCustEmail() + "';";
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            if (rs.next()){
                dto.setCustID(rs.getString("CustID"));
            }
            con.close();
            return !dto.getCustID().equals("");
        } catch (SQLException ex) {
            System.out.println("Customer Email: " + email +" not found");
            return false; // find failed
        }
    }
}
