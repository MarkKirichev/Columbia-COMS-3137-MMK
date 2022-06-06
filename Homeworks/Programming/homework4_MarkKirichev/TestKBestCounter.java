import java.util.Arrays;
import java.util.List;

public class TestKBestCounter{
   
    public static void main(String[] args) { 
        int k = 5;
        KBestCounter<Integer> counter = new KBestCounter<>(k);

        System.out.println("Inserting 1,2,3."); 
        for(int i = 1; i<=3; i++)
            counter.count(i);

        System.out.println("5-best should be [1,2,3]: "+counter.kbest());
        counter.count(2);
        
        System.out.println("Inserting another 2.");
        System.out.println("5-best should be [1,2,2,3]: "+counter.kbest());

        System.out.println("Inserting 4..99.");
        for (int i = 4; i < 100; i++)   
            counter.count(i);

        System.out.println("5-best should be [95,96,97,98,99]: " + counter.kbest());
        
        System.out.println("Inserting 100, 20, 101.");
        counter.count(100);
        counter.count(20);
        counter.count(101);
        
        System.out.println("5-best should be [97,98,99,100,101]: " + counter.kbest());
    }
}
