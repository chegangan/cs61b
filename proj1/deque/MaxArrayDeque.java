package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    Comparator<T> c;

    public MaxArrayDeque(Comparator<T> c) {
        this.c = c;
    }

    public T max() {
        T item = compare(c);
        return item;
    }

    public T max(Comparator<T> c) {
        T item = compare(c);
        return item;
    }
    
    private T compare(Comparator<T> c) {
        /*for (int i = 0; i < ; i++) {
            
        }*/
        return null;
    }

    private static class MyComparator implements Comparator<Integer> {

        /**
         * @param o1 the first object to be compared. 
         * @param o2 the second object to be compared.
         * @return
         */
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    public Comparator<Integer> getComparator() {
        return new MyComparator();
    }
}
