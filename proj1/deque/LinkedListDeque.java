package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private class Node<T> {
        private T value;
        private Node<T> pre;
        private Node<T> next;

        public Node(T value, Node<T> pre, Node<T> next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }

    private Node<T> sentinel;
    private int size;


    /**
     * Creates an empty deque.
     */
    public LinkedListDeque() {
        sentinel = new Node<T>(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }

    // 第一个是sentinel.next
    public void addFirst(T value) {
        Node<T> item = new Node<T>(value, sentinel, sentinel.next);
        sentinel.next.pre = item;
        sentinel.next = item;
        size++;
    }

    public void addLast(T value) {
        Node<T> item = new Node<T>(value, sentinel.pre, sentinel);
        sentinel.pre.next = item;
        sentinel.pre = item;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node<T> current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.value.toString() + " ");
            current = current.next;
        }
    }

    public T removeFirst() {
        if (!isEmpty()) {
            T value = (T) sentinel.next.value;
            sentinel.next = sentinel.next.next;
            sentinel.next.pre = sentinel;
            size--;
            return value;
        }
        return null;
    }

    public T removeLast() {
        if (!isEmpty()) {
            T value = (T) sentinel.pre.value;
            sentinel.pre = sentinel.pre.pre;
            sentinel.pre.next = sentinel;
            size--;
            return value;
        }
        return null;
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LinkedListDeque) {
            if (size == ((LinkedListDeque) obj).size()) {
                Node<T> current1 = sentinel.next;
                Node<T> current2 = ((LinkedListDeque) obj).sentinel.next;
                while (current1 != sentinel) {
                    if (!current1.value.equals(current2.value)) {
                        return false;
                    }
                    current1 = current1.next;
                    current2 = current2.next;
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    @Override
    public T get(int index) {
        DequeIterator iterator = new DequeIterator();
        if(this.size == 0){
            return null;
        }
        for (int i = 0; i < index - 1; i++) {
            iterator.next();
        }
        return iterator.next();
    }


    /**
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
        private Node<T> current = sentinel.next;

        /**
         * @return
         */
        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        /**
         * @return
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T value = current.value;
            current = current.next;
            return value;
        }
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) getRecursive0(sentinel.next, index);

    }

    public T getRecursive0(Node current, int index) {
        if (index != 0) {
            return (T) getRecursive0(current.next, index - 1);
        }
        return (T) current.value;
    }
}
