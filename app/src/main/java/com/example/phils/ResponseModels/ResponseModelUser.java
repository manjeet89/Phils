package com.example.phils.ResponseModels;

public class ResponseModelUser {
    String sn,user_id,user_name,user_employee_id,user_employee_type,employee_type,user_full_name,gender,user_email_id,
            user_phone_number,user_password,project_location_id,reporting_manager,user_otp,user_otp_tried,user_token,
            user_status,user_updated_on,user_created_on,emp_type_name;

    public ResponseModelUser() {
    }

    public ResponseModelUser(String sn, String user_id, String user_name, String user_employee_id, String user_employee_type, String employee_type, String user_full_name, String gender, String user_email_id, String user_phone_number, String user_password, String project_location_id, String reporting_manager, String user_otp, String user_otp_tried, String user_token, String user_status, String user_updated_on, String user_created_on, String emp_type_name) {
        this.sn = sn;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_employee_id = user_employee_id;
        this.user_employee_type = user_employee_type;
        this.employee_type = employee_type;
        this.user_full_name = user_full_name;
        this.gender = gender;
        this.user_email_id = user_email_id;
        this.user_phone_number = user_phone_number;
        this.user_password = user_password;
        this.project_location_id = project_location_id;
        this.reporting_manager = reporting_manager;
        this.user_otp = user_otp;
        this.user_otp_tried = user_otp_tried;
        this.user_token = user_token;
        this.user_status = user_status;
        this.user_updated_on = user_updated_on;
        this.user_created_on = user_created_on;
        this.emp_type_name = emp_type_name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_employee_id() {
        return user_employee_id;
    }

    public void setUser_employee_id(String user_employee_id) {
        this.user_employee_id = user_employee_id;
    }

    public String getUser_employee_type() {
        return user_employee_type;
    }

    public void setUser_employee_type(String user_employee_type) {
        this.user_employee_type = user_employee_type;
    }

    public String getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employee_type;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_email_id() {
        return user_email_id;
    }

    public void setUser_email_id(String user_email_id) {
        this.user_email_id = user_email_id;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getProject_location_id() {
        return project_location_id;
    }

    public void setProject_location_id(String project_location_id) {
        this.project_location_id = project_location_id;
    }

    public String getReporting_manager() {
        return reporting_manager;
    }

    public void setReporting_manager(String reporting_manager) {
        this.reporting_manager = reporting_manager;
    }

    public String getUser_otp() {
        return user_otp;
    }

    public void setUser_otp(String user_otp) {
        this.user_otp = user_otp;
    }

    public String getUser_otp_tried() {
        return user_otp_tried;
    }

    public void setUser_otp_tried(String user_otp_tried) {
        this.user_otp_tried = user_otp_tried;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_updated_on() {
        return user_updated_on;
    }

    public void setUser_updated_on(String user_updated_on) {
        this.user_updated_on = user_updated_on;
    }

    public String getUser_created_on() {
        return user_created_on;
    }

    public void setUser_created_on(String user_created_on) {
        this.user_created_on = user_created_on;
    }

    public String getEmp_type_name() {
        return emp_type_name;
    }

    public void setEmp_type_name(String emp_type_name) {
        this.emp_type_name = emp_type_name;
    }

    //
//    String sn,user_id,user_full_name,emp_type_name,user_name,user_phone_number,user_otp,employee_type,user_status;
//
//    public ResponseModelUser() {
//    }
//
//    public ResponseModelUser(String user_id, String user_full_name, String emp_type_name, String user_name, String user_phone_number, String user_otp, String employee_type, String user_status) {
//        this.user_id = user_id;
//        this.user_full_name = user_full_name;
//        this.emp_type_name = emp_type_name;
//        this.user_name = user_name;
//        this.user_phone_number = user_phone_number;
//        this.user_otp = user_otp;
//        this.employee_type = employee_type;
//        this.user_status = user_status;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getUser_full_name() {
//        return user_full_name;
//    }
//
//    public void setUser_full_name(String user_full_name) {
//        this.user_full_name = user_full_name;
//    }
//
//    public String getEmp_type_name() {
//        return emp_type_name;
//    }
//
//    public void setEmp_type_name(String emp_type_name) {
//        this.emp_type_name = emp_type_name;
//    }
//
//    public String getUser_name() {
//        return user_name;
//    }
//
//    public void setUser_name(String user_name) {
//        this.user_name = user_name;
//    }
//
//    public String getUser_phone_number() {
//        return user_phone_number;
//    }
//
//    public void setUser_phone_number(String user_phone_number) {
//        this.user_phone_number = user_phone_number;
//    }
//
//    public String getUser_otp() {
//        return user_otp;
//    }
//
//    public void setUser_otp(String user_otp) {
//        this.user_otp = user_otp;
//    }
//
//    public String getEmployee_type() {
//        return employee_type;
//    }
//
//    public void setEmployee_type(String employee_type) {
//        this.employee_type = employee_type;
//    }
//
//    public String getUser_status() {
//        return user_status;
//    }
//
//    public void setUser_status(String user_status) {
//        this.user_status = user_status;
//    }
}
