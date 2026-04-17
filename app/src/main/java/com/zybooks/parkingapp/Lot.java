package com.zybooks.parkingapp;

public class Lot {
    private String lotName;
    private int lotnumber;
    private int totalLotnumber;
    private double[] location;

    /**
     * Constructor for the Lot class
     */
    public Lot(String lotName, int lotnumber, int totalLotnumber , double lat, double lon) {
        this.lotName = lotName;
        this.lotnumber = lotnumber;
        this.totalLotnumber = totalLotnumber;
        this.location = new double[]{lat,lon};
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

    public double[] getLocation() {
        return location;
    }

    /**
     * Set the location of the lot
     * @param location
     */
    public void setLocation(double[] location) {
        this.location = location;
    }


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
        return lotnumber;
    }

    /**
     * setLot_number
     * @param lotnumber
     */
    public void setLotnumber(int lotnumber) {
        this.lotnumber = lotnumber;
    }

    /**
     * get total_lot_number
     *
     * @return total_lot_number
     */
    public int getTotalLotnumber() {
        return totalLotnumber;
    }

    /**
     * set total_lot_number
     *
     * @param totalLotnumber
     */
    public void setTotalLotnumber(int totalLotnumber) {
        this.totalLotnumber = totalLotnumber;
    }

    /**
     * Get Status of the lot is the method that
     * provide how many spot are available in the lot
     * out of the full capacity
     *
     * @return lot number /total number lot
     */
    public String getStatus() {
        return  lotnumber + " / " + totalLotnumber;
    }

    /*Optional isFull() and isEmpty() this would be boolean function that check if the lot is full or empty
     */

    // using logic >= for security and testing
    public boolean isFull() {
        return lotnumber >= totalLotnumber;
    }

    public boolean isEmpty() {

        return lotnumber == 0;
    }

}
