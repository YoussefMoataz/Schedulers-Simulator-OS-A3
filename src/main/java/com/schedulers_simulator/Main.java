package com.schedulers_simulator;

import com.schedulers_simulator.algorithms.AGScheduling;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

//        System.out.print("Enter the number of processes: ");
//        int numProcesses = scanner.nextInt();

//        Queue<Process> processes = Main.getProcessesInput(numProcesses);
        Queue<Process> processes = Main.getProcessesInput(4);

        AGScheduling schedulingAlgorithm = new AGScheduling();
        schedulingAlgorithm.setProcesses(processes);
        schedulingAlgorithm.run();
        List<Process> result = schedulingAlgorithm.getFinishedProcesses();
        Map<Integer, Process> resultWthTimings = schedulingAlgorithm.getFinishedProcessesWithTimings();

//        System.out.println(result);

        JFrame frame = new JFrame("AG Scheduling");
        frame.setBounds(200, 100, 1000, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        JPanel panel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//
//                int x = 50;
//                int y = 50;
//                int width = 60;
//                int height = 60;
//
//                for (Process process : result) {
//                    g.setColor(Color.BLUE);
//                    g.drawRect(x, y, width, height);
//                    g.drawString(process.getName(), x + width / 2 - 5, y + height / 2 + 5);
//                    x += width + 10;
//                }
//            }
//        };
//        frame.getContentPane().add(panel);

//        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
//        for (Process process : result) {
//            JPanel processPanel = new JPanel();
//            processPanel.setPreferredSize(new Dimension(70, 70));
//            processPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            processPanel.setBackground(process.getColor());
//            JLabel label = new JLabel();
//            String labelString = process.getName();
//            label.setText(labelString);
//            processPanel.add(label);
//            frame.add(processPanel);
//        }

        List<Map.Entry<Integer, Process>> arr = new ArrayList<>();
        for (Map.Entry<Integer, Process> pair: resultWthTimings.entrySet()) {
            arr.add(pair);
        }
        arr.sort(new ProcessResultMapComparator());

        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (Map.Entry<Integer, Process> pair: arr) {
            JPanel processPanel = new JPanel();
            processPanel.setLayout(new GridLayout(2, 1));
            processPanel.setPreferredSize(new Dimension(70, 70));
            processPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            processPanel.setBackground(pair.getValue().getColor());
            JLabel label1 = new JLabel(pair.getValue().getName());
            JLabel label2 = new JLabel(pair.getKey().toString());
            processPanel.add(label1);
            processPanel.add(label2);
            frame.add(processPanel);
        }

        frame.setVisible(true);
    }

    private static Queue<Process> getProcessesInput(Integer numProcesses) {

        Queue<Process> processes = new PriorityQueue<>(new ProcessArrivalTimeComparator());

        Scanner scanner = new Scanner(System.in);

//        System.out.print("Enter the Round Robin time quantum: ");
//        int quantum = scanner.nextInt();

//        for (int i = 0; i < numProcesses; i++) {
//            System.out.println("Enter details for process " + (i + 1) + ":");
//            System.out.print("Name: ");
//            String name = scanner.next();
//
//            System.out.print("Color: ");
//            String color = scanner.next();
//
//            System.out.print("Burst Time: ");
//            int burstTime = scanner.nextInt();
//
//            System.out.print("Arrival Time: ");
//            int arrivalTime = scanner.nextInt();
//
//            System.out.print("Priority Number: ");
//            int priority = scanner.nextInt();
//
//            int agFactor;
//
//            Random random = new Random();
//            int randomValue = random.nextInt(21);
//            if (randomValue < 10) {
//                agFactor = randomValue + arrivalTime + burstTime;
//            } else if (randomValue > 10) {
//                agFactor = 10 + arrivalTime + burstTime;
//            } else {
//                agFactor = priority + arrivalTime + burstTime;
//            }
//
//            processes.add(new Process(name, color, arrivalTime, burstTime, priority, quantum, agFactor));
//        }

        processes.add(new Process("P1", 0, 0, 17, 4, 4, 20));
        processes.add(new Process("P2", 1, 3, 6, 9, 4, 17));
        processes.add(new Process("P3", 2, 4, 10, 3, 4, 16));
        processes.add(new Process("P4", 3, 29, 4, 8, 4, 43));

        scanner.close();

        return processes;
    }

    private static class ProcessArrivalTimeComparator implements Comparator<Process> {
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

}