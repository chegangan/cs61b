package deque;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;


public class testrate {
    @Test
    public void test() {

        int numSet = 100000;
        ArrayDeque<Integer> a = new ArrayDeque<>();
        LinkedListDeque<Integer> l = new LinkedListDeque<>();


        LocalTime t1 = LocalTime.now();
        for (int i = 0; i < numSet; i++) {
            a.addFirst(i);
        }
        LocalTime t2 = LocalTime.now();
        Duration duration1 = Duration.between(t1, t2);


        LocalTime t3 = LocalTime.now();
        for (int i = 0; i < numSet; i++) {
            l.addFirst(i);
        }
        LocalTime t4 = LocalTime.now();
        Duration duration2 = Duration.between(t3, t4);

        System.out.println(duration1);
        System.out.println(duration2);




        LocalTime t5 = LocalTime.now();
        for (int i = 0; i < numSet; i++) {
            a.get(i);
        }
        LocalTime t6 = LocalTime.now();
        Duration duration3 = Duration.between(t5, t6);


        LocalTime t7 = LocalTime.now();
        for (int i = 0; i < numSet; i++) {
            l.get(i);
        }
        LocalTime t8 = LocalTime.now();
        Duration duration4 = Duration.between(t7, t8);

        System.out.println(duration3);
        System.out.println(duration4);
    }

    @Test
    public void test2() {

        int numSet = 1000000;
        ArrayDeque<Integer> a = new ArrayDeque<>();
        LinkedListDeque<Integer> l = new LinkedListDeque<>();


        LocalTime t1 = LocalTime.now();
        for (int i = 0; i < numSet; i++) {
            a.addFirst(i);
        }
        LocalTime t2 = LocalTime.now();
        Duration duration1 = Duration.between(t1, t2);


        LocalTime t3 = LocalTime.now();
        for (int i = 0; i < numSet; i++) {
            a.addFirst(i);
        }
        LocalTime t4 = LocalTime.now();
        Duration duration2 = Duration.between(t3, t4);

        System.out.println(duration1);
        System.out.println(duration2);
    }
}
