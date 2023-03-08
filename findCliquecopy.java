import java.io.*;
import java.util.*;

class findCliquecopy {

    private static ArrayList<int[][]> matrices = new ArrayList<int[][]>(50);

    // maximum clique helper method
    // takes graph and calls recursive method with list of vertices
    // returns maximum clique
    public static int[] findMaxClique(int[][] graph) {
        int size = graph.length;
        int[] clique = new int[0];
        List<Integer> candidateList = new ArrayList<>();

        // add vertices to candidate list
        for (int i = 0; i < size; i++) {
            candidateList.add(i);
        }

        // call algorithm to find max clique
        clique = branchAndBound(graph, new int[0], candidateList, clique);
        return clique;
    }
    
    // recursive maximum clique finder using branch and bound algorithm
    // takes undirected graph, current found clique array, list of vertices, and current max clique array
    // returns maximum clique 
    private static int[] branchAndBound(int[][] graph, int[] current, List<Integer> candidates, int[] curr_best) {
        // graph has no vertices, return empty clique
        if (candidates.isEmpty()) {
            return current;
        }

        // maximum possible clique has already been found, return current best
        if (current.length + candidates.size() <= curr_best.length) {
            return curr_best;
        }

        int[] clique = current;

        // for all candidates/vertices
        for (int i = 0; i < candidates.size(); i++) 
        {
            int v = candidates.get(i); // current vertex

            // if current clique is empty or v is adjacent to current clique,
            // add v to newCurrent and call branch and bound with only
            // candidates after v and adjacent to current
            if (current.length == 0 || isAdjacent(graph, v, current)) 
            {
                List<Integer> newCandidates = new ArrayList<>();

                // loops through candidates after v
                for (int j = i + 1; j < candidates.size(); j++) 
                {
                    int w = candidates.get(j);
                    if (isAdjacent(graph, w, current)) 
                    {
                        newCandidates.add(w);
                    }
                }
                // add v to new current clique
                int[] newCurrent = addNode(current, v);

                // find new max clique
                int[] newClique = branchAndBound(graph, newCurrent, newCandidates, clique);
                
                // if new found clique is better than old best clique, update
                if (newClique.length > clique.length) {
                    clique = newClique;
                }
            }
        }
        return clique;
    }

    // this method is a helper for the find max clique recursive method
    // takes a current clique array, adds a node, and returns the new clique array
    public static int[] addNode(int[] oldClique, int node) 
    {
        int[] newClique = new int[oldClique.length + 1];

        // copy old clique to new
        for (int i = 0; i < oldClique.length; i++)
        {
            newClique[i] = oldClique[i];
        }

        // add node to end of new clique
        newClique[oldClique.length] = node;

        return newClique;
    }

    // returns true if node is adjacent to all vertices in clique
    // part of maximum clique algorithm
    private static boolean isAdjacent(int[][] graph, int node, int[] clique) 
    {
        if (clique.length == 0) {
            return true;
        }
        for (int i = 0; i < clique.length; i++) {
            if (graph[node][clique[i]] == 0) {
                return false;
            }
        }
        return true;
    }

    // prints 2D array as a matrix
    // good for testing
    public static void printMatrix(int[][] mat) {
        // loop through rows
        for (int i = 0; i < mat.length; i++) {
            // loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // input file path with undirected graphs
    // stores graphs in matrices arraylist
    public static void saveMatrices(String pathname) {
        try {
            File myObj = new File(pathname);
            Scanner scan = new Scanner(myObj);

            while (scan.hasNextLine()) {
                // size read from graph file
                int size = scan.nextInt();
                String[] data2 = new String[size];
                scan.nextLine();

                // set matrix size
                int matrix[][] = new int[size][size];

                // for looping through matrix
                int row = 0;

                // loop through remaining lines of file
                while (scan.hasNextLine()) {
                    String data = scan.nextLine(); // reads line from graph file
                    data2 = data.split("\\s+"); // splits white space, stores characters in array

                    // store line of numbers to corresponding row in matrix
                    for (int i = 0; i < size; i++) {
                        matrix[row][i] = Integer.parseInt(data2[i]); // cast string to int, store in matrix
                    }
                    row++;
                    if(row == size) break; // break out of loop if all rows are filled
                }
                matrices.add(matrix);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //driver code for the problem
    public static void main(String[] args) {
        saveMatrices("graphs.txt");
        for(int i=0; i<matrices.size();i++){
            int clique [] = findMaxClique(matrices.get(i));
            for(int j=0; j<clique.length; j++){
                if(j == clique.length-1){
                    System.out.print(clique[j]);
                    break;
                }
                System.out.print(clique[j] + ", ");
            }
            System.out.println('\n');
        }
    }
}