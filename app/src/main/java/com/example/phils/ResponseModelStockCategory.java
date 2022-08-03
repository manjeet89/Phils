package com.example.phils;

public class ResponseModelStockCategory {
    String stock_category_id,stock_category_name,stock_category_status,emp_type_name;

    public ResponseModelStockCategory() {
    }

    public ResponseModelStockCategory(String stock_category_id, String stock_category_name, String stock_category_status, String emp_type_name) {
        this.stock_category_id = stock_category_id;
        this.stock_category_name = stock_category_name;
        this.stock_category_status = stock_category_status;
        this.emp_type_name = emp_type_name;
    }

    public String getStock_category_id() {
        return stock_category_id;
    }

    public void setStock_category_id(String stock_category_id) {
        this.stock_category_id = stock_category_id;
    }

    public String getStock_category_name() {
        return stock_category_name;
    }

    public void setStock_category_name(String stock_category_name) {
        this.stock_category_name = stock_category_name;
    }

    public String getStock_category_status() {
        return stock_category_status;
    }

    public void setStock_category_status(String stock_category_status) {
        this.stock_category_status = stock_category_status;
    }

    public String getEmp_type_name() {
        return emp_type_name;
    }

    public void setEmp_type_name(String emp_type_name) {
        this.emp_type_name = emp_type_name;
    }
}
