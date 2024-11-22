package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {

    @Test
    public void test(){
        ArrayDeque<Integer> d1 = new ArrayDeque<>();
        LinkedListDeque<Integer> d2 = new LinkedListDeque<>();

        int num1 = 500;
        for (int i = 0; i < num1; i++) {
            int a = StdRandom.uniform(0,6);
            int b = StdRandom.uniform(0,500);
            switch (i) {
                case 0:
                    assertEquals(d1.size(), d2.size());
                case 1:
                    d1.addFirst(b);
                    d2.addFirst(b);
                case 2:
                    d1.addLast(b);
                    d2.addLast(b);
                case 3:
                    Integer i1 = d1.removeFirst();
                    Integer i2 = d2.removeFirst();
                    assertEquals(i1, i2);
                case 4:
                    Integer i3 = d1.removeLast();
                    Integer i4 = d2.removeLast();
                    assertEquals(i3, i4);
                case 5:
                    assertEquals(d1.isEmpty(), d2.isEmpty());
                case 6:
                    int num;
                    do {
                        num = StdRandom.uniform(0, 100);
                    } while (d2.size() < num);
                    assertEquals(d1.get(num), d2.get(num));
            }
        }

    }

    @Test
    public void testGet(){
        ArrayDeque<Integer> d1 = new ArrayDeque<>();
        for (int i = 0; i < 4; i++) {
            d1.addFirst(i);
        }
        for (int i = 4; i < 8; i++) {
            d1.addLast(i);
        }
        for (int i = 0; i < 8; i++) {
            int a = d1.get(i);
            System.out.println(a);
        }
    }
}
