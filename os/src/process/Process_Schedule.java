package process;

public class Process_Schedule
{
    private String name;
    private int priority;
    private Process_Schedule next;
    private int burst;
    private String status = "ready";

    public Process_Schedule(String name,int priority,int burst)
    {
        this.name = name;
        this.priority = priority;
        this.burst = burst;
    };

    public void setNext(Process_Schedule next)
    {
        this.next = next;
    }

    public Process_Schedule getNext()
    {
        return next;
    }

    public String getName()
    {
        return name;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBurst()
    {
        return burst;
    }

    public String getStatus()
    {
        return status;
    }

    public void work(){
        burst--;
        priority--;
    }

    public boolean compareTo(Process_Schedule process_schedule)
    {
        if(priority > process_schedule.getPriority())
        {
            return true;
        }
        return false;
    }

}
