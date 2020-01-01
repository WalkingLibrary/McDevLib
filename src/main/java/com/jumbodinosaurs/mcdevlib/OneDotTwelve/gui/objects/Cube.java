package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;

import java.awt.*;
import java.util.ArrayList;

public class Cube extends LineObject
{
    private WayPoint contextPoint;
    private boolean alignWithGrid;
    private Color color;
    
    
    public Cube(WayPoint contextPoint, boolean alignWithGrid, Color color)
    {
        this.contextPoint = contextPoint;
        this.alignWithGrid = alignWithGrid;
        this.color = color;
    }
    
    @Override
    public void generate()
    {
        double x,y,z;
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
        
        //Looks Horrible I KNOW but makes a cube :P
        WayPoint point1 = new WayPoint(x,y,z);
        WayPoint point2 = new WayPoint(x + 1,y,z);
        WayPoint point3 = new WayPoint(x,y,z + 1);
        WayPoint point4 = new WayPoint(x + 1,y,z + 1);
        WayPoint point5 = new WayPoint(x,y + 1,z);
        WayPoint point6 = new WayPoint(x+ 1,y + 1,z);
        WayPoint point7 = new WayPoint(x,y + 1,z + 1);
        WayPoint point8 = new WayPoint(x + 1,y + 1,z + 1);
        
        ArrayList<Line> linesToAdd = new ArrayList<Line>();
        linesToAdd.add(new Line(point1, point2, this.color));
        linesToAdd.add(new Line(point1, point3, this.color));
        linesToAdd.add(new Line(point1, point5, this.color));
        
        linesToAdd.add(new Line(point8, point7, this.color));
        linesToAdd.add(new Line(point8, point6, this.color));
        linesToAdd.add(new Line(point8, point4, this.color));
        
        linesToAdd.add(new Line(point5, point6, this.color));
        linesToAdd.add(new Line(point5, point7, this.color));
        linesToAdd.add(new Line(point7, point3, this.color));
        
        linesToAdd.add(new Line(point6, point2, this.color));
        linesToAdd.add(new Line(point3, point4, this.color));
        linesToAdd.add(new Line(point2, point4, this.color));
        
        this.lines = linesToAdd;
    }
    
    public WayPoint getContextPoint()
    {
        return contextPoint;
    }
    
    public void setContextPoint(WayPoint contextPoint)
    {
        this.contextPoint = contextPoint;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
        for(Line line: lines)
        {
            line.setColor(color);
        }
    }
    
    public boolean equals(Cube cube)
    {
        return this.contextPoint.equals(cube.getContextPoint()) && this.color.equals(cube.getColor());
    }
}
