/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.chart;

/**
 *
 * @author smp
 */
public class ProjectBooking {

    String projectname;
    long unbooked;
    long book;
    long transfer;
    Long flatId;
    long numberof;

    public Long getFlatId() {
        return flatId;
    }

    public void setFlatId(Long flatId) {
        this.flatId = flatId;
    }

    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public long getTransfer() {
        return transfer;
    }

    public void setTransfer(long transfer) {
        this.transfer = transfer;
    }

    public long getUnbooked() {
        return unbooked;
    }

    public void setUnbooked(long unbooked) {
        this.unbooked = unbooked;
    }

    public long getNumberof() {
        return numberof;
    }

    public void setNumberof(long numberof) {
        this.numberof = numberof;
    }


    
}
