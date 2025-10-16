package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Search {
    public LocalDate startDate;
    public LocalDate endDate;
    public String description;
    public String vendor;
    public double amount;
    public boolean after;

    public Search(LocalDate startDate, LocalDate endDate, String description, String vendor, double amount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.after = false;
    }
    public Search(LocalDate startDate, LocalDate endDate, String description, String vendor) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.vendor = vendor;
        this.after = false;
        this.amount = 0;
    }
    public Search(LocalDate endDate, String description, String vendor, double amount) {
        this.startDate = null;
        this.endDate = endDate;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.after = false;
    }
    public Search(LocalDate startDate, String description, String vendor, double amount, boolean after) {
        this.startDate = startDate;
        this.endDate = null;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.after = after;
    }
    public Search(LocalDate startDate, LocalDate endDate, String vendor, double amount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = "";
        this.vendor = vendor;
        this.amount = amount;
        this.after = false;
    }
    public Search(LocalDate startDate, LocalDate endDate, double amount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = "";
        this.vendor = "";
        this.amount = amount;
        this.after = false;
    }
    public Search(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = "";
        this.vendor = "";
        this.amount = 0;
        this.after = false;
    }
    public Search(LocalDate startDate, boolean after) {
        this.startDate = startDate;
        this.endDate = null;
        this.description = "";
        this.vendor = "";
        this.amount = 0;
        this.after = after;
    }
    public Search(LocalDate endDate) {
        this.startDate = null;
        this.endDate = endDate;
        this.description = "";
        this.vendor = "";
        this.amount = 0;
        this.after = false;
    }
    public Search(LocalDate endDate, String vendor) {
        this.startDate = null;
        this.endDate = endDate;
        this.description = "";
        this.vendor = vendor;
        this.amount = 0;
        this.after = false;
    }
    public Search(String description, LocalDate endDate) {
        this.startDate = null;
        this.endDate = endDate;
        this.description = description;
        this.vendor = "";
        this.amount = 0;
        this.after = false;
    }
    public Search(LocalDate endDate, double amount) {
        this.startDate = null;
        this.endDate = endDate;
        this.description = "";
        this.vendor = "";
        this.amount = amount;
        this.after = false;
    }
    public Search(String description, LocalDate endDate, double amount) {
        this.startDate = null;
        this.endDate = endDate;
        this.description = description;
        this.vendor = "";
        this.amount = amount;
        this.after = false;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}