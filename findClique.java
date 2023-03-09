import java.io.*;
import java.util.*;

// This problem takes an undirected graph G and returns a maximum clique.
// This problem helps us also solve the Vertex Cover problem.
// IT 328
// Assignment 1
// Greg Yonan

class findClique {

    // primary data structure to store graphs
    private static ArrayList<int[][]> matrices = new ArrayList<int[][]>(50);

    // takes graph and calls recursive method with list of vertices
    // returns maximum clique
    public static int[] findMaxClique(int[][] graph) {
        int size = graph.length;
        int[] clique = new int[0];
        List<Integer> candidateList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            candidateList.add(i);
        }
        // recursive algorithm to find max clique
        clique = branchAndBound(graph, new int[0], candidateList, clique);
        return clique;
    }

    // recursive branch and bound algorithm
    // takes undirected graph, returns maximum clique
    private static int[] branchAndBound(int[][] graph, int[] current, List<Integer> candidates, int[] best) {
        // base case, graph has no vertices, return empty clique
        if (candidates.isEmpty()) {
            return current;
        }
        // if maximum possible clique has already been found, return current best
        if (current.length + candidates.size() <= best.length) {
            return best;
        }

        int[] clique = current;
        for (int i = 0; i < candidates.size(); i++) {
            int vertex = candidates.get(i); // current vertex
            if (current.length == 0 || isAdjacent(graph, vertex, current)) {
                List<Integer> newCandidates = new ArrayList<>();
                for (int j = i + 1; j < candidates.size(); j++) {
                    int node = candidates.get(j);
                    if (isAdjacent(graph, node, current)) {
                        newCandidates.add(node);
                    }
                }
                // add vertex to new current clique
                int[] newCurrent = addNode(current, vertex);
                // recursive call to find max clique
                int[] newClique = branchAndBound(graph, newCurrent, newCandidates, clique);
                // if new found clique is better than old best clique, update
                if (newClique.length > clique.length) {
                    clique = newClique;
                }
            }
        }
        return clique;
    }

    // add node to clique by creating new array and copying old clique to new
    public static int[] addNode(int[] oldClique, int vertex) {
        int[] newClique = new int[oldClique.length + 1];
        // copy old clique to new
        for (int i = 0; i < oldClique.length; i++) {
            newClique[i] = oldClique[i];
        }
        // add node to end of new clique
        newClique[oldClique.length] = vertex;
        return newClique;
    }

    // returns true only if vertex is adjacent to all vertices in clique
    // part of maximum clique algorithm
    private static boolean isAdjacent(int[][] graph, int vertex, int[] clique) {
        if (clique.length == 0) {
            return true;
        }
        for (int i = 0; i < clique.length; i++) {
            if (graph[vertex][clique[i]] == 0) {
                return false;
            }
        }
        return true;
    }

    // count the number of edges in a graph
    // used to determine |E| in toString method
    public static int findEdges(int[][] graph) {
        int edges = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] == 1 && i != j) {
                    edges++;
                }
            }
        }
        return edges / 2;
    }

    // input a file path with undirected graphs
    // stores graphs in matrices arraylist, returns nothing
    public static void saveMatrices(String pathname) {
        try {
            File myObj = new File(pathname);
            Scanner scan = new Scanner(myObj);
            while (scan.hasNextLine()) {
                // read size of matrix from file and create matrix
                int size = scan.nextInt();
                String[] matrixData = new String[size];
                scan.nextLine();
                int matrix[][] = new int[size][size];
                // loop through remaining lines of matrix in the file
                int row = 0;
                while (scan.hasNextLine()) {
                    String data = scan.nextLine();
                    matrixData = data.split("\\s+"); // splits white space, stores characters in String array
                    // store line of numbers to corresponding row in matrix
                    for (int i = 0; i < size; i++) {
                        matrix[row][i] = Integer.parseInt(matrixData[i]);
                    }
                    row++;
                    if (row == size)
                        break;
                }
                matrices.add(matrix);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // formats and prints results of findMaxClique() call
    public static String toString(int[][] matrix, int iterations) {
        long startTime = System.currentTimeMillis();
        int[] clique = findMaxClique(matrix);
        long timeDifference = System.currentTimeMillis() - startTime;
        return "G" + iterations + " (" + matrix.length + ", " + findEdges(matrix) + ")  (size=" + clique.length + " ms="
                + timeDifference + ")  " + Arrays.toString(clique) + "";
    }

    // driver code for the Maximum Clique problem
    public static void main(String[] args) {

        // validate number of arguments
        if (args.length != 1) {
            System.out.println("Proper Usage is: java findClique graphs.txt");
            System.exit(0);
        }
        saveMatrices(args[0]);
        System.out.println("\n* Max Cliques in graphs.txt *");
        System.out.println("    (|V|,|E|)  (size, ms used)  (Cliques)");
        // loop through each graph in matrices arraylist and print toString
        for (int i = 0; i < matrices.size() - 1; i++) {
            System.out.println(toString(matrices.get(i), i + 1));
        }
    }
}