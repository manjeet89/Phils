package com.example.phils.ResponseModels;

public class ResponseModelStockType {

    String sn,stock_type_id,stock_category_id,stock_type_name,stock_type_status,stock_type_updated_on,stock_type_created_on,stock_category_name;

    public ResponseModelStockType() {
    }

    public ResponseModelStockType(String sn, String stock_type_id, String stock_category_id, String stock_type_name, String stock_type_status, String stock_type_updated_on, String stock_type_created_on, String stock_category_name) {
        this.sn = sn;
        this.stock_type_id = stock_type_id;
        this.stock_category_id = stock_category_id;
        this.stock_type_name = stock_type_name;
        this.stock_type_status = stock_type_status;
        this.stock_type_updated_on = stock_type_updated_on;
        this.stock_type_created_on = stock_type_created_on;
        this.stock_category_name = stock_category_name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStock_type_id() {
        return stock_type_id;
    }

    public void setStock_type_id(String stock_type_id) {
        this.stock_type_id = stock_type_id;
    }

    public String getStock_category_id() {
        return stock_category_id;
    }

    public void setStock_category_id(String stock_category_id) {
        this.stock_category_id = stock_category_id;
    }

    public String getStock_type_name() {
        return stock_type_name;
    }

    public void setStock_type_name(String stock_type_name) {
        this.stock_type_name = stock_type_name;
    }

    public String getStock_type_status() {
        return stock_type_status;
    }

    public void setStock_type_status(String stock_type_status) {
        this.stock_type_status = stock_type_status;
    }

    public String getStock_type_updated_on() {
        return stock_type_updated_on;
    }

    public void setStock_type_updated_on(String stock_type_updated_on) {
        this.stock_type_updated_on = stock_type_updated_on;
    }

    public String getStock_type_created_on() {
        return stock_type_created_on;
    }

    public void setStock_type_created_on(String stock_type_created_on) {
        this.stock_type_created_on = stock_type_created_on;
    }

    public String getStock_category_name() {
        return stock_category_name;
    }

    public void setStock_category_name(String stock_category_name) {
        this.stock_category_name = stock_category_name;
    }

    //    String sn,stock_category_id,stock_type_name,stock_type_status,stock_type_id;
//
//    public ResponseModelStockType() {
//    }
//
//    public ResponseModelStockType(String sn, String stock_category_id, String stock_type_name, String stock_type_status, String stock_type_id) {
//        this.sn = sn;
//        this.stock_category_id = stock_category_id;
//        this.stock_type_name = stock_type_name;
//        this.stock_type_status = stock_type_status;
//        this.stock_type_id = stock_type_id;
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
//    public String getStock_category_id() {
//        return stock_category_id;
//    }
//
//    public void setStock_category_id(String stock_category_id) {
//        this.stock_category_id = stock_category_id;
//    }
//
//    public String getStock_type_name() {
//        return stock_type_name;
//    }
//
//    public void setStock_type_name(String stock_type_name) {
//        this.stock_type_name = stock_type_name;
//    }
//
//    public String getStock_type_status() {
//        return stock_type_status;
//    }
//
//    public void setStock_type_status(String stock_type_status) {
//        this.stock_type_status = stock_type_status;
//    }
//
//    public String getStock_type_id() {
//        return stock_type_id;
//    }
//
//    public void setStock_type_id(String stock_type_id) {
//        this.stock_type_id = stock_type_id;
//    }
}
