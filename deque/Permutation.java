import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> bag = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            bag.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++)
            StdOut.println(bag.dequeue());
    }
}