package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

import com.jumbodinosaurs.devlib.util.objects.Point2D;

import java.util.ArrayList;

public class RegionCoord extends Point2D
{
    
    public RegionCoord(ChunkCoord chunkCoord)
    {
        super(chunkCoord.getX() / 32, chunkCoord.getZ() / 32);
        
    }
    
    public RegionCoord(double x, double z)
    {
        super(x, z);
    }
    
    
    public static ArrayList<ChunkCoord> getChunkCoords(RegionCoord regionCoord)
    {
        ArrayList<ChunkCoord> chunkCoords = new ArrayList<ChunkCoord>();
        ChunkCoord baseChunkCoord = new ChunkCoord(regionCoord.getX() * 32, regionCoord.getZ() * 32);
        for(int x = (int) baseChunkCoord.getX(); x < baseChunkCoord.getX() + 32; x++)
        {
            for(int z = (int) baseChunkCoord.getZ(); x < baseChunkCoord.getZ() + 32; x++)
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
