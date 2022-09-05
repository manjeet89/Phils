package com.example.phils.ResponseModels;

public class ResponseModelJobCategory {
    String sn,ob_category_id,job_category_name,job_category_status;

    public ResponseModelJobCategory() {
    }

    public ResponseModelJobCategory(String sn, String ob_category_id, String job_category_name, String job_category_status) {
        this.sn = sn;
        this.ob_category_id = ob_category_id;
        this.job_category_name = job_category_name;
        this.job_category_status = job_category_status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOb_category_id() {
        return ob_category_id;
    }

    public void setOb_category_id(String ob_category_id) {
        this.ob_category_id = ob_category_id;
    }

    public String getJob_category_name() {
        return job_category_name;
    }

    public void setJob_category_name(String job_category_name) {
        this.job_category_name = job_category_name;
    }

    public String getJob_category_status() {
        return job_category_status;
    }

    public void setJob_category_status(String job_category_status) {
        this.job_category_status = job_category_status;
    }
}
