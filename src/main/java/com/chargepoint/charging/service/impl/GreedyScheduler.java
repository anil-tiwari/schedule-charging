package com.chargepoint.charging.service.impl;

import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;
import com.chargepoint.charging.service.ChargingScheduler;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("greedyScheduler")
public class GreedyScheduler implements ChargingScheduler {
    @Override
    public Map<Integer, List<Integer>> schedule(List<Truck> trucks, List<Charger> chargers, int availableHours) {
        Map<Integer, List<Integer>> schedule = new HashMap<>();
        Map<Integer, Double> chargerTime = new HashMap<>();

        for (Charger c : chargers) {
            schedule.put(c.getId(), new ArrayList<>());
            chargerTime.put(c.getId(), 0.0);
        }

        List<Truck> sortedTrucks = new ArrayList<>(trucks);
        sortedTrucks.sort(Comparator.comparingDouble(truck ->
                -((truck.getCapacity() - truck.getCurrentCharge()) / getMaxChargerRate(chargers)))
        );
//        trucks.sort(Comparator.comparingDouble(Truck::getRequiredCharge));

        for (Truck truck : sortedTrucks) {
            for (Charger charger : chargers) {
                double timeRequired = truck.getRequiredCharge() / charger.getRate();
                double usedTime = chargerTime.get(charger.getId());

                if (usedTime + timeRequired <= availableHours) {
                    chargerTime.put(charger.getId(), usedTime + timeRequired);
                    schedule.get(charger.getId()).add(truck.getId());
                    break;
                }
            }
        }

        return schedule;
    }

    private double getMaxChargerRate(List<Charger> chargers) {
        return chargers.stream()
                .mapToDouble(Charger::getRate)
                .max()
                .orElse(0.0); // fallback to 0 if list is empty
    }
}