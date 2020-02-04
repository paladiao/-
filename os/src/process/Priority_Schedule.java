package process;

import datastructure.PriorityQueue;

public class Priority_Schedule
{
    public static void main(String[] args)
    {
        Process_Schedule p1 = new Process_Schedule("p1",1,2);
        Process_Schedule p2 = new Process_Schedule("p2",5,3);
        Process_Schedule p3 = new Process_Schedule("p3",3,1);
        Process_Schedule p4 = new Process_Schedule("p4",4,2);
        Process_Schedule p5 = new Process_Schedule("p5",2,4);

        PriorityQueue priorityQueue = new PriorityQueue(p2);
        priorityQueue.addProcess(p1);
        priorityQueue.addProcess(p3);
        priorityQueue.addProcess(p4);
        priorityQueue.addProcess(p5);
        exe(priorityQueue);
    }

    public static void exe(PriorityQueue priorityQueue)
    {
        int timer = 0;
        System.out.println("CPUTIME :"+timer);
        priorityQueue.printinfo();

        timer += 1;
        System.out.println("CPUTIME :"+timer);
        priorityQueue.getHead();
        priorityQueue.printinfo();
        Process_Schedule work = priorityQueue.pop();
        priorityQueue.addProcess(work);

        while(priorityQueue.getHead().getBurst() != -1)
        {
            timer += 1;
            System.out.println("CPUTIME :"+timer);
            priorityQueue.printinfo();
            Process_Schedule work0 = priorityQueue.pop();
            priorityQueue.addProcess(work0);
        }

    }
}
