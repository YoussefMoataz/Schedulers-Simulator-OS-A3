package com.schedulers_simulator.algorithms;
import com.schedulers_simulator.Process;

import java.util.*;

public class SJFScheduling extends Algorithm {
    private int contextSwitchTime;
    List<Process> finishedProcesses;

    public SJFScheduling() {
        this.contextSwitchTime = 0;
        processes  = new PriorityQueue<>(new ProcessArrivalTimeComparator());
        finishedProcesses = new ArrayList<>();
        finishedProcessesWithTimings = new HashMap<>();
        assignProcesses();
    }

    public void assignProcesses() {
        contextSwitchTime = 1;
    }

    @Override
    public void run()
    {
        int currentTime = 0;
        while (!processes.isEmpty()) {
            Process min = null;
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime) {
                    if (min == null || process.getBurstTime() < min.getBurstTime()) {
                        min = process;
                    }
                }
            }

            if (min != null) {
                processes.remove(min);
                finishedProcesses.add(min);
                currentTime += min.getBurstTime() + contextSwitchTime;
                finishedProcessesWithTimings.put(currentTime, min);
            } else {
                currentTime++;
            }
        }

        double totalBurst = 0;
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        for (Process process : finishedProcesses) {
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

        System.out.println("New List Using Shortest Job First\n" + finishedProcesses + "\n");
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime / finishedProcesses.size());
        System.out.println("Average Waiting Time: " + totalWaitingTime / finishedProcesses.size());
    }

    public static void main(String[] args) {
        SJFScheduling sjf = new SJFScheduling();
        sjf.run();
    }
}