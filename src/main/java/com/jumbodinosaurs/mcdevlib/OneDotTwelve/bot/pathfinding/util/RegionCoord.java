package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

import java.util.ArrayList;

public class RegionCoord
{
    private int x;
    private int z;
    
    public RegionCoord(int x, int z)
    {
        this.x = x;
        this.z = z;
    }
    
    public RegionCoord(ChunkCoord chunkCoord)
    {
        this.x = chunkCoord.getX() / 32;
        this.z = chunkCoord.getZ() / 32;
    }
    
    public RegionCoord addX(int amount)
    {
        return new RegionCoord(this.x + amount, this.z);
    }
    
    public RegionCoord addZ(int amount)
    {
        return new RegionCoord(this.x, this.z + amount);
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
    
    public double getEuclideanDistance(RegionCoord regionCoord)
    {
        return Math.sqrt(Math.pow(getX() - regionCoord.getX(), 2) + (Math.pow(getZ() - regionCoord.getZ(), 2)));
    }
    
    public static ArrayList<ChunkCoord> getChunkCoords(RegionCoord regionCoord)
    {
        ArrayList<ChunkCoord> chunkCoords = new ArrayList<ChunkCoord>();
        ChunkCoord baseChunkCoord = new ChunkCoord(regionCoord.getX() * 32, regionCoord.getZ() * 32);
        for(int x = baseChunkCoord.getX(); x < baseChunkCoord.getX() + 32; x++)
        {
            for(int z = baseChunkCoord.getZ(); x < baseChunkCoord.getZ() + 32; x++)
            {
                chunkCoords.add(new ChunkCoord(x, z));
            }
        }
        return chunkCoords;
    }
    
    @Override
    public String toString()
    {
        return "{" + "x=" + x + ", z=" + z + '}';
    }
}
