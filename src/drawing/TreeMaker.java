package drawing;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class TreeMaker
{
    private DefaultTreeForTreeLayout<TextInBox> tree;
    private String name, filepath, format;
    private TextInBox root;

    /**
     * Creates a TreeMaker object, which can create a tree from a file created by MALLET HLDA/HPAM
     * @param name
     * @param filepath
     * @param format
     */
    private TreeMaker(String name, String filepath, String format)
    {
        if(filepath == null)
            throw new IllegalArgumentException("No file parsed!");

        File file = new File(filepath);

        if(!file.exists())
            throw new IllegalArgumentException("File does not exist!");

        if(name == null)
            name = file.getName();

        if(format == null)
            format = "hlda";
        else if(!format.toLowerCase().equals("hlda") || !format.toLowerCase().equals("hpam"))
            throw new IllegalArgumentException("Unrecognized algorithm!");

        root = new TextInBox(name, 100, 50);
        tree = new DefaultTreeForTreeLayout<>(root);
    }

    /**
     * TreeMaker constructor only taking a filepath as an argument.
     * Name of tree will be equal to the filename.
     * Format will be set to HLDA.
     *
     * FOR NOW THE ONLY CONSTRUCTOR UNTIL THE PENDING HPAM IMPLEMENTATION
     *
     * @param filepath
     */
    public TreeMaker(String filepath)
    {
        new TreeMaker(null, filepath, null);
    }


    /**
     * Automatically builds a DefaultTreeForTreeLayout tree object bases on a prepared hierarchical topic model file
     * @return tree
     */
    public DefaultTreeForTreeLayout<TextInBox> make()
    {
        Stack<TextInBox> parents = new Stack<>();
        parents.push(tree.getRoot());
        int indent = 0;

        try
        {
            Scanner reader = new Scanner(new FileReader(filepath));
            TextInBox previous = null;

            while(reader.hasNextLine())
            {
                String temp = reader.nextLine();
                TextInBox element = new TextInBox(temp, temp.length()*12 + 12, 72);
                int elementIndent = 0;

                //counting the indents
                while(element.text.charAt(elementIndent) == '\t')
                    elementIndent++;

                //creating tree structure based on indents
                if(indent > elementIndent)
                {
                    parents.pop();
                    indent--;
                }
                else if(indent < elementIndent)
                {
                    if(previous != null)
                    {
                        parents.push(previous);
                        indent++;
                    }
                    else
                    {
                        //leveling indent with elementIndent for initial element, if initial element has more than 0 indents
                        indent = elementIndent;
                    }
                }
                //will add nodes to the same parent if the amount of indents is the same
                tree.addChild(parents.peek(),element);
                previous = element;
            }
        }
        catch(IOException ioex)
        {
            System.out.println(ioex.getMessage());
        }

        return tree;
    }
}
