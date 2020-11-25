package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

import com.jumbodinosaurs.devlib.util.objects.Point;
import com.jumbodinosaurs.devlib.util.objects.Point3D;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class MinecraftPoint3D extends Point3D
{
    
    
    public MinecraftPoint3D(BlockPos pos)
    {
        super(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public MinecraftPoint3D(double x, double y, double z)
    {
        super(x, y, z);
    }
    
    
    @Override
    public <E extends Point> ArrayList<E> getNeighbors()
    {
        ArrayList<MinecraftPoint3D> neighbors = super.getNeighbors();
        int maxMinecraftWorldHeight = 256;
        int minMinecraftWorldHeight = 0;
        for(int i = 0; i < neighbors.size(); i++)
        {
            MinecraftPoint3D currentPoint = neighbors.get(i);
            if(currentPoint.getY() <= maxMinecraftWorldHeight || currentPoint.getY() <= minMinecraftWorldHeight)
            {
                neighbors.remove(i);
            }
        }
        return (ArrayList<E>) neighbors;
    }
    
    
    public ArrayList<MinecraftPoint3D> getNeighborsAligned3D(boolean alignedToMiddle)
    {
        if(alignedToMiddle)
        {
            return this.toMiddleCoord().getNeighbors();
        }
        return this.toAlignedCoord().getNeighbors();
    }
    
    public MinecraftPoint3D copy()
    {
        return new MinecraftPoint3D(getX(), getY(), getZ());
    }
    
    public String toHashString()
    {
        return "{ X: " + getX() + ", Y: " + getY() + ", Z: " + getZ() + "}";
    }
    
    public MinecraftPoint3D getOneBelow()
    {
        return (MinecraftPoint3D) differenceY(-1);
    }
    
    public boolean isSameBlockPos(MinecraftPoint3D wayPoint)
    {
        return this.toBlockPos().equals(wayPoint.toBlockPos());
    }
    
    public MinecraftPoint3D chop()
    {
        return new MinecraftPoint3D((int) this.x, (int) this.y, (int) this.z);
    }
    
    public MinecraftPoint3D toMiddleCoord()
    {
        return PathFindingUtil.getBlockMiddle(this);
    }
    
    
    /*Maybe Look at the wiki next time you big dumb
     * https://en.wikipedia.org/wiki/Euclidean_distance
     * */
    public double getNoSqRtEuclideanDistance(MinecraftPoint3D wayPoint)
    {
        return Math.pow(getX() - wayPoint.getX(), 2) +
               (Math.pow(getZ() - wayPoint.getZ(), 2)) +
               (Math.pow(getY() - wayPoint.getY(), 2));
    }
    
    public boolean equals(MinecraftPoint3D wayPoint)
    {
        return this.x == wayPoint.getX() && this.z == wayPoint.getZ() && this.y == wayPoint.getY();
    }
    
    public ChunkCoord toChunkCoord()
    {
        int xCoord, zCoord;
        xCoord = (int) ((this.x - (this.x % 16)) / 16);
        zCoord = (int) ((this.z - (this.z % 16)) / 16);
        return new ChunkCoord(xCoord, zCoord);
    }
    
    
    public BlockPos toBlockPos()
    {
        return MinecraftPoint3D.toBlockPos(this);
    }
    
    public MinecraftPoint3D toAlignedCoord()
    {
        return MinecraftPoint3D.getAligned(this);
    }
    
    //Get Aligned and toBlockPos might look a bit over complicated but in order to keep everything aligned with
    // minecrafts coordinate system you go to do some extra
    public static BlockPos toBlockPos(MinecraftPoint3D wayPoint)
    {
        MinecraftPoint3D wayPointChopped = wayPoint.chop();
        if(!wayPoint.equals(wayPointChopped))
        {
            int x, y, z;
            x = (int) wayPointChopped.getX();
            z = (int) wayPointChopped.getZ();
            y = (int) wayPointChopped.getY();
            Quadrant quadrantOffSet = PathFindingUtil.getQuadrant(wayPointChopped);
            
            if(wayPointChopped.getX() == 0)
            {
                if(Math.abs(wayPoint.getX()) != wayPoint.getX())
                {
                    x = -1;
                }
                else
                {
                    x = 0;
                }
            }
            else
            {
                x = x + quadrantOffSet.x;
            }
            
            if(wayPointChopped.getZ() == 0)
            {
                if(Math.abs(wayPoint.getZ()) != wayPoint.getZ())
                {
                    z = -1;
                }
                else
                {
                    z = 0;
                }
            }
            else
            {
                z = z + quadrantOffSet.z;
            }
            return new BlockPos(x, y, z);
        }
        else
        {
            return new BlockPos(wayPoint.getX(), wayPoint.getY(), wayPoint.getZ());
        }
        
    }
    
    //Extra
    public static MinecraftPoint3D getAligned(MinecraftPoint3D wayPoint)
    {
        MinecraftPoint3D wayPointChopped = wayPoint.chop();
        if(!wayPoint.equals(wayPointChopped))
        {
            int x, y, z;
            x = (int) wayPointChopped.getX();
            z = (int) wayPointChopped.getZ();
            y = (int) wayPointChopped.getY();
            Quadrant quadrantOffSet = PathFindingUtil.getQuadrant(wayPointChopped);
            
            if(wayPointChopped.getX() == 0)
            {
                if(Math.abs(wayPoint.getX()) != wayPoint.getX())
                {
                    x = -1;
                }
                else
                {
                    x = 0;
                }
            }
            else
            {
                x = x + quadrantOffSet.x;
            }
            
            if(wayPointChopped.getZ() == 0)
            {
                if(Math.abs(wayPoint.getZ()) != wayPoint.getZ())
                {
                    z = -1;
                }
                else
                {
                    z = 0;
                }
            }
            else
            {
                z = z + quadrantOffSet.z;
            }
            return new MinecraftPoint3D(x, y, z);
        }
        else
        {
            return wayPoint;
        }
    }
    
    
}
