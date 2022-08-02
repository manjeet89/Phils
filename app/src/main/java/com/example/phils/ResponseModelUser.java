package com.example.phils;

public class ResponseModelUser {
    String emp_type_name,user_id,user_name,user_employee_id,employee_type,user_full_name,user_email_id,user_phone_number,user_otp,user_status;

    public ResponseModelUser() {
    }

    public ResponseModelUser(String emp_type_name, String user_id, String user_name, String user_employee_id, String employee_type, String user_full_name, String user_email_id, String user_phone_number, String user_otp, String user_status) {
        this.emp_type_name = emp_type_name;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_employee_id = user_employee_id;
        this.employee_type = employee_type;
        this.user_full_name = user_full_name;
        this.user_email_id = user_email_id;
        this.user_phone_number = user_phone_number;
        this.user_otp = user_otp;
        this.user_status = user_status;
    }

    public String getEmp_type_name() {
        return emp_type_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_employee_id() {
        return user_employee_id;
    }

    public String getEmployee_type() {
        return employee_type;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public String getUser_email_id() {
        return user_email_id;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public String getUser_otp() {
        return user_otp;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setEmp_type_name(String emp_type_name) {
        this.emp_type_name = emp_type_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_employee_id(String user_employee_id) {
        this.user_employee_id = user_employee_id;
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employee_type;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public void setUser_email_id(String user_email_id) {
        this.user_email_id = user_email_id;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public void setUser_otp(String user_otp) {
        this.user_otp = user_otp;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }
}
