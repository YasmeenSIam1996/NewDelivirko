package com.ict.delivirko.Module.pilot;

import java.io.Serializable;

public class Pilot_bill implements Serializable {
    private int workHours;
    private int rating;
    private int countOrders;
    private double countPoints;
    private float acceptanceRate;
    private float completedRate;
    private int rejectOrder;
    private int countTargets;
    private int countTargetsMoney;
    private int officePayment;
    private int electronicPaymentService;
    private int bag;
    private int guarantee;
    private double wallet;
    private double revenue;
    private double amount_cash;
    private String from;
    private String to;


    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getWorkHours() {
        return workHours;
    }

    public int getRating() {
        return rating;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public double getCountPoints() {
        return countPoints;
    }

    public float getAcceptanceRate() {
        return acceptanceRate;
    }

    public float getCompletedRate() {
        return completedRate;
    }

    public int getRejectOrder() {
        return rejectOrder;
    }

    public int getCountTargets() {
        return countTargets;
    }

    public int getCountTargetsMoney() {
        return countTargetsMoney;
    }

    public int getOfficePayment() {
        return officePayment;
    }

    public int getElectronicPaymentService() {
        return electronicPaymentService;
    }

    public int getBag() {
        return bag;
    }

    public int getGuarantee() {
        return guarantee;
    }

    public double getWallet() {
        return wallet;
    }

    public double getRevenue() {
        return revenue;
    }

    public double getAmount_cash() {
        return amount_cash;
    }
}
