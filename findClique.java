import java.io.*;
import java.util.*;

class findClique {

    private static ArrayList<int[][]> matrixes = new ArrayList<int[][]>(50);

    // returns maxiumum subgraph of a graph, returns as an array of vertices using recusrsion
    // graph is undirected graph
    // node is the vertex in which the recursion starts
    // clique is the array that will be returned
    // **if graph has over 30 vertices, it may take a long time to run**
    public static int[] findMaxClique(int[][] graph, int node, int[] clique) {
        // base case
        if (node == graph.length) {
            return clique;
        }

        // if node is not adjacent to any vertex in clique, add it to clique
        if (isAdjacent(graph, node, clique)) {
            clique = addNode(clique, node);
        }

        // find max clique with node added to clique
        int[] clique1 = findMaxClique(graph, node + 1, clique);

        // find max clique with node not added to clique
        int[] clique2 = findMaxClique(graph, node + 1, removeNode(clique, node));

        // return max clique
        if (clique1.length > clique2.length) {
            return clique1;
        } else {
            return clique2;
        }
    }

    // removes node from clique array
    private static int[] removeNode(int[] clique, int node) {
        int[] newClique = new int[clique.length - 1];
        int index = 0;
        for (int i = 0; i < clique.length-1; i++) {
            if (clique[i] != node) {
                newClique[index] = clique[i];
                index++;
            }
        }
        return newClique;
    }

    // adds node to clique array
    private static int[] addNode(int[] clique, int node) {
        int[] newClique = new int[clique.length + 1];
        for (int i = 0; i < clique.length; i++) {
            newClique[i] = clique[i];
        }
        newClique[clique.length] = node;
        return newClique;
    }

    // returns true if node is adjacent to all vertices in clique
    // part of maximal clique algorithm
    private static boolean isAdjacent(int[][] graph, int node, int[] clique) {
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
    // stores graphs in matrixes arraylist
    public static void saveMatrixes(String pathname) {
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
                matrixes.add(matrix);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //driver code for the problem
    public static void main(String[] args) {
        saveMatrixes("graphs.txt");
        for(int i=0; i<matrixes.size();i++){
            int clique [] = new int[0];
            int [] kClique = findMaxClique(matrixes.get(i), 0, clique);
            for(int j=0; j<kClique.length; j++){
                if(j == kClique.length-1){
                    System.out.print(kClique[j]);
                    break;
                }
                System.out.print(kClique[j] + ", ");
            }
            System.out.println('\n');
        }
    }
}