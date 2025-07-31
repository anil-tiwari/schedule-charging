package com.chargepoint.charging.service.impl;

import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;
import com.chargepoint.charging.service.ChargingScheduler;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("earliestFinishTimeScheduler")
public class EarliestFinishTimeScheduler implements ChargingScheduler {
    @Override
    public Map<Integer, List<Integer>> schedule(List<Truck> trucks, List<Charger> chargers, int hours) {
        PriorityQueue<ChargerSlot> pq = new PriorityQueue<>(Comparator.comparingDouble(cs -> cs.nextAvailableTime));
        chargers.forEach(c -> pq.add(new ChargerSlot(c, 0)));

        List<Truck> sorted = new ArrayList<>(trucks);
        sorted.sort(Comparator.comparingDouble(t -> (t.getCapacity() - t.getCurrentCharge())));

        Map<Integer, List<Integer>> result = new HashMap<>();

        for (Truck truck : sorted) {
            double neededEnergy = truck.getCapacity() - truck.getCurrentCharge();
            List<ChargerSlot> tried = new ArrayList<>();

            while (!pq.isEmpty()) {
                ChargerSlot cs = pq.poll();
                double chargingTime = neededEnergy / cs.charger.getRate();
                if (cs.nextAvailableTime + chargingTime <= hours) {
                    result.computeIfAbsent(cs.charger.getId(), k -> new ArrayList<>()).add(truck.getId());
                    cs.nextAvailableTime += chargingTime;
                    pq.add(cs);
                    break;
                }
                tried.add(cs);
            }
            pq.addAll(tried); // re-add all skipped chargers
        }

        return result;
    }

    private static class ChargerSlot {
        Charger charger;
        double nextAvailableTime;

        ChargerSlot(Charger charger, double nextAvailableTime) {
            this.charger = charger;
            this.nextAvailableTime = nextAvailableTime;
        }
    }
}