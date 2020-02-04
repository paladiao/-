package process;

import java.util.LinkedList;

public class Semaphores {
    private int value;
    //private LinkedList<Thread> processlist = new LinkedList<>();
    public Semaphores(int value){ this.value = value;}
    public Semaphores(){}
    void signal(){
//        value++;
//        if(value >= 0){
//            processlist.pop();
//        }
//        while (value > 0){
//                  processlist.pop();
//        }
        value++;
//        return processlist.pop();
    }

    void wait_Sph(){
//        value--;
//        if(value < 0){
//            processlist.push(thread);
//            while(true);
//        }
        while(value <= 0){
//            processlist.push(thread);
    }
        value--;
    }
}
