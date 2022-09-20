package com.example.phils.ResponseModels;

public class ResponseModelJobSize {
    String sn,job_size_id,job_size_name,job_size_status,job_size_updated_on,job_size_created_on;

    public ResponseModelJobSize() {
    }

    public ResponseModelJobSize(String sn, String job_size_id, String job_size_name, String job_size_status, String job_size_updated_on, String job_size_created_on) {
        this.sn = sn;
        this.job_size_id = job_size_id;
        this.job_size_name = job_size_name;
        this.job_size_status = job_size_status;
        this.job_size_updated_on = job_size_updated_on;
        this.job_size_created_on = job_size_created_on;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getJob_size_id() {
        return job_size_id;
    }

    public void setJob_size_id(String job_size_id) {
        this.job_size_id = job_size_id;
    }

    public String getJob_size_name() {
        return job_size_name;
    }

    public void setJob_size_name(String job_size_name) {
        this.job_size_name = job_size_name;
    }

    public String getJob_size_status() {
        return job_size_status;
    }

    public void setJob_size_status(String job_size_status) {
        this.job_size_status = job_size_status;
    }

    public String getJob_size_updated_on() {
        return job_size_updated_on;
    }

    public void setJob_size_updated_on(String job_size_updated_on) {
        this.job_size_updated_on = job_size_updated_on;
    }

    public String getJob_size_created_on() {
        return job_size_created_on;
    }

    public void setJob_size_created_on(String job_size_created_on) {
        this.job_size_created_on = job_size_created_on;
    }
    //    public ResponseModelJobSize() {
//    }
//
//    public ResponseModelJobSize(String sn, String job_size_id, String job_size_name, String job_size_status) {
//        this.sn = sn;
//        this.job_size_id = job_size_id;
//        this.job_size_name = job_size_name;
//        this.job_size_status = job_size_status;
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
//    public String getJob_size_id() {
//        return job_size_id;
//    }
//
//    public void setJob_size_id(String job_size_id) {
//        this.job_size_id = job_size_id;
//    }
//
//    public String getJob_size_name() {
//        return job_size_name;
//    }
//
//    public void setJob_size_name(String job_size_name) {
//        this.job_size_name = job_size_name;
//    }
//
//    public String getJob_size_status() {
//        return job_size_status;
//    }
//
//    public void setJob_size_status(String job_size_status) {
//        this.job_size_status = job_size_status;
//    }
}
