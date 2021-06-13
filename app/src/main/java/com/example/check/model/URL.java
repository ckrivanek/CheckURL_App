package com.example.check.model;

public class URL {
    public int urlId;
    public String url;
    public String uuid;
    public String malicious;
    public String api;

    public URL(int urlId, String url, String uuid, String malicious, String api) {
        this.urlId = urlId;
        this.url = url;
        this.uuid = uuid;
        this.malicious = malicious;
        this.api = api;
    }

    public URL(){}
}
