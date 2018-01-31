package drawing;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class TreeMaker
{
    private DefaultTreeForTreeLayout<TextInBox> tree;

    public TreeMaker(String name, String filepath)
    {

        if(filepath == null)
            throw new IllegalArgumentException("No file parsed!");

        File file = new File(filepath);

        if(!file.exists())
            throw new IllegalArgumentException("File does not exist!");

        if(name == null)
            name = file.getName();

        TextInBox root = new TextInBox(name, 100, 50);

        tree = new DefaultTreeForTreeLayout<>(root);

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));

        }
        catch(IOException ioex)
        {
            System.out.println(ioex.getMessage());
        }
    }
}
