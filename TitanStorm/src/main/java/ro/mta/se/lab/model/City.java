package ro.mta.se.lab.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model for the city object
 */
public class City {

    /**
     * All the fields specific to a city
     */
    StringProperty id;
    StringProperty name;
    StringProperty lat;
    StringProperty lon;
    StringProperty countryCode;

    /**
     * Basic constructor that receives only the name, all the other props will be null.
     * @param name name of the city
     */
    public City(String name) {
        this.name = new SimpleStringProperty(name);
        this.id = null;
        this.lat = null;
        this.lon = null;
        this.countryCode = null;
    }

    /**
     * Constructor that will initialize our city object.
     * @param id if of the city
     * @param name name of the city
     * @param lat latitude of the city
     * @param lon longitude of the city
     * @param countryCode afferent country code
     */
    public City(String id, String name, String lat, String lon, String countryCode) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.lat = new SimpleStringProperty(lat);
        this.lon = new SimpleStringProperty(lon);
        this.countryCode = new SimpleStringProperty(countryCode);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLat() {
        return lat.get();
    }

    public StringProperty latProperty() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat.set(lat);
    }

    public String getLon() {
        return lon.get();
    }

    public StringProperty lonProperty() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon.set(lon);
    }

    public String getCountryCode() {
        return countryCode.get();
    }

    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode.set(countryCode);
    }

    @Override
    public String toString() {
        return name.getValue();
    }
}
