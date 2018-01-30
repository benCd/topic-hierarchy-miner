package main;

import preprocessing.HierarchicalTM_Preprocessor;

/**
 * Driver class for THM
 */

public class TopicHierarchyMiner
{
    public static void main(String[] args)
    {
        String test = "BODY=\"Hi how are you?\" TITLE=\"Hello World!\"";
        String[] testArr = {"--delimiters", "BODY=,TITLE="};

        HierarchicalTM_Preprocessor preprocessor= new HierarchicalTM_Preprocessor(testArr);
        preprocessor.processLineDelimiters(test);
    }
}
