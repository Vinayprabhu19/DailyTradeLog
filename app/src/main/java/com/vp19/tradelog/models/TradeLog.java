package com.vp19.tradelog.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TradeLog {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long key;

    private String date;
    private String dateString;

    private double profit;

    private double brokerage;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(double brokerage) {
        this.brokerage = brokerage;
    }

    @NonNull
    public Long getKey() {
        return key;
    }

    public void setKey(@NonNull Long key) {
        this.key = key;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
