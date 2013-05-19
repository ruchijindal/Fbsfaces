/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.fbs;

/**
 *
 * @author smp7
 */
public class ParkingInfo {
    private int parkingTypeId;
    private String parkingType;
    private int availParking;

    public int getAvailParking() {
        return availParking;
    }

    public void setAvailParking(int availParking) {
        this.availParking = availParking;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public int getParkingTypeId() {
        return parkingTypeId;
    }

    public void setParkingTypeId(int parkingTypeId) {
        this.parkingTypeId = parkingTypeId;
    }
    

}
