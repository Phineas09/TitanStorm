package ro.mta.se.lab.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {

    IntegerProperty id;
    StringProperty name;
    StringProperty lat;
    StringProperty lon;
    StringProperty countryCode;

    public City(Integer id, String name, String lat, String lon, String countryCode) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.lat = new SimpleStringProperty(lat);
        this.lon = new SimpleStringProperty(lon);
        this.countryCode = new SimpleStringProperty(countryCode);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
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
}
