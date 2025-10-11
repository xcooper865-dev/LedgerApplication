package com.pluralsight;

public class Payments {
private String Date;
private String Time;
private String Description;
private String Vendor;
private Double Amount;

    public Payments(double amount, String vendor, String description, String time, String date) {
        Amount = amount;
        Vendor = vendor;
        Description = description;
        Time = time;
        Date = date;
    }

    public Payments(String date, String description, String vendor, double amount) {
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
@Override
    public String toString(){
        return Date + ","+ Time + ","+ Description +","+ Vendor+ ","+Amount;
}

    }
