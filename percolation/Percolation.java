import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private static final boolean OPEN = true;
    private static final boolean BLOCKED = false;
    private static final int VIRT_TOP = 0;

    private int n;
    private WeightedQuickUnionUF uf;

    private boolean[] grid;
    private int numOfOpenSites;
    private int virtBot; 



    // creates n-x-n grid with all sides initially blocked
    public Percolation(int n) {

        if (n <= 0) 
            throw new IllegalArgumentException("n should be a positive integer");

        this.n = n;
        numOfOpenSites = 0;

        // Total size of the grid is nxn plus 2 virtual sites for top and bottom.
        virtBot = n*n + 1;
        this.grid = new boolean[n*n + 2];
        
        // Initialize all sites in blocked state
        for (int i = 0; i < grid.length; i++) grid[i] = BLOCKED;

        // Create a UF based on the grid.
        uf = new WeightedQuickUnionUF(n*n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("invalid coordinates " + row + ", " + col);
        
        
        int ix = coordToIndex(row, col);
        // Do nothing if it is already open
        if (grid[ix] == OPEN) return;

        // set status to OPEN
        grid[ix] = OPEN;
        numOfOpenSites++;

        // Join to neighbors if they are OPEN.
        joinNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("invalid coordinates " + row + ", " + col);

        int ix = coordToIndex(row, col);
        
        if (grid[ix] == OPEN) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        return uf.find(coordToIndex(row, col)) == uf.find(VIRT_TOP);
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        return (uf.find(VIRT_TOP) == uf.find(virtBot));
    }

    // converts pair of coordinates on grid to index of the array
    private int coordToIndex(int row, int col) {
        // coordinates run from (1, 1) to (n, n). 0 and n*n+1 are reserved for virtual sites. 
        // so, the actual index ix = n * (row -1) + col.
        return (row - 1) * n + col;
    }

    private void joinNeighbors(int row, int col) {
        int ix = coordToIndex(row, col);    

    // Join to virtual top and virtual bottom if row is 1 or n respectively.    
        if (row == 1) uf.union(VIRT_TOP, ix);

        if (row == n) uf.union(virtBot, ix);

    // join to top, bot, left, and right as applicable.

        if (row > 1 && isOpen(row-1, col)) {
            int topIx = coordToIndex(row-1, col);
            uf.union(topIx, ix);
        }

        if (row < n && isOpen(row+1, col)) {
            int botIx = coordToIndex(row+1, col);
            uf.union(botIx, ix);
        }
          
        if (col > 1 && isOpen(row, col-1)) {
            int leftIx = coordToIndex(row, col-1);
            uf.union(leftIx, ix);
        }

        if (col < n && isOpen(row, col+1)) {
            int rightIx = coordToIndex(row, col+1);
            uf.union(rightIx, ix);
        }
    }

    private void drawGrid() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                StdOut.print(grid[coordToIndex(i, j)]);
            }       
            StdOut.println();
        }
    }
        
    public static void main(String[] args) {
        Percolation p = new Percolation(4);

        p.drawGrid();
        p.open(1, 1);
        StdOut.println("p is open at 1, 1? " + p.isOpen(1, 1));
        StdOut.println("number of open sites : " + p.numberOfOpenSites());
        StdOut.println("1, 1 is full? " + p.isFull(1, 1));

        p.drawGrid();
        p.open(1, 2);
        StdOut.println("p is open at 1, 2? " + p.isOpen(1, 2));
        StdOut.println("number of open sites : " + p.numberOfOpenSites());
        StdOut.println("1, 2 is full? " + p.isFull(1, 2));
        
        p.drawGrid();
        p.open(1, 3); 
        StdOut.println("p is open at 1, 3? " + p.isOpen(1, 3));
        StdOut.println("1, 3 is full? " + p.isFull(1, 3));
          
        p.drawGrid();
        p.open(1, 4);
        StdOut.println("p is open at 1, 4? " + p.isOpen(1, 4));
        StdOut.println("number of open sites : " + p.numberOfOpenSites());
        StdOut.println("1, 4 is full? " + p.isFull(1, 4));
        
        StdOut.println("percolates? : " + p.percolates());
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        p.open(4, 3);

        StdOut.println("percolates? : " + p.percolates());
         
        p.drawGrid();
    }

}
