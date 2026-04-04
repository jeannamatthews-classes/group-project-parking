package com.zybooks.parkingapp;

import javax.xml.transform.sax.SAXResult;

public class Lot {
    private String lotName;
    private int lot_number;
    private int total_lot_number;

    public Lot(String lotName, int lot_number, int total_lot_number) {
        this.lotName = lotName;
        this.lot_number = lot_number;
        this.total_lot_number = total_lot_number;
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
     * Set the name of the lot
     *
     * @param lotName
     */
    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    /**
     * getLot_number
     *
     * @return lot_number
     */
    public int getLot_number() {
        return lot_number;
    }

    /**
     * setLot_number
     *
     * @param lot_number
     */
    public void setLot_number(int lot_number) {
        this.lot_number = lot_number;
    }

    /**
     * get total_lot_number
     *
     * @return total_lot_number
     */
    public int getTotal_lot_number() {
        return total_lot_number;
    }

    /**
     * set total_lot_number
     *
     * @param total_lot_number
     */
    public void setTotal_lot_number(int total_lot_number) {
        this.total_lot_number = total_lot_number;
    }

    /**
     * Get Status of the lot is the method that
     * provide how many spot are avaliable in the lot
     * out of the full capacity
     *
     * @return lot number /total number lot
     */
    public String getStatus() {
        return "Lot Status: " + lot_number + "/" + total_lot_number;
    }



    /*Optional isFull() and isEmpty() this would be boolean function that check if the lot is full or empty
     */

    public boolean isFull() {

        // using logic >= for security and testing
    return lot_number >= total_lot_number;
    }

    public boolean isEmpty() {

        return lot_number == 0;
    }
}
