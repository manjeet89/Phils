package com.example.phils.Spinner;

public class StockUOMSpinner {

    public String uom_id;
    public String uom_name;

    public StockUOMSpinner(String uom_id, String uom_name) {
        this.uom_id = uom_id;
        this.uom_name = uom_name;
    }

    @Override
    public String toString() {
        return  uom_name;
    }
}
