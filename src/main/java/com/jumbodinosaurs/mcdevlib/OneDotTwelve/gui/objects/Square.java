package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.MinecraftPoint3D;

import java.awt.*;
import java.util.ArrayList;

public class Square extends LineObject
{
    private MinecraftPoint3D contextPoint;
    private boolean alignWithGrid;
    private int width;
    private Color color;
    
    public Square(MinecraftPoint3D contextPoint, boolean alignWithGrid, int width, Color color)
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
    
        MinecraftPoint3D point1 = new MinecraftPoint3D(x, y, z);
        MinecraftPoint3D point2 = new MinecraftPoint3D(x + this.width, y, z);
        MinecraftPoint3D point3 = new MinecraftPoint3D(x, y, z + this.width);
        MinecraftPoint3D point4 = new MinecraftPoint3D(x + this.width, y, z + this.width);
    
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
    
    public MinecraftPoint3D getContextPoint()
    {
        return contextPoint;
    }
    
    public void setContextPoint(MinecraftPoint3D contextPoint)
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
