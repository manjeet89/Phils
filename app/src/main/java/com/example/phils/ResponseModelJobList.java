package com.example.phils;

public class ResponseModelJobList {
    String sn,job_name,job_number,job_manager_id,job_status,job_id;

    public ResponseModelJobList() {
    }

    public ResponseModelJobList(String sn, String job_name, String job_number, String job_manager_id, String job_status, String job_id) {
        this.sn = sn;
        this.job_name = job_name;
        this.job_number = job_number;
        this.job_manager_id = job_manager_id;
        this.job_status = job_status;
        this.job_id = job_id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public String getJob_manager_id() {
        return job_manager_id;
    }

    public void setJob_manager_id(String job_manager_id) {
        this.job_manager_id = job_manager_id;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
