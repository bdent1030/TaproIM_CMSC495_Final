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

//Gunar Gorder, Brandon Dent, Frank Montoya, Kent Kenyon, Spencer Ward
//TAMPROW IM FINAL PROJECT
//CMSC 495 6380
//9-27-15
//Customer.java
public class Customer{
    //  Establishing location, UID, password and sql command string
    String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    String userid = "gunnargo_umuc15";
    String password = "Ib7t5BRa74mTr0N9aS6";
    
    String custID;
    String custName;
    String custAddress;
    String custEmail;
    
    Connection con;
    
    public Customer(){
        
        
        
    }
    
    public boolean addCustomerRecord(ArrayList<String> info){
        String name = info.get(0);
        String address = info.get(1);
        String email = info.get(2);
        
        //if(custExists(custID)){
            String sqlCreate = "INSERT INTO gunnargo_cmsc495.Customer SET "
                    + " Name = '" + name
                    + "', Address = '" + address
                    + "', Email = '" + email + "';";
        
            try {
                con = DriverManager.getConnection(url, userid, password);
                Statement stmt = con.createStatement();
                stmt.execute(sqlCreate); 
                ResultSet rs = stmt.executeQuery("SELECT * FROM gunnargo_cmsc495.Customer WHERE Email = '" + email + "';"); 
                if (rs.next()) custID = rs.getString("CustID");
                return custExists(custID);
            } catch (SQLException ex) {
                System.out.println("Customer was not added");
                System.out.println(ex);
                return false; // creation failed
            }
       // }
        
    
    
//        this.custName = name;
//        this.custAddress = address;
//        this.custEmail = email;
        
    }
    
    public HashMap retrieveCust(String custID){
        HashMap<String, String> results = new HashMap<String, String>();
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Customer WHERE CustID = " + custID + ";";
        
        try {
                con = DriverManager.getConnection(url, userid, password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlSelect);
                if(rs.next()){
                    int id = rs.getInt("CustID");
                    String name = rs.getString("Name");
                    String address = rs.getString("Address");
                    String email = rs.getString("Email");
                    results.put("CustID", id +"");
                    results.put("Name", name);
                    results.put("Address", address);
                    results.put("Email", email);
                }
                con.close();
            } catch (SQLException ex) {
                System.out.println("Customer was not retrieved");
                System.out.println(ex);
            }
        
        
        return results;
    }
    
    public boolean updateCustomer(ArrayList<String> info){
        
        String id = info.get(0);
        String name = info.get(1);
        String address = info.get(2);
        String email = info.get(3);
        
        
        
        
            String sqlCreate = "UPDATE gunnargo_cmsc495.Customer SET "
                    + "Name = '" + name
                    + "', Address = '" + address
                    + "', Email = '" + email
                    + "' WHERE CustID = " + id.trim() + ";";
        
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
        

        String sqlSelect = "DELETE FROM gunnargo_cmsc495.Customer WHERE CustID =" + custID.trim() + ";";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            stmt.execute(sqlSelect);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Customer ID: " + custID +" not found");
            return false; // find failed
        }
        
        
        
    }
    
    public boolean custExists(String id){
        boolean result;
        
        String sqlSelect = "SELECT CustID FROM gunnargo_cmsc495.Customer WHERE CustID = " + id.trim() + ";";

        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            result = stmt.execute(sqlSelect);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Customer ID: " + id +" not found");
            return false; // find failed
        }
        
        return result;
    }
    
    public boolean custExistsEmail(String email) {
        String sqlSelect = "SELECT * FROM gunnargo_cmsc495.Customer WHERE Email = '" + email + "';";
        String id = "";
        
        try {
            con = DriverManager.getConnection(url, userid, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            if (rs.next()){
                id = rs.getString("CustID");
            }
            con.close();
            return !id.equals("");
        } catch (SQLException ex) {
            System.out.println("Customer Email: " + email +" not found");
            return false; // find failed
        }
    }
  
   
}
