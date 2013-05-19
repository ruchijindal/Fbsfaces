/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.fbs;

/**
 *
 * @author smp7
 */
public class BlockInfo {
    public int blockId;
    public String blockName;
    public String noOfFloors;
    public String noOfFlats;
    public boolean status;
    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

  
    
    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getNoOfFlats() {
        return noOfFlats;
    }

    public void setNoOfFlats(String noOfFlats) {
        this.noOfFlats = noOfFlats;
    }

    public String getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(String noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
