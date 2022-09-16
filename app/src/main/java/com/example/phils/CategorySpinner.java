package com.example.phils;

public class CategorySpinner {
    public String stock_category_id;
    public String stock_category_name;

    public CategorySpinner(String stock_category_id, String stock_category_name) {
        this.stock_category_id = stock_category_id;
        this.stock_category_name = stock_category_name;
    }

    @Override
    public String toString() {
        return stock_category_name ;
    }
}
