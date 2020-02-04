package process;

import java.util.concurrent.Semaphore;

public class Process implements Runnable{
    static Semaphores semaphores = new Semaphores(1);
    Semaphore semaphore = new Semaphore(1);
    private static int num;
    Object obj = new Object();
    public void setNum(int num){ this.num = num;}
    @Override
    public void run() {
        while (num > 0){
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(num>0) {
                    System.out.println(Thread.currentThread().getName() + ": " + num);
                    num--;
                }
            semaphore.release();
        }
//            try {
//                semaphore.acquire();
//                if(num>0){
//                    System.out.println(Thread.currentThread().getName()+": "+num);
//                    num--;
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            finally {
//                semaphore.release();
//            }

    }


}
