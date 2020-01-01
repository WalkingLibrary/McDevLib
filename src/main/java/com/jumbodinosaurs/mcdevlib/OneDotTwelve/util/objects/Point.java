package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects;

public class Point
{
    private double x;
    private double y;
    
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    
    public double getEuclideanDistance(Point point)
    {
        return Math.sqrt(Math.pow(getXDifference(point), 2) + Math.pow(getYDifference(point),2));
    }
    
    
    public boolean equals(Point point)
    {
        return this.x == point.getX() && this.y == point.getY();
    }
    
    public double getX()
    {
        return x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getXDifference(Point point)
    {
        return this.x - point.getX();
    }
    
    public double getYDifference(Point point)
    {
        return this.y - point.getY();
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    @Override
    public String toString()
    {
        return "Point: {" + "x:" + this.x + ", y:" + this.y + "}";
    }
}
