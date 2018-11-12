package com.nobbyknox.absa.controllers;

import java.util.Date;

public class ErrorDetails {
    public Date timestamp;
    public String message;
    public String details;

    ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
