/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
/**
 *
 * @author 14155
 */
public class Customers {

    private final IntegerProperty customerID;
    private final StringProperty customerName;
    StringProperty address = new SimpleStringProperty();
    StringProperty postalCode = new SimpleStringProperty();
    StringProperty phone = new SimpleStringProperty();
    private final IntegerProperty divisionID;
    Timestamp lastUpdated;
    Timestamp createDate;
    StringProperty createdBy = new SimpleStringProperty();
    StringProperty lastUpdatedBy = new SimpleStringProperty();    
    
    /**
     * Constructor
     * @param customerID the customer ID
     * @param customerName the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phone the customer phone number as a string
     * @param lastUpdated when the customer information was last updated
     * @param createdBy the individual who created the customer data
     * @param createDate date and time when customer data was created
     * @param lastUpdatedBy the individual who last updated the customer data
     * @param divisionID the division id
     */
    public Customers(int customerID, String customerName, String address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionID){
        this.customerID = new SimpleIntegerProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.divisionID = new SimpleIntegerProperty(divisionID);
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdated = lastUpdated;
        this.createDate = createDate;   
    }
    /**
     *
     * @return the customer id as an integer property
     */
    public IntegerProperty CustomerIDProperty() {
        return customerID;
    }
    /**
     *
     * @return the customer ID
     */
    public int getCustomerID() {
        return customerID.get();
    }
    /**
     *
     * @param customerID the customer id to set
     */
    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }
    
    public StringProperty CustomerNameProperty() {
        return customerName;
    }
    /**
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName.get();
    }
    /**
     *
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }
    /**
     *
     * @return the address as a string property
     */
    public StringProperty AddressProperty() {
        return address;
    }
    /**
     *
     * @return the address
     */
    public String getAddress() {
        return address.get();
    }
    /**
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }
    
    /**
     *
     * @return the postal code as a string property
     */
    public StringProperty PostalCodeProperty() {
        return postalCode;
    }
    /**
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode.get();
    }
    /**
     *
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }
    /**
     *
     * @return the phone number as a string property
     */
    public StringProperty PhoneProperty() {
        return phone;
    }
    /**
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone.get();
    }
    /**
     *
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    /**
     *
     * @return the division id as an integer property
     */
    public IntegerProperty DivisionIDProperty() {
        return divisionID;
    }
    /**
     *
     * @return the division id
     */
    public int getDivisionID() {
        return divisionID.get();
    }
    /**
     *
     * @param divisionID the division ID to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID.set(divisionID);
    }
    
    //Add invalid input exception
}
