package com.schedulers_simulator.algorithms;
import com.schedulers_simulator.Process;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJFScheduling implements Algorithm {
    private int contextSwitchTime;
    private List<Process> processes;
    public SJFScheduling() {
        this.contextSwitchTime = 0; // To be default context switch time if needed
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

    public static void main(String[] args)
    {
        SJFScheduling sjf = new SJFScheduling();
        sjf.run();
    }
}
