package com.zybooks.parkingapp;

import java.util.ArrayList;

public class ParkingRepository {
    private static ParkingRepository instance;
    private ArrayList<Lot> parkingLots = new ArrayList<>();

    private ParkingRepository() {}

    public static synchronized ParkingRepository getInstance() {
        if (instance == null) {
            instance = new ParkingRepository();
        }
        return instance;
    }

    public ArrayList<Lot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(ArrayList<Lot> lots) {
        this.parkingLots = lots;
    }
}