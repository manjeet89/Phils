package com.example.phils;

public class ResponseModelRequisitionComplete {
    String sn,req_id,req_user_id,req_by_user_id,req_job_id,seam_number,req_category_id,req_type_id,req_size_id,req_quantity,
            req_remark,req_location_id,req_manager_id,req_manager_comment,req_manager_status,req_status,req_updated_on,
            req_created_on,stock_type_name,stock_size_name,stock_category_name,job_number,user_full_name,user_employee_id,
            assign_quantity,req_user_id_details;

    public ResponseModelRequisitionComplete() {
    }

    public ResponseModelRequisitionComplete(String sn, String req_id, String req_user_id, String req_by_user_id, String req_job_id, String seam_number, String req_category_id, String req_type_id, String req_size_id, String req_quantity, String req_remark, String req_location_id, String req_manager_id, String req_manager_comment, String req_manager_status, String req_status, String req_updated_on, String req_created_on, String stock_type_name, String stock_size_name, String stock_category_name, String job_number, String user_full_name, String user_employee_id, String assign_quantity, String req_user_id_details) {
        this.sn = sn;
        this.req_id = req_id;
        this.req_user_id = req_user_id;
        this.req_by_user_id = req_by_user_id;
        this.req_job_id = req_job_id;
        this.seam_number = seam_number;
        this.req_category_id = req_category_id;
        this.req_type_id = req_type_id;
        this.req_size_id = req_size_id;
        this.req_quantity = req_quantity;
        this.req_remark = req_remark;
        this.req_location_id = req_location_id;
        this.req_manager_id = req_manager_id;
        this.req_manager_comment = req_manager_comment;
        this.req_manager_status = req_manager_status;
        this.req_status = req_status;
        this.req_updated_on = req_updated_on;
        this.req_created_on = req_created_on;
        this.stock_type_name = stock_type_name;
        this.stock_size_name = stock_size_name;
        this.stock_category_name = stock_category_name;
        this.job_number = job_number;
        this.user_full_name = user_full_name;
        this.user_employee_id = user_employee_id;
        this.assign_quantity = assign_quantity;
        this.req_user_id_details = req_user_id_details;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getReq_user_id() {
        return req_user_id;
    }

    public void setReq_user_id(String req_user_id) {
        this.req_user_id = req_user_id;
    }

    public String getReq_by_user_id() {
        return req_by_user_id;
    }

    public void setReq_by_user_id(String req_by_user_id) {
        this.req_by_user_id = req_by_user_id;
    }

    public String getReq_job_id() {
        return req_job_id;
    }

    public void setReq_job_id(String req_job_id) {
        this.req_job_id = req_job_id;
    }

    public String getSeam_number() {
        return seam_number;
    }

    public void setSeam_number(String seam_number) {
        this.seam_number = seam_number;
    }

    public String getReq_category_id() {
        return req_category_id;
    }

    public void setReq_category_id(String req_category_id) {
        this.req_category_id = req_category_id;
    }

    public String getReq_type_id() {
        return req_type_id;
    }

    public void setReq_type_id(String req_type_id) {
        this.req_type_id = req_type_id;
    }

    public String getReq_size_id() {
        return req_size_id;
    }

    public void setReq_size_id(String req_size_id) {
        this.req_size_id = req_size_id;
    }

    public String getReq_quantity() {
        return req_quantity;
    }

    public void setReq_quantity(String req_quantity) {
        this.req_quantity = req_quantity;
    }

    public String getReq_remark() {
        return req_remark;
    }

    public void setReq_remark(String req_remark) {
        this.req_remark = req_remark;
    }

    public String getReq_location_id() {
        return req_location_id;
    }

    public void setReq_location_id(String req_location_id) {
        this.req_location_id = req_location_id;
    }

    public String getReq_manager_id() {
        return req_manager_id;
    }

    public void setReq_manager_id(String req_manager_id) {
        this.req_manager_id = req_manager_id;
    }

    public String getReq_manager_comment() {
        return req_manager_comment;
    }

    public void setReq_manager_comment(String req_manager_comment) {
        this.req_manager_comment = req_manager_comment;
    }

    public String getReq_manager_status() {
        return req_manager_status;
    }

    public void setReq_manager_status(String req_manager_status) {
        this.req_manager_status = req_manager_status;
    }

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }

    public String getReq_updated_on() {
        return req_updated_on;
    }

    public void setReq_updated_on(String req_updated_on) {
        this.req_updated_on = req_updated_on;
    }

    public String getReq_created_on() {
        return req_created_on;
    }

    public void setReq_created_on(String req_created_on) {
        this.req_created_on = req_created_on;
    }

    public String getStock_type_name() {
        return stock_type_name;
    }

    public void setStock_type_name(String stock_type_name) {
        this.stock_type_name = stock_type_name;
    }

    public String getStock_size_name() {
        return stock_size_name;
    }

    public void setStock_size_name(String stock_size_name) {
        this.stock_size_name = stock_size_name;
    }

    public String getStock_category_name() {
        return stock_category_name;
    }

    public void setStock_category_name(String stock_category_name) {
        this.stock_category_name = stock_category_name;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getUser_employee_id() {
        return user_employee_id;
    }

    public void setUser_employee_id(String user_employee_id) {
        this.user_employee_id = user_employee_id;
    }

    public String getAssign_quantity() {
        return assign_quantity;
    }

    public void setAssign_quantity(String assign_quantity) {
        this.assign_quantity = assign_quantity;
    }

    public String getReq_user_id_details() {
        return req_user_id_details;
    }

    public void setReq_user_id_details(String req_user_id_details) {
        this.req_user_id_details = req_user_id_details;
    }
}
