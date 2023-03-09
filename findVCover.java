// This program takes an undirected graph G and returns a minimum vertex cover in G.
// The problem is not solved directly, the problem is reduced to a maximum clique
// problem to be solved by the findClique.java program.
// IT 328 Section 1
// Spring 2023
// By Kaden Hargrove

import java.io.*;
import java.util.*;

class findVCover extends findClique
{
    // primary data structure to store complement graphs from graphs.txt
    private static ArrayList<int[][]> complements = new ArrayList<int[][]>(50);

    // stores graphs in complements arraylist
    // converts graphs to complement graphs
    public static void saveMatricesAsComplements(String pathname) {
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
                    // save graphs as complements
                    for (int i = 0; i < size; i++) {
                        int temp = Integer.parseInt(data2[i]); // cast string to int

                        if (temp == 1)
                        {
                            temp = 0;
                        }
                        else
                        {
                            temp = 1;
                        }
                        matrix[row][i] = temp; // store complement value in matrix
                    }
                    row++;
                    if(row == size) break; // break out of loop if all rows are filled
                }
                complements.add(matrix);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //call find max clique function from findClique.java and print results
    public static String toString(int[][] matrix, int iterations) {
        long startTime = System.currentTimeMillis();
        int[] clique = findMaxClique(matrix);
        long timeDifference = System.currentTimeMillis() - startTime;

        //create set of vertices in graph
        Set<Integer> vertices = new HashSet<Integer>();
        for (int i = 0; i < matrix.length; i++) {
            vertices.add(i);
        }

        //convert found clique array to set
        Set<Integer> cliqueVertices = new HashSet<Integer>();
        for (int i = 0; i < clique.length; i++)
        {
            cliqueVertices.add(clique[i]);
        }

        //find difference of sets 
        //min vertex cover = vertices - vertices in max clique of complement graph
        vertices.removeAll(cliqueVertices);

        return "G" + iterations + " (" + matrix.length + ", " + findEdgesInOriginalGraph(matrix) + ")  (size=" + vertices.size() + " ms="
                + timeDifference + ")  " + vertices.toString() + "";
    }

    // returns the number of edges in the non-complement graph
    // used to determine |E| in toString method
    public static int findEdgesInOriginalGraph(int[][] graph) {
        int edges = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] == 0 && i != j) {
                    edges++;
                }
            }
        }
        return edges / 2;
    }

    // driver code for the Minimum Vertex Cover problem
    public static void main(String[] args) {
        // validate number of arguments
        if (args.length != 1) {
            System.out.println("Proper Usage is: java findVCover graphs.txt");
            System.exit(0);
        }

        //save matrices from graph file
        saveMatricesAsComplements("graphs.txt");

        System.out.println("\n* A Minimum Vertex Cover of every graph in graphs.txt: (reduced to K-Clique) *");
        System.out.println("    (|V|,|E|)  (size, ms used)  Vertex Cover");

        // loop through each graph in complements arraylist and print toString
        for (int i = 0; i < complements.size() - 1; i++) {
            System.out.println(toString(complements.get(i), i + 1));
        }
    }
}