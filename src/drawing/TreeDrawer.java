
package drawing;

import org.abego.treelayout.*;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TreeDrawer
{
    TreeLayout<String> treeLayout;

    public TreeDrawer(DefaultTreeForTreeLayout<TextInBox> tree)
    {
    }

    private TreeForTreeLayout<String> getTree()
    {
        return treeLayout.getTree();
    }

    public void drawTree()
    {

    }

}
