package main;

import preprocessing.HierarchicalTM_Preprocessor;

/**
 * Driver class for THM
 */

public class TopicHierarchyMiner
{
    public static void main(String[] args)
    {
        String[] testArr = {"--delimiters", "Title=", "--output", "/home/ben/Documents/Research/preprocessingTestTtitles2/out", "--input", "/home/ben/Documents/Research/so-soft-eng/Posts.xml"};

        HierarchicalTM_Preprocessor preprocessor= new HierarchicalTM_Preprocessor(testArr);
        preprocessor.process();
    }
}
