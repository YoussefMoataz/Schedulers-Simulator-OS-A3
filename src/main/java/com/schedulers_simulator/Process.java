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
    private Integer waitingTime = -1;
    private Integer turnaroundTime = -1;
    private Integer remainingTime;

    public Process(Integer id, String name, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
        this.id = id;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.remainingTime = burstTime;
    }

    public Process(String name, Color color, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
        this.name = name;
        this.color = color;
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

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public Integer getTurnaroundTime() {
        return turnaroundTime;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public Integer getCompletionTime() {
        return this.arrivalTime + this.turnaroundTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setCompletionTime(Integer completionTime) {
        this.turnaroundTime = completionTime - this.arrivalTime;
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




