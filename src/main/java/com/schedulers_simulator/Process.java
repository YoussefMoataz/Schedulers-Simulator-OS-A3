package com.schedulers_simulator;

import java.awt.*;

public class Process {
    private Integer id;
    private String name;
    private Color color;
    private Integer arrivalTime;
    private Integer burstTime;
    private Integer priorityNumber;
    private Integer waitingTime = 0;
    private Integer turnaroundTime = 0;

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

    public Integer incrementPriority(){
        return ++priorityNumber;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(Integer turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", priorityNumber=" + priorityNumber +
                ", waitingTime=" + waitingTime +
                ", turnaroundTime=" + turnaroundTime +
                '}' + '\n';
    }
}
