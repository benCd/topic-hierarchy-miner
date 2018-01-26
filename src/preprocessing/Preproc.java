package preprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Preproc
{
    public static void main(String[] args) {

        String fn = "/home/ben/Documents/Research/so-soft-eng/Posts.xml";

        ArrayList<String> strings = createString(fn);
        ArrayList<String[]> stringArrays = processLines(strings);
        characterManagement(stringArrays);
    }

    private static ArrayList<String> createString(String file)
    {
        ArrayList<String> out = new ArrayList<>();

        try
        {
            Scanner get = new Scanner(new BufferedReader(new FileReader(new File(file))));

            while(get.hasNextLine())
                out.add(get.nextLine());
        }
        catch (FileNotFoundException e)
        {
            System.out.println(file + " was not found!");
        }
        return out;
    }

    private static ArrayList<String[]> processLines(ArrayList<String> list)
    {
        ArrayList<String[]> out = new ArrayList<>();
        String line;
        String temp[];

        for(int i = 0; i < list.size(); i++)
        {
            line = list.get(i);
            if(line.contains("row"))
            {
                out.add(line.split(" Body="));
            }
        }
        return out;
    }

    private static void characterManagement(ArrayList<String[]> list)
    {
        final int SIZE = list.size();
        String[] temp;
        Scanner get;

        for(int i = 0; i < SIZE; i++)
        {
            temp = list.get(i);
            temp[1] = temp[1].replaceAll("&lt;", "<");
            temp[1] = temp[1].replaceAll("&gt;", ">");
            temp[1] = temp[1].replaceAll("&#xA;", "");
            temp[1] = temp[1].replaceAll("&quot;", "");
            temp[1] = temp[1].replaceAll("&amp;", "&");
            temp[1] = temp[1].replaceAll("\"", "");
            temp[1] = temp[1].replaceAll("\\.", "");
            temp[1] = temp[1].replaceAll(",", "");
            temp[1] = temp[1].replaceAll(":", "");
            temp[1] = temp[1].replaceAll(";", "");

            list.set(i, temp);
        }
    }

    private static void outPutFiles(String path, String filename)
    {
        BufferedWriter write;


        //write = new BufferedWriter(new FileWriter(new File(filename)));
    }
}
