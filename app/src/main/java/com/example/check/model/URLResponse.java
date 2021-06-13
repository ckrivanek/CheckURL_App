package com.example.check.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class URLResponse {
    public ArrayList<URL> data;
    public int status;
    public String error;

    public URLResponse(ArrayList<URL> data, int status, String error) {
        this.data = data;
        this.status = status;
        this.error = error;
    }
}
