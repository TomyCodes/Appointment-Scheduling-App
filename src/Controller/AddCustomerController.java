/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.*;
import DAO.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Tomy Li He
 */
public class AddCustomerController implements Initializable{
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField IdField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField PostalField;
    @FXML
    private TextField AddressField;
    @FXML
    private TextField PhoneField;
    @FXML
    private ComboBox<String> CountryCombo;
    @FXML
    private ComboBox<String> FirstDivisionCombo;
    @FXML
    private Button CloseButton;
    @FXML 
    private Button SaveButton;
            
    ObservableList<Countries> countries = FXCollections.observableArrayList();
    ObservableList<String> countryNames = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivisions> firstDivisions = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populates the Country combobox with all the countries in the database
        try{
            countries.addAll(DaoImpl.getAllCountries());   
            
            for(Countries country : countries){
                String countryName = country.getCountry();
                countryNames.add(countryName);
            }        
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CountryCombo.setItems(countryNames);
                        
    }
    
    @FXML
    private void initializeCity(){
        String selectedCountry = CountryCombo.getValue();
        String query = "SELECT first_level_divisions.Division "
                    + "FROM countries, first_level_divisions "
                    + "WHERE first_level_divisions.Country_ID = countries.Country_ID "
                    + " AND countries.Country = \"" + selectedCountry + "\"";
        try{
            Statement dbConnection = JDBC.getConnection().createStatement();
            ResultSet rs = dbConnection.executeQuery(query);
            FirstDivisionCombo.getItems().clear();
            
            while(rs.next()){
                
                FirstDivisionCombo.getItems().add(rs.getString(1));
            }
        } catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
        }
        
    }
    /**
     *
     * @throws IOException
     */
    @FXML
    private void closeHandler(ActionEvent event) throws IOException{
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     * Creates and adds a new customer into the DB + customer list
     * @throws IOException
     */
    @FXML
    private void saveHandler(ActionEvent event) throws IOException{
        int autoID = 0;
        int divisionID = 0;
        
        if(NameField.getText().isEmpty() || PostalField.getText().isEmpty() || AddressField.getText().isEmpty() || PhoneField.getText().isEmpty() || CountryCombo.getValue().isEmpty() || FirstDivisionCombo.getValue().isEmpty()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Missing Information Fields");
            alert.setHeaderText("Fill All Required Fields");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else {
            // get last customer ID and add one to it 
            try {
   
                Statement dbConnection = JDBC.getConnection().createStatement();
                ResultSet rs = dbConnection.executeQuery("SELECT MAX(Customer_ID) FROM customers");
                
                if(rs.next()){
                    autoID = rs.getInt(1) + 1;
                }   
                ResultSet rs2 = dbConnection.executeQuery("SELECT Division_ID FROM first_level_divisions "
                                                        + "WHERE Division = \"" + FirstDivisionCombo.getValue() + "\"");
                
                if(rs2.next()) {
                    divisionID = rs2.getInt("Division_ID");
                }
                
                String insertCustomer = "INSERT INTO customers VALUES(" + autoID + ",'" + NameField.getText() + "','" + AddressField.getText() + "','" + PostalField.getText() + "','" + PhoneField.getText() + "','2022-05-28 00:00:00','test','2022-05-28 00:00:00','test'" + "," + divisionID + ")";
                dbConnection.executeUpdate(insertCustomer);
                
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Customer added");
                alert.setHeaderText("Customer successfully added");
                alert.showAndWait();
                          
                scene = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene((Parent) scene));
                stage.show();
                        
                
            } catch(SQLException e){
            System.out.println("SQLException error: " + e.getMessage());
            }
                        
        }
    } 
    
}
