package com.example.phils.ResponseModels;

public class ResponseModelStockList {

    String sn,stock_id,stock_location_id,stock_category_id,stock_type_id,stock_size_id,stock_batch_number,stock_invoice_number,
            stock_distributor_name,stock_make_id,stock_uom_id,safety_stock,stock_quantity,stock_lot,stock_price,is_stock_transfer,
            stock_status,stock_type_name,stock_size_name,make_name,uom_name,stock_category_name,assign_quantity;

    public ResponseModelStockList() {
    }

    public ResponseModelStockList(String sn, String stock_id, String stock_location_id, String stock_category_id, String stock_type_id, String stock_size_id, String stock_batch_number, String stock_invoice_number, String stock_distributor_name, String stock_make_id, String stock_uom_id, String safety_stock, String stock_quantity, String stock_lot, String stock_price, String is_stock_transfer, String stock_status, String stock_type_name, String stock_size_name, String make_name, String uom_name, String stock_category_name, String assign_quantity) {
        this.sn = sn;
        this.stock_id = stock_id;
        this.stock_location_id = stock_location_id;
        this.stock_category_id = stock_category_id;
        this.stock_type_id = stock_type_id;
        this.stock_size_id = stock_size_id;
        this.stock_batch_number = stock_batch_number;
        this.stock_invoice_number = stock_invoice_number;
        this.stock_distributor_name = stock_distributor_name;
        this.stock_make_id = stock_make_id;
        this.stock_uom_id = stock_uom_id;
        this.safety_stock = safety_stock;
        this.stock_quantity = stock_quantity;
        this.stock_lot = stock_lot;
        this.stock_price = stock_price;
        this.is_stock_transfer = is_stock_transfer;
        this.stock_status = stock_status;
        this.stock_type_name = stock_type_name;
        this.stock_size_name = stock_size_name;
        this.make_name = make_name;
        this.uom_name = uom_name;
        this.stock_category_name = stock_category_name;
        this.assign_quantity = assign_quantity;
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

    public String getStock_location_id() {
        return stock_location_id;
    }

    public void setStock_location_id(String stock_location_id) {
        this.stock_location_id = stock_location_id;
    }

    public String getStock_category_id() {
        return stock_category_id;
    }

    public void setStock_category_id(String stock_category_id) {
        this.stock_category_id = stock_category_id;
    }

    public String getStock_type_id() {
        return stock_type_id;
    }

    public void setStock_type_id(String stock_type_id) {
        this.stock_type_id = stock_type_id;
    }

    public String getStock_size_id() {
        return stock_size_id;
    }

    public void setStock_size_id(String stock_size_id) {
        this.stock_size_id = stock_size_id;
    }

    public String getStock_batch_number() {
        return stock_batch_number;
    }

    public void setStock_batch_number(String stock_batch_number) {
        this.stock_batch_number = stock_batch_number;
    }

    public String getStock_invoice_number() {
        return stock_invoice_number;
    }

    public void setStock_invoice_number(String stock_invoice_number) {
        this.stock_invoice_number = stock_invoice_number;
    }

    public String getStock_distributor_name() {
        return stock_distributor_name;
    }

    public void setStock_distributor_name(String stock_distributor_name) {
        this.stock_distributor_name = stock_distributor_name;
    }

    public String getStock_make_id() {
        return stock_make_id;
    }

    public void setStock_make_id(String stock_make_id) {
        this.stock_make_id = stock_make_id;
    }

    public String getStock_uom_id() {
        return stock_uom_id;
    }

    public void setStock_uom_id(String stock_uom_id) {
        this.stock_uom_id = stock_uom_id;
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

    public String getStock_lot() {
        return stock_lot;
    }

    public void setStock_lot(String stock_lot) {
        this.stock_lot = stock_lot;
    }

    public String getStock_price() {
        return stock_price;
    }

    public void setStock_price(String stock_price) {
        this.stock_price = stock_price;
    }

    public String getIs_stock_transfer() {
        return is_stock_transfer;
    }

    public void setIs_stock_transfer(String is_stock_transfer) {
        this.is_stock_transfer = is_stock_transfer;
    }

    public String getStock_status() {
        return stock_status;
    }

    public void setStock_status(String stock_status) {
        this.stock_status = stock_status;
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

    public String getStock_category_name() {
        return stock_category_name;
    }

    public void setStock_category_name(String stock_category_name) {
        this.stock_category_name = stock_category_name;
    }

    public String getAssign_quantity() {
        return assign_quantity;
    }

    public void setAssign_quantity(String assign_quantity) {
        this.assign_quantity = assign_quantity;
    }
    //    String sn,stock_id,stock_category_name,stock_type_name,stock_size_name,stock_batch_number,make_name,uom_name,safety_stock,stock_quantity,stock_price,stock_status;
//
//    public ResponseModelStockList() {
//    }
//
//    public ResponseModelStockList(String sn, String stock_id, String stock_category_name, String stock_type_name, String stock_size_name, String stock_batch_number, String make_name, String uom_name, String safety_stock, String stock_quantity, String stock_price, String stock_status) {
//        this.sn = sn;
//        this.stock_id = stock_id;
//        this.stock_category_name = stock_category_name;
//        this.stock_type_name = stock_type_name;
//        this.stock_size_name = stock_size_name;
//        this.stock_batch_number = stock_batch_number;
//        this.make_name = make_name;
//        this.uom_name = uom_name;
//        this.safety_stock = safety_stock;
//        this.stock_quantity = stock_quantity;
//        this.stock_price = stock_price;
//        this.stock_status = stock_status;
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
//    public String getStock_id() {
//        return stock_id;
//    }
//
//    public void setStock_id(String stock_id) {
//        this.stock_id = stock_id;
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
//    public String getStock_type_name() {
//        return stock_type_name;
//    }
//
//    public void setStock_type_name(String stock_type_name) {
//        this.stock_type_name = stock_type_name;
//    }
//
//    public String getStock_size_name() {
//        return stock_size_name;
//    }
//
//    public void setStock_size_name(String stock_size_name) {
//        this.stock_size_name = stock_size_name;
//    }
//
//    public String getStock_batch_number() {
//        return stock_batch_number;
//    }
//
//    public void setStock_batch_number(String stock_batch_number) {
//        this.stock_batch_number = stock_batch_number;
//    }
//
//    public String getMake_name() {
//        return make_name;
//    }
//
//    public void setMake_name(String make_name) {
//        this.make_name = make_name;
//    }
//
//    public String getUom_name() {
//        return uom_name;
//    }
//
//    public void setUom_name(String uom_name) {
//        this.uom_name = uom_name;
//    }
//
//    public String getSafety_stock() {
//        return safety_stock;
//    }
//
//    public void setSafety_stock(String safety_stock) {
//        this.safety_stock = safety_stock;
//    }
//
//    public String getStock_quantity() {
//        return stock_quantity;
//    }
//
//    public void setStock_quantity(String stock_quantity) {
//        this.stock_quantity = stock_quantity;
//    }
//
//    public String getStock_price() {
//        return stock_price;
//    }
//
//    public void setStock_price(String stock_price) {
//        this.stock_price = stock_price;
//    }
//
//    public String getStock_status() {
//        return stock_status;
//    }
//
//    public void setStock_status(String stock_status) {
//        this.stock_status = stock_status;
//    }
}
