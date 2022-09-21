/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tomy Li He
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    // LOCAL
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; 
    // Driver reference
    private static final String driver = "com.mysql.cj.jdbc.Driver"; 
    // Username
    private static final String userName = "sqlUser"; 
    // Password
    private static String password = "Passw0rd!"; 
    // Connection Interface
    public static Connection connection;  
    
    // Opens DB connection
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
    // Closes DB connection
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /**
     *
     * @return returns the connection
     */
    public static Connection getConnection() {
        return connection;
    }
    
}
