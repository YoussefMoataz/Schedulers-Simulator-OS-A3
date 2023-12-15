//package com.schedulers_simulator;
//
//import java.awt.*;
//
//public class Process {
//    private Integer id;
//    private String name;
//    private Color color;
//    private Integer arrivalTime;
//    private Integer burstTime;
//    private Integer priorityNumber;
//    private Integer waitingTime = -1;
//    private Integer turnaroundTime = -1;
//    private Integer remainingTime;
//    private Integer quantum = -1;
//    private Integer agFactor = -1;
//
//    private Color[] PROCESS_COLORS = {
//            Color.decode("#ff4747"), // red
//            Color.decode("#af73f0"), // purple
//            Color.PINK, // pink
//            Color.LIGHT_GRAY, // gray
//            Color.decode("#a1ffd9"), // teal
//            Color.YELLOW, // yellow
//            Color.ORANGE, // orange
//    };
//
//    public Process(Integer id, String name, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
//        this.id = id;
//        this.name = name;
//        this.arrivalTime = arrivalTime;
//        this.burstTime = burstTime;
//        this.priorityNumber = priorityNumber;
//        this.remainingTime = burstTime;
//    }
//
//    public Process(String name, Color color, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
//        this.name = name;
//        this.color = color;
//        this.arrivalTime = arrivalTime;
//        this.burstTime = burstTime;
//        this.priorityNumber = priorityNumber;
//        this.remainingTime = burstTime;
//    }
//
//    public Process(String name, String color, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
//        this.name = name;
////        this.color = color;
//        this.arrivalTime = arrivalTime;
//        this.burstTime = burstTime;
//        this.priorityNumber = priorityNumber;
//        this.remainingTime = burstTime;
//    }
//
//    public Process(String name, String color, Integer arrivalTime, Integer burstTime, Integer priorityNumber, Integer quantum) {
//        this.name = name;
////        this.color = color;
//        this.arrivalTime = arrivalTime;
//        this.burstTime = burstTime;
//        this.priorityNumber = priorityNumber;
//        this.quantum = quantum;
//        this.remainingTime = burstTime;
//    }
//
//    public Process(String name, String color, Integer arrivalTime, Integer burstTime, Integer priorityNumber, Integer quantum, Integer agFactor) {
//        this.name = name;
//        this.color = getColorByName(color);
//        this.arrivalTime = arrivalTime;
//        this.burstTime = burstTime;
//        this.priorityNumber = priorityNumber;
//        this.quantum = quantum;
//        this.remainingTime = burstTime;
//        this.agFactor = agFactor;
//    }
//
//    public Process(String name, Integer color, Integer arrivalTime, Integer burstTime, Integer priorityNumber, Integer quantum, Integer agFactor) {
//        this.name = name;
//        this.color = PROCESS_COLORS[color];
//        this.arrivalTime = arrivalTime;
//        this.burstTime = burstTime;
//        this.priorityNumber = priorityNumber;
//        this.quantum = quantum;
//        this.remainingTime = burstTime;
//        this.agFactor = agFactor;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Color getColor() {
//        return color;
//    }
//
//    public Integer getArrivalTime() {
//        return arrivalTime;
//    }
//
//    public Integer getBurstTime() {
//        return burstTime;
//    }
//
//    public Integer getPriorityNumber() {
//        return priorityNumber;
//    }
//
//    public Integer incrementPriority() {
//        if (priorityNumber == 1) {
//            return priorityNumber;
//        }
//        return --priorityNumber;
//    }
//
//    public Integer getWaitingTime() {
//        return waitingTime;
//    }
//
//    public Integer getTurnaroundTime() {
//        return turnaroundTime;
//    }
//
//    public Integer getRemainingTime() {
//        return remainingTime;
//    }
//
//    public Integer getCompletionTime() {
//        return this.arrivalTime + this.turnaroundTime;
//    }
//
//    public void setRemainingTime(Integer remainingTime) {
//        this.remainingTime = remainingTime;
//    }
//
//    public void decrementRemainingTime() {
//        this.remainingTime--;
//    }
//
//    public void setCompletionTime(Integer completionTime) {
//        this.turnaroundTime = completionTime - this.arrivalTime;
//    }
//
//    public void setWaitingTime(Integer waitingTime) {
//        this.waitingTime = waitingTime;
//    }
//
//    public void setTurnaroundTime(Integer turnaroundTime) {
//        this.turnaroundTime = turnaroundTime;
//    }
//
//    public Integer getQuantum() {
//        return quantum;
//    }
//
//    public void setQuantum(Integer quantum) {
//        this.quantum = quantum;
//    }
//
//    public Integer getAGFactor() {
//        return agFactor;
//    }
//
//    public void setAGFactor(Integer agFactor) {
//        this.agFactor = agFactor;
//    }
//
//    public Color getColorByName(String name) {
//        name = name.toLowerCase();
//        if (name.contains("red")) {
//            return this.PROCESS_COLORS[0];
//        } else if (name.contains("purple")) {
//            return this.PROCESS_COLORS[1];
//        } else if (name.contains("pink")) {
//            return this.PROCESS_COLORS[2];
//        } else if (name.contains("gray")) {
//            return this.PROCESS_COLORS[3];
//        } else if (name.contains("teal")) {
//            return this.PROCESS_COLORS[4];
//        } else if (name.contains("yellow")) {
//            return this.PROCESS_COLORS[5];
//        } else if (name.contains("orange")) {
//            return this.PROCESS_COLORS[6];
//        } else {
//            return Color.white;
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Process{" +
//                "name='" + name + '\'' +
//                ", arrivalTime=" + arrivalTime +
//                ", burstTime=" + burstTime +
//                ", priorityNumber=" + priorityNumber +
//                ", waitingTime=" + waitingTime +
//                ", turnaroundTime=" + turnaroundTime +
//                ", remainingTime=" + remainingTime +
//                ", agFactor=" + agFactor +
//                '}' + '\n';
//    }
//}
