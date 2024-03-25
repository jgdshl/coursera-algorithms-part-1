import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdPicture;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node head;
    private Node tail;
    private int size;

    public Deque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return (this.head == null);
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item cannot be null to addFirst");
        
        Node oldHead = head;
        
        head = new Node();
        head.item = item;

        size++;
        
        if (isEmpty()) {
            // this is the first element added
            tail = head;
            return;
        }

        // Not the first element. Join old elements
        head.next = oldHead;
        oldHead.prev = head;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item cannot be null to addLast");
        
        Node oldTail = tail;
        
        tail = new Node();
        tail.item = item;

        // increment size;
        size ++;

        if (isEmpty()) {
            //first element to be added;
            head = tail;
            return;
        }

        // Not the first element. Join old elements
        tail.prev = oldTail;
        oldTail.next = tail;
        
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot remove from empty deque");

        Item item = head.item;
        head = head.next;
        size--;
        
        return item;
    }


    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot remove from empty deque");

        Item item = tail.item;
        tail = tail.prev;
        size--;
        
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            
            if (!hasNext())
                throw new UnsupportedOperationException("No more items to fetch");
            
            current = current.next;
            return item;
        }
        
    }

    public static void main(String[] args) {
        
        // Create a new empty Deque
        Deque<Integer> d1 = new Deque<Integer>();

        StdOut.println("Is d1 empty?" + d1.isEmpty());

        d1.addLast(4);
        d1.addLast(5);
        d1.addLast(6);
        d1.addLast(7);

        StdOut.println("Added 4, 5, 6, 7 to Last");
        StdOut.println("Is d1 empty?" + d1.isEmpty());
        
        for (int item : d1) {
            StdOut.println("Item=" + item);
        }

        d1.addFirst(1);
        d1.addFirst(2);
        d1.addFirst(3);

        StdOut.println("Added 1, 2, 3 to First");
        
        for (int item : d1) {
            StdOut.println("Item=" + item);
        } 

        StdOut.println("Size=" + d1.size());

        StdOut.println("removed from first: " + d1.removeFirst());
        for (int item : d1) {
            StdOut.println("Item=" + item);
        } 
        StdOut.println("Size=" + d1.size());

        StdOut.println("removed from last: " + d1.removeLast());
        for (int item : d1) {
            StdOut.println("Item=" + item);
        } 
        StdOut.println("Size=" + d1.size());

    }

}