package com.example.phils.Spinner;

public class GrinderWelderListSpinner {
    public String user_id;
    public String user_full_name;

    public GrinderWelderListSpinner(String user_id, String user_full_name) {
        this.user_id = user_id;
        this.user_full_name = user_full_name;
    }

    @Override
    public String toString() {
        return user_full_name ;
    }
}
