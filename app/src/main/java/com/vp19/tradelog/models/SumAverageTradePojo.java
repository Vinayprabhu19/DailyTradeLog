package com.vp19.tradelog.models;

public class SumAverageTradePojo
{ 
    public float totalProfit;
    public float totalBrokerage;

    public float getTotalBrokerage() {
        return totalBrokerage;
    }

    public void setTotalBrokerage(float totalBrokerage) {
        this.totalBrokerage = totalBrokerage;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }
}