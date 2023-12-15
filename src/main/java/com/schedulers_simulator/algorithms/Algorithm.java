package com.schedulers_simulator.algorithms;

import com.schedulers_simulator.Process;

import java.util.*;

public abstract class Algorithm {
    Queue<Process> processes;
    Map<Integer, Process> finishedProcessesWithTimings;

    public abstract void run();

    public Algorithm() {
        processes = new PriorityQueue<>(new ProcessArrivalTimeComparator());
        finishedProcessesWithTimings = new HashMap<>();
    }

    public void setProcesses(Queue processes) {
        this.processes = processes;
    }

    private Map<Integer, Process> getFinishedProcessesWithTimings() {
        return finishedProcessesWithTimings;
    }

    public List<Map.Entry<Integer, Process>> getFinishedProcesses() {
        List<Map.Entry<Integer, Process>> arr = new ArrayList<>();
        for (Map.Entry<Integer, Process> pair : finishedProcessesWithTimings.entrySet()) {
            arr.add(pair);
        }
        arr.sort(new ProcessResultMapComparator());
        return arr;
    }

    public static class ProcessArrivalTimeComparator implements Comparator<Process> {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getArrivalTime() - p2.getArrivalTime();
        }
    }

    private static class ProcessResultMapComparator implements Comparator<Map.Entry<Integer, Process>> {

        @Override
        public int compare(Map.Entry<Integer, Process> o1, Map.Entry<Integer, Process> o2) {
            return o1.getKey() - o2.getKey();
        }
    }

    public Double getAverageWaitingTime() {
        Set<Process> finishedSet = new HashSet<>(finishedProcessesWithTimings.values());

        Double avg = 0.0;
        for (Process p : finishedSet) {
            avg += p.getWaitingTime();
        }
        return avg / finishedSet.size();
    }

    public Double getAverageTurnaroundTime() {
        Set<Process> finishedSet = new HashSet<>(finishedProcessesWithTimings.values());

        Double avg = 0.0;
        for (Process p : finishedSet) {
            avg += p.getTurnaroundTime();
        }
        return avg / finishedSet.size();
    }

}
