package com.zybooks.parkingapp;

import com.google.gson.annotations.SerializedName;

public class Lot {
    @SerializedName("lot_name")
    private String lotName;

    @SerializedName("used_spaces")
    private int UsedSpaces;

    @SerializedName("total_spaces")
    private int TotalSpaces;

    @SerializedName("available_spots")
    private int AvailaleSpots;

    @SerializedName("camera_id")
    private int CamId;
    private double latitude;

    private double longitude;

    /**
     * Constructor for the Lot class
     */
    public Lot(String lotName, int UsedSpaces, int TotalSpaces , double lat, double lon) {
        this.lotName = lotName;
        this.UsedSpaces = UsedSpaces;
        this.TotalSpaces = TotalSpaces;
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Get the name of the lot
     *
     * @return lot name
     */
    public String getLotName() {
        return lotName;
    }

    /**
     * Get the ID of the lot (using name as ID)
     *
     * @return lot name
     */
    public String getLotId() {
        return lotName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    /**
     * Set the latitude of the lot
     * @param latitude
     */
    public void setLatitude(double latitude){this.latitude = latitude;}

    /**
     * Set the longitude of the lot
     * @param longitude
     */
    public void setLongitude(double longitude){this.longitude = longitude;}


    /**
     * Set the name of the lot
     */
    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    /**
     * getLot_number
     * @return lot_number
     */
    public int getLotnumber() {
        return UsedSpaces;
    }

    /**
     * setLot_number
     * @param lotnumber
     */
    public void setLotnumber(int lotnumber) {
        this.UsedSpaces = lotnumber;
    }

    /**
     * get total_lot_number
     *
     * @return total_lot_number
     */
    public int getTotalLotnumber() {
        return TotalSpaces;
    }

    /**
     * set total_lot_number
     *
     * @param totalLotnumber
     */
    public void setTotalLotnumber(int totalLotnumber) {
        this.TotalSpaces = totalLotnumber;
    }

    /**
     * Get Status of the lot is the method that
     * provide how many spot are available in the lot
     * out of the full capacity
     *
     * @return lot number /total number lot
     */
    public String getStatus() {
        return  UsedSpaces + " / " + TotalSpaces;
    }

    /*Optional isFull() and isEmpty() this would be boolean function that check if the lot is full or empty
     */

    // using logic >= for security and testing
    public boolean isFull() {
        return UsedSpaces >= TotalSpaces;
    }

    public boolean isEmpty() {

        return UsedSpaces == 0;
    }

    public double getFillPercentage() {
        if (TotalSpaces == 0) return 0;
        return (double) UsedSpaces / TotalSpaces;
    }
}
