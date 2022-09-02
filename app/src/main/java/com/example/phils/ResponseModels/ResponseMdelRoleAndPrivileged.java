package com.example.phils.ResponseModels;

public class ResponseMdelRoleAndPrivileged {
    String sn,emp_type_id,emp_type_name,emp_type_created_on;

    public ResponseMdelRoleAndPrivileged() {
    }

    public ResponseMdelRoleAndPrivileged(String sn, String emp_type_id, String emp_type_name, String emp_type_created_on) {
        this.sn = sn;
        this.emp_type_id = emp_type_id;
        this.emp_type_name = emp_type_name;
        this.emp_type_created_on = emp_type_created_on;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getEmp_type_id() {
        return emp_type_id;
    }

    public void setEmp_type_id(String emp_type_id) {
        this.emp_type_id = emp_type_id;
    }

    public String getEmp_type_name() {
        return emp_type_name;
    }

    public void setEmp_type_name(String emp_type_name) {
        this.emp_type_name = emp_type_name;
    }

    public String getEmp_type_created_on() {
        return emp_type_created_on;
    }

    public void setEmp_type_created_on(String emp_type_created_on) {
        this.emp_type_created_on = emp_type_created_on;
    }
}
