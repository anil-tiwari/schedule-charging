package com.chargepoint.charging;

import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;
import com.chargepoint.charging.service.impl.EarliestFinishTimeScheduler;
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
public class EarliestFinishTimeSchedulerTest {

    @Autowired
    private EarliestFinishTimeScheduler scheduler;

    @Test
    public void testBasicSchedule() {
        List<Truck> trucks = List.of(
                new Truck(1, 100, 20),
                new Truck(2, 80, 10),
                new Truck(3, 60, 30)
        );
        List<Charger> chargers = List.of(
                new Charger(1, 10),
                new Charger(2, 15)
        );

        Map<Integer, List<Integer>> result = scheduler.schedule(trucks, chargers, 10);
        int totalCharged = result.values().stream().mapToInt(List::size).sum();
        assertTrue(totalCharged <= trucks.size());
    }

    @Test
    public void testNoChargers() {
        List<Truck> trucks = List.of(new Truck(1, 100, 50));
        List<Charger> chargers = List.of();
        Map<Integer, List<Integer>> result = scheduler.schedule(trucks, chargers, 5);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testZeroAvailableHours() {
        List<Truck> trucks = List.of(new Truck(1, 100, 10));
        List<Charger> chargers = List.of(new Charger(1, 20));
        Map<Integer, List<Integer>> result = scheduler.schedule(trucks, chargers, 0);
        assertTrue(result.isEmpty());
    }
}