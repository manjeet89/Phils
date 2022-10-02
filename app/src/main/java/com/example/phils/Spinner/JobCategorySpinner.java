package com.example.phils.Spinner;

public class JobCategorySpinner {
    public String job_category_id;
    public String job_category_name;

    public JobCategorySpinner(String job_category_id, String job_category_name) {
        this.job_category_id = job_category_id;
        this.job_category_name = job_category_name;
    }

    @Override
    public String toString() {
        return job_category_name;
    }
}
