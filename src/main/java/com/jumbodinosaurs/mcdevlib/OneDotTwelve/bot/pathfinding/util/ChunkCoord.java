package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;

public class ChunkCoord
{
    private int x;
    private int z;
    
    public ChunkCoord(int x, int z)
    {
        this.x = x;
        this.z = z;
    }
    
    
    public int getX()
    {
        return x;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getZ()
    {
        return z;
    }
    
    public void setZ(int z)
    {
        this.z = z;
    }
    
    public ChunkCoord addX(int amount)
    {
        return new ChunkCoord(this.x + amount, this.z);
    }
    
    public ChunkCoord addZ(int amount)
    {
        return new ChunkCoord(this.x, this.z + amount);
    }
    
    public ArrayList<ChunkCoord> getAdjacentCoords()
    {
        ArrayList<ChunkCoord> neighbors = new ArrayList<ChunkCoord>();
        for(Direction direction : Direction.values())
        {
            if(!direction.equals(Direction.SAMEPOINT))
            {
                neighbors.add(this.addX(direction.x).addZ(direction.z));
            }
        }
        return neighbors;
    }
    
    public boolean equals(ChunkCoord chunkCoord)
    {
        return this.x == chunkCoord.getX() && this.z == chunkCoord.z;
    }
    
    public boolean equals(ChunkPos pos)
    {
        return this.x == pos.x && this.z == pos.z;
    }
    
    @Override
    public String toString()
    {
        return "{ X: " + this.x + ", Z: " + this.z + " }";
    }
}
