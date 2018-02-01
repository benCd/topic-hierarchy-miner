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
                    output = "temp";
    private int outputNo = 0;

    /**
     * Argument listing:
     * --keep_files TRUE/FALSE || 1/0 (default false): Defines whether to keep the files after processing or not
     * --separate_files TRUE/FALSE || 1/0 (default false): Defines whether to separate delimiter content into different files for the analysis or not
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

        if(input == null)
            throw new IllegalStateException("Input is null");
        arg = null;


    }

    public void process()
    {
        File file = new File(input);
        File[] files;
        String path = file.getPath();

        if(file.isDirectory())
        {
            files = file.listFiles();

            for(File f : files)
            {
                process(f);
            }
        }
        else
        {
            process(file);
        }
    }

    private void process(File file)
    {

        try
        {
            Scanner get = new Scanner(file);

            String lineArray;

            while(get.hasNext())
            {
                currentLine = get.nextLine();

                boolean cont = true;

                if(!currentLine.contains("<row"))
                    continue;
                for (String del: delimiters)
                    cont = cont && currentLine.contains(del);

                if(!cont)
                    continue;

                lineArray = processLineDelimiters(currentLine);

                characterManagement(lineArray);

                System.out.println(lineArray);

                String[] pass = {lineArray};

                outputFiles(pass);
            }
        }
        catch(FileNotFoundException fNFE)
        {
            System.out.println(file.getName() + " not found!");
        }
    }


    public String processLineDelimiters(String line)
    {
        //System.out.println(line);

        String[] out = {line};

        if(delimiters != null)
        {
            if(keep != null) {

                int i = 0;
                out = new String[keep.length + 1];

                String[] tempArr;

                for(String delimiter : keep)
                {
                    tempArr = line.split(delimiter, 2);
                    if(tempArr[1] != null)
                    {
                        if(tempArr[1].charAt(0) == '\"')
                        {
                            tempArr[1] = tempArr[1].replaceFirst("\"", "");
                            out[i] = tempArr[1].split("\"",2)[0];

                            if(out[i] == null)
                                throw new IllegalStateException("Formatted string not ending or not recognized");
                        }
                    }
                    i++;
                }

            }
            else
            {
                int i = 0;
                for(String k : delimiters)
                    if(line.contains(k))
                        i++;

                out = new String[i];

                i = 0;

                String[] tempArr;

                for(String delimiter : delimiters)
                {
                    tempArr = line.split(delimiter, 2);
                    if(tempArr[1] != null)
                    {
                        if(tempArr[1].charAt(0) == '\"')
                        {
                            tempArr[1] = tempArr[1].replaceFirst("\"", "");
                            out[i] = tempArr[1].split("\"",2)[0];

                            if(out[i] == null)
                                throw new IllegalStateException("Formatted string not ending or not recognized");
                        }
                    }
                    i++;
                }
            }
        }

        String output = String.join("",out);

        return output;
    }

    public void characterManagement(String line)
    {
        line = line.replaceAll("&lt;", "<");
        line = line.replaceAll("&gt;", ">");
        line = line.replaceAll("&#xA;", "");
        line = line.replaceAll("&quot;", "");
        line = line.replaceAll("&amp;", "&");
        line = line.replaceAll("\"", "");
        line = line.replaceAll("\\.", "");
        line = line.replaceAll(",", "");
        line = line.replaceAll(":", "");
        line = line.replaceAll(";", "");

    }

    public void outputFiles(String[] lineArr)
    {
        try {
            File file = new File(output.concat("_" + outputNo + ".htmpp"));
            file.createNewFile();

            BufferedWriter write = new BufferedWriter(new FileWriter(file));

            if(!separate_files)
            {
                write.write("");

                for(String toWrite : lineArr)
                {
                    write.append(toWrite);
                }
            }
            else
            {
                for(String toWrite : lineArr) {

                    write.write(toWrite);
                    outputNo++;
                    file = new File(output.concat("_" + outputNo + ".htmpp"));
                    file.createNewFile();
                    write = new BufferedWriter(new FileWriter(file));
                }
            }
            write.flush();
            write.close();
            outputNo++;
        }
        catch(IOException exception)
        {
            System.out.println("output file error " + exception.getMessage());
        }
    }
}
