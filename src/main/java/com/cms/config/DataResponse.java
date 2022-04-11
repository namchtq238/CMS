package com.cms.config;

import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Collections;

public class DataResponse<E>{
    long total = 0L;

    boolean success;

    HttpStatus status;

    Collection<E> elements;

    public DataResponse() {
    }

    public DataResponse<E> setData(Collection<E> elements) {
        this.elements = (Collection)(elements == null ? Collections.emptyList() : elements);
        return this;
    }

    public Collection<E> getData() {
        return this.elements;
    }

    public DataResponse<E> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getTotal() {
        return this.success == true ? "true" : "false";
    }

    public String getStatus(){
        return this.status.toString();
    }

    public HttpStatus setStatus(HttpStatus status){
        this.status = status;
        return status;
    }

}
