package com.chargepoint.charging.model;

import lombok.Getter;

@Getter
public class Charger {
    private int id;
    private double rate;

    public Charger(int id, double rate) {
        this.id = id;
        this.rate = rate;
    }

}