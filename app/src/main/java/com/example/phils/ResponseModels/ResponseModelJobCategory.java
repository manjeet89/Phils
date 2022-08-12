package com.example.phils.ResponseModels;

public class ResponseModelJobCategory {
    String job_category_id,job_category_name,job_category_status;

    public ResponseModelJobCategory() {
    }

    public ResponseModelJobCategory(String job_category_id, String job_category_name, String job_category_status) {
        this.job_category_id = job_category_id;
        this.job_category_name = job_category_name;
        this.job_category_status = job_category_status;
    }

    public String getJob_category_id() {
        return job_category_id;
    }

    public void setJob_category_id(String job_category_id) {
        this.job_category_id = job_category_id;
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
