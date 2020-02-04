package process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Test
{
    public static  void  main(String[] args)
    {
        Process p1 = new Process();
        p1.setNum(30);
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p1);
        Thread t3 = new Thread(p1);
        t1.start();t2.start();t3.start();
    }

}
