/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taproim_cmsc495;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author kakenyon32
 */
public class DisplayTable extends JFrame {
    //  Establishing location, UID, password and sql command string
    private final String url = "jdbc:mysql://siteground324.com:3306/gunnargo_cmsc495";
    private final String userid = "gunnargo_umuc15";
    private final String password = "Ib7t5BRa74mTr0N9aS6";
    private final String table;
    
    public DisplayTable(String table) {
        this.table = table;
    }
    
    public void showTable() {
        this.setTitle("TAPRO-IM " + table + " Table");
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        String sql = "SELECT * FROM gunnargo_cmsc495." + table;

        // Try command to establish JDBC connection with above provided credentials
        try (Connection connection = DriverManager.getConnection(url, userid, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql )) 
        {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++) columnNames.add( md.getColumnName(i) );

            //  Get row data
            while (rs.next()){
                ArrayList row = new ArrayList(columns);
                for (int i = 1; i <= columns; i++) row.add( rs.getObject(i) );
                data.add( row );
            }
        }
        catch (SQLException e) { System.out.println( e.getMessage() ); }

        // Create Vectors and copy over elements from ArrayLists to them
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (Iterator it = data.iterator(); it.hasNext();) {
            Object data1 = it.next();
            ArrayList subArray = (ArrayList) data1;
            Vector subVector = new Vector();
            subArray.stream().forEach((subArray1) -> { subVector.add(subArray1); });
            dataVector.add(subVector);
        }

        for (Iterator it = columnNames.iterator(); it.hasNext();) {
            Object columnName = it.next();
            columnNamesVector.add(columnName);
        }

        //  Create table with database data    
        JTable displayTable = new JTable(dataVector, columnNamesVector)
        {
            @Override public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);
                    if (o != null) return o.getClass();
                }
                return Object.class;
            }
        };

        JScrollPane scrollPane = new JScrollPane(displayTable);
        getContentPane().add(scrollPane);
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
    }
}
