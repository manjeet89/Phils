package com.example.phils;

public class StockTransferlocation {
    public String location_id;
    public  String location_name;

    public StockTransferlocation(String location_id, String location_name) {
        this.location_id = location_id;
        this.location_name = location_name;
    }

    @Override
    public String toString() {
        return location_name;
    }
}
