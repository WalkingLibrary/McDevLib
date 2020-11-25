package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.MinecraftPoint3D;

import java.awt.*;

public class Line
{
    private MinecraftPoint3D pointOne;
    private MinecraftPoint3D pointTwo;
    private boolean needsDepth;
    private Color color;
    private float width;
    private static final float defaultWidth = 50f;
    
    
    public Line(MinecraftPoint3D pointOne, MinecraftPoint3D pointTwo, Color color)
    {
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
        this.color = color;
        this.width = defaultWidth;
        this.needsDepth = false;
    }
    
    
    public MinecraftPoint3D getPointOne()
    {
        return pointOne;
    }
    
    public void setPointOne(MinecraftPoint3D pointOne)
    {
        this.pointOne = pointOne;
    }
    
    public MinecraftPoint3D getPointTwo()
    {
        return pointTwo;
    }
    
    public void setPointTwo(MinecraftPoint3D pointTwo)
    {
        this.pointTwo = pointTwo;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    public float getWidth()
    {
        return width;
    }
    
    public void setWidth(float width)
    {
        this.width = width;
    }
    
    public static float getDefaultWidth()
    {
        return defaultWidth;
    }
    
    public boolean equals(Line line)
    {
        return this.getPointOne().equals(line.getPointOne()) && this.getPointTwo().equals(line.getPointTwo());
    }
    
    public boolean needsDepth()
    {
        return needsDepth;
    }
    
    public void setNeedsDepth(boolean needsDepth)
    {
        this.needsDepth = needsDepth;
    }
}
