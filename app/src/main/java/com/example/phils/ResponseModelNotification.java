package com.example.phils;

public class ResponseModelNotification {
    String noti_id,noti_user_id,url_created,noti_message,noti_status;

    public ResponseModelNotification() {
    }

    public ResponseModelNotification(String noti_id, String noti_user_id, String url_created, String noti_message, String noti_status) {
        this.noti_id = noti_id;
        this.noti_user_id = noti_user_id;
        this.url_created = url_created;
        this.noti_message = noti_message;
        this.noti_status = noti_status;
    }

    public String getNoti_id() {
        return noti_id;
    }

    public void setNoti_id(String noti_id) {
        this.noti_id = noti_id;
    }

    public String getNoti_user_id() {
        return noti_user_id;
    }

    public void setNoti_user_id(String noti_user_id) {
        this.noti_user_id = noti_user_id;
    }

    public String getUrl_created() {
        return url_created;
    }

    public void setUrl_created(String url_created) {
        this.url_created = url_created;
    }

    public String getNoti_message() {
        return noti_message;
    }

    public void setNoti_message(String noti_message) {
        this.noti_message = noti_message;
    }

    public String getNoti_status() {
        return noti_status;
    }

    public void setNoti_status(String noti_status) {
        this.noti_status = noti_status;
    }
}
