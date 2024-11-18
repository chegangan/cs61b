package deque;


//0 1 2 3 _ _ _ 4 5 6 7 则3为最前，4为最后，nextFirst=4, nextLast=7
public class ArrayDeque<T> implements Deque<T> {
    T[] items;
    int nextFirst;
    int nextLast;
    int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = items.length - 1;
    }

    // 0 1 2 3 4
    // newitems.length = 10 items.lenth = 5  后面从索引2开始复制，到索引4   newitems的索引从7开始 8 9
    //addfirst = 2   1  复制2次，i=2
    //addlast = 2   3的索引为addlast+1 ，一直到索引为lenth-1，lenth-1也要复制一次

    /**
     * @param item
     */
    @Override
    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            reviseSize(2);
        }
        items[nextFirst] = item;
        nextFirst++;
        size++;
    }

    public void reviseSize(double rate) {
        T[] newItems = (T[]) new Object[(int) (items.length * rate)];
        for (int i = 0; i < nextFirst; i++) {
            newItems[i] = items[i];
        }
        for (int i = nextLast + 1; i < items.length; i++) {
            newItems[newItems.length - i - 1] = items[i];
        }
        nextLast = newItems.length - items.length + nextLast + 1;
        items = newItems;
    }

    /**
     * @param item
     */
    @Override
    public void addLast(T item) {
        if (nextFirst == nextLast) {
            reviseSize(2);
        }
        items[nextLast] = item;
        nextLast--;
        size++;
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     *
     */
    @Override
    public void printDeque() {
        for (int i = nextFirst - 1; i >= 0; i--) {
            System.out.print(items[i] + " ");
        }
        for (int i = items.length - 1; i >= nextLast + 1; i--) {
            System.out.print(items[i] + " ");
        }
    }

    /**
     * @return
     */
    @Override
    public T removeFirst() {
        if ((nextLast - nextFirst + 1) * 4 < items.length) {
            reviseSize(0.5);
        }
        nextFirst--;
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return item;
    }

    /**
     * @return
     */
    @Override
    public T removeLast() {
        if ((nextLast - nextFirst + 1) * 4 < items.length) {
            reviseSize(0.5);
        }
        nextLast++;
        T item = items[nextLast];
        items[nextLast] = null;
        size--;
        return item;
    }

    /**
     * @param index
     * @return
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range.");
        }
        int realIndex = nextFirst - 1 - index;
        if (realIndex < 0) {
            realIndex += items.length;
        }
        return items[realIndex];
    }
}
