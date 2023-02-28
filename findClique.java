import java.io.*;
import java.util.*;

class findClique
{

    private static int[][] matrix;

    public void findMaxClique(int m[][])
    {

    }

    //prints 2D array
    public static void printMatrix(int mat[][])
    {
        //loop through rows
        for (int i = 0; i < mat.length; i++)
        {
            //loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++)
            {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        try {
            File myObj = new File("file.txt");
            Scanner scan = new Scanner(myObj);

            //size read from graph file
            int size = scan.nextInt();
            String[] data2 = new String[size];
            scan.nextLine();

            //set matrix size
            matrix = new int[size][size];

            //for looping through matrix
            int row = 0;

            //loop through remaining lines of file
            while(scan.hasNextLine())
            {
                String data = scan.nextLine(); //reads line from graph file
                data2 = data.split("\\s+"); //splits white space, stores characters in array

                //store line of numbers to corresponding row in matrix
                for (int i = 0; i < size; i++)
                {
                    matrix[row][i] = Integer.parseInt(data2[i]); //cast string to int, store in matrix
                }
                row++;
            }
            scan.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        printMatrix(matrix); //print the stored matrix!
    }
}