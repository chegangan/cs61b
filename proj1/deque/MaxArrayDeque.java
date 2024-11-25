package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> c;

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
        if (this.size == 0) {
            return null;
        }
        T max = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            int flag = c.compare(max, get(i));
            if (flag < 0) {
                max = this.get(i);
            }
        }
        return max;
    }

    private static class MyComparator implements Comparator<Integer> {

        /**
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         *           如果o1大于o2,那么返回正数
         *           如果o1小于o2,那么返回负数
         *           如果o1等于o2,返回0
         * @return
         */
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    private static Comparator<Integer> getComparator() {
        return new MyComparator();
    }


    private static class MyComparator2 implements Comparator<String> {

        /**
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return
         */
        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    }

    private static Comparator<String> getComparator2() {
        return new MyComparator2();
    }

    /*public static void main(String[] args) {
        Comparator<Integer> c = MaxArrayDeque.getComparator();
        Comparator<String> c2 = MaxArrayDeque.getComparator2();
        MaxArrayDeque<Integer> maxArrayDeque = new MaxArrayDeque<Integer>(c);
        for (int i = 0; i < 20; i++) {
            maxArrayDeque.addFirst(i);
        }
        MaxArrayDeque<String> maxArrayDeque1 = new MaxArrayDeque<>(c2);
        for (int i = 0; i < 20; i++) {
            String str = "";
            for (int j = 0; j < i; j++) {
                str += "a";
            }
            maxArrayDeque1.addFirst(str);
        }

        Integer a = maxArrayDeque.max();
        System.out.println(a);

        String b = maxArrayDeque1.max();
        System.out.println(b);
    }*/
}
