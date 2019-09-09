package com.garden;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class AppleFounderPerfomanceTest {

    InputData inputData;
    InputData inputData1;
    InputData bigData;
    InputData realBigData;

    @Before
    public void setUp() throws IOException {

         bigData = getInputDataFromGenerator(100,100);
        realBigData = getInputDataFromGenerator(1000,1000);
        inputData1 = ReadWriteFileUtils.getDataFromTextFile(new File("resource\\2.file"));
    }

    private InputData getInputDataFromGenerator(int rowSize, int columnSize) {

        InputData inputData = new InputData();

        inputData.rowSize = rowSize;
        inputData.columnSize = columnSize;
        inputData.garden = new int[inputData.columnSize][inputData.rowSize];
        for (int i = 0; i < inputData.rowSize; i++) {
            for (int j = 0; j < inputData.columnSize; j++) {
                inputData.garden[j][i] = (int)Math.random()*1000;
            }
        }

        return  inputData;

    }


   // @Test
    public void checkStartByTotalCheck() {
        ApplesFounder af = new ApplesFounder();
        int result1 = af.getApplesCountBestWayByTotalCheck(inputData1);


    }

   // @Test
    public void checkStartByMergeMethod() {
        ApplesFounder af = new ApplesFounder();
        int result2  =  af.getApplesCountFromBestWayByMergeMetod(inputData1);

    }

   //@Test
    //java.lang.OutOfMemoryError: GC overhead limit exceeded
    public void checkStartByTotalCheckbd() {
        ApplesFounder af = new ApplesFounder();
        int result1 = af.getApplesCountBestWayByTotalCheck(bigData);


    }

    @Test
    //11s778ms
    public void checkStartByMergeMethodbd() {
        ApplesFounder af = new ApplesFounder();
        int result2  =  af.getApplesCountFromBestWayByMergeMetod(bigData);

    }
    @Test
    public void checkStartByMergeMethodRbd() {
        ApplesFounder af = new ApplesFounder();
        int result2  =  af.getApplesCountFromBestWayByMergeMetod(realBigData);

    }
}
