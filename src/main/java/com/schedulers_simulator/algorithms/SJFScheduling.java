package com.schedulers_simulator.algorithms;

import com.schedulers_simulator.Process;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJFScheduling implements Algorithm {
    private int contextSwitchTime;
    private List<Process> processes;

    public SJFScheduling() {
        this.contextSwitchTime = 0;
        this.processes = new ArrayList<>();
        assignProcesses();
    }

    public void assignProcesses() {
        processes.add(new Process(1, "P1", 0, 4, 0));
        processes.add(new Process(2, "P2", 1, 8, 0));
        processes.add(new Process(3, "P3", 3, 2, 0));
        processes.add(new Process(4, "P4", 10, 6, 0));
        processes.add(new Process(5, "P5", 12, 3, 0));
        contextSwitchTime = 0;
    }

    @Override
    public void run() {
        List<Process> waitingProcesses = new ArrayList<>();
        List<Process> completedProcesses = new ArrayList<>();

        for (Process process : processes) {
            waitingProcesses.add(process);
        }

        int currentTime = 0;
        while (!waitingProcesses.isEmpty()) {
            waitingProcesses.sort(Comparator.comparingInt(Process::getArrivalTime));

            Process min = null;
            for (Process process : waitingProcesses) {
                if (process.getArrivalTime() <= currentTime) {
                    if (min == null || process.getBurstTime() < min.getBurstTime()) {
                        min = process;
                    }
                }
            }

            if (min != null) {
                waitingProcesses.remove(min);
                completedProcesses.add(min);
                currentTime += min.getBurstTime() + contextSwitchTime;
            } else {
                currentTime++;
            }
        }

        double totalBurst = 0;
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        for (Process process : completedProcesses) {
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

        System.out.println("New List Using Shortest Job First: \n" + completedProcesses + "\n");
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime / completedProcesses.size());
        System.out.println("Average Waiting Time: " + totalWaitingTime / completedProcesses.size());
    }

    public static void main(String[] args) {
        SJFScheduling sjf = new SJFScheduling();
        sjf.run();
    }
}
