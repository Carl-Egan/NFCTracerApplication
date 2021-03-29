package com.example.nfccontacttracing.models;

public class History {
    private String location;
    private String timestamp;
    private String description;

    private History(){
    }

    private History(String location, String timestamp, String description){
        this.location = location;
        this.timestamp = timestamp;
    }
    public String getLocation() {
        return location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
