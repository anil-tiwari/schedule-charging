package com.chargepoint.charging;

import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;
import com.chargepoint.charging.service.impl.GreedyScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GreedySchedulerTest {
    @Test
    public void testSchedule() {
        GreedyScheduler scheduler = new GreedyScheduler();

        List<Truck> trucks = List.of(
            new Truck(1, 100, 20),
            new Truck(2, 100, 90),
            new Truck(3, 80, 10),
            new Truck(4, 60, 0)
        );

        List<Charger> chargers = List.of(
            new Charger(1, 10),
            new Charger(2, 20)
        );

        Map<Integer, List<Integer>> result = scheduler.schedule(trucks, chargers, 5);

        assertTrue(result.containsKey(1));
        assertTrue(result.containsKey(2));
    }
}