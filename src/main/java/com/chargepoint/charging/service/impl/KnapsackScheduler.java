package com.chargepoint.charging.service.impl;

import com.chargepoint.charging.model.Charger;
import com.chargepoint.charging.model.Truck;
import com.chargepoint.charging.service.ChargingScheduler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("knapsackScheduler")
public class KnapsackScheduler implements ChargingScheduler {
    @Override
    public Map<Integer, List<Integer>> schedule(List<Truck> trucks, List<Charger> chargers, int hours) {
        Map<Integer, List<Integer>> result = new HashMap<>();

        for (Charger charger : chargers) {
            int capacity = hours * (int) charger.getRate();
            int n = trucks.size();
            int[] weight = new int[n];
            int[] value = new int[n];

            for (int i = 0; i < n; i++) {
                Truck t = trucks.get(i);
                weight[i] = (int) Math.ceil((t.getCapacity() - t.getCurrentCharge()));
                value[i] = weight[i];
            }

            boolean[] picked = knapsack(weight, value, capacity);

            for (int i = 0; i < n; i++) {
                if (picked[i]) {
                    result.computeIfAbsent(charger.getId(), k -> new ArrayList<>()).add(trucks.get(i).getId());
                }
            }
        }
        return result;
    }

    private boolean[] knapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (weight[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weight[i - 1]] + value[i - 1]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        boolean[] selected = new boolean[n];
        int w = W;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected[i - 1] = true;
                w -= weight[i - 1];
            }
        }
        return selected;
    }
}