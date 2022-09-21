/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import DAO.*;
import Model.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


/**
 * FXML Controller class
 *
 * @author Tomy Li He
 */
public class LoginScreenController {
    @FXML
    public Button LoginButton;
    @FXML
    private TextField UsernameField;
    @FXML
    private TextField PasswordField;
    @FXML
    private Label Username;
    @FXML
    private Label Password;
    @FXML
    private Label Error;
    @FXML
    private Label Title;
    @FXML
    private Button ExitButton;
    @FXML
    private Label ZoneLabel;
    
    Stage stage;
    Parent scene;
    ObservableList<Appointments> upcomingAppointments = FXCollections.observableArrayList();   
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        Error.setVisible(false);
        try{
            ResourceBundle rb = ResourceBundle.getBundle("Languages/login", Locale.getDefault());
            Username.setText(rb.getString("username"));
            Password.setText(rb.getString("password"));
            LoginButton.setText(rb.getString("login"));
            Error.setText(rb.getString("error"));
            Title.setText(rb.getString("title"));
            ExitButton.setText(rb.getString("exit"));
        }catch (MissingResourceException e) {
            System.out.println("Missing resource");
        }
        
        ZoneId userZone = ZoneId.systemDefault();
        String userZoneStr = String.valueOf(userZone);
        
        ZoneLabel.setText("*" + userZoneStr);
        
        
        
    }
    /**
     * Log in handler
     * @throws IOException if unable to access FXML file
     * @throws SQLException if unable to connect to database
     * @param event the event when clicking log in button
     */
    @FXML
    public void loginHandler(ActionEvent event) throws IOException, SQLException {
        int userID = getUserID(UsernameField.getText());
        Users user = new Users();
        boolean attemptCheck;
        if (isValidPassword(userID, PasswordField.getText())) {
            attemptCheck = true;
            user.setUserID(userID);
            user.setUserName(UsernameField.getText());
            
            userLogs(user.getUserName(), attemptCheck);
            
            ObservableList<Appointments> upcomingAppointments = getAppointmentsWithin15Mins();
            
            if(!upcomingAppointments.isEmpty()){
                for(Appointments appt: upcomingAppointments){
                    String msg = "Upcoming appointment ID: " + appt.getAppointmentID() + "\n" + "Start time: " + appt.getStart() + "\n" + "End time: " + appt.getEnd();
                    ButtonType okayButton = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.WARNING, msg, okayButton);
                    alert.setHeaderText("Upcoming appointments");
                    alert.showAndWait();
                }
            }else{
                ButtonType okayButton = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming Appointments", okayButton);
                alert.setHeaderText("No appointments");
                alert.showAndWait();
            }
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Error.setVisible(true);
            attemptCheck = false;
            
            userLogs(user.getUserName(), attemptCheck);
        }
    }

 
    /**
     * Checks to see if the password matches
     * @param userID the user ID to check against the DB information
     * @param password the password to check against the DB information
     * @throws SQLException
     */
    private boolean isValidPassword(int userID, String password) throws SQLException{
        Statement dbConnection = JDBC.getConnection().createStatement();
        
        ResultSet rs = dbConnection.executeQuery("SELECT Password FROM users WHERE User_ID ='" + userID +"'");
        
        while(rs.next()){
            if(rs.getString("Password").equals(password)){
                return true;
            }
        }
        return false;
    }
    /**
     * Logs the users attempt to login into a different file
     * @param user the username to log
     * @param loginAttempt the log in attempt 
     */
    public void userLogs(String user, boolean loginAttempt){
        if(loginAttempt){
            try{
                String fileName = "login_activity";
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

                ZoneId utcZoneId = ZoneId.of("UTC");
                LocalDateTime localDateTime = LocalDateTime.now(utcZoneId);
                Timestamp timestamp = Timestamp.valueOf(localDateTime);

                writer.append(timestamp + " " + user + " " + "successful login " + "\n");
                writer.flush();
                writer.close();
                      
            }catch(IOException e){
                System.out.println(e);
            }
        }else{
            try{
                String fileName = "login_activity";
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

                ZoneId utcZoneId = ZoneId.of("UTC");
                LocalDateTime localDateTime = LocalDateTime.now(utcZoneId);
                Timestamp timestamp = Timestamp.valueOf(localDateTime);

                writer.append(timestamp + " " + user + " " + "failed login attempt " + "\n");
                writer.flush();
                writer.close();
                      
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }
    
    /**
     * Gets the user ID given the username
     * @throws SQLException
     * @param username the username to get the user ID with
     */
    private int getUserID(String username) throws SQLException{
        int userID = -1;
        
        Statement dbConnection = JDBC.getConnection().createStatement();
        
        ResultSet rs = dbConnection.executeQuery("SELECT User_ID from users WHERE User_Name ='" + username + "'");
        
        while(rs.next()){
            userID = rs.getInt("User_ID");
        }
        return userID;
    }
    /**
     * Retrieves all the appointments 15 minutes within the user's login
     * @throws SQLException
     * @return returns all the appointments 15 minutes within the user's login
     */
    private ObservableList<Appointments> getAppointmentsWithin15Mins() throws SQLException{   
        
        
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime userTimeZone = now.atZone(ZoneId.systemDefault());
        ZonedDateTime nowUTC = userTimeZone.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime utcPlus15 = nowUTC.plusMinutes(15);
        
        String startStr = nowUTC.format(formatter).toString();
        String endStr = utcPlus15.format(formatter).toString();
        
        Integer userID = getUserID(UsernameField.getText());
        
        PreparedStatement dbConnection = JDBC.getConnection().prepareStatement("SELECT * FROM appointments as a " +
                                                                                "LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID WHERE " +
                                                                                "Start BETWEEN ? AND ? AND User_ID = ? "); 
        dbConnection.setString(1, startStr);
        dbConnection.setString(2, endStr);
        dbConnection.setInt(3, userID);
        ResultSet rs = dbConnection.executeQuery();
        
        while(rs.next()){
            Calendar utcTZ = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            
            ZoneId newZoneId = ZoneId.systemDefault();
                
            Timestamp tsStart = rs.getTimestamp("Start", utcTZ);
            LocalDateTime utcStart = tsStart.toLocalDateTime();
            ZonedDateTime localStart = ZonedDateTime.of(utcStart, newZoneId);
                
            Timestamp tsEnd = rs.getTimestamp("End", utcTZ); 
            LocalDateTime utcEnd = tsEnd.toLocalDateTime();
            ZonedDateTime localEnd = ZonedDateTime.of(utcEnd, newZoneId);
            
            
            allAppointments.add(new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"), 
                                    rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), 
                                    localStart, localEnd, rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID")));
        }
        
        return allAppointments;
        
   }
    
}