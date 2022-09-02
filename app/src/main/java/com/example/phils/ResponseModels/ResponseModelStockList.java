package com.example.phils.ResponseModels;

public class ResponseModelStockList {

    String sn,stock_id,stock_category_name,stock_type_name,stock_size_name,stock_batch_number,make_name,uom_name,safety_stock,stock_quantity,stock_price,stock_status;

    public ResponseModelStockList() {
    }

    public ResponseModelStockList(String sn, String stock_id, String stock_category_name, String stock_type_name, String stock_size_name, String stock_batch_number, String make_name, String uom_name, String safety_stock, String stock_quantity, String stock_price, String stock_status) {
        this.sn = sn;
        this.stock_id = stock_id;
        this.stock_category_name = stock_category_name;
        this.stock_type_name = stock_type_name;
        this.stock_size_name = stock_size_name;
        this.stock_batch_number = stock_batch_number;
        this.make_name = make_name;
        this.uom_name = uom_name;
        this.safety_stock = safety_stock;
        this.stock_quantity = stock_quantity;
        this.stock_price = stock_price;
        this.stock_status = stock_status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
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

    public String getStock_batch_number() {
        return stock_batch_number;
    }

    public void setStock_batch_number(String stock_batch_number) {
        this.stock_batch_number = stock_batch_number;
    }

    public String getMake_name() {
        return make_name;
    }

    public void setMake_name(String make_name) {
        this.make_name = make_name;
    }

    public String getUom_name() {
        return uom_name;
    }

    public void setUom_name(String uom_name) {
        this.uom_name = uom_name;
    }

    public String getSafety_stock() {
        return safety_stock;
    }

    public void setSafety_stock(String safety_stock) {
        this.safety_stock = safety_stock;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getStock_price() {
        return stock_price;
    }

    public void setStock_price(String stock_price) {
        this.stock_price = stock_price;
    }

    public String getStock_status() {
        return stock_status;
    }

    public void setStock_status(String stock_status) {
        this.stock_status = stock_status;
    }
}
