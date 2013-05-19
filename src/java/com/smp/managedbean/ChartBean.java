/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.managedbean;

import com.smp.chart.Booking;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author smp
 */
@ManagedBean(name="chartBean")
@SessionScoped
public class ChartBean implements Serializable {

	private List<Booking> booking;

	public ChartBean() {
		booking = new ArrayList<Booking>();
		booking.add(new Booking(2004, 120, 52, 50));
		booking.add(new Booking(2005, 100, 60, 50));
		booking.add(new Booking(2006, 44, 110, 50));
		booking.add(new Booking(2007, 150, 135, 50));
		booking.add(new Booking(2008, 125, 120, 50));
                booking.add(new Booking(2009, 200, 110, 50));
		booking.add(new Booking(2010, 150, 135, 50));
		booking.add(new Booking(2011, 125, 120, 50));
	}

	public List<Booking> getBirths() {
		return booking;
	}
}
