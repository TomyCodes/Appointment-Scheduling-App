/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.*;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;
/**
 *
 * @author Tomy Li He
 */
public class DaoImpl {
      
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
    
    /**
     *
     * @return a list of all the countries
     */
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
        
        try {
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT * FROM countries");
            
            while(rs.next()){
                Countries countryResult = new Countries(rs.getString("Country"), rs.getInt("Country_ID"));
                allCountries.add(countryResult);
            }
        } catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
        }
         return allCountries;
     }
    /**
     *
     * @param divisionID the id to get the selected country
     * @return selected country name
     */
    public static String getSelectedCountry(int divisionID){
        String selectedCountry = "";
        
        try {
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT countries.Country "
                    + "FROM countries, first_level_divisions "
                    + "WHERE first_level_divisions.Country_ID = countries.Country_ID "
                    + "AND first_level_divisions.Division_ID = \"" + divisionID + "\"");
            
            while(rs.next()){
                selectedCountry = rs.getString("Country");
            }
         }catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
        }        
        return selectedCountry;
    }   
    
    /**
     *
     * @return a list of all customers
     */
    public static ObservableList<Customers> getAllCustomers(){
         
        try {
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT * FROM customers");
            
            while(rs.next()){
                Customers customerResult = new Customers(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), 
                        rs.getTimestamp("Create_Date"), rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"), rs.getInt("Division_ID"));
                allCustomers.add(customerResult);
            }
         }catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
        }
         return allCustomers;
     }
     /**
     *
     * @param selectedCustomer the customer to delete
     */
     public static void deleteCustomer(Customers selectedCustomer){
         allCustomers.remove(selectedCustomer);
     }
     /**
     *
     * @param customerID the id to get the selected customer
     * @return the selected customer name
     */
     public static Customers getSelectedCustomer(int customerID) {
        Customers selectedCustomer = null;
        try {
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT * FROM customers WHERE Customer_ID = '" + customerID +"'");
            
            while(rs.next()){
                Customers customerResult = new Customers(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), 
                        rs.getTimestamp("Create_Date"), rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"), rs.getInt("Division_ID"));
                selectedCustomer = customerResult;
            }
         }catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
        }        
        return selectedCustomer;
     }
     /**
     *
     * @param divisionID the id to get the selected first division
     * @return the selected first division name
     */
     public static String getSelectedFirstDivision(int divisionID){
        String selectedFirstDiv = "";
        try {
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT * FROM first_level_divisions WHERE Division_ID = '" + divisionID +"'");
            
            while(rs.next()){
                selectedFirstDiv = rs.getString("Division_ID");
            }
         }catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
        }        
        return selectedFirstDiv; 
     }
    /**
     *
     * @return a list of all appointments
     */ 
    public static ObservableList<Appointments> getAllAppointments(){
        appointmentList.clear();
        try {
            
            Statement dbConnection = JDBC.getConnection().createStatement();

            ResultSet rs = dbConnection.executeQuery("SELECT * FROM appointments");
            
            
            while(rs.next()) {
                ZoneId newZoneId = ZoneId.systemDefault();
                
                Calendar utcTZ = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                Timestamp tsStart = rs.getTimestamp("Start", utcTZ);
                LocalDateTime utcStart = tsStart.toLocalDateTime();
                ZonedDateTime localStart = ZonedDateTime.of(utcStart, newZoneId);
                
                Timestamp tsEnd = rs.getTimestamp("End", utcTZ); 
                LocalDateTime utcEnd = tsEnd.toLocalDateTime();
                ZonedDateTime localEnd = ZonedDateTime.of(utcEnd, newZoneId);
                
                
                
                appointmentList.add(new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"), 
                                    rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), 
                                    localStart, localEnd, rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID")));       
            }

        } catch(SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
        return appointmentList;
     }
    
    /**
     *
     * @param selectedAppointment the appointment to delete
     */
    public static void deleteAppointment(Appointments selectedAppointment){
         appointmentList.remove(selectedAppointment);
     }
    /**
     *
     * @return a list of all contacts
     */ 
    public static ObservableList<Contacts> getAllContacts(){
         ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
         try {
            Statement dbConnection = JDBC.getConnection().createStatement();

            ResultSet rs = dbConnection.executeQuery("SELECT * FROM contacts");
            
            while(rs.next()) {
                allContacts.add(new Contacts(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), 
                                    rs.getString("Email")));       
            }

        } catch(SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
        return allContacts;
     }
    /**
     *
     * @param contactID the id to get the selected contact
     * @return the selected contact name
     */ 
    public static String getSelectedContact(int contactID){
        String selectedContact = "";
         
        try {
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT * FROM contacts WHERE Contact_ID = '" + contactID +"'");
            
            while(rs.next()){
                selectedContact = rs.getString("Contact_Name");
            }
        }catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
        }        
        return selectedContact;     
    }
    /**
     *
     * @param appointmentID the appointment ID to update
     * @param title the title to update
     * @param description the description to update
     * @param location the location to update
     * @param type the appointment type to update
     * @param start the start time to update
     * @param end the end time to update
     * @param customerID the customer id to update
     * @param userID the user id to update
     * @param contactID the contact id to update
     * to update an appointment based on matching appointment ID
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID){
        appointmentList.forEach((appointment) -> {
            if(appointment.getAppointmentID() == appointmentID){
                appointment.setTitle(title);
                appointment.setDescription(description);
                appointment.setLocation(location);
                appointment.setType(type);
                appointment.setStart(start);
                appointment.setEnd(end);
                appointment.setCustomerID(customerID);
                appointment.setUserID(userID);
                appointment.setContactID(contactID);
            }
        });
    }
    /**
     *
     * @return a list of appointments by type and month
     */
    public static ObservableList<String> getAppointmentsByMonthAndType(){
        ObservableList<String> reports = FXCollections.observableArrayList();
        
        reports.add("The Total Number of Appointments by month and type: \n");
        
        try {
            
            Statement dbConnection = JDBC.getConnection().createStatement();
          
            ResultSet rsType = dbConnection.executeQuery("SELECT Type, COUNT(Type) as \"Total\" FROM appointments GROUP BY Type");
            
            
            while(rsType.next()){
                String type = "Type: " + rsType.getString("Type") + " Count: " + rsType.getString("Total") + "\n";
                reports.add(type);
            }
            
            ResultSet rsMonth = dbConnection.executeQuery("SELECT MONTHNAME(Start) as \"Month\", COUNT(MONTH(Start)) as \"Total\" from appointments GROUP BY Month");
            
            while(rsMonth.next()){
                String month = "Month: " + rsMonth.getString("Month") + " Count: " + rsMonth.getString("Total") + "\n";
                reports.add(month);
            }
            
        }catch(SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
        return reports;
    }
    /**
     *
     * @return a list of all contact names
     */
    public static  ObservableList<String> getAllContactName(){
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        try{
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT DISTINCT Contact_Name "
                                                    + "FROM contacts;");

            while(rs.next() ) {
                allContactNames.add(rs.getString("Contact_Name"));
            }
        }catch(SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
        
        return allContactNames;
    }
    /**
     *
     * @param contactName the contact name to get matching contact ID
     * @return contact ID
     */
    public static Integer getContactID(String contactName){
        Integer contactID = -1;
        
        try{
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT Contact_ID, Contact_Name " +
                                                    "FROM contacts WHERE Contact_Name = '" + contactName +"'");
            
            while(rs.next()){
                contactID = rs.getInt("Contact_ID");
            }
               
        }catch(SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
        
        return contactID;
    }
    /**
     *
     * @param contactID the id to get a list of assigned appointments
     * @return list of appointments based on contact id
     */
    public static ObservableList<String> getContactAppointments(String contactID){
        ObservableList<String> appointments = FXCollections.observableArrayList();
        try{ 
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT * FROM appointments WHERE Contact_ID = '" + contactID +"'");
            
            while(rs.next()){
                String appointmentID = Integer.toString(rs.getInt("Appointment_ID"));
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                String customerID = Integer.toString(rs.getInt("Customer_ID"));
                
                String appointmentInfo = "Appointment ID: " + appointmentID + "\n";
                appointmentInfo += "Title: " + title + "\n";
                appointmentInfo += "Type: " + type + "\n";
                appointmentInfo += "Description: " + description + "\n";
                appointmentInfo += "Start date and time: " + start + "\n";
                appointmentInfo += "End date and time: " + end + "\n";
                appointmentInfo += "Customer ID: " + customerID + "\n";
                appointmentInfo += "\n";
                appointments.add(appointmentInfo);
                
            }
            
        }catch(SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
        return appointments;
    }
    /**
     *
     * @param contactID the id to get the total minutes of scheduled appointments
     * @return total minutes scheduled based on contact id
     */
    public static Integer getContactMinutes(String contactID){
        Integer totalMinutes = 0;
        
        try{
            
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery("SELECT * FROM appointments WHERE Contact_ID = '" + contactID +"'");
            
            while(rs.next()){
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                totalMinutes += (int)Duration.between(startDateTime, endDateTime).toMinutes();
            }
            
        }catch(SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
        return totalMinutes;
    }
    /**
     *
     * Business hours are 8AM to 10PM EST, including weekends
     * @param start user inputted start time
     * @param end user inputted end time
     * @param appointmentDate current date we are checking
     * @return true or false depending on if appointment is within business hours
     */
    public static Boolean validateWithinBusinessHours(LocalDateTime start, LocalDateTime end, LocalDate appointmentDate){
        ZonedDateTime startZDT = ZonedDateTime.of(start, ZoneId.systemDefault());
        ZonedDateTime endZDT = ZonedDateTime.of(end, ZoneId.systemDefault());
        
        ZonedDateTime startHours = ZonedDateTime.of(appointmentDate, LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime endHours = ZonedDateTime.of(appointmentDate, LocalTime.of(22,0), ZoneId.of("America/New_York"));
        
        if(startZDT.isBefore(startHours) || startZDT.isAfter(endHours) || 
           endZDT.isBefore(startHours) || endZDT.isAfter(endHours)){
            return false;
        }
        else{
            return true;
        }
    }
    /**
     *
     * Retrieves all appointments for a given customer using customer ID
     * @param appointmentDate the date we are checking
     * @param inputCustomerID customer ID we are using to retrieve appointments
     * @return List of appointments for the customer
     * @throws SQLException if unable to access DB
     */
    public static ObservableList<Appointments> getFilteredCustomerAppointments(LocalDate appointmentDate, Integer inputCustomerID, LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException{
        ObservableList<Appointments> customerAppointments = FXCollections.observableArrayList();

               
        PreparedStatement sqlQuery = JDBC.getConnection().prepareStatement("SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c " +
                                "ON a.Contact_ID = c.Contact_ID "
                              + "WHERE datediff(a.Start, ?) = 0 AND Customer_ID = ?;");
        
        sqlQuery.setInt(2, inputCustomerID);
        sqlQuery.setString(1, appointmentDate.toString());

        
        ResultSet rs = sqlQuery.executeQuery();
        
        while(rs.next()){
            Calendar utcTZ = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            
            Integer appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Integer customerID = rs.getInt("Customer_ID");
            Integer userID = rs.getInt("User_ID");
            Integer contactID = rs.getInt("Contact_ID");
            Timestamp tsStart = rs.getTimestamp("Start", utcTZ);
            Timestamp tsEnd = rs.getTimestamp("End", utcTZ);
            
            
            LocalDateTime utcStart = tsStart.toLocalDateTime();
            ZonedDateTime localStart = ZonedDateTime.of(utcStart, ZoneId.systemDefault());
            
            LocalDateTime utcEnd = tsEnd.toLocalDateTime();
            ZonedDateTime localEnd = ZonedDateTime.of(utcEnd, ZoneId.systemDefault());
            
            Appointments newAppointment = new Appointments(appointmentID, title, description, location, type, localStart, localEnd, customerID, userID, contactID);
            
            customerAppointments.add(newAppointment);
        }
        sqlQuery.close();
        return customerAppointments;
    }
      
     
}
