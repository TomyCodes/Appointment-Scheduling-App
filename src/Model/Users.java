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

/**
 *
 * @author Tomy Li He
 */
public class Users {
    StringProperty userName = new SimpleStringProperty();
    SimpleIntegerProperty userID = new SimpleIntegerProperty();
    StringProperty password = new SimpleStringProperty();
    Date createDate;
    Timestamp lastUpdated;
    Users lastUpdatedBy;
    Users createdBy;
    
    /**
     * Default Constructor 
     */
    public Users(){};
    
    /**
     * Constructor that takes in a username and password
     * @param userName the user name
     * @param password the password
     */
    public Users(String userName, String password) {
        
    }
    /**
     *
     * @return the user name
     */
    public String getUserName() {
        return userName.get();
    }
    /**
     *
     * @return the user name as a string property
     */
    public StringProperty userNameProperty() {
        return userName;
    }
    /**
     *
     * @param userName the user name to set
     */
    public void setUserName(String userName) {
        this.userName.set(userName);
    }
    /**
     *
     * @return the password
     */
    public String getPassword() {
        return password.get();
    }
    public StringProperty passwordProperty() {
        return password;
    }
    /**
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password.set(password);
    }
    /**
     *
     * @return the user ID;
     */
    public int getUserID() {
        return userID.get();
    }
    /**
     *
     * @return the user id as a string property
     */
    public SimpleIntegerProperty userIDProperty() {
        return userID;
    }
    
    /**
     *
     * @param userID the id to set
     */
    public void setUserID(int userID) {
        this.userID.set(userID);
    }
}


