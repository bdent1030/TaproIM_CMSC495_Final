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
 * @author Dent, Gorder, Kenyon, Montoya, Ward
 * This pop-out simply displays SQL table information for Shipment
 */
public class Shipment extends JFrame {
    SQLTransferManager sqlMgr = new SQLTransferManager();
    ArrayList<String> columns = new ArrayList<>();
    
    
    
    public boolean newShipment(ArrayList<String> info) {
        return sqlMgr.insertRow("Shipment", getColumns(), info);
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
    
    /*
    public Shipment()
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
    }*/
    
}
