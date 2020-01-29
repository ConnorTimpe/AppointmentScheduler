/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

//import lib.*;
//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Connor Timpe
 */
public class Database {

    private static final String DATABASENAME = "U05Q8T";
    private static final String DATABASEURL = "jdbc:mysql://3.227.166.251/" + DATABASENAME;
    private static final String USERNAME = "U05Q8T";
    private static final String PASSWORD = "53688574512";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static Connection connection;

    public static void makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        connection = (Connection) DriverManager.getConnection(DATABASEURL, USERNAME, PASSWORD);
        System.out.println("Connection made");
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("Connection closed");
    }

    public static void deleteCustomer(int customerId) //String?
    {

    }

    public static void addCustomer(Customer customer) {

    }

    public static void addAppointment() //Appointment appointment
    {

    }

    public static void deleteAppointment(int appointmentId) {

    }
}
