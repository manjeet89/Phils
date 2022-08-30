package com.example.phils;

import com.google.gson.annotations.SerializedName;

public class ResponseModelUserProfile {

    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_full_name")
    private String user_full_name;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("user_employee_id")
    private String user_employee_id;

    @SerializedName("user_email_id")
    private String user_email_id;

    @SerializedName("user_phone_number")
    private String user_phone_number;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
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
}
