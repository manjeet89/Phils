package com.example.phils.ResponseModels;

public class ResponseModelStockMake {
    String sn,make_name,make_status,make_id;

    public ResponseModelStockMake() {
    }

    public ResponseModelStockMake(String sn, String make_name, String make_status, String make_id) {
        this.sn = sn;
        this.make_name = make_name;
        this.make_status = make_status;
        this.make_id = make_id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMake_name() {
        return make_name;
    }

    public void setMake_name(String make_name) {
        this.make_name = make_name;
    }

    public String getMake_status() {
        return make_status;
    }

    public void setMake_status(String make_status) {
        this.make_status = make_status;
    }

    public String getMake_id() {
        return make_id;
    }

    public void setMake_id(String make_id) {
        this.make_id = make_id;
    }
}
