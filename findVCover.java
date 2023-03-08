// This program takes an undirected graph G and returns a minimum vertex cover in G.
// The problem is not solved directly, the problem is reduced to a maximum clique
// problem to be solved by the findClique.java program.

import java.io.*;
import java.util.*;

class findVCover
{
    private static ArrayList<int[][]> complements = new ArrayList<int[][]>(50);

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

    public static void main(String[] args) {
        saveMatricesAsComplements("graphs.txt");

        // prints all complement graphs
        // int[][] comp;
        
        // for (int i = 0; i < complements.size(); i++)
        // {
        //     comp = complements.get(i);
        //     printMatrix(comp);
        //     System.out.println();
        // }
        
        // System.out.println("Done!");
    }
}