package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;

import java.awt.*;
import java.util.ArrayList;

public class Square extends LineObject
{
    private WayPoint contextPoint;
    private boolean alignWithGrid;
    private int width;
    private Color color;
    
    public Square(WayPoint contextPoint, boolean alignWithGrid, int width, Color color)
    {
        this.contextPoint = contextPoint;
        this.alignWithGrid = alignWithGrid;
        this.width = width;
        this.color = color;
    }
    
    @Override
    public void  generate()
    {
        double x, y, z;
        if(alignWithGrid)
        {
            x = (int) this.contextPoint.getX();
            y = (int) this.contextPoint.getY();
            z = (int) this.contextPoint.getZ();
        }
        else
        {
            x = this.contextPoint.getX();
            y = this.contextPoint.getY();
            z = this.contextPoint.getZ();
        }
        
        WayPoint point1 = new WayPoint(x, y, z);
        WayPoint point2 = new WayPoint(x + this.width, y, z);
        WayPoint point3 = new WayPoint(x, y, z + this.width);
        WayPoint point4 = new WayPoint(x + this.width, y, z + this.width);
    
        ArrayList<Line> linesToAdd = new ArrayList<Line>();
        linesToAdd.add(new Line(point1, point2, this.color));
        linesToAdd.add(new Line(point1, point3, this.color));
        linesToAdd.add(new Line(point4, point3, this.color));
        linesToAdd.add(new Line(point4, point2, this.color));
        this.lines = linesToAdd;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
        for(Line line : lines)
        {
            line.setColor(color);
        }
    }
    
    public WayPoint getContextPoint()
    {
        return contextPoint;
    }
    
    public void setContextPoint(WayPoint contextPoint)
    {
        this.contextPoint = contextPoint;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public boolean equals(Square square)
    {
        return this.contextPoint.equals(square.getContextPoint()) && this.color.equals(square.getColor());
    }
    
}
