package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import sun.reflect.generics.tree.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FileChooser fc = new FileChooser();
        fc.setTitle("Please pick a file");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Graph generator for hLDA and hPAM");
        primaryStage.setScene(new Scene(root, 900, 900));
        primaryStage.show();
        generateTree(fc.showOpenDialog(primaryStage));
    }


    public static void main(String[] args) {
        launch(args);
    }


    private static void generateTree(File file) throws IOException
    {
        String temp;
        Scanner get = new Scanner(new BufferedReader(new FileReader(file)));

        ArrayList<Pair<Integer, String>> nodes = new ArrayList<>();


        while(get.hasNextLine())
        {
            temp = get.nextLine();
            if(!temp.startsWith("<") && !temp.isEmpty())
            {
                nodes.add(new Pair<Integer, String>(0, temp));
            }
        }
        nodes = preProcess(nodes);
        for(Pair pair : nodes)
            System.out.println(pair.toString());
    }

    private static ArrayList<Pair<Integer,String>> preProcess(ArrayList<Pair<Integer,String>> list) {

        ArrayList<Pair<Integer,String>> out = new ArrayList<>();

        for (Pair<Integer,String> pair : list)
        {
            if(Character.isDigit(pair.getValue().charAt(0)))
            {
                out.add(new Pair<Integer, String>(Integer.parseInt(pair.getValue().split(":")[0]), pair.getValue().split(":")[1].replaceFirst("\t","")));
            }
            else
            {
                out.add(pair);
            }
        }
        return out;
    }


    private static Tree createTree(ArrayList<Pair<Integer,String>> list)
    {
        return null;
    }
}
