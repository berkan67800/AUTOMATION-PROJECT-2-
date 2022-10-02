package AUTOMATION_PROJECT_2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utility {
    public static List<String[]> readFromCSV()   {
        String path = "src/AUTOMATION_PROJECT_2/data.csv";
        List<String[]> rows = new ArrayList<>();

        Scanner reader = null;
        try {
            reader = new Scanner(new File(path));
            while(reader.hasNext()){
                rows.add(reader.nextLine().split(","));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rows;
    }


}
