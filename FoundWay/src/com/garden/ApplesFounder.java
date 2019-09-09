package com.garden;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ApplesFounder {

    public static final int minBlockSize = 2;

    public int getApplesCountFromBestWayByMergeMetod(InputData inputData) {


        int countBlockX = inputData.columnSize / minBlockSize + inputData.columnSize % minBlockSize;
        int countBlockY = inputData.rowSize / minBlockSize + inputData.rowSize % minBlockSize;
        Block[][] blocks = new Block[countBlockX][countBlockY];
        System.out.println("Strat prepare block");
        for (int j = 0; j < countBlockY; j++) {
            for (int i = 0; i < countBlockX; i++) {

                blocks[i][j] = new Block();
                setLimitBlock(inputData, blocks[i][j], j, i);
                setBlockInpoint(blocks[i][j]);
                setBlockOutPoints(blocks[i][j], inputData);
                setBlockRouters(blocks[i][j], inputData);

            }
        }
        System.out.println("Finish prepare block");
        boolean notOnlyOneBlock = true;
        while (notOnlyOneBlock) {
            for (int j = 0; j < countBlockY; j++) {
                boolean check = true;
                Block one;
                Block two;
                int index = 0;
                while (check) {
                    index = findNextBlock(index, j, blocks, true);
                    if (index == -1) break;
                    one = blocks[index][j];
                    index++;
                    index = findNextBlock(index, j, blocks, true);
                    if (index == -1) break;
                    two = blocks[index][j];
                    mergeBlock(one, two, true, inputData);
                    blocks[index][j] = null;
                    index++;
                }
            }
            for (int i = 0; i < countBlockX; i++) {
                boolean check = true;
                Block one;
                Block two;
                int index = 0;
                while (check) {
                    index = findNextBlock(index, i, blocks, false);
                    if (index == -1) break;
                    one = blocks[i][index];
                    index++;
                    index = findNextBlock(index, i, blocks, false);
                    if (index == -1) break;
                    two = blocks[i][index];
                    mergeBlock(one, two, false, inputData);
                    blocks[i][index] = null;
                    index++;
                }


            }
            notOnlyOneBlock = !checkForOne(blocks);
        }
        return blocks[0][0].routes.get(0).length;
    }

    private boolean checkForOne(Block[][] blocks) {
        for (int i = 0; i < blocks.length; i++) {
          //  System.out.println ("i - "+i +"max - "+ blocks.length);
            for (int j = 0; j < blocks[i].length; j++) {
              //  System.out.println ("j - "+j +"max - "+ blocks[i].length);
            //    System.out.println ("blocks[i][j] ="+blocks[i][j]);
                if (i != 0 && j != 0 && blocks[i][j] != null) return false;
            }
        }
        return true;
    }


    void mergeBlock(Block one, Block two, boolean isMergeHoriz, InputData inputData) {


        List<Route> routes1 = new CopyOnWriteArrayList<>(one.routes);
        List<Route> routes2 = new CopyOnWriteArrayList<>(two.routes);
        Set<Route> result = new HashSet();

        for (Route route1 : routes1) {
            for (Route route2 : routes2) {
                boolean condition;
                if (isMergeHoriz) {
                    condition = (route1.end.x + 1 == route2.start.x) &&
                            (route1.end.y == route2.start.y);
                } else {
                    condition = (route1.end.x == route2.start.x) &&
                            (route1.end.y + 1 == route2.start.y);
                }
                if (condition) {
                    boolean isSpecificPoint = route1.end.equals(one.end);
                    boolean conditionDontRemoveRoutesBlockOne =
                            isSpecificPoint  &&
                                    !(((route1.end.x + 1 == inputData.columnSize) ) ||
                                            ((route1.end.y + 1 == inputData.rowSize) ));
                    if (!conditionDontRemoveRoutesBlockOne) route1.leave=false;

                    boolean conditionDontRemoveRoutesBlockTwo =
                            route2.start.equals(two.start) &&(! (route2.start.y == 0|| route2.start.x==0 ));
                  if (!conditionDontRemoveRoutesBlockTwo)  route2.leave = false;
                    Route temp = new Route();
                    temp.start = route1.start;
                    temp.end = route2.end;
                    temp.length = route1.length + route2.length;
                    if (result.contains(temp)) {

                        Route temp1 = null;
                        for (Route r : result) {
                            if (r.equals(temp))
                                temp1 = r;
                        }
                        if (temp.length > temp1.length) {
                            result.remove(temp);
                            result.add(temp);
                        }
                    } else

                        result.add(temp);
                }
            }
        }
        routes1.removeIf(p->!p.leave);
        routes2.removeIf(p->!p.leave);
        one.routes = new ArrayList<>(result);
        one.routes.addAll(routes1);
        one.routes.addAll(routes2);
        one.end = two.end;
    }

    /**
     * return first not null element in Two-dimensional array  beginning with index, if found any elements
     * return -1
     * isRecordSeek - true find for record
     * false for column
     *
     * @param index
     * @param j
     * @param blocks
     * @return
     */
    private int findNextBlock(int index, int j, Block[][] blocks, boolean isRecordSeek) {

        if (isRecordSeek) {
            for (int i = index; i < blocks[j].length; i++) {

                if (blocks[i][j] != null) return i;
            }
        } else {
            for (int i = index; i < blocks.length; i++) {

                if (blocks[j][i] != null) return i;
            }

        }

        return -1;
    }

    void setBlockRouters(Block block, InputData inputData) {

        for (Point inPoint : block.inPoints) {
            for (Point outPoint : block.outPoints) {

                int appleLength = this.getApplesCountBestWayByTotalCheck(inputData.garden, inPoint,
                        outPoint);
                Route route = new Route();
                route.start = inPoint;
                route.end = outPoint;
                route.length = appleLength;
                block.routes.add(route);
            }

        }
    }

    void setBlockOutPoints(Block block, InputData inputData) {

        Point top = block.start;
        Point bottom = block.end;
        //vertical
        // System.out.println("Vertical");
        for (int k = top.y; k < bottom.y + 1; k++) {
            if (bottom.x == inputData.columnSize - 1) {
                if (k == bottom.y) {
                    block.outPoints.add(new Point(bottom.x, k));
                    //     System.out.println(new Point(bottom.x, k));
                }

            } else {
                block.outPoints.add(new Point(bottom.x, k));
                // System.out.println(new Point(bottom.x, k));
            }

        }
        //horizontal
        // System.out.println("Horizontal");
        for (int k = top.x; k < bottom.x - 1 + 1; k++) {
            if (bottom.y == inputData.rowSize - 1) {
                break;
            } else {
                block.outPoints.add(new Point(k, bottom.y));
                //  System.out.println(new Point(k, bottom.y));
            }


        }
    }

    void setLimitBlock(InputData inputData, Block block, int j, int i) {
        Point top = new Point(i * minBlockSize, j * minBlockSize);
        int x = ((i + 1) * minBlockSize - 1 < inputData.columnSize - 1) ?
                (i + 1) * minBlockSize - 1 : inputData.columnSize - 1;
        int y = ((j + 1) * minBlockSize - 1 < inputData.rowSize - 1) ?
                (j + 1) * minBlockSize - 1 : inputData.rowSize - 1;
        Point bottom = new Point(x, y);
        block.start = top;
        block.end = bottom;
    }


    void setBlockInpoint(Block block) {

        Point top = block.start;
        Point bottom = block.end;
        for (int k = top.y; k < bottom.y + 1; k++) {
            if (top.x == 0) {
                if (top.y == k) {
                    block.inPoints.add(new Point(0, k));
                }
                break;
            } else {
                block.inPoints.add(new Point(top.x, k));
            }

        }
        // set inpoint vertical
        for (int k = top.x + 1; k < bottom.x + 1; k++) {
            if (top.y == 0) {
                break;
            } else {
                block.inPoints.add(new Point(k, top.y));
            }


        }
    }

    public int getApplesCountBestWayByTotalCheck(int[][] garden, Point start, Point End) {
        int columnSize = End.x + 1;
        int rowSize = End.y + 1;
        return foundBest(getWays(columnSize, rowSize, start), garden);

    }

    public int getApplesCountBestWayByTotalCheck(InputData inputData) {

        int rowSize = inputData.rowSize;
        int columnSize = inputData.columnSize;
        int[][] garden = inputData.garden;
        return foundBest(getWays(columnSize, rowSize, new Point(0, 0)), garden);
    }

    int foundBest(List<HedgehogWay> ways, int[][] garden) {

        int[] apple = new int[ways.size()];
        int index = 0;
        for (HedgehogWay hw : ways) {

            for (Point point : hw.way) {
                apple[index] = apple[index] + garden[point.x][point.y];
            }
            index++;
        }

        int max = Arrays.stream(apple).max().orElse(0);
        return max;
    }

    List<HedgehogWay> getWays(int columnSize, int rowSize, Point startPoint) {

        if (columnSize == 0 || rowSize == 0)
            throw new IllegalArgumentException("wrong input data  " + columnSize + " " + rowSize);
        //todo size 1

        List<HedgehogWay> result = new LinkedList<>();
        //todo startpoint
        int maxIteration = columnSize + rowSize - 2 - startPoint.x - startPoint.y;
        result.add(getWayWithStartPoint(startPoint));
        for (int j = 0; j < maxIteration; j++) {
            result = getIterationWays(result, columnSize, rowSize);
          /*  System.out.println("");
            System.out.println("Iteration - " + j);
            for (HedgehogWay hw : result) {
                System.out.println("");
                for (Point p : hw.way) {

                    System.out.print(p + "  ");
                }

            }*/
        }

        return result;
    }

    List<HedgehogWay> getIterationWays(List<HedgehogWay> input, int columnSize, int rowSize) {

        List<HedgehogWay> result = new LinkedList<>();
        for (HedgehogWay hw : input) {
            Point point = hw.way.get(hw.way.size() - 1);
            if (point.x < columnSize - 1) {
                HedgehogWay hwX = new HedgehogWay(hw);
                hwX.way.add(new Point(point.x + 1, point.y));
                result.add(hwX);
            }
            if (point.y < rowSize - 1) {
                HedgehogWay hwY = new HedgehogWay(hw);
                hwY.way.add(new Point(point.x, point.y + 1));
                result.add(hwY);
            }
        }

        return result;
    }

    private HedgehogWay getWayWithStartPoint() {
        Point startPoint = new Point(0, 0);
        return getWayWithStartPoint(startPoint);

    }

    private HedgehogWay getWayWithStartPoint(Point startPoint) {
        HedgehogWay hw = new HedgehogWay();
        hw.way.add(startPoint);
        return hw;

    }
}

class HedgehogWay {

    List<Point> way = new LinkedList<Point>();

    HedgehogWay() {
    }

    HedgehogWay(HedgehogWay h) {
        way = new LinkedList<Point>(h.way);
    }

}