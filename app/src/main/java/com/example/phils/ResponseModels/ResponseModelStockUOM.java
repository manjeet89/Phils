package com.example.phils.ResponseModels;

public class ResponseModelStockUOM {
    String sn,uom_id,uom_name,uom_status;

    public ResponseModelStockUOM() {
    }

    public ResponseModelStockUOM(String sn, String uom_id, String uom_name, String uom_status) {
        this.sn = sn;
        this.uom_id = uom_id;
        this.uom_name = uom_name;
        this.uom_status = uom_status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUom_id() {
        return uom_id;
    }

    public void setUom_id(String uom_id) {
        this.uom_id = uom_id;
    }

    public String getUom_name() {
        return uom_name;
    }

    public void setUom_name(String uom_name) {
        this.uom_name = uom_name;
    }

    public String getUom_status() {
        return uom_status;
    }

    public void setUom_status(String uom_status) {
        this.uom_status = uom_status;
    }
    //
//    public ResponseModelStockUOM() {
//    }
//
//    public ResponseModelStockUOM(String uom_id, String uom_name, String uom_status) {
//        this.uom_id = uom_id;
//        this.uom_name = uom_name;
//        this.uom_status = uom_status;
//    }
//
//    public String getUom_id() {
//        return uom_id;
//    }
//
//    public void setUom_id(String uom_id) {
//        this.uom_id = uom_id;
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
//    public String getUom_status() {
//        return uom_status;
//    }
//
//    public void setUom_status(String uom_status) {
//        this.uom_status = uom_status;
//    }
}
