
package drawing;

import org.abego.treelayout.*;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * @link TreeDrawer represents a drawing model for trees created by a TreeMaker object
 * and outputs the drawn tree as a Scalable Vector Graphic (svg). The svg will then be
 * written to a file specified by the user.
 *
 * TreeDrawer is based on the svg demo code by Udo Burkowski, who published abego Tree Layout under the "BSD license".
 * The demo code can be found here <a href="https://github.com/abego/treelayout/tree/master/org.abego.treelayout.demo">abego Tree Layout demo</a>
 */
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
