/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Timestamp;
/**
 *
 * @author Tomy Li He
 */
public class FirstLevelDivisions{
    int divisionID;
    int countryID;
    String division;
    
    Date createDate;
    Timestamp lastUpdated;
    Users lastUpdatedBy;
    Users createdBy;
    
    /**
     * Constructor
     * @param divisionID the division id
     * @param division the division name
     * @param countryID the country id
     */
    public FirstLevelDivisions(int divisionID, String division, int countryID){
        this.division = division;
        this.divisionID = divisionID;
        this.countryID = countryID;
    }
    /**
     *
     * @return the division ID
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     *
     * @param divisionID the id to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /**
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }
    /**
     *
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }
}
