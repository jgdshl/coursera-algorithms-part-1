import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int size = 0;

    public RandomizedQueue()
    {
        arr = (Item[]) new Object[1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot enqueue null");

        if (size == arr.length)
            resize(2 * arr.length);

        arr[size++] = item;
    }

    public Item dequeue() {
        if (size() == 0)
            throw new NoSuchElementException("Cannot dequeue an empty bag");

        // sample a random element and return.
        // pop from end, and replace at sampled index.
        int index = StdRandom.uniformInt(size);
        Item item = getItemAt(index);

        Item replacingItem = pop();
        setAt(replacingItem, index);

        return item;
    }

    public Item sample() {
        if (size() == 0)
            throw new NoSuchElementException("Cannot sample an empty bag");
        
        // sample a random element and return.
        int index = StdRandom.uniformInt(size);
        Item item = getItemAt(index);

        return item;
    }

    private Item pop() {
        Item item = arr[--size];
        arr[size] = null;

        if (size > 0 && size == arr.length / 4)
            resize(arr.length / 2);

        return item;
    }

    private Item getItemAt(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Index out of bounds");

        return arr[index];
    }

    private void setAt(Item item, int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Index out of bounds");

        arr[index] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return size() > 0;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more items to fetch");

            return dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Method not supported");
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Double> bag = new RandomizedQueue<>();

        StdOut.println("The bag is empty?: " + bag.isEmpty());

        bag.enqueue(4.5);

        StdOut.println("The bag is empty?: " + bag.isEmpty());

        bag.enqueue(3.5);
        bag.enqueue(4.2);

        StdOut.println("size of the bag is=" + bag.size());
        StdOut.println("dequeuing all data");

        for (double item : bag) StdOut.println(item);

        StdOut.println("size of the bag is=" + bag.size());

        bag.enqueue(0.1);
        bag.enqueue(0.2);
        bag.enqueue(0.3);
        bag.enqueue(0.4);
        bag.enqueue(0.5);
        bag.enqueue(0.6);

        StdOut.println("sampling an item=" + bag.sample());
        StdOut.println("size remains=" + bag.size());
        StdOut.println("removing a random item " + bag.dequeue());
        StdOut.println("size is now=" + bag.size());

        bag.enqueue(1.1);
        bag.enqueue(1.2);

        StdOut.println("dequeuing all data");
        for (double item : bag) StdOut.println(item);

        StdOut.println("size is now=" + bag.size());
    }

}