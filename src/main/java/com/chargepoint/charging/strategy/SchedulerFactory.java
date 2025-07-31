package com.chargepoint.charging.strategy;

import com.chargepoint.charging.ChargingStrategyType;
import com.chargepoint.charging.service.ChargingScheduler;
import com.chargepoint.charging.service.impl.EarliestFinishTimeScheduler;
import com.chargepoint.charging.service.impl.GreedyScheduler;
import com.chargepoint.charging.service.impl.KnapsackScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulerFactory {
    @Autowired
    private GreedyScheduler greedyScheduler;
    @Autowired
    private EarliestFinishTimeScheduler earliestFinishTimeScheduler;
    @Autowired
    private KnapsackScheduler knapsackScheduler;

    public ChargingScheduler getScheduler(ChargingStrategyType strategy) {
        return switch (strategy) {
            case EARLIEST_FINISH_TIME -> earliestFinishTimeScheduler;
            case KNAPSACK -> knapsackScheduler;
            case GREEDY -> greedyScheduler;
        };
    }
}