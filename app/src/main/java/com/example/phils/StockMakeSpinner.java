package com.example.phils;

public class StockMakeSpinner {

    public String make_id;
    public String make_name;

    public StockMakeSpinner(String make_id, String make_name) {
        this.make_id = make_id;
        this.make_name = make_name;
    }

    @Override
    public String toString() {
        return  make_name;
    }
}
