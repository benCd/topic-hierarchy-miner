package preprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HierarchicalTM_Preprocessor
{
    private String currentLine;

    //Argument variables
    private boolean keep_files = false,
                    separate_files = false;
    private String[] delimiters = null,
                     keep = null;
    private String  input = null,
                    output = null;

    /**
     * Argument listing:
     * --keep_files TRUE/FALSE || 1/0 (default false): Defines whether to keep the files after processing or not
     * --separate_files TRUE/FALSE || 1/0 (default false): Defines whether to separate delimiter content or not into different files for the analysis
     *
     * --delimiters XXXX,YYYY,ZZZZ (default null): The delimiters used in the splitting of the files and the extraction of the data, separated by commas
     * --keep XXXX,YYYY,ZZZZ (default null): The content of the delimiters, which's data should be used in the run (pushed into files)
     *
     * --input XXXX (default null -> throws Exception): Defines input file
     * --output XXXX (default input-filename_X.htmpp input-filename = name of input file, X = number of output): Defines output files, which will hold data
     */

    public HierarchicalTM_Preprocessor(String[] stringArgs)
    {
        String arg;

        //Reading arguments in arbitrary order

        for(int iterator = 0; iterator < stringArgs.length; iterator++)
        {
            arg = stringArgs[iterator];

            switch (arg)
            {
                case "--keep_files":
                {
                    if(iterator + 1 < stringArgs.length)
                    {
                        switch(stringArgs[++iterator].toLowerCase())
                        {
                            case "true":
                            case "1":
                            {
                                keep_files = true;
                                break;
                            }
                            case "false":
                            case "0":
                            {
                                keep_files = false;
                                break;
                            }

                        }
                    }

                    break;

                }
                case "--separate_files":
                {
                    if(iterator + 1 < stringArgs.length)
                    {
                        switch(stringArgs[++iterator].toLowerCase())
                        {
                            case "true":
                            case "1":
                            {
                                separate_files = true;
                                break;
                            }
                            case "false":
                            case "0":
                            {
                                separate_files = false;
                                break;
                            }

                        }
                    }

                    break;

                }
                case "--delimiters":
                {
                    if(iterator + 1 < stringArgs.length)
                    {
                        delimiters = stringArgs[++iterator].split(",");
                    }
                    break;
                }
                case "--keep":
                {
                    if(iterator + 1 < stringArgs.length)
                    {
                        keep = stringArgs[++iterator].split(",");
                        if(delimiters == null || keep.length > delimiters.length) {
                            throw new IllegalArgumentException("The number of delimiters to keep is higher than the number of actual delimiters");
                        }
                    }
                    break;
                }
                case "--input":
                {
                    if(iterator + 1 < stringArgs.length)
                        input = stringArgs[++iterator];
                    break;
                }
                case "--output":
                {
                    if(iterator + 1 < stringArgs.length)
                        output = stringArgs[++iterator];
                    break;
                }
                default:
                {
                    HTMPP_Utilities.printHelp("unknown argument");
                }
            }
        }

        arg = null;


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
