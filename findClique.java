import java.io.*;
import java.util.*;

class findClique {

    private static ArrayList<int[][]> matrixes = new ArrayList<int[][]>(50);
    private static int[][] clique;
    private static int[] edges;

    // find max clique of a undirected graph
    public static int findMaxClique(List<List<Integer>> edges, int nodes) {
        int maxClique = 0;
        return maxClique;
    }

    // find number of nodes in graph based on length of matrix
    public static int findVerticies(int m[][]) {
        int nodes = m.length;
        System.out.println(nodes);
        return nodes;
    }

    // find edges in a graph and save them in a list of pairs
    public static List<List<Integer>> findEdges(int m[][]) {
        List<List<Integer>> edges = new ArrayList<List<Integer>>();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == 1) {
                    List<Integer> edge = new ArrayList<Integer>();
                    edge.add(i);
                    edge.add(j);
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    // prints 2D array
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

    public static void main(String[] args) {
        saveMatrixes("graphs.txt");
        for(int i=0; i<matrixes.size();i++){
            printMatrix(matrixes.get(i));
        }

    }
}