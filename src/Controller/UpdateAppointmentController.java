/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DaoImpl;
import static DAO.DaoImpl.getFilteredCustomerAppointments;
import DAO.JDBC;
import Model.*;
import java.sql.Timestamp;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author Tomy Li He
 */
public class UpdateAppointmentController implements Initializable{
    Stage stage;
    Parent scene;
    @FXML
    private TableView<Appointments> AppointmentView2;
    @FXML
    private TableColumn<Appointments, Integer> ApptIDCol;
    @FXML
    private TableColumn<Appointments, Integer> CustIDCol;
    @FXML
    private TableColumn<Appointments, Integer> UserIDCol;
    @FXML
    private TableColumn<Appointments, String> TitleCol;
    @FXML
    private TableColumn<Appointments, String> DescriptionCol;
    @FXML
    private TableColumn<Appointments, String> LocationCol;
    @FXML
    private TableColumn<Contacts, String> ContactCol;
    @FXML
    private TableColumn<Appointments, String> TypeCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> StartCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> EndCol;
    @FXML
    private TextField AppointmentIDField;
    @FXML
    private TextField CustomerIDField;
    @FXML
    private TextField UserIDField;
    @FXML
    private TextField ContactIDField;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField LocationField;
    @FXML
    private TextField StartHourField;
    @FXML
    private TextField StartMinuteField;
    @FXML
    private TextField EndHourField;
    @FXML
    private TextField EndMinuteField;
    @FXML
    private TextField TitleField;
    @FXML
    private TextField DescriptionField;
    @FXML 
    private DatePicker StartDateField;
    @FXML 
    private DatePicker EndDateField;
    @FXML
    private ComboBox<String> ContactCombo;
    
    ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    ObservableList<String> contactNames = FXCollections.observableArrayList();
    
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        
        ApptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        CustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        UserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        LocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        StartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        EndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        
        AppointmentView2.setItems(DaoImpl.getAllAppointments());
        
        try{           
            contactList.addAll(DaoImpl.getAllContacts());   
            
            for(Contacts contact : contactList){
                String contactName = contact.getContactName();
                contactNames.add(contactName);
            }        
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ContactCombo.setItems(contactNames);
        
        /**
        * Action Listener to set the text fields with the selected appointment information
        * 
        */
        AppointmentView2.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null){
                //gets time values from selected appointment               
                ZonedDateTime startTS = newVal.getStart();
                LocalDate localStartDate = startTS.toLocalDateTime().toLocalDate();
                              
                ZonedDateTime endTS = newVal.getEnd();
                LocalDate localEndDate = endTS.toLocalDateTime().toLocalDate();              
                
                String startHourStr = startTS.toString().substring(11,13);
                String startMinuteStr = startTS.toString().substring(14,16);
                String endHourStr = endTS.toString().substring(11,13);
                String endMinuteStr = endTS.toString().substring(14,16);
                
                
                AppointmentIDField.setText(Integer.toString(newVal.getAppointmentID()));
                StartDateField.setValue(localStartDate);
                StartHourField.setText(startHourStr);
                StartMinuteField.setText(startMinuteStr);              
                EndDateField.setValue(localEndDate);
                EndHourField.setText(endHourStr);
                EndMinuteField.setText(endMinuteStr);
                CustomerIDField.setText(Integer.toString(newVal.getCustomerID()));
                UserIDField.setText(Integer.toString(newVal.getUserID()));
                ContactIDField.setText(Integer.toString(newVal.getContactID()));
                TypeField.setText(newVal.getType());
                LocationField.setText(newVal.getLocation());
                TitleField.setText(newVal.getTitle());
                DescriptionField.setText(newVal.getDescription());
                ContactCombo.setValue(DaoImpl.getSelectedContact(newVal.getContactID()));                           
            }
        });
    }
    /**
     *
     * @throws IOException
     */
    @FXML
    private void cancelHandler(ActionEvent event) throws IOException{
        AppointmentView2.getItems().clear();
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();  
    }
    /**
     * Saves the updated text fields and updates the DB + appointments list
     * @throws IOException
     */
    @FXML
    private void saveHandler(ActionEvent event) throws IOException, SQLException{
        int startHour = 0;
        int startMinute = 0;
        int endHour= 0;
        int endMinute = 0;   
        int customerID = Integer.parseInt(CustomerIDField.getText());
        String startHourStr = "";
        String startMinuteStr = "";
        String endHourStr = "";
        String endMinuteStr = "";
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = LocalTime.now();
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
        
        //Checks to see if input is within validf 24H format
        if(startHour > 23 || startMinute > 59 || endHour > 23 || endMinute > 59){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Invalid Time");
            alert.setHeaderText("Please enter a valid time");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }
        //Check to see if appointment is within business hours
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
            alert.setHeaderText("Appointment is invalid or not within the business hours of 8AM to 10PM EST");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }
        if(!overlapCheck){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Overlapping Appointment");
            alert.setHeaderText("Appointment time overlaps with another appointment.");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }
        
        else{
            
            String startDateStr = StartDateField.getValue().toString();
            String endDateStr = EndDateField.getValue().toString();
            
            
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            //Converts appointment time string into localdatetime var           
            LocalDate startDate = LocalDate.parse(startDateStr, dateFormat);
            LocalDate endDate = LocalDate.parse(endDateStr, dateFormat);
            
            //UTC time zone and current user time zone
            ZoneId userZone = ZoneId.systemDefault();
            ZoneId utcZone = ZoneId.of("UTC");
            
            //Converts appointment time input into user's time zone
            ZonedDateTime userZoneStartTime = startDateTime.atZone(userZone);
            ZonedDateTime userZoneEndTime = endDateTime.atZone(userZone);
            
            //Convert appointment time input into UTC 
            ZonedDateTime utcStartTime = userZoneStartTime.withZoneSameInstant(utcZone);
            ZonedDateTime utcEndTime = userZoneEndTime.withZoneSameInstant(utcZone);
            
            //Convert back to store in DB
            startDateTime = utcStartTime.toLocalDateTime();
            endDateTime = utcEndTime.toLocalDateTime();
            
            Timestamp tsStart = Timestamp.valueOf(startDateTime);
            Timestamp tsEnd = Timestamp.valueOf(endDateTime);
            
            
            try{
                if(userZoneStartTime.isAfter(userZoneEndTime)){
                    throw new ArithmeticException("Appointment start time cannot be after end time");
                }else{
                    if(!startDate.isEqual(endDate)){
                        throw new ArithmeticException("Appointment exceeds business hours on the same day");
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
                            boolean check = false;
                            try{
                                Statement dbConnection = JDBC.getConnection().createStatement();
                            
                            
                                String updateQuery = "UPDATE appointments SET Title='" + TitleField.getText() + "', Description='" + DescriptionField.getText() + "', Location='" + LocationField.getText() + "', Type='" + TypeField.getText() +
                                                    "', Start='" + tsStart + "', End='" + tsEnd + "', Customer_ID=" + Integer.parseInt(CustomerIDField.getText()) + ", User_ID=" + 
                                                    Integer.parseInt(UserIDField.getText()) + ", Contact_ID=" + Integer.parseInt(ContactIDField.getText()) + " WHERE Appointment_ID =" + 
                                                    AppointmentView2.getSelectionModel().getSelectedItem().getAppointmentID() + "";
                            
                                dbConnection.execute(updateQuery);
                                
                                check = true;
                            } catch(SQLException e){
                                System.out.println("SQLException error: " + e.getMessage());
                            }
                            
                            DaoImpl.updateAppointment(Integer.parseInt(AppointmentIDField.getText()), TitleField.getText(), DescriptionField.getText(), LocationField.getText(), 
                                                       TypeField.getText(), userZoneStartTime, userZoneEndTime, Integer.parseInt(CustomerIDField.getText()), Integer.parseInt(UserIDField.getText()), Integer.parseInt(ContactIDField.getText()));
                            if(check){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initModality(Modality.NONE);
                                alert.setTitle("Appointment Updated");
                                alert.setHeaderText("Appointment successfully updated");
                                alert.showAndWait();
                            }
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
     * @param appointmentDate the date of the appointment
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
        
            
