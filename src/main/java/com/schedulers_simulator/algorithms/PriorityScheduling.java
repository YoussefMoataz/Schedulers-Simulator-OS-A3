package com.schedulers_simulator.algorithms;

import com.schedulers_simulator.Process;

import java.util.*;

public class PriorityScheduling implements Algorithm {

    Queue<Process> readyQueue;
    Queue<Process> runningQueue;
    List<Process> finishedProcesses;

    public PriorityScheduling() {
        readyQueue = new PriorityQueue<>(new ProcessArrivalTimeComparator());
        runningQueue = new PriorityQueue<>(new ProcessPriorityComparator());
        finishedProcesses = new ArrayList<>();
        createSampleList();
    }

    @Override
    public void run() {

        Integer time = 0;

        while (true) {

            if (readyQueue.isEmpty() && runningQueue.isEmpty()) {
                break;
            }

            while (!readyQueue.isEmpty() && readyQueue.peek().getArrivalTime() <= time) {
                runningQueue.add(readyQueue.poll());
            }

            Boolean wasEmpty = true;
            while (!runningQueue.isEmpty()) {
                time += runningQueue.peek().getBurstTime();
                finishedProcesses.add(runningQueue.poll());
                wasEmpty = false;
            }

            if (wasEmpty) {
                time++;
            }

        }

        System.out.println(finishedProcesses);
    }

    private void createSampleList() {
//        readyQueue.add(new Process(0, "P1", 0, 10, 3));
//        readyQueue.add(new Process(0, "P2", 0, 1, 1));
//        readyQueue.add(new Process(0, "P3", 0, 2, 4));
//        readyQueue.add(new Process(0, "P4", 0, 1, 5));
//        readyQueue.add(new Process(0, "P5", 0, 5, 2));

        readyQueue.add(new Process(0, "P1", 0, 17, 4));
        readyQueue.add(new Process(0, "P2", 3, 6, 9));
        readyQueue.add(new Process(0, "P3", 4, 10, 3));
        readyQueue.add(new Process(0, "P4", 29, 4, 8));
    }

    private static class ProcessPriorityComparator implements Comparator<Process> {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getPriorityNumber() - p2.getPriorityNumber();
        }
    }

    private static class ProcessArrivalTimeComparator implements Comparator<Process> {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getArrivalTime() - p2.getArrivalTime();
        }
    }

    public static void main(String[] args) {
        Algorithm algorithm = new PriorityScheduling();
        algorithm.run();
    }

}
