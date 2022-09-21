/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Tomy Li He
 */
public class Countries {
    int countryID;
    String country;
    /**
     * Constructor
     * @param country the country name
     * @param countryID the country ID
     */    
    public Countries(String country, int countryID){
        this.country = country;
        this.countryID = countryID;
    }

    
    /**
     *
     * @return the country ID
     */
    public int getCountryID() {
        return countryID;
    }
    /**
     *
     * @param countryID the country id to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    /**
     *
     * @return the country name
     */
    public String getCountry() {
        return country;
    }
    /**
     *
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
