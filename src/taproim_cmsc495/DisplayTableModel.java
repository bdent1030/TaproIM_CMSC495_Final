/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taproim_cmsc495;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author kakenyon32
 */
public class DisplayTableModel extends AbstractTableModel {
    ArrayList columnNames = new ArrayList();
    ArrayList data = new ArrayList();

    //  Establishing location, UID, password and sql command string
    String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    String userid = "gunnargo_umuc15";
    String password = "Ib7t5BRa74mTr0N9aS6";
    String sql = "SELECT * FROM gunnargo_cmsc495.";
    
    public void SetData(String table) {
        String sqlAttempt = sql + table;
        try (Connection connection = DriverManager.getConnection( url, userid, password );
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlAttempt))
        {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++) columnNames.add(md.getColumnName(i));

            //  Get row data
            while (rs.next()){
                ArrayList row = new ArrayList(columns);
                for (int i = 1; i <= columns; i++) row.add(rs.getObject(i));
                data.add(row);
            }
        } catch (SQLException e) { 
            System.out.println(e.getMessage()); 
        }
        
        fireTableDataChanged();
    }
    
    @Override public int getRowCount() {
        return data.size();
    }

    @Override public int getColumnCount() {
        return columnNames.size();
    }
    
    @Override public String getColumnName(int index) {
        return columnNames.get(index).toString();
    }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Object row = data.get(rowIndex);
        for (int i = 0; i < getColumnCount(); i++) {
            if (row != null) return row.getClass();
        }
        return Object.class;
    }
}
