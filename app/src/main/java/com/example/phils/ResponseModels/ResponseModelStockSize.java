package com.example.phils.ResponseModels;

public class ResponseModelStockSize {

    String stock_size_id,stock_category_name,stock_type_name,stock_size_name,stock_size_status;

    public ResponseModelStockSize() {
    }

    public ResponseModelStockSize(String stock_size_id, String stock_category_name, String stock_type_name, String stock_size_name, String stock_size_status) {
        this.stock_size_id = stock_size_id;
        this.stock_category_name = stock_category_name;
        this.stock_type_name = stock_type_name;
        this.stock_size_name = stock_size_name;
        this.stock_size_status = stock_size_status;
    }

    public String getStock_size_id() {
        return stock_size_id;
    }

    public void setStock_size_id(String stock_size_id) {
        this.stock_size_id = stock_size_id;
    }

    public String getStock_category_name() {
        return stock_category_name;
    }

    public void setStock_category_name(String stock_category_name) {
        this.stock_category_name = stock_category_name;
    }

    public String getStock_type_name() {
        return stock_type_name;
    }

    public void setStock_type_name(String stock_type_name) {
        this.stock_type_name = stock_type_name;
    }

    public String getStock_size_name() {
        return stock_size_name;
    }

    public void setStock_size_name(String stock_size_name) {
        this.stock_size_name = stock_size_name;
    }

    public String getStock_size_status() {
        return stock_size_status;
    }

    public void setStock_size_status(String stock_size_status) {
        this.stock_size_status = stock_size_status;
    }
}
