package com.chargepoint.charging.service;


import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;

import java.util.List;
import java.util.Map;

public interface ChargingScheduler {
    Map<Integer, List<Integer>> schedule(List<Truck> trucks, List<Charger> chargers, int availableHours);
}