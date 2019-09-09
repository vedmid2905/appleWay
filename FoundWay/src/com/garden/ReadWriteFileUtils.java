package com.garden;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ReadWriteFileUtils {


    public static InputData getDataFromTextFile() throws FileNotFoundException {
        return  getDataFromTextFile(new File("Input.txt"));
    }
    public static InputData getDataFromTextFile(File file) throws FileNotFoundException {

        InputData inputData = new InputData();
        Scanner scanner = new Scanner(file);
        inputData.rowSize = scanner.nextInt();
        inputData.columnSize = scanner.nextInt();
        inputData.garden = new int[inputData.columnSize][inputData.rowSize];
        for (int i = 0; i < inputData.rowSize; i++) {
            for (int j = 0; j < inputData.columnSize; j++) {
                inputData.garden[j][i] = scanner.nextInt();
            }
        }

        return  inputData;


    }


    public static void printResult(int foundBest) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(new File("Output.txt"));
        pw.println(foundBest);
        pw.flush();
        pw.close();
    }




}
