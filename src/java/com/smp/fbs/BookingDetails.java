/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.fbs;

/**
 *
 * @author smp
 */
public class BookingDetails {
    int month;
    int book;
    int transfer;
    int cancel;
    String block;

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public int getCancel() {
        return cancel;
    }

    public void setCancel(int cancel) {
        this.cancel = cancel;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
    

}
