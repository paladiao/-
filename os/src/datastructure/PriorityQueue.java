package datastructure;

import process.Process;
import process.Process_Schedule;

public class PriorityQueue
{
    private Process_Schedule head ;
    private Process_Schedule tail = new Process_Schedule("end",-Integer.MAX_VALUE,-1);
    public int size;

    public PriorityQueue(Process_Schedule process_schedule)
    {
        head = process_schedule;
        head.setNext(tail);
        size = 1;
    };

    public void addProcess(Process_Schedule process_schedule)
    {
        if(process_schedule.getBurst() == 0)
        {
            process_schedule.setStatus("finishing");
            Process_Schedule temp = head;
            while(!temp.getNext().getName().equals("end")){
                temp = temp.getNext();
            }
            temp.setNext(process_schedule);
            process_schedule.setNext(tail);size++;return;
        }
        int chance = size;
        process_schedule.setStatus("ready");
        if (head.getBurst() == 0)
        {
            process_schedule.setNext(head);
            head = process_schedule;
            size++;
            return;
        }
        if(process_schedule.compareTo(head))
        {
            process_schedule.setNext(head);
            head = process_schedule;
            size++;
            return;
        }
        Process_Schedule cur = head;
        while(chance >0)
        {
            if (cur.getNext().getBurst() == 0){
                process_schedule.setNext(cur.getNext());
                cur.setNext(process_schedule);
                size++;
                return;
            }
            if(process_schedule.compareTo(cur.getNext()))
            {
                process_schedule.setNext(cur.getNext());
                cur.setNext(process_schedule);
                size++;
                return;
            }
            cur = cur.getNext();
            chance--;
        }

        cur.setNext(process_schedule);
        process_schedule.setNext(tail);
        size++;
    }

    public Process_Schedule pop()
    {
        if(head != null)
        {
            Process_Schedule temp = head;
            head = head.getNext();
            size--;
            return temp;
        }
        return null;
    }

    public Process_Schedule getHead(){
        if(head!=null){
            head.work();
            head.setStatus("working");
            return head;
        }
        return tail;
    }

    public void printinfo()
    {
        System.out.println("Name " + " NeedTime "+ " Priority "+ " State");
        Process_Schedule cur = head;

        while(!cur.getName().equals("end"))
        {
            System.out.println(cur.getName()+ "        "+cur.getBurst()+ "        "+cur.getPriority()+ "      "+cur.getStatus()) ;
            cur = cur.getNext();
        }
    }
}
