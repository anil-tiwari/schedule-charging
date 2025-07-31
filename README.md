# Electric Truck Charging Scheduler

This project provides a backend scheduling engine for optimally assigning electric trucks to chargers within a given time window. The goal is to charge **as many trucks as possible to full capacity**, using one of several pluggable scheduling algorithms.

Built using **Java Spring Boot** with clean modularization and tested with JUnit.

---

## 🧩 Key Features

* Supports **pluggable scheduling strategies**
* Unit-tested algorithms: Greedy, Earliest Finish Time (EFT), and Knapsack
* REST API for scheduling trucks
* Strategy selection via query parameter (`/schedule?strategy=EARLIEST_FINISH_TIME`)
* Performance benchmarking via test suite

---

## 🚚 Problem Statement

You are given:

* A list of **electric trucks** with:

    * ID
    * Battery capacity
    * Current charge level

* A list of **chargers**, each with a fixed charging rate (kW)

* A **number of hours** available to charge overnight

**Goal:** Maximize the number of trucks fully charged by the end of the time window, using available chargers efficiently.

---

## ⚙️ Strategies Implemented

### ✅ 1. Greedy Algorithm (Default)

* Sort trucks by time to charge (ascending)
* Assign to the fastest available charger
* **Pros:** Fast, simple
* **Cons:** May miss better global options

### ✅ 2. Earliest Finish Time (EFT)

* Prioritizes chargers finishing earliest
* Keeps chargers active with minimal idle time
* **Pros:** Good real-time utilization
* **Cons:** May skip smaller trucks if bigger ones finish earlier

### ✅ 3. Knapsack-Based

* Maximizes energy delivery using bounded knapsack principle
* Fits the best combination of trucks per charger based on available time
* **Pros:** More optimal in constrained environments
* **Cons:** Higher computational cost

---

## 🚀 REST API

### Endpoint

```http
POST /schedule?strategy=GREEDY
POST /schedule?strategy=EARLIEST_FINISH_TIME
POST /schedule?strategy=KNAPSACK
```

### Sample Request (JSON)

```json
{
  "trucks": [
    { "id": 1, "capacity": 100, "currentCharge": 20 },
    { "id": 2, "capacity": 80, "currentCharge": 30 },
    { "id": 3, "capacity": 120, "currentCharge": 90 },
    { "id": 4, "capacity": 90, "currentCharge": 0 },
    { "id": 5, "capacity": 100, "currentCharge": 0 }
  ],
  "chargers": [
    { "id": 1, "rate": 20 },
    { "id": 2, "rate": 30 },
    { "id": 3, "rate": 50 }
  ],
  "availableHours": 4
}

```

### Sample Response

```json
{
  "1": [
    3
  ],
  "2": [
    1
  ],
  "3": [
    2,
    4
  ]
}
```

This means charger 1 will charge trucks 3, charger 2 will charge trucks 1, and charger 3 will charge truck 2 & 4.

---
## 🔧 Prerequisites

- Java 17+
- Maven 3.x
---

---

## 🧪 Testing

Run unit tests:

```bash
mvn test
```

Includes:

* Normal test cases
* Edge/boundary scenarios
* Large randomized load testing
* Cross-strategy performance comparison

---

## 🏗️ Directory Structure

```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── chargepoint
│   │   │           └── charging
│   │   │               ├── ChargingStrategyType.java
│   │   │               ├── controller
│   │   │               │   └── SchedulerController.java
│   │   │               ├── model
│   │   │               │   ├── Charger.java
│   │   │               │   ├── ChargingRequest.java
│   │   │               │   └── Truck.java
│   │   │               ├── ScheduleChargingApplication.java
│   │   │               ├── service
│   │   │               │   ├── ChargingScheduler.java
│   │   │               │   └── impl
│   │   │               │       ├── EarliestFinishTimeScheduler.java
│   │   │               │       ├── GreedyScheduler.java
│   │   │               │       └── KnapsackScheduler.java
│   │   │               └── strategy
│   │   │                   └── SchedulerFactory.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── chargepoint
│                   └── charging
│                       ├── EarliestFinishTimeSchedulerTest.java
│                       ├── GreedySchedulerTest.java
│                       ├── KnapsackSchedulerTest.java
│                       └── StrategyComparisonTest.java
```

---

## 👨‍💻 Author

Built by \[Anil Kumar Tiwari]

---
