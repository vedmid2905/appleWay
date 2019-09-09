package com.garden;


import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class ApplesFounderTest {

    InputData inputData;
    InputData inputData1;

    @Before
    public void setUp() throws IOException {

        inputData = ReadWriteFileUtils.getDataFromTextFile(new File("resource\\1.txt"));
        inputData1 = ReadWriteFileUtils.getDataFromTextFile(new File("resource\\2.file"));
    }


    @Test
    public void checkStartByTotalCheck()  throws FileNotFoundException {

        ApplesFounder af = new ApplesFounder();
        int result = af.getApplesCountBestWayByTotalCheck(inputData);
        assertTrue("Wrong result from find way", result == 34);


    }

     @Test
    public void checkStartAnotherPositionWork() throws FileNotFoundException {

        ApplesFounder af = new ApplesFounder();
        int result = af.getApplesCountBestWayByTotalCheck(inputData.garden, new Point(1, 1),
                new Point(2, 2));
        assertTrue("Wrong result from find way - " + result, result == 26);

        int result2 = af.getApplesCountBestWayByTotalCheck(inputData.garden, new Point(0, 0),
                new Point(2, 0));
        assertTrue("Wrong result from find way - " + result2, result2 == 6);
    }

    @Test
    public void checkMidlleFile() throws FileNotFoundException {

        ApplesFounder af = new ApplesFounder();
        int result = af.getApplesCountBestWayByTotalCheck(inputData1);
        assertTrue("Wrong result from find way - " + result, result == 77);
    }

    @Test
    public void checkSetBlockDataWork() throws FileNotFoundException {

        Point anglLeftTop = new Point(1, 1);
        Point anglRigthBot = new Point(2, 2);
        Point point12 = new Point(1, 2);
        Point point21 = new Point(2, 1);
        Point[] containsInPoint = {anglLeftTop, point12, point21};
        Point[] containsOutPoint = {anglRigthBot, point21};
        checkSetInOutPointUtility(anglLeftTop, anglRigthBot,
                containsInPoint, containsOutPoint);
    }

    void checkSetInOutPointUtility(Point anglLeftTop, Point anglRigthBot,
                                   Point[] containsInPoint, Point[] containsOutPoint) {

        ApplesFounder af = new ApplesFounder();
        Block block = new Block();
        block.start = anglLeftTop;
        block.end = anglRigthBot;
        block.start = anglLeftTop;
        block.end = anglRigthBot;
        af.setBlockInpoint(block);
        af.setBlockOutPoints(block, inputData);
        assertTrue("Wrong counts point in  - " + block.inPoints.size(),
                block.inPoints.size() == containsInPoint.length);
        assertTrue("Wrong counts point out  - " + block.inPoints.size(),
                block.outPoints.size() == containsOutPoint.length);
        for (Point point : containsInPoint) {
            assertTrue("Point not found - " + point, block.inPoints.contains(point));
        }
        for (Point point : containsOutPoint) {
            assertTrue("Point not found - " + point, block.outPoints.contains(point));
        }
    }

    @Test
    public void checkSetBlockDataLimitUpWork() throws FileNotFoundException {

        Point anglLeftTop = new Point(0, 0);
        Point anglRigthBot = new Point(1, 1);
        Point point01 = new Point(0, 1);
        Point point10 = new Point(1, 0);
        Point[] containsInPoint = {anglLeftTop};
        Point[] containsOutPoint = {anglRigthBot, point10, point01};
        checkSetInOutPointUtility(anglLeftTop, anglRigthBot,
                containsInPoint, containsOutPoint);
    }

    @Test
    public void checkSetBlockDataLimitLeftUpWork() throws FileNotFoundException {

        Point anglLeftTop = new Point(0, 1);
        Point anglRigthBot = new Point(1, 2);
        Point point11 = new Point(1, 1);
        Point point02 = new Point(0, 2);
        Point[] containsInPoint = {anglLeftTop, point11};
        Point[] containsOutPoint = {anglRigthBot, point11};
        checkSetInOutPointUtility(anglLeftTop, anglRigthBot,
                containsInPoint, containsOutPoint);

    }

    @Test
    public void checkSetBlockDataLimitRighUpWork() throws FileNotFoundException {

        Point anglLeftTop = new Point(2, 1);
        Point anglRigthBot = new Point(3, 2);
        Point point31 = new Point(3, 1);
        Point point22 = new Point(2, 2);
        Point[] containsInPoint = {anglLeftTop, point31, point22};
        Point[] containsOutPoint = {anglRigthBot};
        checkSetInOutPointUtility(anglLeftTop, anglRigthBot,
                containsInPoint, containsOutPoint);

    }


    @Test
    public void checkRouteAdd() {

        Point anglLeftTop = new Point(0, 0);
        Point anglRigthBot = new Point(1, 1);
        Point point01 = new Point(0, 1);
        Point point10 = new Point(1, 0);
        ApplesFounder af = new ApplesFounder();
        Block block = new Block();
        block.start = anglLeftTop;
        block.end = anglRigthBot;
        block.start = anglLeftTop;
        block.end = anglRigthBot;
        af.setBlockInpoint(block);
        af.setBlockOutPoints(block, inputData);
        af.setBlockRouters(block, inputData);
       /* for (Route route: block.routes ){
            System.out.println (route);

        }*/
        assertTrue("Wrong counts point in  - " + block.routes.size(),
                block.routes.size() == 3);

    }
    @Test
    public void checkRouteAdd1 () {
        ApplesFounder af = new ApplesFounder();
        Point anglLeftTop1 = new Point(2, 0);
        Point anglRigthBot1 = new Point(3, 1);
        Block block = new Block();
        block.start = anglLeftTop1;
        block.end = anglRigthBot1;
        af.setBlockInpoint(block);
        af.setBlockOutPoints(block, inputData);
        af.setBlockRouters(block, inputData);
         /* for (Route route: block.routes ){
            System.out.println (route);

        }*/
        assertTrue("Wrong counts point in  - " + block.routes.size(),
                block.routes.size() == 4);
    }


    @Test

    public void testMergeRoutes (){

        ApplesFounder af = new ApplesFounder();
        Point anglLeftTop = new Point(0, 0);
        Point anglRigthBot = new Point(1, 1);

       Block block = new Block();
        block.start = anglLeftTop;
        block.end = anglRigthBot;
        af.setBlockInpoint(block);
        af.setBlockOutPoints(block, inputData);
        af.setBlockRouters(block, inputData);
       /* for (Route route: block.routes ){
            System.out.println (route);

        }*/
        System.out.println("after set block");
        Point anglLeftTop1 = new Point(2, 0);
        Point anglRigthBot1 = new Point(3,1 );


        Block block1 = new Block();
        block1.start = anglLeftTop1;
        block1.end = anglRigthBot1;
        af.setBlockInpoint(block1);
        af.setBlockOutPoints(block1, inputData);
        af.setBlockRouters(block1, inputData);
        System.out.println("after set block1");
        af.mergeBlock(block,block1, true,inputData);
           for (Route route: block.routes ){
            System.out.println (route);

        }

    }
//ajava.awt.Point[x=0,y=2]__java.awt.Point[x=3,y=2]
@Test

public void testMergeRoutes1 (){

    ApplesFounder af = new ApplesFounder();
    Point anglLeftTop = new Point(0, 2);
    Point anglRigthBot = new Point(1, 2);

    Block block = new Block();
    block.start = anglLeftTop;
    block.end = anglRigthBot;
    af.setBlockInpoint(block);
    af.setBlockOutPoints(block, inputData);
    af.setBlockRouters(block, inputData);
       /* for (Route route: block.routes ){
            System.out.println (route);

        }*/
    System.out.println("after set block");
    Point anglLeftTop1 = new Point(2, 2);
    Point anglRigthBot1 = new Point(3,2 );


    Block block1 = new Block();
    block1.start = anglLeftTop1;
    block1.end = anglRigthBot1;
    af.setBlockInpoint(block1);
    af.setBlockOutPoints(block1, inputData);
    af.setBlockRouters(block1, inputData);
    System.out.println("after set block1");
    af.mergeBlock(block,block1, true, inputData);
    for (Route route: block.routes ){
        System.out.println (route);

    }

}


    @Test

    public void testMergeRoutes11 (){

        ApplesFounder af = new ApplesFounder();
        Point anglLeftTop = new Point(0, 0);
        Point anglRigthBot = new Point(3, 1);

        Block block = new Block();
        block.start = anglLeftTop;
        block.end = anglRigthBot;
        af.setBlockInpoint(block);
        af.setBlockOutPoints(block, inputData);
        af.setBlockRouters(block, inputData);
       /* for (Route route: block.routes ){
            System.out.println (route);

        }*/
        System.out.println("after set block");
        Point anglLeftTop1 = new Point(0, 2);
        Point anglRigthBot1 = new Point(3,2 );


        Block block1 = new Block();
        block1.start = anglLeftTop1;
        block1.end = anglRigthBot1;
        af.setBlockInpoint(block1);
        af.setBlockOutPoints(block1, inputData);
        af.setBlockRouters(block1, inputData);
        System.out.println("after set block1");
        af.mergeBlock(block,block1, false, inputData);
        for (Route route: block.routes ){
            System.out.println (route);

        }

    }
    @Test
    public void checkStartAll() {
       ApplesFounder af = new ApplesFounder();
      int result1 = af.getApplesCountBestWayByTotalCheck(inputData1);
      int result2  =  af.getApplesCountFromBestWayByMergeMetod(inputData1);
      assertTrue("Wrong result from find way", result1 == result2);

    }

    @Test
    public void checkMerge3() {

        checkMergeUtils(new Point(0,0),new Point (1,1), new Point(2,0),new Point (3,1),
                inputData1,true, 5);


    }
    @Test
    public void checkMerge4() {

        checkMergeUtils(new Point(4,0),new Point (5,1), new Point(6,0),new Point (7,1),
                inputData1,true, 8);


    }
     void checkMergeUtils (Point firstStart, Point firstEnd, Point secondStart, Point seconEnd,
                           InputData data, boolean mergeType, int expectationResult
                           )   {
        ApplesFounder af = new ApplesFounder();
        Block one = new Block();
        one.start = firstStart;
        one.end = firstEnd;
        af.setBlockInpoint(one);
        af.setBlockOutPoints(one, data);
        af.setBlockRouters(one, data);
        Block two = new Block();
        two.start = secondStart;
        two.end = seconEnd;
        af.setBlockInpoint(two);
        af.setBlockOutPoints(two, data);
        af.setBlockRouters(two, data);
        af.mergeBlock(one,two, true, data);
        assertTrue("Wrong count routers - "+one.routes.size(), one.routes.size() ==expectationResult);

    }
    @Test
    public void checkSetLimitBlock() {
        ApplesFounder af = new ApplesFounder();
        Block block00 = new Block();
        af.setLimitBlock(inputData, block00, 0, 0);
        assertTrue("Wrong top", block00.start.equals(new Point(0, 0)));
        assertTrue("Wrong bottom -" + block00.end, block00.end.equals(new Point(1, 1)));
        af.setLimitBlock(inputData, block00, 0, 1);
        assertTrue("Wrong top", block00.start.equals(new Point(2, 0)));
        assertTrue("Wrong bottom -" + block00.end, block00.end.equals(new Point(3, 1)));

        af.setLimitBlock(inputData, block00, 1, 0);
        assertTrue("Wrong top", block00.start.equals(new Point(0, 2)));
        assertTrue("Wrong bottom -" + block00.end, block00.end.equals(new Point(1, 2)));
    }



}
