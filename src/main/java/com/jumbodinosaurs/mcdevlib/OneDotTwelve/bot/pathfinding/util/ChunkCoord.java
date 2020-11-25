package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

import com.jumbodinosaurs.devlib.util.objects.Point2D;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;

public class ChunkCoord extends Point2D
{
    
    
    public ChunkCoord(double x, double z)
    {
        super(x, z);
    }
    
    @Override
    public ArrayList<ChunkCoord> getNeighbors()
    {
        ArrayList<ChunkCoord> neighbors = new ArrayList<ChunkCoord>();
        for(Direction direction : Direction.values())
        {
            if(!direction.equals(Direction.SAMEPOINT))
            {
                neighbors.add((ChunkCoord) this.differenceX(direction.x).differenceZ(direction.z));
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
