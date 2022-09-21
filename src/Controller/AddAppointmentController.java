/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.*;
import static DAO.DaoImpl.getFilteredCustomerAppointments;
import Model.Appointments;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Tomy Li He
 */
public class AddAppointmentController{
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField CustomerIDField;
    @FXML
    private TextField TitleField;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField UserIDField;
    @FXML
    private DatePicker StartDateField;
    @FXML
    private DatePicker EndDateField;
    @FXML
    private TextField StartHourField;
    @FXML
    private TextField StartMinuteField;
    @FXML
    private TextField EndHourField;
    @FXML
    private TextField EndMinuteField;
    @FXML
    private TextField LocationField;
    @FXML
    private TextField DescriptionField;
    @FXML
    private TextField ContactIDField;
    
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    /**
     *
     * @throws IOException
     */
    @FXML
    private void closeHandler(ActionEvent event) throws IOException{
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     * saves information from text fields and inserts into DB + appointments list
     * @throws IOException 
     * @throws SQLException
     */
    @FXML
    private void saveHandler(ActionEvent event) throws IOException, SQLException {
        String startHourStr = "";
        String startMinuteStr = "";
        String endHourStr = "";
        String endMinuteStr = "";
        int startHour = 0;
        int startMinute = 0;
        int endHour= 0;
        int endMinute = 0;
        int customerID = Integer.parseInt(CustomerIDField.getText());
        LocalTime startTime = null;
        LocalTime endTime = null;
        LocalDate appointmentDate = StartDateField.getValue();
        
        
        try{
            startHour = Integer.parseInt(StartHourField.getText());
            startMinute = Integer.parseInt(StartMinuteField.getText());
            endHour= Integer.parseInt(EndHourField.getText());
            endMinute = Integer.parseInt(EndMinuteField.getText());     
            
            if(startHour < 10) {
                startHourStr = "0" + Integer.toString(startHour);
            }
            else {
                startHourStr = StartHourField.getText();
            }
            if(startMinute < 10) {
                startMinuteStr = "0" + Integer.toString(startMinute);
            }
            else {
                startMinuteStr = StartMinuteField.getText();
            }

            if(endHour < 10) {
                endHourStr = "0" + Integer.toString(endHour);
            }
            else {
                endHourStr = EndHourField.getText();
            }
            if(endMinute < 10) {
                endMinuteStr = "0" + Integer.toString(endMinute);
            }
            else {
                endMinuteStr = EndMinuteField.getText();
            }    
        }catch(Exception e){
            System.out.println("Exception error: " + e.getMessage());
        }
             
        
        try{
              
            String userStartInput = startHourStr + ":" + startMinuteStr + ":00";
            String userEndInput = endHourStr + ":" + endMinuteStr + ":00";
            
            startTime = LocalTime.parse(userStartInput, timeFormat);
            endTime = LocalTime.parse(userEndInput, timeFormat);  
            
        }catch(Exception e){
            System.out.println("Exception error: " + e.getMessage());
        }
        
        if(CustomerIDField.getText().isEmpty() || TitleField.getText().isEmpty() || TypeField.getText().isEmpty() || UserIDField.getText().isEmpty() || StartHourField.getText().isEmpty() 
                                                                    || StartMinuteField.getText().isEmpty() || EndHourField.getText().isEmpty() || EndMinuteField.getText().isEmpty() 
                                                                    || StartDateField.getValue() == null || EndDateField.getValue() == null || LocationField.getText().isEmpty() || DescriptionField.getText().isEmpty() 
                                                                    || ContactIDField.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Missing Information Fields");
            alert.setHeaderText("Fill All Required Fields");
            Optional<ButtonType> showAndWait = alert.showAndWait();          
           
        }
                      
        if(startHour > 23 || startMinute > 59 || endHour > 23 || endMinute > 59){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Invalid Times entered");
            alert.setHeaderText("Please enter valid time values in the 24H format");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }
        // Check to see if appointment is within business hours
        String startDateTimeStr = StartDateField.getValue().toString() + " " + startHourStr + ":" + startMinuteStr + ":00.0";
        String endDateTimeStr = EndDateField.getValue().toString() + " " + endHourStr + ":" + endMinuteStr + ":00.0";
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, dateTimeFormat);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, dateTimeFormat);
        
        Boolean businessHourCheck = DaoImpl.validateWithinBusinessHours(startDateTime, endDateTime, appointmentDate);
        Boolean overlapCheck = checkOverlap(customerID, startDateTime, endDateTime, appointmentDate);
        
        if(!businessHourCheck){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Invalid Appointment Time");
            alert.setHeaderText("Appointment time is invalid or not within the business hours of 8AM to 10PM EST");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }
        else if(!overlapCheck){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Overlapping Appointment");
            alert.setHeaderText("Appointment time overlaps with another appointment.");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }
        
        else{
            //convert into string       
            String startDateStr = StartDateField.getValue().toString();
            String endDateStr = EndDateField.getValue().toString();
            
            
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            //Converts appointment time string into localdatetime var         
            LocalDate startDate = LocalDate.parse(startDateStr, dateFormat);
            LocalDate endDate = LocalDate.parse(endDateStr, dateFormat);
            
            //UTC time zone and current user time zone
            ZoneId userZone = ZoneId.systemDefault();           
            
            //Converts appointment time input into user's time zone
            ZonedDateTime userZoneStartTime = ZonedDateTime.of(startDateTime, userZone);
            ZonedDateTime userZoneEndTime = ZonedDateTime.of(endDateTime, userZone);
            
            //Convert appointment time input into UTC 
            ZonedDateTime utcStartTime = userZoneStartTime.withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime utcEndTime = userZoneEndTime.withZoneSameInstant(ZoneId.of("UTC"));
            
            //Convert back store in DB and appointment view
            
            startDateTime = utcStartTime.toLocalDateTime();
            endDateTime = utcEndTime.toLocalDateTime();
            
            
            Timestamp tsStart = Timestamp.valueOf(startDateTime);
            Timestamp tsEnd = Timestamp.valueOf(endDateTime);
                  
            
            try{
                if(userZoneStartTime.isAfter(userZoneEndTime)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initModality(Modality.NONE);
                    alert.setTitle("Invalid start and end times");
                    alert.setHeaderText("End time cannot be before start time");
                    alert.showAndWait();
                }else{
                    if(!startDate.isEqual(endDate)){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initModality(Modality.NONE);
                        alert.setTitle("Invalid start and end times");
                        alert.setHeaderText("End time cannot be before start time");
                        alert.showAndWait();
                    }else{
                        
                        Statement dbc_start = JDBC.getConnection().createStatement();
                        Statement dbc_end = JDBC.getConnection().createStatement();

                        //Start and end strings need the UTC datetime
                        String queryStartDate = "SELECT * FROM appointments WHERE Start BETWEEN '" + tsStart + "' AND '" + tsEnd + "'";
                        String queryEndDate = "SELECT * FROM appointments WHERE End BETWEEN '" + tsStart + "' AND '" + tsEnd + "'";

                        ResultSet rs_start = dbc_start.executeQuery(queryStartDate);
                        ResultSet rs_end = dbc_end.executeQuery(queryEndDate);                                            
                        
                        
                        if(rs_start.getRow() > 0 || rs_end.getRow() > 0) {
                            throw new ArithmeticException("Appointment exists during this time frame. Please choose another time frame for your appointment");
                        }else{
                            
                            int appointmentID = 1;
                            
                            Statement dbConnection = JDBC.getConnection().createStatement();
                            ResultSet rs = dbConnection.executeQuery("SELECT MAX(Appointment_ID) FROM appointments");
                                                 
                            if(rs.next()){
                                appointmentID = rs.getInt(1) + 1;                                                            
                            }
                            
                            
                            String insertQuery = "INSERT INTO appointments VALUES (" + appointmentID + ",'" + TitleField.getText() + "','" + DescriptionField.getText() + "','" + LocationField.getText() + "','" 
                                                                                    + TypeField.getText() + "','" + tsStart + "','" + tsEnd + "','2022-06-04 00:00:00','test','2022-06-04 00:00:00','test','" + CustomerIDField.getText()
                                                                                    + "','" + UserIDField.getText() + "'," + ContactIDField.getText() + ")";
                            
                            dbConnection.executeUpdate(insertQuery);
                            
                            Appointments newAppointment = new Appointments(appointmentID, TitleField.getText(), DescriptionField.getText(), LocationField.getText(), TypeField.getText(), userZoneStartTime, userZoneEndTime, Integer.parseInt(CustomerIDField.getText()),
                                                                            Integer.parseInt(UserIDField.getText()), Integer.parseInt(ContactIDField.getText()));
                            
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initModality(Modality.NONE);
                            alert.setTitle("Appointment Added");
                            alert.setHeaderText("Appointment successfully added");
                            alert.showAndWait();
                            
                            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene((Parent) scene));
                            stage.show();
                        }
                        
                    }
                }
            } catch(SQLException e){
                System.out.println("SQLException error: " + e.getMessage());
            }
        }
    }
    /**
     *
     * Checks for any overlapping appointments
     * @param startDateTime the inputted start time
     * @param inputCustomerID customer ID we are using to retrieve appointments
     * @param endDateTime the inputted end time
     * @param appointmentDate the date
     * @return true or false if there are any overlapping appointments
     * @throws SQLException if unable to access DB
     */
    public Boolean checkOverlap(Integer inputCustomerID, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate appointmentDate) throws SQLException{
        ObservableList<Appointments> conflictingAppointments = DaoImpl.getFilteredCustomerAppointments(appointmentDate, inputCustomerID, startDateTime, endDateTime);
        // Check to see if any of the appointments that are on the same date have start/end times that conflict with another appointment
        // Cannot start before another ends
        if(conflictingAppointments.isEmpty()){
            return true;
        }else{
            for(Appointments newAppt : conflictingAppointments){
                
                LocalDateTime overlapStart = newAppt.getStart().toLocalDateTime();
                LocalDateTime overlapEnd = newAppt.getEnd().toLocalDateTime();
                
                if(overlapStart.isBefore(startDateTime) & overlapEnd.isAfter(endDateTime)){
                    return false;
                }
                if(overlapStart.isBefore(endDateTime) & overlapStart.isAfter(startDateTime)){
                    return false;
                }
                if(overlapEnd.isBefore(endDateTime) & overlapEnd.isAfter(startDateTime)){
                    return false;
                }
                if(overlapStart.isEqual(startDateTime) & overlapEnd.isEqual(endDateTime)){
                    return false;
                }
                if(overlapStart.isEqual(startDateTime) & overlapEnd.isBefore(endDateTime) | overlapEnd.isAfter(endDateTime)){
                    return false;
                }
                if(overlapEnd.isEqual(endDateTime) & overlapStart.isBefore(startDateTime) | overlapStart.isAfter(startDateTime)){
                    return false;
                }
                else{
                    return true;
                }
            }
        }
        return true;
    }
}
