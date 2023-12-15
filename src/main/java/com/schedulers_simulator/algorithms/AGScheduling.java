//package com.schedulers_simulator.algorithms;
//
//import com.schedulers_simulator.Process;
//
//import java.util.*;
//
///*
//i. The running process used all its quantum time and still has a job to do
//(add this process to the end of the queue, then increase its Quantum time by (ceil(10% of the (mean of Quantum))) ).
//
//ii. The running process didn't use all its quantum time based on another
//process converted from ready to running (add this process to the end
//of the queue, and then increase its Quantum time by the remaining
//unused Quantum time of this process).
//
//iii. The running process finished its job (set its quantum time to zero
//and remove it from ready queue and add it to the die list). DONE
//*/
//
//public class AGScheduling extends Algorithm {
//
//    List<Process> readyQueue;
//    List<Process> runningQueue;
//    List<Process> finishedProcesses;
//    Integer initialQuantum;
//    Integer numProcesses;
//
//    public AGScheduling() {
//        processes = new PriorityQueue<>(new ProcessArrivalTimeComparator());
//        readyQueue = new ArrayList<>();
//        runningQueue = new ArrayList<>();
//        finishedProcesses = new ArrayList<>();
//        finishedProcessesWithTimings = new HashMap<>();
//        initialQuantum = 4;
//        createSampleList();
//        numProcesses = processes.size();
//    }
//
//    @Override
//    public void run() {
//
//        Integer currentTime = 0;
//
//        Process currentProcess = null;
//        Integer currentQuantum = 0;
//        Integer currentHalfQuantum = 0;
//
//        while (true) {
//
//            if (processes.isEmpty() && readyQueue.isEmpty()) {
//
//                if (currentProcess != null) {
//                    currentTime += currentProcess.getBurstTime();
//                    currentProcess.setRemainingTime(0);
//                    // todo increment time with context switching value
//                    currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
//                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
//
//                    finishedProcesses.add(currentProcess);
//                    finishedProcessesWithTimings.put(currentTime, currentProcess);
//                }
//
//                break;
//            }
//
//            // add all arrived at current time to queue
//            while (!processes.isEmpty() && processes.peek().getArrivalTime() == currentTime) {
//                readyQueue.add(processes.poll());
//            }
//
//            if (currentProcess == null && !readyQueue.isEmpty()) {
//                currentProcess = readyQueue.remove(0);
//            }
//
//            if (currentProcess == null) {
//                currentTime++;
//                continue;
//            }
//
//            currentQuantum = currentProcess.getQuantum();
//            currentHalfQuantum = (int) Math.ceil(currentQuantum / 2.0);
//
//            // non-preemptive
//            if (currentProcess.getRemainingTime() < currentHalfQuantum) {
//                currentTime += currentProcess.getRemainingTime();
//                currentProcess.setRemainingTime(0);
//                // todo increment time with context switching value
//                currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
//                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
//
//                finishedProcesses.add(currentProcess);
//                finishedProcessesWithTimings.put(currentTime, currentProcess);
//                if (!readyQueue.isEmpty()) {
//                    currentProcess = readyQueue.remove(0);
//                }
//                continue;
//            } else {
//                currentTime += currentHalfQuantum;
//                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - currentHalfQuantum);
//            }
//
//            // add all arrived at current time to queue
//            while (!processes.isEmpty() && processes.peek().getArrivalTime() <= currentTime) {
//                readyQueue.add(processes.poll());
//            }
//
//            // preemptive
//            while (currentHalfQuantum < currentQuantum) {
//
//                // add all arrived at current time to queue
//                while (!processes.isEmpty() && processes.peek().getArrivalTime() <= currentTime) {
//                    readyQueue.add(processes.poll());
//                }
//
//                if (getMinAGFactor() < currentProcess.getAGFactor()) {
//                    Process currentPreemptive = currentProcess;
//                    currentPreemptive.setQuantum(currentPreemptive.getQuantum() + (currentPreemptive.getQuantum() - currentHalfQuantum));
//
//                    // todo increment time with context switching value
//
//                    finishedProcesses.add(currentProcess);
//                    finishedProcessesWithTimings.put(currentTime, currentProcess);
//                    if (!readyQueue.isEmpty()) {
//                        currentProcess = getProcessWithMinAGFactor();
//                    }
//                    readyQueue.add(currentPreemptive);
//
//                    break;
//                } else {
//                    if (currentProcess.getRemainingTime() == 0) {
//                        currentProcess.setQuantum(0);
//                        // todo increment time with context switching value
//                        currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
//                        currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
//
//                        finishedProcesses.add(currentProcess);
//                        finishedProcessesWithTimings.put(currentTime, currentProcess);
//                        if (!readyQueue.isEmpty()) {
//                            currentProcess = readyQueue.get(0);
//                        }
//
//                        break;
//                    }
//                    currentProcess.decrementRemainingTime();
//                }
//
//                currentTime++;
//                if (currentProcess.getRemainingTime() == 0) {
//                    currentProcess.setQuantum(0);
//                    // todo increment time with context switching value
//                    currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
//                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
//
//                    finishedProcesses.add(currentProcess);
//                    finishedProcessesWithTimings.put(currentTime, currentProcess);
//                    if (!readyQueue.isEmpty()) {
//                        currentProcess = readyQueue.remove(0);
//                    }
//
//                    break;
//                }
//                currentHalfQuantum++;
//            }
//
//            if (currentHalfQuantum == currentQuantum) {
//                // quantum finished
//                Integer quantumSum = 0;
//                for (Process p : readyQueue) {
//                    quantumSum += p.getQuantum();
//                }
//                Integer addedQuantum = (int) Math.ceil(((double) quantumSum / numProcesses) * 0.1);
//
//                currentProcess.setQuantum(currentProcess.getQuantum() + addedQuantum);
//
//                // todo increment time with context switching value
//
//                finishedProcesses.add(currentProcess);
//                finishedProcessesWithTimings.put(currentTime, currentProcess);
//                readyQueue.add(currentProcess);
//
//                currentProcess = readyQueue.remove(0);
//            }
//
////            currentTime++;
//        }
//
////        System.out.println(currentTime);
//        System.out.println(finishedProcesses);
//
//    }
//
//    private Integer getMinAGFactor() {
//        Integer minAGFactor = Integer.MAX_VALUE;
//        for (int i = 0; i < readyQueue.size(); i++) {
//            if (readyQueue.get(i).getAGFactor() < minAGFactor) {
//                minAGFactor = readyQueue.get(i).getAGFactor();
//            }
//        }
//
//        return minAGFactor;
//    }
//
//    private Process getProcessWithMinAGFactor() {
//        Integer minIndex = -1;
//        Integer minAGFactor = Integer.MAX_VALUE;
//        for (int i = 0; i < readyQueue.size(); i++) {
//            if (readyQueue.get(i).getAGFactor() < minAGFactor) {
//                minAGFactor = readyQueue.get(i).getAGFactor();
//                minIndex = i;
//            }
//        }
//
//        if (minIndex != -1) {
//            Process foundProcess = readyQueue.get(minIndex);
//            readyQueue.remove(foundProcess);
//            return foundProcess;
//        } else {
//            return null;
//        }
//    }
//
//    public Integer calculateAGFactor(Process process) {
//        Random random = new Random();
//        int randomValue = random.nextInt(21);
//        if (randomValue < 10) {
//            return randomValue + process.getArrivalTime() + process.getBurstTime();
//        } else if (randomValue > 10) {
//            return 10 + process.getArrivalTime() + process.getBurstTime();
//        } else {
//            return process.getPriorityNumber() + process.getArrivalTime() + process.getBurstTime();
//        }
//    }
//
//    private void createSampleList() {
//        processes.add(new Process("P1", "red", 0, 17, 4, initialQuantum, 20));
//        processes.add(new Process("P2", "red", 3, 6, 9, initialQuantum, 17));
//        processes.add(new Process("P3", "red", 4, 10, 3, initialQuantum, 16));
//        processes.add(new Process("P4", "red", 29, 4, 8, initialQuantum, 43));
//    }
//
//    private static class ProcessArrivalTimeComparator implements Comparator<Process> {
//        @Override
//        public int compare(Process p1, Process p2) {
//            return p1.getArrivalTime() - p2.getArrivalTime();
//        }
//    }
//
//    public void setProcesses(Queue processes){
//        this.processes = processes;
//    }
//
//    public static void main(String[] args) {
//        Algorithm algorithm = new AGScheduling();
//        algorithm.run();
//    }
//
//}
