package Test;
import java.util.*;
public class Test {
    public static void main(String[] args) throws Exception {
        t tw = new t();
        if(t.set()>0){
            System.out.println("hh");
        }
    }
}
class t{


    t(){}
    static int set() throws Exception {
        throw new Exception();
    }

}