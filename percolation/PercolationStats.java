import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n = 0;
    private int trials = 0;
    private double[] scores;
    // perform independent trials on an n-by-n grid
    /**
     * @param n
     * number of sites in the system
     * @param trials
     * Number of trials performed for statistics.
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0) 
            throw new IllegalArgumentException("n should be a positive integer");
        
        if (trials <= 0) 
            throw new IllegalArgumentException("trials should be a positive integer");
            
        this.n = n;
        this.trials = trials;
        this.scores = new double[trials];

        for (int t = 0; t < trials; t++) {
            scores[t] = performExperiment();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(scores);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(scores);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (this.mean() - 1.96 * this.stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (this.mean() + 1.96 * this.stddev() / Math.sqrt(trials));
    }

    // perform experiment
    private double performExperiment() {
        Percolation system = new Percolation(n);
        while (!system.percolates()) {
            int row=0, col=0;
            // Get an unopened site at random
            do {
                row = StdRandom.uniformInt(1, n+1);
                col = StdRandom.uniformInt(1, n+1);
            } while (system.isOpen(row, col));

            // Open the site.
            system.open(row, col);
        }
        int sizeOfLattice = n * n;
        return (system.numberOfOpenSites() * 1.0/sizeOfLattice);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) return;

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo()+", " + stats.confidenceHi()+"]");
   }

}
