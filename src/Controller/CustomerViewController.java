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
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Tomy Li He
 */
public class CustomerViewController implements Initializable{
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField IDField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField PostalField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField AddressField;
    @FXML
    private TableView<Customers> CustomerTable;
    @FXML
    private TableColumn<Customers, Integer> CustomerIdCol;
    @FXML
    private TableColumn<Customers, String> CustomerNameCol;
    @FXML
    private TableColumn<Customers, String> CustomerAddressCol;
    @FXML
    private TableColumn<Customers, Integer> DivisionIDCol;
    @FXML
    private TableColumn<Customers, String> PostalCol;
    @FXML
    private TableColumn<Customers, String> CustomerPhoneCol; 
    @FXML
    private ComboBox<String> CountryCombo;
    @FXML
    private ComboBox<String> FirstDivisionCombo;
    
    ObservableList<Countries> countries = FXCollections.observableArrayList();
    ObservableList<String> countryNames = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivisions> firstDivisions = FXCollections.observableArrayList();
    /**
     * @param url the URL
     * @param rb the RB
     * Lambda is used to set the text fields based on the customer selected.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerIdCol.setCellValueFactory(cellData -> cellData.getValue().CustomerIDProperty().asObject());
        CustomerNameCol.setCellValueFactory(cellData -> cellData.getValue().CustomerNameProperty());
        CustomerAddressCol.setCellValueFactory(cellData -> cellData.getValue().AddressProperty());
        DivisionIDCol.setCellValueFactory(cellData -> cellData.getValue().DivisionIDProperty().asObject());
        PostalCol.setCellValueFactory(cellData -> cellData.getValue().PostalCodeProperty());
        CustomerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().PhoneProperty());
        CustomerTable.getItems().clear();
        CustomerTable.setItems(DaoImpl.getAllCustomers());
        
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
        
        
        CustomerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null){
                IDField.setText(Integer.toString(newVal.getCustomerID()));
                NameField.setText(newVal.getCustomerName());
                PostalField.setText(newVal.getPostalCode());
                PhoneField.setText(newVal.getPhone());
                AddressField.setText(newVal.getAddress()); 
                CountryCombo.setValue(DaoImpl.getSelectedCountry(newVal.getDivisionID()));
                FirstDivisionCombo.setValue(DaoImpl.getSelectedFirstDivision(newVal.getDivisionID()));
            }
        });
    }
    /**
     *
     * @throws IOException
     */
    @FXML
    private void addCustomerHandler(ActionEvent event)throws IOException{
        CustomerTable.getItems().clear();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     *
     * @throws IOException
     */
    @FXML
    private void backHandler(ActionEvent event)throws IOException{
        CustomerTable.getItems().clear();
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     * Updates the customer only when a customer is selected from the table
     * @throws IOException
     */
    @FXML
    private void updateCustomerHandler(ActionEvent event) throws IOException{
        if(CustomerTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("No selection");
            alert.setHeaderText("No customer selected");
            alert.setContentText("Please select a customer from the table");
            alert.showAndWait();
        }else {
            if(NameField.getText().isEmpty() || PostalField.getText().isEmpty() || AddressField.getText().isEmpty() || PhoneField.getText().isEmpty() || CountryCombo.getValue().isEmpty() || FirstDivisionCombo.getValue().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Missing Information Fields");
                alert.setHeaderText("Fill All Required Fields");
                Optional<ButtonType> showAndWait = alert.showAndWait();
            }else{
                Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);
                updateAlert.initModality(Modality.NONE);
                updateAlert.setTitle("Update");
                updateAlert.setHeaderText("Update customer?");
                updateAlert.setContentText("Are you sure you want to update this customer?");
                Optional<ButtonType> selectOption = updateAlert.showAndWait();
                
                if(selectOption.get() == ButtonType.OK){
                                        
                    try{
                 
                        Statement dbConnection = JDBC.getConnection().createStatement();
                        
                        String updateCustomer = "UPDATE customers SET Customer_NAME = '" + NameField.getText() + "', Address = '" + AddressField.getText() + 
                                "', Postal_Code = '" + PostalField.getText() + "', Phone = '" + PhoneField.getText() + "'";
                        
                        dbConnection.executeUpdate(updateCustomer);
                        
                    }catch(SQLException e){
                        System.out.println("SQLException error: " + e.getMessage());
                    }
                }
            }
            CustomerTable.getItems().clear();  
            CustomerTable.setItems(DaoImpl.getAllCustomers());
        }       
        
    }
    /**
     * Deletes a customer only when a customer is selected from the table
     * @throws IOException
     */
    @FXML
    private void deleteCustomerHandler(ActionEvent event) throws IOException{
        if(CustomerTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("No selection");
            alert.setHeaderText("No customer selected");
            alert.setContentText("Please select a customer from the table");
            alert.showAndWait();
        }else{
            int selectedCustomerID = CustomerTable.getSelectionModel().getSelectedItem().getCustomerID();
            
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.initModality(Modality.NONE);
            deleteAlert.setTitle("Delete");
            deleteAlert.setHeaderText("Delete customer?");
            deleteAlert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> selectOption = deleteAlert.showAndWait();
            
            if(selectOption.get() == ButtonType.OK){
                //Get selected customer
                Customers selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
                //Delete selected customer 
                DaoImpl.deleteCustomer(selectedCustomer);
                              
                try{
                    Statement dbConnection = JDBC.getConnection().createStatement();

                    dbConnection.executeUpdate("DELETE FROM appointments WHERE Customer_ID = " + selectedCustomerID + "");
                    dbConnection.executeUpdate("DELETE FROM customers WHERE Customer_ID = " + selectedCustomerID + "");
                }catch(SQLException e){
                    System.out.println("SQLException error: " + e.getMessage());
                }
                //Update the table 
                CustomerTable.getItems().clear();
                CustomerTable.setItems(DaoImpl.getAllCustomers());
            }
        }   
        
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

    
}
