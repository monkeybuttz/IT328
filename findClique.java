import java.io.*;
import java.util.*;

class findClique
{

    int[][] matrix;

    public void findMaxClique(int m[][])
    {

    }

    public static void main(String[] args)
    {
        try {
            File myObj = new File("file.txt");
            Scanner scan = new Scanner(myObj);

            int size = scan.nextInt();
            String[] data2 = new String[size];

            while(scan.hasNextLine())
            {
                String data = scan.nextLine();
                data2 = data.split("\\s+");
                
                for(int i = 0; i < data2.length; i++)
                {
                    
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}