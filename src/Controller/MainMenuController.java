    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import DAO.*;
import Model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tomy Li He
 */
public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;
    
    @FXML
    private Button Exit;
    @FXML
    private Button AddCustomer;
    @FXML
    private Button AddAppointment;
    @FXML
    private Button CustomerViewButton;
    @FXML
    private Button DeleteAppointment;
    @FXML
    private RadioButton MonthRadio;
    @FXML
    private RadioButton ViewAllRadio;
    @FXML
    private RadioButton WeekRadio;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Appointments> AppointmentView;
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
    private TableColumn<Appointments, ZonedDateTime> StartCol;
    @FXML
    private TableColumn<Appointments, ZonedDateTime> EndCol;
    
    private ToggleGroup ScheduleView;

    /**
     * Initializes the controller class.
     */
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
        AppointmentView.setItems(DaoImpl.getAllAppointments());
        
        ScheduleView = new ToggleGroup();
        MonthRadio.setToggleGroup(ScheduleView);
        WeekRadio.setToggleGroup(ScheduleView);
        ViewAllRadio.setToggleGroup(ScheduleView);
               
    }
    /**
     *
     * @param event the event when clicking the log out button
     */
    @FXML
    public void exitHandler(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit?");
        alert.setContentText("Would you like to exit the application?");
        Optional<ButtonType> choice = alert.showAndWait();
        
        if(choice.get() == ButtonType.OK){
            System.exit(0);
        }
    }
    /**
     *
     * @throws IOException if unable to access FXML file
     * @param event the event when clicking the customer view button
     */
    @FXML
    public void customerHandler(ActionEvent event) throws IOException{
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }    
    /**
     *
     * @throws IOException if unable to access FXML file
     * @param event the event when clicking the add appointment button
     */
    @FXML
    public void addAppointmentHandler(ActionEvent event) throws IOException{
        AppointmentView.getItems().clear();
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }     
    /**
     *
     * @throws IOException if unable to access FXML file
     * @param event the event when clicking the update appointment button
     */
    @FXML
    public void updateAppointmentHandler(ActionEvent event) throws IOException{  
        AppointmentView.getItems().clear();
        scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
    /**
     * Deletes a selected appointment from the table and alerts the user to confirm deletion
     * @throws IOException if unable to access DAO file
     * @param event the event when clicking the delete appointment button
     */
    @FXML
    public void deleteAppointmentHandler(ActionEvent event) throws IOException{
        if(AppointmentView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("No selection");
            alert.setHeaderText("No appointment selected");
            alert.setContentText("Please select an appointment from the table");
            alert.showAndWait();
        }else{
            int selectedAppointmentID = AppointmentView.getSelectionModel().getSelectedItem().getAppointmentID();
            
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.initModality(Modality.NONE);
            deleteAlert.setTitle("Delete");
            deleteAlert.setHeaderText("Delete appointment?");
            deleteAlert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> selectOption = deleteAlert.showAndWait();
            
            if(selectOption.get() == ButtonType.OK){
                
                Appointments selectedAppointment = AppointmentView.getSelectionModel().getSelectedItem();
                
                DaoImpl.deleteAppointment(selectedAppointment);
                
                try{
                    Statement dbConnection = JDBC.getConnection().createStatement();
                    
                    dbConnection.executeUpdate("DELETE FROM appointments WHERE Appointment_ID = " + selectedAppointmentID + "");
                    
                }catch(SQLException e){
                    System.out.println("SQLException error: " + e.getMessage());
                }
                
                AppointmentView.getItems().clear();
                AppointmentView.setItems(DaoImpl.getAllAppointments());
                
            }
        }
    }
    /**
     * Filters appointment table view by month.
     * Lambda is used to efficiently filter out appointments by month 
     * @throws IOException if unable to access DAO file
     * @param event the event when clicking the view by month radio button
     */
    @FXML
    public void viewByMonth(ActionEvent event) throws IOException{
        LocalDate now = LocalDate.now();
        LocalDate nowPlusOneMonth = now.plusMonths(1);
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList(DaoImpl.getAllAppointments());
        
        FilteredList<Appointments> filteredAppointments = new FilteredList<>(appointmentList);
        filteredAppointments.setPredicate(row -> {
            LocalDate rowDate = row.getStart().toLocalDate();
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlusOneMonth);           
        });
        
        AppointmentView.setItems(filteredAppointments);
    }
    /**
     * Displays all appointments
     * @throws IOException if unable to access DAO file
     * @param event the event when clicking the view all radio button
     */
    @FXML
    private void viewAll(ActionEvent event) throws IOException{
        AppointmentView.setItems(DaoImpl.getAllAppointments());
    }
    
    /**
     * Filters appointment table view by week.
     * Lambda is used to efficiently filter out appointments by the week
     * @throws IOException if unable to access DAO file
     * @param event the event when clicking the view by week radio button
     */
    @FXML void viewByWeek(ActionEvent event) throws IOException{
        LocalDate now = LocalDate.now();
        LocalDate nowPlusOneWeek = now.plusWeeks(1);
        
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList(DaoImpl.getAllAppointments());
        FilteredList<Appointments> filteredAppointments = new FilteredList<>(appointmentList);
      
        filteredAppointments.setPredicate(row -> {
            LocalDate rowDate = row.getStart().toLocalDate();
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlusOneWeek);           
        });
        AppointmentView.setItems(filteredAppointments);
    }
    
    /**
     *
     * @throws IOException if unable to access FXML file
     * @param event the event when clicking the view reports button
     */
    @FXML void viewReportHandler(ActionEvent event) throws IOException {
        scene = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    

}   
    
    

