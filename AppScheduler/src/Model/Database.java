/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public static void makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        connection = (Connection) DriverManager.getConnection(DATABASEURL, USERNAME, PASSWORD);
        System.out.println("Connection made");
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("Connection closed");
    }

    public static ObservableList<Customer> buildCustomerList() throws SQLException {
        customerList.clear();

        Statement nameStatement = connection.createStatement();
        Statement addressStatement = connection.createStatement();
        Statement cityStatement = connection.createStatement();
        Statement countryStatement = connection.createStatement();

        String sqlStmtGetCustomerTableData = "SELECT customerName, customerId, active FROM customer";
        String sqlStmtGetCustomerAddressTableData = "SELECT addressId, address, address2, postalCode, phone  FROM address";
        String sqlStmtGetCustomerCityTableData = "SELECT cityId, city FROM city";
        String sqlStmtGetCustomerCountryTableData = "SELECT countryId, country FROM country";

        ResultSet rsCustomer = nameStatement.executeQuery(sqlStmtGetCustomerTableData);
        ResultSet rsAddress = addressStatement.executeQuery(sqlStmtGetCustomerAddressTableData);
        ResultSet rsCity = cityStatement.executeQuery(sqlStmtGetCustomerCityTableData);
        ResultSet rsCountry = countryStatement.executeQuery(sqlStmtGetCustomerCountryTableData);

        while (rsCustomer.next() && rsAddress.next() && rsCity.next() && rsCountry.next()) {
            Customer newCustomer = new Customer();

            newCustomer.setName(rsCustomer.getString("customerName"));
            newCustomer.setId(rsCustomer.getInt("customerId"));
            newCustomer.setActive(rsCustomer.getInt("active"));

            newCustomer.setAddressId(rsAddress.getInt("addressId"));
            newCustomer.setAddress(rsAddress.getString("address"));
            newCustomer.setAddress2(rsAddress.getString("address2"));
            newCustomer.setPostalCode(rsAddress.getString("postalCode"));
            newCustomer.setPhoneNumber(rsAddress.getString("phone"));

            newCustomer.setCityId(rsCity.getInt("cityId"));
            newCustomer.setCity(rsCity.getString("city"));

            newCustomer.setCountryId(rsCountry.getInt("countryId"));
            newCustomer.setCountry(rsCountry.getString("country"));

            customerList.add(newCustomer);
        }

        return customerList;
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        int customerId = customer.getId();
        int addressId = customer.getAddressId();
        int cityId = customer.getCityId();
        int countryId = customer.getCountryId();

        Statement statement = connection.createStatement();

        //Delete customer record
        String sqlStmtDelete = "DELETE FROM customer WHERE customerId = " + customerId + ";";
        statement.executeUpdate(sqlStmtDelete);

        //Delete address record
        sqlStmtDelete = "DELETE FROM address WHERE addressId = " + addressId + ";";
        statement.executeUpdate(sqlStmtDelete);

        //Delete city record
        sqlStmtDelete = "DELETE FROM city WHERE cityId = " + cityId + ";";
        statement.executeUpdate(sqlStmtDelete);

        //Delete country record
        sqlStmtDelete = "DELETE FROM country WHERE countryId = " + countryId + ";";
        statement.executeUpdate(sqlStmtDelete);
    }

    public static void addCustomer(Customer customer) throws SQLException {

        Statement statement = connection.createStatement();

        LocalDateTime now = LocalDateTime.now();

        String name = customer.getName();
        String address = customer.getAddress();
        String address2 = customer.getAddress2();
        String phoneNumber = customer.getPhoneNumber();
        String city = customer.getCity();
        String country = customer.getCountry();
        String postalCode = customer.getPostalCode();
        int active = customer.getActive();

        String currentUser = "Test user";
        String lastUpdateBy = "Test user";

        addToCountryTable(statement, country, now, currentUser, lastUpdateBy);
        int countryId = getCountryId(statement, country);

        addToCityTable(statement, city, countryId, now, currentUser, lastUpdateBy);
        int cityId = getCityId(statement, city);

        addToAddressTable(statement, address, address2, cityId, postalCode, phoneNumber, now, currentUser, lastUpdateBy);
        int addressId = getAddressId(statement, address);

        addToCustomerTable(statement, name, addressId, active, now, currentUser, lastUpdateBy);

    }

    private static void addToCountryTable(Statement statement, String country, LocalDateTime now,
            String currentUser, String lastUpdateBy) throws SQLException {
        String sqlStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy)"
                + "VALUES("
                + "'" + country + "', "
                + "'" + now + "', "
                + "'" + currentUser + "', "
                + "'" + lastUpdateBy + "')";
        statement.executeUpdate(sqlStatement);
    }

    private static int getCountryId(Statement statement, String country) throws SQLException {
        int countryId = -1;

        String sqlGetCountryId = "SELECT countryId FROM country WHERE country = '" + country + "';";
        ResultSet rs = statement.executeQuery(sqlGetCountryId);

        if (rs.next()) {
            countryId = rs.getInt("countryId");
        }
        return countryId;
    }

    private static void addToCityTable(Statement statement, String city, int countryId, LocalDateTime now,
            String currentUser, String lastUpdateBy) throws SQLException {
        String sqlStatement = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdateBy)"
                + "VALUES("
                + "'" + city + "', "
                + "'" + countryId + "', "
                + "'" + now + "', "
                + "'" + currentUser + "', "
                + "'" + lastUpdateBy + "')";
        statement.executeUpdate(sqlStatement);
    }

    private static int getCityId(Statement statement, String city) throws SQLException {
        int cityId = -1;

        String sqlGetCityId = "SELECT cityId FROM city WHERE city = '" + city + "';";
        ResultSet rs = statement.executeQuery(sqlGetCityId);

        if (rs.next()) {
            cityId = rs.getInt("cityId");
        }

        return cityId;
    }

    private static void addToAddressTable(Statement statement, String address, String address2, int cityId,
            String postalCode, String phoneNumber, LocalDateTime now, String currentUser, String lastUpdateBy) throws SQLException {
        String sqlStatement = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)"
                + "VALUES("
                + "'" + address + "', "
                + "'" + address2 + "', "
                + "'" + cityId + "', "
                + "'" + postalCode + "', "
                + "'" + phoneNumber + "', "
                + "'" + now + "', "
                + "'" + currentUser + "', "
                + "'" + lastUpdateBy + "')";
        statement.executeUpdate(sqlStatement);
    }

    private static int getAddressId(Statement statement, String address) throws SQLException {
        int addressId = -1;

        String sqlGetAddressId = "SELECT addressId FROM address WHERE address = '" + address + "';";
        ResultSet rs = statement.executeQuery(sqlGetAddressId);

        if (rs.next()) {
            addressId = rs.getInt("addressId");
        }
        return addressId;
    }

    private static void addToCustomerTable(Statement statement, String name, int addressId, int active,
            LocalDateTime now, String currentUser, String lastUpdateBy) throws SQLException {
        String sqlStatement = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy)"
                + "VALUES("
                + "'" + name + "', "
                + "'" + addressId + "', "
                + "'" + active + "', "
                + "'" + now + "', "
                + "'" + currentUser + "', "
                + "'" + lastUpdateBy + "')";
        statement.executeUpdate(sqlStatement);
    }

    static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public static ObservableList<Appointment> buildAppointmentList() throws SQLException {
        appointmentList.clear();

        Statement statement = connection.createStatement();
        String sqlStmtApp = "SELECT * FROM appointment";
        ResultSet rs = statement.executeQuery(sqlStmtApp);

        while (rs.next()) {
            Appointment newApp = new Appointment();

            newApp.setAppointmentId(rs.getInt("appointmentId"));
            newApp.setCustomerId(rs.getInt("customerId"));
            newApp.setUserId(rs.getInt("userId"));
            newApp.setTitle(rs.getString("title"));
            newApp.setDescription(rs.getString("description"));
            newApp.setLocation(rs.getString("location"));
            newApp.setContact(rs.getString("contact"));
            newApp.setType(rs.getString("type"));
            newApp.setUrl(rs.getString("url"));

            Instant startInstant = rs.getTimestamp("start").toInstant();
            Instant endInstant = rs.getTimestamp("end").toInstant();

            LocalDateTime startTime = LocalDateTime.ofInstant(startInstant, ZoneOffset.UTC);
            LocalDateTime endTime = LocalDateTime.ofInstant(endInstant, ZoneOffset.UTC);

            DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm MM/dd/yyyy");
            String formattedStart = startTime.format(formater);

            newApp.setStartTime(startTime);
            newApp.setEndTime(endTime);
            newApp.setFormattedStart(formattedStart);
        }

        return appointmentList;
    }

    public static void addAppointment(Appointment appointment) throws SQLException {
        int customerId = appointment.getCustomerId();
        int userId = appointment.getUserId();
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String contact = appointment.getContact();
        String type = appointment.getType();
        String url = Integer.toString(customerId);
        LocalDateTime createDate = LocalDateTime.now();
        String createdBy = "Test user";
        LocalDateTime startTime = appointment.getStartTime();
        LocalDateTime endTime = appointment.getEndTime();
        String lastUpdateBy = "Test user";

        Statement statement = connection.createStatement();

        //Add into Appointment table
        String sqlAppStmt = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, "
                + "start, end, createDate, createdBy, lastUpdateBy) "
                + "VALUES("
                + "'" + customerId + "', "
                + "'" + userId + "', "
                + "'" + title + "', "
                + "'" + description + "', "
                + "'" + location + "', "
                + "'" + contact + "', "
                + "'" + type + "', "
                + "'" + url + "', "
                + "'" + startTime + "', "
                + "'" + endTime + "', "
                + "'" + createDate + "', "
                + "'" + createdBy + "', "
                + "'" + lastUpdateBy + "')";

        statement.executeUpdate(sqlAppStmt);
    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        Statement statement = connection.createStatement();
        int appointmentId = appointment.getAppointmentId();
        String sqlStmtDelete = "DELETE FROM appointment WHERE appointmentId = " + appointmentId + ";";
        statement.executeUpdate(sqlStmtDelete);
    }
}
