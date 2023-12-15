package com.schedulers_simulator;

import com.schedulers_simulator.algorithms.AGScheduling;
import com.schedulers_simulator.algorithms.Algorithm;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // uncomment before uploading
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        // uncomment before uploading
        Queue<Process> processes = Main.getProcessesInput(numProcesses);
//        Queue<Process> processes = Main.getProcessesInput(4);

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
        JLabel labelAvgTurnaroundTime = new JLabel("Average waiting time = " + schedulingAlgorithm.getAverageTurnaroundTime().toString());

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

    private static Queue<Process> getProcessesInput(Integer numProcesses) {

        Queue<Process> processes = new PriorityQueue<>(new Algorithm.ProcessArrivalTimeComparator());

        // uncomment before uploading
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Round Robin time quantum: ");
        int quantum = scanner.nextInt();
//        System.out.print("Enter the context switch time: ");
//        int contextSwitchTime = scanner.nextInt();
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter details for process " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.next();

            System.out.print("Color: ");
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
        scanner.close();

        // comment before uploading
//        processes.add(new Process("P1", "pink", 0, 17, 4, 4, 20));
//        processes.add(new Process("P2", "teal", 3, 6, 9, 4, 17));
//        processes.add(new Process("P3", "gray", 4, 10, 3, 4, 16));
//        processes.add(new Process("P4", "yellow", 29, 4, 8, 4, 43));

        return processes;
    }

}