package com.zybooks.parkingapp;

public class Lot {
    private String name;
    private String status;

    // This "Constructor" lets you create a lot like: new Lot("North Lot", "Full")
    public Lot(String name, String status) {
        this.name = name;
        this.status = status;
    }

    // "Getters" allow the Adapter to read the data
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}