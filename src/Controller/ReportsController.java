/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import DAO.*;
import javafx.collections.ObservableList;
/**
 *
 * @author Tomy Li He
 */
public class ReportsController implements Initializable{
    Stage stage;
    Parent scene;
    
    @FXML
    private Button minsPerContactButton;
    @FXML
    private Button ContactScheduleButton;
    @FXML
    private Button AppointmentReportButton;
    @FXML
    private TextArea ReportsTextArea;
    @FXML
    private Button BackButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    /**
     * Sets text area with appointments by month and type
     * @throws IOException
     * @param event Button click
     */
    @FXML
    private void setAppointments(ActionEvent event) throws IOException{
        ReportsTextArea.clear();
        ObservableList<String> reports = DaoImpl.getAppointmentsByMonthAndType();
        
        for(String string : reports){
            ReportsTextArea.appendText(string);
        }
    }
    /**
     * Sets text area with every contact and their appointment schedules
     * @throws IOException
     * @param event Button click
     */
    @FXML
    private void setContactSchedule(ActionEvent event) throws IOException{  
        ReportsTextArea.clear();
        ObservableList<String> contactReports = DaoImpl.getAllContactName();
        
        for(String contact : contactReports){
            String contactID = DaoImpl.getContactID(contact).toString();
            ReportsTextArea.appendText("Contact Name: " + contact + "\n" + "Contact ID: " + contactID + "\n");
            
            ObservableList<String> appointments = DaoImpl.getContactAppointments(contactID);
            if(appointments.isEmpty()){
                ReportsTextArea.appendText("No appointments for contact \n");
                ReportsTextArea.appendText("\n");
            }else{
                for(String appointment: appointments){
                    ReportsTextArea.appendText(appointment);
                }
            }
            
            
        }
    }
    /**
     * Sets the text area with every contacts total appointment minutes scheduled
     * @throws IOException
     * @param event Button click
     */
    @FXML
    private void getMinutesScheduled(ActionEvent event) throws IOException{
        ReportsTextArea.clear();
        ObservableList<String> contacts = DaoImpl.getAllContactName();
        
        for(String contact: contacts){
            String contactID = DaoImpl.getContactID(contact).toString();
            ReportsTextArea.appendText("Contact: " + contact + "\n");
            ReportsTextArea.appendText("Contact ID: " + contactID + "\n");
            ReportsTextArea.appendText("Total minutes scheduled: " + DaoImpl.getContactMinutes(contactID) + "\n");
            ReportsTextArea.appendText("\n");
        }
    }
    /**
     *
     * @throws IOException
     * @param event Button click
     */
    @FXML
    private void backHandler(ActionEvent event) throws IOException{
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
}
