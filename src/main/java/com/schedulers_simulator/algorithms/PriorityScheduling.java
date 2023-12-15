package com.schedulers_simulator.algorithms;

import com.schedulers_simulator.Process;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class is the implementation of the OS Priority Scheduling algorithm.
 *
 * @author Youssef Moataz
 */
public class PriorityScheduling extends Algorithm {

    Queue<Process> runningQueue;

    public PriorityScheduling() {
        runningQueue = new PriorityQueue<>(new ProcessPriorityComparator());
//        createSampleList();
    }

    @Override
    public void run() {

        Integer time = 0;

        while (true) {

            if (processes.isEmpty() && runningQueue.isEmpty()) {
                break;
            }

            while (!processes.isEmpty() && processes.peek().getArrivalTime() <= time) {
                runningQueue.add(processes.poll());
            }

            Boolean wasIdle = true;
            while (!runningQueue.isEmpty()) {
                if (runningQueue.peek().getWaitingTime() == -1) {
                    runningQueue.peek().setWaitingTime(time);
                }
                time += runningQueue.peek().getBurstTime();
                runningQueue.peek().setTurnaroundTime(time - runningQueue.peek().getWaitingTime());
                finishedProcessesWithTimings.put(time, runningQueue.poll());

                // solving starvation problem using aging method
                Queue<Process> tempQueue = new PriorityQueue<>(new ProcessArrivalTimeComparator());
                while (!runningQueue.isEmpty()) {
                    runningQueue.peek().incrementPriority();
                    tempQueue.add(runningQueue.poll());
                }
                while (!tempQueue.isEmpty()) {
                    runningQueue.add(tempQueue.poll());
                }

                wasIdle = false;
            }

            if (wasIdle) {
                time++;
            }

        }

        System.out.println(finishedProcessesWithTimings);
        System.out.println("Average waiting time " + getAverageWaitingTime());
        System.out.println("Average turnaround time " + getAverageTurnaroundTime());
    }

    private void createSampleList() {
//        readyQueue.add(new Process(0, "P1", 0, 10, 3));
//        readyQueue.add(new Process(0, "P2", 0, 1, 1));
//        readyQueue.add(new Process(0, "P3", 0, 2, 4));
//        readyQueue.add(new Process(0, "P4", 0, 1, 5));
//        readyQueue.add(new Process(0, "P5", 0, 5, 2));

//        readyQueue.add(new Process(0, "P1", 0, 17, 4));
//        readyQueue.add(new Process(0, "P2", 3, 6, 9));
//        readyQueue.add(new Process(0, "P3", 4, 10, 3));
//        readyQueue.add(new Process(0, "P4", 29, 4, 8));
    }

    private static class ProcessPriorityComparator implements Comparator<Process> {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getPriorityNumber() - p2.getPriorityNumber();
        }
    }

    public static void main(String[] args) {
        Algorithm algorithm = new PriorityScheduling();
        algorithm.run();
    }

}
