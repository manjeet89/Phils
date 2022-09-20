package com.example.phils;

public class EmpSpinner {
    public String emp_type_id;
    public String emp_type_name;

    public EmpSpinner(String emp_type_id, String emp_type_name) {
        this.emp_type_id = emp_type_id;
        this.emp_type_name = emp_type_name;
    }

    @Override
    public String toString() {
        return  emp_type_name;
    }
}
