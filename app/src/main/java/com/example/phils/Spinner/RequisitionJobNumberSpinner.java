package com.example.phils.Spinner;

public class RequisitionJobNumberSpinner {
    public String job_id;
    public String job_number;

    public RequisitionJobNumberSpinner(String job_id, String job_number) {
        this.job_id = job_id;
        this.job_number = job_number;
    }

    @Override
    public String toString() {
        return  job_number;
    }
}
