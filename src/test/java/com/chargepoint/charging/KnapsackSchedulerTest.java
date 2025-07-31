package com.chargepoint.charging;

import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;
import com.chargepoint.charging.service.impl.KnapsackScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class KnapsackSchedulerTest {

    @Autowired
    private KnapsackScheduler scheduler;

    @Test
    public void testBasicKnapsackSchedule() {
        List<Truck> trucks = List.of(
                new Truck(1, 50, 10),
                new Truck(2, 80, 30),
                new Truck(3, 70, 40)
        );
        List<Charger> chargers = List.of(new Charger(1, 15));

        Map<Integer, List<Integer>> result = scheduler.schedule(trucks, chargers, 5);
        assertTrue(result.get(1).size() <= trucks.size());
    }

    @Test
    public void testNoTrucks() {
        List<Truck> trucks = List.of();
        List<Charger> chargers = List.of(new Charger(1, 10));
        Map<Integer, List<Integer>> result = scheduler.schedule(trucks, chargers, 5);
        assertTrue(result.getOrDefault(1, List.of()).isEmpty());
    }

    @Test
    public void testLargeEnergyRequirement() {
        List<Truck> trucks = List.of(new Truck(1, 1000, 0));
        List<Charger> chargers = List.of(new Charger(1, 10));
        Map<Integer, List<Integer>> result = scheduler.schedule(trucks, chargers, 5);
        assertTrue(result.getOrDefault(1, List.of()).isEmpty());
    }
}