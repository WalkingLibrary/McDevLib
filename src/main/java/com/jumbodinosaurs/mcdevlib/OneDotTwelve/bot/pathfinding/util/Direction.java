package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

import java.util.Arrays;
import java.util.List;

public enum Direction
{
    //Directions should always have only ones and zeros as there x and z
    WEST(-1, 0, "WEST"),
    EAST(1, 0, "EAST"),
    NORTH(0, -1, "NORTH"),
    SOUTH(0, 1, "SOUTH"),
    NORTHWEST(-1, -1, "NORTHWEST"),
    NORTHEAST(1, -1, "NORTHEAST"),
    SOUTHWEST(-1, 1, "SOUTHWEST"),
    SOUTHEAST(1, 1, "SOUTHEAST"),
    SAMEPOINT(0, 0, "SAMEPOINT");
    
    
    public int x, z;
    public String direction;
    
    Direction(int x, int z, String direction)
    {
        this.x = x;
        this.z = z;
        this.direction = direction;
    }
    
    
    public boolean isDiagonal()
    {
        return this.x != 0 && this.z != 0;
    }
    
    public String toString()
    {
        return direction;
    }
    
    public Direction getOppositeDirection()
    {
        for(Direction direction : Direction.values())
        {
            if((this.x * -1) == direction.x && (this.z * -1) == direction.z)
            {
                return direction;
            }
        }
        return SAMEPOINT;
    }
    
    public static List<Direction> getCardinalDirections()
    {
        Direction[] cardinalPointsArray = {NORTH, SOUTH, EAST, WEST};
        return Arrays.asList(cardinalPointsArray);
        
    }
    
    public static List<Direction> getOrdinalDirections()
    {
        Direction[] ordinalPointsArray = {NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST};
        return Arrays.asList(ordinalPointsArray);
        
    }
    
    public boolean equals(Direction direction)
    {
        return direction.x == this.x && direction.z == this.z;
    }
    
}
