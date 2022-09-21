/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 *
 * @author Tomy Li He
 */
public class Appointments {
    private IntegerProperty appointmentID = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty location = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private ZonedDateTime start;
    private ZonedDateTime end;
    Timestamp lastUpdate;
    Users lastUpdatedBy;
    private IntegerProperty customerID = new SimpleIntegerProperty();
    private IntegerProperty userID = new SimpleIntegerProperty();
    private IntegerProperty contactID = new SimpleIntegerProperty();
            

    /**
     * Constructor
     * @param appointmentID the appointment ID
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the appointment start time
     * @param end the appointment end time
     * @param customerID the customer id
     * @param userID the user id
     * @param contactID the contact id
     */
    public Appointments(int appointmentID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID){
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.start = start;
        this.end = end;
        this.customerID = new SimpleIntegerProperty(customerID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contactID = new SimpleIntegerProperty(contactID);
    }
    
    /**
     *
     * @return the appointment ID
     */
    public final int getAppointmentID() {
        return appointmentID.get();
    }
    /**
     *
     * @param appointmentID the appointment id to set
     */
    public final void setAppointmentID(int appointmentID) {
        this.appointmentID.set(appointmentID);
    }
    /**
     *
     * @return the appointment ID as integer property
     */
    public IntegerProperty appointmentIdProperty() {
        return appointmentID;
    }
    /**
     *
     * @return the Title
     */
    public String getTitle() {
        return title.get();
    }
    /**
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title.set(title);
    }
    /**
     *
     * @return the title as a string property
     */
    public StringProperty titleProperty() {
        return title;
    }
    /**
     *
     * @return the Appointment description
     */
    public String getDescription() {
        return description.get();
    }
    /**
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description.set(description);
    }
    /**
     *
     * @return the description as a string property
     */
    public StringProperty descriptionProperty() {
        return description;
    }
    /**
     *
     * @return the Appointment location
     */
    public String getLocation() {
        return location.get();
    }
    /**
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location.set(location);
    }
    /**
     *
     * @return the location as a string property
     */
    public StringProperty locationProperty() {
        return location;
    }
    /**
     *
     * @return the Appointment type
     */
    public String getType() {
        return type.get();
    }
    /**
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type.set(type);
    }
    /**
     *
     * @return the appointment type as a string property
     */
    public StringProperty typeProperty() {
        return type;
    }
    /**
     *
     * @return the Appointment start time
     */
    public ZonedDateTime getStart() {
        return start;
    }
    /**
     *
     * @param start the start time to set
     */
    public void setStart(ZonedDateTime start) {
        this.start = start;
    }
    /**
     *
     * @return the Appointment end time
     */
    public ZonedDateTime getEnd() {
        return end;
    }
    /**
     *
     * @param end the end time to set
     */
    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
    /**
     *
     * @return the customer ID
     */
    public final int getCustomerID() {
        return customerID.get();
    }
    /**
     *
     * @param customerID the customerID to set
     */
    public final void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }
    /**
     *
     * @return the customer id as an integer property
     */
    public IntegerProperty customerIDProperty() {
        return customerID;
    }
    /**
     *
     * @return the user ID
     */
    public final int getUserID() {
        return userID.get();
    }
    /**
     *
     * @param userID the user ID to set
     */
    public final void setUserID(int userID) {
        this.userID.set(userID);
    }
    /**
     *
     * @return the user ID as an integer property
     */
    public IntegerProperty userIDProperty() {
        return userID;
    }
    /**
     *
     * @return the contact id as an integer property
     */
    public IntegerProperty contactIDProperty(){
        return contactID;
    }
    /**
     *
     * @return the contact ID
     */
    public final int getContactID() {
        return contactID.get();
    }
    /**
     *
     * @param contactID the contact ID to set
     */
    public final void setContactID(int contactID) {
        this.contactID.set(contactID);
    }
    
}
