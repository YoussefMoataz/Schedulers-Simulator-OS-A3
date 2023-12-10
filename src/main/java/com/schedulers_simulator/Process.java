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




class SJF implements Algorithm {
    @Override
    public void run() {
    }
    public void shortestJobFirst(com.schedulers_simulator.Process[] processes, int contextSwitchTime) {
        // Created 2 Lists: waitingProcesses to add in it the given process
        // and completedProcesses to be the final answer after sorting the processes based on burst time

        List<com.schedulers_simulator.Process> waitingProcesses = new ArrayList<>();
        List<com.schedulers_simulator.Process> completedProcesses = new ArrayList<>();

        // Add processes to waitingProcesses list
        for (com.schedulers_simulator.Process process : processes) {
            waitingProcesses.add(process);
        }

        int currentTime = 0;
        while (!waitingProcesses.isEmpty()) {
            // Sort waitingProcesses based on arrival time
            waitingProcesses.sort(Comparator.comparingInt(com.schedulers_simulator.Process::getArrivalTime));

            com.schedulers_simulator.Process min = null; // The process with the minimum burst time initialized with null -has no value-
            for (com.schedulers_simulator.Process process : waitingProcesses) {
                // if the process arrival time is less than or equal to the current time
                if (process.getArrivalTime() <= currentTime) {
                    // if the minimum burst time is null OR the process burst time is less than the minimum burst time
                    if (min == null || process.getBurstTime() < min.getBurstTime()) {
                        min = process;
                    }
                }
            }

            if (min != null) {
                waitingProcesses.remove(min); // remove the process with the minimum burst time from waitingProcesses
                completedProcesses.add(min); // then add it to completedProcesses
                // Incrementing the current time by the burst time of the process and the context time
                currentTime += min.getBurstTime() + contextSwitchTime;
            } else { // To simulate the passage of time if there is no process to execute
                currentTime++;
            }
        }

        // Printing the turnaround time and waiting time for each process
        double totalBurst = 0;
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        for (com.schedulers_simulator.Process process : completedProcesses) {
            totalBurst += process.getBurstTime() + contextSwitchTime;
            double turnAroundTime = (totalBurst) - process.getArrivalTime();
            process.setTurnaroundTime((int) turnAroundTime);
            double waitingTime = turnAroundTime - process.getBurstTime();
            if (waitingTime < 0) {
                waitingTime = 0;
            }
            process.setWaitingTime((int) waitingTime);
            totalTurnaroundTime += turnAroundTime;
            totalWaitingTime += waitingTime;
        }

        // Print the list of completed processes
        System.out.println("New List Using Shortest Job First: \n" + completedProcesses + "\n");
        // Print the average turnaround time and waiting time for all processes
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime / completedProcesses.size());
        System.out.println("Average Waiting Time: " + totalWaitingTime / completedProcesses.size());
    }
}

