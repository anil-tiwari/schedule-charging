package com.chargepoint.charging.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChargingRequest {
        private List<Truck> trucks;
        private List<Charger> chargers;
        private int availableHours;

//        public List<Truck> getTrucks() { return trucks; }
//        public void setTrucks(List<Truck> trucks) { this.trucks = trucks; }
//
//        public List<Charger> getChargers() { return chargers; }
//        public void setChargers(List<Charger> chargers) { this.chargers = chargers; }
//
//        public int getAvailableHours() { return availableHours; }
//        public void setAvailableHours(int availableHours) { this.availableHours = availableHours; }
    }