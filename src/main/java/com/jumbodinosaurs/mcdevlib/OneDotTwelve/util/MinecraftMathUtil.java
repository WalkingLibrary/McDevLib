package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.Direction;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.PathFindingUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.util.MovementHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.exceptions.ImaginaryNumberException;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;

import java.util.ArrayList;

public class MinecraftMathUtil
{
    public static double roundAvoid(double value, int places)
    {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
    
    /* Khan Video
     * https://youtu.be/u00I3MCrspU
     *
     */
    public static double getDeterminate(double[][] matrix)
    {
        boolean isSquareMatrix = true;
        for(int r = 0; r < matrix.length; r++)
        {
            if(matrix.length != matrix[r].length)
            {
                isSquareMatrix = false;
            }
        }
        if(isSquareMatrix)
        {
            if(matrix.length > 2)
            {
                double determinate = 0;
                for(int i = 0; i < matrix.length; i++)
                {
                    double sign = -1;
                    if((i + 1) % 2 != 0)
                    {
                        sign = 1;
                    }
                    
                    determinate += (sign * (matrix[0][i] * getDeterminate(getSubMatrix(matrix, 0, i))));
                }
                return determinate;
            }
            else
            {
                return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
            }
        }
        else
        {
            return 0;
        }
    }
    
    /* Khan Video
     * https://youtu.be/u00I3MCrspU
     *
     */
    public static double[][] getSubMatrix(double[][] matrix, int row, int column)
    {
        double[][] subMatrix = new double[matrix.length - 1][matrix.length - 1];
        ArrayList<Double> tempSubMatrix = new ArrayList<Double>();
        
        for(int r = 0; r < matrix.length; r++)
        {
            for(int c = 0; c < matrix[r].length; c++)
            {
                if(r != row && c != column)
                {
                    tempSubMatrix.add(new Double(matrix[r][c]));
                }
            }
        }
        int tempSubMatrixIndex = 0;
        for(int r = 0; r < subMatrix.length; r++)
        {
            for(int c = 0; c < subMatrix[r].length; c++)
            {
                subMatrix[r][c] = tempSubMatrix.get(tempSubMatrixIndex);
                tempSubMatrixIndex++;
            }
        }
        return subMatrix;
    }
    
    /* Mario's Math Tutoring Explains The Solving of Systems of Equations
     * https://www.youtube.com/watch?v=v1gSS2oG4LA&t=3s
     *
     *
     * */
    public static double[][] changeColumn(double[][] matrix, double[] newColumn, int column)
    {
        double[][] tempMatrix = matrix.clone();
        for(int r = 0; r < tempMatrix.length; r++)
        {
            for(int c = 0; c < tempMatrix[r].length; c++)
            {
                if(c == column)
                {
                    tempMatrix[r][c] = newColumn[r];
                }
                else
                {
                    tempMatrix[r][c] = matrix[r][c];
                }
            }
        }
        return tempMatrix;
    }
    
    
    /*
     * Getting a Parabola from three points
     * https://www.youtube.com/watch?v=MMl8VHt1nU4
     * Mario's Math Tutoring Explains The Solving of Systems of Equations
     * https://www.youtube.com/watch?v=v1gSS2oG4LA&t=3s
     * y = ax^2 + bx + c
     * Cramer's rule
     * Returns those quadratic coefficients
     *
     * */
    public static double[] getCoefficients(Point pointOne, Point pointTwo, Point pointThree)
    {
        
        double a, b, c;
        double[] aCoefficients = {Math.pow(pointOne.getX(), 2), pointOne.getX(), 1};
        double[] bCoefficients = {Math.pow(pointTwo.getX(), 2), pointTwo.getX(), 1};
        double[] cCoefficients = {Math.pow(pointThree.getX(), 2), pointThree.getX(), 1};
        
        
        double[] yValueArray = {pointOne.getY(), pointTwo.getY(), pointThree.getY()};
        double[][] aChanged, bChanged, cChanged;
        
        double[][] denominator = {aCoefficients, bCoefficients, cCoefficients};
        double denominatorDeterminate = getDeterminate(denominator);
        
        double[][] aNumerator = {aCoefficients.clone(), bCoefficients.clone(), cCoefficients.clone()};
        aChanged = changeColumn(aNumerator, yValueArray, 0);
        
        double[][] bNumerator = {aCoefficients.clone(), bCoefficients.clone(), cCoefficients.clone()};
        bChanged = changeColumn(bNumerator, yValueArray, 1);
        
        double[][] cNumerator = {aCoefficients.clone(), bCoefficients.clone(), cCoefficients.clone()};
        cChanged = changeColumn(cNumerator, yValueArray, 2);
        
        if(denominatorDeterminate != 0)
        {
            a = getDeterminate(aChanged) / denominatorDeterminate;
            b = getDeterminate(bChanged) / denominatorDeterminate;
            c = getDeterminate(cChanged) / denominatorDeterminate;
            double[] answer = {a, b, c};
            return answer;
        }
        return null;
    }
    
    /*
     * y = ax^2 + bx + c
     * */
    public static double solveQuadraticForY(double[] coefficients, double x)
    {
        
        double[] tempCoefficients = coefficients.clone();
        double a, b, c, xSquared;
        a = tempCoefficients[0];
        b = tempCoefficients[1];
        c = tempCoefficients[2];
        xSquared = Math.pow(x, 2);
        return ((a * xSquared) + (x * b) + c);
    }
    
    /*
     * Quadratic Formula
     * x = -b +- sqrt(b^2-4ac) / 2a
     */
    public static double[] solveQuadraticFormula(double[] coefficients, double y) throws ImaginaryNumberException
    {
        
        double[] tempCoefficients = coefficients.clone();
        tempCoefficients[2] = tempCoefficients[2] - y;
        double a, b, c, negativeB, aTwo, fourAC, bSquared, bSquaredMinus4AC;
        a = tempCoefficients[0];
        b = tempCoefficients[1];
        c = tempCoefficients[2];
        bSquared = Math.pow(b, 2);
        fourAC = 4 * a * c;
        bSquaredMinus4AC = bSquared - fourAC;
        if(bSquaredMinus4AC >= 0)
        {
            negativeB = -b;
            aTwo = 2 * a;
            double[] answer = {((negativeB + Math.sqrt(bSquaredMinus4AC)) / aTwo), ((negativeB - Math.sqrt(
                    bSquaredMinus4AC)) / aTwo)};
            return answer;
            
        }
        else
        {
            //This should be caught by whoever is calling this function
            //I don't like living in an imaginary world
            throw new ImaginaryNumberException("No Roots A: " + a + " B:" + b + " C: " + c + " Y: " + y);
        }
    }
    
    
    /* returns the minimum and maximum points of a quadratic equation between the specified waypoints
     * returns {min, max} minimum first
     * */
    public static double[] getMaxAndMinY(double[] coefficients, double endPointOne, double endPointTwo)
    {
        
        double[] tempCoefficients = coefficients.clone();
        double endPointOneYValue = solveQuadraticForY(tempCoefficients, endPointOne);
        double endPointTwoYValue = solveQuadraticForY(tempCoefficients, endPointTwo);
        
        double min, max;
        max = Math.max(endPointOneYValue, endPointTwoYValue);
        min = Math.min(endPointOneYValue, endPointTwoYValue);
        
        double minMaxXValue = getQuadraticMinOrMaxXValue(tempCoefficients);
        boolean isMaxOrMinBetweenEndPoints = minMaxXValue >= Math.abs(endPointOne) && minMaxXValue <= Math.abs(
                endPointTwo);
        
        if(!isMaxOrMinBetweenEndPoints)
        {
            double minMax = solveQuadraticForY(tempCoefficients, minMaxXValue);
            max = Math.max(max, minMax);
            min = Math.min(min, minMax);
        }
        
        double[] minAndMax = {min, max};
        return minAndMax;
    }
    
    /* "Proceed as if Success is inevitable" -Linberg
     * */
    public static double getQuadraticMinOrMaxXValue(double[] coefficients)
    {
        
        double[] tempCoefficients = coefficients.clone();
        // y = ax2 +bx + c
        // y` = 2ax + b
        //
        //DO THE CALCULUS
        double a, b, twoA;
        a = tempCoefficients[0];
        b = tempCoefficients[1];
        twoA = a * 2;
        double maximumOrMinimum = (0 - b) / twoA;
        return maximumOrMinimum;
    }
    
    
    public static ArrayList<WayPoint> getBlockPointsQuadratic(WayPoint startPoint,
                                                              WayPoint endPoint) throws ImaginaryNumberException
    {
        
        
        /*
           Anatomy of a Jump
           * Using these three points we can make a
              Quadratic Equation to then give us each
              BlockPoint we will travel thru when jumping
              
           * Split the x-axis and z-axis
           * Six Points
           * 3 for x-axis
           * 3 for z-axis
                    y-axis
                     |
                     |
                     |
                     |         O
                     |         : ((x1 - x2), Max Minecraft Jump Height)
                     |         :
          ___________O_________:_________O______________
                     |(x1,0)              (x2,0)       x-axis
                     |
                     |
                     |
                     |
                    
         */
        
        
        Direction directionDifference = PathFindingUtil.getDirectionDifference(startPoint, endPoint);
        
        
        double xDifference, zDifference, xDiffMiddle, zDiffMiddle;
        
        
        xDifference = Math.abs(endPoint.getX() - startPoint.getX());
        zDifference = Math.abs(endPoint.getZ() - startPoint.getZ());
        
        xDiffMiddle = xDifference / 2;
        zDiffMiddle = zDifference / 2;
        
        
        Point[] xAxisPoints = new Point[3];
        xAxisPoints[0] = new Point(startPoint.getX(), startPoint.getY());
        
        xAxisPoints[1] = new Point(startPoint.getX() + (xDiffMiddle * directionDifference.x),
                                   startPoint.getY() + MovementHelper.maxJumpHeight);
        
        xAxisPoints[2] = new Point(endPoint.getX(), endPoint.getY());
        
        
        Point[] zAxisPoints = new Point[3];
        zAxisPoints[0] = new Point(startPoint.getZ(), startPoint.getY());
        
        zAxisPoints[1] = new Point(startPoint.getZ() + (zDiffMiddle * directionDifference.z),
                                   startPoint.getY() + MovementHelper.maxJumpHeight);
        
        zAxisPoints[2] = new Point(endPoint.getZ(), endPoint.getY());
        
        
        double[] xCoefficients, zCoefficients;
        
        
        xCoefficients = getCoefficients(xAxisPoints[0], xAxisPoints[1], xAxisPoints[2]);
        
        
        zCoefficients = getCoefficients(zAxisPoints[0], zAxisPoints[1], zAxisPoints[2]);
      

        
        
        /*
                    y-axis
                     |
                     |
                     |
                     |         O
                     |         : ((x1 - x2), Max Minecraft Jump Height)
                     |         :
          ___________O_________:________________________
                     |(x1,0)                          x-axis
                     |
                     |
                     |                    O
                     |                     (x2,0)         - I want to be able to get points below the start point.
                    
         */
        
        // Need All the "Chopped" Points between the start and end points
        // for both x and z as we split the x and z axis
        //
        int absIntXDifference = Math.abs((int) startPoint.getX() - (int) endPoint.getX());
        
        int absIntZDifference = Math.abs((int) startPoint.getZ() - (int) endPoint.getZ());
        
        
        int[] xPoints = new int[absIntXDifference];
        int[] zPoints = new int[absIntZDifference];
        
        
        for(int i = 0; i < absIntXDifference; i++)
        {
            xPoints[i] = (int) startPoint.getX() + i;
            
        }
        
        
        for(int i = 0; i < absIntZDifference; i++)
        {
            zPoints[i] = (int) startPoint.getZ() + i;
            
        }
        
        
        //We Now need to make waypoints from each int x and int z between the end and start
        //this requires that we calculate the Y value for either x or z and then also solve for
        // the missing x or z value. There may be a smart way of "Combining" the two quadratics
        // But as a RedHead Once said "Life is harder when your stupid." -TheDark_Emperor
        
        
        ArrayList<WayPoint> blockPoints = new ArrayList<WayPoint>();
        double maxY, minY, xMaxY, zMaxY;
        maxY = startPoint.getY() + MovementHelper.maxJumpHeight;
        minY = Math.min(startPoint.getY(), endPoint.getY());
        if(xCoefficients != null)
        {
            xMaxY = getQuadraticMinOrMaxXValue(xCoefficients);
        }
        else
        {
            xMaxY = startPoint.getX();
        }
        
        if(zCoefficients != null)
        {
            zMaxY = getQuadraticMinOrMaxXValue(zCoefficients);
        }
        else
        {
            zMaxY = startPoint.getZ();
        }
        
        blockPoints.add(new WayPoint(xMaxY, maxY, zMaxY));
        double wayPointDifference = .01;
        for(double i = maxY - wayPointDifference; minY < (i - wayPointDifference); i -= wayPointDifference)
        {
            double[] xAnswers = new double[2];
            double[] zAnswers = new double[2];
            
            if(xCoefficients != null)
            {
                xAnswers = solveQuadraticFormula(xCoefficients, i);
            }
            else
            {
                xAnswers[0] = startPoint.getX();
                xAnswers[1] = startPoint.getX();
            }
            
            if(zCoefficients != null)
            {
                zAnswers = solveQuadraticFormula(zCoefficients, i);
            }
            else
            {
                zAnswers[0] = startPoint.getZ();
                zAnswers[1] = startPoint.getZ();
            }
            
            
            //Direction dictates if we start from largest or smallest
            // if it's negative we swap the two answers
            if(directionDifference.x == -1)
            {
                double temp = xAnswers.clone()[0];
                double temp2 = xAnswers.clone()[1];
                xAnswers[0] = temp2;
                xAnswers[1] = temp;
            }
            
            if(directionDifference.z == -1)
            {
                double temp = zAnswers.clone()[0];
                double temp2 = zAnswers.clone()[1];
                zAnswers[0] = temp2;
                zAnswers[1] = temp;
            }
            
            if(i > startPoint.getY())
            {
                blockPoints.add(0, new WayPoint(xAnswers[0], i, zAnswers[0]));
            }
            
            if(i > endPoint.getY())
            {
                blockPoints.add(blockPoints.size(), new WayPoint(xAnswers[1], i, zAnswers[1]));
            }
        }
        
        /*
        System.out.println("WayPoints Created");
        for(WayPoint wayPoint : blockPoints)
        {
            System.out.println(wayPoint.toString());
        }
        */
        
        return blockPoints;
    }
    
    
    public static int round(double value)
    {
        int num1, num2;
        num1 = (int) value;
        num2 = num1 + 1;
        double differenceNum1 = Math.abs(num1 - value);
        double differenceNum2 = Math.abs(num2 - value);
        if(differenceNum1 < differenceNum2)
        {
            return num1;
        }
        return num2;
    }
    
}

