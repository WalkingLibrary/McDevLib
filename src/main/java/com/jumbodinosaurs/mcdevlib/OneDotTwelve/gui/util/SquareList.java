package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.Square;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class SquareList
{
    private ConcurrentHashMap<String, Square> squares = new ConcurrentHashMap<String, Square>();
    private boolean alignWithGrid;
    private int width;
    private Color color;
    
    public SquareList(boolean alignWithGrid, int width)
    {
        this.alignWithGrid = alignWithGrid;
        this.width = width;
        this.color = Color.YELLOW;
    }
    
    public void add(WayPoint wayPoint)
    {
        if(this.squares.containsKey(wayPoint.toHashString()))
        {
            this.remove(wayPoint);
        }
    
        Square newSquare = new Square(wayPoint, this.alignWithGrid, this.width, this.color);
        newSquare.generate();
        newSquare.draw();
        
        this.squares.put(wayPoint.toHashString(), newSquare);
    }
    
    public void add(WayPoint wayPoint, Color color)
    {
        
        
        if(this.squares.containsKey(wayPoint.toHashString()))
        {
            this.remove(wayPoint);
        }
    
        Square newSquare = new Square(wayPoint, this.alignWithGrid, this.width, color);
        newSquare.generate();
        newSquare.draw();
        this.squares.put(wayPoint.toHashString(), newSquare);
    }
    
    public void remove(WayPoint wayPoint)
    {
        if(this.squares.containsKey(wayPoint.toHashString()))
        {
           Square squareToRemove = this.squares.remove(wayPoint.toHashString());
           squareToRemove.erase();
        }
    }
    
    public void eraseAll()
    {
        for(Square square: this.squares.values())
        {
            square.erase();
        }
        this.squares = new ConcurrentHashMap<String, Square>();
    }
}
