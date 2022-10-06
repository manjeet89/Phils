package com.example.phils.Spinner;

public class UserLocationSpinner {
    public String location_id;
    public String location_name;

    public UserLocationSpinner(String location_id, String location_name) {
        this.location_id = location_id;
        this.location_name = location_name;
    }

    @Override
    public String toString() {
        return location_name;
    }
}
