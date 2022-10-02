package com.example.phils.Spinner;

public class JobSizeSpinner {
    public String job_size_id;
    public String job_size_name;

    public JobSizeSpinner(String job_size_id, String job_size_name) {
        this.job_size_id = job_size_id;
        this.job_size_name = job_size_name;
    }

    @Override
    public String toString() {
        return  job_size_name;
    }
}
