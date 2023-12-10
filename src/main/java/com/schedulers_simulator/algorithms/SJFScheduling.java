package com.schedulers_simulator.algorithms;
import com.schedulers_simulator.Process;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJFScheduling implements Algorithm {
    private int contextSwitchTime;
    private List<Process> processes;

    public SJFScheduling(List<Process> processes, int contextSwitchTime) {
        this.contextSwitchTime = contextSwitchTime;
        this.processes = processes;
    }
    @Override
    public void run()
    {
        // Created 2 Lists: waitingProcesses to add in it the given process
        // and completedProcesses to be the final answer after sorting the processes based on burst time
        List<Process> waitingProcesses = new ArrayList<>();
        List<Process> completedProcesses = new ArrayList<>();

        // Add processes to waitingProcesses list
        for (Process process : processes) {
            waitingProcesses.add(process);
        }

        int currentTime = 0;
        while (!waitingProcesses.isEmpty()) {
            // Sort waitingProcesses based on arrival time
            waitingProcesses.sort(Comparator.comparingInt(Process::getArrivalTime));

            Process min = null; // The process with the minimum burst time initialized with null -has no value-
            for (Process process : waitingProcesses) {
                // if the process arrival time is less than or equal to the current time
                if (process.getArrivalTime() <= currentTime) {
                    // if the minimum burst time is null OR the process burst time is less than the minimum burst time
                    if (min == null || process.getBurstTime() < min.getBurstTime()) {
                        min = process;
                    }
                }
            }

            if (min != null) {
                waitingProcesses.remove(min); // remove the process with the minimum burst time from waitingProcesses
                completedProcesses.add(min); // then add it to completedProcesses
                // Incrementing the current time by the burst time of the process and the context time
                currentTime += min.getBurstTime() + contextSwitchTime;
            } else { // To simulate the passage of time if there is no process to execute
                currentTime++;
            }
        }

        // Printing the turnaround time and waiting time for each process
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

        // Print the list of completed processes
        System.out.println("New List Using Shortest Job First: \n" + completedProcesses + "\n");
        // Print the average turnaround time and waiting time for all processes
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime / completedProcesses.size());
        System.out.println("Average Waiting Time: " + totalWaitingTime / completedProcesses.size());

    }
    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, "P1", 0, 7, 0));
        processes.add(new Process(2, "P2", 0, 14, 0));
        processes.add(new Process(3, "P3", 0, 1, 0));
        processes.add(new Process(4, "P4", 0, 9, 0));
        SJFScheduling sjf1 = new SJFScheduling(processes, 1);
        sjf1.run();

        //Lab Example
        List<Process> processes1 = new ArrayList<>();
        processes1.add(new Process(1, "P1", 0, 4, 0));
        processes1.add(new Process(2, "P2", 1, 8, 0));
        processes1.add(new Process(3, "P3", 2, 2, 0));
        processes1.add(new Process(4, "P4", 3, 6, 0));
        processes1.add(new Process(5, "P5", 4, 3, 0));
        SJFScheduling sjf2 = new SJFScheduling(processes1, 1);
        sjf2.run();

        //Lecture Example
        List<Process> processes2 = new ArrayList<>();
        processes2.add(new Process(1, "P1", 0, 7, 0));
        processes2.add(new Process(2, "p2", 2, 4, 0));
        processes2.add(new Process(3, "p3", 4, 1, 0));
        processes2.add(new Process(4, "p4", 5, 4, 0));
        SJFScheduling sjf3 = new SJFScheduling(processes2, 0);
        sjf3.run();

        List<Process> processes3 = new ArrayList<>();
        processes3.add(new Process(1, "P1", 0, 4, 0));
        processes3.add(new Process(2, "P2", 1, 8, 0));
        processes3.add(new Process(3, "P3", 3, 2, 0));
        processes3.add(new Process(4, "P4", 10, 6, 0));
        processes3.add(new Process(5, "P5", 12, 3, 0));
        SJFScheduling sjf4 = new SJFScheduling(processes3, 1);
        sjf4.run();


    }
}

