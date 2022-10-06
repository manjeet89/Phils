package com.example.phils.Spinner;

public class UserEmployeeListSpinner {
    public String emp_type_id;
    public String emp_type_name;

    public UserEmployeeListSpinner(String emp_type_id, String emp_type_name) {
        this.emp_type_id = emp_type_id;
        this.emp_type_name = emp_type_name;
    }

    @Override
    public String toString() {
        return emp_type_name;
    }
}
