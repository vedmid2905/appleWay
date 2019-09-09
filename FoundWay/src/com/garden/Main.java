package com.garden;


import java.io.FileNotFoundException;

public class Main {

    static int rowSize;
    static int columnSize;
    static int[][] garden;

    public static void main(String[] args) throws FileNotFoundException {

        InputData inputData = ReadWriteFileUtils.getDataFromTextFile();
        ApplesFounder af = new ApplesFounder();
        int result = af.getApplesCountFromBestWayByMergeMetod(inputData);
        ReadWriteFileUtils.printResult(result);
    }


}



