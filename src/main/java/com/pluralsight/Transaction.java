package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction implements Comparable<Transaction>{
    public LocalDate date;
    public LocalTime time;
    public String description;
    public String vendor;
    public double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount){
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }


    @Override
    public int compareTo(Transaction other) {
        int cmp = other.date.compareTo(this.date);
        if (cmp == 0) {
            return other.time.compareTo(this.time);
        }
        return cmp;
    }
}
