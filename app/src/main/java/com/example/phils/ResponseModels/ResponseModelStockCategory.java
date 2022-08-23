package com.example.phils.ResponseModels;

public class ResponseModelStockCategory {
    String sn,stock_category_name,emp_type_name,stock_category_status,stock_category_id;

    public ResponseModelStockCategory() {
    }

    public ResponseModelStockCategory(String sn, String stock_category_name, String emp_type_name, String stock_category_status, String stock_category_id) {
        this.sn = sn;
        this.stock_category_name = stock_category_name;
        this.emp_type_name = emp_type_name;
        this.stock_category_status = stock_category_status;
        this.stock_category_id = stock_category_id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStock_category_name() {
        return stock_category_name;
    }

    public void setStock_category_name(String stock_category_name) {
        this.stock_category_name = stock_category_name;
    }

    public String getEmp_type_name() {
        return emp_type_name;
    }

    public void setEmp_type_name(String emp_type_name) {
        this.emp_type_name = emp_type_name;
    }

    public String getStock_category_status() {
        return stock_category_status;
    }

    public void setStock_category_status(String stock_category_status) {
        this.stock_category_status = stock_category_status;
    }

    public String getStock_category_id() {
        return stock_category_id;
    }

    public void setStock_category_id(String stock_category_id) {
        this.stock_category_id = stock_category_id;
    }
}
