package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    public static void main(String[] args) {
        testThreeAddThreeRemove();
    }

    public static void testThreeAddThreeRemove() {
        AListNoResizing<Integer> al = new AListNoResizing<>();
        BuggyAList<Integer> bl = new BuggyAList<>();
        for (int i = 0; i < 3; i++) {
            al.addLast(i);
            bl.addLast(i);
        }
        boolean same = true;
        for (int i = 0; i < 3; i++) {
            int a = al.removeLast();
            int b = bl.removeLast();
            if (a != b) {
                same = false;
                break;
            }
        }
        System.out.println(same);
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);

            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int sizeb = B.size();
                assertTrue(size == sizeb);
            } else if (operationNumber == 2 && L.size() > 0) {
                int num = L.getLast();
                int numb = B.getLast();
                assertTrue(num == numb);
            } else if (operationNumber == 3 && L.size() > 0) {
                int num = L.removeLast();
                int numb = B.removeLast();
                assertTrue(num == numb);
            }
        }
    }
}
