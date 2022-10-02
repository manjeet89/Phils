package com.example.phils.Spinner;

public class StockTypeSpinner {

    public String stock_type_id;
    public String stock_type_name;

    public StockTypeSpinner(String stock_type_id, String stock_type_name) {
        this.stock_type_id = stock_type_id;
        this.stock_type_name = stock_type_name;
    }

    @Override
    public String toString() {
        return  stock_type_name;
    }
}
