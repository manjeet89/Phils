package com.example.phils.Spinner;

public class StockSizeSpinner {

    public String stock_size_id;
    public String stock_size_name;

    public StockSizeSpinner(String stock_size_id, String stock_size_name) {
        this.stock_size_id = stock_size_id;
        this.stock_size_name = stock_size_name;
    }

    @Override
    public String toString() {
        return  stock_size_name;
    }
}
