package com.pluralsight;

public class Payments {
private String Date;
private String Time;
private String Description;
private String Vendor;
private Double Amount;

    public Payments(Double amount, String vendor, String description, String time, String date) {
        Amount = amount;
        Vendor = vendor;
        Description = description;
        Time = time;
        Date = date;
    }


    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getDescription() {
        return Description;
    }

    public String getVendor() {
        return Vendor;
    }

    public Double getAmount() {
        return Amount;
    }
}
