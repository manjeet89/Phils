package com.example.phils.ResponseModels;

public class ResponseModelStockCategory {

    String sn,stock_category_id,stock_category_name,stock_emp_category,stock_category_status,stock_category_updated_on,stock_category_created_on,emp_type_name;

    public ResponseModelStockCategory() {
    }

    public ResponseModelStockCategory(String sn, String stock_category_id, String stock_category_name, String stock_emp_category, String stock_category_status, String stock_category_updated_on, String stock_category_created_on, String emp_type_name) {
        this.sn = sn;
        this.stock_category_id = stock_category_id;
        this.stock_category_name = stock_category_name;
        this.stock_emp_category = stock_emp_category;
        this.stock_category_status = stock_category_status;
        this.stock_category_updated_on = stock_category_updated_on;
        this.stock_category_created_on = stock_category_created_on;
        this.emp_type_name = emp_type_name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public String getStock_emp_category() {
        return stock_emp_category;
    }

    public void setStock_emp_category(String stock_emp_category) {
        this.stock_emp_category = stock_emp_category;
    }

    public String getStock_category_status() {
        return stock_category_status;
    }

    public void setStock_category_status(String stock_category_status) {
        this.stock_category_status = stock_category_status;
    }

    public String getStock_category_updated_on() {
        return stock_category_updated_on;
    }

    public void setStock_category_updated_on(String stock_category_updated_on) {
        this.stock_category_updated_on = stock_category_updated_on;
    }

    public String getStock_category_created_on() {
        return stock_category_created_on;
    }

    public void setStock_category_created_on(String stock_category_created_on) {
        this.stock_category_created_on = stock_category_created_on;
    }

    public String getEmp_type_name() {
        return emp_type_name;
    }

    public void setEmp_type_name(String emp_type_name) {
        this.emp_type_name = emp_type_name;
    }
    //    String sn,stock_category_name,emp_type_name,stock_category_status,stock_category_id;
//
//    public ResponseModelStockCategory() {
//    }
//
//    public ResponseModelStockCategory(String sn, String stock_category_name, String emp_type_name, String stock_category_status, String stock_category_id) {
//        this.sn = sn;
//        this.stock_category_name = stock_category_name;
//        this.emp_type_name = emp_type_name;
//        this.stock_category_status = stock_category_status;
//        this.stock_category_id = stock_category_id;
//    }
//
//    public String getSn() {
//        return sn;
//    }
//
//    public void setSn(String sn) {
//        this.sn = sn;
//    }
//
//    public String getStock_category_name() {
//        return stock_category_name;
//    }
//
//    public void setStock_category_name(String stock_category_name) {
//        this.stock_category_name = stock_category_name;
//    }
//
//    public String getEmp_type_name() {
//        return emp_type_name;
//    }
//
//    public void setEmp_type_name(String emp_type_name) {
//        this.emp_type_name = emp_type_name;
//    }
//
//    public String getStock_category_status() {
//        return stock_category_status;
//    }
//
//    public void setStock_category_status(String stock_category_status) {
//        this.stock_category_status = stock_category_status;
//    }
//
//    public String getStock_category_id() {
//        return stock_category_id;
//    }
//
//    public void setStock_category_id(String stock_category_id) {
//        this.stock_category_id = stock_category_id;
//    }
}
