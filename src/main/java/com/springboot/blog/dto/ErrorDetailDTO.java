package com.springboot.blog.dto;

import java.util.Date;

public class ErrorDetailDTO {

    private Date createdDate;

    private String message;

    private String detail;

    public ErrorDetailDTO(Date createdDate, String message, String detail) {
        this.createdDate = createdDate;
        this.message = message;
        this.detail = detail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
