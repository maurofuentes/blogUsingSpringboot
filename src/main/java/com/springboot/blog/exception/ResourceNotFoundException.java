package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String msg;
    private Long resourceId;

    public ResourceNotFoundException(String msg, Long resourceId) {
        super(String.format("%s with id: %s", msg, resourceId));
        this.msg = msg;
        this.resourceId = resourceId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "ResourceNotFoundException [msg=" + msg + ", resourceId=" + resourceId + "]";
    }

}
