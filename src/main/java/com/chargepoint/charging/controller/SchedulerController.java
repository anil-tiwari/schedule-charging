package com.chargepoint.charging.controller;

import com.chargepoint.charging.ChargingStrategyType;
import com.chargepoint.charging.model.ChargingRequest;
import com.chargepoint.charging.service.ChargingScheduler;
import com.chargepoint.charging.strategy.SchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {

    @Autowired
    private SchedulerFactory schedulerFactory;

//    @PostMapping
//    public Map<Integer, List<Integer>> schedule(@RequestBody RequestDTO request) {
//        ChargingScheduler scheduler = new GreedyScheduler();
//        return scheduler.schedule(request.getTrucks(), request.getChargers(), request.getAvailableHours());
//    }

    @PostMapping
    public Map<Integer, List<Integer>> schedule(@RequestParam(defaultValue = "GREEDY") ChargingStrategyType strategy,
                                                @RequestBody ChargingRequest request) {
        ChargingScheduler scheduler = schedulerFactory.getScheduler(strategy);
        return scheduler.schedule(request.getTrucks(), request.getChargers(), request.getAvailableHours());
    }

//    public static class RequestDTO {
//        private List<Truck> trucks;
//        private List<Charger> chargers;
//        private int availableHours;
//
//        public List<Truck> getTrucks() { return trucks; }
//        public void setTrucks(List<Truck> trucks) { this.trucks = trucks; }
//
//        public List<Charger> getChargers() { return chargers; }
//        public void setChargers(List<Charger> chargers) { this.chargers = chargers; }
//
//        public int getAvailableHours() { return availableHours; }
//        public void setAvailableHours(int availableHours) { this.availableHours = availableHours; }
//    }
}