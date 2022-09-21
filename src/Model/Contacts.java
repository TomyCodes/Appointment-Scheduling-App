/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Tomy Li He
 */
public class Contacts {  
    StringProperty contactName = new SimpleStringProperty();
    SimpleIntegerProperty contactID = new SimpleIntegerProperty();
    StringProperty email = new SimpleStringProperty();
    /**
     * Constructor
     * @param contactID the contact id
     * @param contactName the contact name
     * @param email the contact email
     */    
    public Contacts(int contactID, String contactName, String email){
        this.contactID = new SimpleIntegerProperty(contactID);
        this.contactName = new SimpleStringProperty(contactName);
        this.email = new SimpleStringProperty(email);
    }
    /**
     *
     * @return the contact name as string property
     */
    public StringProperty ContactNameProperty() {
        return contactName;
    }
    /**
     *
     * @return the contact name
     */
    public String getContactName(){
        return contactName.get();
    }
    /**
     *
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName){
        this.contactName.set(contactName);
    }
    /**
     *
     * @return the contact ID as simple integer
     */
    public SimpleIntegerProperty ContactIDProperty() {
        return contactID;
    }
    /**
     *
     * @return the contact ID
     */
    public int getContactID(){
        return contactID.get();
    }
    /**
     *
     * @param contactID the id to set
     */
    public void setContactID(int contactID) {
        this.contactID.set(contactID);
    }
    /**
     *
     * @return the email as string property
     */
    public StringProperty EmailProperty() {
        return email;
    }  
    /**
     *
     * @return the email
     */
    public String getEmail(){
        return email.get();
    }
    /**
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email.set(email);
    }
}
