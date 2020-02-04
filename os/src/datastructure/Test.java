package datastructure;

import process.Process_Schedule;

public class Test {
    public static void  main(String[] args){
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
        priorityQueue.pop();
        priorityQueue.printinfo();
        System.out.println(priorityQueue.size);
    }
}
