package com.schedulers_simulator;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

class SJFScheduling extends Algorithm {
    private int contextSwitchTime;
    List<Process> finishedProcesses;

    public SJFScheduling() {
        this.contextSwitchTime = 0;
        processes = new PriorityQueue<>(new ProcessArrivalTimeComparator());
        finishedProcesses = new ArrayList<>();
        finishedProcessesWithTimings = new HashMap<>();
        assignProcesses();
    }

    public void assignProcesses() {
        contextSwitchTime = 1;
    }

    @Override
    public void run() {

        System.out.print("Enter the context switch time: ");
        this.contextSwitchTime = new Scanner(System.in).nextInt();

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

}

class SRTFScheduling extends Algorithm {
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
                    finishedProcessesWithTimings.put(currentTime, currentProcess);
                }

                lastProcess = currentProcess;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                if (currentProcess.getRemainingTime() == 0) {
//                    finishedProcessesWithTimings.put(currentTime, currentProcess);
                    currentProcess.setCompletionTime(currentTime + 1);
                    currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                    totalWaitingTime += currentProcess.getWaitingTime();
                    totalTurnaroundTime += currentProcess.getTurnaroundTime();
                    processesCopy.remove(currentProcess);
                    processesCopy.add(currentProcess);
                } else {
                    readyQueue.add(currentProcess);
//                    finishedProcessesWithTimings.put(currentTime, currentProcess);
                }
            }
            currentTime++;
        }

        setFinishedProcessesWithTimings(finishedProcessesWithTimings);
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

    public Map<Integer, Process> getFinishedProcessesWithTimings() {
        return finishedProcessesWithTimings;
    }


    private void setFinishedProcessesWithTimings(Map<Integer, Process> finishedProcessesWithTimings) {
        this.finishedProcessesWithTimings = finishedProcessesWithTimings;
    }
}

/**
 * This class is the implementation of the OS Priority Scheduling algorithm.
 *
 * @author Youssef Moataz
 */
class PriorityScheduling extends Algorithm {

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

}

class AGScheduling extends Algorithm {

    List<Process> readyQueue;
    List<Process> runningQueue;
    List<Process> finishedProcesses;
    Integer initialQuantum;
    Integer numProcesses;

    public AGScheduling() {
        processes = new PriorityQueue<>(new ProcessArrivalTimeComparator());
        readyQueue = new ArrayList<>();
        runningQueue = new ArrayList<>();
        finishedProcesses = new ArrayList<>();
        finishedProcessesWithTimings = new HashMap<>();
        initialQuantum = 4;
        createSampleList();
        numProcesses = processes.size();
    }

    @Override
    public void run() {

        Integer currentTime = 0;

        Process currentProcess = null;
        Integer currentQuantum = 0;
        Integer currentHalfQuantum = 0;

        while (true) {

            if (processes.isEmpty() && readyQueue.isEmpty()) {

                if (currentProcess != null) {
                    currentTime += currentProcess.getBurstTime();
                    currentProcess.setRemainingTime(0);

                    currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());

                    finishedProcesses.add(currentProcess);
                    finishedProcessesWithTimings.put(currentTime, currentProcess);
                }

                break;
            }

            // add all arrived at current time to queue
            while (!processes.isEmpty() && processes.peek().getArrivalTime() == currentTime) {
                readyQueue.add(processes.poll());
            }

            if (currentProcess == null && !readyQueue.isEmpty()) {
                currentProcess = readyQueue.remove(0);
            }

            if (currentProcess == null) {
                currentTime++;
                continue;
            }

            currentQuantum = currentProcess.getQuantum();
            currentHalfQuantum = (int) Math.ceil(currentQuantum / 2.0);

            // non-preemptive
            if (currentProcess.getRemainingTime() < currentHalfQuantum) {
                currentTime += currentProcess.getRemainingTime();
                currentProcess.setRemainingTime(0);

                currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());

                finishedProcesses.add(currentProcess);
                finishedProcessesWithTimings.put(currentTime, currentProcess);
                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.remove(0);
                }
                continue;
            } else {
                currentTime += currentHalfQuantum;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - currentHalfQuantum);
            }

            // add all arrived at current time to queue
            while (!processes.isEmpty() && processes.peek().getArrivalTime() <= currentTime) {
                readyQueue.add(processes.poll());
            }

            // preemptive
            while (currentHalfQuantum < currentQuantum) {

                // add all arrived at current time to queue
                while (!processes.isEmpty() && processes.peek().getArrivalTime() <= currentTime) {
                    readyQueue.add(processes.poll());
                }

                if (getMinAGFactor() < currentProcess.getAGFactor()) {
                    Process currentPreemptive = currentProcess;
                    currentPreemptive.setQuantum(currentPreemptive.getQuantum() + (currentPreemptive.getQuantum() - currentHalfQuantum));

                    finishedProcesses.add(currentProcess);
                    finishedProcessesWithTimings.put(currentTime, currentProcess);
                    if (!readyQueue.isEmpty()) {
                        currentProcess = getProcessWithMinAGFactor();
                    }
                    readyQueue.add(currentPreemptive);

                    break;
                } else {
                    if (currentProcess.getRemainingTime() == 0) {
                        currentProcess.setQuantum(0);

                        currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                        currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());

                        finishedProcesses.add(currentProcess);
                        finishedProcessesWithTimings.put(currentTime, currentProcess);
                        if (!readyQueue.isEmpty()) {
                            currentProcess = readyQueue.get(0);
                        }

                        break;
                    }
                    currentProcess.decrementRemainingTime();
                }

                currentTime++;
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setQuantum(0);

                    currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());

                    finishedProcesses.add(currentProcess);
                    finishedProcessesWithTimings.put(currentTime, currentProcess);
                    if (!readyQueue.isEmpty()) {
                        currentProcess = readyQueue.remove(0);
                    }

                    break;
                }
                currentHalfQuantum++;
            }

            if (currentHalfQuantum == currentQuantum) {
                // quantum finished
                Integer quantumSum = 0;
                for (Process p : readyQueue) {
                    quantumSum += p.getQuantum();
                }
                Integer addedQuantum = (int) Math.ceil(((double) quantumSum / numProcesses) * 0.1);

                currentProcess.setQuantum(currentProcess.getQuantum() + addedQuantum);

                finishedProcesses.add(currentProcess);
                finishedProcessesWithTimings.put(currentTime, currentProcess);
                readyQueue.add(currentProcess);

                currentProcess = readyQueue.remove(0);
            }

//            currentTime++;
        }

//        System.out.println(currentTime);
        System.out.println(finishedProcesses);

    }

    private Integer getMinAGFactor() {
        Integer minAGFactor = Integer.MAX_VALUE;
        for (int i = 0; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).getAGFactor() < minAGFactor) {
                minAGFactor = readyQueue.get(i).getAGFactor();
            }
        }

        return minAGFactor;
    }

    private Process getProcessWithMinAGFactor() {
        Integer minIndex = -1;
        Integer minAGFactor = Integer.MAX_VALUE;
        for (int i = 0; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).getAGFactor() < minAGFactor) {
                minAGFactor = readyQueue.get(i).getAGFactor();
                minIndex = i;
            }
        }

        if (minIndex != -1) {
            Process foundProcess = readyQueue.get(minIndex);
            readyQueue.remove(foundProcess);
            return foundProcess;
        } else {
            return null;
        }
    }

    public Integer calculateAGFactor(Process process) {
        Random random = new Random();
        int randomValue = random.nextInt(21);
        if (randomValue < 10) {
            return randomValue + process.getArrivalTime() + process.getBurstTime();
        } else if (randomValue > 10) {
            return 10 + process.getArrivalTime() + process.getBurstTime();
        } else {
            return process.getPriorityNumber() + process.getArrivalTime() + process.getBurstTime();
        }
    }

    private void createSampleList() {
        processes.add(new Process("P1", "red", 0, 17, 4, initialQuantum, 20));
        processes.add(new Process("P2", "red", 3, 6, 9, initialQuantum, 17));
        processes.add(new Process("P3", "red", 4, 10, 3, initialQuantum, 16));
        processes.add(new Process("P4", "red", 29, 4, 8, initialQuantum, 43));
    }

    private static class ProcessArrivalTimeComparator implements Comparator<Process> {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getArrivalTime() - p2.getArrivalTime();
        }
    }

    public void setProcesses(Queue processes) {
        this.processes = processes;
    }

    public static void main(String[] args) {
        Algorithm algorithm = new AGScheduling();
        algorithm.run();
    }

}


abstract class Algorithm {
    Queue<Process> processes;
    Map<Integer, Process> finishedProcessesWithTimings;

    public abstract void run();

    public Algorithm() {
        processes = new PriorityQueue<>(new Algorithm.ProcessArrivalTimeComparator());
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
        arr.sort(new Algorithm.ProcessResultMapComparator());
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

class Process {
    private Integer id;
    private String name;
    private Color color;
    private Integer arrivalTime;
    private Integer burstTime;
    private Integer priorityNumber;
    private Integer waitingTime = -1;
    private Integer turnaroundTime = -1;
    private Integer remainingTime;
    private Integer quantum = -1;
    private Integer agFactor = -1;

    private Color[] PROCESS_COLORS = {
            Color.decode("#ff4747"), // red
            Color.decode("#af73f0"), // purple
            Color.PINK, // pink
            Color.LIGHT_GRAY, // gray
            Color.decode("#a1ffd9"), // teal
            Color.YELLOW, // yellow
            Color.ORANGE, // orange
    };

    public Process(Integer id, String name, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
        this.id = id;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.remainingTime = burstTime;
    }

    public Process(String name, Color color, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.remainingTime = burstTime;
    }

    public Process(String name, String color, Integer arrivalTime, Integer burstTime, Integer priorityNumber) {
        this.name = name;
//        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.remainingTime = burstTime;
    }

    public Process(String name, String color, Integer arrivalTime, Integer burstTime, Integer priorityNumber, Integer quantum) {
        this.name = name;
//        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.quantum = quantum;
        this.remainingTime = burstTime;
    }

    public Process(String name, String color, Integer arrivalTime, Integer burstTime, Integer priorityNumber, Integer quantum, Integer agFactor) {
        this.name = name;
        this.color = getColorByName(color);
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.quantum = quantum;
        this.remainingTime = burstTime;
        this.agFactor = agFactor;
    }

    public Process(String name, Integer color, Integer arrivalTime, Integer burstTime, Integer priorityNumber, Integer quantum, Integer agFactor) {
        this.name = name;
        this.color = PROCESS_COLORS[color];
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.quantum = quantum;
        this.remainingTime = burstTime;
        this.agFactor = agFactor;
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

    public Integer incrementPriority() {
        if (priorityNumber == 1) {
            return priorityNumber;
        }
        return --priorityNumber;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public Integer getTurnaroundTime() {
        return turnaroundTime;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public Integer getCompletionTime() {
        return this.arrivalTime + this.turnaroundTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void decrementRemainingTime() {
        this.remainingTime--;
    }

    public void setCompletionTime(Integer completionTime) {
        this.turnaroundTime = completionTime - this.arrivalTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(Integer turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public Integer getQuantum() {
        return quantum;
    }

    public void setQuantum(Integer quantum) {
        this.quantum = quantum;
    }

    public Integer getAGFactor() {
        return agFactor;
    }

    public void setAGFactor(Integer agFactor) {
        this.agFactor = agFactor;
    }

    public Color getColorByName(String name) {
        name = name.toLowerCase();
        if (name.contains("red")) {
            return this.PROCESS_COLORS[0];
        } else if (name.contains("purple")) {
            return this.PROCESS_COLORS[1];
        } else if (name.contains("pink")) {
            return this.PROCESS_COLORS[2];
        } else if (name.contains("gray")) {
            return this.PROCESS_COLORS[3];
        } else if (name.contains("teal")) {
            return this.PROCESS_COLORS[4];
        } else if (name.contains("yellow")) {
            return this.PROCESS_COLORS[5];
        } else if (name.contains("orange")) {
            return this.PROCESS_COLORS[6];
        } else {
            return Color.white;
        }
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", priorityNumber=" + priorityNumber +
                ", waitingTime=" + waitingTime +
                ", turnaroundTime=" + turnaroundTime +
                ", remainingTime=" + remainingTime +
                ", agFactor=" + agFactor +
                '}' + '\n';
    }
}

class Main {

    public static void main(String[] args) {

        // uncomment before uploading
        Queue<Process> processes = Main.getProcessesInput();
//        Queue<Process> processes = Main.getSampleProcesses();

        // replace with the required algorithm
        Algorithm schedulingAlgorithm = new AGScheduling();

        schedulingAlgorithm.setProcesses(processes);
        schedulingAlgorithm.run();

        JFrame frame = new JFrame("AG Scheduling");
        frame.setBounds(200, 100, 1000, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel components = new JPanel();
        components.setLayout(new GridLayout(3, 0));

        JLabel labelAvgWaitTime = new JLabel("Average waiting time = " + schedulingAlgorithm.getAverageWaitingTime().toString());
        JLabel labelAvgTurnaroundTime = new JLabel("Average turnaround time = " + schedulingAlgorithm.getAverageTurnaroundTime().toString());

        components.add(labelAvgWaitTime);
        components.add(labelAvgTurnaroundTime);

        JPanel processesComponents = new JPanel();
        processesComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (Map.Entry<Integer, Process> pair : schedulingAlgorithm.getFinishedProcesses()) {
            JPanel processPanel = new JPanel();
            processPanel.setLayout(new GridLayout(2, 1));
            processPanel.setPreferredSize(new Dimension(70, 70));
            processPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            processPanel.setBackground(pair.getValue().getColor());

            JLabel labelName = new JLabel(pair.getValue().getName());
            labelName.setHorizontalAlignment(SwingConstants.CENTER);
            processPanel.add(labelName);

            JLabel labelTime = new JLabel(pair.getKey().toString());
            labelTime.setHorizontalAlignment(SwingConstants.CENTER);
            processPanel.add(labelTime);

            processesComponents.add(processPanel);
        }
        components.add(processesComponents);

        frame.add(components);

        frame.setVisible(true);
    }

    private static Queue<Process> getProcessesInput() {

        Queue<Process> processes = new PriorityQueue<>(new Algorithm.ProcessArrivalTimeComparator());

        // uncomment before uploading
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter the Round Robin time quantum: ");
        int quantum = scanner.nextInt();
//        System.out.print("Enter the context switch time: ");
//        int contextSwitchTime = scanner.nextInt();
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter details for process " + (i + 1) + ":");

            System.out.print("Name: ");
            String name = scanner.next();

            System.out.print("Color (red/purple/pink/gray/teal/yellow/orange): ");
            String color = scanner.next();

            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Priority Number: ");
            int priority = scanner.nextInt();

            int agFactor;
            Random random = new Random();
            int randomValue = random.nextInt(21);
            if (randomValue < 10) {
                agFactor = randomValue + arrivalTime + burstTime;
            } else if (randomValue > 10) {
                agFactor = 10 + arrivalTime + burstTime;
            } else {
                agFactor = priority + arrivalTime + burstTime;
            }

            processes.add(new Process(name, color, arrivalTime, burstTime, priority, quantum, agFactor));
        }
//        scanner.close();

        return processes;
    }

    private static Queue<Process> getSampleProcesses() {
        Queue<Process> processes = new PriorityQueue<>(new Algorithm.ProcessArrivalTimeComparator());
        processes.add(new Process("P1", "pink", 0, 17, 4, 4, 20));
        processes.add(new Process("P2", "teal", 3, 6, 9, 4, 17));
        processes.add(new Process("P3", "gray", 4, 10, 3, 4, 16));
        processes.add(new Process("P4", "yellow", 29, 4, 8, 4, 43));
        return processes;
    }

}

// End of code