// This program takes an undirected graph G and returns a minimum vertex cover in G.
// The problem is not solved directly, the problem is reduced to a maximum clique
// problem to be solved by the findClique.java program.

import java.io.*;
import java.util.*;

class findVCover
{
    private static int[][] matrix;
    private static int[][] complement;

    // constructs complement graph for clique problem reduction
    public static int[][] constructComplement(int[][] mat)
    {
        int size = mat.length;
        int[][] comp = new int[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (mat[i][j] == 1)
                {
                    comp[i][j] = 0;
                }
                else
                {
                    comp[i][j] = 1;
                }
            }
        }
        
        return comp;
    }

    // prints 2D array
    public static void printMatrix(int mat[][]) {
        // loop through rows
        for (int i = 0; i < mat.length; i++) {
            // loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    // input file path with undirected graphs
    public static void saveMatrix(String pathname) {
        try {
            File myObj = new File(pathname);
            Scanner scan = new Scanner(myObj);

            // size read from graph file
            int size = scan.nextInt();
            String[] data2 = new String[size];
            scan.nextLine();

            // set matrix size
            matrix = new int[size][size];

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
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        saveMatrix("file.txt");
        printMatrix(matrix);
        
        System.out.println();

        complement = constructComplement(matrix);
        printMatrix(complement);
    }
}