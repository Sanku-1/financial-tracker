package com.pluralsight;

public class Transaction {
    public String date;
    public String time;
    public String purchase;
    public String vendorName;
    public double purchasePrice;

    public Transaction(String date, String time, String purchase, String vendorName, double purchasePrice){
        this.date = date;
        this.time = time;
        this.purchase = purchase;
        this.vendorName = vendorName;
        this.purchasePrice = purchasePrice;
    }

}
