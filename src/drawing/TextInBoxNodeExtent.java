package drawing;

import org.abego.treelayout.NodeExtentProvider;

public class TextInBoxNodeExtent implements NodeExtentProvider<TextInBox>
{
    @Override
    public double getWidth(TextInBox textInBox) {
        return textInBox.width;
    }

    @Override
    public double getHeight(TextInBox textInBox) {
        return textInBox.height;
    }
}
