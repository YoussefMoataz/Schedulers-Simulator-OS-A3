package com.schedulers_simulator;

import com.schedulers_simulator.algorithms.Algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Process {
    private Integer id;
    private String name;
    private Color color;
    private Integer arrivalTime;
    private Integer burstTime;
    private Integer priorityNumber;

    public Process(Integer id, String name, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
        this.id = id;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getBurstTime() {
        return burstTime;
    }

    public Integer getPriorityNumber() {
        return priorityNumber;
    }
}




