package com.chargepoint.charging.model;

import lombok.Getter;

@Getter
public class Truck {
    private int id;
    private double capacity;
    private double currentCharge;

    public Truck(int id, double capacity, double currentCharge) {
        this.id = id;
        this.capacity = capacity;
        this.currentCharge = currentCharge;
    }

    public double getRequiredCharge() { return capacity - currentCharge; }
}
