/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taproim_cmsc495;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class will manage the SQL transfer items
 * @author Dent, Gorder, Kenyon, Montoya, Ward
 */
public class SQLTransferManager {
    // Variable declarations
    String url;
    String userid;
    String password;
    Connection con;
    
    /**
     * The class constructor. Initiates the url, userid & password values
     */
    public SQLTransferManager() {
        url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
        userid = "gunnargo_umuc15";
        password = "Ib7t5BRa74mTr0N9aS6";
    }
    
    /**
     * Updates a specific database item in an existing SQL table
     * @param dbName the database name
     * @param keyName the key name (column ID)
     * @param keyValue the key value (table data)
     * @param column the column containing the new data
     * @param newValue the new value to be updated
     * @return whether or not the update was successful
     */
    public boolean setItemOnTable(String dbName, String keyName, String keyValue, 
            String column, String newValue) {
        String sqlQ = "UPDATE " + dbName + 
                " SET " + column + " = '" + newValue + "'" +
                " WHERE " + keyName + " = '" + keyValue + "'";
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
     * will insert a new row into the designated SQL table
     * @param dbName the database name
     * @param columns the names of the columns
     * @param values the values to be added in the row
     * @return whether or not the insertion was successful
     */
    public boolean insertRow(String dbName,
        ArrayList<String> columns, ArrayList<String> values) {
        String sqlQ = "INSERT INTO " + dbName + "(";
        sqlQ = columns.stream().map((name) -> name + ",").reduce(sqlQ, String::concat);
        sqlQ += ") VALUES (";
        sqlQ = values.stream().map((name) -> "'" + name + "',").reduce(sqlQ, String::concat);
        sqlQ += ")";
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
     * will delete the input row from the designated SQL table
     * @param keyName the key name (column ID)
     * @param keyValue the key value (table data)
     * @param dbName the database name
     * @return whether or not the deletion was successful
     */
    public boolean deleteRow(String keyName, String keyValue, String dbName) {
        String sqlQ = "DELETE FROM " + dbName + " WHERE " + keyName + " = '" + keyValue + "'";
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
}
