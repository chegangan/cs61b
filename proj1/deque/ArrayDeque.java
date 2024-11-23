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
        nextFirstAdd();
        size++;
    }
    /*
     1 2 3 4 5 _ 7 8
     nextfirst = 5 , nextlast = 5
     _ 1 2 3 4 5 6 7
     nextfirst = 0 , nextlast = 0
     1 2 3 4 5 _ _ _ _ _ _ _ _ _ 7 8
     nextfirst = 5 , nextlast = 14
     7 8 1 2 3 4 5 _ _ _ _ _ _ _ _ _
     nextfirst = size , nextlast = lenth-1
     */

    public void reviseSize(double rate) {
        T[] newItems = (T[]) new Object[(int) (items.length * rate)];
        int index = nextLast + 1;
        for (int i = 0; i < size; i++) {
            index = index > size ? 0 : index;
            newItems[i] = items[index];
            index++;
        }
        nextLast = newItems.length - 1;
        nextFirst = size;
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
        nextLastReduce();
        size++;
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
        if(size == 0){
            return null;
        }
        if (size * 4 < items.length && items.length > 8) {
            reviseSize(0.5);
        }
        nextFirstReduce();
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
        if(size == 0){
            return null;
        }
        if (size * 4 < items.length && items.length > 8) {
            reviseSize(0.5);
        }
        nextLastAdd();
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of range.");
        }
        int realIndex = nextFirst - 1 - index;
        if (realIndex < 0) {
            realIndex += items.length;
        }
        return items[realIndex];
    }

    private void nextFirstAdd() {
        nextFirst++;
        if (nextFirst == items.length) {
            nextFirst = 0;
        }
    }

    private void nextLastAdd() {
        nextLast++;
        if (nextLast == items.length) {
            nextLast = 0;
        }
    }

    private void nextFirstReduce() {
        nextFirst--;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
    }

    private void nextLastReduce() {
        nextLast--;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }
    }
}
