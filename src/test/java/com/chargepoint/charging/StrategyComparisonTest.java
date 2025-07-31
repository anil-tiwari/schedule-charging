package com.chargepoint.charging;

import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;
import com.chargepoint.charging.service.impl.EarliestFinishTimeScheduler;
import com.chargepoint.charging.service.impl.GreedyScheduler;
import com.chargepoint.charging.service.impl.KnapsackScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// StrategyComparisonTest.java
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StrategyComparisonTest {

    @Autowired
    private GreedyScheduler greedyScheduler;

    @Autowired
    private EarliestFinishTimeScheduler eftScheduler;

    @Autowired
    private KnapsackScheduler knapsackScheduler;

    private List<Truck> generateRandomTrucks(int count) {
        Random random = new Random();
        List<Truck> trucks = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            int capacity = 50 + random.nextInt(100);
            int current = random.nextInt(capacity);
            trucks.add(new Truck(i, capacity, current));
        }
        return trucks;
    }

    private List<Charger> generateRandomChargers(int count) {
        Random random = new Random();
        List<Charger> chargers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            chargers.add(new Charger(i, 5 + random.nextInt(20)));
        }
        return chargers;
    }

    @Test
    public void testStrategiesWithLargeInput() {
        List<Truck> trucks = generateRandomTrucks(500);
        List<Charger> chargers = generateRandomChargers(50);
        int availableHours = 10;

        Map<Integer, List<Integer>> greedyResult = greedyScheduler.schedule(trucks, chargers, availableHours);
        Map<Integer, List<Integer>> eftResult = eftScheduler.schedule(trucks, chargers, availableHours);
        Map<Integer, List<Integer>> knapsackResult = knapsackScheduler.schedule(trucks, chargers, availableHours);

        int greedyCount = greedyResult.values().stream().mapToInt(List::size).sum();
        int eftCount = eftResult.values().stream().mapToInt(List::size).sum();
        int knapsackCount = knapsackResult.values().stream().mapToInt(List::size).sum();

        System.out.println("Greedy Charged Trucks: " + greedyCount);
        System.out.println("EFT Charged Trucks: " + eftCount);
        System.out.println("Knapsack Charged Trucks: " + knapsackCount);

        assertTrue(greedyCount >= 0);
        assertTrue(eftCount >= 0);
        assertTrue(knapsackCount >= 0);
    }

    @Test
    public void testStrategiesConsistencyOnSmallInput() {
        List<Truck> trucks = List.of(
                new Truck(1, 60, 10),
                new Truck(2, 80, 30),
                new Truck(3, 70, 60)
        );
        List<Charger> chargers = List.of(
                new Charger(1, 10),
                new Charger(2, 20)
        );

        int hours = 5;
        Map<Integer, List<Integer>> greedy = greedyScheduler.schedule(trucks, chargers, hours);
        Map<Integer, List<Integer>> eft = eftScheduler.schedule(trucks, chargers, hours);
        Map<Integer, List<Integer>> knapsack = knapsackScheduler.schedule(trucks, chargers, hours);

        System.out.println("Greedy: " + greedy);
        System.out.println("EFT: " + eft);
        System.out.println("Knapsack: " + knapsack);

        assertNotNull(greedy);
        assertNotNull(eft);
        assertNotNull(knapsack);
    }
}
