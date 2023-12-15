package com.schedulers_simulator.algorithms;

import com.schedulers_simulator.Process;

import java.util.*;

public class SRTFScheduling extends Algorithm {
    private PriorityQueue<Process> readyQueue;
    private List<String> runningQueue;
    private Queue<Process> processesCopy;
    private int currentTime;
    private int totalWaitingTime;
    private int totalTurnaroundTime;

    public SRTFScheduling() {
        this.readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));
        this.runningQueue = new ArrayList<>();
        this.currentTime = 0;
        this.totalWaitingTime = 0;
        this.totalTurnaroundTime = 0;
//        createSampleList();
        this.processesCopy = new PriorityQueue<>(processes);
    }

    public void run() {
        Process lastProcess = null;
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.peek().getArrivalTime() == currentTime) {
                readyQueue.add(processes.poll());
            }
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                if (lastProcess == null || !lastProcess.getName().equals(currentProcess.getName())) {
                    runningQueue.add(currentProcess.getName());
                }
                lastProcess = currentProcess;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime + 1);
                    currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                    totalWaitingTime += currentProcess.getWaitingTime();
                    totalTurnaroundTime += currentProcess.getTurnaroundTime();
                } else {
                    readyQueue.add(currentProcess);
                }
            }
            currentTime++;
        }
        printResults();
    }

    public void printResults() {
        System.out.println("Processes execution order:");
        for (String processName : runningQueue) {
            System.out.println(processName);
        }

        System.out.println("\nWaiting time for each process:");
        for (Process process : processesCopy) {
            System.out.println(process.getName() + ": " + process.getWaitingTime());
        }

        System.out.println("\nTurnaround time for each process:");
        for (Process process : processesCopy) {
            System.out.println(process.getName() + ": " + process.getTurnaroundTime());
        }

        int numProcesses = processesCopy.size();
        System.out.println("\nAverage waiting time: " + (double) totalWaitingTime / numProcesses);
        System.out.println("Average turnaround time: " + (double) totalTurnaroundTime / numProcesses);
    }

//    private void createSampleList() {
////        processes.add(new Process(1, "P1", 0, 7, 1));
////        processes.add(new Process(2, "P2", 2, 5, 1));
////        processes.add(new Process(3, "P3", 4, 3, 1));
////        processes.add(new Process(4, "P4", 6, 4, 1));
////        processes.add(new Process(5, "P5", 8, 1, 1));
//        processes.add(new Process(1, "P1", 0, 1, 1));
//        processes.add(new Process(2, "P2", 1, 7, 1));
//        processes.add(new Process(3, "P3", 2, 3, 1));
//        processes.add(new Process(4, "P4", 3, 6, 1));
//        processes.add(new Process(5, "P5", 4, 5, 1));
//        processes.add(new Process(6, "P6", 5, 15, 1));
//        processes.add(new Process(7, "P7", 15, 8, 1));
//    }

    public static void main(String[] args) {
        Algorithm algorithm = new SRTFScheduling();
        algorithm.run();
    }
}
