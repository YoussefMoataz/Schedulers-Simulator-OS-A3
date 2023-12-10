package com.schedulers_simulator.algorithms;

import com.schedulers_simulator.Process;

import java.util.*;

public class SRTFScheduling {
    private List<Process> processes;
    private PriorityQueue<Process> readyQueue;
    private int currentTime;
    private int numProcesses;
    private int totalWaitingTime;
    private int totalTurnaroundTime;

    public SRTFScheduling(List<Process> processes) {
        this.processes = processes;
        this.numProcesses = processes.size();
        this.readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));
        this.currentTime = 0;
        this.totalWaitingTime = 0;
        this.totalTurnaroundTime = 0;
    }

    public void run() {
        int completedProcesses = 0;
        while (completedProcesses < numProcesses) {
            for (Process process : processes) {
                if (process.getArrivalTime() == currentTime) {
                    readyQueue.add(process);
                }
            }
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime + 1);
                    currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                    totalWaitingTime += currentProcess.getWaitingTime();
                    totalTurnaroundTime += currentProcess.getTurnaroundTime();
                    completedProcesses++;
                } else {
                    readyQueue.add(currentProcess);
                }
            }
            currentTime++;
        }
    }

    public void printResults() {
        System.out.println("Processes execution order:");
        List<Process> completedProcesses = new ArrayList<>();
        for (Process process : processes) {
            if (process.getCompletionTime() != null) {
                completedProcesses.add(process);
            }
        }
        completedProcesses.sort(Comparator.comparing(Process::getCompletionTime));
        for (Process process : completedProcesses) {
            System.out.println(process.getName());
        }
        System.out.println("Waiting time for each process:");
        for (Process process : processes) {
            System.out.println(process.getName() + ": " + process.getWaitingTime());
        }
        System.out.println("Turnaround time for each process:");
        for (Process process : processes) {
            System.out.println(process.getName() + ": " + process.getTurnaroundTime());
        }
        System.out.println("Average waiting time: " + (double) totalWaitingTime / numProcesses);
        System.out.println("Average turnaround time: " + (double) totalTurnaroundTime / numProcesses);
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
//        processes.add(new Process(1, "P1", 0, 7, 1));
//        processes.add(new Process(2, "P2", 2, 5, 1));
//        processes.add(new Process(3, "P3", 4, 3, 1));
//        processes.add(new Process(4, "P4", 6, 4, 1));
//        processes.add(new Process(5, "P5", 8, 1, 1));
        processes.add(new Process(1, "P1", 0, 1, 1));
        processes.add(new Process(2, "P2", 1, 7, 1));
        processes.add(new Process(3, "P3", 2, 3, 1));
        processes.add(new Process(4, "P4", 3, 6, 1));
        processes.add(new Process(5, "P5", 4, 5, 1));
        processes.add(new Process(6, "P6", 5, 15, 1));
        processes.add(new Process(7, "P7", 15, 8, 1));

        SRTFScheduling scheduler = new SRTFScheduling(processes);
        scheduler.run();
        scheduler.printResults();
    }
}
