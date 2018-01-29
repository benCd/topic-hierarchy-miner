package preprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HierarchicalTM_Preprocessor
{
    private String input;
    private String output;
    private String currentLine;

    /**
     * Argument listing:
     * keep_files TRUE/FALSE || 1/0 (default true): Decides whether to keep the files after processing or not
     * delimiters XXXX,YYYY,ZZZZ (default null): The delimiters used in the splitting of the files and the extraction of the data, separated by commas
     * keep XXXX,YYYY,ZZZZ (default null): The content of the delimiters, which's data should be used in the run (pushed into files)
     * separate_files TRUE/FALSE || 1/0 (default false): Defines whether to separate delimiter content or not into different files for the analysis
     *
     */

    public HierarchicalTM_Preprocessor(String input, String ouput, String[] stringArgs, boolean[] boolArgs)
    {

    }

    public ArrayList<String> createString()
    {
        ArrayList<String> out = new ArrayList<>();

        try
        {
            Scanner get = new Scanner(new BufferedReader(new FileReader(new File(input))));

            while(get.hasNextLine())
                out.add(get.nextLine());
        }
        catch (FileNotFoundException e)
        {
            System.out.println(input + " was not found!");
        }
        return out;
    }

    public ArrayList<String[]> processLines(ArrayList<String> list)
    {
        ArrayList<String[]> out = new ArrayList<>();
        String line;
        String temp[];

        for(int i = 0; i < list.size(); i++)
        {
            line = list.get(i);
            if(line.contains("<row>"))
            {
                out.add(line.split(" Body="));
            }
        }
        return out;
    }

    public void characterManagement(ArrayList<String[]> list)
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

    private void outPutFiles(String path, String filename)
    {
        BufferedWriter write;


        //write = new BufferedWriter(new FileWriter(new File(filename)));
    }
}
